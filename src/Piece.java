public abstract class Piece implements IsTheSame
{

    private Position p;

    public Piece(Position p)
    {
            this.p = p;
            this.p.setY(p.getY());
            this.p.setX(p.getX());

    }

    public Position getP() {
        return p;
    }


    public void setP(Position p)
    {
        this.p.setX(p.getX());
        this.p.setY(p.getY());
    }
}
