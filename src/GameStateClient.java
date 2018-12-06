import jig.Vector;
import org.lwjgl.Sys;
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 *
 */
class GameStateClient extends BasicGameState
{
    private World    		world;
    private Player   		player1;
    private Player          player2;
    private Client          client;

    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException
    {
        world = new World();
        world.loadWorldFromFile( "one" );
    }

    @Override
    public void enter(GameContainer container, StateBasedGame game)
    {
        container.setSoundOn(true);
        ContraGame cg = (ContraGame) game;

        player1 = new Player( world,  Player.Type.PINK  );
        player2 = new Player( world,  Player.Type.BLUE  );

        client  = new Client("localhost", 9999 );
        try {
            client.start();
        }
        catch ( Exception e )
        {
            e.printStackTrace();
        }
        System.out.println( "Leaving Enter State\n" );
    }
    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        ContraGame tg = (ContraGame)sbg;
        world.render(g);
        player1.render(g);
        player2.render(g);
    }

    @Override
    public void update(GameContainer gc, StateBasedGame game, int delta) throws SlickException {

        ClientPacket cp = new ClientPacket();
        if (gc.getInput().isKeyDown(Input.KEY_A)) {
            cp.setClientHorizontalControl(ClientPacket.ClientHorizontalControl.LEFT);
        }
        else
        if (gc.getInput().isKeyDown(Input.KEY_D)) {
            cp.setClientHorizontalControl(ClientPacket.ClientHorizontalControl.RIGHT);

        } else {
            cp.setClientHorizontalControl(ClientPacket.ClientHorizontalControl.NONE);
        }

        if (gc.getInput().isKeyDown(Input.KEY_W)) {
           cp.setClientVerticalControl(ClientPacket.ClientVerticalControl.UP);
        }
        else
        if (gc.getInput().isKeyDown(Input.KEY_S)){
            cp.setClientVerticalControl(ClientPacket.ClientVerticalControl.DOWN);
        }
        else {
            cp.setClientVerticalControl(ClientPacket.ClientVerticalControl.NONE);
        }

        if (gc.getInput().isKeyDown(Input.KEY_J)) {
            cp.setClientJump( true );
        }
        else
        {
            cp.setClientJump( false );
        }

        if (gc.getInput().isKeyDown(Input.KEY_K)) {
            cp.setClientFire( true );
        }
        else
        {
            cp.setClientFire( false );
        }
        client.writeToServer( cp );
        ServerPacket c = client.readFromServer();
        if( c != null )
        {
            player1.playerDesc = c.getLocalPlayerDescriptor();
            player1.setPosition( player1.getX(), c.getLocalPlayerWorldPosition().getY() );
            ContraGame.VIEWPORT.setViewPortOffset( new Vector( player1.getX() - c.getLocalPlayerWorldPosition().getX(), 0 ) );
            player1.updateAnimation(gc,game,delta);

            player2.playerDesc = c.getServerPlayerDescriptor();
            player2.setPosition( new Vector( c.getServerPlayerWorldPosition().getX() + ContraGame.VIEWPORT.getViewPortOffsetTopLeft().getX(), c.getServerPlayerWorldPosition().getY() ) );
            player2.updateAnimation(gc,game,delta);
        }
        world.update(gc,game, delta);
    }

    @Override
    public int getID() {
        return ContraGame.CLIENT;
    }
}