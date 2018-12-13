import jig.Collision;
import jig.Entity;
import jig.Vector;
import org.lwjgl.Sys;
import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Queue;


public class NetworkPlayer extends Player
{
    private Server serverConnection;
    private Player serverLocalPlayer;

    public NetworkPlayer( World world, Player lPlayer, Server svr )
    {
        super(world, Type.PINK);
        this.serverConnection  = svr;
        this.serverLocalPlayer = lPlayer;
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int delta) {
        getState(gc, sbg, delta);
        fireAndUpdateBullets(gc, sbg, delta);
        updateAnimation( gc, sbg, delta );
        updatePosition( delta );
    }

    @Override
    public void fireAndUpdateBullets(GameContainer gc, StateBasedGame sbg, int delta)
    {
        if (isPlayerShooting) {
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
    }

    @Override
    public void getState(GameContainer gc, StateBasedGame sbg, int delta)
    {
        ClientPacket cp = serverConnection.readFromClient();
        if( cp == null )
            return;

        switch ( cp.getClientHorizontalControl() )
        {
            case LEFT:
                playerDesc.state          = PlayerDescriptor.State.RUNNING;
                playerDesc.movement       = PlayerDescriptor.Movement.LEFT;
                playerDesc.hfd            = PlayerDescriptor.HorizontalFacingDirection.LEFT;
                break;
            case RIGHT:
                playerDesc.state          = PlayerDescriptor.State.RUNNING;
                playerDesc.movement       = PlayerDescriptor.Movement.RIGHT;
                playerDesc.hfd            = PlayerDescriptor.HorizontalFacingDirection.RIGHT;
                break;
            case NONE:
                playerDesc.state          = PlayerDescriptor.State.IDLE;
                playerDesc.movement       = PlayerDescriptor.Movement.NONE;
                break;
        }

        switch ( cp.getClientVerticalControl() )
        {
            case UP:
                this.playerDesc.vfd      = PlayerDescriptor.VerticalFacingDirection.UP;
                break;
            case DOWN:
                this.playerDesc.vfd      = PlayerDescriptor.VerticalFacingDirection.DOWN;
                break;
            case NONE:
                this.playerDesc.vfd      = PlayerDescriptor.VerticalFacingDirection.NONE;
                break;
        }

        if ( cp.isClientJump() ) {
            if (isPLayerSwimming){
                return;
            }

            playerDesc.state  = PlayerDescriptor.State.JUMPING;
            if (playerPlatformed) {
                playerVelocity    = new Vector(0, -PLAYER_VELOCITY_Y);
                playerPlatformed = false;
            }
        }
        else if( !playerPlatformed )
        {
            playerDesc.state  = PlayerDescriptor.State.JUMPING;
        }

        isPlayerShooting = cp.isClientFire();

        if (isPLayerSwimming){
            playerDesc.state  = PlayerDescriptor.State.SWIMMING;
        }
    }


    @Override
    public void moveStop( ) {
        playerPosition = this.getPosition().subtract( ContraGame.VIEWPORT.getViewPortOffsetTopLeft() );
        this.setPosition( this.getPosition().getX() + serverLocalPlayer.getPlayerVelocity().getX(), this.getPosition().getY() );
    }

    @Override
    public void moveLeft( int delta ) {
        playerPosition = this.getPosition().subtract( ContraGame.VIEWPORT.getViewPortOffsetTopLeft() );
        playerVelocity = new Vector( -PLAYER_VELOCITY_X * delta, this.getPlayerVelocity().getY() );
        this.setPosition( this.getPosition().getX() + this.playerVelocity.getX() + serverLocalPlayer.getPlayerVelocity().getX(), this.getPosition().getY() + this.getPlayerVelocity().getY() );
    }

    @Override
    public void moveRight( int delta ) {
        playerPosition = this.getPosition().subtract( ContraGame.VIEWPORT.getViewPortOffsetTopLeft() );
        playerVelocity = new Vector( +PLAYER_VELOCITY_X * delta, this.getPlayerVelocity().getY() );
        this.setPosition( this.getPosition().getX() + this.playerVelocity.getX() + serverLocalPlayer.getPlayerVelocity().getX(), this.getPosition().getY() + this.getPlayerVelocity().getY() );
    }

    @Override
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
                updateGravity( delta );
            }

            if (isPLayerSwimming){
                ArrayList<WorldBlock> cornerBlocks = new ArrayList<>();
                cornerBlocks.add(leftBlock);
                cornerBlocks.add(rightBlock);
                for (WorldBlock i : cornerBlocks)
                {
                    if( i.getBlockTexture() == null )
                        continue;

                    if ( !i.getBlockTexture().equals("WATER")){
                        this.isPLayerSwimming = false;
                        this.setPosition(getX() + 10, getY() - 37);
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
}
