import jig.Entity;
import jig.Vector;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import sun.security.provider.certpath.Vertex;

import javax.swing.text.View;

public class World
{
    public static final int DEFAULT_BLOCK_WIDTH  = 16; // IN PX
    public static final int DEFAULT_BLOCK_HEIGHT = 16; // IN PX
    public static final int DEFAULT_WORLD_BLOCK_WIDTH  = 200; // In block size
    public static final int DEFAULT_WORLD_BLOCK_HEIGHT =  48; // In block size

    /*
     *
     */
    private float blockWidth;
    private float blockHeight;
    private float worldWidth;
    private float worldHeight;
    private int   blockVerticalCount;
    private int   blockHorizontalCount;

    public World()
    {
        this( DEFAULT_BLOCK_WIDTH, DEFAULT_BLOCK_HEIGHT, DEFAULT_WORLD_BLOCK_WIDTH, DEFAULT_WORLD_BLOCK_HEIGHT );
    }

    public World( int horizontalBlockCount, int verticalBlockCount )
    {
        this( DEFAULT_BLOCK_WIDTH, DEFAULT_BLOCK_HEIGHT, horizontalBlockCount, verticalBlockCount );
    }

    public World( int blockWidth, int blockHeight, int horizontalBlockCount, int verticalBlockCount )
    {
        this.blockWidth           = blockWidth;
        this.blockHeight          = blockHeight;
        this.blockHorizontalCount = horizontalBlockCount;
        this.blockVerticalCount   = verticalBlockCount;
        this.worldWidth           = horizontalBlockCount*blockWidth;
        this.worldHeight          = verticalBlockCount  *blockHeight;
    }

    public void render( ViewPort vp, final Graphics g )
    {
        g.setColor(Color.cyan);
        for( int i = 0; i <= blockVerticalCount; i++ ) {
            Vector ls = new Vector( vp.getViewPortOffsetTopLeft().getX(),
                                 vp.getViewPortOffsetTopLeft().getY() + blockHeight * i );

            Vector le = new Vector( vp.getViewPortOffsetTopLeft().getX() + worldWidth,
                                    vp.getViewPortOffsetTopLeft().getY() + blockHeight * i);

            g.drawLine(ls.getX(), ls.getY(), le.getX(), le.getY());
        }

        for( int i = 0; i <= blockHorizontalCount; i++ ) {
            Vector ls = new Vector( vp.getViewPortOffsetTopLeft().getX() + blockWidth * i,
                                        vp.getViewPortOffsetTopLeft().getY() );

            Vector le = new Vector( vp.getViewPortOffsetTopLeft().getX() + blockWidth * i,
                                    vp.getViewPortOffsetTopLeft().getY() + worldHeight );


            g.drawLine( ls.getX(), ls.getY(), le.getX(), le.getY() );
        }

    }

}
