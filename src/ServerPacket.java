import jig.Vector;

import java.io.Serializable;

/*
* The packet that the server expects from the client.
* */
public class ServerPacket implements Serializable
{
    private PlayerDescriptor localPlayerDescriptor;
    private Vector           localPlayerWorldPosition;

    private PlayerDescriptor serverPlayerDescriptor;
    private Vector           serverPlayerWorldPosition;

    private NetworkEntityList serverEntityList;

    private int                 numberOfLives;

    public ServerPacket(PlayerDescriptor localPlayerDescriptor, Vector localPlayerWorldPosition,
                        PlayerDescriptor serverPlayerDescriptor, Vector serverPlayerWorldPosition,
                        NetworkEntityList     serverEnemyManager , int numberOfLives)
    {
        this.localPlayerDescriptor        = localPlayerDescriptor;
        this.localPlayerWorldPosition     = localPlayerWorldPosition;
        this.serverPlayerDescriptor       = serverPlayerDescriptor;
        this.serverPlayerWorldPosition    = serverPlayerWorldPosition;
        this.serverEntityList           = serverEnemyManager;
        this.numberOfLives                =  numberOfLives;
    }

    public Vector getLocalPlayerWorldPosition() {
        return localPlayerWorldPosition;
    }

    public PlayerDescriptor getLocalPlayerDescriptor() {
        return localPlayerDescriptor;
    }

    public PlayerDescriptor getServerPlayerDescriptor() {
        return serverPlayerDescriptor;
    }

    public Vector getServerPlayerWorldPosition() {
        return serverPlayerWorldPosition;
    }

    public NetworkEntityList getServerEntityList() {
        return serverEntityList;
    }

    public int getNumberOfLives() {
        return numberOfLives;
    }
};