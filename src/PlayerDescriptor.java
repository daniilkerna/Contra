import java.io.Serializable;

public class PlayerDescriptor implements Serializable
{
    public enum State
    {
        IDLE,
        RUNNING,
        JUMPING,
        SWIMMING
    }

    public enum Movement
    {
        NONE,
        LEFT,
        RIGHT
    }

    public enum HorizontalFacingDirection
    {
        LEFT,
        RIGHT
    }

    public enum VerticalFacingDirection
    {
        NONE,
        UP,
        DOWN
    }

    public PlayerDescriptor()
    {
        this.state               = State.IDLE;
        this.movement            = Movement.NONE;
        this.hfd   = HorizontalFacingDirection.RIGHT;
        this.vfd   = VerticalFacingDirection.NONE;
    }


    public PlayerDescriptor( PlayerDescriptor pd )
    {
        this.state               = pd.state;
        this.movement            = pd.movement;
        this.hfd                 = pd.hfd;
        this.vfd                 = pd.vfd;
    }

    public PlayerDescriptor( State state, Movement movement, HorizontalFacingDirection hfd, VerticalFacingDirection vfd )
    {
        this.state               = state;
        this.movement            = movement;
        this.hfd = hfd;
        this.vfd   = vfd;
    }
    public State                       state;
    public Movement                    movement;
    public HorizontalFacingDirection   hfd;
    public VerticalFacingDirection     vfd;
};