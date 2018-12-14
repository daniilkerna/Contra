import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import java.io.Serializable;
import java.util.ArrayList;

public class EnemyManager implements Serializable
{
    private ArrayList<EnemySniper>       sniperArrayList;
    private ArrayList<EnemyRunner>       runnerArrayList;
    private ArrayList<EnemyTurret>       turretArrayList;
    private ArrayList<Bullet>            allEnemyBullets;

    public EnemyManager(World world, Player player1 , Player player2){
        runnerArrayList = new ArrayList<>();
        turretArrayList = new ArrayList<>();
        sniperArrayList = new ArrayList<>();
        allEnemyBullets = new ArrayList<>();


        //add Runners
       // runnerArrayList.add(new EnemyRunner(world , player1));

        //add snipers
        sniperArrayList.add(new EnemySniper(world , player1, player2 , 634 , 408));
        sniperArrayList.add(new EnemySniper(world , player1 , player2 , 1275 , 446));
        sniperArrayList.add(new EnemySniper(world , player1 , player2 , 2554 , 225));
        sniperArrayList.add(new EnemySniper(world , player1 , player2 , 4864 , 225));
        sniperArrayList.add(new EnemySniper(world , player1 ,player2 , 5346 , 450));
        sniperArrayList.add(new EnemySniper(world , player1 , player2 ,6285 , 330));
        sniperArrayList.add(new EnemySniper(world , player1 , player2 ,6952 , 210));


        //add Turrets

        turretArrayList.add(new EnemyTurret(  795 ,  400 ,  player1 , player2));
        turretArrayList.add(new EnemyTurret(  1495, 370 ,  player1 , player2));
        turretArrayList.add(new EnemyTurret( 2430 , 320 ,  player1 , player2));
        turretArrayList.add(new EnemyTurret( 3305 , 366 ,  player1 , player2));
        turretArrayList.add(new EnemyTurret( 3690 , 366 ,  player1 , player2));
        turretArrayList.add(new EnemyTurret( 4200 , 395 ,  player1 , player2));
        turretArrayList.add(new EnemyTurret( 4452 , 170 ,  player1 , player2));
        turretArrayList.add(new EnemyTurret( 5864 , 333 ,  player1 , player2));
        turretArrayList.add(new EnemyTurret( 6260 , 460 ,  player1 , player2));
        turretArrayList.add(new EnemyTurret( 6600 , 474 ,  player1 , player2));
    }


    public void render(GameContainer container, StateBasedGame game, final Graphics g){
        for (EnemySniper p : sniperArrayList)
            p.render(g);

        for (EnemyTurret enemy : turretArrayList)
            enemy.render(g);

        for (EnemyRunner enemy : runnerArrayList)
            enemy.render(g);
    }


    public void update(GameContainer container, StateBasedGame game,
                       int delta) throws SlickException {

        for (EnemySniper p : sniperArrayList)
            p.update(container , game , delta);

        for (EnemyTurret enemy : turretArrayList)
            enemy.update(container , game , delta);

        for (EnemyRunner enemy : runnerArrayList)
            enemy.update(container , game , delta);
    }

    public ArrayList<EnemySniper> getSniperArrayList() {
        return sniperArrayList;
    }

    public ArrayList<EnemyRunner> getRunnerArrayList() {
        return runnerArrayList;
    }

    public ArrayList<EnemyTurret> getTurretArrayList() {
        return turretArrayList;
    }

    public ArrayList<Bullet> getAllEnemyBullets() {
        allEnemyBullets = null;
        allEnemyBullets = new ArrayList<>();

        for (EnemySniper p : sniperArrayList)
            for( Bullet b : p.sniperBulletArrayList)
                allEnemyBullets.add(b);

        for (EnemyTurret enemy : turretArrayList)
            for( Bullet b : enemy.turretBulletArrayList)
                allEnemyBullets.add(b);

        return allEnemyBullets;
    }

}
