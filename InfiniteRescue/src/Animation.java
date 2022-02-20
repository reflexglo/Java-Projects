import java.awt.Color;
import java.awt.Graphics;
import java.awt.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;
public class Animation{
    
    int x;
    int y;
    int fx;
    int fy;
    Color color;
    int height;
    int width;
    int timer;
    int type;
    int flip;
    ArrayList<Rectangle> rects;
    ArrayList<Line2D> lines;
    
    public Animation(int x, int y, int width, int height, int timer, int type, Color color){
        this.x = x;
        this.y = y;
        this.fx = x;
        this.fy = y;
        this.width = width;
        this.height = height;
        this.timer = timer;
        this.type = type;
        this.color = color;
        this.rects = new ArrayList<Rectangle>();
        this.flip = 0;
        if(type == 2)
        {
            this.x = this.fx + 3*(width/10);
            this.y = this.fy + 3*(height/10);
            this.width = 2*(width/5);
            this.height = 2*(height/5);
        }
        if(type == 3)
        {
            this.lines = new ArrayList<Line2D>();
        }
    }
    
    public int getTimer()
    {
        return timer;
    }
    
    public Line2D rotateLine(double angle)
    {
        double rx1 = Math.cos(angle)*200+x+200;
        double ry1 = Math.sin(angle)*200+y+200;
        double rx2 = -Math.cos(angle)*200+x+200;
        double ry2 = -Math.sin(angle)*200+y+200;
        return new Line2D.Double(rx1,ry1,rx2,ry2);
    }
    
    public void genRects()
    {
        rects = new ArrayList<Rectangle>();
        lines = new ArrayList<Line2D>();
        if(type == 0)
        {
            int raddi = 10;
            for(int i = 0;i<5;i++)
            {
                rects.add(new Rectangle(x+i*raddi,y+i*raddi,width-i*2*raddi,height-i*2*raddi));
            }
        }
        if(type == 1)
        {
            int raddi = 10;
            for(int i = 0;i<5;i++)
            {
                rects.add(new Rectangle(x+i*raddi,y+i*raddi,width-i*2*raddi,height-i*2*raddi));
            }
        }
        if(type == 2)
        {
            int raddi = 5;
            for(int i = 0;i<20;i++)
            {
                rects.add(new Rectangle(x+i*raddi,y+i*raddi,width-i*2*raddi,height-i*2*raddi));
            }
        }
        if(type == 3)
        {
            int raddi = 10;
            double angle = (double)(timer);
            for(int i = 0;i<20;i++)
            {
                rects.add(new Rectangle(x+i*raddi,y+i*raddi,width-i*2*raddi,height-i*2*raddi));
            }
            for(int i = 0;i<4;i++)
            {
                lines.add(rotateLine(((i*Math.PI)/4)+angle/10));
            }
        }
    }
    
    public void animate()
    {
        genRects();
        if(type == 0)
        {
            timer--;
            x -= 5;
            y -= 5;
            width += 5*2;
            height += 5*2;
        }
        if(type == 1)
        {
            timer--;
            x -= 5;
            y -= 5;
            width += 5*2;
            height += 5*2;
        }
        if(type == 2)
        {
            timer--;
            if(x > fx)
            {
                x -= 5;
                width += 5*2;
            }
            if(y > fy)
            {
                y -= 5;
                height += 5*2;
            }
        }
        if(type == 3)
        {
            timer--;
        }
    }
    
    public void render(Graphics g){
        Graphics2D g2d = (Graphics2D)g;
        int cnt = 0;
        if(timer %5 == 0)
        {
            if(flip == 1)
            {
                flip = 0;
            }
            else
            {
                flip = 1;
            }
        }
        for(Rectangle r:rects)
        {
            int rx = r.x;
            int ry = r.y;
            int rwidth = r.width;
            int rheight = r.height;
            if(type == 0)
            {
                color = new Color(255,cnt*50,0);
            }
            if(type == 1)
            {
                color = new Color(cnt*50,0,255);
            }
            if(type == 2)
            {
                if(cnt % 2 == 0)
                {
                    color = new Color(255,cnt*10,0);
                }
                else
                {
                    color = new Color(cnt*10,0,255);
                }
            }
            if(type == 3)
            {
                if((cnt+flip) % 2 == 0)
                {

                    color = new Color(0,255,255);
                }
                else
                {
                    color = new Color(0,0,255);
                }
            }
            g.drawOval(rx, ry, rwidth, rheight);
            g.setColor(color);
            g.fillOval(rx, ry, rwidth, rheight);
            cnt++;
        }
        if(type == 3)
        {
            cnt = 0;
            for(Line2D line:lines)
            {
                g2d.setStroke(new BasicStroke(5));
                g2d.setColor(Color.MAGENTA);
                g2d.drawLine((int)line.getX1(),(int)line.getY1(),(int)line.getX2(),(int)line.getY2());
            }
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
    public Rectangle getBounds(){
        return new Rectangle(x,y,width,height);
    }
    
}

