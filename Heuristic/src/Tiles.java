import java.awt.Color;
import java.awt.Graphics;
import javax.swing.*;
import java.awt.*;
public class Tiles{
    
    int x;
    int y;
    Color color;
    int width;
    int height;
    String dist;
    
    public Tiles(int x, int y, int width, int height, Color color){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
        this.dist = "";
    }
    
    public void render(Graphics g){
        g.drawRect(x, y, width, height);
         g.setColor(color);
         g.fillRect(x, y, width, height);
         g.setColor(Color.BLACK);
         g.drawString(dist,x+width/2,y+height/2);
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
    public void setDist(int dist)
    {
        this.dist = dist+"";
    }
    public void setColor(Color color)
    {
        this.color = color;
    }
    public boolean getObst()
    {
        if(color.equals(Color.BLACK))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    public Rectangle getBounds(){
        return new Rectangle(x,y,width,height);
    }
    
}