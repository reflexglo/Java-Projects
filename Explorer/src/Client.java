
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import javafx.util.Pair;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Client extends JPanel implements ActionListener {

    Timer clock;
    int xDim,yDim;
    HashMap<String,BiomeTile> tiles,land; 
    Player player;
    String destination;
    boolean boating,destSet;
    ArrayList<String> path;
    int zoom = 2;
    Client() {
        //Retrieve map data from map generation file, init map
        destination = "-1,-1";
        tiles = new HashMap<String,BiomeTile>();
        land = new HashMap<String,BiomeTile>();
        path = new ArrayList<String>();
        boating = false;
        destSet = false;
        try {
      File myObj = new File("src//map.txt");
      Scanner myReader = new Scanner(myObj);
      for(int i = 0;i<2560;i+=2)
      {
          for(int j = 0;j<1440;j+=2)
          {
              BiomeTile tile = new BiomeTile(i,j,2,2,5);
              tiles.put(i+","+j, tile);
          }
      }
      while (myReader.hasNextLine()) {
        String data = myReader.nextLine();
        String[] dArr = data.split(",");
        BiomeTile tile = new BiomeTile(Integer.parseInt(dArr[0]),Integer.parseInt(dArr[1]),Integer.parseInt(dArr[3]),Integer.parseInt(dArr[3]),Integer.parseInt(dArr[2]));
        String coord = Integer.parseInt(dArr[0]) + "," + Integer.parseInt(dArr[1]);
        tiles.put(coord, tile);
        land.put(coord, tile);
      }
      myReader.close();
    } catch (FileNotFoundException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
        String spawn = (String)land.keySet().toArray()[(int)(Math.random()*land.values().size())];
        player = new Player(Integer.parseInt(spawn.split(",")[0]),Integer.parseInt(spawn.split(",")[1]),2,2,Color.BLACK);
        
        //Setup window and dimensions
        xDim = 2650;
        yDim = 1440;
        Dimension dim = new Dimension(xDim, yDim);
        this.setPreferredSize(dim);
        this.setBackground(Color.blue);
        this.setVisible(true);
        this.setFocusable(true);
        clock = new Timer(1, this);
        clock.start();
    }
    //Draw functions for UI
    public void drawIslands(Graphics g)
    {
        for(BiomeTile tile : land.values())
        {
        if(tile.getBiome() == 2){
            g.setColor(new Color(0,255,120));
        }
        if(tile.getBiome() == 3){
            g.setColor(Color.green);
        }
        if(tile.getBiome() == 5){
            g.setColor(Color.blue);
        }
        if(tile.getBiome() == 1){
            g.setColor(new Color(120,255,0));
        }
        if(tile.getBiome() == 0){
            g.setColor(Color.gray);
        }
        if(tile.getBiome() == 4){
            g.setColor(new Color(245,245,220));
        }
        if(tile.getTrace())
        {
            System.out.println("HI");
            g.setColor(Color.WHITE);
        }
            g.drawRect(tile.getX(),tile.getY(), tile.getWidth(), tile.getHeight());
            g.fillRect(tile.getX(),tile.getY(), tile.getWidth(), tile.getHeight());
        }
    }
    
    public void drawPlayer(Graphics g)
    {
        player.render(g);
    }
    //Update draw functions every frame
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g;
        if(zoom > 1)
        g2d.translate(1280-zoom*player.getX(),720-zoom*player.getY());
        g2d.scale(zoom,zoom);
        drawIslands(g);
        drawPlayer(g);
        repaint();
    }
    //Update game timer every tick
    @Override
    public void actionPerformed(ActionEvent e) {
        //If player's path is not empty, advance to next node
        if(path.size()> 0)
        {
            int newX = Integer.parseInt(path.get(path.size()-1).split(",")[0]);
            int newY = Integer.parseInt(path.get(path.size()-1).split(",")[1]);
            path.remove(path.size()-1);
            player.setX(newX);
            player.setY(newY);
        }
        else
        {
            destSet = false;
        }
    }

    //Update key and mouse handler functions
    public void addNotify() {
        super.addNotify();
        addKeyListener(new KeyHandler());
        addMouseListener(new MouseHandler());
        addMouseMotionListener(new MouseHandler());
    }

    //Key handler functions
    public class KeyHandler extends KeyAdapter implements KeyListener {

        @Override
        public void keyPressed(KeyEvent e) {
            //Arrow keks to move on land, use B to move from land to water, space to zoom in/out of map
            String key = KeyEvent.getKeyText(e.getKeyCode());
            String coords = player.getX()+","+player.getY();
            if(boating == false)
            {
            if(key.equals("Right"))
            {
                coords = (player.getX()+2)+","+player.getY();
                if(land.containsKey(coords) && land.get(coords).getBiome() != 5)
                {
                    player.setX(player.getX()+2);
                }
            }
            if(key.equals("Left"))
            {
                coords = (player.getX()-2)+","+player.getY();
                if(land.containsKey(coords) && land.get(coords).getBiome() != 5)
                {
                    player.setX(player.getX()-2);
                }
            }
            if(key.equals("Up"))
            {
                coords = player.getX()+","+(player.getY()-2);
                if(land.containsKey(coords) && land.get(coords).getBiome() != 5)
                {
                    player.setY(player.getY()-2);
                }
            }
            if(key.equals("Down"))
            {
                coords = player.getX()+","+(player.getY()+2);
                if(land.containsKey(coords) && land.get(coords).getBiome() != 5)
                {
                    player.setY(player.getY()+2);
                }
            }
            }
            if(key.equals("B"))
            {
                coords = (player.getX()+2)+","+player.getY();
                if(!land.containsKey(coords) || land.get(coords).getBiome() == 5)
                {
                    player.setX(player.getX()+2);
                    boating = true;
                    return;
                }
                coords = (player.getX()-2)+","+player.getY();
                if(!land.containsKey(coords) || land.get(coords).getBiome() == 5)
                {
                    player.setX(player.getX()-2);
                    boating = true;
                    return;
                }
                coords = player.getX()+","+(player.getY()-2);
                if(!land.containsKey(coords) || land.get(coords).getBiome() == 5)
                {
                    player.setY(player.getY()-2);
                    boating = true;
                    return;
                }
                coords = player.getX()+","+(player.getY()+2);
                if(!land.containsKey(coords) || land.get(coords).getBiome() == 5)
                {
                    player.setY(player.getY()+2);
                    boating = true;
                    return;
                }
            }
            if(key.equals("Space"))
            {
                if(zoom == 1)
                {
                    zoom = 2;
                }
                else if(zoom == 2)
                {
                    zoom = 3;
                }
                else
                {
                    zoom = 1;
                }
            }
        }

    }

    //Creates string representation of path
    public String getPath(Node endNode)
    {
        String path = "";
        while(endNode.getPrev() != null)
        {
            path += endNode.getCoord();
            endNode = endNode.getPrev();
        }
        return path;
    }
    
    //Calculates value of current path
     public boolean worthTrip(Node curNode, Node newNode, HashMap<String,Integer> pos)
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
    
     //Uses A* Hueristic algorithm to determine a path to selected destination
    public void setDest(int destX, int destY)
    {
        HashMap<String,Integer> pos = new HashMap<String,Integer>();
        Pair<Integer,Integer> start,end,cur;
        ArrayList<String> select = new ArrayList<String>();
        ArrayList<Node> nodes = new ArrayList<Node>();
        start = new Pair<Integer,Integer>(player.getX(),player.getY());
        end = new Pair<Integer,Integer>(destX,destY);
        cur = start;
                while(!cur.getKey().equals(end.getKey()) || !cur.getValue().equals(end.getValue()))
                {
                    select.add(cur.getKey()+","+cur.getValue());
                    int posX = cur.getKey()-2;
                    int posY = cur.getValue()-2;
                    for(int i = 0;i<6;i+=2)
                    {
                        int neX = posX + i;
                        for(int j = 0;j<6;j+=2)
                        {
                            int neY = posY + j;
                            String sel = neX + "," + neY;
                            if((neX >= 0 && neX < 2560) && (neY >= 0 && neY < 1440) && (neX != cur.getKey() || neY != cur.getValue()) && !select.contains(sel))
                            {
                                if(tiles.get(sel).getBiome() == 5)
                                {
                                int sDist = (int)(Math.sqrt(Math.pow(neX-cur.getKey(),2)+Math.pow(neY-cur.getValue(), 2))*10);
                                int eDist = (int)(Math.sqrt(Math.pow(neX-end.getKey(),2)+Math.pow(neY-end.getValue(), 2))*10);
                                pos.put(neX+","+neY,sDist+eDist);
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
                                    if(worthTrip(thisNode,curNode,pos))
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
                    if(!minPos.equals(""))
                    {
                        int newX = Integer.parseInt(minPos.split(",")[0]);
                        int newY = Integer.parseInt(minPos.split(",")[1]);
                        cur = new Pair(newX,newY);   
                    }
                }
                Node lastNode = null;
                String lastCoords = end.getKey()+","+end.getValue();
                for(int i = 0;i<nodes.size();i++)
                {
                    if(nodes.get(i).getCoord().equals(lastCoords))
                    {
                        lastNode = nodes.get(i);
                    }
                }
                while(lastNode.getPrev() != null)
                {
                    path.add(lastNode.getCoord());
                    lastNode = lastNode.getPrev();
                }
    }
    //Mouse handler functions
    public class MouseHandler extends MouseAdapter {
        //is called when the mouse is moved
        @Override
        public void mouseMoved(MouseEvent e) {
            
        }
        //is called when the mouse button is pressed
        @Override
        public void mousePressed(MouseEvent e) {
            //Press on the desired location to travel to, must be in water first
            if(boating && !destSet)
            {
                int x = e.getX();
                int y = e.getY();
                if(x%2 != 0)
                {
                    x++;
                }
                if(y%2 != 0)
                {
                    y++;
                }
                destSet = true;
                setDest(x,y);
            }
            }
        }
    }
