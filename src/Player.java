import jig.Collision;
import jig.Entity;
import jig.Vector;
import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

public class Player extends Entity
{
    enum Type
    {
        BLUE,
        PINK
    };

    public static float PLAYER_VELOCITY_X = 0.1f;
    public static float PLAYER_VELOCITY_Y = 0.35f;


    public PlayerDescriptor            playerDesc;
    public Vector                      playerPosition,
                                       playerVelocity;

    public World                       playerWorld;
    public boolean                     playerPlatformed;
    public float                       playerYOffset;
    public boolean                     isPlayerShooting;
    public boolean                     isPLayerSwimming;

    public HashMap<String, Animation>  playerAnimations;
    public ArrayList<Bullet>           bulletArrayList;

    public Player( World world, Type color ) {
        super();

        // Not on a platform
        playerPlatformed = false;
        isPlayerShooting = false;
        isPLayerSwimming = false;

        playerWorld = world;

        setScale(2.0f);
        setPosition(ContraGame.VIEWPORT.getWidth() / 2, ContraGame.VIEWPORT.getHeight() / 2 - 70 );

        addImageWithBoundingBox(ContraGame.getSpriteSheet("PLAYER_RUN_RIGHT_SS").getSprite(1, 0));

        playerAnimations = new HashMap<>();
        bulletArrayList = new ArrayList<>();


        playerAnimations.put("PLAYER_WATER_RIGHT",
                new Animation(ContraGame.getSpriteSheet("PLAYER_WATER_RIGHT_SS"), 0, 0, 0, 0, true, 150, true));

        playerAnimations.put("PLAYER_WATER_LEFT",
                new Animation(ContraGame.getSpriteSheet("PLAYER_WATER_LEFT_SS"), 0, 0, 0, 0, true, 150, true));

        playerAnimations.put("PLAYER_WATER_DOWN",
                new Animation(ContraGame.getSpriteSheet("PLAYER_WATER_DOWN_SS"), 0, 0, 0, 0, true, 150, true));

        playerAnimations.put("PLAYER_WATER_GUN_UP_RIGHT",
                new Animation(ContraGame.getSpriteSheet("PLAYER_WATER_GUN_UP_RIGHT_SS"), 0, 0, 0, 0, true, 150, true));

        playerAnimations.put("PLAYER_WATER_GUN_UP_LEFT",
                new Animation(ContraGame.getSpriteSheet("PLAYER_WATER_GUN_UP_LEFT_SS"), 0, 0, 0, 0, true, 150, true));

        playerAnimations.put("PLAYER_WATER_GUN_RIGHTUP",
                new Animation(ContraGame.getSpriteSheet("PLAYER_WATER_GUN_RIGHTUP_SS"), 0, 0, 0, 0, true, 150, true));

        playerAnimations.put("PLAYER_WATER_GUN_LEFTUP",
                new Animation(ContraGame.getSpriteSheet("PLAYER_WATER_GUN_LEFTUP_SS"), 0, 0, 0, 0, true, 150, true));

        playerAnimations.put("PLAYER_WATER_FIRE_RIGHT",
                new Animation(ContraGame.getSpriteSheet("PLAYER_WATER_FIRE_RIGHT_SS"), 0, 0, 0, 0, true, 150, true));

        playerAnimations.put("PLAYER_WATER_FIRE_LEFT",
                new Animation(ContraGame.getSpriteSheet("PLAYER_WATER_FIRE_LEFT_SS"), 0, 0, 0, 0, true, 150, true));

        switch ( color )
        {
            case BLUE:
                playerAnimations.put("PLAYER_PRONE_LEFT",
                        new Animation(ContraGame.getSpriteSheet("PLAYER_PRONE_LEFT_SS"), 0, 0, 0, 0, true, 150, true));

                playerAnimations.put("PLAYER_PRONE_RIGHT",
                        new Animation(ContraGame.getSpriteSheet("PLAYER_PRONE_RIGHT_SS"), 0, 0, 0, 0, true, 150, true));

                playerAnimations.put("PLAYER_RUN_LEFT",
                        new Animation(ContraGame.getSpriteSheet("PLAYER_RUN_LEFT_SS"), 0, 0, 4, 0, true, 150, true));

                playerAnimations.put("PLAYER_RUN_LEFT_UP",
                        new Animation(ContraGame.getSpriteSheet("PLAYER_RUN_LEFT_UP_SS"), 0, 0, 2, 0, true, 150, true));

                playerAnimations.put("PLAYER_RUN_LEFT_DOWN",
                        new Animation(ContraGame.getSpriteSheet("PLAYER_RUN_LEFT_DOWN_SS"), 0, 0, 2, 0, true, 150, true));

                playerAnimations.put("PLAYER_RUN_RIGHT",
                        new Animation(ContraGame.getSpriteSheet("PLAYER_RUN_RIGHT_SS"), 0, 0, 4, 0, true, 150, true));

                playerAnimations.put("PLAYER_RUN_RIGHT_UP",
                        new Animation(ContraGame.getSpriteSheet("PLAYER_RUN_RIGHT_UP_SS"), 0, 0, 2, 0, true, 150, true));

                playerAnimations.put("PLAYER_RUN_RIGHT_DOWN",
                        new Animation(ContraGame.getSpriteSheet("PLAYER_RUN_RIGHT_DOWN_SS"), 0, 0, 2, 0, true, 150, true));

                playerAnimations.put("PLAYER_JUMP_LEFT",
                        new Animation(ContraGame.getSpriteSheet("PLAYER_JUMP_LEFT_SS"), 0, 0, 3, 0, true, 150, true));

                playerAnimations.put("PLAYER_JUMP_RIGHT",
                        new Animation(ContraGame.getSpriteSheet("PLAYER_JUMP_RIGHT_SS"), 0, 0, 3, 0, true, 150, true));

                playerAnimations.put("PLAYER_FIRE_LEFT",
                        new Animation(ContraGame.getSpriteSheet("PLAYER_FIRE_LEFT_SS"), 0, 0, 1, 0, true, 150, true));

                playerAnimations.put("PLAYER_FIRE_RIGHT",
                        new Animation(ContraGame.getSpriteSheet("PLAYER_FIRE_RIGHT_SS"), 0, 0, 1, 0, true, 150, true));

                playerAnimations.put("PLAYER_FIRE_LEFT_UP",
                        new Animation(ContraGame.getSpriteSheet("PLAYER_FIRE_LEFT_UP_SS"), 0, 0, 1, 0, true, 150, true));

                playerAnimations.put("PLAYER_FIRE_RIGHT_UP",
                        new Animation(ContraGame.getSpriteSheet("PLAYER_FIRE_RIGHT_UP_SS"), 0, 0, 1, 0, true, 150, true));

                playerAnimations.put("PLAYER_FIRE_RUN_LEFT_STRAIGHT",
                        new Animation(ContraGame.getSpriteSheet("PLAYER_FIRE_RUN_LEFT_STRAIGHT_SS"), 0, 0, 2, 0, true, 150, true));

                playerAnimations.put("PLAYER_FIRE_RUN_RIGHT_STRAIGHT",
                        new Animation(ContraGame.getSpriteSheet("PLAYER_FIRE_RUN_RIGHT_STRAIGHT_SS"), 0, 0, 2, 0, true, 150, true));
                break;

            case PINK:
                playerAnimations.put("PLAYER_PRONE_LEFT",
                        new Animation(ContraGame.getSpriteSheet("PLAYER2_PRONE_LEFT_SS"), 0, 0, 0, 0, true, 150, true));

                playerAnimations.put("PLAYER_PRONE_RIGHT",
                        new Animation(ContraGame.getSpriteSheet("PLAYER2_PRONE_RIGHT_SS"), 0, 0, 0, 0, true, 150, true));

                playerAnimations.put("PLAYER_RUN_LEFT",
                        new Animation(ContraGame.getSpriteSheet("PLAYER2_RUN_LEFT_SS"), 0, 0, 4, 0, true, 150, true));

                playerAnimations.put("PLAYER_RUN_LEFT_UP",
                        new Animation(ContraGame.getSpriteSheet("PLAYER2_RUN_LEFT_UP_SS"), 0, 0, 2, 0, true, 150, true));

                playerAnimations.put("PLAYER_RUN_LEFT_DOWN",
                        new Animation(ContraGame.getSpriteSheet("PLAYER2_RUN_LEFT_DOWN_SS"), 0, 0, 2, 0, true, 150, true));

                playerAnimations.put("PLAYER_RUN_RIGHT",
                        new Animation(ContraGame.getSpriteSheet("PLAYER2_RUN_RIGHT_SS"), 0, 0, 4, 0, true, 150, true));

                playerAnimations.put("PLAYER_RUN_RIGHT_UP",
                        new Animation(ContraGame.getSpriteSheet("PLAYER2_RUN_RIGHT_UP_SS"), 0, 0, 2, 0, true, 150, true));

                playerAnimations.put("PLAYER_RUN_RIGHT_DOWN",
                        new Animation(ContraGame.getSpriteSheet("PLAYER2_RUN_RIGHT_DOWN_SS"), 0, 0, 2, 0, true, 150, true));

                playerAnimations.put("PLAYER_JUMP_LEFT",
                        new Animation(ContraGame.getSpriteSheet("PLAYER2_JUMP_LEFT_SS"), 0, 0, 3, 0, true, 150, true));

                playerAnimations.put("PLAYER_JUMP_RIGHT",
                        new Animation(ContraGame.getSpriteSheet("PLAYER2_JUMP_RIGHT_SS"), 0, 0, 3, 0, true, 150, true));

                playerAnimations.put("PLAYER_FIRE_LEFT",
                        new Animation(ContraGame.getSpriteSheet("PLAYER2_FIRE_LEFT_SS"), 0, 0, 1, 0, true, 150, true));

                playerAnimations.put("PLAYER_FIRE_RIGHT",
                        new Animation(ContraGame.getSpriteSheet("PLAYER2_FIRE_RIGHT_SS"), 0, 0, 1, 0, true, 150, true));

                playerAnimations.put("PLAYER_FIRE_LEFT_UP",
                        new Animation(ContraGame.getSpriteSheet("PLAYER2_FIRE_LEFT_UP_SS"), 0, 0, 1, 0, true, 150, true));

                playerAnimations.put("PLAYER_FIRE_RIGHT_UP",
                        new Animation(ContraGame.getSpriteSheet("PLAYER2_FIRE_RIGHT_UP_SS"), 0, 0, 1, 0, true, 150, true));

                playerAnimations.put("PLAYER_FIRE_RUN_LEFT_STRAIGHT",
                        new Animation(ContraGame.getSpriteSheet("PLAYER2_FIRE_RUN_LEFT_STRAIGHT_SS"), 0, 0, 2, 0, true, 150, true));

                playerAnimations.put("PLAYER_FIRE_RUN_RIGHT_STRAIGHT",
                        new Animation(ContraGame.getSpriteSheet("PLAYER2_FIRE_RUN_RIGHT_STRAIGHT_SS"), 0, 0, 2, 0, true, 150, true));
                break;
        }
        playerPosition = new Vector(0, 0);
        playerVelocity = new Vector(0, 0);

        playerDesc = new PlayerDescriptor(PlayerDescriptor.State.IDLE, PlayerDescriptor.Movement.NONE, PlayerDescriptor.HorizontalFacingDirection.RIGHT, PlayerDescriptor.VerticalFacingDirection.NONE);
    }

    @Override
    public void render(final Graphics g)  {

        /* Draw Collision box */
        g.setColor( Color.green );
        //g.drawRect( this.getX() - this.getCoarseGrainedWidth()/2, this.getY() - this.getCoarseGrainedHeight()/2 + playerYOffset/2,  this.getCoarseGrainedWidth(),  this.getCoarseGrainedHeight() + playerYOffset/2 );

        Vector blc = this.getBottomLeftCorner();
        Vector brc = this.getBottomRightCorner();

        g.drawOval( blc.getX() - 2.5f, blc.getY() - 2.5f, 5, 5 );
        g.drawOval( brc.getX() - 2.5f, brc.getY() - 2.5f, 5, 5 );

        /* Player Information */
        //g.drawString( "Player Screen Position:(" + this.getX() + "," + this.getY() + ")", 400, 10 );
        //g.drawString( "Player World Position:(" + this.playerPosition.getX() + "," + this.playerPosition.getY() + ")", 400, 10 );
        //g.drawString( "Player World Velocity:(" + this.playerVelocity.getX() + "," + this.playerVelocity.getY() + ")", 400, 40 );

        // render all the bullets
        for (Bullet b : bulletArrayList){
            b.render(g);
        }
        super.render(g);
    }

    public void update(GameContainer gc, StateBasedGame sbg, int delta) {
        fireAndUpdateBullets(gc , sbg , delta);
        getState( gc, sbg, delta );
        updateAnimation( gc, sbg, delta );
        updatePosition( delta );
    }

    public void fireAndUpdateBullets(GameContainer gc, StateBasedGame sbg, int delta){

        if (gc.getInput().isKeyPressed(Input.KEY_K)) {
            bulletArrayList.add(new Bullet(getX() , getY() , BulletType.REGULAR , playerDesc ) );
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
        isPlayerShooting = gc.getInput().isKeyDown(Input.KEY_K);
    }

    public Vector getBottomLeftCorner()
    {
        return new Vector( this.getX() - this.getCoarseGrainedWidth()/2 , this.getY() + this.getCoarseGrainedHeight()/2);
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


    private void setAnimationFrame( String key, int frame ) {
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

    public void getState(GameContainer gc, StateBasedGame sbg, int delta )
    {
        if (gc.getInput().isKeyDown(Input.KEY_A)) {
            playerDesc.state          = PlayerDescriptor.State.RUNNING;
            playerDesc.movement       = PlayerDescriptor.Movement.LEFT;
            playerDesc.hfd            = PlayerDescriptor.HorizontalFacingDirection.LEFT;
        }
        else
        if (gc.getInput().isKeyDown(Input.KEY_D)) {
            playerDesc.state          = PlayerDescriptor.State.RUNNING;
            playerDesc.movement       = PlayerDescriptor.Movement.RIGHT;
            playerDesc.hfd            = PlayerDescriptor.HorizontalFacingDirection.RIGHT;
        } else {
            playerDesc.state          = PlayerDescriptor.State.IDLE;
            playerDesc.movement       = PlayerDescriptor.Movement.NONE;
        }

        if (gc.getInput().isKeyDown(Input.KEY_W)) {
            playerDesc.vfd = PlayerDescriptor.VerticalFacingDirection.UP;
        }
        else
        if (gc.getInput().isKeyDown(Input.KEY_S)){
            playerDesc.vfd = PlayerDescriptor.VerticalFacingDirection.DOWN;
        }
        else {
            playerDesc.vfd = PlayerDescriptor.VerticalFacingDirection.NONE;
        }

        if (gc.getInput().isKeyDown(Input.KEY_J)) {
            if (isPLayerSwimming){
                return;
            }

            playerDesc.state  = PlayerDescriptor.State.JUMPING;
            if (playerPlatformed) {
                playerVelocity = new Vector(0, -PLAYER_VELOCITY_Y);
                playerPlatformed = false;
            }
        }
        else if( !playerPlatformed )
        {
            playerDesc.state  = PlayerDescriptor.State.JUMPING;
        }

        if (isPLayerSwimming){
            playerDesc.state  = PlayerDescriptor.State.SWIMMING;
        }
    }

    public void updateAnimation( GameContainer gc, StateBasedGame sbg, int delta )
    {
        switch (playerDesc.state )
        {
            case IDLE:
                switch (playerDesc.vfd) {
                    case NONE:
                        switch (playerDesc.hfd) {
                            case LEFT:
                                setAnimationFrame("PLAYER_FIRE_LEFT", 1);
                                break;
                            case RIGHT:
                                setAnimationFrame("PLAYER_FIRE_RIGHT", 1);
                                break;
                        }
                        break;
                    case UP:
                        switch (playerDesc.hfd) {
                            case LEFT:
                                setAnimationFrame("PLAYER_FIRE_LEFT_UP", 1);
                                break;
                            case RIGHT:
                                setAnimationFrame("PLAYER_FIRE_RIGHT_UP", 1);
                                break;
                        }
                        break;
                    case DOWN:
                        switch (playerDesc.hfd) {
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
                switch (playerDesc.hfd) {
                    case LEFT:
                        setAnimation("PLAYER_JUMP_LEFT");
                        break;
                    case RIGHT:
                        setAnimation("PLAYER_JUMP_RIGHT");
                        break;
                }
                break;

            case RUNNING:
                switch (playerDesc.vfd) {
                    case NONE:
                        switch (playerDesc.movement) {
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
                        switch (playerDesc.hfd) {
                            case LEFT:
                                setAnimation("PLAYER_RUN_LEFT_UP");
                                break;
                            case RIGHT:
                                setAnimation("PLAYER_RUN_RIGHT_UP");
                                break;
                        }
                        break;
                    case DOWN:
                        switch (playerDesc.hfd) {
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
                switch (playerDesc.vfd){
                    case NONE:
                        switch (playerDesc.hfd) {
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
                        switch (playerDesc.movement) {
                            case LEFT:
                                setAnimation("PLAYER_WATER_GUN_LEFTUP");
                                break;
                            case RIGHT:
                                setAnimation("PLAYER_WATER_GUN_RIGHTUP");
                                break;
                            case NONE:
                                switch (playerDesc.hfd) {
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

    public void moveStop()
    {
        playerPosition = this.getPosition().subtract( ContraGame.VIEWPORT.getViewPortOffsetTopLeft() );
        setPlayerVelocity(new Vector(0.0f, playerVelocity.getY()));
    }

    public void moveLeft( int delta )
    {
        playerPosition = this.getPosition().subtract( ContraGame.VIEWPORT.getViewPortOffsetTopLeft() );
        playerVelocity = new Vector( PLAYER_VELOCITY_X * delta, this.getPlayerVelocity().getY() );
        ContraGame.VIEWPORT.shiftViewPortOffset(new Vector( playerVelocity.getX(), 0));
    }

    public void moveRight( int delta )
    {
        playerPosition = this.getPosition().subtract( ContraGame.VIEWPORT.getViewPortOffsetTopLeft() );
        playerVelocity = new Vector( -PLAYER_VELOCITY_X * delta, this.getPlayerVelocity().getY() );
        ContraGame.VIEWPORT.shiftViewPortOffset(new Vector( playerVelocity.getX(), 0));
    }

    public void updatePosition( int delta )
    {
        WorldBlock leftBlock  = playerWorld.getScreenBlock( this.getBottomLeftCorner() );
        WorldBlock rightBlock = playerWorld.getScreenBlock( this.getBottomRightCorner() );

        if( leftBlock == null || rightBlock == null ) {
            this.playerPlatformed = false;
            updateGravity( delta );
        }
        else
        if (rightBlock.getBlockType() != WorldBlockType.PLATFORM && leftBlock.getBlockType() != WorldBlockType.PLATFORM) {

            this.playerPlatformed = false;
            updateGravity( delta );
        }

        else
        {
            if( leftBlock.getBlockTexture() != null  )
                if(leftBlock.getBlockTexture().equals("WATER") )
                    this.isPLayerSwimming = true;

            if( rightBlock.getBlockTexture() != null  )
                if( rightBlock.getBlockTexture().equals("WATER") )
                    this.isPLayerSwimming = true;

            if( !this.playerPlatformed )
            {
                ArrayList<WorldBlock> cornerBlocks = new ArrayList<>();
                cornerBlocks.add(leftBlock);
                cornerBlocks.add(rightBlock);

                for (WorldBlock i : cornerBlocks)
                {
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
                updateGravity( delta );
            }

            if (isPLayerSwimming)
            {
                ArrayList<WorldBlock> cornerBlocks = new ArrayList<>();
                cornerBlocks.add(leftBlock);
                cornerBlocks.add(rightBlock);
                for (WorldBlock i : cornerBlocks)
                {
                    if( i.getBlockTexture() == null )
                        continue;

                    if ( !i.getBlockTexture().equals("WATER")){
                        this.isPLayerSwimming = false;
                        this.setPosition(getX(), getY() - 37);
                        ContraGame.VIEWPORT.shiftViewPortOffset( new Vector( 10, 0 ) );
                    }
                }
            }
        }

        switch ( playerDesc.movement )
        {
            case RIGHT:
                moveRight( delta ); break;
            case LEFT:
                moveLeft( delta ); break;
            default:
                moveStop(); break;
        }
    }

    public void updateGravity( int delta )
    {
        Vector v = new Vector( playerVelocity.getX(),
                            playerVelocity.getY() + World.GRAVITY);

        setPlayerVelocity( v );
        setPosition( getX(), getY() + playerVelocity.getY()*delta );
    }

    public Vector getPlayerVelocity() {
        return playerVelocity;
    }

    public void setPlayerVelocity(Vector playerVelocity) {
        this.playerVelocity = playerVelocity;
    }
}
