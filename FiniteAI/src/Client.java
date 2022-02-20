import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Polygon;
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
public class Client extends JPanel implements ActionListener
{
    int xDim, yDim;
    int[][] field;
    boolean up, down, left, right;
    Timer clock;
    Goal goal;
    AI ai;
    ArrayList<Wall> walls;
    
    Client(){
        up = false;
        down = false;
        left = false;
        right = false;
        goal = new Goal(2000,1000,20,20,Color.GREEN);
        ai = new AI(100,100,20,20,Color.RED);
        walls = new ArrayList<Wall>();
        walls.add(new Wall(0,0,0,1440,Color.BLACK));
        walls.add(new Wall(2540,0,2540,1440,Color.BLACK));
        walls.add(new Wall(0,0,2560,0,Color.BLACK));
        walls.add(new Wall(0,1420,2560,1420,Color.BLACK));
        int rez = 60;
        field = new int[2560/rez][1440/rez];
        for(int i = 0;i<field.length;i++)
        {
            for(int j = 0;j<field[i].length;j++)
            {
                field[i][j] = (int)(Math.random()*2);
            }
        }
        
        for(int i = 0;i<field.length;i++)
        {
            for(int j = 0;j<field[i].length;j++)
            {
                if(i-1 > 0)
                {
                    if(field[i][j] == 1 && field[i-1][j] == 1)
                    {
                        walls.add(new Wall((i-1)*rez,j*rez,i*rez,j*rez,Color.RED));
                    }
                }
                if(j-1 > 0)
                {
                    if(field[i][j] == 1 && field[i][j-1] == 1)
                    {
                        walls.add(new Wall(i*rez,(j-1)*rez,i*rez,j*rez,Color.RED));
                    }
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
    
    public void drawGoal(Graphics g)
    {
        int[][] points = ai.getPoints(walls);
        Polygon p = new Polygon(points[0],points[1],points[0].length);
        if(p.contains(goal.getX(),goal.getY()))
        {
            goal.render(g);
        }
    }
    
    public void drawAI(Graphics g)
    {
        ai.render(g,walls);
    }
    
    public void drawWalls(Graphics g)
    {
        ArrayList<Integer> close = ai.getViewWalls(walls);
        for(int i = 0;i<close.size();i++)
        {
            walls.get(close.get(i)).render(g);
        }
    }
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        drawAI(g);
        drawWalls(g);
        drawGoal(g);
        repaint();
    }
    
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        for(Wall w:walls)
        {
            if(w.getLine().intersects(ai.getTBounds()))
            {
                up = false;
            }
            if(w.getLine().intersects(ai.getBBounds()))
            {
                down = false;
            }
            if(w.getLine().intersects(ai.getLBounds()))
            {
                left = false;
            }
            if(w.getLine().intersects(ai.getRBounds()))
            {
                right = false;
            }
        }
        if(up)
        {
            ai.setY(ai.getY()-5);
        }
        if(down)
        {
            ai.setY(ai.getY()+5);
        }
        if(left)
        {
            ai.setX(ai.getX()-5);
        }
        if(right)
        {
            ai.setX(ai.getX()+5);
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
        if(key.equals("W"))
          {
              up = true;
          }
          if(key.equals("S"))
          {
              down = true;
          }
          if(key.equals("A"))
          {
              left = true;
          }
          if(key.equals("D"))
          {
              right = true;
          }
      }
      @Override
      public void keyReleased(KeyEvent e)
      {
          String key = KeyEvent.getKeyText(e.getKeyCode());
          if(key.equals("W"))
          {
              up = false;
          }
          if(key.equals("S"))
          {
              down = false;
          }
          if(key.equals("A"))
          {
              left = false;
          }
          if(key.equals("D"))
          {
              right = false;
          }
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
          
      }
     } 
}
