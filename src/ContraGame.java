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

	/**
	 * Asset directories
	 */
	public static final String Contra_Banner_RSC          = "resource/contra_banner.png";

	/* Player prone animations */
	public static final String PLAYER_PRONE_LEFT_RSC      = "resource/player/animation_prone_left.png";
	public static final String PLAYER_PRONE_RIGHT_RSC     = "resource/player/animation_prone_right.png";

	/* Player run animations */
	public static final String PLAYER_RUN_LEFT_RSC        = "resource/player/animation_run_left.png";
	public static final String PLAYER_RUN_LEFT_UP_RSC     = "resource/player/animation_run_left_up.png";
	public static final String PLAYER_RUN_LEFT_DOWN_RSC   = "resource/player/animation_run_left_down.png";

	public static final String PLAYER_RUN_RIGHT_RSC       = "resource/player/animation_run_right.png";
	public static final String PLAYER_RUN_RIGHT_UP_RSC    = "resource/player/animation_run_right_up.png";
	public static final String PLAYER_RUN_RIGHT_DOWN_RSC  = "resource/player/animation_run_right_down.png";

	/* Jump left and right animation */
	public static final String PLAYER_RUN_JUMP_LEFT_RSC   = "resource/player/animation_jump_left.png";
	public static final String PLAYER_RUN_JUMP_RIGHT_RSC  = "resource/player/animation_jump_right.png";

	/*Gun firing animation*/
	public static final String PLAYER_FIRE_LEFT_RSC       = "resource/player/animation_fire_left.png";
	public static final String PLAYER_FIRE_RIGHT_RSC      = "resource/player/animation_fire_right.png";

	public static final String PLAYER_FIRE_LEFT_UP_RSC    = "resource/player/animation_fire_left_up.png";
	public static final String PLAYER_FIRE_RIGHT_UP_RSC   = "resource/player/animation_fire_right_up.png";
	/*
	*  Game Scale Factor :
	*   	Scalar used to keep proportions the same on resolution change.
	* */
	public static final  int   GAME_SCALE_FACTOR = 3;

	/*
	* 	Hash to store all the sprite sheets
	*
	* */
	private HashMap<String, SpriteSheet> spriteSheetHashMap;

	/**
	 * Create the BounceGame frame, saving the width and height for later use.
	 * 
	 * @param title
	 *            the window's title

	 */
	public ContraGame(String title) {
		super(title);
		Entity.setCoarseGrainedCollisionBoundary(Entity.AABB);

		/**
		 * Initialise, variables
		 */
		spriteSheetHashMap = new HashMap<>();
	}

	private HashMap<String, SpriteSheet> getSpriteSheetHashMap() {
		return spriteSheetHashMap;
	}


	public SpriteSheet getSpriteSheet( String key )
	{
		return spriteSheetHashMap.get( key );
	}

	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		addState(new StartUpState());
		addState(new GameOverState());
		addState(new GameState());

		ResourceManager.loadImage(Contra_Banner_RSC);

		/*Player prone animations*/
		ResourceManager.loadImage( PLAYER_PRONE_LEFT_RSC     );
		ResourceManager.loadImage( PLAYER_PRONE_RIGHT_RSC    );
		/* Load running left images */
		ResourceManager.loadImage( PLAYER_RUN_LEFT_RSC       );
        ResourceManager.loadImage( PLAYER_RUN_LEFT_UP_RSC    );
		ResourceManager.loadImage( PLAYER_RUN_LEFT_DOWN_RSC  );
		/* Load running right images */
		ResourceManager.loadImage( PLAYER_RUN_RIGHT_RSC      );
		ResourceManager.loadImage( PLAYER_RUN_RIGHT_UP_RSC   );
		ResourceManager.loadImage( PLAYER_RUN_RIGHT_DOWN_RSC );
		/* Load running jump images */
		ResourceManager.loadImage( PLAYER_RUN_JUMP_LEFT_RSC  );
		ResourceManager.loadImage( PLAYER_RUN_JUMP_RIGHT_RSC );
		/* Fire animations */
		ResourceManager.loadImage( PLAYER_FIRE_LEFT_RSC      );
		ResourceManager.loadImage( PLAYER_FIRE_RIGHT_RSC     );
		ResourceManager.loadImage( PLAYER_FIRE_LEFT_UP_RSC   );
		ResourceManager.loadImage( PLAYER_FIRE_RIGHT_UP_RSC  );

		/* Set image filtering */
		ResourceManager.getImage( PLAYER_PRONE_LEFT_RSC     ).setFilter(Image.FILTER_NEAREST);
		ResourceManager.getImage( PLAYER_PRONE_RIGHT_RSC    ).setFilter(Image.FILTER_NEAREST);
		/**/
		ResourceManager.getImage( PLAYER_RUN_LEFT_RSC       ).setFilter(Image.FILTER_NEAREST);
		ResourceManager.getImage( PLAYER_RUN_LEFT_UP_RSC    ).setFilter(Image.FILTER_NEAREST);
		ResourceManager.getImage( PLAYER_RUN_LEFT_DOWN_RSC  ).setFilter(Image.FILTER_NEAREST);
		/**/
		ResourceManager.getImage( PLAYER_RUN_RIGHT_RSC      ).setFilter(Image.FILTER_NEAREST);
		ResourceManager.getImage( PLAYER_RUN_RIGHT_UP_RSC   ).setFilter(Image.FILTER_NEAREST);
		ResourceManager.getImage( PLAYER_RUN_RIGHT_DOWN_RSC ).setFilter(Image.FILTER_NEAREST);
		/**/
		ResourceManager.getImage( PLAYER_RUN_JUMP_LEFT_RSC  ).setFilter(Image.FILTER_NEAREST);
		ResourceManager.getImage( PLAYER_RUN_JUMP_RIGHT_RSC ).setFilter(Image.FILTER_NEAREST);
		/**/
		ResourceManager.getImage( PLAYER_FIRE_LEFT_RSC      ).setFilter(Image.FILTER_NEAREST);
		ResourceManager.getImage( PLAYER_FIRE_RIGHT_RSC     ).setFilter(Image.FILTER_NEAREST);
		ResourceManager.getImage( PLAYER_FIRE_LEFT_UP_RSC   ).setFilter(Image.FILTER_NEAREST);
		ResourceManager.getImage( PLAYER_FIRE_RIGHT_UP_RSC  ).setFilter(Image.FILTER_NEAREST);

		/* Prone Animation */
		spriteSheetHashMap.put( "PLAYER_PRONE_LEFT_SS"  ,
				new SpriteSheet( ResourceManager.getImage( PLAYER_PRONE_LEFT_RSC     ), 32 , 16 ) );
		spriteSheetHashMap.put( "PLAYER_PRONE_RIGHT_SS"  ,
				new SpriteSheet( ResourceManager.getImage( PLAYER_PRONE_RIGHT_RSC    ), 32 , 16 ) );

		/* Run Left */
		spriteSheetHashMap.put( "PLAYER_RUN_LEFT_SS"  ,
				new SpriteSheet( ResourceManager.getImage( PLAYER_RUN_LEFT_RSC       ), 20 , 35 ) );
		spriteSheetHashMap.put( "PLAYER_RUN_LEFT_UP_SS"  ,
				new SpriteSheet( ResourceManager.getImage( PLAYER_RUN_LEFT_UP_RSC    ), 21 , 35 ) );
		spriteSheetHashMap.put( "PLAYER_RUN_LEFT_DOWN_SS"  ,
				new SpriteSheet( ResourceManager.getImage( PLAYER_RUN_LEFT_DOWN_RSC  ), 21 , 35 ) );

		/* Run Right */
        spriteSheetHashMap.put( "PLAYER_RUN_RIGHT_SS" ,
				new SpriteSheet( ResourceManager.getImage( PLAYER_RUN_RIGHT_RSC      ), 20 , 35 ) );
		spriteSheetHashMap.put( "PLAYER_RUN_RIGHT_UP_SS" ,
				new SpriteSheet( ResourceManager.getImage( PLAYER_RUN_RIGHT_UP_RSC   ), 21 , 35 ) );
		spriteSheetHashMap.put( "PLAYER_RUN_RIGHT_DOWN_SS" ,
				new SpriteSheet( ResourceManager.getImage( PLAYER_RUN_RIGHT_DOWN_RSC ), 21 , 35 ) );

		/* Jump animations */
		spriteSheetHashMap.put( "PLAYER_JUMP_LEFT_SS" ,
				new SpriteSheet( ResourceManager.getImage( PLAYER_RUN_JUMP_LEFT_RSC  ), 20 , 20 ) );
		spriteSheetHashMap.put( "PLAYER_JUMP_RIGHT_SS",
				new SpriteSheet( ResourceManager.getImage( PLAYER_RUN_JUMP_RIGHT_RSC ), 20 , 20 ) );

		/* Fire animations */
		spriteSheetHashMap.put( "PLAYER_FIRE_LEFT_SS" ,
				new SpriteSheet( ResourceManager.getImage( PLAYER_FIRE_LEFT_RSC  ), 23 , 35 ) );
		spriteSheetHashMap.put( "PLAYER_FIRE_RIGHT_SS",
				new SpriteSheet( ResourceManager.getImage( PLAYER_FIRE_RIGHT_RSC ), 23 , 35 ) );

		/* Fire animations */
		spriteSheetHashMap.put( "PLAYER_FIRE_LEFT_UP_SS" ,
				new SpriteSheet( ResourceManager.getImage( PLAYER_FIRE_LEFT_UP_RSC  ), 15 , 45 ) );
		spriteSheetHashMap.put( "PLAYER_FIRE_RIGHT_UP_SS",
				new SpriteSheet( ResourceManager.getImage( PLAYER_FIRE_RIGHT_UP_RSC ), 15 , 45 ) );
	}
	
	public static void main(String[] args) {
		AppGameContainer app;
		try {
			app = new AppGameContainer(new ContraGame("Contra"));
			app.setDisplayMode(1920/2, 1080/2, false);
			app.setVSync(true);
			app.setAlwaysRender(true);
			app.setShowFPS(true);
			app.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}

	}
}
