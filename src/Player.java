import jig.Entity;
import jig.Vector;
import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;

public class Player extends Entity {
    private static float DEFAULT_PLAYER_VELOCITY_X = 0.1f;
    private static float DEFAULT_PLAYER_VELOCITY_Y = 0.1f;

    private PlayerState                     playerState;
    private PlayerMovementDirection         playerMovementDirection;    //the direction the player is facing
    private PlayerVerticalFacingDirection   playerVerticalFacingDirection;

    private Vector playerVelocity;
    private Animation   runningLeft,
                        runningLeftGunUp,
                        runningLeftGunDown,
                        runningRight,
                        runningRightGunUp,
                        runningRightGunDown;


    public Player(final float x, final float y) {
        super(x, y);
        playerVelocity = new Vector( 0, 0 );

        addImageWithBoundingBox(ContraGame.getSpriteSheetHashMap().get("PLAYER_RUN_RIGHT_SS").getSprite(5, 0));
        this.setScale(1.5f);

        runningRight = new Animation(ContraGame.getSpriteSheetHashMap().get("PLAYER_RUN_RIGHT_SS"), 0, 0, 5, 0, true, 150,
                true);

        runningRightGunUp = new Animation(ContraGame.getSpriteSheetHashMap().get("PLAYER_RUN_RIGHT_SS"), 6, 0, 8, 0, true, 150,
                true);

        runningRightGunDown = new Animation(ContraGame.getSpriteSheetHashMap().get("PLAYER_RUN_RIGHT_SS"), 9, 0, 11, 0, true, 150,
                true);

        runningLeft = new Animation(ContraGame.getSpriteSheetHashMap().get("PLAYER_RUN_LEFT_SS"), 0, 0, 8, 0, true, 150,
                true);

        runningLeftGunUp = new Animation(ContraGame.getSpriteSheetHashMap().get("PLAYER_RUN_LEFT_SS"), 7, 0, 11, 0, true, 150,
                true);

        runningLeftGunDown = new Animation(ContraGame.getSpriteSheetHashMap().get("PLAYER_RUN_LEFT_SS"), 7, 0, 11, 0, true, 150,
                true);

        runningRight.setLooping(true);
        runningRightGunDown.setLooping(true);
        runningRightGunUp.setLooping(true);
        runningLeft.setLooping(true);
        playerState             = PlayerState.IDLE;
        playerMovementDirection = PlayerMovementDirection.RIGHT;
        playerVerticalFacingDirection = PlayerVerticalFacingDirection.NONE;

    }

    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {

    }

    public void update(GameContainer gc, StateBasedGame sbg, int delta) {
        //go LEFT or RIGHT
        if (gc.getInput().isKeyDown(Input.KEY_A)) {
            playerState = PlayerState.MOVING;
            playerMovementDirection = PlayerMovementDirection.LEFT;
        }
        else
        if (gc.getInput().isKeyDown(Input.KEY_D)) {
            playerState = PlayerState.MOVING;
            playerMovementDirection = PlayerMovementDirection.RIGHT;
        } else {
            playerState = PlayerState.IDLE;
        }

        //UP or DOWN
        if (gc.getInput().isKeyDown(Input.KEY_W)) {
            playerVerticalFacingDirection = PlayerVerticalFacingDirection.UP;
        }
        else if (gc.getInput().isKeyDown(Input.KEY_S)){
            playerVerticalFacingDirection = PlayerVerticalFacingDirection.DOWN;
        }
        else {
            playerVerticalFacingDirection = PlayerVerticalFacingDirection.NONE;
        }

        //jumping or prone
        if ( gc.getInput().isKeyDown( Input.KEY_S ) && playerState == PlayerState.IDLE) {
            playerState = PlayerState.PRONE;
        }
        else if ( gc.getInput().isKeyDown( Input.KEY_SPACE ) ) {
            playerState = PlayerState.JUMPING;
        }

        switch ( playerState )
        {
            case IDLE:
                this.setPlayerVelocity(new Vector(0.0f, 0.0f));

                this.removeAllImages();
                if (playerMovementDirection == PlayerMovementDirection.RIGHT) {
                    addImage(ContraGame.getSpriteSheetHashMap().get("PLAYER_RUN_RIGHT_SS").getSprite(5, 0));
                } else {
                    addImage(ContraGame.getSpriteSheetHashMap().get("PLAYER_RUN_RIGHT_SS").getSprite(5, 0).getFlippedCopy(true, false));
                }
                break;

            case MOVING:
                if (playerMovementDirection == PlayerMovementDirection.LEFT) {
                    this.setPlayerVelocity(new Vector(-DEFAULT_PLAYER_VELOCITY_X, 0.0f));
                    this.removeAllImages();
                    if (playerVerticalFacingDirection == PlayerVerticalFacingDirection.UP){
                        addAnimation(runningLeftGunUp);
                    }
                    else if (playerVerticalFacingDirection == PlayerVerticalFacingDirection.DOWN){
                        addAnimation(runningLeftGunDown);
                    }
                    else{
                        addAnimation(runningLeft);
                    }

                }

                if (playerMovementDirection == PlayerMovementDirection.RIGHT) {
                    this.setPlayerVelocity(new Vector(DEFAULT_PLAYER_VELOCITY_X, 0.0f));
                    this.removeAllImages();
                    if (playerVerticalFacingDirection == PlayerVerticalFacingDirection.UP){
                        addAnimation(runningRightGunUp);
                    }
                    else if (playerVerticalFacingDirection == PlayerVerticalFacingDirection.DOWN){
                        addAnimation(runningRightGunDown);
                    }
                    else{
                        addAnimation(runningRight);
                    }
                }
                break;


            case JUMPING:
                break;

            case PRONE:
                break;
        }
        //player state logic
        if (playerState == PlayerState.MOVING || playerState == PlayerState.IDLE) {

        }
        if( playerState == PlayerState.JUMPING ) {

        }




        this.setPosition( this.getX() + this.playerVelocity.getX()*delta,
                          this.getY() + this.playerVelocity.getY()*delta );

    }

    public Vector getPlayerVelocity() {
        return playerVelocity;
    }

    public void setPlayerVelocity(Vector playerVelocity) {
        this.playerVelocity = playerVelocity;
    }
}
