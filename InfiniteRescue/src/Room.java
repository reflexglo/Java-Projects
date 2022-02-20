import java.awt.Color;
import java.awt.Graphics;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 *
 * @author Mr. Maroney
 */
public class Room {
    
    int x;
    int y;
    Color color;
    int height;
    int width;
    int door;
    int roomNum;
    Wall[] walls;
    ArrayList<Enemy> enemies;
    
    public Room(int x, int y, int width, int height, int roomNum, Color color){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
        this.walls = new Wall[4];
        this.door = (int)(Math.random()*4);
        this.roomNum = roomNum;
        walls[0] = new Wall(x,y,width,50,false,Color.BLACK);
        walls[1] = new Wall(x,y,50,height,false,Color.BLACK);
        walls[2] = new Wall(x,y+height,width+50,50,false,Color.BLACK);
        walls[3] = new Wall(x+width,y,50,height+50,false,Color.BLACK);
        this.enemies = new ArrayList<Enemy>();
    }
    
    public void addAnimation(int i, Animation ani)
    {
        enemies.get(i).addAnimation(ani);
    }
    
    public void removeEnProj(int i, int j)
    {
        enemies.get(i).removeProj(j);
    }
    
    public void update(Player player)
    {
        for(int i = 0;i<enemies.size();i++)
        {
            enemies.get(i).update(player,walls);
        }
    }
    
    public void genEnemies()
    {
        int numEn = (int)(Math.random()*10)+5;
        for(int i = 0;i<numEn;i++)
        {
            int randX = (int)(Math.random()*(width-75))+x+50;
            int randY = (int)(Math.random()*(height-75))+y+50;
            int randDmg = (int)(Math.random()*4)+roomNum;
            enemies.add(new Enemy(randX,randY,25,25,100,randDmg,Color.RED));
        }
    }
    
    public int getEnemyHealth(int i)
    {
        return enemies.get(i).getHealth();
    }
    
    public void setEnemyHealth(int i, int health)
    {
        enemies.get(i).setHealth(health);
    }
    
    public void removeEnemy(int i)
    {
        enemies.remove(i);
    }
    
    public Enemy getEnemy(int i)
    {
        return enemies.get(i);
    }
    
    public void openDoor()
    {
        walls[door].setIsDoor(true);
    }
    public Wall getWall(int i)
    {
        return walls[i];
    }
    
    public void closeEntrance()
    {
        for(int i = 0;i<4;i++)
        {
            walls[i].closeEntrance();
        }
    }
    
    public void openEntrance(Rectangle edoor, int i)
    {
        walls[i].openEntrance(edoor);
    }
    
    public Room genNext()
    {
        Room nroom;
        int nheight = 0;
        int nwidth = 0;
        int nx = 0;
        int ny = 0;
        int ewall = 0;
        Rectangle rdoor = walls[door].getDoor();
        if(door == 0)
        {
            nheight = (int)(Math.random()*height)+height/2;
            nwidth = (int)(Math.random()*width)+width/2;
            ny = y-nheight;
            nx = (int)(Math.random()*(nwidth-rdoor.width))+rdoor.x + rdoor.width - nwidth;
            ewall = 2;
        }
        if(door == 1)
        {
            nheight = (int)(Math.random()*height)+height/2;
            nwidth = (int)(Math.random()*width)+width/2;
            ny = (int)(Math.random()*(nheight-rdoor.height))+rdoor.y + rdoor.height - nheight;
            nx = x-nwidth;
            ewall = 3;
        }
        if(door == 2)
        {
            nheight = (int)(Math.random()*height)+height/2;
            nwidth = (int)(Math.random()*width)+width/2;
            ny = y+height;
            nx = (int)(Math.random()*(nwidth-rdoor.width))+rdoor.x + rdoor.width - nwidth;
            ewall = 0;
        }
        if(door == 3)
        {
            nheight = (int)(Math.random()*height)+height/2;
            nwidth = (int)(Math.random()*width)+width/2;
            ny = (int)(Math.random()*(nheight-rdoor.height))+rdoor.y + rdoor.height - nheight;
            nx = x+width;
            ewall = 1;
        }
        nroom = new Room(nx,ny,nwidth,nheight,roomNum+1,Color.BLACK);
        nroom.openEntrance(rdoor, ewall);
        return nroom;
    }
    
    public void render(Graphics g){
       for(int i = 0;i<walls.length;i++)
       {
           walls[i].render(g);
       }
       g.setColor(Color.YELLOW);
       g.drawRect(x+50,y+50,width-50,height-50);
       g.fillRect(x+50,y+50,width-50,height-50);
       for(int i = 0;i<enemies.size();i++)
       {
           enemies.get(i).render(g);
       }
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
    public int getNumEn()
    {
        return enemies.size();
    }

    public boolean inArea(Player p)
    {
        Rectangle area = new Rectangle(x+50,y+50,width-50,height-50);
        boolean isInArea = area.intersects(p.getLBounds()) && area.intersects(p.getRBounds()) &&
                area.intersects(p.getUBounds()) && area.intersects(p.getDBounds());
        
        return isInArea;
    }
    
    public Rectangle getBounds(){
        return new Rectangle(x,y,width,height);
    }
    
}
