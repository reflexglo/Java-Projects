import java.awt.Color;
import java.awt.Graphics;
import java.awt.*;
import java.awt.geom.Line2D;

/**
 *
 * @author Mr. Maroney
 */
public class Wall{
    
    int x;
    int y;
    Color color;
    int height;
    int width;
    
    public Wall(int x, int y, int width, int height, Color color){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
    }
    
    public void render(Graphics g){
       
        Graphics2D g2d = (Graphics2D)g;
        g2d.setStroke(new BasicStroke(10));
         g.setColor(Color.RED);
         g.drawLine(x,y,width,height);
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
        return new Rectangle(x,y,width-x+5,height-y+5);
    }
    
    public Line2D getLine()
    {
        return new Line2D.Float(x,y,width,height);
    }
    
}