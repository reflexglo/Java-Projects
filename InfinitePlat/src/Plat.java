import java.awt.Color;
import java.awt.Graphics;
import java.awt.*;
import java.util.ArrayList;
public class Plat{
    
    int x;
    int y;
    Color color;
    int height;
    int width;
    ArrayList<Rectangle> steps;
    
    public Plat(int x, int y, int width, int height, Color color){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
        this.steps = new ArrayList<Rectangle>();
        steps.add(new Rectangle(x,y,width,height));
    }
    
    public void render(Graphics g){
         for( Rectangle r:steps)
         {
            g.drawRect(r.x, r.y, r.width, r.height);
            g.setColor(color);
            g.fillRect(r.x, r.y, r.width, r.height);
         }
    }
    public void addStep(Rectangle r)
    {
        steps.add(r);
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
    public int getStepY(int index)
    {
        return steps.get(index).y;
    }
    public int getBounds(Rectangle r){
        int index = 0;
        for(Rectangle s:steps)
        {
            if(r.intersects(s))
            {
                return index;
            }
            index++;
        }
        return -1;
    }
    public Rectangle getBound(int index)
    {
        return steps.get(index);
    }
    public Rectangle getStep(int index)
    {
        return steps.get(index);
    }
}