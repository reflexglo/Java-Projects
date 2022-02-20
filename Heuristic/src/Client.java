
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.util.Pair;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Client extends JPanel implements ActionListener {

    Timer clock;
    Tiles[][] tiles;
    int xDim,yDim;
    HashMap<String,Integer> pos;
    Pair<Integer,Integer> start,end,cur;
    ArrayList<String> select;
    ArrayList<Node> nodes;
    Client() {
        tiles = new Tiles[16][9];
        start = new Pair<Integer,Integer>(0,1);
        cur = start;
        end = new Pair<Integer,Integer>(10,7);
        pos = new HashMap<String,Integer>();
        select = new ArrayList<String>();
        nodes = new ArrayList<Node>();
        nodes.add(new Node(null,"0,1"));
        for(int i = 0;i<tiles.length;i++)
        {
            for(int j = 0;j<tiles[i].length;j++)
            {
                if(i == start.getKey() && j == start.getValue())
                {
                    tiles[i][j] = new Tiles(i*160,j*160,160,160,Color.BLUE);
                }
                else if(i == end.getKey() && j == end.getValue())
                {
                    tiles[i][j] = new Tiles(i*160,j*160,160,160,Color.RED);
                }
                else
                {
                    tiles[i][j] = new Tiles(i*160,j*160,160,160,Color.WHITE);
                }
            }
        }
        tiles[2][3].setColor(Color.BLACK);
        tiles[3][3].setColor(Color.BLACK);
        tiles[4][5].setColor(Color.BLACK);
        tiles[4][6].setColor(Color.BLACK);
        tiles[4][4].setColor(Color.BLACK);
        tiles[4][3].setColor(Color.BLACK);
        tiles[3][2].setColor(Color.BLACK);
        tiles[3][1].setColor(Color.BLACK);
        tiles[3][0].setColor(Color.BLACK);
        xDim = 2650;
        yDim = 1440;
        Dimension dim = new Dimension(xDim, yDim);
        this.setPreferredSize(dim);
        this.setBackground(Color.red);
        this.setVisible(true);
        this.setFocusable(true);
        clock = new Timer(100, this);
        clock.start();
    }
    
    public void drawTiles(Graphics g)
    {
        for(int i = 0;i<tiles.length;i++)
        {
            for(int j = 0;j<tiles[i].length;j++)
            {
                tiles[i][j].render(g);
            }
        }
    }
    
    public boolean worthTrip(Node curNode, Node newNode)
    {
        int value1 = 0;
        int value2 = 0;
        Node search = curNode;
        while(search.getPrev() != null)
        {
            value1 += pos.get(search.getCoord());
            search = search.getPrev();
        }
        search = new Node(newNode,curNode.getCoord());
        while(search.getPrev() != null)
        {
            value2 += pos.get(search.getCoord());
            search = search.getPrev();
        }
        return value1 > value2;
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawTiles(g);
        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
    }

    public void addNotify() {
        super.addNotify();
        addKeyListener(new KeyHandler());
        addMouseListener(new MouseHandler());
        addMouseMotionListener(new MouseHandler());
    }
    
    public class KeyHandler extends KeyAdapter implements KeyListener {

        @Override
        public void keyPressed(KeyEvent e) {
            String key = KeyEvent.getKeyText(e.getKeyCode());
            if(key.equals("Space"))
            {
                if(cur.getKey() != end.getKey() || cur.getValue() != end.getValue())
                {
                    select.add(cur.getKey()+","+cur.getValue());
                    int posX = cur.getKey()-1;
                    int posY = cur.getValue()-1;
                    for(int i = 0;i<3;i++)
                    {
                        int neX = posX + i;
                        for(int j = 0;j<3;j++)
                        {
                            int neY = posY + j;
                            String sel = neX + "," + neY;
                            if((neX >= 0 && neX < 16) && (neY >= 0 && neY < 9) && (neX != cur.getKey() || neY != cur.getValue()) && !select.contains(sel))
                            {
                                if(!tiles[neX][neY].getObst())
                                {
                                int sDist = (int)(Math.sqrt(Math.pow(neX-cur.getKey(),2)+Math.pow(neY-cur.getValue(), 2))*10);
                                int eDist = (int)(Math.sqrt(Math.pow(neX-end.getKey(),2)+Math.pow(neY-end.getValue(), 2))*10);
                                pos.put(neX+","+neY,sDist+eDist);
                                tiles[neX][neY].setDist(sDist+eDist);
                                boolean needNode = true;
                                Node curNode = null;
                                Node thisNode = null;
                                for(int k = 0;k<nodes.size();k++)
                                {
                                    if(nodes.get(k).getCoord().equals(neX+","+neY))
                                    {
                                        needNode = false;
                                        thisNode = nodes.get(k);
                                    }
                                    if(nodes.get(k).getCoord().equals(cur.getKey()+","+cur.getValue()))
                                    {
                                        curNode = nodes.get(k);
                                    }
                                }
                                if(needNode == true)
                                {
                                    nodes.add(new Node(curNode,neX+","+neY));
                                }
                                else
                                {
                                    if(worthTrip(thisNode,curNode))
                                    {
                                        thisNode.setPrev(curNode);
                                    }
                                }
                            }
                        }
                    }
                    }
                    int min = 10000;
                    String minPos = "";

                    for(String coord:pos.keySet())
                    {
                        if(pos.get(coord) < min && !select.contains(coord))
                        {
                            min = pos.get(coord);
                            minPos = coord;
                        }
                    }
                    int newX = Integer.parseInt(minPos.split(",")[0]);
                    int newY = Integer.parseInt(minPos.split(",")[1]);
                    cur = new Pair(newX,newY);
                    tiles[newX][newY].setColor(Color.BLUE);
                }
                else
                {
                    Node searchNode = null;
                    String endCoord = end.getKey()+","+end.getValue();
                    for(int i = 0;i<nodes.size();i++)
                    {
                        if(nodes.get(i).getCoord().equals(endCoord))
                        {
                            searchNode = nodes.get(i);
                            break;
                        }
                    }
                    while(searchNode.getPrev() != null)
                    {
                        int x = Integer.parseInt(searchNode.getCoord().split(",")[0]);
                        int y = Integer.parseInt(searchNode.getCoord().split(",")[1]);
                        tiles[x][y].setColor(Color.GREEN);
                        searchNode = searchNode.getPrev();
                    }
                }
            }
        }

    }

    public class MouseHandler extends MouseAdapter {
        //is called when the mouse is moved
        @Override
        public void mouseMoved(MouseEvent e) {
            
            
        }
        //is called when the mouse button is pressed
        @Override
        public void mousePressed(MouseEvent e) {
            
            
        }
    }
    
}