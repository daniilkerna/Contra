import java.io.Serializable;
import java.util.ArrayList;

public class NetworkEntityList implements Serializable
{
    ArrayList<NetworkEntity> entities;
    public NetworkEntityList( )
    {
        entities = new ArrayList<>();
    }

    public ArrayList<NetworkEntity> getEntities() {
        return entities;
    }

    public void addNetworkEntity( NetworkEntity ne )
    {
        entities.add( ne );
    }

    public void setEntities(ArrayList<NetworkEntity> entities) {
        this.entities = entities;
    }

    public void addSnipers(ArrayList <EnemySniper>  enemySnipers){
        for (EnemySniper p : enemySnipers){
            entities.add(new NetworkEntity(NetworkEntity.NetworkEntityType.SNIPER, p.getSniperDesc(), p.getSniperPosition(), null , p.getCurrentAnimation() ) );
        }
    }

    public void addRunners(ArrayList <EnemyRunner>  enemyRunners){
        for (EnemyRunner p : enemyRunners){
            entities.add(new NetworkEntity(NetworkEntity.NetworkEntityType.RUNNER, p.playerDesc, p.getPlayerPosition(), null , null ));
        }
    }

    public void addTurrets(ArrayList <EnemyTurret>  enemyTurrets){
        for (EnemyTurret p : enemyTurrets){
            entities.add(new NetworkEntity(NetworkEntity.NetworkEntityType.TURRET, null, p.getTurretWorldPos() , p.getTurretState() , null));
        }
    }

    public void addBullets(ArrayList <Bullet>  bullets){
        for (Bullet b : bullets){
            entities.add(new NetworkEntity(NetworkEntity.NetworkEntityType.BULLET, null, b.getBulletWorldPos() , null  , null));
        }
    }

    public void addPlayer(Player p1){
        entities.add(new NetworkEntity(NetworkEntity.NetworkEntityType.PLAYER, p1.playerDesc, p1.getPosition() , null  , null));
    }


}
