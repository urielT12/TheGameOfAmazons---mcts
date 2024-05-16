import java.awt.*;

public class Block extends Piece
{
    protected Color color;
    public Block(Position p)
    {
        super(p);
        this.color = Color.darkGray;
    }


    public Block()
    {
        super(new Position(0,0));
    }


    @Override
    public boolean same(Queen q)
    {
        return false;
    }

    @Override
    public boolean same(Block q)
    {
        return true;
    }


}
