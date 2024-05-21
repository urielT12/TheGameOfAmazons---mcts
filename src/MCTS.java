import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

public class MCTS {
    private static final double EXPLORATION_CONSTANT = Math.sqrt(2);

    public TreeNode root;

    private Model m;

    public MCTS(Model m) {
        this.m = m;
        this.root = new TreeNode(this.m);


    }


    public ArrayList<Position> runIterations() {

        int i = 0;
        while (i < 550) {
            // Selection
            TreeNode selectedNode = selection(root);

            // Simulation
            double score = simulation(selectedNode);

            // Backpropagation
            backpropagation(selectedNode, score);

            // Update elapsed time
            //elapsedTime = System.currentTimeMillis() - startTime;
            i++;
        }
        ArrayList<Position> selected = getBestMove();
        //first - arrow , second - dest,  third - queen
        return selected;
    }

    //the selection part, uses the ucb formula to decide which node to investigate
    private TreeNode selection(TreeNode node) {

        Random random = new Random();

        TreeNode current = node;
        while (!current.getM().isTerminal()) {
            {
                if (current.getChildren().isEmpty()) {
                    expand(current);

                    int child = random.nextInt(current.getChildren().size());

                    return current.getChildren().get(child);

                } else {
                    double maxUCB = Double.NEGATIVE_INFINITY;
                    TreeNode bestChild = null;
                    for (TreeNode child : current.getChildren()) {
                        double UCB = calculateUCB(child);
                        if (UCB >= maxUCB) {
                            maxUCB = UCB;
                            bestChild = child;
                        }
                    }
                    current = bestChild;

                }

            }
        }
        return current;
    }



    //expanding a specific node - adds all the possible moves
    private void expand(TreeNode node) {
        for (Queen q : node.getM().getQueens())
            if (q.getColor() == Color.black) {
                ArrayList<Position> possibleMoves = node.getM().getLegalMovesForSelected(q.getP());
                possibleMoves.remove(q.getP());
                for (Position move : possibleMoves) {
                    ArrayList<Position> possibleArrows = node.getM().getLegalMovesForSelected(move);
                    possibleArrows.remove(move);
                    if (!possibleArrows.isEmpty())
                        for (Position arrow : possibleArrows) {
                            ArrayList<Position> check = new ArrayList<>();
                            check.add(arrow);
                            check.add(move);
                            check.add(q.getP());
                            if (m.checkIfMoveIsRelevante(check))
                                node.getChildren().add(new TreeNode(arrow, q, move, node.getM(), node));
                        }
                    else {
                        node.getChildren().add(new TreeNode(q.getP(), q, move, node.getM(), node));
                    }

                }
            }
        if(node.getChildren().isEmpty())
            expandWhenEmpty(node);

    }

    private void expandWhenEmpty(TreeNode node)
    {
        for (Queen q : node.getM().getQueens())
            if (q.getColor() == Color.black) {
                ArrayList<Position> possibleMoves = node.getM().getLegalMovesForSelected(q.getP());
                possibleMoves.remove(q.getP());
                for (Position move : possibleMoves) {
                    ArrayList<Position> possibleArrows = node.getM().getLegalMovesForSelected(move);
                    possibleArrows.remove(move);
                    if (!possibleArrows.isEmpty())
                        for (Position arrow : possibleArrows) {
                            node.getChildren().add(new TreeNode(arrow, q, move, node.getM(), node));
                        }
                    else {
                        node.getChildren().add(new TreeNode(q.getP(), q, move, node.getM(), node));
                    }
                }
            }
    }








    private double simulation(TreeNode node) {
        // Simulate a game from the given node until a terminal state is reached
        // Use a playout policy to conduct the simulation
        // Return the score of the simulated game
        Model currentModel = node.getM().copy();

        int score = 0;
        int randomMove;
        int count =0;
        int depth = 300;
        ArrayList<Position> moves = node.getMove();
        currentModel.makeMoveForAI(moves);
        while (!currentModel.isTerminal() && count < depth) {


            ArrayList<ArrayList<Position>> allMoves = new ArrayList<>();
            for (Queen q : currentModel.getQueens())
                if (q.getColor() == currentModel.getPlayers_turn_color()) {
                    ArrayList<Position> possibleMoves = currentModel.getLegalMovesForSelected(q.getP());
                    possibleMoves.remove(q.getP());
                    for (Position move : possibleMoves) {
                        ArrayList<Position> possibleArrows = currentModel.getLegalMovesForSelected(move);
                        possibleArrows.remove(move);
                        for (Position arrow : possibleArrows) {

                            ArrayList<Position> newMove = new ArrayList<>();
                            newMove.add(arrow);
                            newMove.add(move);
                            newMove.add(q.getP());
                            allMoves.add(newMove);
                        }
                    }
                }
            if(allMoves.isEmpty())
                break;
            randomMove = new Random().nextInt(allMoves.size());
            ArrayList<Position> selectedAction = allMoves.get(randomMove);
            currentModel.makeMoveForAI(selectedAction);
            count++;
        }
        if(currentModel.isGameOverFor(Color.white))
            score = 1;
        return score;

    }


    private void backpropagation(TreeNode node, double score) {
        // Update the visit count and accumulated score of all nodes along the path from the given node to the root
        // Propagate the simulation result back up the tree
        while (node != null) {
            // Update visit count
            node.incrementVisitCount();

            // Update total score
            node.addToTotalScore(score);

            // Move to the parent node
            node = node.getParent();
        }

    }



    public ArrayList<Position> getBestMove() {
        int i = 0;
        TreeNode bestChild = null;
        TreeNode bestAggresive = null;
        double bestScore = Double.NEGATIVE_INFINITY;
        double bestAggresiveScore = Double.NEGATIVE_INFINITY;

        // Iterate through the children of the root node to find the best move
        while (i < root.getChildren().size()) {
            // Calculate a score for each child node (e.g., based on visit count or accumulated score)
            double score =  root.getChildren().get(i).getScore() / (double) root.getChildren().get(i).getCountVisited();
            if(Double.isNaN(score))
                score = 0.0;
            if (score >= bestScore) {
                bestScore = score;
                bestChild = root.getChildren().get(i);
            }


            if(root.getChildren().get(i).getM().IsAggresive(root.getChildren().get(i).getMove().get(0)))
            {
                if (score >= bestAggresiveScore) {
                    bestAggresiveScore = score;
                    bestAggresive = root.getChildren().get(i);
                }
            }



            if(m.checkWaysToMove(root.getChildren().get(i).getMove().get(2)) <= 2)
            {
                bestChild = root.getChildren().get(i);
                return bestChild.getMove();
            }

            i++;

        }
        if(bestAggresive != null)
            return bestAggresive.getMove();
        // Return the move associated with the best child node
        //first - arrow , second - dest,  third - queen
        return bestChild.getMove();
    }



    private double calculateUCB(TreeNode node) {
        if (node.getCountVisited() == 0)
            return Double.NEGATIVE_INFINITY;

        // Calculate the exploitation term
        double exploitationTerm = (double) node.getScore() / node.getCountVisited();

        // Calculate the exploration term
        double explorationTerm = EXPLORATION_CONSTANT * Math.sqrt(Math.log(node.getParent().getCountVisited() + 1) / (node.getCountVisited() + 1));

        return exploitationTerm + explorationTerm;
    }



}
