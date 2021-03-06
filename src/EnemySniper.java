import jig.Vector;
import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.state.StateBasedGame;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class EnemySniper extends Player implements Serializable {

    private Player          refPlayer,
                            refPlayer2;
    private int             livesLeft = 2;
    private int             booletCooldown = 2000;

    public Vector                       sniperPosition;
    public HashMap<String, Animation>   sniperAnimations;
    public ArrayList<Bullet>            sniperBulletArrayList;



    private String                      currentAnimation;


    public Vector getSniperPosition() {
        return sniperPosition;
    }

    public EnemySniper (World world , Player p1, Player p2 , final float x , final float y, PlayerDescriptor playerDescriptor){
        this(world , p1, p2 , x , y);
        this.playerDesc = playerDescriptor;

    }

    public EnemySniper (World world , Player p1, Player p2 , final float x , final float y){
        super(world, Type.PINK);

        sniperPosition = new Vector(x, y);

        this.refPlayer = p1;
        this.refPlayer2 = p2;

        sniperAnimations = new HashMap<>();
        sniperBulletArrayList = new ArrayList<>();

        playerDesc = new PlayerDescriptor(PlayerDescriptor.State.IDLE, PlayerDescriptor.Movement.NONE, PlayerDescriptor.HorizontalFacingDirection.LEFT, PlayerDescriptor.VerticalFacingDirection.NONE);


        addImageWithBoundingBox(ContraGame.getSpriteSheet("ENEMY_SNIPER_LEFT_SS").getSprite(0, 0));

        sniperAnimations.put("LEFT",
                new Animation(ContraGame.getSpriteSheet("ENEMY_SNIPER_LEFT_SS"), 0, 0, 1, 0, true, 150, true));

        sniperAnimations.put("LEFT_DOWN",
                new Animation(ContraGame.getSpriteSheet("ENEMY_SNIPER_LEFT_SS"), 4, 0, 4, 0, true, 150, true));

        sniperAnimations.put("LEFT_UP",
                new Animation(ContraGame.getSpriteSheet("ENEMY_SNIPER_LEFT_SS"), 2, 0, 3, 0, true, 150, true));

        sniperAnimations.put("RIGHT",
                new Animation(ContraGame.getSpriteSheet("ENEMY_SNIPER_RIGHT_SS"), 3, 0, 4, 0, true, 150, true));

        sniperAnimations.put("RIGHT_DOWN",
                new Animation(ContraGame.getSpriteSheet("ENEMY_SNIPER_RIGHT_SS"), 0, 0, 0, 0, true, 150, true));

        sniperAnimations.put("RIGHT_UP",
                new Animation(ContraGame.getSpriteSheet("ENEMY_SNIPER_RIGHT_SS"), 1, 0, 2, 0, true, 150, true));


        setAnimation("LEFT");

    }


    public void update(GameContainer gc, StateBasedGame sbg, int delta) {
        setPosition( sniperPosition.getX() - refPlayer.getPlayerVelocity().getX() + ContraGame.VIEWPORT.getViewPortOffsetTopLeft().getX(), sniperPosition.getY() + ContraGame.VIEWPORT.getViewPortOffsetTopLeft().getY());
        fireAndUpdateBullets(gc , sbg , delta);
        getState( gc, sbg, delta );
        updateAnimation();
        updateReferencePlayer();
    }


    public void fireAndUpdateBullets(GameContainer gc, StateBasedGame sbg, int delta){
        if (Math.abs(this.getSniperPosition().subtract(this.refPlayer.getPlayerPosition()).getX()) <= 400 || Math.abs(this.getSniperPosition().subtract(this.refPlayer2.getPlayerPosition()).getX()) <= 400 ) {
            booletCooldown -= delta;
            if (booletCooldown < 0) {
                booletCooldown = 2000;
                sniperBulletArrayList.add(new Bullet(sniperPosition.getX(), sniperPosition.getY(), BulletType.REGULAR, playerDesc, 20));
            }
        }

        Iterator<Bullet> iter = sniperBulletArrayList.iterator();
        for ( ;iter.hasNext(); )
        {
            Bullet b = iter.next();

            if( b.isInTheWorld() )
                b.update(gc , sbg , delta, this.refPlayer.getPlayerVelocity().getX());
            else
                iter.remove();

            if(b.isBulletDead )
                iter.remove();
        }
    }

    public void getState(GameContainer gc, StateBasedGame sbg, int delta ){
        if (refPlayer.getX() < getX()){
            this.playerDesc.hfd = PlayerDescriptor.HorizontalFacingDirection.LEFT;
            this.playerDesc.movement = PlayerDescriptor.Movement.LEFT;
        }
        else{
            this.playerDesc.hfd = PlayerDescriptor.HorizontalFacingDirection.RIGHT;
            this.playerDesc.movement = PlayerDescriptor.Movement.RIGHT;
        }

        if (refPlayer.getY() < getCoarseGrainedMinY()){
            this.playerDesc.vfd = PlayerDescriptor.VerticalFacingDirection.UP;
            this.playerDesc.state = PlayerDescriptor.State.RUNNING;
        }
        else if (refPlayer.getY() > getCoarseGrainedMaxY()){
            this.playerDesc.vfd = PlayerDescriptor.VerticalFacingDirection.DOWN;
            this.playerDesc.state = PlayerDescriptor.State.RUNNING;
        }
        else{
            this.playerDesc.vfd = PlayerDescriptor.VerticalFacingDirection.NONE;
        }
    }


    public void updateAnimation(){
        switch (playerDesc.hfd){
            case LEFT:
                switch (playerDesc.vfd){
                    case DOWN:
                        setAnimationFrame1("LEFT_DOWN"  , 0);
                        break;

                    case UP:
                        setAnimationFrame1("LEFT_UP"  , 0);
                        break;

                    case NONE:
                        setAnimationFrame1( "LEFT"  ,  0);
                        break;
                }
                break;

            case RIGHT:
                switch (playerDesc.vfd){
                    case DOWN:
                        setAnimationFrame1("RIGHT_DOWN" , 0);
                        break;

                    case UP:
                        setAnimationFrame1("RIGHT_UP" , 0);
                        break;

                    case NONE:
                        setAnimationFrame1("RIGHT" , 0);
                        break;

                }
                break;
        }
    }

    public void setAnimation( String key ) {
        Animation a = sniperAnimations.get( key );
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
        addAnimation( a );
        this.currentAnimation = key;
    }


    public void setAnimationFrame1( String key, int frame ) {
        Animation a = sniperAnimations.get(key);
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
        addImage(a.getImage(frame));
        this.currentAnimation = key;
    }

    @Override
    public void render(final Graphics g)  {
        super.render(g);

        // render all the bullets
        for (Bullet b : sniperBulletArrayList){
            b.render(g);
        }

    }

    public void setPlayerDesc(PlayerDescriptor playerDesc){
        this.playerDesc = playerDesc;
    }

    public void updateReferencePlayer(){
        Player temp;
        if (Math.abs(this.getSniperPosition().subtract(this.refPlayer.getPlayerPosition()).getX()) > Math.abs(this.getSniperPosition().subtract(this.refPlayer2.getPlayerPosition()).getX())  ) {
            temp = refPlayer;
            refPlayer = refPlayer2;
            refPlayer2 = temp;
        }

    }

    public PlayerDescriptor getSniperDesc(){
        return this.playerDesc;
    }

    public void decrementLives(){
        this.livesLeft--;
    }

    public int getLivesLeft(){
        return  this.livesLeft;
    }

    public String getCurrentAnimation() {
        return currentAnimation;
    }
}
