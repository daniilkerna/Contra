import java.util.ArrayList;

import jig.Entity;
import jig.ResourceManager;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.*;

/**
 * A Clone on Contra
 *
 *
 * by Daniil Kernazhytski and Alex K
 *
 * WSU Fall 2018
 * 
 */
public class ContraGame extends StateBasedGame {
	
	public static final int STARTUPSTATE = 0;
	public static final int PLAYINGSTATE = 1;
	public static final int PLAYINGSTATE2 = 2;
	public static final int GAMEOVERSTATE = 3;

	public static final String Contra_Banner_RSC = "resource/contra_banner.png";



	public final int ScreenWidth;
	public final int ScreenHeight;


	/**
	 * Create the BounceGame frame, saving the width and height for later use.
	 * 
	 * @param title
	 *            the window's title
	 * @param width
	 *            the window's width
	 * @param height
	 *            the window's height
	 */
	public ContraGame(String title, int width, int height) {
		super(title);
		ScreenHeight = height;
		ScreenWidth = width;

		Entity.setCoarseGrainedCollisionBoundary(Entity.AABB);
		//Entity.setCoarseGrainedCollisionBoundary(Entity.CIRCLE);

	}


	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		addState(new StartUpState());
		addState(new GameOverState());
		addState(new PlayingState());
		


		// preload all the resources to avoid warnings & minimize latency...
		ResourceManager.loadImage(Contra_Banner_RSC);






	}
	
	public static void main(String[] args) {
		AppGameContainer app;
		try {
			app = new AppGameContainer(new ContraGame("Contra", 600, 600));
			app.setDisplayMode(600, 600, false);
			app.setVSync(true);
			app.setAlwaysRender(true);
			app.setShowFPS(true);
			app.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}

	}




	
}
