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

	public static final int STARTUPSTATE  = 0;
	public static final int CLIENT        = 1;
	public static final int HOST          = 2;
	public static final int PLAYINGSTATE  = 3;
	public static final int GAMEOVERSTATE = 4;

	public static ViewPort VIEWPORT;
	/**
	 * Asset directories
	 */
	public static final String Contra_Banner_RSC          = "resource/contra-u00001.png";

	public static final String PLAYER_PRONE_LEFT_RSC       = "resource/player/animation_prone_left.png";
	public static final String PLAYER_PRONE_RIGHT_RSC      = "resource/player/animation_prone_right.png";
	public static final String PLAYER_RUN_LEFT_RSC         = "resource/player/animation_run_left.png";
	public static final String PLAYER_RUN_LEFT_UP_RSC      = "resource/player/animation_run_left_up.png";
	public static final String PLAYER_RUN_LEFT_DOWN_RSC    = "resource/player/animation_run_left_down.png";
	public static final String PLAYER_RUN_RIGHT_RSC        = "resource/player/animation_run_right.png";
	public static final String PLAYER_RUN_RIGHT_UP_RSC     = "resource/player/animation_run_right_up.png";
	public static final String PLAYER_RUN_RIGHT_DOWN_RSC   = "resource/player/animation_run_right_down.png";
	public static final String PLAYER_RUN_JUMP_LEFT_RSC    = "resource/player/animation_jump_left.png";
	public static final String PLAYER_RUN_JUMP_RIGHT_RSC   = "resource/player/animation_jump_right.png";
	public static final String PLAYER_FIRE_LEFT_RSC        = "resource/player/animation_fire_left.png";
	public static final String PLAYER_FIRE_RIGHT_RSC       = "resource/player/animation_fire_right.png";
	public static final String PLAYER_FIRE_LEFT_UP_RSC     = "resource/player/animation_fire_left_up.png";
	public static final String PLAYER_FIRE_RIGHT_UP_RSC    = "resource/player/animation_fire_right_up.png";
	public static final String PLAYER_FIRE_RUN_LEFT_RSC    = "resource/player/animation_fire_run_left.png";
	public static final String PLAYER_FIRE_RUN_RIGHT_RSC   = "resource/player/animation_fire_run_right.png";

    public static final String PLAYER2_PRONE_LEFT_RSC       = "resource/player2/animation_prone_left.png";
    public static final String PLAYER2_PRONE_RIGHT_RSC      = "resource/player2/animation_prone_right.png";
    public static final String PLAYER2_RUN_LEFT_RSC         = "resource/player2/animation_run_left.png";
    public static final String PLAYER2_RUN_LEFT_UP_RSC      = "resource/player2/animation_run_left_up.png";
    public static final String PLAYER2_RUN_LEFT_DOWN_RSC    = "resource/player2/animation_run_left_down.png";
    public static final String PLAYER2_RUN_RIGHT_RSC        = "resource/player2/animation_run_right.png";
    public static final String PLAYER2_RUN_RIGHT_UP_RSC     = "resource/player2/animation_run_right_up.png";
    public static final String PLAYER2_RUN_RIGHT_DOWN_RSC   = "resource/player2/animation_run_right_down.png";
    public static final String PLAYER2_RUN_JUMP_LEFT_RSC    = "resource/player2/animation_jump_left.png";
    public static final String PLAYER2_RUN_JUMP_RIGHT_RSC   = "resource/player2/animation_jump_right.png";
    public static final String PLAYER2_FIRE_LEFT_RSC        = "resource/player2/animation_fire_left.png";
    public static final String PLAYER2_FIRE_RIGHT_RSC       = "resource/player2/animation_fire_right.png";
    public static final String PLAYER2_FIRE_LEFT_UP_RSC     = "resource/player2/animation_fire_left_up.png";
    public static final String PLAYER2_FIRE_RIGHT_UP_RSC    = "resource/player2/animation_fire_right_up.png";
    public static final String PLAYER2_FIRE_RUN_LEFT_RSC    = "resource/player2/animation_fire_run_left.png";
    public static final String PLAYER2_FIRE_RUN_RIGHT_RSC   = "resource/player2/animation_fire_run_right.png";

    /*Player swimming animations*/
    public static final String PLAYER_WATER_RIGHT     		= "resource/player/animation_water_right.png";
    public static final String PLAYER_WATER_LEFT   			= "resource/player/animation_water_left.png";
    public static final String PLAYER_WATER_DOWN  			= "resource/player/animation_water_down.png";
    public static final String PLAYER_WATER_GUN_UP_RIGHT    = "resource/player/animation_water_gun_up_rightfacing.png";
    public static final String PLAYER_WATER_GUN_UP_LEFT     = "resource/player/animation_water_gun_up_leftfacing.png";
    public static final String PLAYER_WATER_GUN_RIGHTUP     = "resource/player/animation_water_gun_rightup.png";
    public static final String PLAYER_WATER_GUN_LEFTUP      = "resource/player/animation_water_gun_leftup.png";
    public static final String PLAYER_WATER_FIRE_RIGHT      = "resource/player/animation_water_fire_right.png";
    public static final String PLAYER_WATER_FIRE_LEFT       = "resource/player/animation_water_fire_left.png";


	public static final String WORLD_BLOCK_BOSS                          = "resource/block/boss.png";
	public static final String WORLD_BLOCK_GOLD_RSC      				 = "resource/block/gold_brick.png";
	public static final String WORLD_BLOCK_GRASS_PLATFORM_LEFT_RSC       = "resource/block/grass_platform_left.png";
	public static final String WORLD_BLOCK_GRASS_PLATFORM_RIGHT_RSC      = "resource/block/grass_platform_right.png";

	public static final String WORLD_BLOCK_BRIDGE_PLATFORM_LEFT_RSC       = "resource/block/bridge_platform_left.png";
	public static final String WORLD_BLOCK_BRIDGE_PLATFORM_RIGHT_RSC      = "resource/block/bridge_platform_right.png";
	public static final String WORLD_BLOCK_BRIDGE_PLATFORM_SUPPORT_LEFT_RSC       = "resource/block/bridge_platform_support_left.png";
	public static final String WORLD_BLOCK_BRIDGE_PLATFORM_SUPPORT_RIGHT_RSC      = "resource/block/bridge_platform_support_right.png";
	public static final String WORLD_BLOCK_BRIDGE_PLATFORM_SUPPORT_CENTER_RSC     = "resource/block/bridge_platform_support_center.png";

	public static final String WORLD_BLOCK_CLIFF_TL                        = "resource/block/cliff_texture_tl.png";
	public static final String WORLD_BLOCK_CLIFF_TR                        = "resource/block/cliff_texture_tr.png";
	public static final String WORLD_BLOCK_CLIFF_BL                        = "resource/block/cliff_texture_bl.png";
	public static final String WORLD_BLOCK_CLIFF_BR                        = "resource/block/cliff_texture_br.png";
	public static final String WORLD_BLOCK_CLIFF_TBL                       = "resource/block/cliff_texture_tbl.png";
	public static final String WORLD_BLOCK_CLIFF_TBR                       = "resource/block/cliff_texture_tbr.png";

	public static final String WORLD_BLOCK_VINE_BL                         = "resource/block/vine_texture_bl.png";
	public static final String WORLD_BLOCK_VINE_BR                         = "resource/block/vine_texture_br.png";
	public static final String WORLD_BLOCK_VINE_CL                         = "resource/block/vine_texture_cl.png";
	public static final String WORLD_BLOCK_VINE_CR                         = "resource/block/vine_texture_cr.png";
	public static final String WORLD_BLOCK_VINE_TL                         = "resource/block/vine_texture_tl.png";
	public static final String WORLD_BLOCK_VINE_TR                         = "resource/block/vine_texture_tr.png";
	public static final String WORLD_BLOCK_VINE_CCL                        = "resource/block/vine_texture_ccl.png";
	public static final String WORLD_BLOCK_VINE_CCR                        = "resource/block/vine_texture_ccr.png";
	public static final String WORLD_BLOCK_VINE_CCS                        = "resource/block/vine_texture_ccs.png";

	public static final String WORLD_BLOCK_JUNGLE_TL                       = "resource/block/jungle_texture_tl.png";
	public static final String WORLD_BLOCK_JUNGLE_TR                       = "resource/block/jungle_texture_tr.png";
	public static final String WORLD_BLOCK_JUNGLE_BL                       = "resource/block/jungle_texture_bl.png";
	public static final String WORLD_BLOCK_JUNGLE_BR                       = "resource/block/jungle_texture_br.png";
	public static final String WORLD_BLOCK_JUNGLE_CT                       = "resource/block/jungle_texture_ct.png";
	public static final String WORLD_BLOCK_JUNGLE_CB                       = "resource/block/jungle_texture_cb.png";
    public static final String WORLD_BLOCK_WATER                      	   = "resource/block/water.png";
    public static final String WORLD_BLOCK_WATER_TOP_ANIMATION             = "resource/block/water_top_animation.png";

	public static final String WORLD_BLOCK_WATERFALL_ANIMATION             = "resource/block/waterfall_animation.png";
	public static final String WORLD_BLOCK_WATERFALL_ANIMATION_TOP         = "resource/block/waterfall_animation_top.png";


	//Bullet Images
	public static final String BULLET_REGULAR_RSC                          = "resource/bullet/regular.png";
	public static final String BULLET_UPGRADE_RSC                          = "resource/bullet/upgrade.png";

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
	public static HashMap<String, Image      > blockTextureHashMap   = new HashMap<>();

    /*
     * 	Hash to store all block Animations
     *
     * */
	public static HashMap<String, SpriteSheet> blockSpriteSheetHashMap = new HashMap<>();


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
	public static Image getBlockTexture( String key ) { return blockTextureHashMap.get( key ); }

	public static SpriteSheet getBlockSpriteSheet( String key ) { return blockSpriteSheetHashMap.get( key ); }

	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		addState(new StartUpState());
		addState(new GameStateClient());
		addState(new GameStateServer());
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
			app.setDisplayMode(800, 600, false );
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
		ResourceManager.loadImage( PLAYER_RUN_LEFT_RSC       );
		ResourceManager.loadImage( PLAYER_RUN_LEFT_UP_RSC    );
		ResourceManager.loadImage( PLAYER_RUN_LEFT_DOWN_RSC  );
		ResourceManager.loadImage( PLAYER_RUN_RIGHT_RSC      );
		ResourceManager.loadImage( PLAYER_RUN_RIGHT_UP_RSC   );
		ResourceManager.loadImage( PLAYER_RUN_RIGHT_DOWN_RSC );
		ResourceManager.loadImage( PLAYER_RUN_JUMP_LEFT_RSC  );
		ResourceManager.loadImage( PLAYER_FIRE_LEFT_RSC      );
		ResourceManager.loadImage( PLAYER_FIRE_RIGHT_RSC     );
		ResourceManager.loadImage( PLAYER_FIRE_LEFT_UP_RSC   );
		ResourceManager.loadImage( PLAYER_FIRE_RIGHT_UP_RSC  );
		ResourceManager.loadImage( PLAYER_FIRE_RUN_LEFT_RSC  );
		ResourceManager.loadImage( PLAYER_FIRE_RUN_RIGHT_RSC  );

        ResourceManager.loadImage( PLAYER2_PRONE_LEFT_RSC     );
        ResourceManager.loadImage( PLAYER2_PRONE_RIGHT_RSC    );
        ResourceManager.loadImage( PLAYER2_RUN_LEFT_RSC       );
        ResourceManager.loadImage( PLAYER2_RUN_LEFT_UP_RSC    );
        ResourceManager.loadImage( PLAYER2_RUN_LEFT_DOWN_RSC  );
        ResourceManager.loadImage( PLAYER2_RUN_RIGHT_RSC      );
        ResourceManager.loadImage( PLAYER2_RUN_RIGHT_UP_RSC   );
        ResourceManager.loadImage( PLAYER2_RUN_RIGHT_DOWN_RSC );
        ResourceManager.loadImage( PLAYER2_RUN_JUMP_LEFT_RSC  );
        ResourceManager.loadImage( PLAYER2_FIRE_LEFT_RSC      );
        ResourceManager.loadImage( PLAYER2_FIRE_RIGHT_RSC     );
        ResourceManager.loadImage( PLAYER2_FIRE_LEFT_UP_RSC   );
        ResourceManager.loadImage( PLAYER2_FIRE_RIGHT_UP_RSC  );
        ResourceManager.loadImage( PLAYER2_FIRE_RUN_LEFT_RSC  );
        ResourceManager.loadImage( PLAYER2_FIRE_RUN_RIGHT_RSC  );

		//Bullet Images
		ResourceManager.loadImage( BULLET_REGULAR_RSC  );
		ResourceManager.loadImage( BULLET_UPGRADE_RSC  );

        /*Player swimming animations*/
        ResourceManager.loadImage( PLAYER_WATER_RIGHT        );
        ResourceManager.loadImage( PLAYER_WATER_LEFT         );
        ResourceManager.loadImage( PLAYER_WATER_DOWN         );
        ResourceManager.loadImage( PLAYER_WATER_GUN_UP_RIGHT );
        ResourceManager.loadImage( PLAYER_WATER_GUN_UP_LEFT  );
        ResourceManager.loadImage( PLAYER_WATER_GUN_RIGHTUP  );
        ResourceManager.loadImage( PLAYER_WATER_GUN_LEFTUP   );
        ResourceManager.loadImage( PLAYER_WATER_FIRE_RIGHT   );
        ResourceManager.loadImage( PLAYER_WATER_FIRE_LEFT    );

		spriteSheetHashMap.put( "PLAYER_PRONE_LEFT_SS"  ,
				new SpriteSheet( ResourceManager.getImage( PLAYER_PRONE_LEFT_RSC     ), 32 , 16 ) );
		spriteSheetHashMap.put( "PLAYER_PRONE_RIGHT_SS"  ,
				new SpriteSheet( ResourceManager.getImage( PLAYER_PRONE_RIGHT_RSC    ), 32 , 16 ) );
		spriteSheetHashMap.put( "PLAYER_RUN_LEFT_SS"  ,
				new SpriteSheet( ResourceManager.getImage( PLAYER_RUN_LEFT_RSC       ), 20 , 35 ) );
		spriteSheetHashMap.put( "PLAYER_RUN_LEFT_UP_SS"  ,
				new SpriteSheet( ResourceManager.getImage( PLAYER_RUN_LEFT_UP_RSC    ), 21 , 35 ) );
		spriteSheetHashMap.put( "PLAYER_RUN_LEFT_DOWN_SS"  ,
				new SpriteSheet( ResourceManager.getImage( PLAYER_RUN_LEFT_DOWN_RSC  ), 21 , 35 ) );
		spriteSheetHashMap.put( "PLAYER_RUN_RIGHT_SS" ,
				new SpriteSheet( ResourceManager.getImage( PLAYER_RUN_RIGHT_RSC      ), 20 , 35 ) );
		spriteSheetHashMap.put( "PLAYER_RUN_RIGHT_UP_SS" ,
				new SpriteSheet( ResourceManager.getImage( PLAYER_RUN_RIGHT_UP_RSC   ), 21 , 35 ) );
		spriteSheetHashMap.put( "PLAYER_RUN_RIGHT_DOWN_SS" ,
				new SpriteSheet( ResourceManager.getImage( PLAYER_RUN_RIGHT_DOWN_RSC ), 21 , 35 ) );
		spriteSheetHashMap.put( "PLAYER_JUMP_LEFT_SS" ,
				new SpriteSheet( ResourceManager.getImage( PLAYER_RUN_JUMP_LEFT_RSC  ), 20 , 20 ) );
		spriteSheetHashMap.put( "PLAYER_JUMP_RIGHT_SS",
				new SpriteSheet( ResourceManager.getImage( PLAYER_RUN_JUMP_RIGHT_RSC ), 20 , 20 ) );
		spriteSheetHashMap.put( "PLAYER_FIRE_LEFT_SS" ,
				new SpriteSheet( ResourceManager.getImage( PLAYER_FIRE_LEFT_RSC  ), 23 , 35 ) );
		spriteSheetHashMap.put( "PLAYER_FIRE_RIGHT_SS",
				new SpriteSheet( ResourceManager.getImage( PLAYER_FIRE_RIGHT_RSC ), 23 , 35 ) );
		spriteSheetHashMap.put( "PLAYER_FIRE_LEFT_UP_SS" ,
				new SpriteSheet( ResourceManager.getImage( PLAYER_FIRE_LEFT_UP_RSC  ), 15 , 45 ) );
		spriteSheetHashMap.put( "PLAYER_FIRE_RIGHT_UP_SS",
				new SpriteSheet( ResourceManager.getImage( PLAYER_FIRE_RIGHT_UP_RSC ), 15 , 45 ) );
		spriteSheetHashMap.put( "PLAYER_FIRE_RUN_LEFT_STRAIGHT_SS" ,
				new SpriteSheet( ResourceManager.getImage( PLAYER_FIRE_RUN_LEFT_RSC  ), 25 , 35 ) );
		spriteSheetHashMap.put( "PLAYER_FIRE_RUN_RIGHT_STRAIGHT_SS",
				new SpriteSheet( ResourceManager.getImage( PLAYER_FIRE_RUN_RIGHT_RSC ), 25 , 35 ) );

        spriteSheetHashMap.put( "PLAYER2_PRONE_LEFT_SS"  ,
                new SpriteSheet( ResourceManager.getImage( PLAYER2_PRONE_LEFT_RSC     ), 32 , 16 ) );
        spriteSheetHashMap.put( "PLAYER2_PRONE_RIGHT_SS"  ,
                new SpriteSheet( ResourceManager.getImage( PLAYER2_PRONE_RIGHT_RSC    ), 32 , 16 ) );
        spriteSheetHashMap.put( "PLAYER2_RUN_LEFT_SS"  ,
                new SpriteSheet( ResourceManager.getImage( PLAYER2_RUN_LEFT_RSC       ), 20 , 35 ) );
        spriteSheetHashMap.put( "PLAYER2_RUN_LEFT_UP_SS"  ,
                new SpriteSheet( ResourceManager.getImage( PLAYER2_RUN_LEFT_UP_RSC    ), 21 , 35 ) );
        spriteSheetHashMap.put( "PLAYER2_RUN_LEFT_DOWN_SS"  ,
                new SpriteSheet( ResourceManager.getImage( PLAYER2_RUN_LEFT_DOWN_RSC  ), 21 , 35 ) );
        spriteSheetHashMap.put( "PLAYER2_RUN_RIGHT_SS" ,
                new SpriteSheet( ResourceManager.getImage( PLAYER2_RUN_RIGHT_RSC      ), 20 , 35 ) );
        spriteSheetHashMap.put( "PLAYER2_RUN_RIGHT_UP_SS" ,
                new SpriteSheet( ResourceManager.getImage( PLAYER2_RUN_RIGHT_UP_RSC   ), 21 , 35 ) );
        spriteSheetHashMap.put( "PLAYER2_RUN_RIGHT_DOWN_SS" ,
                new SpriteSheet( ResourceManager.getImage( PLAYER2_RUN_RIGHT_DOWN_RSC ), 21 , 35 ) );
        spriteSheetHashMap.put( "PLAYER2_JUMP_LEFT_SS" ,
                new SpriteSheet( ResourceManager.getImage( PLAYER2_RUN_JUMP_LEFT_RSC  ), 20 , 20 ) );
        spriteSheetHashMap.put( "PLAYER2_JUMP_RIGHT_SS",
                new SpriteSheet( ResourceManager.getImage( PLAYER2_RUN_JUMP_RIGHT_RSC ), 20 , 20 ) );
        spriteSheetHashMap.put( "PLAYER2_FIRE_LEFT_SS" ,
                new SpriteSheet( ResourceManager.getImage( PLAYER2_FIRE_LEFT_RSC  ), 23 , 35 ) );
        spriteSheetHashMap.put( "PLAYER2_FIRE_RIGHT_SS",
                new SpriteSheet( ResourceManager.getImage( PLAYER2_FIRE_RIGHT_RSC ), 23 , 35 ) );
        spriteSheetHashMap.put( "PLAYER2_FIRE_LEFT_UP_SS" ,
                new SpriteSheet( ResourceManager.getImage( PLAYER2_FIRE_LEFT_UP_RSC  ), 15 , 45 ) );
        spriteSheetHashMap.put( "PLAYER2_FIRE_RIGHT_UP_SS",
                new SpriteSheet( ResourceManager.getImage( PLAYER2_FIRE_RIGHT_UP_RSC ), 15 , 45 ) );
        spriteSheetHashMap.put( "PLAYER2_FIRE_RUN_LEFT_STRAIGHT_SS" ,
                new SpriteSheet( ResourceManager.getImage( PLAYER2_FIRE_RUN_LEFT_RSC  ), 25 , 35 ) );
        spriteSheetHashMap.put( "PLAYER2_FIRE_RUN_RIGHT_STRAIGHT_SS",
                new SpriteSheet( ResourceManager.getImage( PLAYER2_FIRE_RUN_RIGHT_RSC ), 25 , 35 ) );


        /*Player swimming animations*/
        spriteSheetHashMap.put( "PLAYER_WATER_RIGHT_SS"  ,
                new SpriteSheet( ResourceManager.getImage( PLAYER_WATER_RIGHT     ), 16 , 16 ) );
        spriteSheetHashMap.put( "PLAYER_WATER_DOWN_SS"  ,
                new SpriteSheet( ResourceManager.getImage( PLAYER_WATER_DOWN    ), 16 , 16 ) );
        spriteSheetHashMap.put( "PLAYER_WATER_LEFT_SS"  ,
                new SpriteSheet( ResourceManager.getImage( PLAYER_WATER_LEFT    ), 16 , 16 ) );
        spriteSheetHashMap.put( "PLAYER_WATER_GUN_UP_RIGHT_SS"  ,
                new SpriteSheet( ResourceManager.getImage( PLAYER_WATER_GUN_UP_RIGHT    ), 17  , 27 ) );
        spriteSheetHashMap.put( "PLAYER_WATER_GUN_UP_LEFT_SS"  ,
                new SpriteSheet( ResourceManager.getImage( PLAYER_WATER_GUN_UP_LEFT   ), 17 , 27 ) );
        spriteSheetHashMap.put( "PLAYER_WATER_GUN_RIGHTUP_SS"  ,
                new SpriteSheet( ResourceManager.getImage( PLAYER_WATER_GUN_RIGHTUP    ), 19  , 17 ) );
        spriteSheetHashMap.put( "PLAYER_WATER_GUN_LEFTUP_SS"  ,
                new SpriteSheet( ResourceManager.getImage( PLAYER_WATER_GUN_LEFTUP   ), 19 , 17 ) );
        spriteSheetHashMap.put( "PLAYER_WATER_FIRE_RIGHT_SS"  ,
                new SpriteSheet( ResourceManager.getImage( PLAYER_WATER_FIRE_RIGHT    ), 19  , 17 ) );
        spriteSheetHashMap.put( "PLAYER_WATER_FIRE_LEFT_SS"  ,
                new SpriteSheet( ResourceManager.getImage( PLAYER_WATER_FIRE_LEFT   ), 19 , 17 ) );

		// Bullet Assets
		imageAssetHashMap.put( "BULLET_REGULAR", ResourceManager.getImage(BULLET_REGULAR_RSC) );
		imageAssetHashMap.put( "BULLET_UPGRADE", ResourceManager.getImage(BULLET_UPGRADE_RSC) );
	}

	/*
	 *
	 */
	private void loadWorldAssets()
	{
		ResourceManager.loadImage(WORLD_BLOCK_BOSS);

		ResourceManager.loadImage(WORLD_BLOCK_GOLD_RSC);
		ResourceManager.loadImage(WORLD_BLOCK_GRASS_PLATFORM_LEFT_RSC);
		ResourceManager.loadImage(WORLD_BLOCK_GRASS_PLATFORM_RIGHT_RSC);

		ResourceManager.loadImage(WORLD_BLOCK_BRIDGE_PLATFORM_LEFT_RSC);
		ResourceManager.loadImage(WORLD_BLOCK_BRIDGE_PLATFORM_RIGHT_RSC);

		ResourceManager.loadImage(WORLD_BLOCK_BRIDGE_PLATFORM_SUPPORT_LEFT_RSC);
		ResourceManager.loadImage(WORLD_BLOCK_BRIDGE_PLATFORM_SUPPORT_RIGHT_RSC);
		ResourceManager.loadImage(WORLD_BLOCK_BRIDGE_PLATFORM_SUPPORT_CENTER_RSC);

		ResourceManager.loadImage(WORLD_BLOCK_CLIFF_TL);
		ResourceManager.loadImage(WORLD_BLOCK_CLIFF_TR);
		ResourceManager.loadImage(WORLD_BLOCK_CLIFF_BL);
		ResourceManager.loadImage(WORLD_BLOCK_CLIFF_BR);
		ResourceManager.loadImage(WORLD_BLOCK_CLIFF_TBL);
		ResourceManager.loadImage(WORLD_BLOCK_CLIFF_TBR);

		ResourceManager.loadImage(WORLD_BLOCK_JUNGLE_TL);
		ResourceManager.loadImage(WORLD_BLOCK_JUNGLE_TR);
		ResourceManager.loadImage(WORLD_BLOCK_JUNGLE_BL);
		ResourceManager.loadImage(WORLD_BLOCK_JUNGLE_BR);
		ResourceManager.loadImage(WORLD_BLOCK_JUNGLE_CB);
		ResourceManager.loadImage(WORLD_BLOCK_JUNGLE_CT);

		ResourceManager.loadImage(WORLD_BLOCK_VINE_BL);
		ResourceManager.loadImage(WORLD_BLOCK_VINE_BR);
		ResourceManager.loadImage(WORLD_BLOCK_VINE_CL);
		ResourceManager.loadImage(WORLD_BLOCK_VINE_CR);
		ResourceManager.loadImage(WORLD_BLOCK_VINE_TL);
		ResourceManager.loadImage(WORLD_BLOCK_VINE_TR);
		ResourceManager.loadImage(WORLD_BLOCK_VINE_CCL);
		ResourceManager.loadImage(WORLD_BLOCK_VINE_CCR);
		ResourceManager.loadImage(WORLD_BLOCK_VINE_CCS);
		ResourceManager.loadImage(WORLD_BLOCK_VINE_CCR);
        ResourceManager.loadImage(WORLD_BLOCK_WATER);
        ResourceManager.loadImage(WORLD_BLOCK_WATER_TOP_ANIMATION);

		ResourceManager.loadImage(WORLD_BLOCK_WATERFALL_ANIMATION);
		ResourceManager.loadImage(WORLD_BLOCK_WATERFALL_ANIMATION_TOP);

		blockTextureHashMap.put( "BOSS"                , ResourceManager.getImage(WORLD_BLOCK_BOSS) );

		blockTextureHashMap.put( "GOLD_BRICK"          , ResourceManager.getImage(WORLD_BLOCK_GOLD_RSC) );
		blockTextureHashMap.put( "GRASS_PLATFORM_LEFT" , ResourceManager.getImage(WORLD_BLOCK_GRASS_PLATFORM_LEFT_RSC) );
		blockTextureHashMap.put( "GRASS_PLATFORM_RIGHT", ResourceManager.getImage(WORLD_BLOCK_GRASS_PLATFORM_RIGHT_RSC) );

		blockTextureHashMap.put( "BRIDGE_PLATFORM_LEFT" , ResourceManager.getImage(WORLD_BLOCK_BRIDGE_PLATFORM_LEFT_RSC) );
		blockTextureHashMap.put( "BRIDGE_PLATFORM_RIGHT", ResourceManager.getImage(WORLD_BLOCK_BRIDGE_PLATFORM_RIGHT_RSC) );
		blockTextureHashMap.put( "BRIDGE_PLATFORM_SUPPORT_LEFT" , ResourceManager.getImage(WORLD_BLOCK_BRIDGE_PLATFORM_SUPPORT_LEFT_RSC) );
		blockTextureHashMap.put( "BRIDGE_PLATFORM_SUPPORT_RIGHT", ResourceManager.getImage(WORLD_BLOCK_BRIDGE_PLATFORM_SUPPORT_RIGHT_RSC) );
		blockTextureHashMap.put( "BRIDGE_PLATFORM_SUPPORT_CENTER", ResourceManager.getImage(WORLD_BLOCK_BRIDGE_PLATFORM_SUPPORT_CENTER_RSC) );

		blockTextureHashMap.put( "CLIFF_TL"  , ResourceManager.getImage(WORLD_BLOCK_CLIFF_TL) );
		blockTextureHashMap.put( "CLIFF_TR"  , ResourceManager.getImage(WORLD_BLOCK_CLIFF_TR) );
		blockTextureHashMap.put( "CLIFF_BL"  , ResourceManager.getImage(WORLD_BLOCK_CLIFF_BL) );
		blockTextureHashMap.put( "CLIFF_BR"  , ResourceManager.getImage(WORLD_BLOCK_CLIFF_BR) );
		blockTextureHashMap.put( "CLIFF_TBL" , ResourceManager.getImage(WORLD_BLOCK_CLIFF_TBL) );
		blockTextureHashMap.put( "CLIFF_TBR" , ResourceManager.getImage(WORLD_BLOCK_CLIFF_TBR) );

		blockTextureHashMap.put( "JUNGLE_TL" , ResourceManager.getImage(WORLD_BLOCK_JUNGLE_TL) );
		blockTextureHashMap.put( "JUNGLE_TR" , ResourceManager.getImage(WORLD_BLOCK_JUNGLE_TR) );
		blockTextureHashMap.put( "JUNGLE_BL" , ResourceManager.getImage(WORLD_BLOCK_JUNGLE_BL) );
		blockTextureHashMap.put( "JUNGLE_BR" , ResourceManager.getImage(WORLD_BLOCK_JUNGLE_BR) );
		blockTextureHashMap.put( "JUNGLE_CB" , ResourceManager.getImage(WORLD_BLOCK_JUNGLE_CB) );
		blockTextureHashMap.put( "JUNGLE_CT" , ResourceManager.getImage(WORLD_BLOCK_JUNGLE_CT) );

		blockTextureHashMap.put( "VINE_TL"  , ResourceManager.getImage(WORLD_BLOCK_VINE_BL) );
		blockTextureHashMap.put( "VINE_TR"  , ResourceManager.getImage(WORLD_BLOCK_VINE_BR) );
		blockTextureHashMap.put( "VINE_CL"  , ResourceManager.getImage(WORLD_BLOCK_VINE_CL) );
		blockTextureHashMap.put( "VINE_CR"  , ResourceManager.getImage(WORLD_BLOCK_VINE_CR) );
		blockTextureHashMap.put( "VINE_BL"  , ResourceManager.getImage(WORLD_BLOCK_VINE_TL) );
		blockTextureHashMap.put( "VINE_BR"  , ResourceManager.getImage(WORLD_BLOCK_VINE_TR) );
		blockTextureHashMap.put( "VINE_CCL" , ResourceManager.getImage(WORLD_BLOCK_VINE_CCL) );
		blockTextureHashMap.put( "VINE_CCR" , ResourceManager.getImage(WORLD_BLOCK_VINE_CCR) );
		blockTextureHashMap.put( "VINE_CCS" , ResourceManager.getImage(WORLD_BLOCK_VINE_CCS) );

        blockTextureHashMap.put( "WATER"    , ResourceManager.getImage(WORLD_BLOCK_WATER) );

        blockSpriteSheetHashMap.put( "WATER_TOP_ANIMATION", new SpriteSheet( ResourceManager.getImage( WORLD_BLOCK_WATER_TOP_ANIMATION ), 16 , 16 ) );
		blockSpriteSheetHashMap.put( "WATERFALL_ANIMATION"    , new SpriteSheet( ResourceManager.getImage( WORLD_BLOCK_WATERFALL_ANIMATION )    , 16 , 16 ) );
		blockSpriteSheetHashMap.put( "WATERFALL_ANIMATION_TOP", new SpriteSheet( ResourceManager.getImage( WORLD_BLOCK_WATERFALL_ANIMATION_TOP ), 16 , 16 ) );
	}
}
