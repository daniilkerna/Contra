import jig.Entity;
import jig.Vector;
import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;

import javax.swing.text.View;
import java.util.ArrayList;
import java.util.HashMap;

public class World
{
    public static final float GRAVITY    = 0.01f;
    public static final int BLOCK_WIDTH  = 32; // IN PX
    public static final int BLOCK_HEIGHT = 32; // IN PX
    public static final int DEFAULT_WORLD_BLOCK_WIDTH  = 100; // In block size
    public static final int DEFAULT_WORLD_BLOCK_HEIGHT =  17; // In block size

    /*
     *
     */
    private WorldBlock worldBlocks[][];
    private ArrayList <WorldBlock> goldBlocksList;
    private int        blockVerticalCount;
    private int        blockHorizontalCount;
    private float      worldWidth;
    private float      worldHeight;

    /*
     *
     */
    public WorldBlock selectedScreenBlock = null;
    public WorldBlock selectedScreenBlock2 = null;

    public World()
    {
        this( DEFAULT_WORLD_BLOCK_WIDTH, DEFAULT_WORLD_BLOCK_HEIGHT );
    }

    public World( int horizontalBlockCount, int verticalBlockCount )
    {
        worldBlocks               = new WorldBlock[horizontalBlockCount][verticalBlockCount];
        this.blockHorizontalCount = horizontalBlockCount;
        this.blockVerticalCount   = verticalBlockCount;
        this.worldWidth           = horizontalBlockCount*BLOCK_WIDTH;
        this.worldHeight          = verticalBlockCount  *BLOCK_HEIGHT;

        goldBlocksList = new ArrayList<>(0);
        for( int x = 0; x < blockHorizontalCount; x++ ) {
            for( int y = 0; y < blockVerticalCount; y++ ) {
                if( (y % 4==0) && y > 0 && (((x % 5) == 0) || ((x % 5) == 1)) ){
                    WorldBlock temp = new WorldBlock( WorldBlockType.PLATFORM, x, y, "GOLD_BRICK"  );
                    worldBlocks[x][y] = temp;
                    goldBlocksList.add(temp);
                }
                else if ( x == 0 || x == 99 || y == 0 || y == 16){
                    WorldBlock temp = new WorldBlock( WorldBlockType.PLATFORM, x, y, "GOLD_BRICK"  );
                    worldBlocks[x][y] = temp;
                    goldBlocksList.add(temp);
                }

                else
                    worldBlocks[x][y] = new WorldBlock( WorldBlockType.NONE, x, y, null  );
            }
        }
    }

    public void update( GameContainer gc, StateBasedGame sbg, int delta )
    {
        //Input i  = gc.getInput();
        //selectedScreenBlock = getScreenBlock( new Vector( i.getMouseX(), i.getMouseY() ));

        for( int x = 0; x < blockHorizontalCount; x++ ) {
            for( int y = 0; y < blockVerticalCount; y++ ) {
                worldBlocks[x][y].update( gc, sbg, delta );
            }
        }
    }

    public void render( final Graphics g )
    {
        renderGrid( g );

        for( int x = 0; x < blockHorizontalCount; x++ ) {
            for( int y = 0; y < blockVerticalCount; y++ ) {
                WorldBlock wb = worldBlocks[x][y];
                if( wb == selectedScreenBlock || wb == selectedScreenBlock2 ) {
                    g.setColor(Color.red);
                    g.drawRect(selectedScreenBlock.getX() - World.BLOCK_WIDTH / 2, selectedScreenBlock.getY() - World.BLOCK_HEIGHT / 2, World.BLOCK_WIDTH, World.BLOCK_HEIGHT);
                }
                wb.render(g);
            }
        }
    }

    public WorldBlock getIndexedBlock( int x, int y )
    {
        if((x > 0) && (x < blockHorizontalCount) && ((y > 0) && (y < blockVerticalCount)))
            return  worldBlocks[x][y];
        return null;
    }

    public WorldBlock getScreenBlock( Vector screenPos )
    {
        int x = (int)((screenPos.getX()) - ContraGame.VIEWPORT.getViewPortOffsetTopLeft().getX())/BLOCK_WIDTH;
        int y = (int)((screenPos.getY()) - ContraGame.VIEWPORT.getViewPortOffsetTopLeft().getY())/BLOCK_HEIGHT;
        if((x > 0) && (x < blockHorizontalCount) && ((y > 0) && (y < blockVerticalCount)))
            return worldBlocks[x][y];
        else
            return null;
    }

    public WorldBlock getWorldBlock( Vector worldPos )
    {
        int x = (int)(worldPos.getX()/World.BLOCK_WIDTH);
        int y = (int)(worldPos.getY()/World.BLOCK_HEIGHT);

        if( x > 0 && x < blockVerticalCount && y > 0 && y < blockHorizontalCount)
            return worldBlocks[x][y];
        else
            return null;
    }

    public void renderGrid( final Graphics g  )
    {
        g.setColor(Color.cyan);
        for( int i = 0; i <= blockVerticalCount; i++ ) {
            Vector ls = new Vector( ContraGame.VIEWPORT.getViewPortOffsetTopLeft().getX(),
                    ContraGame.VIEWPORT.getViewPortOffsetTopLeft().getY() + BLOCK_HEIGHT * i );

            Vector le = new Vector( ContraGame.VIEWPORT.getViewPortOffsetTopLeft().getX() + worldWidth,
                    ContraGame.VIEWPORT.getViewPortOffsetTopLeft().getY() + BLOCK_HEIGHT * i);

            g.drawLine(ls.getX(), ls.getY(), le.getX(), le.getY());
        }

        for( int i = 0; i <= blockHorizontalCount; i++ ) {
            Vector ls = new Vector( ContraGame.VIEWPORT.getViewPortOffsetTopLeft().getX() + BLOCK_WIDTH * i,
                    ContraGame.VIEWPORT.getViewPortOffsetTopLeft().getY() );

            Vector le = new Vector( ContraGame.VIEWPORT.getViewPortOffsetTopLeft().getX() + BLOCK_WIDTH * i,
                    ContraGame.VIEWPORT.getViewPortOffsetTopLeft().getY() + worldHeight );


            g.drawLine( ls.getX(), ls.getY(), le.getX(), le.getY() );
        }
    }

    public ArrayList<WorldBlock> getGoldBlockList(){
        return goldBlocksList;
    }

}
