
import java.awt.Graphics;
import java.util.ArrayList;

public class Player {
    int x;
    int y;
    int dir;
    int gx;
    int gy;
    int health;
    int maxHealth;
    View view;
    ConeView cview;
    boolean viewGen;
    int ammo;
    int maxAmmo;
    int gunTimer;
    int gunReset;
    int relTimer;
    int relReset;
    int range;
    int los;
    public Player(Maze maze, int gx, int gy)
    {
        this.gx = gx;
        this.gy = gy;
        maxHealth = 100;
        health = maxHealth;
        maxAmmo = 6;
        ammo = maxAmmo;
        gunReset = 50;
        gunTimer = gunReset;
        relReset = 200;
        relTimer = relReset;
        range = 10;
        los = 5;
        view = new View(los,gx,gy);
        cview = new ConeView(los,gx,gy,1000);
        viewGen = false;
        dir = (int)(Math.random()*4);
        while(true)
        {
            x = (int)(Math.random()*maze.getLength());
            y = (int)(Math.random()*maze.getWidth());
            if(maze.getType(x, y) == 0)
            {
                break;
            }
        } 
    }
    
    public void move(int mdir, Maze maze)
    {
        int newX = x;
        int newY = y;
        if(dir == 0)
        {
            newX -= mdir;
        }
        if(dir == 1)
        {
            newY += mdir;
        }
        if(dir == 2)
        {
            newX += mdir;
        }
        if(dir == 3)
        {
            newY -= mdir;
        }
        if(maze.getType(newX, newY) == 0 && maze.withinBounds(x, y) == true)
        {
            x = newX;
            y = newY;
        }
    }
    
    public void renderCone(Graphics g, Maze maze, int wsize, int mx, int my)
    {
        cview.drawCone(g, this, maze, wsize, mx, my);
    }
    
    public void genConeView(Maze maze, int wsize, int mx, int my)
    {
        cview.genConeView(this, maze, wsize, mx, my);
    }
    
    public void genView(Maze maze, ArrayList<Zombie> zombs)
    {
        view.genView(maze, this, zombs);
        viewGen = true;
    }
    
    public void renderCView(Graphics g, Maze maze, int wsize, int mx, int my)
    {
        cview.drawCView(g, this, maze, wsize, mx, my);
    }
    
    public void renderView(Graphics g)
    {
        if(viewGen == true)
        {
            view.render(g);
        }
    }
    
    public int getTarget(Maze maze, ArrayList<Zombie> zombs)
    {
        int newX = x;
        int newY = y;
        for(int i = 0;i<=range;i++)
        {
            if(maze.withinBounds(newX, newY) == false || maze.getType(newX, newY) != 0)
            {
                return -1;
            }
            int hasTarget = maze.hasZombie(newX, newY, zombs);
            if(hasTarget != -1)
            {
                return hasTarget;
            }
            if(dir == 0)
            {
                newX--;
            }
            if(dir == 1)
            {
                newY++;
            }
            if(dir == 2)
            {
                newX++;
            }
            if(dir == 3)
            {
                newY--;
            }
        }
        return -1;
    }
    
    public int shoot(Maze maze, ArrayList<Zombie> zombs)
    {
        if(gunTimer == 0 && relTimer == 0)
        {
            if(ammo > 0)
            {
                ammo--;
                gunTimer = gunReset;
                return getTarget(maze,zombs);
            }
            else
            {
                return -1;
            }
        }
        else
        {
            return -1;
        }
    }
    
    public void reload()
    {
        if(relTimer == 0)
        {
            if(ammo < maxAmmo)
            {
                relTimer = (relReset)*((maxAmmo-ammo)/6);
                ammo = maxAmmo;
            }
        }
    }
    
    public void cooldown()
    {
        if(gunTimer > 0)
        {
            gunTimer--;
        }
        if(relTimer > 0)
        {
            relTimer--;
        }
    }
    
    public String open(Maze maze)
    {
        int newX = x;
        int newY = y;
        if(dir == 0)
        {
            newX--;
        }
        if(dir == 1)
        {
            newY++;
        }
        if(dir == 2)
        {
            newX++;
        }
        if(dir == 3)
        {
            newY--;
        }
        if(maze.getType(newX, newY) == 2)
        {
            return newX+","+newY;
        }
        else
        {
            return "";
        }
    }
    
    public int getX()
    {
        return x;
    }
    
    public int getY()
    {
        return y;
    }
    
    public int getDir()
    {
        return dir;
    }
    
    public void setX(int x)
    {
        this.x = x;
    }
    
    public void setY(int y)
    {
        this.y = y;
    }
    
    public void setDir(int dir)
    {
        this.dir = dir;
    }
    
}
