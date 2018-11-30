import jig.Entity;
import jig.Vector;
import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;

public class WorldBlock extends Entity {
    private WorldBlockType blockType;
    private Vector         blockWorldPosition;
    private String         blockTexture;
    private int            horizontalIndex;
    private int            verticalIndex;

    public WorldBlock(WorldBlockType blockType, int hIndex, int vIndex, String blockTexture) {
        this.blockType       = blockType;
        this.horizontalIndex = hIndex;
        this.verticalIndex   = vIndex;
        this.blockTexture    = blockTexture;

        Image i = ContraGame.getBlockTexture(blockTexture);
        if (i != null) {
            addImageWithBoundingBox(i);
            setScale(2.0f);
            if( blockType == WorldBlockType.PLATFORM ) {
                setCoarseGrainedMinY(-i.getHeight()/2.0f);
                setCoarseGrainedMaxY( 0.0f );
            }
        }
        setPosition( ContraGame.VIEWPORT.getViewPortOffsetTopLeft().getX() + hIndex * World.BLOCK_WIDTH  + World.BLOCK_HEIGHT/2.0f,
                     ContraGame.VIEWPORT.getViewPortOffsetTopLeft().getY() + vIndex * World.BLOCK_HEIGHT + World.BLOCK_WIDTH /2.0f );
    }

    public WorldBlockType getBlockType() {
        return blockType;
    }

    public int getVerticalIndex() {
        return verticalIndex;
    }

    public int getHorizontalIndex() { return horizontalIndex; }

    public void render(final Graphics g)
    {
        super.render(g);
        g.setColor(Color.red);
        g.drawRect(  getCoarseGrainedMinX(), getCoarseGrainedMinY(), getCoarseGrainedWidth(), getCoarseGrainedHeight() );
    }

    public void update(GameContainer gc, StateBasedGame sbg, int delta )
    {
        setPosition( ContraGame.VIEWPORT.getViewPortOffsetTopLeft().getX() + horizontalIndex * World.BLOCK_WIDTH    + World.BLOCK_WIDTH /2.0f ,
                     ContraGame.VIEWPORT.getViewPortOffsetTopLeft().getY() + verticalIndex   * World.BLOCK_HEIGHT   + World.BLOCK_HEIGHT/2.0f);
    }
}