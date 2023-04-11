import java.awt.Color;
import java.awt.Graphics;
import javax.swing.*;
import java.awt.*;

//Generic goal class
public class Goal{
    
    int x;
    int y;
    Color color;
    int height;
    int width;
    
    public Goal(int x, int y, int width, int height, Color color){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
    }
    //Render goal
    public void render(Graphics g){
       
        g.drawRect(x, y, width, height);
        g.setColor(color);
         g.fillRect(x, y, width, height);
    }
    //Get and set methods for fields
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

    //Get rectangle bounds of goal
    public Rectangle getBounds(){
        return new Rectangle(x,y,width,height);
    }
    
}