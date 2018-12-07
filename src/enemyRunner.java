import jig.Vector;
import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

public class enemyRunner extends Player {
    public static float PLAYER_VELOCITY_X = 0.04f;
    public static float PLAYER_VELOCITY_Y = 0.35f;

    private Player refPlayer;

    public enemyRunner (World world , Player p1){
        super(world);

        this.refPlayer = p1;

        playerAnimations.put("ENEMY_RUNNER",
                new Animation(ContraGame.getSpriteSheet("ENEMY_RUNNER_SS"), 0, 0, 6, 0, true, 150, true));

        playerDesc = new Player.Descriptor(State.RUNNING, Movement.LEFT, HorizontalFacingDirection.LEFT, VerticalFacingDirection.NONE);

        addImageWithBoundingBox(ContraGame.getSpriteSheet("ENEMY_RUNNER_SS").getSprite(0, 0));
        setAnimation("ENEMY_RUNNER");
        setPosition(ContraGame.VIEWPORT.getWidth()  , ContraGame.VIEWPORT.getHeight() / 2);
        playerPosition = getPosition();

    }


    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int delta) {
        //moveLeft( delta );
        updateAnimation( gc, sbg, delta );
        updatePosition( delta );
    }

    @Override
    public void moveLeft( int delta ) {
        // Position of player
        playerPosition = this.getPosition().add(playerVelocity );
        playerVelocity = new Vector( -PLAYER_VELOCITY_X * delta, this.getPlayerVelocity().getY() );

        //setPosition(playerPosition);
        this.setPosition( this.playerPosition.getX() + this.playerVelocity.getX() +  this.refPlayer.getPlayerVelocity().getX(), this.playerPosition.getY() + this.getPlayerVelocity().getY() );

    }

    public void updateAnimation( GameContainer gc, StateBasedGame sbg, int delta )
    {
        ContraGame contraGame = (ContraGame)sbg;
        if (isPLayerSwimming){
            playerDesc.state  = State.SWIMMING;
        }


        switch (playerDesc.state )
        {

            case RUNNING:
                switch (playerDesc.vfd) {
                    case NONE:
                        switch (playerDesc.movement) {
                            case LEFT:
                                if (isPlayerShooting)
                                    setAnimation("ENEMY_RUNNER");
                                else
                                    setAnimation("ENEMY_RUNNER");
                                break;
                            case RIGHT:
                                if (isPlayerShooting)
                                    setAnimation("ENEMY_RUNNER");
                                else
                                    setAnimation("ENEMY_RUNNER");
                                break;
                        }
                        break;
                }
                break;

            case JUMPING:
                switch (playerDesc.hfd) {
                    case LEFT:
                        setAnimation("ENEMY_RUNNER");
                        break;
                    case RIGHT:
                        setAnimation("ENEMY_RUNNER");
                        break;
                }
                break;

            case SWIMMING:
                switch (playerDesc.vfd){
                    case NONE:
                        switch (playerDesc.hfd) {
                            case LEFT:
                                if (isPlayerShooting){
                                    setAnimation("PLAYER_WATER_DOWN");
                                }
                                else
                                    setAnimation("PLAYER_WATER_DOWN");
                                break;
                            case RIGHT:
                                if (isPlayerShooting)
                                    setAnimation("PLAYER_WATER_DOWN");
                                else
                                    setAnimation("PLAYER_WATER_DOWN");
                                break;
                        }
                        break;
                }
        }
    }
}
