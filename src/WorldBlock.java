import jig.Entity;
import jig.Vector;
import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;

public class WorldBlock extends Entity {
    private WorldBlockType blockType;
    private Vector         blockWorldPosition;
    private String         blockTexture;
    private boolean        blockAnimation;
    private int            horizontalIndex;
    private int            verticalIndex;

    public WorldBlock(WorldBlockType blockType, int hIndex, int vIndex, String blockTexture) {
        this.blockType       = blockType;
        this.horizontalIndex = hIndex;
        this.verticalIndex   = vIndex;
        this.blockTexture    = blockTexture;
        this.blockAnimation  = false;

        Image i = ContraGame.getBlockTexture(blockTexture);
        if (i != null) {
            addImageWithBoundingBox(i);
            setScale(2.0f);
        }
        else {
            SpriteSheet ss = ContraGame.getBlockSpriteSheet(blockTexture);
            if (ss != null) {
                addAnimation(new Animation(ss, 0, 0, 1, 0, true, 150, true));
                setScale(2.0f);
                this.blockAnimation = true;
            }
        }
        setPosition( ContraGame.VIEWPORT.getViewPortOffsetTopLeft().getX() + hIndex * World.BLOCK_WIDTH  + World.BLOCK_HEIGHT/2.0f,
                     ContraGame.VIEWPORT.getViewPortOffsetTopLeft().getY() + vIndex * World.BLOCK_HEIGHT + World.BLOCK_WIDTH /2.0f );
    }

    public String getBlockTexture() {
        return blockTexture;
    }

    public WorldBlockType getBlockType() {
        return blockType;
    }

    public int getVerticalIndex() {
        return verticalIndex;
    }

    public int getHorizontalIndex() { return horizontalIndex; }

    public boolean isOnScreen()
    {
        if( this.getX() > -50.0f && this.getX() < ContraGame.VIEWPORT.getWidth() + 100.0f )
            return true;
        return  false;
    }

    public void render(final Graphics g)
    {
        if( isOnScreen() || blockAnimation )
            super.render(g);
    }

    public void update(GameContainer gc, StateBasedGame sbg, int delta )
    {
        setPosition( ContraGame.VIEWPORT.getViewPortOffsetTopLeft().getX() + horizontalIndex * World.BLOCK_WIDTH    + World.BLOCK_WIDTH /2.0f ,
                     ContraGame.VIEWPORT.getViewPortOffsetTopLeft().getY() + verticalIndex   * World.BLOCK_HEIGHT   + World.BLOCK_HEIGHT/2.0f);
    }
}