import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.SocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.util.Random;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

import jig.ResourceManager;
import jig.Vector;
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;


/**
 *
 */

class GameState extends BasicGameState
{
	private World    		world;
	private Player   		player1;
	private NetworkPlayer   player2;
	private Server          server;
	private Client          client;

	private ServerSocketChannel clientConnection;
	private ServerSocket        clientSocket;
	private InetSocketAddress   clientAddress;

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException
	{
		world    = new World();
		world.loadWorldFromFile( "one" );
	}

	@Override
	public void enter(GameContainer container, StateBasedGame game)
	{
		container.setSoundOn(true);
		ContraGame cg = (ContraGame) game;

		player1 = new Player( world,  Player.Type.PINK  );
		player2 = new NetworkPlayer( world , player1, server );

		server  = new Server(9999 );
		server.start();

		client  = new Client("localhost", 9999 );
	}
	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		ContraGame tg = (ContraGame)game;

		ContraGame.VIEWPORT.render(g);
		world.render(g);
		player1.render(g);
		player2.render(g);

		//SpriteSheet ss = new SpriteSheet( ResourceManager.getImage( ContraGame.PLAYER_RUN_RIGHT_RSC ).getFlippedCopy( true, false ), 37, 45 );
		//ss.getSprite( 3, 0).draw( 100, 100 );
	}

	@Override
	public void update(GameContainer container, StateBasedGame game,
			int delta) throws SlickException {


		world.update(container,game, delta);
		player1.update(container, game, delta );
		player2.update(container, game, delta );
	}

	@Override
	public int getID() {
		return ContraGame.PLAYINGSTATE;
	}

	//return random int [0,maximum]
	public int getRandomInt(int maximum){
		Random random = new Random();
		int number = random.nextInt(maximum + 1);

		return number;
	}
}