import java.awt.Graphics;
import java.awt.*;
import java.util.ArrayList;
public class View {
    Panel[] pan;
    int depth;
    int gx;
    int gy;
    public View(int depth, int gx, int gy)
    {
        pan = new Panel[5];
        this.depth = depth;
        this.gx = gx;
        this.gy = gy;
    }
    public void genView(Maze maze, Player player, ArrayList<Zombie> zombs)
    {
        int dir = player.getDir();
        int y = player.getX();
        int x = player.getY();
        int losDepth = Math.min(getLOSDepth(x,y,dir,maze),depth);
        initPanels(losDepth);
        fillPanels(losDepth,x,y,dir,maze,zombs);
    }
    public void fillPanels(int losDepth, int x, int y, int dir, Maze maze, ArrayList<Zombie> zombs)
    {
        if(dir == 0)
        {
            y--;
        }
        if(dir == 1)
        {
            x++;
        }
        if(dir == 2)
        {
            y++;
        }
        if(dir == 3)
        {
            x--;
        }
        int length = maze.getLength();
        int width = maze.getWidth();
        for(int i = 0;i<losDepth && (x >= 0 && x < length) && (y >= 0 && y < width);i++)
        {
            if(dir == 0)
            {
                if(maze.getType(y, x) != 0)
                {
                    pan[0].addBlock(maze.getColor(y, x), i);
                }
                else
                {
                    if(maze.getType(y, x-1) != 0)
                    {
                        pan[1].addBlock(maze.getColor(y, x-1), i);
                    }
                    else
                    {
                        pan[1].addBlock(backWall(maze,y-1,x-1), i);
                    }
                    if(maze.getType(y, x+1) != 0)
                    {
                        pan[3].addBlock(maze.getColor(y, x+1), i);
                    }
                    else
                    {
                        pan[3].addBlock(backWall(maze,y-1,x+1), i);
                    }
                    if(maze.hasZombie(y, x, zombs) != -1)
                    {
                        pan[0].addBlock(Color.GRAY, i);
                    }
                    pan[2].addBlock(maze.getColor(y, x), i);
                    pan[4].addBlock(maze.getColor(y, x), i);
                }
                y--;
            }
            if(dir == 1)
            {
                if(maze.getType(y, x) != 0)
                {
                    pan[0].addBlock(maze.getColor(y, x), i);
                }
                else
                {
                    if(maze.getType(y-1, x) != 0)
                    {
                        pan[1].addBlock(maze.getColor(y-1, x), i);
                    }
                    else
                    {
                        pan[1].addBlock(backWall(maze,y-1,x+1), i);
                    }
                    if(maze.getType(y+1, x) != 0)
                    {
                        pan[3].addBlock(maze.getColor(y+1, x), i);
                    }
                    else
                    {
                        pan[3].addBlock(backWall(maze,y+1,x+1), i);
                    }
                    if(maze.hasZombie(y, x, zombs) != -1)
                    {
                        pan[0].addBlock(Color.GRAY, i);
                    }
                    pan[2].addBlock(maze.getColor(y, x), i);
                    pan[4].addBlock(maze.getColor(y, x), i);
                }
                x++;
            }
            if(dir == 2)
            {
                if(maze.getType(y, x) != 0)
                {
                    pan[0].addBlock(maze.getColor(y, x), i);
                }
                else
                {
                    if(maze.getType(y, x+1) != 0)
                    {
                        pan[1].addBlock(maze.getColor(y, x+1), i);
                    }
                    else
                    {
                        pan[1].addBlock(backWall(maze,y+1,x+1), i);
                    }
                    if(maze.getType(y, x-1) != 0)
                    {
                        pan[3].addBlock(maze.getColor(y, x-1), i);
                    }
                    else
                    {
                        pan[3].addBlock(backWall(maze,y+1,x-1), i);
                    }
                    if(maze.hasZombie(y, x, zombs) != -1)
                    {
                        pan[0].addBlock(Color.GRAY, i);
                    }
                    pan[2].addBlock(maze.getColor(y, x), i);
                    pan[4].addBlock(maze.getColor(y, x), i);
                }
                y++;
            }
            if(dir == 3)
            {
                if(maze.getType(y, x) != 0)
                {
                    pan[0].addBlock(maze.getColor(y, x), i);
                }
                else
                {
                    if(maze.getType(y+1, x) != 0)
                    {
                        pan[1].addBlock(maze.getColor(y+1, x), i);
                    }
                    else
                    {
                        pan[1].addBlock(backWall(maze,y+1,x-1), i);
                    }
                    if(maze.getType(y-1, x) != 0)
                    {
                        pan[3].addBlock(maze.getColor(y-1, x), i);
                    }
                    else
                    {
                        pan[3].addBlock(backWall(maze,y-1,x-1), i);
                    }
                    if(maze.hasZombie(y, x, zombs) != -1)
                    {
                        pan[0].addBlock(Color.GRAY, i);
                    }
                    pan[2].addBlock(maze.getColor(y, x), i);
                    pan[4].addBlock(maze.getColor(y, x), i);
                }
                x--;
            }
        }
    }
    public Color backWall(Maze maze, int x, int y)
    {
        if(maze.withinBounds(x, y))
        {
            if(maze.getType(x, y) != 0)
            {
                Color wallColor = maze.getColor(x,y);
                Color backColor = new Color(wallColor.getRed()/2,wallColor.getGreen()/2,wallColor.getBlue()/2);
                return backColor;
            }
            else
            {
                return Color.BLACK;
            }
        }
        else
        {
            return Color.BLACK;
        }
    }
    public void initPanels(int depth)
    {
        for(int i = 0;i<pan.length;i++)
        {
            pan[i] = new Panel(depth,i,gx,gy);
        }
    }
    public int getLOSDepth(int newX, int newY, int dir, Maze maze)
    {
        int losBlock = depth;
        for(int i = 1;i<=depth;i++)
        {
            if(maze.withinBounds(newY, newX))
            {
                if(maze.getType(newY, newX) != 0)
                {
                    losBlock = i;
                    break;
                }
            }
            if(dir == 0)
            {
                newY -= 1;
            }
            if(dir == 1)
            {
                newX += 1;
            }
            if(dir == 2)
            {
                newY += 1;
            }
            if(dir == 3)
            {
                newX -= 1;
            }
        }
        return losBlock;
    }
    public void render(Graphics g)
    {
        pan[4].render(g);
        pan[3].render(g);
        pan[2].render(g);
        pan[1].render(g);
        pan[0].render(g);
        renderGun(g);
    }
    public void renderGun(Graphics g)
    {
        int[] xp1 = {gx+550,gx+550,gx+1000,gx+1000};
        int[] yp1 = {gy+700,gy+800,gy+1000,gy+800};
        g.setColor(new Color(245,245,220));
        g.drawPolygon(xp1,yp1,4);
        g.fillPolygon(xp1,yp1,4);
        int[] xp2 = {gx+450,gx+450,gx,gx};
        int[] yp2 = {gy+700,gy+800,gy+1000,gy+800};
        g.setColor(new Color(245,245,220));
        g.drawPolygon(xp2,yp2,4);
        g.fillPolygon(xp2,yp2,4);
        int[] xp3 = {gx+450,gx+550,gx+550,gx+450};
        int[] yp3 = {gy+600,gy+600,gy+950,gy+950};
        g.setColor(Color.DARK_GRAY);
        g.drawPolygon(xp3,yp3,4);
        g.fillPolygon(xp3,yp3,4);
        int[] xp4 = {gx+450,gx+450,gx+470,gx+470};
        int[] yp4 = {gy+600,gy+500,gy+500,gy+600};
        g.setColor(Color.LIGHT_GRAY);
        g.drawPolygon(xp4,yp4,4);
        g.fillPolygon(xp4,yp4,4);
        int[] xp5 = {gx+550,gx+550,gx+530,gx+530};
        int[] yp5 = {gy+600,gy+500,gy+500,gy+600};
        g.setColor(Color.LIGHT_GRAY);
        g.drawPolygon(xp5,yp5,4);
        g.fillPolygon(xp5,yp5,4);
        int[] xp6 = {gx+450,gx+450,gx+550,gx+550};
        int[] yp6 = {gy+600,gy+580,gy+580,gy+600};
        g.setColor(Color.LIGHT_GRAY);
        g.drawPolygon(xp6,yp6,4);
        g.fillPolygon(xp6,yp6,4);
        int[] xp7 = {gx+497,gx+497,gx+503,gx+503};
        int[] yp7 = {gy+527,gy+521,gy+521,gy+527};
        g.setColor(Color.WHITE);
        g.drawPolygon(xp7,yp7,4);
        g.fillPolygon(xp7,yp7,4);
    }
}
