import java.awt.Color;
import java.awt.Graphics;
import java.awt.*;
import java.util.ArrayList;
public class Rope{
    
    int x;
    int y;
    Color color;
    int height;
    int width;
    double length;
    double angleVelocity;
    double angle;
    
    public Rope(int x, int y, int width, int height, Color color){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
        this.length = y - height;
        this.angleVelocity = 0;
        this.angle = 0;
    }
    
    public void move()
    {
        double angleAccel, dt = 0.1;
        angleAccel = -9.81 / length * Math.sin(angle)+(-angleVelocity/10);
        angleVelocity += angleAccel * dt;
        angle += angleVelocity * dt;
    }
    
    public void render(Graphics g){
       x = width + (int) (Math.sin(angle) * length);
        y = height + (int) (Math.cos(angle) * length);
        g.setColor(color);
        g.drawLine(x, y, width, height);
        g.drawRect(width-20,height-20,40,40);
        g.fillRect(width-20,height-20,40,40);
    }
    public void setAngVel(double vel)
    {
        this.angleVelocity = vel;
    }
    public double getExitVel()
    {
        return this.angleVelocity;
    }
    public double getAngle()
    {
        return this.angle;
    }
    public double getLength()
    {
        return this.length;
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
    public Rectangle getBounds(){
        return new Rectangle(width-20,height,40,y-height);
    }
}