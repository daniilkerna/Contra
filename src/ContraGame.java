import java.util.ArrayList;
import java.util.HashMap;

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
	public static final String PLAYER_RUN_RIGHT_RSC = "resource/playerRunningRight.png";
    public static final String PLAYER_RUN_LEFT_RSC = "resource/playerRunningLeft.png";




	private static HashMap<String, SpriteSheet> spriteSheetHashMap = new HashMap<>();
	/**
	 * Create the BounceGame frame, saving the width and height for later use.
	 * 
	 * @param title
	 *            the window's title

	 */
	public ContraGame(String title) {
		super(title);

		Entity.setCoarseGrainedCollisionBoundary(Entity.AABB);
		//Entity.setCoarseGrainedCollisionBoundary(Entity.CIRCLE);

	}

	public static HashMap<String, SpriteSheet> getSpriteSheetHashMap() {
		return spriteSheetHashMap;
	}

	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		addState(new StartUpState());
		addState(new GameOverState());
		addState(new PlayingState());

		ResourceManager.loadImage( PLAYER_RUN_RIGHT_RSC );
		ResourceManager.getImage( PLAYER_RUN_RIGHT_RSC ).setFilter(Image.FILTER_NEAREST);

		spriteSheetHashMap.put( "PLAYER_RUN_RIGHT_SS", new SpriteSheet( ResourceManager.getImage( PLAYER_RUN_RIGHT_RSC ), 20 , 36 ) );
        spriteSheetHashMap.put( "PLAYER_RUN_LEFT_SS" , new SpriteSheet( ResourceManager.getImage( PLAYER_RUN_RIGHT_RSC ).getFlippedCopy( true, false ), 20 , 36 ) );

        //spriteSheetHashMap.put( "PLAYER_RUN_LEFT_SS ", new SpriteSheet( ResourceManager.getImage( PLAYER_RUN_RIGHT_RSC ).getFlippedCopy( true, false ), 37 , 45 ) );

       // ResourceManager.loadImage( PLAYER_RUN_LEFT_RSC );
       // ResourceManager.getImage(PLAYER_RUN_LEFT_RSC).setFilter(Image.FILTER_NEAREST);
       // spriteSheetHashMap.put( "PLAYER_RUN_LEFT_SS", ResourceManager.getSpriteSheet(PLAYER_RUN_LEFT_RSC , 37 , 45) );
		// preload all the resources to avoid warnings & minimize latency...
		ResourceManager.loadImage(Contra_Banner_RSC);







	}
	
	public static void main(String[] args) {
		AppGameContainer app;
		try {
			app = new AppGameContainer(new ContraGame("Contra"));
			app.setDisplayMode(256*2, 240*2, false);
			app.setVSync(true);
			app.setAlwaysRender(true);
			app.setShowFPS(true);
			app.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}

	}




	
}
