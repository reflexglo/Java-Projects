import java.awt.Color;
import java.awt.Graphics;
import java.awt.*;
import java.util.ArrayList;
public class Enemy{
    
    int x;
    int y;
    int xs;
    int ys;
    Color color;
    int height;
    int width;
    int health;
    int healthMax;
    int cool;
    int coolMax;
    int moveTimer;
    int moveTimerMax;
    int dmg;
    ArrayList<Projectile> projs;
    ArrayList<Animation> anms;
    
    public Enemy(int x, int y, int width, int height, int health, int dmg, Color color){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.health = health*dmg;
        this.healthMax = health*dmg;
        this.color = color;
        this.cool = 200;
        this.coolMax = 200;
        this.projs = new ArrayList<Projectile>();
        this.anms = new ArrayList<Animation>();
        this.xs = 0;
        this.ys = 0;
        this.moveTimer = 25;
        this.moveTimerMax = 25;
        this.dmg = dmg;
    }
    
    public int getDmg()
    {
        return dmg;
    }
    
    public int getProjDmg(int i)
    {
        return projs.get(i).getDmg();
    }
    
    public void removeProj(int i)
    {
        projs.remove(i);
    }
    
    public Projectile getProj(int i)
    {
        return projs.get(i);
    }
    
    public int getNumProj()
    {
        return projs.size();
    }
    
    public void shoot(Player player)
    {
        projs.add(new Projectile(x+width/2,y+width/2,10,10,1,1,dmg,player,Color.GREEN));
    }
    
    public void addAnimation(Animation ani)
    {
        anms.add(ani);
    }
    
    public void move(Wall[] walls)
    {
        if(moveTimer == 0)
        {
            int maxSpeed = 3;
            int xacc = (int)(Math.random()*3)-1;
            int yacc = (int)(Math.random()*3)-1;
            if(xs + xacc > -maxSpeed && xs + xacc < maxSpeed)
            {
                xs += xacc;
            }
            if(ys + yacc > -maxSpeed && ys + yacc < maxSpeed)
            {
                ys += yacc;
            }
            moveTimer = moveTimerMax;
        }
        else
        {
            moveTimer--;
        }
        for(int i = 0;i<walls.length;i++)
            {
                if(getLBounds().intersects(walls[i].getBounds()))
                {
                    xs = -xs;
                }
                if(getRBounds().intersects(walls[i].getBounds()))
                {
                    xs = -xs;
                }
                if(getUBounds().intersects(walls[i].getBounds()))
                {
                    ys = -ys;
                }
                if(getDBounds().intersects(walls[i].getBounds()))
                {
                    ys = -ys;
                }
            }
        x += xs;
        y += ys;
    }
    
    public void update(Player player, Wall[] walls)
    {
        if(cool > 0)
        {
            cool--;
        }
        else
        {
            shoot(player);
            cool = coolMax;
        }
        for(int i = 0;i<projs.size();i++)
        {
            if(projs.get(i).getHit() <= 0)
            {
                projs.remove(i);
                i--;
            }
            else
            {
                projs.get(i).move();
            }
        }
        for(int i = 0;i<anms.size();i++)
        {
            anms.get(i).animate();
            if(anms.get(i).getTimer() <= 0)
            {
                anms.remove(i);
                i--;
            }
        }
        move(walls);
    }
    
    public void render(Graphics g){
       color = new Color(255-dmg,0,0);
        g.drawRect(x, y, width, height);
         g.setColor(color);
         g.fillRect(x, y, width, height);
         if(health > 0){
          double newHealth = (((double)health)/((double)healthMax))*25;
          g.drawRect(x, y-15, (int)(newHealth), 10);
         g.setColor(Color.GREEN);
         g.fillRect(x, y-15, (int)(newHealth), 10);
         }
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
    public void setHealth(int health){
        this.health = health;
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public int getHealth(){
        return health;
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
    
    public Rectangle getBounds(){
        return new Rectangle(x,y,width,height);
    }
    
}