public class Node {
    Node prev;
    String coord;
    public Node(Node prev, String coord)
    {
        this.prev = prev;
        this.coord = coord;
    }
    public Node getPrev()
    {
        return prev;
    }
    public String getCoord()
    {
        return coord;
    }
    public void setPrev(Node prev)
    {
        this.prev = prev;
    }
}
