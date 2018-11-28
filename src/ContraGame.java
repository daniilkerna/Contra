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

	public static ViewPort VIEWPORT;
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


	public static final String WORLD_BLOCK_GOLD_RSC       = "resource/block/gold_brick.png";

	//Bullet Images
	public static final String BULLET_REGULAR_RSC       = "resource/bullet/regular.png";
	public static final String BULLET_UPGRADE_RSC       = "resource/bullet/upgrade.png";

	/*
	*  Game Scale Factor :
	*   	Scalar used to keep proportions the same on resolution change.
	* */
	public static final  int   GAME_SCALE_FACTOR = 3;

	/*
	* 	Hash to store all the sprite sheets
	*
	* */
	public static HashMap<String, SpriteSheet> spriteSheetHashMap = new HashMap<>();

	/*
	 * 	Hash to store all images
	 *
	 * */
	public static HashMap<String, Image> imageAssetHashMap = new HashMap<>();


	/*
	 * 	Hash to store all block Textures
	 *
	 * */
	public static HashMap<String, Image> blockTextureHashMap = new HashMap<>();

	/**
	 * Create the BounceGame frame, saving the width and height for later use.
	 * 
	 * @param title
	 *            the window's title

	 */
	public ContraGame(String title) {
		super(title);
		Entity.setCoarseGrainedCollisionBoundary(Entity.AABB);
	}

	public static SpriteSheet getSpriteSheet( String key )
	{
		return spriteSheetHashMap.get( key );
	}

	public static Image getImageAsset( String key )
	{
		return imageAssetHashMap.get( key );
	}
	public static Image       getBlockTexture( String key ) { return blockTextureHashMap.get( key ); }

	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		addState(new StartUpState());
		addState(new GameOverState());
		addState(new GameState());

		VIEWPORT = new ViewPort( container.getWidth(), container.getHeight() );

		ResourceManager.loadImage(Contra_Banner_RSC);

		loadPlayerAssets();
		loadWorldAssets();
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

	/*
	* Loads all the assets assosiated with the player.
	* */
	private void loadPlayerAssets()
	{
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
		//Bullet Images
		ResourceManager.loadImage( BULLET_REGULAR_RSC  );
		ResourceManager.loadImage( BULLET_UPGRADE_RSC  );

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
		/**/
		ResourceManager.getImage( BULLET_REGULAR_RSC  ).setFilter(Image.FILTER_NEAREST);
		ResourceManager.getImage( BULLET_UPGRADE_RSC  ).setFilter(Image.FILTER_NEAREST);


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

		// Bullet Assets
		imageAssetHashMap.put( "BULLET_REGULAR", ResourceManager.getImage(BULLET_REGULAR_RSC) );
		imageAssetHashMap.put( "BULLET_UPGRADE", ResourceManager.getImage(BULLET_UPGRADE_RSC) );
	}

	/*
	 *
	 */
	private void loadWorldAssets()
	{
		ResourceManager.loadImage(WORLD_BLOCK_GOLD_RSC);
		ResourceManager.getImage(WORLD_BLOCK_GOLD_RSC).setFilter(Image.FILTER_NEAREST);

		blockTextureHashMap.put( "GOLD_BRICK", ResourceManager.getImage(WORLD_BLOCK_GOLD_RSC) );
	}

}
