import jig.Entity;
import jig.Vector;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class EnemyTurret extends Entity implements Serializable {
    private int             livesLeft = 5;
    private Player          refPlayer,
                            refPlayer2;


    private TurretState     turretState;
    private int             booletCooldown = 1000;

    public HashMap<String, Animation>   turretAnimations;
    public ArrayList<Bullet>            turretBulletArrayList;




    private Vector                      turretWorldPos;


    public EnemyTurret(final float x, final float y, Player p1 , Player p2) {
        super();

        turretWorldPos = new Vector(x,y);

        this.refPlayer = p1;
        this.refPlayer2 = p2;

        this.setScale(1.5f);

        turretAnimations = new HashMap<>();
        turretBulletArrayList = new ArrayList<>();

        addImageWithBoundingBox(ContraGame.getSpriteSheet("ENEMY_TURRET_SS").getSprite(0, 2));


        turretAnimations.put("WEST",
                new Animation(ContraGame.getSpriteSheet("ENEMY_TURRET_SS"), 0, 2, 2, 2, true, 250, true));

        turretAnimations.put("WEST_NORTH",
                new Animation(ContraGame.getSpriteSheet("ENEMY_TURRET_SS"), 3, 2, 5, 2, true, 250, true));

        turretAnimations.put("WEST_SOUTH",
                new Animation(ContraGame.getSpriteSheet("ENEMY_TURRET_SS"), 6, 1, 8, 1, true, 250, true));

        turretAnimations.put("EAST",
                new Animation(ContraGame.getSpriteSheet("ENEMY_TURRET_SS"), 0, 0, 2, 0, true, 250, true));

        turretAnimations.put("EAST_NORTH",
                new Animation(ContraGame.getSpriteSheet("ENEMY_TURRET_SS"), 6, 3, 8, 3, true, 250, true));

        turretAnimations.put("EAST_SOUTH",
                new Animation(ContraGame.getSpriteSheet("ENEMY_TURRET_SS"), 3, 0, 5, 0, true, 250, true));

        turretAnimations.put("NORTH",
                new Animation(ContraGame.getSpriteSheet("ENEMY_TURRET_SS"), 0, 3, 2, 3, true, 250, true));

        turretAnimations.put("NORTH_WEST",
                new Animation(ContraGame.getSpriteSheet("ENEMY_TURRET_SS"), 6, 2, 8, 2, true, 250, true));

        turretAnimations.put("NORTH_EAST",
                new Animation(ContraGame.getSpriteSheet("ENEMY_TURRET_SS"), 3, 3, 5, 3, true, 250, true));

        turretAnimations.put("SOUTH",
                new Animation(ContraGame.getSpriteSheet("ENEMY_TURRET_SS"), 0, 1, 2, 1, true, 250, true));

        turretAnimations.put("SOUTH_WEST",
                new Animation(ContraGame.getSpriteSheet("ENEMY_TURRET_SS"), 3, 1, 5, 1, true, 250, true));

        turretAnimations.put("SOUTH_EAST",
                new Animation(ContraGame.getSpriteSheet("ENEMY_TURRET_SS"), 6, 0, 8, 0, true, 250, true));


        turretState = TurretState.EAST;
    }


    public void update(GameContainer gc, StateBasedGame sbg, int delta) {
        setPosition( turretWorldPos.getX() - refPlayer.getPlayerVelocity().getX() + ContraGame.VIEWPORT.getViewPortOffsetTopLeft().getX(), turretWorldPos.getY() + ContraGame.VIEWPORT.getViewPortOffsetTopLeft().getY());

        updateState();
        updateAnimation();
        shootBoolets(gc , sbg , delta);
        updateReferencePlayer();
    }

    public void shootBoolets(GameContainer gc, StateBasedGame sbg, int delta){
        if (Math.abs(this.getTurretWorldPos().subtract(this.refPlayer.getPlayerPosition()).getX()) <= 400 || Math.abs(this.getTurretWorldPos().subtract(this.refPlayer2.getPlayerPosition()).getX()) <= 400 ) {
            booletCooldown -= delta;
            if (booletCooldown < 0) {
                booletCooldown = 2000;
                turretBulletArrayList.add(new Bullet(turretWorldPos.getX(), turretWorldPos.getY(), BulletType.REGULAR, turretState));
            }
        }

        Iterator<Bullet> iter = turretBulletArrayList.iterator();

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

    @Override
    public void render(final Graphics g)  {
        super.render(g);

        // render all the bullets
        for (Bullet b : turretBulletArrayList){
            b.render(g);
        }

    }


    public void setAnimation(String key) {
        Animation a = turretAnimations.get(key);
        if (a == null) {
            System.out.println(String.format("[Player: Class] Animation %s, not found!", key));
            return;
        }

        removeAllImages();
        addAnimation(a);
    }


    public void updateAnimation(){
        setAnimation(turretState.toString());
    }

    public void updateState(){
        if (refPlayer.getCoarseGrainedMaxX() < getCoarseGrainedMinX()){
            this.turretState = TurretState.WEST;

        }
        else if ( refPlayer.getCoarseGrainedMinX() > getCoarseGrainedMaxX() ){
            this.turretState = TurretState.EAST;
        }
        else{
            if ( refPlayer.getY() < getY() ){
                this.turretState = TurretState.NORTH;
            }
            else {
                this.turretState = TurretState.SOUTH;
            }
        }
    }

    public void updateReferencePlayer(){
        Player temp;
        if (Math.abs(this.getTurretWorldPos().subtract(this.refPlayer.getPlayerPosition()).getX()) > Math.abs(this.getTurretWorldPos().subtract(this.refPlayer2.getPlayerPosition()).getX())  ) {
            temp = refPlayer;
            refPlayer = refPlayer2;
            refPlayer2 = temp;
        }
    }

    public Vector getTurretWorldPos() {
        return turretWorldPos;
    }

    public TurretState getTurretState() {
        return turretState;
    }

    public void setTurretState(TurretState turretState) {
        this.turretState = turretState;
    }

    public void decrementLives(){
        this.livesLeft--;
    }

    public int getLivesLeft(){
        return  this.livesLeft;
    }

}
