import jig.Entity;
import jig.Vector;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

public class Bullet extends Entity {
    private static float DEFAULT_BULLET_VELOCITY = 0.25f;

    private Vector      bulletVelocity;
    private BulletType  bulletType;


    public Bullet (final float x, final float y , BulletType bulletType , PlayerHorizontalDirection playerHorizontalDirection,
                   PlayerVerticalDirection playerVerticalDirection, PlayerState playerState , PlayerMovement playerMovement){
        super( x , y - 25);

        this.setScale(2f);
        this.bulletType = bulletType;

        if (bulletType == BulletType.REGULAR)
            addImageWithBoundingBox(ContraGame.getImageAsset("BULLET_REGULAR"));
        else{
            addImageWithBoundingBox(ContraGame.getImageAsset("BULLET_UPGRADE"));
        }

        if (playerVerticalDirection == PlayerVerticalDirection.UP ){
            //shoot straight up
            if (playerMovement == PlayerMovement.NONE)
                this.bulletVelocity = new Vector(0 , -DEFAULT_BULLET_VELOCITY);

            //shoot up and right
            else if (playerHorizontalDirection == PlayerHorizontalDirection.RIGHT )
                this.bulletVelocity = new Vector(DEFAULT_BULLET_VELOCITY , -DEFAULT_BULLET_VELOCITY);
            //shoot up and left
            else if (playerHorizontalDirection == PlayerHorizontalDirection.LEFT )
                this.bulletVelocity = new Vector(-DEFAULT_BULLET_VELOCITY , -DEFAULT_BULLET_VELOCITY);
            //shot up
            else
                this.bulletVelocity = new Vector(0 , -DEFAULT_BULLET_VELOCITY);
        }
        else if (playerVerticalDirection == PlayerVerticalDirection.NONE){
            //shoot straight left
            if (playerHorizontalDirection == PlayerHorizontalDirection.LEFT)
                this.bulletVelocity = new Vector(-DEFAULT_BULLET_VELOCITY , 0);
            //shoot straight right
            else
                this.bulletVelocity = new Vector(DEFAULT_BULLET_VELOCITY , 0);

        }
        else if (playerVerticalDirection == PlayerVerticalDirection.DOWN){

            if (playerState == PlayerState.RUNNING || playerState == PlayerState.JUMPING) {
                //shoot straight down
                if (playerMovement == PlayerMovement.NONE)
                    this.bulletVelocity = new Vector(0, DEFAULT_BULLET_VELOCITY);

                //shoot down and left
                else if (playerHorizontalDirection == PlayerHorizontalDirection.LEFT) {
                    setPosition(x - 10, y - 25 );
                    this.bulletVelocity = new Vector(-DEFAULT_BULLET_VELOCITY, DEFAULT_BULLET_VELOCITY);
                }

                //shoot down and right
                else {
                    setPosition(x + 10, y - 25 );
                    this.bulletVelocity = new Vector(DEFAULT_BULLET_VELOCITY, DEFAULT_BULLET_VELOCITY);
                }

            }
                //else prone
            else {
                setPosition( x , y - 6);
                //shoot straight left
                if (playerHorizontalDirection == PlayerHorizontalDirection.LEFT)
                    this.bulletVelocity = new Vector(-DEFAULT_BULLET_VELOCITY, 0);
                    //shoot straight right
                else
                    this.bulletVelocity = new Vector(DEFAULT_BULLET_VELOCITY, 0);
            }

        }

        else{
            this.bulletVelocity = new Vector(DEFAULT_BULLET_VELOCITY , 0);
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
