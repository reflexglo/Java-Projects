public class Node {
    Node prev;
    String coord;
    //Generic node class
    public Node(Node prev, String coord)
    {
        this.prev = prev;
        this.coord = coord;
    }
    //Get and set methods for fields
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
