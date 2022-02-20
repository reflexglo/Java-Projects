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
public class Client extends JPanel implements ActionListener
{
    int xDim, yDim, maxHSpeed, dragSpeed, machineSpeed,score,dashTimer,dashRes,dashDel,dashDelTimer,start,end,xin,yin,vine,vLen;
    double hAcc, friction, grav, jumpAcc;
    boolean accL, accR, dbJump, graple, grapling, rec, ren, roping;
    Timer clock;
    Player p;
    ArrayList<Plat> plat;
    ArrayList<Rope> rope;
    Machine machine;
    Machine mouse;
    Plat toGraple;
    Graple hook;
    
    Client(){
        score = 0;
        start = 0;
        end = 0;
        xin = 0;
        yin = 0;
        vine = 0;
        vLen = 0;
        dashDel = 200;
        dashDelTimer = dashDel;
        dashRes = 400;
        dashTimer = dashRes;
        maxHSpeed = 9;
        dragSpeed = 10;
        hAcc = 0.3;
        jumpAcc = -22.5;
        friction = 0.1;
        machineSpeed = 0;
        grav = 0.6;
        accL = false;
        accR = false;
        dbJump = false;
        graple = false;
        grapling = false;
        rec = false;
        ren = false;
        
        p = new Player(500,900,50,50,0,0,Color.BLACK);
        hook = new Graple(0,0,0,0,Color.BLACK);
        mouse = new Machine(0,0,100,100,Color.BLACK);
        machine = new Machine(-3560,-2500,2560,5000,Color.RED);
        plat = new ArrayList<Plat>();
        rope = new ArrayList<Rope>();
        plat.add(new Plat(p.getX()-100,p.getY()+49,300,50,Color.GRAY));
        toGraple = new Plat(0,0,0,0,Color.BLACK);
        int startPlats = (int)(Math.random()*5)+2;
        for(int i = 1;i<startPlats;i++)
        {
            int newDist = (int)(Math.random()*300)+300;
            int newHeight = (int)(Math.random()*500)-250;
            plat.add(new Plat(plat.get(i-1).getX()+plat.get(i-1).getWidth()+newDist,plat.get(i-1).getY()+newHeight,300,50,Color.GRAY));
        }
        
        xDim = 2560;
        yDim = 1440;
        clock = new Timer(10,this);    
        clock.start(); 
        Dimension dim = new Dimension(xDim,yDim);        
        this.setPreferredSize(dim);
        this.setBackground(Color.WHITE);
        this.setVisible(true);
        this.setFocusable(true);
    }
    
    public void drawPlayer(Graphics g)
    {
        p.render(g);
    }
    
    public void drawMachine(Graphics g)
    {
        machine.render(g);
    }
    
    public void drawPlats(Graphics g)
    {
        for(Plat pt:plat)
        {
            pt.render(g);
        }
    }
    
    public void drawRope(Graphics g)
    {
        for(Rope r:rope)
        {
            r.render(g);
        }
    }
    
    public void drawScore(Graphics g)
    {
        g.setColor(Color.BLACK);
        g.setFont(new Font("TimesRoman",Font.BOLD,30));
        g.drawString("Current Score: "+score, p.getX()-1000, p.getY()-500);
    }
    
    public void drawJumpAb(Graphics g)
    {
        if(dbJump)
        {
            g.setColor(Color.BLACK);
        }
        else
        {
            g.setColor(Color.GRAY);
        }
        g.setFont(new Font("TimesRoman",Font.BOLD,15));
        g.drawRect(p.getX()-1000, p.getY()-400, 100, 100);
        g.fillRect(p.getX()-1000, p.getY()-400, 100, 100);
        g.setColor(Color.WHITE);
        g.drawString("Double Jump", p.getX()-1000, p.getY()-350);
    }
    
    public void drawDashAb(Graphics g)
    {
        g.setColor(Color.BLACK);
        g.drawRect(p.getX()-800, p.getY()-400, 100, 100);
        g.fillRect(p.getX()-800, p.getY()-400, 100, 100);
        g.setColor(Color.GRAY);
        g.drawRect(p.getX()-800, p.getY()-400, 100, dashTimer/4);
        g.fillRect(p.getX()-800, p.getY()-400, 100, dashTimer/4);
        g.setColor(Color.WHITE);
        g.drawString("Dash", p.getX()-780, p.getY()-350);
    }

    public void drawGrapleAb(Graphics g)
    {
        if(grapling)
        {
            hook.render(g);
        }
        if(graple)
        {
            g.setColor(Color.BLACK);
        }
        else
        {
            g.setColor(Color.GRAY);
        }
        g.drawRect(p.getX()-1000, p.getY()-200, 100, 100);
        g.fillRect(p.getX()-1000, p.getY()-200, 100, 100);
        g.setColor(Color.WHITE);
        g.drawString("Graple", p.getX()-980, p.getY()-150);
    }
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.translate(1200-p.getX(),700-p.getY());
        drawPlats(g);
        drawPlayer(g);
        drawRope(g);
        drawMachine(g);
        drawScore(g);
        drawJumpAb(g);
        drawDashAb(g);
        drawGrapleAb(g);
        repaint();
    }
    
    public int onGround()
    {
        int index = 0;
        for(Plat pt:plat)
        {
            if(isCollision(p.getBottom(),pt))
            {
                return index;
            }
            index++;
        }
        return -1;
    }
    
    public void leftCheck()
    {
        for(Plat pt:plat)
        {
            int lCheck = pt.getBounds(p.getLeft().getBounds());
            if(lCheck != -1)
            {
                p.setVx(0);
                p.setX(pt.getBound(lCheck).x+pt.getBound(lCheck).width);
                break;
            }
        }
    }
    public void rightCheck()
    {
        for(Plat pt:plat)
        {
            int rCheck = pt.getBounds(p.getRight().getBounds());
            if(rCheck != -1)
            {
                p.setVx(0);
                p.setX(pt.getBound(rCheck).x-p.width);
                break;
            }
        }
    }
    
    public void playerMovement()
    {
        if(dashDelTimer == dashDel)
        {
            p.setVx(p.getVx()/2);
            dashDelTimer = 0;
        }
        if(accR && p.getVx() < maxHSpeed)
        {
            p.setVx(p.getVx()+hAcc);
        }
        if(accL && roping)
        {
            if(rope.get(vine).getAngle()<=0)
            {
                rope.get(vine).setAngVel(rope.get(vine).getExitVel()-(0.001*Math.sin(90+rope.get(vine).getAngle())));
            }
            else
            {
                rope.get(vine).setAngVel(rope.get(vine).getExitVel()-(0.002*Math.sin(rope.get(vine).getAngle())));
            }
        }
        if(accR && roping)
        {
            if(rope.get(vine).getAngle()>=0)
            {
                rope.get(vine).setAngVel(rope.get(vine).getExitVel()+(0.001*Math.sin(90-rope.get(vine).getAngle())));
            }
            else
            {
                rope.get(vine).setAngVel(rope.get(vine).getExitVel()+(0.002*Math.sin(-rope.get(vine).getAngle())));
            }
        }
        else if(accL && p.getVx() > -maxHSpeed)
        {
            p.setVx(p.getVx()-hAcc);
        }
        if(onGround() != -1)
        {
            if(p.getVy() > 0)
            {
                p.setVy(0);
                p.setY(plat.get(onGround()).getStepY(plat.get(onGround()).getBounds(p.getBottom().getBounds()))-49);
                dbJump = false;
            }
            if(p.getVx() > 0)
            {
                p.setVx(p.getVx()-friction);
            }
            else if(p.getVx() < 0)
            {
                p.setVx(p.getVx()+friction);
            }
            graple = false;
        }
        else
        {
            if(p.getVy()<dragSpeed)
            {
                p.setVy(p.getVy()+grav);
            }
            graple = true;
        }
        leftCheck();
        rightCheck();
        p.setX(p.getX()+(int)(p.getVx()));
        p.setY(p.getY()+(int)(p.getVy()));
    }
    
    public void machineMovement()
    {
        for(int i = 0;i<plat.size();i++)
        {
            if(machine.getX() > plat.get(i).getX())
            {
                plat.remove(plat.get(i));
                i--;
            }
        }
        machineSpeed = plat.size()+3;
        machine.setX(machine.getX()+machineSpeed);
        machine.setY(p.getY()-2500);
    }
    
    public void generatePlat(int index)
    {
        int newDist = (int)(Math.random()*300)+300;
        int newHeight = (int)(Math.random()*500)-250;
        int numSteps = (int)(Math.random()*5);
        boolean hasRope = (int)(Math.random()*3) == 0;
        plat.add(new Plat(plat.get(index).getX()+plat.get(index).getWidth()+newDist,plat.get(index).getY()+newHeight,300,50,Color.GRAY));
        if(hasRope)
        {
            rope.add(new Rope(plat.get(plat.size()-1).getX()+plat.get(plat.size()-1).getWidth()/2,plat.get(plat.size()-1).getY()-150,plat.get(plat.size()-1).getX()+plat.get(plat.size()-1).getWidth()/2,plat.get(plat.size()-1).getY()-1000,Color.BLACK));
        }
        /*for(int i = 0;i < numSteps;i++)
        {
         int stepX = (int)(Math.random()*plat.get(plat.size()-1).getWidth())+plat.get(plat.size()-1).getX();
         int stepY = plat.get(plat.size()-1).getY()-(int)(Math.random()*plat.get(plat.size()-1).getHeight())-100;
         int stepWidth = (int)(Math.random()*plat.get(plat.size()-1).getWidth())+50;
         int stepHeight = plat.get(plat.size()-1).getY()-stepY;
         plat.get(plat.size()-1).addStep(new Rectangle(stepX,stepY,stepWidth,stepHeight));
        }*/
        score++;
    }
    
    public int getLowestPlat()
    {
        int min = 0;
        for(Plat pt:plat)
        {
            if(pt.getY() > min)
            {
                min = pt.getY();
            }
        }
        return min;
    }
    
    public void playerGraple()
    {
        if(hook.grapleMovement() == 0)
        {
            p.setX(plat.get(plat.indexOf(toGraple)).getX()+50);
            p.setY(plat.get(plat.indexOf(toGraple)).getY()-50);
            grapling = false;
        }
        else
        {
            p.setX(hook.getX());
            p.setY(hook.getY());
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        playerMovement();
        machineMovement();
        if(rec)
        {
            start = (int)e.getWhen();
            rec = false;
        }
        if(ren)
        {
            end = (int)e.getWhen();
            int gTime = (end-start)/20;
            int gDistX = p.getX()-xin;
            int gDistY = p.getY()-yin;
            if(gTime != 0)
            {
                int newVx = gDistX/gTime;
                int newVy = gDistY/gTime;
                p.setVx(newVx);
                p.setVy(newVy);
            }
            else
            {
                p.setVx(0);
                p.setVy(0);
            }
            ren = false;
        }
        if(grapling)
        {
            playerGraple();
        }
        if(p.getX() > plat.get(plat.size()-2).getX())
        {
            generatePlat(plat.size()-1);
        }
        if(p.getY() > getLowestPlat()*2)
        {
            System.exit(0);
        }
        if(isCollision(p,machine))
        {
            System.exit(0);
        }
        if(dashTimer > 0)
        {
            dashTimer--;
        }
        if(dashDelTimer < dashDel)
        {
            dashDelTimer++;
        }
        if(!roping)
        {
            for(int i = 0;i<rope.size();i++)
            {
                if(isCollision(p,rope.get(i)))
                {
                    roping = true;
                    vine = i;
                    vLen = rope.get(i).getHeight()-p.getY();
                    rope.get(i).setAngVel((-vLen/rope.get(i).getLength())*(double)(p.getVx())/25);
                    break;
                }
            }
        }
        else
        {
            p.setX(rope.get(vine).getWidth()-(int)(vLen*Math.sin(rope.get(vine).getAngle())));
            p.setY(rope.get(vine).getHeight()-(int)(vLen*Math.cos(rope.get(vine).getAngle())));
        }
        for(int i = 0;i<rope.size();i++)
        {
            rope.get(i).move();
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
        if(key.equals("Space") && onGround() != -1)
        {
            p.setVy(jumpAcc);
            dbJump = true;
        }
        else if(key.equals("Space") && dbJump)
        {
            p.setVy(jumpAcc);
            dbJump = false;
        }
        else if(key.equals("Space") && grapling)
        {
              ren = true;
              grapling = false;
        }
        if(key.equals("Space") && roping)
        {
            roping = false;
            p.setVx((int)(rope.get(vine).getExitVel()*100));
        }
        if(key.equals("D"))
        {
            accR = true;
        }
        if(key.equals("A"))
        {
            accL = true;
        }
        if(key.equals("E") && dashTimer == 0)
        {
            if(accR)
            {
                p.setVx(2*maxHSpeed);
                dashTimer = dashRes;
                dashDelTimer = 0;
            }
            else if(accL)
            {
                p.setVx(-2*maxHSpeed);
                dashTimer = dashRes;
                dashDelTimer = 0;
            }
        }
      }
      @Override
      public void keyReleased(KeyEvent e)
      {
          String key = KeyEvent.getKeyText(e.getKeyCode());
          if(key.equals("D"))
        {
            accR = false;
        }
        if(key.equals("A"))
        {
            accL = false;
        }
      }
    
     
}
     public class MouseHandler extends MouseAdapter
    {
      @Override
      public void mouseMoved(MouseEvent e){
         mouse.setX(p.getX()+e.getX()-1250);
         mouse.setY(p.getY()+e.getY()-750);
      }
      @Override
      public void mousePressed(MouseEvent e)
      {
          if(grapling)
          {
              ren = true;
              grapling = false;
          }
          if(graple && mouse.getY()+200 < p.getY() && !grapling)
          {
              
              int index = -1;
              for(Plat pt:plat)
              {
                  if(isCollision(mouse,pt))
                  {
                      index = plat.indexOf(pt);
                  }
              }
              if(index!=-1)
              {
                  grapling = true;
                  rec = true;
                  toGraple = plat.get(index);
                  xin = p.getX();
                  yin = p.getY();
                  hook.setX(p.getX());
                  hook.setY(p.getY());
                  hook.setWidth(plat.get(index).getX()+plat.get(index).getWidth()/2);
                  hook.setHeight(plat.get(index).getY()+plat.get(index).getHeight()/2);
              }
          }
      }
     } 
    private boolean isCollision(Player p, Plat w) {
        boolean collision = w.getBounds(p.getBounds()) != -1;
        return collision;
    }
    private boolean isCollision(Machine p, Plat w) {
        boolean collision = w.getBounds(p.getBounds()) != -1;
        return collision;
    }
    private boolean isCollision(Player p, Machine w) {
        boolean collision = p.getBounds().intersects(w.getBounds());
        return collision;
    }
    private boolean isCollision(Player p, Rope w) {
        boolean collision = p.getBounds().intersects(w.getBounds());
        return collision;
    }
}
