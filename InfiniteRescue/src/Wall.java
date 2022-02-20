import java.awt.Color;
import java.awt.Graphics;
import java.awt.*;
import java.util.ArrayList;

/**
 *
 * @author Mr. Maroney
 */
public class Wall {
    
    int x;
    int y;
    Color color;
    int height;
    int width;
    int type;
    boolean isDoor;
    Rectangle door;
    ArrayList<Rectangle> rects;
    
    public Wall(int x, int y, int width, int height, boolean isDoor, Color color){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
        this.isDoor = isDoor;
        if(height > width)
        {
            this.type = 0;
        }
        else
        {
            this.type = 1;
        }
        this.setIsDoor(isDoor);
    }
    
    public void openEntrance(Rectangle edoor)
    {
        this.isDoor = true;
        rects = new ArrayList<Rectangle>();
        if(type == 0)
        {
            int dh = edoor.height;
            int randS = edoor.y - y;
            rects.add(new Rectangle(x,y,width,randS));
            rects.add(new Rectangle(x,y+dh+randS,width,height-randS-dh));
            door = new Rectangle(x,y+randS,width,dh);
        }
        else
        {
            int dw = edoor.width;
            int randS = edoor.x - x;
            rects.add(new Rectangle(x,y,randS,height));
            rects.add(new Rectangle(x+dw+randS,y,width-randS-dw,height));
            door = new Rectangle(x+randS,y,dw,height);
        }
    }
    
    public void closeEntrance()
    {
        this.isDoor = false;
        rects = new ArrayList<Rectangle>();
        rects.add(new Rectangle(x,y,width,height));
        door = null;
    }
    
    public void render(Graphics g){
        for(Rectangle r:rects)
        {
            g.drawRect(r.x, r.y, r.width, r.height);
            g.setColor(color);
            g.fillRect(r.x, r.y, r.width, r.height);
        }
        if(door != null)
        {
            g.setColor(Color.GRAY);
            g.drawRect(door.x, door.y, door.width, door.height);
            g.fillRect(door.x, door.y, door.width, door.height);
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
    public boolean getIsDoor(){
        return isDoor;
    }
    public Rectangle getDoor()
    {
        if(isDoor)
        {
            return door;
        }
        else
        {
            return null;
        }
    }
    public void setIsDoor(boolean isDoor)
    {
        this.isDoor = isDoor;
        rects = new ArrayList<Rectangle>();
        if(isDoor)
        {
            if(type == 0)
            {
                int dh = height/3;
                int randS = (int)(Math.random()*(height/6))+height/6;
                rects.add(new Rectangle(x,y,width,randS));
                rects.add(new Rectangle(x,y+dh+randS,width,height-randS-dh));
                door = new Rectangle(x,y+randS,width,dh);
            }
            else
            {
                int dw = width/3;
                int randS = (int)(Math.random()*(width/6))+width/6;
                rects.add(new Rectangle(x,y,randS,height));
                rects.add(new Rectangle(x+dw+randS,y,width-randS-dw,height));
                door = new Rectangle(x+randS,y,dw,height);
            }
        }
        else
        {
                rects.add(new Rectangle(x,y,width,height));
                door = null;
        }
    }
    
    public boolean checkBounds(Rectangle r)
    {
        for(Rectangle w:rects)
        {
            if(r.intersects(w))
            {
                return true;
            }
        }
        return false;
    }
    
    public Rectangle getBounds(){
        return new Rectangle(x,y,width,height);
    }
    
}
