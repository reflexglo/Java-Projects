import java.awt.Color;
import java.awt.Graphics;
import javax.swing.*;
import java.awt.*;
public class BiomeTile{
    
    int x;
    int y;
    Color color;
    int height;
    int width;
    int biome;
    int[] bias;
    
    //Generic biome tile class
    public BiomeTile(int x, int y, int width, int height, int biome, Color color, int b1, int b2, int b3, int b4){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
        if(biome%11 > 5){
            biome = 10-(biome%11);
        }
        else{
            biome = biome%11;
        }
        if(biome == 2){
            this.color = new Color(0,255,120);
        }
        if(biome == 3){
            this.color = Color.green;
        }
        if(biome == 5){
            this.color = Color.blue;
        }
        if(biome == 1){
            this.color = new Color(120,255,0);
        }
        if(biome == 0){
            this.color = Color.gray;
        }
        if(biome == 4){
            this.color = new Color(245,245,220);
        }
        this.biome = biome;
        this.bias = new int[4];
        this.bias[0] = b1;
        this.bias[1] = b2;
        this.bias[2] = b3;
        this.bias[3] = b4;
    }
    
    //Render tile
    public void render(Graphics g){    
        g.setColor(color);
        g.drawRect(x, y, width, height);
         g.setColor(color);
         g.fillRect(x, y, width, height);
    }
    //Set and get methods for fields
    public void setX(int x){
        this.x = x;
    }
    
    public void setY(int y){
        this.y = y; 
    }
    public void setBiome(int biome){
        this.biome = biome;
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public int getBiome(){
        return biome;
    }
    public int getBias(int biasNum){
        return bias[biasNum];
    }
    public void setBias(int biasValue, int biasNum){
        this.bias[biasNum] = biasValue;
    }
    //Get bounds of tile
    public Rectangle getBounds(){
        return new Rectangle(x,y,width,height);
    }
    
}
