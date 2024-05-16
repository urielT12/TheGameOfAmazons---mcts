import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;

public class Model {
    private Color players_turn_color;

    public Board b;

    private HashSet<Queen> Queens;


    public Model() {
        this.players_turn_color = Color.white;
        this.b = new Board();
        Queens = new HashSet<>();
        Queens.add(new Queen(Color.black, new Position(3, 0)));
        Queens.add(new Queen(Color.black, new Position(0, 3)));
        Queens.add(new Queen(Color.black, new Position(0, 6)));
        Queens.add(new Queen(Color.black, new Position(3, 9)));
        Queens.add(new Queen(Color.white, new Position(6, 0)));
        Queens.add(new Queen(Color.white, new Position(6, 9)));
        Queens.add(new Queen(Color.white, new Position(9, 3)));
        Queens.add(new Queen(Color.white, new Position(9, 6)));

    }

    public Model copy() {
        Model newModel = new Model();
        newModel.b = this.b.copy();

        newModel.getQueens().clear();

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (b.getGameBoard()[i][j] instanceof Queen)
                    newModel.getQueens().add(new Queen(b.getQueen(new Position(i, j)).getColor(), new Position(i, j)));
            }
        }

        newModel.setPlayers_turn_color(this.getPlayers_turn_color());
        return newModel;
    }

    public void setPlayers_turn_color(Color players_turn_color) {
        this.players_turn_color = players_turn_color;
    }

    public boolean isTerminal() {
        if (isGameOverFor(Color.black) || isGameOverFor(Color.white))
            return true;
        return false;
    }

    public Color getPlayers_turn_color() {
        return players_turn_color;
    }

    public void next_player_turn() {
        if (this.players_turn_color.equals(Color.white))
            this.players_turn_color = Color.black;
        else
            this.players_turn_color = Color.white;

    }

    public void movePlayer(Position q, Position dest) {
        Color color = Color.white;

        int old_x = q.getX();
        int old_y = q.getY();

        b.getGameBoard()[q.getX()][q.getY()] = null;

        int new_x = dest.getX();
        int new_y = dest.getY();
        for (Queen a : Queens) {
            if (a.getP().getX() == old_x && a.getP().getY() == old_y) {
                a.setP(new Position(new_x, new_y));
                color = a.getColor();
            }

        }
        Queen queen = new Queen(color, dest);

        b.getGameBoard()[new_x][new_y] = queen;


    }

    public void place_a_block(Position p) {
        b.getGameBoard()[p.getX()][p.getY()] = new Block(p);
    }


    public boolean isGameOverFor(Color color) {
        for (Queen q : getQueens()) {
            if (q.getColor().equals(color))
                if (!(getLegalMovesForSelected(q.getP()).size() == 1))
                    return false;
        }
        return true;
    }

    public ArrayList<Position> getLegalMovesForSelected(Position q) {
        int flag = 0;
        int i = 1;
        ArrayList<Position> legalMoves = new ArrayList<>();
        legalMoves.add(q);
        int currentRow = q.getX();
        int currentCol = q.getY();
        // Legal moves left
        while (currentCol - i >= 0 && flag == 0) {
            if (b.getGameBoard()[currentRow][currentCol - i] == null) {
                legalMoves.add(new Position(currentRow, currentCol - i));
            } else {
                flag = 1;
            }
            i++;
        }
        flag = 0;
        i = 1;
        while (currentCol + i <= 9 && flag == 0) {
            if (b.getGameBoard()[currentRow][currentCol + i] == null) {
                legalMoves.add(new Position(currentRow, currentCol + i));
            } else {
                flag = 1;
            }
            i++;
        }
        flag = 0;
        i = 1;
        while (currentRow - i >= 0 && flag == 0) {
            if (b.getGameBoard()[currentRow - i][currentCol] == null) {
                legalMoves.add(new Position(currentRow - i, currentCol));
            } else {
                flag = 1;
            }
            i++;
        }
        flag = 0;
        i = 1;
        while (currentRow + i <= 9 && flag == 0) {
            if (b.getGameBoard()[currentRow + i][currentCol] == null) {
                legalMoves.add(new Position(currentRow + i, currentCol));
            } else {
                flag = 1;
            }
            i++;
        }
        flag = 0;
        i = 1;
        while (currentRow - i >= 0 && currentCol - i >= 0 && flag == 0) {
            if (b.getGameBoard()[currentRow - i][currentCol - i] == null) {
                legalMoves.add(new Position(currentRow - i, currentCol - i));
            } else {
                flag = 1;
            }
            i++;
        }
        flag = 0;
        i = 1;
        while (currentRow + i <= 9 && currentCol - i >= 0 && flag == 0) {
            if (b.getGameBoard()[currentRow + i][currentCol - i] == null) {
                legalMoves.add(new Position(currentRow + i, currentCol - i));
            } else {
                flag = 1;
            }
            i++;
        }
        flag = 0;
        i = 1;
        while (currentRow - i >= 0 && currentCol + i <= 9 && flag == 0) {
            if (b.getGameBoard()[currentRow - i][currentCol + i] == null) {
                legalMoves.add(new Position(currentRow - i, currentCol + i));
            } else {
                flag = 1;
            }
            i++;
        }
        flag = 0;
        i = 1;
        while (currentRow + i <= 9 && currentCol + i <= 9 && flag == 0) {
            if (b.getGameBoard()[currentRow + i][currentCol + i] == null) {
                legalMoves.add(new Position(currentRow + i, currentCol + i));
            } else {
                flag = 1;
            }
            i++;
        }

        return legalMoves;
    }



    private boolean isValidPosition(int row, int col) {
        return row >= 0 && row <= 9 && col >= 0 && col <= 9; // Assuming board size is 10x10

    }


    public Queen get_selected_Queen(JButton[][] buttons) {
        for (int i = 0; i < Board.ROWS; i++) {
            for (int j = 0; j < Board.COLS; j++) {
                if (b.getGameBoard()[i][j] != null)
                    if (buttons[i][j].getBackground().equals(Color.GREEN) && b.getGameBoard()[i][j].same(new Queen()))
                        return (Queen) b.getGameBoard()[i][j];
            }
        }
        return null;
    }

    public String get_Place(int row, int col) {
        String cha = (String) ((char) (row + 65) + "");
        String num = (String) ((col + 1) + "");
        return cha + num;
    }


    public PartOfMove HandleClick(Position from, Color color) {

        if (IsMoveQueen(from, color))
            return PartOfMove.MoveQueen;

        if (IsBlock(from, color)) {
            return PartOfMove.PlaceABlock;
        }
        if (IspaintMoves(from, color))
            return PartOfMove.SelectQueen;

        if (IsCancelSelection(from, color))
            return PartOfMove.UnSelectQueen;

        return PartOfMove.NothingSelected;
    }

    public boolean IsMoveQueen(Position p, Color Color) {
        if ((b.getGameBoard()[p.getX()][p.getY()] == null) && ((Color.equals(java.awt.Color.green)))) {
            return true;
        }
        return false;
    }

    public boolean IspaintMoves(Position p, Color Color) {
        if ((b.getGameBoard()[p.getX()][p.getY()] != null))
            if ((b.getGameBoard()[p.getX()][p.getY()].same(new Queen()))
                    && (b.getQueen(p).getColor().equals(players_turn_color))
                    && !((Color.equals(java.awt.Color.green))) && !((Color.equals(java.awt.Color.yellow))))
                return true;
        return false;
    }

    public boolean IsCancelSelection(Position p, Color Color) {
        if ((b.getGameBoard()[p.getX()][p.getY()] != null))
            if ((b.getGameBoard()[p.getX()][p.getY()].same(new Queen()))
                    && ((Color.equals(java.awt.Color.green))))
                return true;
        return false;
    }

    public boolean IsBlock(Position p, Color Color) {
        if ((b.getGameBoard()[p.getX()][p.getY()] == null) && (Color.equals(java.awt.Color.yellow))) {
            place_a_block(p);
            return true;
        }
        return false;
    }

    public HashSet<Queen> getQueens() {
        return Queens;
    }

    public void makeMoveForAI(ArrayList<Position> p) {

        /*this.Queens.remove(new Queen(Color.white,p.get(2)));
        this.Queens.add(new Queen(Color.white,p.get(1)));*/

        movePlayer(p.get(2), p.get(1));

        place_a_block(p.get(0));

        next_player_turn();
    }

    //public

    public boolean isDefensiveMove(ArrayList<Position> move)
    {
        Model newModel = this.copy();
        newModel.makeMoveForAI(move);
        if(newModel.getLegalMovesForSelected(move.get(1)).size() >= this.getLegalMovesForSelected(move.get(2)).size())
            return true;
        return false;
    }

    public boolean isAdjacentToOpponentQueen(Position p) {
        // Iterate over all neighboring cells around the given position, including diagonals
        for (int r = Math.max(0, p.getX() - 1); r <= Math.min(p.getX() + 1, b.ROWS - 1); r++) {
            for (int c = Math.max(0, p.getY() - 1); c <= Math.min(p.getY() + 1, b.COLS - 1); c++) {
                // Check that the current cell is not the same as the given position
                if (r != p.getX() || c != p.getY()) {
                    // Check if the current cell is within the boundaries of the board
                    if (r >= 0 && r < b.ROWS && c >= 0 && c < b.COLS) {
                        // Check if there is a queen in the current cell and it's not the same color as the current player
                        if (b.getGameBoard()[r][c] instanceof Queen && ((Queen) b.getGameBoard()[r][c]).getColor() == Color.white) {
                            return true;
                        }
                    }
                }
            }
        }
        // If no queen of a different color was found adjacent to the given position, return false
        return false;
    }

    public boolean isAggressiveMove(ArrayList<Position> move) {
        if(isAdjacentToOpponentQueen(move.get(0)))
            return true;
        return false;
    }

    public boolean checkIfMoveIsRelevante(ArrayList<Position> move)
    {

        // Check if the move cSreates a strong defensive position
        boolean isDefensive = isDefensiveMove(move);

        // Check if the move puts pressure on the opponent's position
        boolean isAggressive = isAggressiveMove(move);

        // Evaluate the overall quality of the move based on defensive and aggressive factors
        if (isDefensive && isAggressive) {
            return true; // Good move
        } else if (isDefensive) {
            return true; // Decent move
        } else {
            return false; // Poor move
        }

    }

    public int checkWaysToMove(Position p)
    {
        int Count = 0;
        for (int r = Math.max(0, p.getX() - 1); r <= Math.min(p.getX() + 1, b.ROWS - 1); r++) {
            for (int c = Math.max(0, p.getY() - 1); c <= Math.min(p.getY() + 1, b.COLS - 1); c++) {
                // Check that the current cell is not the same as the given position
                if (r != p.getX() || c != p.getY()) {
                    // Check if the current cell is within the boundaries of the board
                    if (r >= 0 && r < b.ROWS && c >= 0 && c < b.COLS) {
                        // Check if there is a queen in the current cell and it's not the same color as the current player
                        if (b.getGameBoard()[r][c] == null)
                        {
                            Count++;
                        }
                    }
                }
            }
        }
        // If no queen of a different color was found adjacent to the given position, return false
        return Count;
    }

    public boolean IsAggresive(Position p)
    {
        int CountBlocks = 0;
        int CountQueens = 0;
        for (int r = Math.max(0, p.getX() - 1); r <= Math.min(p.getX() + 1, b.ROWS - 1); r++) {
            for (int c = Math.max(0, p.getY() - 1); c <= Math.min(p.getY() + 1, b.COLS - 1); c++) {
                // Check that the current cell is not the same as the given position
                if (r != p.getX() || c != p.getY()) {
                    // Check if the current cell is within the boundaries of the board
                    if (r >= 0 && r < b.ROWS && c >= 0 && c < b.COLS) {
                        // Check if there is a queen in the current cell and it's not the same color as the current player
                        if (b.getGameBoard()[r][c] instanceof Queen && ((Queen) b.getGameBoard()[r][c]).getColor() == Color.white)
                        {
                            CountQueens++;
                        }
                        if (b.getGameBoard()[r][c] instanceof Block)
                        {
                            CountBlocks++;
                        }
                    }
                }
            }
        }
        if(CountQueens >= 1 && CountBlocks >= 2)
            return true;
        return false;
    }
}
