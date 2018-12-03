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
	private ServerSocketChannel clientConnection;
	private ServerSocket clientSocket;
	private InetSocketAddress clientAddress;

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException
	{
		world    = new World();
		world.loadWorldFromFile( "one" );



	}

	@Override
	public void enter(GameContainer container, StateBasedGame game)
	{
		createConnection();

		container.setSoundOn(true);
		ContraGame cg = (ContraGame) game;

		player1 = new Player( world );
		player2 = new NetworkPlayer( world , player1 );


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


	public void createConnection(){
		try {
			// Selector: multiplexor of SelectableChannel objects
			Selector selector = Selector.open(); // selector is open here

			// ServerSocketChannel: selectable channel for stream-oriented listening sockets
			ServerSocketChannel crunchifySocket = ServerSocketChannel.open();
			InetSocketAddress crunchifyAddr = new InetSocketAddress("127.0.0.1", 8001);

			// Binds the channel's socket to a local address and configures the socket to listen for connections
			crunchifySocket.bind(crunchifyAddr);

			// Adjusts this channel's blocking mode.
			crunchifySocket.configureBlocking(false);

			int ops = crunchifySocket.validOps();
			SelectionKey selectKy = crunchifySocket.register(selector, ops, null);


			// Infinite loop..
			// Keep server running
			while (true) {

				// Selects a set of keys whose corresponding channels are ready for I/O operations
				selector.select();

				// token representing the registration of a SelectableChannel with a Selector
				Set<SelectionKey> crunchifyKeys = selector.selectedKeys();
				Iterator<SelectionKey> crunchifyIterator = crunchifyKeys.iterator();

				while (crunchifyIterator.hasNext()) {
					SelectionKey myKey = crunchifyIterator.next();

					// Tests whether this key's channel is ready to accept a new socket connection
					if (myKey.isAcceptable()) {
						SocketChannel crunchifyClient = crunchifySocket.accept();

						// Adjusts this channel's blocking mode to false
						crunchifyClient.configureBlocking(false);

						// Operation-set bit for read operations
						crunchifyClient.register(selector, SelectionKey.OP_READ);
						System.out.println("Connection Accepted: " + crunchifyClient.getLocalAddress() + "\n");
						break;

						// Tests whether this key's channel is ready for reading
					} else if (myKey.isReadable()) {

						SocketChannel crunchifyClient = (SocketChannel) myKey.channel();
						ByteBuffer crunchifyBuffer = ByteBuffer.allocate(256);
						crunchifyClient.read(crunchifyBuffer);
						String result = new String(crunchifyBuffer.array()).trim();

						System.out.println("Message received: " + result);

						if (result.equals("Crunchify")) {
							crunchifyClient.close();
							System.out.println("\nIt's time to close connection as we got last company name 'Crunchify'");
							System.out.println("\nServer will keep running. Try running client again to establish new connection");
						}
					}
					crunchifyIterator.remove();
				}
			}

		}
		catch (Exception e){
			System.out.println(e);
		}

	}


	
}