
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
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
/**
 *
 * @author Mr. Maroney
 */
public class Client extends JPanel implements ActionListener {

    Timer clock;
    int boundX, boundY;
    ArrayList<Deer> deers;
    ArrayList<Food> foods;
    ArrayList<Wolf> wolfs;
    Client() {
        boundX = 1000;
        boundY = 1000;
        int popSpace = 500;
        wolfs = new ArrayList<Wolf>();
        deers = new ArrayList<Deer>();
        foods = new ArrayList<Food>();
        
        for(int i = 0;i<3;i++)
        {
            int wolfX = (int)(Math.random()*(boundX/2)) + boundX/4;
            int wolfY = (int)(Math.random()*(boundY/2)) + boundY/4;
            wolfs.add(new Wolf(wolfX,wolfY,8,8,Color.GRAY,""+i));
        }
        int deerPop = (int)(Math.random()*((boundX*boundY)/(25*popSpace)))+10;
        for(int i = 0;i<deerPop*3;i++)
        {
            int randX = (int)(Math.random()*(boundX));
            int randY = (int)(Math.random()*(boundY));
            foods.add(new Food(randX,randY,2,2,Color.YELLOW));
        }
        for(int i = 0;i<deerPop;i++)
        {
            int randX = (int)(Math.random()*(boundX/2)) + boundX/4;
            int randY = (int)(Math.random()*(boundY/2)) + boundY/4;
            deers.add(new Deer(randX,randY,5,5,Color.RED));
        }
        
        clock = new Timer(100, this);
        clock.start();
        Dimension dim = new Dimension(2560,1440);

        this.setPreferredSize(dim);
        this.setBackground(Color.BLACK);
        this.setVisible(true);
        this.setFocusable(true);

    }

    public void drawField(Graphics g)
    {
        g.drawRect(0,0,boundX,boundY);
        g.setColor(Color.GREEN);
        g.fillRect(0,0,boundX,boundY);
    }
    public void drawDeer(Graphics g)
    {
        for(int i = 0;i<deers.size();i++)
        {
            deers.get(i).render(g);
        }
    }
    public void drawWolf(Graphics g)
    {
        for(int i = 0;i<wolfs.size();i++)
        {
            wolfs.get(i).render(g);
        }
    }
    public void drawFood(Graphics g)
    {
        for(int i = 0;i<foods.size();i++)
        {
            foods.get(i).render(g);
        }
    }
    public void drawInfo(Graphics g)
    {
        g.setFont(new Font("TimesRoman", Font.PLAIN, 20)); 
        g.setColor(Color.WHITE);
        g.drawString("Wolf Leaderboard",10,1030);
        ArrayList<Wolf> wolfBoard = new ArrayList<Wolf>();
        wolfBoard.add(wolfs.get(0));
        for(int i = 1;i<wolfs.size();i++)
        {
            int index = 0;
            while(wolfBoard.get(index).getStrength()>wolfs.get(i).getStrength() && index < wolfBoard.size())
            {
                index++;
            }
            wolfBoard.add(index,wolfs.get(i));
        }
        for(int i = 0;i<wolfBoard.size();i++)
        {
            g.drawString(wolfBoard.get(i).getName()+": "+wolfBoard.get(i).getStrength(), 10, 1060+i*30);
        }
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawField(g);
        drawDeer(g);
        drawWolf(g);
        drawFood(g);
        drawInfo(g);
        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int newFood = (int)(Math.random()*10);
        for(int i = 0;i<newFood;i++)
        {
            int randX = (int)(Math.random()*(boundX));
            int randY = (int)(Math.random()*(boundY));
            foods.add(new Food(randX,randY,2,2,Color.YELLOW));
        }
        for(int i = 0;i<wolfs.size();i++)
        {
            int target = wolfs.get(i).detect(deers);
            if(target != -1)
            {
                boolean killed = wolfs.get(i).hunt(deers.get(target));
                if(killed)
                {
                    deers.remove(target);
                }
            }
            else
            {
                wolfs.get(i).move(boundX, boundY);
            }
        }
        for(int i = 0;i<deers.size();i++)
        {
            int closeWolf = -1;
            int wolfDist = 1000;
            for(int j = 0;j<wolfs.size();j++)
            {
                int end = deers.get(i).endangered(wolfs.get(j));
                if(end != -1)
                {
                    closeWolf = j;
                    wolfDist = end;
                }
            }
            if(closeWolf != -1)
            {
                deers.get(i).flee(wolfs.get(closeWolf), boundX, boundY);
            }
            else
            {
                int mate = deers.get(i).mating(deers,i);
                if(mate != -1)
                {
                    int newSpeed = (deers.get(i).getSpeed()+deers.get(mate).getSpeed())/2;
                    int newStrength = (deers.get(i).getStrength()+deers.get(mate).getStrength())/2;
                    deers.add(new Deer(deers.get(i).getX(),deers.get(i).getY(),5,5,Color.RED,newSpeed+1,newStrength+1));
                }
                if(deers.get(i).checkHunger())
                {
                    deers.remove(i);
                    i--;
                }
                else
                {
                    int foodTarget = deers.get(i).findFood(foods);
                    if(foodTarget != -1)
                    {
                        boolean eaten = deers.get(i).getFood(foods.get(foodTarget));
                        if(eaten)
                        {
                            foods.remove(foodTarget);
                        }
                    }
                    else
                    {
                        deers.get(i).move(boundX, boundY);
                    }
                }
            }
        }
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

            // get the code that belongs to each key on the keyboard
            String key = KeyEvent.getKeyText(e.getKeyCode());
            

        }

    }
    public class MouseHandler extends MouseAdapter{
        @Override
        public void mouseMoved(MouseEvent e){
            
        }
        @Override
        public void mousePressed(MouseEvent e){
            
        }
    }
}