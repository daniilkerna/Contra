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



    private String              currentAnimation;

    public NetworkEntity()
    {

    }

    public NetworkEntity( NetworkEntityType networkEntityType, PlayerDescriptor playerDesc, Vector worldPosition , TurretState turretState, String currentAnimation )
    {
        this.entityType = networkEntityType;
        this.playerDesc = playerDesc;
        this.position   = worldPosition;
        this.turretState = turretState;
        this.currentAnimation  = currentAnimation;
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
    public String getCurrentAnimation() { return currentAnimation; }
}
