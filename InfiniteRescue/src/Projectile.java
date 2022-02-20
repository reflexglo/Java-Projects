import java.awt.Color;
import java.awt.Graphics;
import javax.swing.*;
import java.awt.*;
public class Projectile{
    
    int x;
    int y;
    Color color;
    int height;
    int width;
    int dmg;
    int xs;
    int ys;
    double dx;
    double dy;
    double dxs;
    double dys;
    Player target;
    int hit;
    int mouse;
    boolean efHit;
    
    public Projectile(int x, int y, int width, int height, int xs, int ys, int pdmg, Color color){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
        this.xs = xs;
        this.ys = ys;
        this.dmg = pdmg;
        this.target = null;
        this.hit = 0;
        this.dx = 0;
        this.dy = 0;
        this.dxs = 0;
        this.dys = 0;
        this.mouse = 0;
        this.efHit = false;
    }
    
    public Projectile(int x, int y, int width, int height, int xs, int ys, int edmg, Player target, Color color){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
        this.xs = xs;
        this.ys = ys;
        this.dmg = edmg;
        this.target = target;
        this.hit = 200;
        this.dx = 0;
        this.dy = 0;
        this.dxs = 0;
        this.dys = 0;
        this.mouse = 0;
        this.efHit = false;
    }
    
    public Projectile(int x, int y, int width, int height, double xt, double yt, boolean efHit, int mdmg, Color color){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
        this.xs = 0;
        this.ys = 0;
        this.dmg = mdmg;
        this.target = null;
        this.hit = 0;
        this.dx = x;
        this.dy = y;
        this.dxs = xt;
        this.dys = yt;
        this.mouse = 1;
        this.efHit = efHit;
    }
    
    public boolean getEfHit()
    {
        return efHit;
    }
    
    public int getHit()
    {
        return hit;
    }
    
    public int getDmg()
    {
        return dmg;
    }
    
    public void move()
    {
        if(target == null)
        {
            if(mouse == 0)
            {
                x += xs;
                y += ys;
            }
            else
            {
                dx += dxs;
                dy += dys;
                x = (int)dx;
                y = (int)dy;
            }
        }
        else
        {
            if(x == target.getX() && y == target.getY())
            {
                hit = 0;
            }
            else
            {
                hit--;
                if(x > target.getX())
                {
                    int nxs = -Math.min(Math.abs(xs), Math.abs(target.getX()-x));
                    x += nxs;
                }
                else if(x < target.getX())
                {
                    int nxs = Math.min(Math.abs(xs), Math.abs(target.getX()-x));
                    x += nxs;
                }
                if(y > target.getY())
                {
                    int nys = -Math.min(Math.abs(ys), Math.abs(target.getY()-y));
                    y += nys;
                }
                else if(y < target.getY())
                {
                    int nys = Math.min(Math.abs(ys), Math.abs(target.getY()-y));
                    y += nys;
                }
            }
        }
    }
    
    public void render(Graphics g){
       
        g.drawRect(x, y, width, height);
         g.setColor(color);
         g.fillRect(x, y, width, height);
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
    
    public Rectangle getBounds(){
        return new Rectangle(x,y,width,height);
    }
    
}