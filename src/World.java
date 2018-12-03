import jig.Vector;
import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;

import java.io.*;
import java.util.*;

public class World
{
    public static final float GRAVITY      = 0.008f;
    public static final int   BLOCK_WIDTH  = 32; // IN PX
    public static final int   BLOCK_HEIGHT = 32; // IN PX
    public static final int   DEFAULT_WORLD_BLOCK_WIDTH  = 220; // In block size
    public static final int   DEFAULT_WORLD_BLOCK_HEIGHT =  17; // In block size

    /*
     *
     */
    private WorldBlock worldBlocks[][];
    private int        blockVerticalCount;
    private int        blockHorizontalCount;
    private float      worldWidth;
    private float      worldHeight;

    /*
     *
     */
    public WorldBlock selectedScreenBlock = null;

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

        ContraGame.VIEWPORT.shiftViewPortOffset( new Vector( 0, (ContraGame.VIEWPORT.getHeight() - this.worldHeight)/2) );

        for( int x = 0; x < blockHorizontalCount; x++ ) {
            for( int y = 0; y < blockVerticalCount; y++ ) {
                worldBlocks[x][y] = new WorldBlock( WorldBlockType.NONE, x, y, null  );
            }
        }
    }

    public void loadWorldFromFile( String filedir )
    {
        BufferedReader in = null;

        try {
            FileReader fstream = new FileReader("src/resource/level/"+ filedir + ".lvl");
            in = new BufferedReader(fstream);

            String line = in.readLine();

            System.out.println(line);

            Scanner scanner = new Scanner(line);

            int w = (new Scanner( scanner.next() ).nextInt() );
            int h = (new Scanner( scanner.next() ).nextInt() );

            while((line = in.readLine()) != null )
            {
                scanner = new Scanner(line);
                int x = (new Scanner( scanner.next() ).nextInt() );
                int y = (new Scanner( scanner.next() ).nextInt() );
                String type    = (new Scanner( scanner.next() ).next() );
                String texture = (new Scanner( scanner.next() ).next() );

                if( type.equals("PLATFORM") )
                    this.worldBlocks[x][y] = new WorldBlock( WorldBlockType.PLATFORM, x, y, texture );
                else
                    this.worldBlocks[x][y] = new WorldBlock( WorldBlockType.NONE, x, y, texture );
            }
            in.close();
        }

        catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public void saveWorldToFile( String levelname )
    {
        BufferedWriter out = null;

        try {
            FileWriter fstream = new FileWriter("src/resource/level/" + levelname + ".lvl", false);
            out = new BufferedWriter(fstream);

            out.write(String.format("%d %d\n", this.blockHorizontalCount, this.blockVerticalCount ) );

            for( int i = 0; i < blockHorizontalCount; i++ )
            {
                for( int j = 0; j < this.blockVerticalCount; j++ )
                {
                    WorldBlock wb = worldBlocks[i][j];
                    out.write(String.format( "%d %d %s %s\n", i,j, wb.getBlockType().toString(), wb.getBlockTexture() ) );
                }
            }
            out.close();
        }

        catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }

    }

    public void updateBlock( int hIndex, int vIndex, WorldBlockType wbt, String texture )
    {
        worldBlocks[hIndex][vIndex] = new WorldBlock( wbt ,hIndex, vIndex, texture );
    }

    public void update( GameContainer gc, StateBasedGame sbg, int delta )
    {
        Input input = gc.getInput();
        int mx = input.getMouseX();
        int my = input.getMouseY();

        this.selectedScreenBlock = getScreenBlock( new Vector( mx, my ) );

        if( input.isKeyPressed( Input.KEY_F5 ) ) {
            this.saveWorldToFile( "one" );
        }

        if( input.isMouseButtonDown( Input.MOUSE_LEFT_BUTTON) )
        {
            this.worldBlocks[selectedScreenBlock.getHorizontalIndex()][selectedScreenBlock.getVerticalIndex()] =
                    new WorldBlock( WorldBlockType.NONE, selectedScreenBlock.getHorizontalIndex(), selectedScreenBlock.getVerticalIndex(), null );
        }

        if( input.isKeyDown( Input.KEY_3 ) )
        {
            this.worldBlocks[selectedScreenBlock.getHorizontalIndex()][selectedScreenBlock.getVerticalIndex()] =
                    new WorldBlock( WorldBlockType.NONE, selectedScreenBlock.getHorizontalIndex(), selectedScreenBlock.getVerticalIndex(), "CLIFF_TL" );

            this.worldBlocks[selectedScreenBlock.getHorizontalIndex()+1][selectedScreenBlock.getVerticalIndex()] =
                    new WorldBlock( WorldBlockType.NONE, selectedScreenBlock.getHorizontalIndex()+1, selectedScreenBlock.getVerticalIndex(), "CLIFF_TR" );
        }


        if( input.isKeyDown( Input.KEY_6 ) )
        {
            this.worldBlocks[selectedScreenBlock.getHorizontalIndex()][selectedScreenBlock.getVerticalIndex()] =
                    new WorldBlock( WorldBlockType.NONE, selectedScreenBlock.getHorizontalIndex(), selectedScreenBlock.getVerticalIndex(), "BOSS" );
        }


        if( input.isKeyDown( Input.KEY_8 ) )
        {
            this.worldBlocks[selectedScreenBlock.getHorizontalIndex()][selectedScreenBlock.getVerticalIndex()] =
                    new WorldBlock( WorldBlockType.NONE, selectedScreenBlock.getHorizontalIndex(), selectedScreenBlock.getVerticalIndex(), "WATERFALL_ANIMATION_TOP" );
        }


        if( input.isKeyDown( Input.KEY_1 ))
        {
            this.worldBlocks[selectedScreenBlock.getHorizontalIndex()][selectedScreenBlock.getVerticalIndex()] =
                    new WorldBlock( WorldBlockType.PLATFORM, selectedScreenBlock.getHorizontalIndex(), selectedScreenBlock.getVerticalIndex(), "GRASS_PLATFORM_LEFT" );

            this.worldBlocks[selectedScreenBlock.getHorizontalIndex()+1][selectedScreenBlock.getVerticalIndex()] =
                    new WorldBlock( WorldBlockType.PLATFORM, selectedScreenBlock.getHorizontalIndex()+1, selectedScreenBlock.getVerticalIndex(), "GRASS_PLATFORM_RIGHT" );
        }

        if( input.isKeyDown( Input.KEY_2 ))
        {
            this.worldBlocks[selectedScreenBlock.getHorizontalIndex()][selectedScreenBlock.getVerticalIndex()] =
                    new WorldBlock( WorldBlockType.PLATFORM, selectedScreenBlock.getHorizontalIndex(), selectedScreenBlock.getVerticalIndex(), "BRIDGE_PLATFORM_LEFT" );

            this.worldBlocks[selectedScreenBlock.getHorizontalIndex()+1][selectedScreenBlock.getVerticalIndex()] =
                    new WorldBlock( WorldBlockType.PLATFORM, selectedScreenBlock.getHorizontalIndex()+1, selectedScreenBlock.getVerticalIndex(), "BRIDGE_PLATFORM_RIGHT" );
        }

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
                if( wb == selectedScreenBlock ) {
                    g.setColor(Color.red);
                    g.drawRect(selectedScreenBlock.getX() - World.BLOCK_WIDTH / 2.0f, selectedScreenBlock.getY() - World.BLOCK_HEIGHT / 2.0f, World.BLOCK_WIDTH, World.BLOCK_HEIGHT);
                }
                wb.render(g);
            }
        }
    }

    public WorldBlock getIndexedBlock( int x, int y )
    {
        if((x >= 0) && (x < blockHorizontalCount) && ((y > 0) && (y < blockVerticalCount)))
            return  worldBlocks[x][y];
        return null;
    }

    public WorldBlock getScreenBlock( Vector screenPos )
    {
        int x = (int)((screenPos.getX()) - ContraGame.VIEWPORT.getViewPortOffsetTopLeft().getX())/BLOCK_WIDTH;
        int y = (int)((screenPos.getY()) - ContraGame.VIEWPORT.getViewPortOffsetTopLeft().getY())/BLOCK_HEIGHT;
        if((x >= 0) && (x < blockHorizontalCount) && ((y >= 0) && (y < blockVerticalCount)))
            return worldBlocks[x][y];
        else
            return null;
    }

    public WorldBlock getWorldBlock( Vector worldPos )
    {
        int x = (int)(worldPos.getX()/World.BLOCK_WIDTH);
        int y = (int)(worldPos.getY()/World.BLOCK_HEIGHT);

        if( x >= 0 && x <= blockVerticalCount && y > 0 && y < blockHorizontalCount)
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
}
