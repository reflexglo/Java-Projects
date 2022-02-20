import java.awt.Color;
import java.awt.Graphics;
import javax.swing.*;
import java.awt.*;
public class Food{
    
    int x;
    int y;
    Color color;
    int height;
    int width;
    
    public Food(int x, int y, int width, int height, Color color){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
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
