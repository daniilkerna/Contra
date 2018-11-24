import jig.Entity;
import jig.Vector;
import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;

import java.util.HashMap;

public class Player extends Entity {
    private static float DEFAULT_PLAYER_VELOCITY_X = 0.1f;
    private static float DEFAULT_PLAYER_VELOCITY_Y = 0.1f;

    private PlayerState               playerState;
    private PlayerMovement            playerMovement;
    private PlayerHorizontalDirection playerHorizontalDirection;
    private PlayerVerticalDirection   playerVerticalDirection;

    private Vector                     playerVelocity;
    private HashMap<String, Animation> playerAnimations;

    public Player( GameContainer gc, StateBasedGame sbg ) {

        super();
        ContraGame contraGame = (ContraGame)sbg;

        setScale(2.0f);
        setPosition( gc.getWidth()/2, gc.getHeight()/2 );

        addImageWithBoundingBox(contraGame.getSpriteSheet("PLAYER_RUN_RIGHT_SS").getSprite(1, 0));


        playerAnimations = new HashMap<>();

        playerAnimations.put( "PLAYER_PRONE_LEFT",
                new Animation(contraGame.getSpriteSheet("PLAYER_PRONE_LEFT_SS") , 0, 0, 0, 0, true, 150, true));

        playerAnimations.put( "PLAYER_PRONE_RIGHT",
                new Animation(contraGame.getSpriteSheet("PLAYER_PRONE_RIGHT_SS" ), 0,0, 0,0, true, 150, true ));


        playerAnimations.put( "PLAYER_RUN_LEFT",
                new Animation(contraGame.getSpriteSheet("PLAYER_RUN_LEFT_SS") , 0, 0, 4, 0, true, 150, true));

        playerAnimations.put( "PLAYER_RUN_LEFT_UP",
                new Animation(contraGame.getSpriteSheet("PLAYER_RUN_LEFT_UP_SS" ), 0,0, 2,0, true, 150, true ));

        playerAnimations.put( "PLAYER_RUN_LEFT_DOWN",
                new Animation(contraGame.getSpriteSheet("PLAYER_RUN_LEFT_DOWN_SS" ), 0,0, 2,0, true, 150, true ));


        playerAnimations.put( "PLAYER_RUN_RIGHT",
                new Animation(contraGame.getSpriteSheet("PLAYER_RUN_RIGHT_SS"), 0, 0, 4, 0, true, 150, true));

        playerAnimations.put( "PLAYER_RUN_RIGHT_UP",
                new Animation(contraGame.getSpriteSheet("PLAYER_RUN_RIGHT_UP_SS"), 0, 0, 2, 0, true, 150, true));

        playerAnimations.put( "PLAYER_RUN_RIGHT_DOWN",
                new Animation(contraGame.getSpriteSheet("PLAYER_RUN_RIGHT_DOWN_SS"), 0, 0, 2, 0, true, 150, true));


        playerAnimations.put( "PLAYER_JUMP_LEFT",
                new Animation(contraGame.getSpriteSheet("PLAYER_JUMP_LEFT_SS" ), 0,0, 3,0, true, 150, true ));

        playerAnimations.put( "PLAYER_JUMP_RIGHT",
                new Animation(contraGame.getSpriteSheet("PLAYER_JUMP_RIGHT_SS" ), 0,0, 3,0, true, 150, true ));

        playerAnimations.put( "PLAYER_FIRE_LEFT",
                new Animation(contraGame.getSpriteSheet("PLAYER_FIRE_LEFT_SS" ), 0,0, 1,0, true, 150, true ));

        playerAnimations.put( "PLAYER_FIRE_RIGHT",
                new Animation(contraGame.getSpriteSheet("PLAYER_FIRE_RIGHT_SS" ), 0,0, 1,0, true, 150, true ));

        playerAnimations.put( "PLAYER_FIRE_LEFT_UP",
                new Animation(contraGame.getSpriteSheet("PLAYER_FIRE_LEFT_UP_SS" ), 0,0, 1,0, true, 150, true ));

        playerAnimations.put( "PLAYER_FIRE_RIGHT_UP",
                new Animation(contraGame.getSpriteSheet("PLAYER_FIRE_RIGHT_UP_SS" ), 0,0, 1,0, true, 150, true ));

        playerVelocity            = new Vector( 0, 0 );
        playerState               = PlayerState.IDLE;
        /*
        *
        * */
        playerMovement            = PlayerMovement.NONE;
        playerVerticalDirection   = PlayerVerticalDirection.NONE;
        playerHorizontalDirection = PlayerHorizontalDirection.RIGHT;
    }

    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        ContraGame contraGame = (ContraGame)sbg;

    }

    public void update(GameContainer gc, StateBasedGame sbg, int delta) {
        getNewState( gc, sbg, delta );
        updateState( gc, sbg, delta );
        updatePosition( delta );
    }

    public void setAnimation( String key )
    {
        Animation a = playerAnimations.get( key );
        if( a ==  null ){
            System.out.println(String.format("[Player: Class] Animation %s, not found!", key ) );
            return;
        }
        removeAllImages();
        addAnimation( a );
    }

    public void setAnimationFrame( String key, int frame )
    {
        Animation a = playerAnimations.get( key );
        if( a ==  null ){
            System.out.println(String.format("[Player: Class] Animation %s, not found!", key ) );
            return;
        }
        removeAllImages();
        addImage( a.getImage( frame ) );
    }

    public void getNewState( GameContainer gc, StateBasedGame sbg, int delta ) {
        //LEFT or RIGHT
        if (gc.getInput().isKeyDown(Input.KEY_A)) {
            playerState               = PlayerState.MOVING;
            playerMovement            = PlayerMovement.LEFT;
            playerHorizontalDirection = PlayerHorizontalDirection.LEFT;
        }
        else
        if (gc.getInput().isKeyDown(Input.KEY_D)) {
            playerState               = PlayerState.MOVING;
            playerMovement            = PlayerMovement.RIGHT;
            playerHorizontalDirection = PlayerHorizontalDirection.RIGHT;
        } else {
            playerState               = PlayerState.IDLE;
            playerMovement            = PlayerMovement.NONE;
        }

        //UP or DOWN
        if (gc.getInput().isKeyDown(Input.KEY_W)) {
            playerVerticalDirection = PlayerVerticalDirection.UP;
        }
        else
        if (gc.getInput().isKeyDown(Input.KEY_S)){
            playerVerticalDirection = PlayerVerticalDirection.DOWN;
        }
        else {
            playerVerticalDirection = PlayerVerticalDirection.NONE;
        }

        if ( gc.getInput().isKeyDown( Input.KEY_SPACE ) ) {
            playerState = PlayerState.JUMPING;
        }
    }

    public void updateState( GameContainer gc, StateBasedGame sbg, int delta )
    {
        ContraGame contraGame = (ContraGame)sbg;
        switch ( playerState )
        {
            case IDLE:
                moveStop();
                removeAllImages();

                switch (playerVerticalDirection)
                {
                    case NONE:
                        switch (playerHorizontalDirection) {
                            case LEFT:
                                setAnimationFrame("PLAYER_FIRE_LEFT", 1);
                                break;

                            case RIGHT:
                                setAnimationFrame("PLAYER_FIRE_RIGHT", 1);
                                break;
                        }
                        break;
                    case UP:
                        switch (playerHorizontalDirection) {
                            case LEFT:
                                setAnimationFrame("PLAYER_FIRE_LEFT_UP", 1);
                                break;

                            case RIGHT:
                                setAnimationFrame("PLAYER_FIRE_RIGHT_UP", 1);
                                break;
                        }
                        break;
                    case DOWN:
                        switch (playerHorizontalDirection) {
                            case LEFT:
                                setAnimationFrame("PLAYER_PRONE_LEFT", 0);
                                break;

                            case RIGHT:
                                setAnimationFrame("PLAYER_PRONE_RIGHT", 0);
                                break;
                        }
                        break;
                }
                break;

            case MOVING:
                switch ( playerVerticalDirection )
                {
                    case NONE:
                        if( playerMovement == PlayerMovement.LEFT)
                            setAnimation("PLAYER_RUN_LEFT");
                        else
                            setAnimation("PLAYER_RUN_RIGHT");
                        break;
                    case UP:
                        if( playerMovement == PlayerMovement.LEFT)
                            setAnimation("PLAYER_RUN_LEFT_UP");
                        else
                            setAnimation("PLAYER_RUN_RIGHT_UP");
                        break;
                    case DOWN:
                        if( playerMovement == PlayerMovement.LEFT)
                            setAnimation("PLAYER_RUN_LEFT_DOWN");
                        else
                            setAnimation("PLAYER_RUN_RIGHT_DOWN");
                        break;
                }
                break;


            case JUMPING:
                if (playerHorizontalDirection == PlayerHorizontalDirection.LEFT) {
                    setAnimation("PLAYER_JUMP_LEFT");
                }

                if (playerHorizontalDirection == PlayerHorizontalDirection.RIGHT) {
                    setAnimation("PLAYER_JUMP_RIGHT");
                }
                break;

            case PRONE:
                moveStop();
                return;
        }


        switch ( playerMovement )
        {
            case RIGHT:
                moveRight(); break;
            case LEFT:
                moveLeft(); break;
            default:
                moveStop(); break;
        }
    }

    public void moveStop() {
        setPlayerVelocity(new Vector(0.0f, 0.0f));
    }
    public void moveLeft() {
        setPlayerVelocity(new Vector(-DEFAULT_PLAYER_VELOCITY_X, 0.0f));
    }

    public void moveRight() {
        setPlayerVelocity(new Vector( DEFAULT_PLAYER_VELOCITY_X, 0.0f));
    }

    public void updatePosition( int delta ) {
        setPosition( getX() + playerVelocity.getX()*delta,
                     getY() + playerVelocity.getY()*delta );
    }
    public Vector getPlayerVelocity() {
        return playerVelocity;
    }

    public void setPlayerVelocity(Vector playerVelocity) {
        this.playerVelocity = playerVelocity;
    }
}
