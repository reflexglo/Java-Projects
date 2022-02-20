import java.awt.Color;
import java.awt.Graphics;
import javax.swing.*;
import java.awt.*;
public class Projectile{
    
    int x;
    int y;
    Color color;
    int height;
    int width;
    Image img;
    int tarX;
    int tarY;
    int speed;
    int pierce;
    
    public Projectile(int x, int y, int width, int height, int tarX, int tarY, int speed, int pierce, Image img, Color color){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.img = img;
        this.color = color;
        this.tarX = tarX;
        this.tarY = tarY;
        this.speed = speed;
        this.pierce = pierce;
    }
    public void move(){
        if(tarX>x+width/2){
            x+=speed;
        }
        if(tarX<x+width/2){
            x-=speed;
        }
        if(tarY>y+height/2){
            y+=speed;
        }
        if(tarY<y+height/2){
            y-=speed;
        }
    }
    public void render(Graphics g){
       g.drawImage(img, x, y, width, height, null);
    }
    public void setX(int x){
        this.x = x;
    }
    public void setY(int y){
        this.y = y; 
    }
    public void setTarX(int tarX){
        this.tarX = tarX;
    }
    public void setTarY(int tarY){
        this.tarY = tarY;
    }
    public void setPierce(int pierce){
        this.pierce = pierce;
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public int getTarX(){
        return tarX;
    }
    public int getTarY(){
        return tarY;
    }
    public int getPierce(){
        return pierce;
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