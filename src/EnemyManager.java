import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import java.util.ArrayList;

public class EnemyManager {

    private ArrayList<Player>       enemyArrayList;
    private ArrayList<enemyTurret>  turretArrayList;

    public EnemyManager(World world, Player player1){
        enemyArrayList = new ArrayList<>();

        //add Runners
        enemyArrayList.add(new enemyRunner(world , player1));


        //add snipers
        enemyArrayList.add(new EnemySniper(world , player1 , 634 , 438));
        enemyArrayList.add(new EnemySniper(world , player1 , 1275 , 476));
        enemyArrayList.add(new EnemySniper(world , player1 , 2554 , 255));
        enemyArrayList.add(new EnemySniper(world , player1 , 4864 , 255));
        enemyArrayList.add(new EnemySniper(world , player1 , 5346 , 470));
        enemyArrayList.add(new EnemySniper(world , player1 , 6285 , 345));
        enemyArrayList.add(new EnemySniper(world , player1 , 6952 , 210));



        //add Turrets
        turretArrayList = new ArrayList<>();
        turretArrayList.add(new enemyTurret(  795 ,  400 ,  player1));
        turretArrayList.add(new enemyTurret(  1495, 370 ,  player1));
        turretArrayList.add(new enemyTurret( 2430 , 320 ,  player1));
        turretArrayList.add(new enemyTurret( 3305 , 396 ,  player1));
        turretArrayList.add(new enemyTurret( 3690 , 396 ,  player1));
        turretArrayList.add(new enemyTurret( 4200 , 425 ,  player1));
        turretArrayList.add(new enemyTurret( 4452 , 200 ,  player1));
        turretArrayList.add(new enemyTurret( 5864 , 363 ,  player1));
        turretArrayList.add(new enemyTurret( 6260 , 490 ,  player1));
        turretArrayList.add(new enemyTurret( 6600 , 494 ,  player1));
    }


    public void render(GameContainer container, StateBasedGame game, final Graphics g){
        for (Player p : enemyArrayList)
            p.render(g);

        for (enemyTurret enemy : turretArrayList)
            enemy.render(g);
    }


    public void update(GameContainer container, StateBasedGame game,
                       int delta) throws SlickException {

        for (Player p : enemyArrayList)
            p.update(container , game , delta);

        for (enemyTurret enemy : turretArrayList)
            enemy.update(container , game , delta);
    }
}
