import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TreeNode {
    private Position arrow;
    private Queen queen;
    private Position dest;
    private ArrayList<TreeNode> children = new ArrayList<TreeNode>();
    private Model m;



    private int countVisited;
    private double score;

    private TreeNode parent;

    public TreeNode(Position arrow , Queen q, Position dest, Model m, TreeNode parent)
    {
        this.arrow = new Position(arrow.getX(), arrow.getY());
        this.queen = new Queen(q.getColor(),q.getP());
        this.dest = new Position(dest.getX(), dest.getY());
        this.m = m;
        this.countVisited = 0;
        this.score = 0.0;
        this.parent = parent;

    }

    public TreeNode( Model m)
    {
        this.m = m;
        this.countVisited = 0;
        this.score = 0.0;
    }





    public TreeNode getParent() {
        return parent;
    }

    public void setParent(TreeNode parent) {
        this.parent = parent;
    }


    public void addChild(TreeNode child) {
        child.setParent(this);
        this.children.add(child);
    }

    public Model getM() {
        return m;
    }



    public ArrayList<TreeNode> getChildren() {
        return children;
    }

    public double getScore() {
        return score;
    }

    public int getCountVisited() {
        return countVisited;
    }





    public void incrementVisitCount() {
        this.countVisited++;
    }

    public void addToTotalScore(double score) {
        this.score += score;
    }

    public ArrayList<Position> getMove()
    {
        ArrayList<Position> m = new ArrayList<>();
        m.add(this.arrow);
        m.add(this.dest);
        m.add(this.queen.getP());
        return m;
    }


}



