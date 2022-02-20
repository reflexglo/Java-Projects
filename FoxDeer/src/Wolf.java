import java.awt.Color;
import java.awt.Graphics;
import java.awt.*;
import java.util.ArrayList;

/**
 *
 * @author Mr. Maroney
 */
public class Wolf{
    
    int x;
    int y;
    Color color;
    int height;
    int width;
    int radius;
    int speed;
    int strength;
    int[] dir;
    int killCount;
    final int dirChange = 20;
    String name;
    
    
    public Wolf(int x, int y, int width, int height, Color color, String name){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
        this.radius = 100;
        this.speed = (int)(Math.random()*3)+5;
        this.strength = (int)(Math.random()*5)+5;
        this.dir = new int[4];
        this.dir[0] = 0;
        this.dir[1] = 0;
        this.dir[2] = 0;
        this.dir[3] = 0;
        this.dir[(int)(Math.random()*4)] = 1;
        this.killCount = 0;
        this.name = name;
    }
    
    public void render(Graphics g){
        g.drawRect(x, y, width, height);
        g.setColor(color);
        g.fillRect(x, y, width, height);
        g.drawString(""+name, x, y-10);
    }
    
    public int detect(ArrayList<Deer> deer)
    {
        int minDist = radius;
        int minDeer = -1;
        for(int i = 0;i<deer.size();i++)
        {
            int dist = (int)(Math.sqrt((Math.pow(deer.get(i).getX()-x,2))+(Math.pow(deer.get(i).getY()-y,2))));
            if(dist < minDist)
            {
                minDist = dist;
                minDeer = i;
            }
        }
        return minDeer;
    }
    
    public boolean hunt(Deer target)
    {
        if(killCount % 20 == 0 && killCount != 0)
        {
            killCount = 0;
            strength++;
            speed++;
        }
        if(target.getX() < x)
        {
            if(target.getX() > x-speed)
            {
                x = target.getX();
            }
            else
            {
                x = x - speed;
            }
        }
        else if(target.getX() > x)
        {
            if(target.getX() < x+speed)
            {
                x = target.getX();
            }
            else
            {
                x = x + speed;
            }
        }
        if(target.getY() < y)
        {
           if(target.getY() > y-speed)
            {
                y = target.getY();
            }
            else
            {
                y = y - speed;
            }
        }
        else if(target.getY() > y)
        {
            if(target.getY() < y+speed)
            {
                y = target.getY();
            }
            else
            {
                y = y + speed;
            }
        }
        if((target.getX() > x-width && target.getX() < x + width*2) && (target.getY() > y-height && target.getY() < y + height*2) && strength > target.strength)
        {
            killCount++;
            return true;
        }
        else
        {
            return false;
        }
    }
    
    public void move(int boundX, int boundY)
    {
        if(killCount % 20 == 0 && killCount != 0)
        {
            killCount = 0;
            strength++;
            speed++;
        }
        if(dir[0] == 1)
        {
            int randMove = (int)(Math.random()*dirChange);
            if(randMove == 0)
            {
                dir[0] = 0;
                dir[1] = 1;
            }
        }
        else if(dir[1] == 1)
        {
            int randMove = (int)(Math.random()*dirChange);
            if(randMove == 0)
            {
                dir[1] = 0;
                dir[0] = 1;
            }
        }
        else
        {
            int randMove = (int)(Math.random()*dirChange);
            if(randMove == 0)
            {
                dir[1] = 0;
                dir[0] = 1;
            }
            else if(randMove == 1)
            {
                dir[0] = 0;
                dir[1] = 1;
            }
        }
        if(dir[2] == 1)
        {
            int randMove = (int)(Math.random()*dirChange);
            if(randMove == 0)
            {
                dir[2] = 0;
                dir[3] = 1;
            }
        }
        else if(dir[3] == 1)
        {
            int randMove = (int)(Math.random()*dirChange);
            if(randMove == 0)
            {
                dir[3] = 0;
                dir[2] = 1;
            }
        }
        else
        {
            int randMove = (int)(Math.random()*dirChange);
            if(randMove == 0)
            {
                dir[3] = 0;
                dir[2] = 1;
            }
            else if(randMove == 1)
            {
                dir[2] = 0;
                dir[3] = 1;
            }
        }
        if(x <= 0 && dir[0] == 1)
        {
            dir[0] = 0;
            dir[1] = 1;
        }
        if(x >= boundX && dir[1] == 1)
        {
            dir[1] = 0;
            dir[0] = 1;
        }
        if(y <= 0 && dir[2] == 1)
        {
            dir[2] = 0;
            dir[3] = 1;
        }
        if(y >= boundY && dir[3] == 1)
        {
            dir[3] = 0;
            dir[2] = 1;
        }
        x = x + speed*(dir[1]-dir[0]);
        y = y + speed*(dir[3]-dir[2]);
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

    public int getRadius()
    {
        return radius;
    }
    
    public int getStrength()
    {
        return strength;
    }
    
    public String getName()
    {
        return name;
    }
    
    public Rectangle getBounds(){
        return new Rectangle(x,y,width,height);
    }
    
}
