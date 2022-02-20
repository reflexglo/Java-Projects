import java.awt.Color;
import java.awt.Graphics;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;

public class Sniper {
    
    int x;
    int y;
    Color color;
    int height;
    int width;
    int range;
    int speedX;
    int speedY;
    
    public Sniper(int x, int y, int width, int height, int range, Color color){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.range = range;
        this.color = color;
        this.speedX = 0;
        this.speedY = 0;
    }
    public void render(Graphics g){
        g.setColor(color);
        g.drawRect(x,y,width,height);
        g.fillRect(x,y,width,height);
        g.drawRect(x-range, y-range, width+2*range, height+2*range);
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
    public void setSpeedX(int speedX){
        this.speedX = speedX;
    }
    public void setSpeedY(int speedY){
        this.speedY = speedY;
    }
    public int getSpeedX(){
        return speedX;
    }
    public int getSpeedY(){
        return speedY;
    }
    public void randMove(){
        int randX;
        int randY;
        randX = (int)(Math.random()*3)-1;
        randY = (int)(Math.random()*3)-1;
        if(Math.abs(speedX+randX)<5){
            speedX+=randX;
        }
        else{
            speedX-=randX;
        }
        if(Math.abs(speedY+randY)<5){
            speedY+=randY;
        }
        else{
            speedY-=randY;
        }
    }
    public void movement(){
        x+=speedX;
        y+=speedY;
    }
    public Rectangle getTop(){
        return new Rectangle(x+10,y,5,5);
    }
    public Rectangle getBottom(){
        return new Rectangle(x+10,y+height-5,5,5);
    }
    public Rectangle getLeft(){
        return new Rectangle(x,y+10,5,5);
    }
    public Rectangle getRight(){
        return new Rectangle(x+width-5,y+10,5,5);
    }
    public Rectangle getRangeBounds(){
        return new Rectangle(x-range, y-range, width+2*range, height+2*range);
    }
    public Rectangle getBounds(){
        return new Rectangle(x,y,width,height);
    }
    
}
