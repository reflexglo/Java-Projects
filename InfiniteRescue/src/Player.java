import java.awt.Color;
import java.awt.Graphics;
import java.awt.*;
import java.util.ArrayList;

/**
 *
 * @author Mr. Maroney
 */
public class Player {
    
    int x;
    int y;
    Color color;
    int height;
    int width;
    int health;
    int healthMax;
    int abTimer;
    int abTimerMax;
    int abDmg;
    int efTimer;
    int efTimerMax;
    int efDmg;
    int ammo;
    int ammoMax;
    int relTimer;
    int relTimerMax;
    int priDmg;
    boolean left, right, up, down, efHit;
    ArrayList<Projectile> projs;
    ArrayList<Animation> anms;
    Rectangle efArea;
    
    public Player(int x, int y, int width, int height, Color color){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.health = 100;
        this.healthMax = 100;
        this.abTimer = 100;
        this.abTimerMax = 250;
        this.abDmg = 100;
        this.efTimer = 250;
        this.efTimerMax = 250;
        this.efDmg = 2;
        this.color = color;
        this.left = false;
        this.right = false;
        this.up = false;
        this.down = false;
        this.projs = new ArrayList<Projectile>();
        this.anms = new ArrayList<Animation>();
        this.efArea = null;
        this.efHit = false;
        this.ammo = 15;
        this.ammoMax = 15;
        this.relTimer = 250;
        this.relTimerMax = 250;
        this.priDmg = 100;
    }
    
    public void setPriDmg(int priDmg)
    {
        this.priDmg = priDmg;
    }
    
    public int getPriDmg()
    {
        return priDmg;
    }
    
    public int getPriRelTimer()
    {
        return relTimerMax;
    }
    
    public void setPriRelTimer(int time)
    {
        relTimerMax = time;
    }
    
    public int getAmmo()
    {
        return ammo;
    }
    
    public void setEfDmg(int efDmg)
    {
        this.efDmg = efDmg;
    }
    
    public int getEfDmg()
    {
        return efDmg;
    }
    
    public void setEfHit(boolean efHit)
    {
        this.efHit = efHit;
    }
    
    public void shootMouse(double xs, double ys, boolean efHit, Color color)
    {
        projs.add(new Projectile(x+width/2,y+width/2,10,10,xs,ys,efHit,priDmg,color));
        ammo--;
    }
    
    public int getHealthMax()
    {
        return healthMax;
    }
    
    public void addAnimation(Animation ani)
    {
        anms.add(ani);
    }
    
    public void setHealth(int health)
    {
        this.health = health;
    }
    
    public int getHealth()
    {
        return health;
    }
    
    public int getProjDmg(int i)
    {
        return projs.get(i).getDmg();
    }
    
    public void removeProj(int i)
    {
        projs.remove(i);
    }
    
    public int getNumProj()
    {
        return projs.size();
    }
    
    public Projectile getProj(int i)
    {
        return projs.get(i);
    }
    
    public void shoot(int dir)
    {
        if(dir == 0)
        {
            projs.add(new Projectile(x+width/2,y+width/2,10,10,0,-5,priDmg,Color.BLACK));
        }
        if(dir == 1)
        {
            projs.add(new Projectile(x+width/2,y+width/2,10,10,5,0,priDmg,Color.BLACK));
        }
        if(dir == 2)
        {
            projs.add(new Projectile(x+width/2,y+width/2,10,10,0,5,priDmg,Color.BLACK));
        }
        if(dir == 3)
        {
            projs.add(new Projectile(x+width/2,y+width/2,10,10,-5,0,priDmg,Color.BLACK));
        }
        ammo--;
    }
    
    public void moveProj()
    {
        for(int i = 0;i<projs.size();i++)
        {
            projs.get(i).move();
        }
    }
    
    public void update()
    {
        moveProj();
        move();
        for(int i = 0;i<anms.size();i++)
        {
            if(anms.get(i).getTimer() <= 0)
            {
                anms.remove(i);
                i--;
            }
            else
            {
                anms.get(i).animate();
            }
        }
        if(efTimer > 0)
        {
            efTimer--;
        }
        if(abTimer > 0)
        {
            abTimer--;
        }
        if(efHit && efTimer > -efTimerMax/5)
        {
            efTimer--;
        }
        if(efHit && efTimer <= -efTimerMax/5)
        {
            efHit = false;
            efArea = null;
            efTimer = efTimerMax;
        }
        if(relTimer > 0 && ammo <= 0)
        {
            relTimer--;
        }
        if(relTimer == 0 && ammo <= 0)
        {
            ammo = ammoMax;
            relTimer = relTimerMax;
        }
    }
    
    public void move()
    {
        if(right)
        {
            x += 5;
        }
        if(left)
        {
            x -= 5;
        }
        if(up)
        {
            y -= 5;
        }
        if(down)
        {
            y += 5;
        }
    }
    
    public void setAbDmg(int abDmg)
    {
        this.abDmg = abDmg;
    }
    
    public int getAbDmg()
    {
        return abDmg;
    }
    
    public int getAbTimerMax()
    {
        return abTimerMax;
    }
    
    public void setAbTimerMax(int time)
    {
        abTimerMax = time;
    }
    
    public int getAbTimer()
    {
        return abTimer;
    }
    
    public void startAb()
    {
        abTimer = abTimerMax;
    }
    
    public int getEfTimerMax()
    {
        return efTimerMax;
    }
    
    public void setEfTimerMax(int time)
    {
        efTimerMax = time;
    }
    
    public int getEfTimer()
    {
        return efTimer;
    }
    
    public void startEf()
    {
        efTimer = efTimerMax;
    }
    
    public void setLeft(boolean left)
    {
        this.left = left;
    }
    public void setRight(boolean right)
    {
        this.right = right;
    }
    public void setUp(boolean up)
    {
        this.up = up;
    }
    public void setDown(boolean down)
    {
        this.down = down;
    }
    
    public void render(Graphics g){
        g.setColor(color);
        g.drawRect(x, y, width, height);
        g.fillRect(x, y, width, height);
        for(int i = 0;i<projs.size();i++)
        {
            projs.get(i).render(g);
        }
        for(int i = 0;i<anms.size();i++)
        {
            anms.get(i).render(g);
        }
    }
    public void setX(int x){
        this.x = x;
    }
    
    public void setY(int y){
        this.y = y; 
    }
    
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    
    public int getWidth(){
        return width;
    }
    public int getHeight(){
        return height;
    }

    public Rectangle getLBounds()
    {
        return new Rectangle(x-5,y+5,10,height-10);
    }
    public Rectangle getRBounds()
    {
        return new Rectangle(x+width-5,y+5,10,height-10);
    }
    public Rectangle getUBounds()
    {
        return new Rectangle(x+5,y-5,width-10,10);
    }
    public Rectangle getDBounds()
    {
        return new Rectangle(x+5,y+height-5,width-10,10);
    }
    
    public Rectangle getAbBounds()
    {
        return new Rectangle(x-width*3,y-height*3,7*width,7*height);
    }
    
    public void setEfBounds(int rx, int ry)
    {
        efArea = new Rectangle(rx-200,ry-200,400,400);
    }
    
    public Rectangle getEfBounds()
    {
        return efArea;
    }
    
    public Rectangle getBounds(){
        return new Rectangle(x,y,width,height);
    }
    
}
