import jig.Vector;

import java.io.Serializable;

public class NetworkEntity implements Serializable
{
    enum NetworkEntityType
    {
        PLAYER,
        RUNNER,
        SNIPER,
        TURRET,
        BULLET
    }
    private NetworkEntityType  entityType;
    private PlayerDescriptor   playerDesc;
    private Vector             position;
    private TurretState         turretState;

    public NetworkEntity()
    {

    }

    public NetworkEntity( NetworkEntityType networkEntityType, PlayerDescriptor playerDesc, Vector worldPosition , TurretState turretState )
    {
        this.entityType = networkEntityType;
        this.playerDesc = playerDesc;
        this.position   = worldPosition;
        this.turretState = turretState;
    }

    public PlayerDescriptor getPlayerDesc() {
        return playerDesc;
    }
    public Vector getPosition() {
        return position;
    }
    public NetworkEntityType getEntityType() {
        return entityType;
    }
    public TurretState getTurretState() {
        return turretState;
    }
}
