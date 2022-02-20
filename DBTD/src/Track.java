import java.awt.Color;
import java.awt.Graphics;
import javax.swing.*;
import java.awt.*;
public class Track{
    
    int x;
    int y;
    Color color;
    int height;
    int width;
    int direction;
    
    public Track(int x, int y, int width, int height, int direction, Color color){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
        this.direction = direction;
    }
    
    public void render(Graphics g){
       
        g.drawRect(x, y+20, width, height);
         g.setColor(color);
         g.fillRect(x, y+20, width, height);
    }
    public void setX(int x){
        this.x = x;
    }
    public void setY(int y){
        this.y = y; 
    }
    public void setDirection(int direction){
        this.direction = direction;
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public int getDirection(){
        return direction;
    }
    public int getWidth(){
        return width;
    }
    public int getHeight(){
        return height;
    }
    public Rectangle getBounds(){
        return new Rectangle(x,y,width,height);
    }
}