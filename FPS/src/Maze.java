import java.awt.Color;
import java.awt.Graphics;
import java.io.*;
import java.util.ArrayList;
public class Maze {
    int length;
    int width;
    Cell[][] maze;
    public Maze(String mzfile)
    {
        File file = new File(mzfile);
        try{
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line = br.readLine();
            length = Integer.parseInt(line.split("-")[0]);
            width = Integer.parseInt(line.split("-")[1]);
            maze = new Cell[length][width];
            while((line = br.readLine()) != null)
            {
                System.out.println(line);
                String[] info = line.split(",");
                int x = Integer.parseInt(info[0]);
                int y = Integer.parseInt(info[1]);
                int type = Integer.parseInt(info[2]);
                String color = info[3];
                Color col = Color.WHITE;
                if(color.equals("r"))
                {
                    col = Color.RED;
                }
                if(color.equals("g"))
                {
                    col = Color.GREEN;
                }
                if(color.equals("b"))
                {
                    col = Color.BLUE;
                }
                if(color.equals("y"))
                {
                    col = Color.YELLOW;
                }
                if(color.equals("o"))
                {
                    col = Color.ORANGE;
                }
                if(color.equals("m"))
                {
                    col = Color.MAGENTA;
                }
                maze[x][y] = new Cell(type,col);
            }
        }
        catch(IOException e)
        {
            System.out.println(e);
        }
    }

    public void render(Graphics g, int x, int y, int wsize, Player player, ArrayList<Zombie> zombs)
    {
        int scale = wsize/Math.max(length, width);
        for(int i = 0;i<length;i++)
        {
            for(int j = 0;j<width;j++)
            {
                g.setColor(maze[i][j].getColor());
                g.drawRect(x+scale*j, y+scale*i, scale, scale);
                g.fillRect(x+scale*j, y+scale*i, scale, scale);
            }
        }
        g.setColor(Color.WHITE);
        g.drawRect(x+scale*player.getY(), y+scale*player.getX(), scale, scale);
        g.fillRect(x+scale*player.getY(), y+scale*player.getX(), scale, scale);
        
        if(player.getDir() == 0)
        {
            int[] xp = {x+scale*player.getY(),x+scale*player.getY()+scale,x+scale*player.getY()+scale/2};
            int[] yp = {y+scale*player.getX()-scale/2,y+scale*player.getX()-scale/2,y+scale*player.getX()-scale};
            g.drawPolygon(xp, yp,3);
        }
        if(player.getDir() == 1)
        {
            int[] xp = {x+scale*player.getY()+(3*scale)/2,x+scale*player.getY()+(3*scale)/2,x+scale*player.getY()+2*scale};
            int[] yp = {y+scale*player.getX(),y+scale*player.getX()+scale,y+scale*player.getX()+scale/2};
            g.drawPolygon(xp, yp,3);
        }
        if(player.getDir() == 2)
        {
            int[] xp = {x+scale*player.getY(),x+scale*player.getY()+scale,x+scale*player.getY()+scale/2};
            int[] yp = {y+scale*player.getX()+(3*scale)/2,y+scale*player.getX()+(3*scale)/2,y+scale*player.getX()+2*scale};
            g.drawPolygon(xp, yp,3);
        }
        if(player.getDir() == 3)
        {
            int[] xp = {x+scale*player.getY()-scale/2,x+scale*player.getY()-scale/2,x+scale*player.getY()-scale};
            int[] yp = {y+scale*player.getX(),y+scale*player.getX()+scale,y+scale*player.getX()+scale/2};
            g.drawPolygon(xp, yp,3);
        }
        for(int i = 0;i<zombs.size();i++)
        {
            g.setColor(Color.GRAY);
            g.drawRect(x+scale*zombs.get(i).getY(), y+scale*zombs.get(i).getX(), scale, scale);
            g.fillRect(x+scale*zombs.get(i).getY(), y+scale*zombs.get(i).getX(), scale, scale);
        }
    }
    
    public int hasZombie(int x, int y, ArrayList<Zombie> zombs)
    {
        for(int i = 0;i<zombs.size();i++)
        {
            if(zombs.get(i).getX() == x && zombs.get(i).getY() == y)
            {
                return i;
            }
        }
        return -1;
    }
    
    public void setType(int x, int y, int type)
    {
        maze[x][y].setType(type);
    }
    
    public int getLength()
    {
        return length;
    }
    
    public int getWidth()
    {
        return width;
    }
    
    public int getType(int x, int y)
    {
        return maze[x][y].getType();
    }
    
    public Color getColor(int x, int y)
    {
        return maze[x][y].getColor();
    }
    
    public boolean withinBounds(int x, int y)
    {
        return (x >= 0) && (x < length) && (y >= 0) && (y < width);
    }
}
