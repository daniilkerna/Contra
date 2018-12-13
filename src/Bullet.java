import jig.ConvexPolygon;
import jig.Entity;
import jig.Vector;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

public class Bullet extends Entity
{
    private static float BULLET_VELOCITY = 0.25f;

    private Vector      bulletVelocity;
    private BulletType  bulletType;


    public Bullet (final float x, final float y , BulletType bulletType , Player.Descriptor pDesc ){
        super( x , y - 25);

        this.setScale(2f);
        this.bulletType = bulletType;

        switch ( bulletType )
        {
            case REGULAR:
                addImageWithBoundingBox(ContraGame.getImageAsset("BULLET_REGULAR"));
                break;
            case UPGRADE:
                addImageWithBoundingBox(ContraGame.getImageAsset("BULLET_UPGRADE"));
                break;
            default:
                System.out.println( "[ Bullet ] Unknown ammo type" );
                addShape( new ConvexPolygon( 5.0f, 5.0f ) );
                break;
        }

        switch ( pDesc.vfd )
        {
            case NONE:
                //shoot straight left
                switch ( pDesc.hfd )
                {
                    case LEFT: bulletVelocity = new Vector(-BULLET_VELOCITY , 0); break;
                    case RIGHT:  bulletVelocity = new Vector(BULLET_VELOCITY , 0); break;
                }
                break;

            case UP:
                //shoot straight up
                if (pDesc.movement == Player.Movement.NONE) {
                    this.bulletVelocity = new Vector(0, -BULLET_VELOCITY);
                    break;
                }
                switch ( pDesc.hfd )
                {
                    case RIGHT: bulletVelocity = new Vector(BULLET_VELOCITY , -BULLET_VELOCITY);
                        break;
                    case LEFT:bulletVelocity = new Vector(-BULLET_VELOCITY , -BULLET_VELOCITY);
                        break;
                }
                break;

            case DOWN:
                switch ( pDesc.state )
                {
                    case JUMPING:
                    case RUNNING:
                        if (pDesc.movement == Player.Movement.NONE) {
                            bulletVelocity = new Vector(0, BULLET_VELOCITY);
                            break;
                        }
                        switch ( pDesc.hfd )
                        {
                            case LEFT:
                                setPosition(x - 10, y - 25 );
                                bulletVelocity = new Vector(-BULLET_VELOCITY, BULLET_VELOCITY);
                                break;
                            case RIGHT:
                                setPosition(x + 10, y - 25 );
                                bulletVelocity = new Vector(BULLET_VELOCITY, BULLET_VELOCITY);
                                break;
                        }
                        break;
                    default:
                        switch ( pDesc.hfd )
                        {
                            case LEFT:
                                setPosition(x - 10, y - 35 );
                                bulletVelocity = new Vector(-BULLET_VELOCITY, 0);
                                break;
                            case RIGHT:
                                setPosition(x + 10, y - 35 );
                                bulletVelocity = new Vector(BULLET_VELOCITY, 0);
                                break;
                        }

                }
                break;
        }
    }

    public Bullet (final float x, final float y , BulletType bulletType , Player.Descriptor pDesc , final int offSet){
        super( x , y - 25 );

        this.setScale(2f);
        this.bulletType = bulletType;

        switch ( bulletType )
        {
            case REGULAR:
                addImageWithBoundingBox(ContraGame.getImageAsset("BULLET_REGULAR"));
                break;
            case UPGRADE:
                addImageWithBoundingBox(ContraGame.getImageAsset("BULLET_UPGRADE"));
                break;
            default:
                System.out.println( "[ Bullet ] Unknown ammo type" );
                addShape( new ConvexPolygon( 5.0f, 5.0f ) );
                break;
        }

        switch ( pDesc.vfd )
        {
            case NONE:
                //shoot straight left
                switch ( pDesc.hfd )
                {
                    case LEFT: bulletVelocity = new Vector(-BULLET_VELOCITY , 0); break;
                    case RIGHT:  bulletVelocity = new Vector(BULLET_VELOCITY , 0); break;
                }
                break;

            case UP:
                //shoot straight up
                if (pDesc.movement == Player.Movement.NONE) {
                    this.bulletVelocity = new Vector(0, -BULLET_VELOCITY);
                    break;
                }
                switch ( pDesc.hfd )
                {
                    case RIGHT: bulletVelocity = new Vector(BULLET_VELOCITY , -BULLET_VELOCITY);
                        break;
                    case LEFT:bulletVelocity = new Vector(-BULLET_VELOCITY , -BULLET_VELOCITY);
                        break;
                }
                break;

            case DOWN:
                switch ( pDesc.state )
                {
                    case JUMPING:
                    case RUNNING:
                        if (pDesc.movement == Player.Movement.NONE) {
                            bulletVelocity = new Vector(0, BULLET_VELOCITY);
                            break;
                        }
                        switch ( pDesc.hfd )
                        {
                            case LEFT:
                                setPosition(x - 10, y - 25 - offSet );
                                bulletVelocity = new Vector(-BULLET_VELOCITY, BULLET_VELOCITY);
                                break;
                            case RIGHT:
                                setPosition(x + 10, y - 25 - offSet );
                                bulletVelocity = new Vector(BULLET_VELOCITY, BULLET_VELOCITY);
                                break;
                        }
                        break;
                    default:
                        switch ( pDesc.hfd )
                        {
                            case LEFT:
                                setPosition(x - 10, y - 35 - offSet );
                                bulletVelocity = new Vector(-BULLET_VELOCITY, 0);
                                break;
                            case RIGHT:
                                setPosition(x + 10, y - 35 - offSet );
                                bulletVelocity = new Vector(BULLET_VELOCITY, 0);
                                break;
                        }

                }
                break;
        }
    }


    public Bullet (final float x, final float y , BulletType bulletType , TurretState turretState){
        super( x , y );

        this.setScale(2f);
        this.bulletType = bulletType;

        switch ( bulletType )
        {
            case REGULAR:
                addImageWithBoundingBox(ContraGame.getImageAsset("BULLET_REGULAR"));
                break;
            case UPGRADE:
                addImageWithBoundingBox(ContraGame.getImageAsset("BULLET_UPGRADE"));
                break;
            default:
                System.out.println( "[ Bullet ] Unknown ammo type" );
                addShape( new ConvexPolygon( 5.0f, 5.0f ) );
                break;
        }

        switch ( turretState ){
            case EAST:
                bulletVelocity = new Vector(BULLET_VELOCITY , 0);
                break;

            case WEST:
                bulletVelocity = new Vector(-BULLET_VELOCITY , 0);
                break;

            case NORTH:
                bulletVelocity = new Vector( 0, -BULLET_VELOCITY);
                break;

            case SOUTH:
                bulletVelocity = new Vector( 0, BULLET_VELOCITY);
                break;
        }

    }

    public boolean isOnScreen()
    {
        if( this.getX() < 0 || this.getX() > ContraGame.VIEWPORT.getWidth() ) {
            return false;
        }
        if( this.getY() < 0 || this.getY() > ContraGame.VIEWPORT.getHeight() ) {
            return false;
        }
        return true;
    }

    public void update(GameContainer gc, StateBasedGame sbg, int delta, float playerVelocity)
    {
        setPosition( getX() + playerVelocity + bulletVelocity.getX()*delta, getY() + bulletVelocity.getY()*delta );
    }
}
