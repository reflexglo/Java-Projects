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
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.Timer;
public class Client extends JPanel implements ActionListener
{

    Timer clock;
    Room rcur, rnext;
    Player player;
    Mouse mouse;
    Upgrade[] upg;
    boolean roomFin,priWp,eff;
    int gameState, roomCounter, startEn, money;
    
    Client(){
        upg = new Upgrade[6];
        for(int i = 0;i<upg.length;i++)
        {
            upg[i] = new Upgrade(0,0,400,50,i,Color.BLACK);
        }
        mouse = new Mouse(0,0,1,1,Color.BLACK);
        money = 0;
        roomCounter = 0;
        gameState = 0;
        roomFin = false;
        priWp = true;
        eff = false;
        player = new Player(800,500,25,25,Color.BLUE);
        rcur = new Room(600,300,500,700,1,Color.BLACK);
        rcur.genEnemies();
        startEn = rcur.getNumEn();
        int xDim = 2560;
        int yDim = 1440;
        clock = new Timer(10,this);    
        clock.start(); 
        Dimension dim = new Dimension(xDim,yDim);        
        this.setPreferredSize(dim);
        this.setBackground(Color.WHITE);
        this.setVisible(true);
        this.setFocusable(true);
    }
    
    public void drawRooms(Graphics g)
    {
        if(rnext != null)
        {
            rnext.render(g);
        }
        rcur.render(g);
    }
    
    public void drawPlayer(Graphics g)
    {
        player.render(g);
    }
    
    public void drawStats(Graphics g)
    {
        double tx = mouse.getX()+player.getX()-1200;
        double ty = mouse.getY()+player.getY()-700;
        double px = player.getX()+player.getWidth()/2;
        double py = player.getY()+player.getHeight()/2;
        double slope = Math.abs((ty-py)/(tx-px));
            double dx = 1;
            double dy = slope;
            double co = 5/(Math.sqrt(dx*dx+dy*dy));
            dx = co;
            dy = co*slope;
            if(ty < py)
            {
                dy = -co*slope;
            }
            if(tx < px)
            {
                dx = -co;
            }
        g.setColor(Color.BLACK);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 50));
        
        g.setColor(Color.BLACK);
        g.drawRect(100+player.getX()-1200, 50+player.getY()-700, player.getHealthMax()*4+100, 150);
        g.fillRect(100+player.getX()-1200, 50+player.getY()-700, player.getHealthMax()*4+100, 150);
        g.setColor(Color.RED);
        g.drawRect(150+player.getX()-1200, 75+player.getY()-700, player.getHealthMax()*4, 100);
        g.fillRect(150+player.getX()-1200, 75+player.getY()-700, player.getHealth()*4, 100);
        
        g.setColor(Color.BLUE);
        double per = (((double)rcur.getNumEn())/((double)startEn))*100;
        g.drawString("Enemies Left: "+(int)per+"%", 100+player.getX()-1200,250+player.getY()-700);
        
        g.setColor(Color.RED);
        g.drawString("Room #"+(roomCounter+1), 100+player.getX()-1200,325+player.getY()-700);
        
        g.setColor(Color.GREEN);
        g.drawString("$"+money, 100+player.getX()-1200,400+player.getY()-700);
        
        if(priWp)
        {
            for(int i = 0;i<player.getAmmo();i++)
            {
                g.setColor(Color.ORANGE);
                g.fillRect(100+player.getX()-1200+i*50, 450+player.getY()-700, 25, 100);
                g.fillOval(100+player.getX()-1200+i*50, 425+player.getY()-700, 25, 125);
            }
        }
        else
        {
            if(player.getEfTimer() == 0 && eff == false)
            {
                g.setColor(Color.BLUE);
                g.fillOval(100+player.getX()-1200, 425+player.getY()-700, 120, 120);
                g.setColor(Color.CYAN);
                g.fillOval(115+player.getX()-1200, 440+player.getY()-700, 90, 90);
                g.setColor(Color.WHITE);
                g.fillOval(130+player.getX()-1200, 455+player.getY()-700, 60, 60);
            }
            else
            {
                g.setColor(Color.BLACK);
                g.fillOval(100+player.getX()-1200, 425+player.getY()-700, 120, 120);
                g.setColor(Color.GRAY);
                g.fillOval(115+player.getX()-1200, 440+player.getY()-700, 90, 90);
                g.setColor(Color.WHITE);
                g.fillOval(130+player.getX()-1200, 455+player.getY()-700, 60, 60);
            }
        }
        
        if(priWp)
        {
            g.setColor(Color.BLACK);
            g.fillOval(175+player.getX()-1200, 600+player.getY()-700, 150, 200);
            g.setColor(Color.RED);
            g.fillOval(200+player.getX()-1200, 625+player.getY()-700, 100, 150);
            g.setColor(Color.BLACK);
            g.drawRect(100+player.getX()-1200, 600+player.getY()-700, 400, 100);
            g.fillRect(100+player.getX()-1200, 600+player.getY()-700, 400, 100);
            g.drawRect(150+player.getX()-1200, 600+player.getY()-700, 100, 300);
            g.fillRect(150+player.getX()-1200, 600+player.getY()-700, 100, 300);
        }
        else
        {
            g.setColor(Color.CYAN);
            g.fillOval(175+player.getX()-1200, 600+player.getY()-700, 150, 200);
            g.setColor(Color.MAGENTA);
            g.fillOval(200+player.getX()-1200, 625+player.getY()-700, 100, 150);
            g.setColor(Color.CYAN);
            g.drawRect(150+player.getX()-1200, 600+player.getY()-700, 100, 300);
            g.fillRect(150+player.getX()-1200, 600+player.getY()-700, 100, 300);
            g.drawRect(450+player.getX()-1200, 600+player.getY()-700, 100, 300);
            g.fillRect(450+player.getX()-1200, 600+player.getY()-700, 100, 300);
            g.setColor(Color.BLUE);
            g.drawRect(100+player.getX()-1200, 600+player.getY()-700, 600, 100);
            g.fillRect(100+player.getX()-1200, 600+player.getY()-700, 600, 100);
            g.setColor(Color.CYAN);
            g.drawRect(150+player.getX()-1200, 625+player.getY()-700, 500, 50);
            g.fillRect(150+player.getX()-1200, 625+player.getY()-700, 500, 50);
        }
    }
    public void drawGameScreens(Graphics g)
    {
        if(gameState == 0)
        {
            g.setColor(Color.BLACK);
            g.setFont(new Font("TimesRoman", Font.PLAIN, 50));
            g.drawString("Press Space To Play",1000,500);
        }
        else if(gameState == 2)
        {
            g.setColor(Color.BLACK);
            g.setFont(new Font("TimesRoman", Font.PLAIN, 50));
            g.drawString("Game Over", 1100,500);
            g.drawString("Press Space To Play",1000,600);
        }
    }
    
    public void drawStore(Graphics g)
    {
        if(roomFin)
        {
            g.drawRect(1800+player.getX()-1200, 50+player.getY()-700, 600, 1200);
            g.setColor(Color.GRAY);
            g.fillRect(1800+player.getX()-1200, 50+player.getY()-700, 600, 1200);
            g.setColor(Color.WHITE);
            g.setFont(new Font("TimesRoman", Font.PLAIN, 50));
            g.drawString("Store", 2050+player.getX()-1200, 150+player.getY()-700);
            
            for(int i = 0;i<upg.length;i++)
            {
                upg[i].setX(1900+player.getX()-1200);
                upg[i].setY(250+player.getY()-700+i*100);
                upg[i].render(g);
            }
        }
    }
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        if(gameState == 1)
        {
            g.translate(1200-player.getX(),700-player.getY());
            drawRooms(g);
            drawPlayer(g);
            drawStats(g);
            drawStore(g);
        }
        else
        {
            drawGameScreens(g);
        }
        repaint();
    }
    
    
   public void addNotify(){
       super.addNotify();
         addKeyListener(new KeyHandler());
         addMouseListener(new MouseHandler());
      addMouseMotionListener(new MouseHandler());
   }
   
   public void checkEnemyShots()
   {
       for(int i = 0;i<rcur.getNumEn();i++)
       {
           for(int j = 0;j<rcur.getEnemy(i).getNumProj();j++)
           {
               if(isCollision(player.getBounds(),rcur.getEnemy(i).getProj(j).getBounds()))
               {
                   player.setHealth(player.getHealth()-rcur.getEnemy(i).getProjDmg(j));
                   rcur.addAnimation(i, new Animation(rcur.getEnemy(i).getProj(j).getX(),rcur.getEnemy(i).getProj(j).getY(),10,10,10,0,Color.RED));
                   rcur.removeEnProj(i, j);
                   j--;
               }
           }
       }
   }
   
   public void checkPlayerShots()
   {
       for(int i = 0;i<player.getNumProj();i++)
       {
           boolean hit = false;
           for(int j = 0;j<rcur.getNumEn();j++)
           {
               if(isCollision(player.getProj(i).getBounds(),rcur.getEnemy(j).getBounds()))
               {
                   rcur.setEnemyHealth(j,rcur.getEnemyHealth(j)-player.getProjDmg(i));
                   player.addAnimation(new Animation(player.getProj(i).getX(),player.getProj(i).getY(),10,10,10,1,Color.RED));
                   if(rcur.getEnemyHealth(j) <= 0)
                   {
                        money += 10*rcur.getEnemy(j).getDmg();
                        rcur.removeEnemy(j);
                   }
                   if(player.getProj(i).getEfHit())
                   {
                       player.setEfBounds(player.getProj(i).getX(),player.getProj(i).getY());
                       player.addAnimation(new Animation(player.getProj(i).getX()-200,player.getProj(i).getY()-200,400,400,50,3,Color.CYAN));
                       player.setEfHit(true);
                   }
                   player.removeProj(i);
                   hit = true;
                   i--;
                   break;
               }
           }
           if(hit == false)
           {
               for(int k = 0;k<4;k++)
               {
                   if(isCollision(player.getProj(i).getBounds(),rcur.getWall(k)))
                   {
                        if(player.getProj(i).getEfHit())
                        {
                            player.setEfBounds(player.getProj(i).getX(),player.getProj(i).getY());
                            player.addAnimation(new Animation(player.getProj(i).getX()-200,player.getProj(i).getY()-200,400,400,50,3,Color.CYAN));
                            player.setEfHit(true);
                        }
                        player.removeProj(i);
                        hit = true;
                        i--;
                        break;
                   }
               }
           }
       }
   }
   
   public void resetGame()
   {
        roomCounter = 0;
        gameState = 1;
        roomFin = false;
        player = new Player(800,500,25,25,Color.BLUE);
        rcur = new Room(600,300,500,700,1,Color.BLACK);
        rcur.genEnemies();
        startEn = rcur.getNumEn();
   }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(gameState == 1)
        {
            for(int i = 0;i<rcur.getNumEn();i++)
            {
                if(player.getEfBounds() != null)
                   {
                       if(isCollision(rcur.getEnemy(i).getBounds(),player.getEfBounds()))
                       {
                           rcur.setEnemyHealth(i,rcur.getEnemyHealth(i)-player.getEfDmg());
                           if(rcur.getEnemyHealth(i) <= 0)
                            {
                                 money += 10*rcur.getEnemy(i).getDmg();
                                 rcur.removeEnemy(i);
                                 i--;
                            }
                       }
                   }
            }
            if(roomFin == true && rnext.inArea(player))
            {
                roomCounter++;
                rcur = rnext;
                rnext = null;
                roomFin = false;
                rcur.closeEntrance();
                rcur.genEnemies();
            }
            if(rcur.getNumEn() == 0 && roomFin == false)
            {
                rcur.openDoor();
                rnext = rcur.genNext();
                roomFin = true;
            }
            for(int i = 0;i<4;i++)
            {
                Wall col = rcur.getWall(i);
                if(isCollision(player.getLBounds(),col))
                {
                    player.setLeft(false);
                }
                if(isCollision(player.getRBounds(),col))
                {
                    player.setRight(false);
                }
                if(isCollision(player.getUBounds(),col))
                {
                    player.setUp(false);
                }
                if(isCollision(player.getDBounds(),col))
                {
                    player.setDown(false);
                }
            }
            if(rnext != null)
            {
                for(int i = 0;i<4;i++)
                {
                    Wall col = rnext.getWall(i);
                    if(isCollision(player.getLBounds(),col))
                    {
                        player.setLeft(false);
                    }
                    if(isCollision(player.getRBounds(),col))
                    {
                        player.setRight(false);
                    }
                    if(isCollision(player.getUBounds(),col))
                    {
                        player.setUp(false);
                    }
                    if(isCollision(player.getDBounds(),col))
                    {
                        player.setDown(false);
                    }
                }
            }
            if(player.getHealth() <= 0)
            {
                gameState = 2;
            }
            if(player.getEfTimer() != 0)
            {
                eff = false;
            }
            rcur.update(player);
            player.update();
            checkPlayerShots();
            checkEnemyShots();
        }
    }
    public class KeyHandler extends KeyAdapter implements KeyListener {
      @Override
      public void keyPressed(KeyEvent e) {        
        String key = KeyEvent.getKeyText(e.getKeyCode());
        if(gameState == 1)
        {
            if(key.equals("Up") && player.getAmmo() > 0)
            {
                player.shoot(0);
            }
            if(key.equals("Down") && player.getAmmo() > 0)
            {
                player.shoot(2);
            }
            if(key.equals("Left") && player.getAmmo() > 0)
            {
                player.shoot(3);
            }
            if(key.equals("Right") && player.getAmmo() > 0)
            {
                player.shoot(1);
            }
            if(key.equals("W"))
              {
                  player.setUp(true);
              }
            if(key.equals("S"))
              {
                  player.setDown(true);
              }
            if(key.equals("A"))
              {
                  player.setLeft(true);
              }
            if(key.equals("D"))
              {
                  player.setRight(true);
              }
            if(key.equals("E"))
            {
                priWp = !priWp;
            }
            if(key.equals("Space"))
            {
                if(player.getAbTimer() == 0)
                {
                    for(int i = 0;i<rcur.getNumEn();i++)
                    {
                        boolean hit = false;
                        if(isCollision(rcur.getEnemy(i).getBounds(),player.getAbBounds()))
                        {
                            rcur.setEnemyHealth(i,rcur.getEnemyHealth(i)-player.getAbDmg());
                            if(rcur.getEnemyHealth(i) <= 0)
                            {
                                 money += 10*rcur.getEnemy(i).getDmg();
                                 rcur.removeEnemy(i);
                                 if(player.getHealth() + 10 > player.getHealthMax())
                                 {
                                     player.setHealth(player.getHealthMax());
                                 }
                                 else
                                 {
                                     player.setHealth(player.getHealth()+10);
                                 }
                                 i--;
                                 hit = true;
                            }
                        }
                        if(hit == false)
                        {
                            for(int j = 0;j<rcur.getEnemy(i).getNumProj();j++)
                            {
                                if(isCollision(rcur.getEnemy(i).getProj(j).getBounds(),player.getAbBounds()))
                                {
                                    if(player.getHealth() + 5 > player.getHealthMax())
                                    {
                                        player.setHealth(player.getHealthMax());
                                    }
                                    else
                                    {
                                        player.setHealth(player.getHealth()+5);
                                    }
                                    rcur.removeEnProj(i, j);
                                    j--;
                                }
                            }
                        }
                    }
                    player.startAb();
                    player.addAnimation(new Animation(player.getX()-3*player.getWidth(),player.getY()-3*player.getHeight(),7*player.getWidth(),7*player.getHeight(),20,2,Color.RED));
                }
            }
        }
        else if(gameState == 0)
        {
            if(key.equals("Space"))
            {
                gameState = 1;
            }
        }
        else if(gameState == 2)
        {
            if(key.equals("Space"))
            {
                resetGame();
            }
        }
      }
      @Override
      public void keyReleased(KeyEvent e)
      {
          String key = KeyEvent.getKeyText(e.getKeyCode());
        if(gameState == 1)
        {
            if(key.equals("W"))
            {
                player.setUp(false);
            }
          if(key.equals("S"))
            {
                player.setDown(false);
            }
          if(key.equals("A"))
            {
                player.setLeft(false);
            }
          if(key.equals("D"))
            {
                player.setRight(false);
            }
        }
      }
}
     public class MouseHandler extends MouseAdapter
    {
      @Override
      public void mouseMoved(MouseEvent e){
         mouse.setX(e.getX()+player.getX()-1200);
         mouse.setY(e.getY()+player.getY()-700);
      }
      @Override
      public void mousePressed(MouseEvent e)
      {
        if(roomFin)
          {
              for(int i = 0;i<upg.length;i++)
              {
                  if(isCollision(mouse.getBounds(),upg[i].getBounds()))
                  {
                      if(money >= upg[i].getPrice())
                      {
                          money -= upg[i].getPrice();
                          player = upg[i].upgrade(player);
                      }
                  }
              }
          }
        else
        {
            double tx = mouse.getX();
            double ty = mouse.getY();
            double px = player.getX()+player.getWidth()/2;
            double py = player.getY()+player.getHeight()/2;
            if(tx != px && (tx != 0 || px != 0))
            {
               double slope = Math.abs((ty-py)/(tx-px));
               double dx = 1;
               double dy = slope;
               double co = 5/(Math.sqrt(dx*dx+dy*dy));
               dx = co;
               dy = co*slope;
               if(ty < py)
               {
                   dy = -co*slope;
               }
               if(tx < px)
               {
                   dx = -co;
               }
               if(dx != 0 || dy != 0)
               {

                   if(priWp)
                   {
                       if(player.getAmmo() > 0)
                       {
                           player.shootMouse(dx, dy, false, Color.BLACK);
                       }
                   }
                   else
                   {
                       if(player.getEfTimer() == 0 && eff == false)
                       {
                           player.shootMouse(dx, dy, true, Color.BLUE);
                           eff = true;
                       }
                   }
               }
            }
            else
            {
               double dx = 0;
               double dy = 5;
               if(ty < py)
               {
                   dy = -5;
               }
               if(dx != 0 || dy != 0)
               {
                   if(priWp)
                   {
                       if(player.getAmmo() > 0)
                       {
                           player.shootMouse(dx, dy, false, Color.BLACK);
                       }
                   }
                   else
                   {
                       if(player.getEfTimer() == 0 && eff == false)
                       {
                           player.shootMouse(dx, dy, true, Color.BLUE);
                           eff = true;
                       }
                   }
               }
            }
        }
         
      }
     } 
     private boolean isCollision(Rectangle p, Rectangle e) {
            return p.intersects(e);
        }
    private boolean isCollision(Rectangle p, Wall w) {
            return w.checkBounds(p);
        }
}
