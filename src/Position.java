public class Position
{
    private int x;
    private int y;

    public Position(int x,int y)
    {
        this.x = x;
        this.y = y;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public boolean equals(Position p)
    {
        if(this.x == p.getX() && this.y == p.getY())
            return true;
        return false;
    }

    @Override
    public String toString() {
        return
                "x=" + x +
                ", y=" + y ;
    }
}
