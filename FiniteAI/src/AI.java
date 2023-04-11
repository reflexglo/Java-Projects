import java.awt.Color;
import java.awt.Graphics;
import java.awt.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import javafx.util.Pair;

//Generic AI class
public class AI{
    
    int x;
    int y;
    Color color;
    int height;
    int width;
    
    public AI(int x, int y, int width, int height, Color color){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
    }
    //Gets all walls in view of AI to list
    public ArrayList<Integer> getViewWalls(ArrayList<Wall> walls)
    {
        float step = 0.01f;
        int numPoints = (int)((2*Math.PI)/(step))+1;
        ArrayList<Integer> seen = new ArrayList<Integer>();
        for(float angle = 0;angle<2*Math.PI;angle+=step)
        {
            int close = getCloseWall(walls,angle);
            if(close != -1 && !seen.contains(close))
            {
                seen.add(close);
            }
        }
        return seen;
    }
    
    //Gets the closest wall in view of the AI
    public int getCloseWall(ArrayList<Wall> walls, float angle)
    {
        ArrayList<Pair<Integer,Integer>> points = new ArrayList<Pair<Integer,Integer>>();
        int view = 250;
        float lx = (float)Math.cos(angle)*view+x;
        float ly = (float)Math.sin(angle)*view+y;
        Line2D line = new Line2D.Float(x,y,lx,ly);
        float min = -1;
        int ind = -1;
        for(int j = 0;j<walls.size();j++)
        {
            if(line.intersectsLine(walls.get(j).getLine()))
            {
                if(lx != x)
                {
                    float m1 = (ly-y)/(lx-x);
                    float b1 = (y-x*m1);
                    if(walls.get(j).getLine().getX1() != walls.get(j).getLine().getX2())
                    {
                        float m2 = (float) ((walls.get(j).getLine().getY1()-walls.get(j).getLine().getY2())/(walls.get(j).getLine().getX1()-walls.get(j).getLine().getX2()));
                        float b2 = (float) (walls.get(j).getLine().getY1()-m2*walls.get(j).getLine().getX1());
                        points.add(new Pair((int)((b2-b1)/(m1-m2)),(int)(m1*((b2-b1)/(m1-m2))+b1)));
                        float dist = (float)Math.sqrt(Math.pow(x-points.get(points.size()-1).getKey(),2)+Math.pow(x-points.get(points.size()-1).getKey(),2));
                        if(min == -1)
                        {
                            min = dist;
                            ind = j;
                        }
                        else if(dist < min)
                        {
                            min = dist;
                            ind = j;
                        }
                    }
                    else
                    {
                        points.add(new Pair((int)(walls.get(j).getLine().getX1()),(int)(m1*((walls.get(j).getLine().getX1()))+b1)));
                        float dist = (float)Math.sqrt(Math.pow(x-points.get(points.size()-1).getKey(),2)+Math.pow(x-points.get(points.size()-1).getKey(),2));
                        if(min == -1)
                        {
                            min = dist;
                            ind = j;
                        }
                        else if(dist < min)
                        {
                            min = dist;
                            ind = j;
                        }
                    }
                }
            }
        }
        return ind;
    }
    
    //Gets list of points within AI field of view
    public int[][] getPoints(ArrayList<Wall> walls)
    {
        float step = 0.01f;
         int numPoints = (int)((2*Math.PI)/(step))+1;
         int[] xps = new int[numPoints];
         int[] yps = new int[numPoints];
         int cnt = 0;
         for(float angle = 0;angle<2*Math.PI;angle+=step)
         {
            ArrayList<Pair<Integer,Integer>> points = new ArrayList<Pair<Integer,Integer>>();
            int view = 250;
            float lx = (float)Math.cos(angle)*view+x;
            float ly = (float)Math.sin(angle)*view+y;
            Line2D line = new Line2D.Float(x,y,lx,ly);
            for(int j = 0;j<walls.size();j++)
            {
                if(line.intersectsLine(walls.get(j).getLine()))
                {
                    if(lx != x)
                    {
                        float m1 = (ly-y)/(lx-x);
                        float b1 = (y-x*m1);
                        if(walls.get(j).getLine().getX1() != walls.get(j).getLine().getX2())
                        {
                            float m2 = (float) ((walls.get(j).getLine().getY1()-walls.get(j).getLine().getY2())/(walls.get(j).getLine().getX1()-walls.get(j).getLine().getX2()));
                            float b2 = (float) (walls.get(j).getLine().getY1()-m2*walls.get(j).getLine().getX1());
                            points.add(new Pair((int)((b2-b1)/(m1-m2)),(int)(m1*((b2-b1)/(m1-m2))+b1)));
                        }
                        else
                        {
                            points.add(new Pair((int)(walls.get(j).getLine().getX1()),(int)(m1*((walls.get(j).getLine().getX1()))+b1)));
                        }
                    }
                }
            }
            float min = -1;
            int nx = 0;
            int ny = 0;
            for(int i = 0;i<points.size();i++)
            {
                float dist = (float)Math.sqrt(Math.pow(x-points.get(i).getKey(),2)+Math.pow(x-points.get(i).getKey(),2));
                if(min == -1)
                {
                    min = dist;
                    nx = points.get(i).getKey();
                    ny = points.get(i).getValue();
                }
                else if(dist < min)
                {
                    min = dist;
                    nx = points.get(i).getKey();
                    ny = points.get(i).getValue();
                }
            }
            if(min == -1)
            {
                xps[cnt] = (int)lx;
                yps[cnt] = (int)ly;
                cnt++;
            }
            else
            {
                xps[cnt] = nx;
                yps[cnt] = ny;
                cnt++;
            }
        }
         int[][] points = new int[2][xps.length];
         points[0] = xps;
         points[1] = yps;
         return points;
    }
    //Renders AI and field of view lines
    public void render(Graphics g, ArrayList<Wall> walls){
         ArrayList<Integer> xp = new ArrayList<Integer>();
         ArrayList<Integer> yp = new ArrayList<Integer>();
         int[][] points = getPoints(walls);
         int[] xps = points[0];
         int[] yps = points[1];  
         Polygon p;
         g.setColor(Color.WHITE);
         g.fillPolygon(xps,yps,xps.length);
         g.drawRect(x-width/2, y-height/2, width, height);
        g.setColor(color);
         g.fillRect(x-width/2, y-height/2, width, height);
    }
    //Get and set methods for fields
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
    
    //Get methods for AI bounds
    public Rectangle getTBounds(){
        return new Rectangle(x-2,y-height/2,4,4);
    }
    public Rectangle getBBounds(){
        return new Rectangle(x-2,y+height/2-4,4,4);
    }
    public Rectangle getLBounds(){
        return new Rectangle(x-width/2,y-2,4,4);
    }
    public Rectangle getRBounds(){
        return new Rectangle(x+width/2-4,y-2,4,4);
    }
    
    
    public Rectangle getBounds(){
        return new Rectangle(x,y,width,height);
    }
    
}