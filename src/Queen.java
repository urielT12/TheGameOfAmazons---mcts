import java.awt.*;

public class Queen extends Piece
{
    private Color color;
    public Queen(Color color,Position p)
    {
            super(p);
            this.color = color;

    }

    public Queen()
    {
        super(new Position(0,0));
    }

    @Override
    public boolean same(Queen q)
    {
        return true;
    }

    @Override
    public Position getP() {
        return super.getP();
    }

    @Override
    public boolean same(Block q)
    {
        return false;
    }

    public Color getColor()
    {
        return color;
    }


    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Queen other = (Queen) obj;
        return this.color == other.color && getP().equals(other.getP());
    }

    @Override
    public String toString() {
        return this.getP().toString();
    }
}


