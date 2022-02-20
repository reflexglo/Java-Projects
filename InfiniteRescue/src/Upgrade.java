import java.awt.Color;
import java.awt.Graphics;
import javax.swing.*;
import java.awt.*;
public class Upgrade{
    
    int x;
    int y;
    Color color;
    int height;
    int width;
    int type;
    String[] types;
    int[] prices;
    
    public Upgrade(int x, int y, int width, int height, int type, Color color){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.type = type;
        this.color = color;
        this.types = new String[]{"Primary Damage","Secondary Damage","Ability Damage","Primary Reload","Secondary Reload","Ability Cooldown"};
        this.prices = new int[]{50,100,150,25,75,225};
    }
    
    public Player upgrade(Player player)
    {
        if(type == 0)
        {
            player.setPriDmg(player.getPriDmg()+1);
        }
        if(type == 1)
        {
            player.setEfDmg(player.getEfDmg()+1);
        }
        if(type == 2)
        {
            player.setAbDmg(player.getAbDmg()+1);
        }
        if(type == 3)
        {
            player.setPriRelTimer(player.getPriRelTimer()/2);
        }
        if(type == 4)
        {
            player.setEfTimerMax(player.getEfTimerMax()/2);
        }
        if(type == 5)
        {
            player.setAbTimerMax(player.getAbTimerMax()/2);
        }
        prices[type] *= 2;
        return player;
    }
    
    public int getPrice()
    {
        return prices[type];
    }
    
    public void render(Graphics g){      
        String fullString = types[type]+": $"+prices[type];
        g.drawRect(x, y, width, height);
         g.setColor(color);
         g.fillRect(x, y, width, height);
         g.setColor(Color.WHITE);
         g.setFont(new Font("TimesRoman", Font.PLAIN, 25));
         g.drawString(fullString, x+7*(30-fullString.length()), y+30);
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
        return new Rectangle(x,y,width,height);
    }
    
}

