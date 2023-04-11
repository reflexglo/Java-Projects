import java.awt.Color;
import java.awt.Graphics;
import javax.swing.*;
import java.awt.*;
public class Tile{
    
    int x;
    int y;
    Color color;
    int height;
    int width;
    int num;
    
    //Generic Tile class
    public Tile(int x, int y, int width, int height, int num, Color color){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
        this.num = num;
    }
    
    //Log base 2 function
    public int pow2(int num)
    {
        int pow = 0;
        int cnt = 1;
        while(cnt<num)
        {
            cnt*=2;
            pow++;
        }
        return pow;
    }
    
    //Renders blank if no number, colored tile if number
    public void render(Graphics g){      
        if(num == 0)
        {
            g.drawRect(x, y, width, height);
            g.setColor(color);
            g.fillRect(x, y, width, height);
        }
        else
        {
            Color numCol = new Color(0, pow2(num)*25, 255-pow2(num)*25);
            g.drawRect(x, y, width, height);
            g.setColor(numCol);
            g.fillRect(x, y, width, height);
            g.setColor(Color.WHITE);
            g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
            g.drawString(num+"", x+width/2, y+height/2);
        }
    }
    //Set and get methods for fields
    public void setX(int x){
        this.x = x;
    }
    public void setY(int y){
        this.y = y; 
    }
    public void setColor(Color color)
    {
        this.color = color;
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public Color getColor()
    {
        return color;
    }
    public void setNum(int num)
    {
        this.num = num;
    }
    public int getNum()
    {
        return num;
    }
    //Get bounds of tile
    public Rectangle getBounds(){
        return new Rectangle(x,y,width,height);
    }
    
}

