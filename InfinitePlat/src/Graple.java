import java.awt.Color;
import java.awt.Graphics;
import java.awt.*;
public class Graple{
    
    int x;
    int y;
    Color color;
    int height;
    int width;
    
    public Graple(int x, int y, int width, int height, Color color){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
    }
    
    public void render(Graphics g){
       Graphics2D g2d = (Graphics2D)g;
       g2d.setColor(color);
       g2d.setStroke(new BasicStroke(10));
       g2d.drawLine(x,y,width,height);
    }
    
    public int grapleMovement()
    {
        if(x < width)
        {
            if(y > height)
            {
                if(x+10<width)
                {
                    x += 10;
                }
                else
                {
                    x = width;
                }
                if(y-5 > height)
                {
                    y -= 5;
                }
                else
                {
                    y = height;
                }
                return 1;
            }
            else
            {
                if(x+10<width)
                {
                    x += 10;
                }
                else
                {
                    x = width;
                }
                return 2;
            }
            
        }
        else if(x > width)
        {
            if(y > height)
            {
                if(x+10<width)
                {
                    x -= 10;
                }
                else
                {
                    x = width;
                }
                if(y-5 > height)
                {
                    y -= 5;
                }
                else
                {
                    y = height;
                }
                return 3;
            }
            else
            {
                if(x+10<width)
                {
                    x -= 10;
                }
                else
                {
                    x = width;
                }
                return 4;
            }
        }
        else if(y > height)
        {
            if(y-5 > height)
            {
                y -= 5;
            }
            else
            {
                y = height;
            }
            return 5;
        }
        else
        {
            return 0;
        }
    }
    
    public void setX(int x){
        this.x = x;
    }
    public void setY(int y){
        this.y = y; 
    }
    public void setWidth(int width){
        this.width = width;
    }
    public void setHeight(int height){
        this.height = height; 
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
    public Rectangle getBounds(){
        return new Rectangle(x,y,width,height);
    }
}