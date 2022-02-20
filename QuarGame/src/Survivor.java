import java.awt.Color;
import java.awt.Graphics;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;

public class Survivor {
    
    int x;
    int y;
    Color color;
    int height;
    int width;
    boolean foward;
    boolean backward;
    boolean leftward;
    boolean rightward;
    public Survivor(int x, int y, int width, int height, Color color){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
        this.foward = false;
        this.backward = false;
        this.leftward = false;
        this.rightward = false;
    }
        public void setFoward(boolean foward){
            this.foward = foward;
        }
        public void setBackward(boolean backward){
            this.backward = backward;
        }
        public void setLeftward(boolean leftward){
            this.leftward = leftward;
        }
        public void setRightward(boolean rightward){
            this.rightward = rightward;
        }
        public boolean getFoward(){
            return foward;
        }
        public boolean getBackward(){
            return backward;
        }
        public boolean getLeftward(){
            return leftward;
        }
        public boolean getRightward(){
            return rightward;
        }
        public void movement(){
        if(foward){
            y-=5;
        }
        if(backward){
            y+=5;
        }
        if(leftward){
            x-=5;
        }
        if(rightward){
            x+=5;
        }
    }
    public void render(Graphics g){
        g.setColor(color);
        g.drawRect(x,y,width,height);
        g.fillRect(x,y,width,height);
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
    public Rectangle getBounds(){
        return new Rectangle(x,y,width,height);
    }
    
}
