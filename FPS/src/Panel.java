import java.awt.Color;
import java.awt.Graphics;
import javax.swing.*;
import java.awt.*;
public class Panel {
    int depth;
    int ori;
    int gx;
    int gy;
    Polygon[] blocks;
    Color[] colors;
    public Panel(int depth, int ori, int gx, int gy)
    {
        this.depth = depth;
        this.ori = ori;
        this.blocks = new Polygon[depth];
        this.colors = new Color[depth];
        this.gx = gx;
        this.gy = gy;
        for(int i = 0;i<blocks.length;i++)
        {
            blocks[i] = null;
        }
    }
    public void addBlock(Color color, int index)
    {
        int fc = index*40;
        int sc = (index+1)*40;
        int tc = 1000-fc;
        int rc = 1000-sc;
        int zs = 100;
            if(ori == 0)
            {
                if(color.equals(Color.GRAY))
                {
                    int[] xp = {gx+zs+fc,gx+zs+fc,gx+tc-zs,gx+tc-zs};
                    int[] yp = {gy+zs+fc,gy+tc,gy+tc,gy+zs+fc};
                    blocks[index] = new Polygon(xp,yp,4);
                    colors[index] = color;
                }
                else
                {
                    int[] xp = {gx+fc,gx+fc,gx+tc,gx+tc};
                    int[] yp = {gy+fc,gy+tc,gy+tc,gy+fc};
                    blocks[index] = new Polygon(xp,yp,4);
                    colors[index] = color;
                }
            }
            if(ori == 1)
            {
                int[] xp = {gx+fc,gx+fc,gx+sc,gx+sc};
                int[] yp = {gy+fc,gy+tc,gy+rc,gy+sc};
                blocks[index] = new Polygon(xp,yp,4);
                colors[index] = color;
            }
            if(ori == 2)
            {
                int[] xp = {gx+fc,gx+tc,gx+rc,gx+sc};
                int[] yp = {gy+fc,gy+fc,gy+sc,gy+sc};
                blocks[index] = new Polygon(xp,yp,4);
                colors[index] = color;
            }
            if(ori == 3)
            {
                int[] xp = {gx+tc,gx+tc,gx+rc,gx+rc};
                int[] yp = {gy+fc,gy+tc,gy+rc,gy+sc};
                blocks[index] = new Polygon(xp,yp,4);
                colors[index] = color;
            }
            if(ori == 4)
            {
                int[] xp = {gx+fc,gx+tc,gx+rc,gx+sc};
                int[] yp = {gy+tc,gy+tc,gy+rc,gy+rc};
                blocks[index] = new Polygon(xp,yp,4);
                colors[index] = color;
            }
    }
    public void render(Graphics g)
    {
        for(int i = blocks.length-1;i>=0;i--)
        {
            if(blocks[i] != null)
            {
                g.drawPolygon(blocks[i]);
                g.setColor(colors[i]);
                g.fillPolygon(blocks[i]);
            }
        }
    }
}
