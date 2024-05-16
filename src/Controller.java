import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Controller
{
    private View view;
    private Model model;
    private MCTS ai;
    public String from = "",to = "";
    public Controller(View view,Model model)
    {
        this.view = view;
        this.model = model;
    }

    public void buttonClicked(JButton[][] allButtonsBoard, int row, int col) throws InterruptedException {
        if(!model.isGameOverFor(model.getPlayers_turn_color())) {
            PartOfMove p = model.HandleClick(new Position(row,col),allButtonsBoard[row][col].getBackground());
            //בחירה של מלכה
            if(p == PartOfMove.SelectQueen && !view.getIfButtonSelected()) {
                from = model.get_Place(row, col);
                view.paintGreenSlots(model.getLegalMovesForSelected(new Position(row, col)));
            }
            //בחירה של block
            if(p == PartOfMove.PlaceABlock) {
                view.displayUpdatedScreen(model.b);
                view.addMove(new Move(from,to,model.get_Place(row, col)),model.getPlayers_turn_color());
                model.next_player_turn();
                view.set_hows_turn(model.getPlayers_turn_color());
            }
            //ביטול בחירה של מלכה
            if(p == PartOfMove.UnSelectQueen)
                view.displayUpdatedScreen(model.b);
            //הזזה של מלכה
            if(p == PartOfMove.MoveQueen)
            {
                model.movePlayer(model.get_selected_Queen(view.allButtonsBoard).getP(),new Position(row,col));
                view.displayUpdatedScreen(model.b);
                view.paintYellowSlots(model.getLegalMovesForSelected(new Position(row,col)));
                to = model.get_Place(row, col);
            }
        }
        if(model.isGameOverFor(model.getPlayers_turn_color()))
        {
            model.next_player_turn();
            view.displayFinishScreen(model.getPlayers_turn_color());
        }


    }
    private void AIpart() throws InterruptedException {

        if(!model.isTerminal()) {
            if (view.isAI() && model.getPlayers_turn_color() == Color.black) {
                ai = new MCTS(model);
                ArrayList<Position> moves;
                moves = ai.runIterations();
                view.addMove(new Move(model.get_Place(moves.get(2).getX(), moves.get(2).getY()), model.get_Place(moves.get(1).getX(), moves.get(1).getY()),
                        model.get_Place(moves.get(0).getX(), moves.get(0).getY())), Color.black);
                model.makeMoveForAI(moves);
                view.set_hows_turn(model.getPlayers_turn_color());
                view.displayUpdatedScreen(model.b);

            }
        }
        if (model.isTerminal())
        {
            view.displayFinishScreen(model.getPlayers_turn_color());
        }

    }
    public void initilaizeButtons(Board b, View view) {

        for (int i = 0; i < Board.ROWS; i++) {
            for (int j = 0; j < Board.COLS; j++) {
                final int row = i;
                final int col = j;
                view.allButtonsBoard[i][j].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                            buttonClicked(view.getAllButtonsBoard(), row, col);
                        } catch (InterruptedException ex) {
                            throw new RuntimeException(ex);
                        }
                        SwingUtilities.invokeLater(new Runnable() {
                            public void run() {
                                // Call the function you want to trigger after the button action is completed
                                try {
                                    AIpart();
                                } catch (InterruptedException ex) {
                                    throw new RuntimeException(ex);
                                }
                            }
                        });
                    }
                });
            }
        }
    }
}
