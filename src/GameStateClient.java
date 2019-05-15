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
    private int frameCount = 0;
    private World    		world;
    private Player   		player1;
    private Player          player2;
    private Client          client;
    private int             numberOfLives = 0;

    private Vector            localPlayerWorkPosition;
    private NetworkEntityList networkEntityList;

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

        g.drawString("Lives Left:" + numberOfLives, 10 , 30);

        if( networkEntityList == null )
            return;

        if(  networkEntityList.getEntities() == null )
            return;

        for( NetworkEntity ne : networkEntityList.getEntities() )
        {
            switch ( ne.getEntityType() )
            {
                case BULLET:
                    Bullet b = new Bullet(ne.getPosition().getX() , ne.getPosition().getY());
                    b.setPosition( ne.getPosition().getX()  + ContraGame.VIEWPORT.getViewPortOffsetTopLeft().getX(), ne.getPosition().getY() );
                    b.render(g);
                    break;
                case SNIPER:
                    EnemySniper es = new EnemySniper(world , player1 , player2 , ne.getPosition().getX(), ne.getPosition().getY() , ne.getPlayerDesc() );
                    es.setPosition( ne.getPosition().getX() + ContraGame.VIEWPORT.getViewPortOffsetTopLeft().getX(), ne.getPosition().getY() );
                    es.setPlayerDesc(ne.getPlayerDesc());
                    //System.out.println("Animation: " + ne.getCurrentAnimation());
                    es.setAnimationFrame1(ne.getCurrentAnimation() , 0);
                    es.render(g);
                    break;
                case RUNNER:
                    //EnemyRunner er =  new EnemyRunner(world , player1);
                    //er.playerDesc = ne.getPlayerDesc();
                    //er.render(g);
                    break;
                case TURRET:
                    EnemyTurret et = new EnemyTurret( ne.getPosition().getX() , ne.getPosition().getY() , player2 , player1);
                    et.setPosition( ne.getPosition().getX() + ContraGame.VIEWPORT.getViewPortOffsetTopLeft().getX(), ne.getPosition().getY() );
                    et.setTurretState(ne.getTurretState());
                    et.updateAnimation();
                    et.render(g);
                    break;
            }
        }
    }

    @Override
    public void update(GameContainer gc, StateBasedGame game, int delta) throws SlickException {

        frameCount += 1;
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

        if (gc.getInput().isKeyPressed(Input.KEY_J)) {
            cp.setClientJump( true );
        }
        else
        {
            cp.setClientJump( false );
        }

        if (gc.getInput().isKeyPressed(Input.KEY_K)) {
            cp.setClientFire( true );
        }
        else
        {
            cp.setClientFire( false );
        }

        world.update(gc,game, delta);

        client.writeToServer(cp);


        ServerPacket c = client.readFromServer();
        if (c != null) {
            networkEntityList  = c.getServerEntityList();
            player1.playerDesc = c.getLocalPlayerDescriptor();
            localPlayerWorkPosition = c.getLocalPlayerWorldPosition();

            player1.setPosition(player1.getX(), c.getLocalPlayerWorldPosition().getY());
            ContraGame.VIEWPORT.setViewPortOffset(new Vector(player1.getX() - c.getLocalPlayerWorldPosition().getX(), 0));
            player1.updateAnimation(gc, game, delta);

            player2.playerDesc = c.getServerPlayerDescriptor();
            player2.setPosition(new Vector(c.getServerPlayerWorldPosition().getX() + ContraGame.VIEWPORT.getViewPortOffsetTopLeft().getX(), c.getServerPlayerWorldPosition().getY()));
            player2.updateAnimation(gc, game, delta);
            numberOfLives = c.getNumberOfLives();
        }
    }

    @Override
    public int getID() {
        return ContraGame.CLIENT;
    }
}