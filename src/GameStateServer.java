import jig.Vector;
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 *
 */
class GameStateServer extends BasicGameState
{
    private World    		world;
    private Player   		localPlayer;
    private NetworkPlayer   networkPlayer;
    private Server          server;
    private EnemyManager    enemyManager;
    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException
    {
        world = new World();
        world.loadWorldFromFile( "one" );

    }

    @Override
    public void enter(GameContainer container, StateBasedGame game)
    {
        ContraGame cg = (ContraGame) game;
        container.setSoundOn(true);

        server = new Server( 9999 );
        try {
            server.start();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        localPlayer   = new Player( world, Player.Type.BLUE );
        networkPlayer = new NetworkPlayer( world , localPlayer, server );
        enemyManager  = new EnemyManager(world, localPlayer);
    }
    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        world.render(g);
        localPlayer.render(g);
        networkPlayer.render(g);
        enemyManager.render(gc, sbg, g);
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
        world.update(gc, sbg, delta);
        localPlayer.update(gc, sbg, delta );
        networkPlayer.update(gc, sbg, delta );
        enemyManager.update(gc,sbg,delta);

        server.writeToClient( new ServerPacket( new PlayerDescriptor( networkPlayer.playerDesc ), new Vector( networkPlayer.playerPosition ),
                                                new PlayerDescriptor( localPlayer.playerDesc ),   new Vector( localPlayer.playerPosition ),
                enemyManager ));
    }

    @Override
    public int getID() {
        return ContraGame.HOST;
    }
}