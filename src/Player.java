import jig.Collision;
import jig.Entity;
import jig.Vector;
import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class Player extends Entity {
    private static float DEFAULT_PLAYER_VELOCITY_X = 0.1f;
    private static float DEFAULT_PLAYER_VELOCITY_Y = 0.1f;
    private static float DEFAULT_PLAYER_HEIGHT     = 35.0f;

    private PlayerState               playerState;
    private PlayerMovement            playerMovement;
    private PlayerHorizontalDirection playerHorizontalDirection;
    private PlayerVerticalDirection   playerVerticalDirection;

    private Vector                      playerPosition,
                                        playerVelocity,
                                        playerAcceleration;
    private HashMap<String, Animation>  playerAnimations;

    private World                       playerWorld;

    public Player( World world ) {
        super();

        playerWorld = world;

        setScale(2.0f);
        setPosition( ContraGame.VIEWPORT.getWidth()/2, ContraGame.VIEWPORT.getHeight()/2 );

        addImageWithBoundingBox(ContraGame.getSpriteSheet("PLAYER_RUN_RIGHT_SS").getSprite(1, 0));

        playerAnimations = new HashMap<>();

        playerAnimations.put( "PLAYER_PRONE_LEFT",
                new Animation(ContraGame.getSpriteSheet("PLAYER_PRONE_LEFT_SS") , 0, 0, 0, 0, true, 150, true));

        playerAnimations.put( "PLAYER_PRONE_RIGHT",
                new Animation(ContraGame.getSpriteSheet("PLAYER_PRONE_RIGHT_SS" ), 0,0, 0,0, true, 150, true ));

        playerAnimations.put( "PLAYER_RUN_LEFT",
                new Animation(ContraGame.getSpriteSheet("PLAYER_RUN_LEFT_SS") , 0, 0, 4, 0, true, 150, true));

        playerAnimations.put( "PLAYER_RUN_LEFT_UP",
                new Animation(ContraGame.getSpriteSheet("PLAYER_RUN_LEFT_UP_SS" ), 0,0, 2,0, true, 150, true ));

        playerAnimations.put( "PLAYER_RUN_LEFT_DOWN",
                new Animation(ContraGame.getSpriteSheet("PLAYER_RUN_LEFT_DOWN_SS" ), 0,0, 2,0, true, 150, true ));

        playerAnimations.put( "PLAYER_RUN_RIGHT",
                new Animation(ContraGame.getSpriteSheet("PLAYER_RUN_RIGHT_SS"), 0, 0, 4, 0, true, 150, true));

        playerAnimations.put( "PLAYER_RUN_RIGHT_UP",
                new Animation(ContraGame.getSpriteSheet("PLAYER_RUN_RIGHT_UP_SS"), 0, 0, 2, 0, true, 150, true));

        playerAnimations.put( "PLAYER_RUN_RIGHT_DOWN",
                new Animation(ContraGame.getSpriteSheet("PLAYER_RUN_RIGHT_DOWN_SS"), 0, 0, 2, 0, true, 150, true));

        playerAnimations.put( "PLAYER_JUMP_LEFT",
                new Animation(ContraGame.getSpriteSheet("PLAYER_JUMP_LEFT_SS" ), 0,0, 3,0, true, 150, true ));

        playerAnimations.put( "PLAYER_JUMP_RIGHT",
                new Animation(ContraGame.getSpriteSheet("PLAYER_JUMP_RIGHT_SS" ), 0,0, 3,0, true, 150, true ));

        playerAnimations.put( "PLAYER_FIRE_LEFT",
                new Animation(ContraGame.getSpriteSheet("PLAYER_FIRE_LEFT_SS" ), 0,0, 1,0, true, 150, true ));

        playerAnimations.put( "PLAYER_FIRE_RIGHT",
                new Animation(ContraGame.getSpriteSheet("PLAYER_FIRE_RIGHT_SS" ), 0,0, 1,0, true, 150, true ));

        playerAnimations.put( "PLAYER_FIRE_LEFT_UP",
                new Animation(ContraGame.getSpriteSheet("PLAYER_FIRE_LEFT_UP_SS" ), 0,0, 1,0, true, 150, true ));

        playerAnimations.put( "PLAYER_FIRE_RIGHT_UP",
                new Animation(ContraGame.getSpriteSheet("PLAYER_FIRE_RIGHT_UP_SS" ), 0,0, 1,0, true, 150, true ));

        playerPosition            = new Vector( 0 ,0 );
        playerVelocity            = new Vector( 0, 0 );
        playerState               = PlayerState.IDLE;
        /*
        *
        * */
        playerMovement            = PlayerMovement.NONE;
        playerVerticalDirection   = PlayerVerticalDirection.NONE;
        playerHorizontalDirection = PlayerHorizontalDirection.RIGHT;
    }

    @Override
    public void render(final Graphics g)  {
        super.render(g);

        /* Draw Collision box */
        g.setColor( Color.green );
        g.drawRect( this.getX() - this.getCoarseGrainedWidth()/2, this.getY() - this.getCoarseGrainedHeight()/2,  this.getCoarseGrainedWidth(),  this.getCoarseGrainedHeight() );

        Vector blc = this.getBottomLeftCorner();
        Vector brc = this.getBottomRightCorner();

        g.drawOval( blc.getX() - 2.5f, blc.getY() - 2.5f, 5, 5 );
        g.drawOval( brc.getX() - 2.5f, brc.getY() - 2.5f, 5, 5 );

        /* Player Information */
        g.drawString( "Player Screen Position:(" + this.getX() + "," + this.getY() + ")", 400, 10 );
        g.drawString( "Player World Position:(" + this.playerPosition.getX() + "," + this.playerPosition.getY() + ")", 400, 25 );
        g.drawString( "Player World Velocity:(" + this.playerVelocity.getX() + "," + this.playerVelocity.getY() + ")", 400, 40 );

    }

    public void update(GameContainer gc, StateBasedGame sbg, int delta) {
        getNewState( gc, sbg, delta );
        updateState( gc, sbg, delta );
        updatePosition( delta );
    }

    public Vector getBottomLeftCorner()
    {
        return new Vector( this.getX() - this.getCoarseGrainedWidth()/2, this.getY() + this.getCoarseGrainedHeight()/2 );
    }

    public Vector getBottomRightCorner()
    {
        return new Vector( this.getX() + this.getCoarseGrainedWidth()/2, this.getY() + this.getCoarseGrainedHeight()/2 );
    }

    private ArrayList<WorldBlock> getPlayerLowerCornerBlocks()
    {
        ArrayList<WorldBlock> ll = new ArrayList<>();
        ll.add( playerWorld.getScreenBlock( this.getBottomLeftCorner() ) );
        ll.add( playerWorld.getScreenBlock( this.getBottomRightCorner() ) );
        return ll;
    }

    public void setAnimation( String key )
    {
        Animation a = playerAnimations.get( key );
        if( a ==  null ){
            System.out.println(String.format("[Player: Class] Animation %s, not found!", key ) );
            return;
        }
        this.setCoarseGrainedMaxX( a.getWidth() );
        this.setCoarseGrainedMinX( 0 );
        this.setCoarseGrainedMaxY( a.getHeight() );
        this.setCoarseGrainedMinY( 0 );

        removeAllImages();
        addAnimation( a, new Vector( 0, 0) );
    }

    public void setAnimationFrame( String key, int frame ) {
        Animation a = playerAnimations.get(key);
        if (a == null) {
            System.out.println(String.format("[Player: Class] Animation %s, not found!", key));
            return;
        }
        this.setCoarseGrainedMaxX(a.getWidth());
        this.setCoarseGrainedMinX(0);
        this.setCoarseGrainedMaxY(a.getHeight());
        this.setCoarseGrainedMinY(0);

        removeAllImages();
        addImage(a.getImage(frame), new Vector(0, 0));
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
                            case LEFT:  setAnimationFrame("PLAYER_FIRE_LEFT",  1); break;
                            case RIGHT: setAnimationFrame("PLAYER_FIRE_RIGHT", 1); break;
                        }
                        break;
                    case UP:
                        switch (playerHorizontalDirection) {
                            case LEFT:  setAnimationFrame("PLAYER_FIRE_LEFT_UP",  1); break;
                            case RIGHT: setAnimationFrame("PLAYER_FIRE_RIGHT_UP", 1); break;
                        }
                        break;
                    case DOWN:
                        switch (playerHorizontalDirection) {
                            case LEFT:  setAnimationFrame("PLAYER_PRONE_LEFT",  0); break;
                            case RIGHT: setAnimationFrame("PLAYER_PRONE_RIGHT", 0); break;
                        }
                        break;
                }
                break;

            case MOVING:
                switch ( playerVerticalDirection )
                {
                    case NONE:
                        switch (playerMovement) {
                            case LEFT:  setAnimation("PLAYER_RUN_LEFT"); break;
                            case RIGHT: setAnimation("PLAYER_RUN_RIGHT"); break;
                        }
                        break;
                    case UP:
                        switch (playerMovement) {
                            case LEFT:  setAnimation("PLAYER_RUN_LEFT_UP"); break;
                            case RIGHT: setAnimation("PLAYER_RUN_RIGHT_UP"); break;
                        }
                        break;
                    case DOWN:
                        switch (playerMovement) {
                            case LEFT:  setAnimation("PLAYER_RUN_LEFT_DOWN"); break;
                            case RIGHT: setAnimation("PLAYER_RUN_RIGHT_DOWN"); break;
                        }
                        break;
                }
                break;

            case JUMPING:
                switch (playerMovement) {
                    case LEFT:   setAnimation("PLAYER_JUMP_LEFT"); break;
                    case RIGHT:  setAnimation("PLAYER_JUMP_RIGHT"); break;
                }
                this.playerVelocity = new Vector( 0.0f, -0.3f );
                break;

            case PRONE:
                moveStop();
                return;
        }


        switch ( playerMovement )
        {
            case RIGHT:
                moveRight( delta ); break;
            case LEFT:
                moveLeft( delta ); break;
            default:
                moveStop(); break;
        }
    }

    public void moveStop() {
        //setPlayerVelocity(new Vector(0.0f, playerVelocity.getY()));
    }

    public void moveLeft( int delta ) {
        playerPosition = this.getPosition().subtract( ContraGame.VIEWPORT.getViewPortOffsetTopLeft() );

        //if( ContraGame.VIEWPORT.getWidth()/4 < getX() ) {
            ContraGame.VIEWPORT.shiftViewPortOffset(new Vector(DEFAULT_PLAYER_VELOCITY_X * delta, 0));
       // }
        //else {
          //  setPlayerVelocity(new Vector( -DEFAULT_PLAYER_VELOCITY_X, 0.0f));
        //}
    }

    public void moveRight( int delta ) {
        playerPosition = this.getPosition().subtract( ContraGame.VIEWPORT.getViewPortOffsetTopLeft() );

        //if( vp.getViewPortOffsetTopLeft().getX() < 5000 ) {
          //  setPlayerVelocity(new Vector( DEFAULT_PLAYER_VELOCITY_X, 0.0f));
        //}
        //else {
        ContraGame.VIEWPORT.shiftViewPortOffset(new Vector(-DEFAULT_PLAYER_VELOCITY_X * delta, 0 ));
        //}
    }


    boolean platformed = false;

    public void updatePosition( int delta )
    {

        //ArrayList<WorldBlock> cb = ;
        //playerWorld.selectedScreenBlock  = cb.get(0);
        //playerWorld.selectedScreenBlock2 = cb.get(1);

        for( WorldBlock i : getPlayerLowerCornerBlocks() ) {
            if( i == null )
                 continue;

            WorldBlock ucb = this.playerWorld.getIndexedBlock( i.getHorizontalIndex(), i.getVerticalIndex() + 1 );
            //if( i.getBlockType() != WorldBlockType.PLATFORM )
              //  continue;

            Collision collision = i.collides( this );
            if( collision == null )
                continue;

            if( this.getPlayerVelocity().getY() > 0.0f ) {
                this.setPosition(this.getPosition().subtract(collision.getMinPenetration()));
                platformed = true;
            }
        }
        if( !platformed )
            setPlayerVelocity(new Vector(0, playerVelocity.getY() + World.GRAVITY));


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
