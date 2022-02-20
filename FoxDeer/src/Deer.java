import java.awt.Color;
import java.awt.Graphics;
import java.awt.*;
import java.util.ArrayList;

/**
 *
 * @author Mr. Maroney
 */
public class Deer{
    
    int x;
    int y;
    Color color;
    int height;
    int width;
    int speed;
    int strength;
    int hunger;
    int hungerMax;
    int[] dir;
    final int dirChange = 10;
    
    public Deer(int x, int y, int width, int height, Color color){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
        this.speed = (int)(Math.random()*5)+1;
        this.strength = (int)(Math.random()*3)+1;
        this.dir = new int[4];
        this.dir[0] = 0;
        this.dir[1] = 0;
        this.dir[2] = 0;
        this.dir[3] = 0;
        this.dir[(int)(Math.random()*4)] = 1;
        this.hungerMax = 500-this.speed*this.strength;
        this.hunger = hungerMax;
    }
    
    public Deer(int x, int y, int width, int height, Color color, int speed, int strength){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
        this.speed = speed;
        this.strength = strength;
        this.dir = new int[4];
        this.dir[0] = 0;
        this.dir[1] = 0;
        this.dir[2] = 0;
        this.dir[3] = 0;
        this.dir[(int)(Math.random()*4)] = 1;
        this.hungerMax = 300-this.speed*this.strength;
        this.hunger = hungerMax;
    }
    
    public void render(Graphics g){
        g.drawRect(x, y, width, height);
        g.setColor(color);
        g.fillRect(x, y, width, height);
    }
    
    public boolean checkHunger()
    {
        if(hunger <= 0)
        {
            return true;
        }
        else
        {
            hunger--;
            return false;
        }
    }
    
    public int findFood(ArrayList<Food> foods)
    {
        int minDist = 1000;
        int minFood = -1;
        for(int i = 0;i<foods.size();i++)
        {
            int dist = (int)(Math.sqrt((Math.pow(foods.get(i).getX()-x,2))+(Math.pow(foods.get(i).getY()-y,2))));
            if(dist < minDist)
            {
                minDist = dist;
                minFood = i;
            }
        }
        return minFood;
    }
    
    public boolean getFood(Food target)
    {
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
        if((target.getX() > x-width && target.getX() < x + width*2) && (target.getY() > y-height && target.getY() < y + height*2))
        {
            hunger = hungerMax;
            return true;
        }
        else
        {
            return false;
        }
    }
    
    public int mating(ArrayList<Deer> deers, int deer)
    {
        int mate = 0;
        int mateDist = width*10;
        for(int i = 0;i<deers.size();i++)
        {
            int dist = (int)(Math.sqrt((Math.pow(deers.get(i).getX()-x,2))+(Math.pow(deers.get(i).getY()-y,2))));
            if(dist < mateDist && i != deer)
            {
                mateDist = dist;
                mate = i;
            }
        }
        int birth = (int)(Math.random()*250);
        if(birth == 0)
        {
            return mate;
        }
        else
        {
            return -1;
        }
    }
    
    public int endangered(Wolf wolf)
    {
        int dist = (int)(Math.sqrt((Math.pow(wolf.getX()-x,2))+(Math.pow(wolf.getY()-y,2))));
        if(dist > wolf.getRadius())
        {
            return -1;
        }
        else
        {
            return dist;
        }
    }
    
    public void flee(Wolf wolf, int boundX, int boundY)
    {
        if(x < wolf.getX())
        {
            if(x > 0+speed)
            x = x - speed;
        }
        else
        {
            if(x < boundX-speed)
            x = x + speed;
        }
        if(y < wolf.getY())
        {
            if(y > 0+speed)
            y = y - speed;
        }
        else
        {
            if(y < boundY-speed)
            y = y + speed;
        }
    }
    
    public void move(int boundX, int boundY)
    {
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

    public int getSpeed()
    {
        return speed;
    }
    
    public int getStrength()
    {
        return strength;
    }
    
    public Rectangle getBounds(){
        return new Rectangle(x,y,width,height);
    }
    
}