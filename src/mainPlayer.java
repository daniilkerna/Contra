import jig.Entity;
import jig.ResourceManager;
import jig.Vector;
import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;

public class mainPlayer extends Entity {
    private Vector playerVelocity;
    private Animation runningLeft,
                      runningRight;


    public mainPlayer(final float x, final float y) {
        super(x, y);
        playerVelocity = new Vector( 0, 0 );

        //addImageWithBoundingBox(ContraGame.getSpriteSheetHashMap().get("PLAYER_SS").getSprite(5, 0));
        this.setScale(1.92f);

        runningRight = new Animation(ContraGame.getSpriteSheetHashMap().get("PLAYER_SS"), 1, 0, 5, 0, true, 150,
                true);

        runningLeft = new Animation(ContraGame.getSpriteSheetHashMap().get("PLAYER_SS"), 1, 0, 5, 0, true, 150,
                true);

        addAnimation( runningLeft );
    }

    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {

    }

    public void update(GameContainer gc, StateBasedGame sbg, int delta) {

        if( gc.getInput().isKeyDown( Input.KEY_A ) ) {
            this.setPlayerVelocity( new Vector( -.25f, 0.0f ) );
        }
        else
            if (  gc.getInput().isKeyDown( Input.KEY_D ) ) {
            this.setPlayerVelocity( new Vector( .25f, 0.0f ) );
        }
        else {
            this.setPlayerVelocity( new Vector( 0.0f, 0.0f ) );
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
