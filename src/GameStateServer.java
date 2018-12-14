import jig.Vector;
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 */
class GameStateServer extends BasicGameState {
    private int frameCount = 0;
    private int teamLivesNumber = 5;
    private World world;
    private Player localPlayer;
    private NetworkPlayer networkPlayer;
    private Server server;
    private EnemyManager enemyManager;
    private NetworkEntityList networkEntityList;

    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        world = new World();
        world.loadWorldFromFile("one");

    }

    @Override
    public void enter(GameContainer container, StateBasedGame game) {
        ContraGame cg = (ContraGame) game;
        container.setSoundOn(true);

        server = new Server(9999);
        try {
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
        localPlayer = new Player(world, Player.Type.BLUE);
        networkPlayer = new NetworkPlayer(world, localPlayer, server);
        enemyManager = new EnemyManager(world, localPlayer, networkPlayer);


    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        world.render(g);
        localPlayer.render(g);
        networkPlayer.render(g);
        enemyManager.render(gc, sbg, g);
        g.drawString("Lives Left:" + getTeamLivesNumber(), 10, 30);
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
        frameCount += 1;
        world.update(gc, sbg, delta);
        localPlayer.update(gc, sbg, delta);
        networkPlayer.update(gc, sbg, delta);

        checkPlayerBulletCollsions(localPlayer.bulletArrayList);
        checkPlayerBulletCollsions(networkPlayer.bulletArrayList);

        checkIfEnemyHitPlayer();

        enemyManager.update(gc, sbg, delta);


        if (frameCount % 10 == 0)
            return;

        networkEntityList = null;
        networkEntityList = new NetworkEntityList();
        networkEntityList.addSnipers(enemyManager.getSniperArrayList());
        networkEntityList.addRunners(enemyManager.getRunnerArrayList());
        networkEntityList.addTurrets(enemyManager.getTurretArrayList());
        networkEntityList.addBullets(enemyManager.getAllEnemyBullets());
        networkEntityList.addBullets(localPlayer.bulletArrayList);
        networkEntityList.addBullets(networkPlayer.bulletArrayList);
//        networkEntityList.addPlayer(localPlayer);
//        networkEntityList.addPlayer(networkPlayer);
        // System.out.println(enemyManager.getAllEnemyBullets().size());
        server.writeToClient(new ServerPacket(new PlayerDescriptor(networkPlayer.playerDesc), new Vector(networkPlayer.playerPosition),
                new PlayerDescriptor(localPlayer.playerDesc), new Vector(localPlayer.playerPosition), networkEntityList, getTeamLivesNumber()));

    }

    @Override
    public int getID() {
        return ContraGame.HOST;
    }

    public int getTeamLivesNumber() {
        return teamLivesNumber;
    }

    public void setTeamLivesNumber(int teamLivesNumber) {
        this.teamLivesNumber = teamLivesNumber;
    }

    public void decrementTeamLivesNumber() {
        this.teamLivesNumber--;
    }

    public void checkPlayerBulletCollsions(ArrayList<Bullet> playerBullets) {
        for (Bullet bullet : playerBullets) {
            Iterator<EnemyTurret> iter1 = this.enemyManager.getTurretArrayList().iterator();
            for (; iter1.hasNext(); ) {
                EnemyTurret turret = iter1.next();

                if (bullet.collides(turret) != null) {
                    bullet.isBulletDead = true;
                    turret.decrementLives();

                    if (turret.getLivesLeft() <= 0)
                        iter1.remove();

                    return;
                }


            }

            Iterator<EnemySniper> iter = this.enemyManager.getSniperArrayList().iterator();
            for (; iter.hasNext(); ) {
                EnemySniper enemy = iter.next();

                if (bullet.collides(enemy) != null) {
                    enemy.decrementLives();
                    bullet.isBulletDead = true;

                    if (enemy.getLivesLeft() <= 0)
                        iter.remove();

                    return;
                }


            }
        }
    }


    public void checkIfEnemyHitPlayer(){
        for (Bullet bullet : enemyManager.getAllEnemyBullets()) {
            if (bullet.collides(networkPlayer) != null || bullet.collides(localPlayer) != null) {
                teamLivesNumber--;
                bullet.isBulletDead = true;
            }
        }

    }

}