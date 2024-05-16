import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

public class View extends JFrame {

    public static final String whiteQueen = "C:\\אוריאל\\student things\\FINAL PROJECT\\whiteQueen.png";
    public static final String BlackQueen = "C:\\אוריאל\\student things\\FINAL PROJECT\\BlackQueen.png";

    public static final String Background = "C:\\אוריאל\\student things\\FINAL PROJECT\\something.jpg";
    private JFrame startingscreen;

    private JLabel turn_indicator;

    private JTextArea text_moves;

    private boolean isAI;

    private JScrollPane move_counter;

    public JButton[][] getAllButtonsBoard() {
        return allButtonsBoard;
    }

    protected JButton[][] allButtonsBoard;
    public View() {
        text_moves = new JTextArea("");
        text_moves.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 20));
    }

    public void setStartingScreen(Board b)
    {
        javax.swing.JButton jButton1;
        javax.swing.JButton jButton2;
        javax.swing.JLabel jLabel1;
        javax.swing.JLabel jLabel2;
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI Black", 0, 24)); // NOI18N
        jLabel1.setText("THE GAME OF AMAZONS");

        jLabel2.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        jLabel2.setText("choose game mode:");

        jButton1.setFont(new java.awt.Font("Segoe UI Black", 0, 18)); // NOI18N
        jButton1.setText("PvP");


        jButton2.setFont(new java.awt.Font("Segoe UI Black", 0, 18)); // NOI18N
        jButton2.setText("PvC");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(124, 124, 124)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(60, 60, 60)
                                                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 306, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(187, 187, 187)
                                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap(93, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(65, 65, 65)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(44, 44, 44)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(153, Short.MAX_VALUE))
        );
        startingscreen = new JFrame();
        startingscreen.setTitle("THE GAME OF AMAZONS");
        startingscreen.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        startingscreen.setSize(1280, 720);
        startingscreen.setLocationRelativeTo(null);


        startingscreen.setResizable(true);
        startingscreen.setVisible(false);
        allButtonsBoard = new JButton[10][10];

        jButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                isAI = false;
                startingscreen.setVisible(true);

            }
        });

        jButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                isAI = true;
                startingscreen.setVisible(true);

            }
        });

        pack();
        this.setVisible(true);
        displayScreen(b);
    }



    public void set_hows_turn(Color c)
    {
        if(c.equals(Color.white))
            turn_indicator.setText("WHITE TURN");
        if(c.equals(Color.black))
            turn_indicator.setText("BLACK TURN");
    }

    public boolean isAI() {
        return isAI;
    }

    private void displayScreen(Board b) {
        JPanel board = new JPanel(new GridLayout(Board.ROWS+1,Board.COLS+1));
        for (int row = 0; row < Board.ROWS+1; row++) {
            for (int col = 0; col < Board.COLS+1; col++) {
                JButton squareButton = new JButton();
                squareButton.setPreferredSize(new Dimension(65, 65));
                squareButton.setBackground(getSlotColor(row, col));
                squareButton.setText(getNumber(row, col));
                if ((row != 0) && (col != 0)) {
                    allButtonsBoard[row - 1][col - 1] = squareButton;
                    if (b.IsQueen(new Position(row - 1, col - 1))) {
                        if (b.getQueen(new Position(row - 1, col - 1)).getColor().equals(Color.white)) {
                            ImageIcon ImageWhite = new ImageIcon(whiteQueen);
                            Image icon = ImageWhite.getImage().getScaledInstance(54, 54, Image.SCALE_SMOOTH);
                            squareButton.setIcon(new ImageIcon(icon));
                        } else {
                            ImageIcon ImageWhite = new ImageIcon(BlackQueen);
                            Image icon = ImageWhite.getImage().getScaledInstance(54, 54, Image.SCALE_SMOOTH);
                            squareButton.setIcon(new ImageIcon(icon));
                        }
                    }

                }

                board.add(squareButton);
            }

        }

        startingscreen.setLayout(new BorderLayout());
        startingscreen.add(board, BorderLayout.WEST);
        move_counter = new JScrollPane();
        move_counter.setPreferredSize(new Dimension(550,300));

        JPanel lPanel = new JPanel(new BorderLayout());
        turn_indicator = new JLabel("White TURN");
        turn_indicator.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 30));
        turn_indicator.setHorizontalAlignment(SwingConstants.CENTER);
        lPanel.add(move_counter, BorderLayout.NORTH);

        lPanel.add(turn_indicator,BorderLayout.SOUTH);

        startingscreen.add(lPanel, BorderLayout.EAST);


        startingscreen.revalidate();
        startingscreen.repaint();
        pack();
        startingscreen.setLocationRelativeTo(null);


    }

    public void paintGreenSlots(ArrayList<Position> slotsToPaint)
    {
        for (Position a : slotsToPaint)
        {
            allButtonsBoard[a.getX()][a.getY()].setBackground(Color.green);
        }

    }

    public void paintYellowSlots(ArrayList<Position> slotsToPaint)
    {
        for (Position a : slotsToPaint)
        {
            allButtonsBoard[a.getX()][a.getY()].setBackground(Color.yellow);
        }

    }

    public void addMove(Move m,Color c)
    {
        String turn = "";
        if (c.equals(Color.black))
            turn = "black";
        if (c.equals(Color.white))
            turn = "white";
        text_moves.append(turn + " moved from " + m.getFrom() + " to " + m.getTo() + " and blocked " + m.getBlock() + "\n");
        text_moves.append("*************************************\n");
        JScrollPane scrollPane = new JScrollPane(text_moves);
        scrollPane.setPreferredSize(new Dimension(200, 200));
        move_counter.setViewportView(scrollPane);
    }

    public Color getSlotColor(int row, int col) {
        if((row == 0 ))
            return Color.WHITE;
        if((col == 0 ))
            return Color.WHITE;
        return (row + col) % 2 == 0 ? new Color(210, 180, 140) : new Color(139, 69, 19);
    }

    private String getNumber(int row, int col)
    {
        if((row == 0)&&(col == 0))
            return "";
        if(row == 0)
        {
            return col + "";
        }
        if(col == 0)
        {
            return (char)(row + 64) + "";
        }
        return "";
    }





    public boolean getIfButtonSelected()
    {
        for (int i = 0; i < 10; i++)
        {
            for (int j = 0; j < 10; j++)
            {
                if (allButtonsBoard[i][j].getBackground().equals(Color.green))
                    return true;
                if (allButtonsBoard[i][j].getBackground().equals(Color.yellow))
                    return true;
            }
        }
        return false;
    }

    private void clearBoard()
    {
        for (int i = 0; i < Board.ROWS ; i++)
        {
            for (int j = 0; j < Board.COLS ;j++)
            {
                if (!allButtonsBoard[i][j].getBackground().equals(Color.darkGray))
                    allButtonsBoard[i][j].setBackground(getSlotColor(i+1,j+1));
                allButtonsBoard[i][j].setIcon(null);
            }
        }
    }
    public void displayUpdatedScreen(Board b)
    {
        clearBoard();
        for (int i = 0; i < Board.ROWS ; i++)
        {
            for (int j = 0; j < Board.COLS ;j++)
            {
                if(b.getGameBoard()[i][j] instanceof Queen)
                {
                    if(((Queen)b.getGameBoard()[i][j]).getColor().equals(Color.white))
                    {
                        ImageIcon ImageWhite = new ImageIcon(whiteQueen);
                        Image icon = ImageWhite.getImage().getScaledInstance(54,54,Image.SCALE_SMOOTH);
                        allButtonsBoard[i][j].setIcon(new ImageIcon(icon));
                    }
                    else
                    {
                        ImageIcon ImageBlack = new ImageIcon(BlackQueen);
                        Image icon = ImageBlack.getImage().getScaledInstance(54,54,Image.SCALE_SMOOTH);
                        allButtonsBoard[i][j].setIcon(new ImageIcon(icon));
                    }
                }
                if(b.getGameBoard()[i][j] instanceof Block)
                {
                    allButtonsBoard[i][j].setBackground(Color.darkGray);
                }
            }
        }
    }

    public void displayFinishScreen(Color c)
    {
        String winner = "";
        if (c.equals(Color.black))
            winner = "black";
        if (c.equals(Color.white))
            winner = "white";
        JFrame frame = new JFrame("Color Changing Label");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setBackground(Color.YELLOW);
        frame.setLocationRelativeTo(null); // Center the frame

        JLabel label = new JLabel(winner + " WON!!!", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 24));
        frame.add(label, BorderLayout.CENTER);

        Timer timer = new Timer(200, new ActionListener() {
            Random random = new Random();

            @Override
            public void actionPerformed(ActionEvent e) {
                int r = random.nextInt(256); // Generate random values for RGB
                int g = random.nextInt(256);
                int b = random.nextInt(256);
                Color randomColor = new Color(r, g, b);
                label.setForeground(randomColor); // Change label color
            }
        });
        timer.start();

        frame.setVisible(true);
        System.out.println(winner + " WON!!!!");
    }
}


