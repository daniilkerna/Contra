import jig.Vector;

public class ViewPort
{
    private float x;
    private float y;
    private float width;
    private float height;

    public ViewPort( int width, int height )
    {
        this( 0, 0, width, height );
    }

    public ViewPort( int x, int y, int width, int height )
    {
        this.x = x;
        this.y = y;
        this.width   = width;
        this.height = height;
    }

    public Vector getViewPortOffset()
    {
        return new Vector( x + width/2, y + height/2 );
    }

    public Vector getViewPortOffsetTopLeft()
    {
        return new Vector( x, y );
    }

    public void shiftViewPortOffset( Vector v )
    {
        this.x += v.getX();
        this.y += v.getY();
    }
}
