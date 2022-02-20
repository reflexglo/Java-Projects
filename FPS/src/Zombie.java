
import java.util.ArrayList;

public class Zombie {
    int x;
    int y;
    int health;
    int maxHealth;
    int timer;
    int reset;
    ArrayList<String> path;
    public Zombie(int x, int y, ArrayList<String> path)
    {
        this.x = x;
        this.y = y;
        maxHealth = 100;
        health = maxHealth;
        this.path = path;
        reset = 100;
        timer = reset;
    }
    
    public void move(Player player)
    {
        if(path != null && path.size() > 0 && timer == 0)
        {
            x = Integer.parseInt(path.get(path.size()-1).split(",")[0]);
            y = Integer.parseInt(path.get(path.size()-1).split(",")[1]);
            path.remove(path.get(path.size()-1));
            timer = reset;
        }
        if(timer > 0)
        {
            timer--;
        }
        boolean playerMoved = true;
        for(int i = 0;i<path.size();i++)
        {
            int zx = Integer.parseInt(path.get(i).split(",")[0]);
            int zy = Integer.parseInt(path.get(i).split(",")[1]);
            if(zx == player.getX() && zy == player.getY())
            {
                playerMoved = false;
                break;
            }
        }
        if(playerMoved == true)
        {
            path.add(0,player.getX()+","+player.getY());
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
    
    public void setX(int x)
    {
        this.x = x;
    }
    
    public void setY(int y)
    {
        this.y = y;
    }
}
