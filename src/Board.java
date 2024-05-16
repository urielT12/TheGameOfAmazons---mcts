import java.awt.*;

public class Board
{
    public static final int ROWS = 10;
    public static final int COLS = 10;

    private Piece[][] GameBoard;
    public Board()
    {
        GameBoard = new Piece[][]{
                {null, null, null, new Queen(Color.black,new Position(0,3)), null, null,new Queen(Color.black,new Position(0,6)), null, null, null },
                { null, null, null, null, null, null, null, null, null, null },
                { null, null, null, null, null, null, null, null, null,	null },
                { new Queen(Color.black,new Position(3,0)), null, null, null, null, null, null, null, null, new Queen(Color.black,new Position(3,9)) },
                { null, null, null, null, null, null, null, null, null,	null },
                { null, null, null, null, null, null, null, null, null,	null },
                { new Queen(Color.white,new Position(6,0)), null, null, null, null, null, null, null, null,new Queen(Color.white,new Position(6,9)) },
                { null, null, null, null, null, null, null, null, null,	null },
                { null, null, null, null, null, null, null, null, null,	null },
                { null, null, null,new Queen(Color.white,new Position(9,3)), null, null, new Queen(Color.white,new Position(9,6)), null, null, null } };
    }

    public boolean IsQueen(Position p)
    {
        if(this.GameBoard[p.getX()][p.getY()] == null)
            return false;
        return true;

    }

    public Piece[][] getGameBoard() {
        return GameBoard;
    }

    public Queen getQueen(Position p)
    {

        return (Queen)this.GameBoard[p.getX()][p.getY()];
    }

    public Board copy()
    {
        Board newBoard = new Board();
        for (int i = 0 ; i < 10 ; i++)
        {
            for (int j = 0 ; j < 10 ; j++)
            {
                newBoard.getGameBoard()[i][j] = this.GameBoard[i][j];
            }
        }
        return  newBoard;
    }




}
