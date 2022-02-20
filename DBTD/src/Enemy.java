import java.awt.Color;
import java.awt.Graphics;
import java.awt.*;
import java.awt.image.BufferedImage;
public class Enemy{
    
    int x;
    int y;
    Color color;
    int height;
    int width;
    int health;
    int speed;
    int direction;
    int price;
    Image img;
    
    public Enemy(int x, int y, int width, int height, int health, int speed, int direction, int price, BufferedImage img, Color color){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
        this.img = img;
        this.health = health;
        this.speed = speed;
        this.direction = direction;
        this.price = price;
    }
    
    public void render(Graphics g){
        g.drawImage(img, x, y, width, height, null);
    }
    public void move(){
        if(direction == 0){
            y -= speed;
        }
        if(direction == 1){
            x -= speed;
        }
        if(direction == 2){
            y += speed;
        }
        if(direction == 3){
            x += speed;
        }
    }
    public void setX(int x){
        this.x = x;
    }
    public void setY(int y){
        this.y = y; 
    }
    public void setHealth(int health){
        this.health = health;
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
    public int getHealth(){
        return health;
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
    public int getPrice(){
        return price;
    }
    public Rectangle getBounds(){
        return new Rectangle(x,y,width,height);
    }
    public Rectangle getHighBound(){
        return new Rectangle(x-10,y-10,1,1);
    }
    public Rectangle getLowBound(){
        return new Rectangle(x+width+9,y+height+9,1,1);
    }
}
