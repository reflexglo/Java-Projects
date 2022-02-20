import java.awt.Color;
import java.awt.Graphics;
import javax.swing.*;
import java.awt.*;
public class UpgradeMenu{
    
    int x;
    int y;
    Color color;
    int height;
    int width;
    String text;
    int price;
    
    public UpgradeMenu(int x, int y, int width, int height, String text, int price,Color color){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
        this.text = text;
        this.price = price;
    }
    
    public void render(Graphics g){
        if(price>0){
            g.drawRect(x, y, width, height);
            g.setColor(color);
            g.fillRect(x, y, width, height);
            g.setColor(Color.WHITE);
            g.setFont(new Font("TimesRoman", Font.PLAIN, 30));
            g.drawString(text, x+(int)(width/5), y+(int)(height/2.5));
            g.drawString(price+" Ƶ", x+(int)(width/3), y+(int)(height/2.5)+30);
        }
    }
    public void setX(int x){
        this.x = x;
    }
    public void setY(int y){
        this.y = y; 
    }
    public void setText(String text){
        this.text = text;
    }
    public void setPrice(int price){
        this.price = price;
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public String getText(){
        return text;
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