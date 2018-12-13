import java.io.Serializable;

public class ClientPacket implements Serializable
{
    public enum ClientHorizontalControl {
        NONE,
        LEFT,
        RIGHT
    }

    public enum ClientVerticalControl {
        NONE,
        UP,
        DOWN
    }

    private ClientHorizontalControl clientHorizontalControl;
    private ClientVerticalControl   clientVerticalControl;
    private boolean clientJump;
    private boolean clientFire;

    public ClientPacket()
    {
        this( ClientHorizontalControl.NONE, ClientVerticalControl.NONE, false, false );
    }

    public ClientPacket(ClientHorizontalControl clientHorizontalControl, ClientVerticalControl clientVerticalControl,
                        boolean clientJump, boolean clientFire)
    {
        this.clientHorizontalControl = clientHorizontalControl;
        this.clientVerticalControl   = clientVerticalControl;
        this.clientFire              = clientFire;
        this.clientJump              = clientJump;
    }

    public ClientHorizontalControl getClientHorizontalControl() {
        return clientHorizontalControl;
    }
    public ClientVerticalControl getClientVerticalControl() {
        return clientVerticalControl;
    }
    public boolean isClientJump() {
        return clientJump;
    }
    public boolean isClientFire() {
        return clientFire;
    }

    public void setClientHorizontalControl(ClientHorizontalControl clientHorizontalControl) {
        this.clientHorizontalControl = clientHorizontalControl;
    }
    public void setClientVerticalControl(ClientVerticalControl clientVerticalControl) {
        this.clientVerticalControl = clientVerticalControl;
    }
    public void setClientJump(boolean clientJump) {
        this.clientJump = clientJump;
    }
    public void setClientFire(boolean clientFire) {
        this.clientFire = clientFire;
    }
}
