import java.awt.Graphics;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class ConeView {
    int depth;
    int gx;
    int gy;
    int vsize;
    public ConeView(int depth, int gx, int gy, int vsize)
    {
        this.depth = depth;
        this.gx = gx;
        this.gy = gy;
        this.vsize = vsize;
    }
    
    public Polygon genCone(int x, int y, int dir, int wsize, int mx, int my, Maze maze)
    {
        int newD = -1;
        int newL = -1;
        int newR = -1;
        int scale = wsize/(Math.max(maze.getLength(), maze.getWidth()));
        for(int i = 0;i<=depth;i++)
        {
            if(dir == 0)
            {
                if(maze.withinBounds(x, y-i) == false && newL == -1)
                {
                    newL = i-1;
                }
                if(maze.withinBounds(x, y+i) == false && newR == -1)
                {
                    newR = i-1;
                }
                if(maze.withinBounds(x-i, y) == false)
                {
                    newD = i-1;
                    break;
                }
            }
            if(dir == 1)
            {
                if(maze.withinBounds(x-i, y) == false && newL == -1)
                {
                    newL = i-1;
                }
                if(maze.withinBounds(x+i, y) == false && newR == -1)
                {
                    newR = i-1;
                }
                if(maze.withinBounds(x, y+i) == false)
                {
                    newD = i-1;
                    break;
                }
            }
            if(dir == 2)
            {
                if(maze.withinBounds(x, y+i) == false && newL == -1)
                {
                    newL = i-1;
                }
                if(maze.withinBounds(x, y-i) == false && newR == -1)
                {
                    newR = i-1;
                }
                if(maze.withinBounds(x+i, y) == false)
                {
                    newD = i-1;
                    break;
                }
            }
            if(dir == 3)
            {
                if(maze.withinBounds(x+i, y) == false && newL == -1)
                {
                    newL = i-1;
                }
                if(maze.withinBounds(x-i, y) == false && newR == -1)
                {
                    newR = i-1;
                }
                if(maze.withinBounds(x, y-i) == false)
                {
                    newD = i-1;
                    break;
                }
            }
        }
        if(dir == 0)
        {
            if(newD == -1)
            {
                newD = depth;
            }
            if(newL != -1)
            {
                    int[] xp = {mx+scale*y,mx+scale*(y+1),mx+scale*(y+1+newD),mx+scale*(y-newL),mx+scale*(y-newL)};
                    int[] yp = {my+scale*x,my+scale*x,my+scale*(x-newD),my+scale*(x-newD),my+scale*(x-newL)};
                    return new Polygon(xp,yp,5);
            }
            else if(newR != -1)
            {
                    int[] xp = {mx+scale*y,mx+scale*(y+1),mx+scale*(y+1+newR),mx+scale*(y+1+newR),mx+scale*(y-newD)};
                    int[] yp = {my+scale*x,my+scale*x,my+scale*(x-newR),my+scale*(x-newD),my+scale*(x-newD)};
                    return new Polygon(xp,yp,5);
            }
            else
            {
                int[] xp = {mx+scale*y,mx+scale*(y+1),mx+scale*(y+1+newD),mx+scale*(y-newD)};
                int[] yp = {my+scale*x,my+scale*x,my+scale*(x-newD),my+scale*(x-newD)};
                return new Polygon(xp,yp,4);
            }
        }
        if(dir == 1)
        {
            y++;
            if(newD == -1)
            {
                newD = depth;
            }
            if(newL != -1)
            {
                    int[] xp = {mx+scale*y,mx+scale*y,mx+scale*(y+newD),mx+scale*(y+newD),mx+scale*(y-newL)};
                    int[] yp = {my+scale*x,my+scale*(x+1),my+scale*(x+1+newD),my+scale*(x-newL),my+scale*(x-newL)};
                    return new Polygon(xp,yp,5);
            }
            else if(newR != -1)
            {
                    int[] xp = {mx+scale*y,mx+scale*y,mx+scale*(y+newR),mx+scale*(y+newD),mx+scale*(y+newD)};
                    int[] yp = {my+scale*x,my+scale*(x+1),my+scale*(x+1+newR),my+scale*(x+1+newR),my+scale*(x-newD)};
                    return new Polygon(xp,yp,5);
            }
            else
            {
                int[] xp = {mx+scale*y,mx+scale*y,mx+scale*(y+newD),mx+scale*(y+newD)};
                int[] yp = {my+scale*x,my+scale*(x+1),my+scale*(x+1+newD),my+scale*(x-newD)};
                return new Polygon(xp,yp,4);
            }
        }
        if(dir == 2)
        {
            x++;
            y++;
            if(newD == -1)
            {
                newD = depth;
            }
            if(newL != -1)
            {
                    int[] xp = {mx+scale*y,mx+scale*(y-1),mx+scale*(y-1-newD),mx+scale*(y+newL),mx+scale*(y+newL)};
                    int[] yp = {my+scale*x,my+scale*x,my+scale*(x+newD),my+scale*(x+newD),my+scale*(x+newL)};
                    return new Polygon(xp,yp,5);
            }
            else if(newR != -1)
            {
                    int[] xp = {mx+scale*y,mx+scale*(y-1),mx+scale*(y-1-newR),mx+scale*(y-1-newR),mx+scale*(y+newD)};
                    int[] yp = {my+scale*x,my+scale*x,my+scale*(x+newR),my+scale*(x+newD),my+scale*(x+newD)};
                    return new Polygon(xp,yp,5);
            }
            else
            {
                int[] xp = {mx+scale*y,mx+scale*(y-1),mx+scale*(y-1-newD),mx+scale*(y+newD)};
                int[] yp = {my+scale*x,my+scale*x,my+scale*(x+newD),my+scale*(x+newD)};
                return new Polygon(xp,yp,4);
            }
        }
        if(dir == 3)
        {
            x++;
            if(newD == -1)
            {
                newD = depth;
            }
            if(newL != -1)
            {
                    int[] xp = {mx+scale*y,mx+scale*y,mx+scale*(y-newD),mx+scale*(y-newD),mx+scale*(y-newL)};
                    int[] yp = {my+scale*x,my+scale*(x-1),my+scale*(x-1-newD),my+scale*(x+newL),my+scale*(x+newL)};
                    return new Polygon(xp,yp,5);
            }
            else if(newR != -1)
            {
                    int[] xp = {mx+scale*y,mx+scale*y,mx+scale*(y-newR),mx+scale*(y-newD),mx+scale*(y-newD)};
                    int[] yp = {my+scale*x,my+scale*(x-1),my+scale*(x-1-newR),my+scale*(x-1-newR),my+scale*(x+newD)};
                    return new Polygon(xp,yp,5);
            }
            else
            {
                int[] xp = {mx+scale*y,mx+scale*y,mx+scale*(y-newD),mx+scale*(y-newD)};
                int[] yp = {my+scale*x,my+scale*(x-1),my+scale*(x-1-newD),my+scale*(x+newD)};
                return new Polygon(xp,yp,4);
            }
        }
        return null;
    }
    
    public void drawCone(Graphics g, Player player, Maze maze,int wsize, int mx, int my)
    {
        g.drawPolygon(genCone(player.getX(),player.getY(),player.getDir(),wsize,mx,my,maze));
        g.setColor(Color.WHITE);
        g.fillPolygon(genCone(player.getX(),player.getY(),player.getDir(),wsize,mx,my,maze));
    }
    
    public ArrayList<ConePanel> genConeView(Player player, Maze maze,int wsize, int mx, int my)
    {
        Polygon cone = genCone(player.getX(),player.getY(),player.getDir(),wsize,mx,my,maze);
        ArrayList<String> objs = getRects(cone,maze,wsize,mx,my);
        ArrayList<ConePanel> floor = genFloor(player,maze);
        ArrayList<ConePanel> walls = revList(genWall(player,maze));
        floor.addAll(walls);
        return floor;
    }
    
    public ArrayList<ConePanel> revList(ArrayList<ConePanel> pan)
    {
        ArrayList<ConePanel> newPan = new ArrayList<ConePanel>();
        for(int i = pan.size()-1;i>=0;i--)
        {
            newPan.add(pan.get(i));
        }
        return newPan;
    }
    
    public void drawCView(Graphics g, Player player, Maze maze,int wsize, int mx, int my)
    {
        g.drawRect(gx, gy, vsize, vsize);
        g.setColor(Color.WHITE);
        g.fillRect(gx, gy, vsize, vsize);
        ArrayList<ConePanel> cview = genConeView(player,maze,wsize,mx,my);
        for(int i = 0;i<cview.size();i++)
        {
            cview.get(i).render(g);
        }
    }
    
    public ArrayList<ConePanel> genWall(Player player, Maze maze)
    {
        ArrayList<ConePanel> walls = new ArrayList<ConePanel>();
        int px = player.getX();
        int py = player.getY();
        int dir = player.getDir();
        int newD = getActDepth(px,py,dir,maze);
        for(int i = 0;i<newD;i++)
        {
            int numPan = 2*(i+1)+1;
            for(int j = 0;j<numPan;j++)
            {
                int hdp = j - numPan/2;
                int dp = i+1;
                if(dir == 0)
                {
                    if(maze.withinBounds(px-dp,py+hdp))
                    {
                        if(maze.getType(px-dp,py+hdp) != 0)
                        {
                            if(j > 0 && j < numPan-1)
                            {
                                walls.add(new ConePanel(createWallPanel(numPan,dp,j,0),maze.getColor(px-dp,py+hdp),2));
                            }
                            if(j != numPan/2)
                            {
                                walls.add(new ConePanel(createWallPanel(numPan,dp,j,1),maze.getColor(px-dp,py+hdp),1));
                            }
                        }
                    }
                }
                if(dir == 1)
                {
                    if(maze.withinBounds(px+hdp,py+dp)){
                        if(maze.getType(px+hdp,py+dp) != 0)
                        {
                            if(j > 0 && j < numPan-1)
                            {
                                walls.add(new ConePanel(createWallPanel(numPan,dp,j,0),maze.getColor(px+hdp,py+dp),2));
                            }
                            if(j != numPan/2)
                            {
                                walls.add(new ConePanel(createWallPanel(numPan,dp,j,1),maze.getColor(px+hdp,py+dp),1));
                            }
                        }
                    }
                }
                if(dir == 2)
                {
                    if(maze.withinBounds(px+dp,py-hdp))
                    {
                        if(maze.getType(px+dp,py-hdp) != 0)
                        {
                            if(j > 0 && j < numPan-1)
                            {
                                walls.add(new ConePanel(createWallPanel(numPan,dp,j,0),maze.getColor(px+dp,py-hdp),2));
                            }
                            if(j != numPan/2)
                            {
                                walls.add(new ConePanel(createWallPanel(numPan,dp,j,1),maze.getColor(px+dp,py-hdp),1));
                            }
                        }
                    }
                }
                if(dir == 3)
                {
                    if(maze.withinBounds(px-hdp,py-dp))
                    {
                        if(maze.getType(px-hdp,py-dp) != 0)
                        {
                            if(j > 0 && j < numPan-1)
                            {
                                walls.add(new ConePanel(createWallPanel(numPan,dp,j,0),maze.getColor(px-hdp,py-dp),2));
                            }
                            if(j != numPan/2)
                            {
                                walls.add(new ConePanel(createWallPanel(numPan,dp,j,1),maze.getColor(px-hdp,py-dp),1));
                            }
                        }
                    }
                }
            }
        }
        return walls;
    }
    
    public Polygon createWallPanel(int numPan, int dp, int hin, int ori)
    {
        int fgx = gx;
        int fgy = gy + vsize;
        if(ori == 0)
        {
            int fw = vsize/(numPan-2);
            fgx += fw*(hin-1);
            int fhy = 0;
            for(int i = 1;i<dp;i++)
            {
                fhy += vsize/(i*4);
            }
            int[] xp = {fgx,fgx,fgx+fw,fgx+fw};
            int[] yp = {fgy-fhy,fgy-vsize,fgy-vsize,fgy-fhy};
            return new Polygon(xp,yp,4);
        }
        else
        {
            if(hin == 0 || hin == numPan-1)
            {
                if(hin == 0)
                {
                    int bgx = fgx;
                    int fw = vsize/numPan;
                    fgx += fw;
                    int fh = vsize/(dp*4);
                    int fhy = 0;
                    for(int i = 1;i<dp;i++)
                    {
                        fhy += vsize/(i*4);
                    }
                    int[] xp = {bgx,fgx,fgx,bgx};
                    int[] yp = {fgy-fhy,fgy-fhy-fh,fgy-vsize,fgy-vsize};
                    return new Polygon(xp,yp,4);
                }
                else
                {
                    int bgx = fgx+vsize;
                    int fw = vsize/numPan;
                    fgx += vsize-fw;
                    int fh = vsize/(dp*4);
                    int fhy = 0;
                    for(int i = 1;i<dp;i++)
                    {
                        fhy += vsize/(i*4);
                    }
                    int[] xp = {bgx,fgx,fgx,bgx};
                    int[] yp = {fgy-fhy,fgy-fhy-fh,fgy-vsize,fgy-vsize};
                    return new Polygon(xp,yp,4);
                }
            }
            else
            {
                if(hin < numPan/2)
                {
                    int bw = vsize/(numPan-2);
                    int bgx = bw*(hin-1)+fgx;
                    int fw = vsize/numPan;
                    fgx += fw*(hin);
                    int fh = vsize/(dp*4);
                    int fhy = 0;
                    for(int i = 1;i<dp;i++)
                    {
                        fhy += vsize/(i*4);
                    }
                    int[] xp = {bgx+bw,fgx+fw,fgx+fw,bgx+bw};
                    int[] yp = {fgy-fhy,fgy-fhy-fh,fgy-vsize,fgy-vsize};
                    return new Polygon(xp,yp,4);
                }
                else
                {
                    int bw = vsize/(numPan-2);
                    int bgx = bw*(hin-1)+fgx;
                    int fw = vsize/numPan;
                    fgx += fw*(hin);
                    int fh = vsize/(dp*4);
                    int fhy = 0;
                    for(int i = 1;i<dp;i++)
                    {
                        fhy += vsize/(i*4);
                    }
                    int[] xp = {bgx,fgx,fgx,bgx};
                    int[] yp = {fgy-fhy,fgy-fhy-fh,fgy-vsize,fgy-vsize};
                    return new Polygon(xp,yp,4);
                }
            }
        }
    }
    
    public ArrayList<ConePanel> genFloor(Player player, Maze maze)
    {
        ArrayList<ConePanel> floor = new ArrayList<ConePanel>();
        int px = player.getX();
        int py = player.getY();
        int dir = player.getDir();
        int newD = getActDepth(px,py,dir,maze);
        for(int i = 0;i<newD;i++)
        {
            int numPan = 2*(i+1)+1;
            for(int j = 0;j<numPan;j++)
            {
                int hdp = j - numPan/2;
                int dp = i+1;
                if(dir == 0 && maze.withinBounds(px-dp,py+hdp))
                {
                    floor.add(new ConePanel(createFloorPanel(numPan,dp,j),maze.getColor(px-dp,py+hdp),0));
                }
                if(dir == 1 && maze.withinBounds(px+hdp,py+dp))
                {
                    floor.add(new ConePanel(createFloorPanel(numPan,dp,j),maze.getColor(px+hdp,py+dp),0));
                }
                if(dir == 2 && maze.withinBounds(px+dp,py-hdp))
                {
                    floor.add(new ConePanel(createFloorPanel(numPan,dp,j),maze.getColor(px+dp,py-hdp),0));
                }
                if(dir == 3 && maze.withinBounds(px-hdp,py-dp))
                {
                    floor.add(new ConePanel(createFloorPanel(numPan,dp,j),maze.getColor(px-hdp,py-dp),0));
                }
            }
        }
        return floor;
    }
    
    public Polygon createFloorPanel(int numPan, int dp, int hin)
    {
        int fgx = gx;
        int fgy = gy + vsize;
        if(hin == 0)
        {
            int fw = vsize/numPan;
            int fh = vsize/(dp*4);
            int fhy = 0;
            for(int i = 1;i<dp;i++)
            {
                fhy += vsize/(i*4);
            }
            int[] xp = {fgx,fgx,fgx+fw};
            int[] yp = {fgy-fhy,fgy-fhy-fh,fgy-fhy-fh};
            return new Polygon(xp,yp,3);
        }
        else if(hin == numPan-1)
        {
            fgx += vsize;
            int fw = vsize/numPan;
            int fh = vsize/(dp*4);
            int fhy = 0;
            for(int i = 1;i<dp;i++)
            {
                fhy += vsize/(i*4);
            }
            int[] xp = {fgx,fgx,fgx-fw};
            int[] yp = {fgy-fhy,fgy-fhy-fh,fgy-fhy-fh};
            return new Polygon(xp,yp,3);
        }
        else if(hin == (numPan/2))
        {
            int bw = vsize/(numPan-2);
            int bgx = bw*((numPan-2)/2)+fgx;
            int fw = vsize/numPan;
            fgx += fw*(numPan/2);
            int fh = vsize/(dp*4);
            int fhy = 0;
            for(int i = 1;i<dp;i++)
            {
                fhy += vsize/(i*4);
            }
            int[] xp = {fgx,fgx+fw,bgx+bw,bgx};
            int[] yp = {fgy-fhy-fh,fgy-fhy-fh,fgy-fhy,fgy-fhy};
            return new Polygon(xp,yp,4);
        }
        else if(hin < (numPan/2) || hin > (numPan/2))
        {
            int bw = vsize/(numPan-2);
            int bgx = bw*(hin-1)+fgx;
            int fw = vsize/numPan;
            fgx += fw*hin;
            int fh = vsize/(dp*4);
            int fhy = 0;
            for(int i = 1;i<dp;i++)
            {
                fhy += vsize/(i*4);
            }
            int[] xp = {fgx,fgx+fw,bgx+bw,bgx};
            int[] yp = {fgy-fhy-fh,fgy-fhy-fh,fgy-fhy,fgy-fhy};
            return new Polygon(xp,yp,4);
        }
        else
        {
            return null;
        }
    }
    
    public ArrayList<String> getRects(Polygon cone, Maze maze, int wsize, int mx, int my)
    {
        ArrayList<String> rects = new ArrayList<String>();
        int scale = (wsize)/(Math.max(maze.getLength(), maze.getWidth()));
        for(int i = 0;i<maze.getLength();i++)
        {
            for(int j = 0;j<maze.getWidth();j++)
            {
                Rectangle r = new Rectangle(mx+scale*j,my+scale*i,scale,scale);
                if(cone.intersects(r))
                {
                    rects.add(i+","+j);
                }
            }
        }
        return rects;
    }
    
    public int getActDepth(int x, int y, int dir, Maze maze)
    {
        for(int i = 0;i<=depth;i++)
        {
            if(dir == 0)
            {
                if(maze.withinBounds(x-i, y) == false)
                {
                    return i-1;
                }
            }
            if(dir == 1)
            {
                if(maze.withinBounds(x, y+i) == false)
                {
                    return i-1;
                }
            }
            if(dir == 2)
            {
                if(maze.withinBounds(x+i, y) == false)
                {
                    return i-1;
                }
            }
            if(dir == 3)
            {
                if(maze.withinBounds(x, y-i) == false)
                {
                    return i-1;
                }
            }
        }
        return depth;
    }
    
}
