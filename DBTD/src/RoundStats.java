import java.awt.Color;
import java.awt.Graphics;
import javax.swing.*;
import java.awt.*;
public class RoundStats{
    
    int x;
    int y;
    Color color;
    int height;
    int width;
    int money;
    int round;
    int lives;
    
    public RoundStats(int x, int y, int width, int height, int money, int round, int lives, Color color){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
        this.money = money;
        this.round = round;
        this.lives = lives;
    }
    
    public void render(Graphics g){
         g.setColor(Color.WHITE);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 40));
         g.drawString("Round: "+round, x+(int)(width/2.5), y+(int)(height/2.5));
         g.drawString("Zenī: "+money+" Ƶ", x+(int)(width/2.5), y+(int)(height/2.5)+50);
         g.drawString("Lives: "+lives, x+(int)(width/2.5), y+(int)(height/2.5)+100);
    }
    public void setX(int x){
        this.x = x;
    }
    public void setY(int y){
        this.y = y; 
    }
    public void setMoney(int money){
        this.money = money;
    }
    public void setRound(int round){
        this.round = round;
    }
    public void setLives(int lives){
        this.lives = lives;
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