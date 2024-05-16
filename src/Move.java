public class Move
{

    private String from;

    private String to;

    private String block;

    public Move(String from, String to, String block)
    {
        this.from = from;
        this.to = to;
        this.block = block;
    }


    public String getBlock() {
        return block;
    }

    public String getTo() {
        return to;
    }

    public String getFrom() {
        return from;
    }
}
