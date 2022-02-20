import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.util.Pair;
public class Client extends JPanel implements ActionListener
{
    int xDim, yDim, maxZombs;
    Maze maze;
    Player player;
    Timer clock;
    ArrayList<Zombie> zombs;
    ArrayList<String> spawns;
    
    Client(){
        maxZombs = 10;
        maze = new Maze("src//maze.txt");
        player = new Player(maze,800,100);
        zombs = new ArrayList<Zombie>();
        spawns = new ArrayList<String>();
        
        for(int i = 0;i<maze.getLength();i++)
        {
            for(int j = 0;j<maze.getWidth();j++)
            {
                if(maze.getColor(i, j).equals(Color.BLUE))
                {
                    spawns.add(i+","+j);
                }
            }
        }
        
        xDim = 2560;
        yDim = 1440;
        clock = new Timer(10,this);    
        clock.start(); 
        Dimension dim = new Dimension(xDim,yDim);        
        this.setPreferredSize(dim);
        this.setBackground(Color.BLACK);
        this.setVisible(true);
        this.setFocusable(true);
    }
    
    public void drawMaze(Graphics g)
    {
        maze.render(g, 100, 100, 600,player,zombs);
        player.renderCone(g, maze, 600, 100, 100);
        player.renderCView(g, maze, 600, 100, 100);
    }
    
    public void drawPlayerView(Graphics g)
    {
        //player.renderView(g);
    }
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        drawMaze(g);
        drawPlayerView(g);
        repaint();
    }
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        player.genView(maze,zombs);
        player.cooldown();
        if(zombs.size()<maxZombs)
        {
            int spawnChance = (int)(Math.random()*0);
            if(spawnChance == 0)
            {
                int spawnLoc = (int)(Math.random()*spawns.size());
                int zx = Integer.parseInt(spawns.get(spawnLoc).split(",")[0]);
                int zy = Integer.parseInt(spawns.get(spawnLoc).split(",")[1]);
                zombs.add(new Zombie(zx,zy,setDest(zx,zy)));
            }
        }
        for(int i = 0;i<zombs.size();i++)
        {
            zombs.get(i).move(player);
        }
    }
    
    
   public void addNotify(){
       super.addNotify();
         addKeyListener(new KeyHandler());
         addMouseListener(new MouseHandler());
      addMouseMotionListener(new MouseHandler());
   }
    public class KeyHandler extends KeyAdapter implements KeyListener {
      @Override
      public void keyPressed(KeyEvent e) {        
        String key = KeyEvent.getKeyText(e.getKeyCode());
        
      }
      @Override
      public void keyReleased(KeyEvent e)
      {
          String key = KeyEvent.getKeyText(e.getKeyCode());
          if(key.equals("Right"))
          {
              if(player.getDir() == 3)
              {
                  player.setDir(0);
              }
              else
              {
                  player.setDir(player.getDir()+1);
              }
          }
          if(key.equals("Left"))
          {
              if(player.getDir() == 0)
              {
                  player.setDir(3);
              }
              else
              {
                  player.setDir(player.getDir()-1);
              }
          }
          if(key.equals("Up"))
          {
              player.move(1, maze);
          }
          if(key.equals("Down"))
          {
              player.move(-1, maze);
          }
          if(key.equals("R"))
          {
              player.reload();
          }
          if(key.equals("E"))
          {
              String door = player.open(maze);
              if(!door.isEmpty())
              {
                  int dx = Integer.parseInt(door.split(",")[0]);
                  int dy = Integer.parseInt(door.split(",")[1]);
                  maze.setType(dx, dy, 3);
              }
          }
      }
}
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
    
    public ArrayList<String> setDest(int startX, int startY)
    {
        HashMap<String,Integer> pos = new HashMap<String,Integer>();
        Pair<Integer,Integer> start,end,cur;
        ArrayList<String> select = new ArrayList<String>();
        ArrayList<Node> nodes = new ArrayList<Node>();
        ArrayList<String> path = new ArrayList<String>();
        start = new Pair<Integer,Integer>(startX,startY);
        end = new Pair<Integer,Integer>(player.getX(),player.getY());
        cur = start;
                while(!cur.getKey().equals(end.getKey()) || !cur.getValue().equals(end.getValue()))
                {
                    select.add(cur.getKey()+","+cur.getValue());
                    int posX = cur.getKey()-1;
                    int posY = cur.getValue()-1;
                    for(int i = 0;i<6;i+=1)
                    {
                        int neX = posX + i;
                        for(int j = 0;j<6;j+=1)
                        {
                            int neY = posY + j;
                            String sel = neX + "," + neY;
                            if((neX >= 0 && neX < maze.getLength()) && (neY >= 0 && neY < maze.getWidth()) && (neX != cur.getKey() || neY != cur.getValue()) && !select.contains(sel))
                            {
                                if(maze.getType(neX, neY) == 0)
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
                if(lastNode != null)
                {
                    while(lastNode.getPrev() != null)
                    {
                        path.add(lastNode.getCoord());
                        lastNode = lastNode.getPrev();
                    }
                    return path;
                }
                else
                {
                    return null;
                }
    }
     public class MouseHandler extends MouseAdapter
    {
      @Override
      public void mouseMoved(MouseEvent e){
         
      }
      @Override
      public void mousePressed(MouseEvent e)
      {
          int zomb = player.shoot(maze,zombs);
          if(zomb != -1)
          {
              zombs.remove(zomb);
          }
      }
     } 
}
