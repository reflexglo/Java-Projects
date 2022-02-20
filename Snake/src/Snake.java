import java.awt.Color;
import java.awt.Graphics;
import java.awt.*;
import java.util.ArrayList;
public class Snake{
    
    int x;
    int y;
    Color color;
    int height;
    int width;
    String dir;
    
    public Snake(int x, int y, int width, int height, Color color){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
        this.dir = "W";
    }
    public void moveSnake(int speed,ArrayList<Snake> body){
        catchUp(body);
        if(dir.equals("W")){
            setY(getY()-speed);
        }
        if(dir.equals("S")){
            setY(getY()+speed);
        }
        if(dir.equals("A")){
            setX(getX()-speed);
        }
        if(dir.equals("D")){
            setX(getX()+speed);
        }
    }
    public void catchUp(ArrayList<Snake> body){
        for(int i = body.size()-1;i>0;i--){
            body.get(i).setX(body.get(i-1).getX());
            body.get(i).setY(body.get(i-1).getY());
        }
    }
    public void render(Graphics g){      
        g.drawRect(x, y, width, height);
         g.setColor(color);
         g.fillRect(x, y, width, height);
    }
    public void setX(int x){
        this.x = x;
    }
    
    public void setY(int y){
        this.y = y; 
    }
    public void setDir(String dir){
        this.dir = dir;
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public String getDir(){
        return dir;
    }
    public Rectangle getBounds(){
        return new Rectangle(x,y,width,height);
    }
    
}