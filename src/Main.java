public class Main {
    public static void main(String[] args) throws InterruptedException {
        Model m = new Model();

        View v = new View();
        Board b = new Board();
        v.setStartingScreen(b);
        Controller c = new Controller(v,m);
        c.initilaizeButtons(b,v);

    }


}