import jig.Collision;
import jig.Entity;
import jig.Vector;
import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

public class Player extends Entity {
    private static float PLAYER_VELOCITY_X = 0.1f;
    private static float PLAYER_VELOCITY_Y = 0.1f;
    private static float PLAYER_JUMP_DELAY_TMER = 500; //ms

    private PlayerState                 playerState;
    private PlayerMovement              playerMovement;
    private PlayerHorizontalDirection   playerHorizontalDirection;
    private PlayerVerticalDirection     playerVerticalDirection;

    private Vector                      playerPosition,
                                        playerVelocity;

    private World                       playerWorld;
    private boolean                     playerPlatformed;
    private boolean                     isPlayerShooting;
    private boolean                     isPLayerSwimming;
    private float                       playerYOffset;

    private HashMap<String, Animation>  playerAnimations;

    private ArrayList<Bullet>           bulletArrayList;

    public Player( World world ) {
        super();

        // Not on a platform
        playerPlatformed = false;
        isPlayerShooting = false;
        isPLayerSwimming = false;

        playerWorld = world;

        setScale(2.0f);
        setPosition( ContraGame.VIEWPORT.getWidth()/2 , ContraGame.VIEWPORT.getHeight()/2 - 100 );

        addImageWithBoundingBox(ContraGame.getSpriteSheet("PLAYER_RUN_RIGHT_SS").getSprite(1, 0));

        playerAnimations = new HashMap<>();
        bulletArrayList = new ArrayList<>();


        playerAnimations.put( "PLAYER_WATER_RIGHT",
                new Animation(ContraGame.getSpriteSheet("PLAYER_WATER_RIGHT_SS") , 0, 0, 0, 0, true, 150, true));

        playerAnimations.put( "PLAYER_WATER_LEFT",
                new Animation(ContraGame.getSpriteSheet("PLAYER_WATER_LEFT_SS" ), 0,0, 0,0, true, 150, true ));

        playerAnimations.put( "PLAYER_WATER_DOWN",
                new Animation(ContraGame.getSpriteSheet("PLAYER_WATER_DOWN_SS" ), 0,0, 0,0, true, 150, true ));

        playerAnimations.put( "PLAYER_WATER_GUN_UP_RIGHT",
                new Animation(ContraGame.getSpriteSheet("PLAYER_WATER_GUN_UP_RIGHT_SS") , 0, 0, 0, 0, true, 150, true));

        playerAnimations.put( "PLAYER_WATER_GUN_UP_LEFT",
                new Animation(ContraGame.getSpriteSheet("PLAYER_WATER_GUN_UP_LEFT_SS" ), 0,0, 0,0, true, 150, true ));

        playerAnimations.put( "PLAYER_WATER_GUN_RIGHTUP",
                new Animation(ContraGame.getSpriteSheet("PLAYER_WATER_GUN_RIGHTUP_SS") , 0, 0, 0, 0, true, 150, true));

        playerAnimations.put( "PLAYER_WATER_GUN_LEFTUP",
                new Animation(ContraGame.getSpriteSheet("PLAYER_WATER_GUN_LEFTUP_SS" ), 0,0, 0,0, true, 150, true ));

        playerAnimations.put( "PLAYER_WATER_FIRE_RIGHT",
                new Animation(ContraGame.getSpriteSheet("PLAYER_WATER_FIRE_RIGHT_SS") , 0, 0, 0, 0, true, 150, true));

        playerAnimations.put( "PLAYER_WATER_FIRE_LEFT",
                new Animation(ContraGame.getSpriteSheet("PLAYER_WATER_FIRE_LEFT_SS" ), 0,0, 0,0, true, 150, true ));

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

        playerAnimations.put( "PLAYER_FIRE_RUN_LEFT_STRAIGHT",
                new Animation(ContraGame.getSpriteSheet("PLAYER_FIRE_RUN_LEFT_STRAIGHT_SS" ), 0,0, 2,0, true, 150, true ));

        playerAnimations.put( "PLAYER_FIRE_RUN_RIGHT_STRAIGHT",
                new Animation(ContraGame.getSpriteSheet("PLAYER_FIRE_RUN_RIGHT_STRAIGHT_SS" ), 0,0, 2,0, true, 150, true ));


        //setAnimationFrame("PLAYER_RUN_RIGHT_SS",0 );

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

        /* Draw Collision box */
        g.setColor( Color.green );
        g.drawRect( this.getX() - this.getCoarseGrainedWidth()/2, this.getY() - this.getCoarseGrainedHeight()/2 + playerYOffset/2,  this.getCoarseGrainedWidth(),  this.getCoarseGrainedHeight() + playerYOffset/2 );

        Vector blc = this.getBottomLeftCorner();
        Vector brc = this.getBottomRightCorner();

        g.drawOval( blc.getX() - 2.5f, blc.getY() - 2.5f, 5, 5 );
        g.drawOval( brc.getX() - 2.5f, brc.getY() - 2.5f, 5, 5 );

        /* Player Information */
        g.drawString( "Player Screen Position:(" + this.getX() + "," + this.getY() + ")", 400, 10 );
        g.drawString( "Player World Position:(" + this.playerPosition.getX() + "," + this.playerPosition.getY() + ")", 400, 25 );
        g.drawString( "Player World Velocity:(" + this.playerVelocity.getX() + "," + this.playerVelocity.getY() + ")", 400, 40 );

        // render all the bullets
        for (Bullet b : bulletArrayList){
            b.render(g);
        }
        super.render(g);
    }

    public void update(GameContainer gc, StateBasedGame sbg, int delta) {
        fireAndUpdateBullets(gc , sbg , delta);
        getNewState( gc, sbg, delta );
        updateState( gc, sbg, delta );
        updatePosition( delta );
    }

    private void fireAndUpdateBullets(GameContainer gc, StateBasedGame sbg, int delta){
        if (gc.getInput().isKeyPressed(Input.KEY_K)) {
            bulletArrayList.add(new Bullet(getX() , getY() , BulletType.REGULAR , playerHorizontalDirection , playerVerticalDirection , playerState , playerMovement));
        }

        Iterator<Bullet> iter = bulletArrayList.iterator();

        for ( ;iter.hasNext(); )
        {
            Bullet b = iter.next();

            if( b.isOnScreen() )
                b.update(gc , sbg , delta, this.getPlayerVelocity().getX());
            else
                iter.remove();
        }

        if (gc.getInput().isKeyDown(Input.KEY_K)){
            isPlayerShooting = true;
        }
        else{
            isPlayerShooting = false;
        }
    }

    public Vector getBottomLeftCorner()
    {
        return new Vector( this.getX() - this.getCoarseGrainedWidth()/2, this.getY() + this.getCoarseGrainedHeight()/2);
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
        playerYOffset = (a.getHeight() -  this.getCoarseGrainedHeight())/2;

        this.setCoarseGrainedMaxX(  a.getWidth()/2.0f  );
        this.setCoarseGrainedMinX( -a.getWidth()/2.0f  );
        this.setCoarseGrainedMaxY(  a.getHeight()/2.0f );
        this.setCoarseGrainedMinY( -a.getHeight()/2.0f );

        removeAllImages();
        addAnimation( a, new Vector( 0, playerYOffset/2 ) );
    }


    public void setAnimationFrame( String key, int frame ) {
        Animation a = playerAnimations.get(key);
        if (a == null) {
            System.out.println(String.format("[Player: Class] Animation %s, not found!", key));
            return;
        }
        playerYOffset = (a.getHeight() -  this.getCoarseGrainedHeight())/2;

        this.setCoarseGrainedMaxX(  a.getWidth()/2.0f );
        this.setCoarseGrainedMinX( -a.getWidth()/2.0f );
        this.setCoarseGrainedMaxY(  a.getHeight()/2.0f );
        this.setCoarseGrainedMinY( -a.getHeight()/2.0f );

        removeAllImages();
        addImage(a.getImage(frame), new Vector(0, playerYOffset/2 ));
    }

    public void getNewState( GameContainer gc, StateBasedGame sbg, int delta ) {
        //LEFT or RIGHT
        if (gc.getInput().isKeyDown(Input.KEY_A)) {
            playerState               = PlayerState.RUNNING;
            playerMovement            = PlayerMovement.LEFT;
            playerHorizontalDirection = PlayerHorizontalDirection.LEFT;
        }
        else
        if (gc.getInput().isKeyDown(Input.KEY_D)) {
            playerState               = PlayerState.RUNNING;
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

        // Jump Logic
        if (gc.getInput().isKeyDown(Input.KEY_J)) {
            if (isPLayerSwimming){
                return;
            }

            playerState      = PlayerState.JUMPING;
            if (playerPlatformed) {
                playerVelocity = new Vector(0, -0.4f);
                playerPlatformed = false;
            }
        }
        else if( !playerPlatformed )
        {
            playerState      = PlayerState.JUMPING;
        }
    }

    public void updateState( GameContainer gc, StateBasedGame sbg, int delta )
    {
        ContraGame contraGame = (ContraGame)sbg;
        if (isPLayerSwimming){
            this.playerState = PlayerState.SWIMMING;
        }


        switch (playerState)
        {
            case IDLE:
                moveStop();
                removeAllImages();

                switch (playerVerticalDirection) {
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

            case JUMPING:
                switch (playerHorizontalDirection) {
                    case LEFT:
                        setAnimation("PLAYER_JUMP_LEFT");
                        break;
                    case RIGHT:
                        setAnimation("PLAYER_JUMP_RIGHT");
                        break;
                }
                break;

            case RUNNING:
                switch (playerVerticalDirection) {
                    case NONE:
                        switch (playerMovement) {
                            case LEFT:
                                if (isPlayerShooting)
                                    setAnimation("PLAYER_FIRE_RUN_LEFT_STRAIGHT");
                                else
                                    setAnimation("PLAYER_RUN_LEFT");
                                break;
                            case RIGHT:
                                if (isPlayerShooting)
                                    setAnimation("PLAYER_FIRE_RUN_RIGHT_STRAIGHT");
                                else
                                    setAnimation("PLAYER_RUN_RIGHT");
                                break;
                        }
                        break;
                    case UP:
                        switch (playerMovement) {
                            case LEFT:
                                setAnimation("PLAYER_RUN_LEFT_UP");
                                break;
                            case RIGHT:
                                setAnimation("PLAYER_RUN_RIGHT_UP");
                                break;
                        }
                        break;
                    case DOWN:
                        switch (playerMovement) {
                            case LEFT:
                                setAnimation("PLAYER_RUN_LEFT_DOWN");
                                break;
                            case RIGHT:
                                setAnimation("PLAYER_RUN_RIGHT_DOWN");
                                break;
                        }
                        break;
                }
                break;

            case SWIMMING:
                switch (playerVerticalDirection){
                    case NONE:
                        switch (playerHorizontalDirection) {
                            case LEFT:
                                if (isPlayerShooting){
                                    setAnimation("PLAYER_WATER_FIRE_LEFT");
                                }
                                else
                                    setAnimation("PLAYER_WATER_LEFT");
                                break;
                            case RIGHT:
                                if (isPlayerShooting)
                                    setAnimation("PLAYER_WATER_FIRE_RIGHT");
                                else
                                    setAnimation("PLAYER_WATER_RIGHT");
                                break;
                        }
                        break;

                    case UP:
                        switch (playerMovement) {
                            case LEFT:
                                setAnimation("PLAYER_WATER_GUN_LEFTUP");
                                break;
                            case RIGHT:
                                setAnimation("PLAYER_WATER_GUN_RIGHTUP");
                                break;
                            case NONE:
                                switch (playerHorizontalDirection) {
                                    case LEFT:
                                        setAnimation("PLAYER_WATER_GUN_UP_LEFT");
                                        break;
                                    case RIGHT:
                                        setAnimation("PLAYER_WATER_GUN_UP_RIGHT");
                                        break;
                                }

                        }
                        break;

                    case DOWN:
                        setAnimation("PLAYER_WATER_DOWN");
                        break;

                }


        }
    }

    public void moveStop() {
        setPlayerVelocity(new Vector(0.0f, playerVelocity.getY()));
    }

    public void moveLeft( int delta ) {
        playerPosition = this.getPosition().subtract( ContraGame.VIEWPORT.getViewPortOffsetTopLeft() );
        playerVelocity = new Vector( PLAYER_VELOCITY_X * delta, this.getPlayerVelocity().getY() );
        ContraGame.VIEWPORT.shiftViewPortOffset(new Vector( playerVelocity.getX(), 0));
    }

    public void moveRight( int delta ) {
        playerPosition = this.getPosition().subtract( ContraGame.VIEWPORT.getViewPortOffsetTopLeft() );
        playerVelocity = new Vector( -PLAYER_VELOCITY_X * delta, this.getPlayerVelocity().getY() );
        ContraGame.VIEWPORT.shiftViewPortOffset(new Vector( playerVelocity.getX(), 0));

        //ContraGame.VIEWPORT.shiftViewPortOffset(new Vector(-PLAYER_VELOCITY_X * delta, 0 ));
    }

    public void updatePosition( int delta )
    {
        WorldBlock leftBlock  = playerWorld.getScreenBlock( this.getBottomLeftCorner() );
        WorldBlock rightBlock = playerWorld.getScreenBlock( this.getBottomRightCorner() );

        if( leftBlock == null || rightBlock == null ) {
            this.playerPlatformed = false;

            setPlayerVelocity(new Vector(playerVelocity.getX(), playerVelocity.getY() + World.GRAVITY));
            setPosition( getX(), getY() + playerVelocity.getY()*delta );
        }
        else
        if (rightBlock.getBlockType() != WorldBlockType.PLATFORM && leftBlock.getBlockType() != WorldBlockType.PLATFORM) {

            this.playerPlatformed = false;
            setPlayerVelocity(new Vector(playerVelocity.getX(), playerVelocity.getY() + World.GRAVITY));
            setPosition( getX(), getY() + playerVelocity.getY()*delta );
        }
        else
            if(leftBlock.getBlockTexture().equals("WATER") && rightBlock.getBlockTexture().equals("WATER") ){
                    this.isPLayerSwimming = true;

            }
        else
        {
            if( !this.playerPlatformed ) {
                ArrayList<WorldBlock> cornerBlocks = new ArrayList<>();
                cornerBlocks.add(leftBlock);
                cornerBlocks.add(rightBlock);

                for (WorldBlock i : cornerBlocks) {
                    if (i == null)
                        continue;


                    if( this.getPosition().getY() > i.getCoarseGrainedMinY() )
                        continue;

                    Collision collision = i.collides(this);
                    if (collision == null)
                        continue;

                    if (this.getPlayerVelocity().getY() > 0.03f) {
                        this.setPosition(this.getPosition().add(collision.getMinPenetration()));
                        this.setPlayerVelocity(new Vector(this.getPlayerVelocity().getX(), 0));
                        this.playerPlatformed = true;


                        return;
                    }

                }
                setPlayerVelocity(new Vector(playerVelocity.getX(), playerVelocity.getY() + World.GRAVITY));
                setPosition(getX(), getY() + playerVelocity.getY() * delta);
            }

            if (isPLayerSwimming){
                ArrayList<WorldBlock> cornerBlocks = new ArrayList<>();
                cornerBlocks.add(leftBlock);
                cornerBlocks.add(rightBlock);
                for (WorldBlock i : cornerBlocks) {
                    if ( !i.getBlockTexture().equals("WATER")){
                        this.isPLayerSwimming = false;
                        this.setPosition(getX() + 10, getY() - 37);
                    }

                    System.out.println(i.getBlockTexture());
                }
            }
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

    public Vector getPlayerVelocity() {
        return playerVelocity;
    }

    public void setPlayerVelocity(Vector playerVelocity) {
        this.playerVelocity = playerVelocity;
    }
}
