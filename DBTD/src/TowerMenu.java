import java.awt.Color;
import java.awt.Graphics;
import java.awt.*;
import java.awt.image.BufferedImage;
public class TowerMenu{
    
    int x;
    int y;
    Color color;
    int height;
    int width;
    int price;
    String text;
    Image img;
    
    public TowerMenu(int x, int y, int width, int height, int price, String text, BufferedImage img, Color color){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
        this.text = text;
        this.img = img;
        this.price = price;
    }
    
    public void render(Graphics g){
        g.drawImage(img, x, y, width, height, null);
        g.setColor(Color.white);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 25));
         g.drawString(text, x+(int)(width/3), y+(int)(height*1.2));
         g.drawString(price+" Ƶ", x+(int)(width/3), y+(int)(height*1.2)+30);
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
    public int getPrice(){
        return price;
    }
    public Rectangle getBounds(){
        return new Rectangle(x,y,width,height);
    }
}
