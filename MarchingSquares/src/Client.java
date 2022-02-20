import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.Timer;
import javafx.util.Pair;
import javax.imageio.ImageIO;
public class Client extends JPanel implements ActionListener
{
    int xDim, yDim, rez, cols, rows, timer;
    ArrayList<Integer> rx, ry;
    float[][] field;
    float thresh;
    boolean dots, grad, terr, rect, pic;
    Timer clock;

    
    Client(){
        xDim = 2560;
        yDim = 1440;
        clock = new Timer(10,this);    
        clock.start(); 
        Dimension dim = new Dimension(xDim,yDim);        
        this.setPreferredSize(dim);
        this.setBackground(Color.BLACK);
        this.setVisible(true);
        this.setFocusable(true);
        
        timer = 10;
        thresh = 0.5f;
        dots = true;
        grad = false;
        terr = false;
        rect = false;
        pic = false;
        rez = 5;
        rx = new ArrayList<Integer>();
        ry = new ArrayList<Integer>();
        cols = xDim/rez;
        rows = yDim/rez;
        field = new float[cols][rows];
        for(int i = 0;i<cols;i++)
        {
            for(int j = 0;j<rows;j++)
            {
                field[i][j] = (float)(Math.random());
            }
        }
    }
    
    public void drawField(Graphics g)
    {
        for(int i = 0;i<cols;i++)
        {
            for(int j = 0;j<rows;j++)
            {
                if(dots)
                {
                    g.setColor(new Color((int)(255*field[i][j])));
                    if(i == 0 && j == 0)
                    {
                        System.out.println(field[i][j]);
                    }
                    g.fillRect(i*rez, j*rez, 2, 2);
                }
            }
        }
        g.setColor(new Color(255));
        for(int i = 0;i<cols-1;i++)
        {
            for(int j = 0;j<rows-1;j++)
            {
                int x = i*rez;
                int y = j*rez;
                Pair<Integer,Integer> a = new Pair<Integer,Integer>(x+rez/2,y);
                Pair<Integer,Integer> b = new Pair<Integer,Integer>(x+rez,y+rez/2);
                Pair<Integer,Integer> c = new Pair<Integer,Integer>(x+rez/2,y+rez);
                Pair<Integer,Integer> d = new Pair<Integer,Integer>(x,y+rez/2);
                int aVal = field[i][j] > thresh ? 1 : 0;
                int bVal = field[i+1][j] > thresh ? 1 : 0;
                int cVal = field[i+1][j+1] > thresh ? 1 : 0;
                int dVal = field[i][j+1] > thresh ? 1 : 0;
                int state = getState(aVal,bVal,cVal,dVal);
                switch(state)
                {
                    case 0:
                        break;
                    case 1:
                        drawIsoLines(c,d,g);
                        break;
                    case 2:
                        drawIsoLines(b,c,g);
                        break;
                    case 3:
                        drawIsoLines(b,d,g);
                        break;
                    case 4:
                        drawIsoLines(a,b,g);
                        break;
                    case 5:
                        drawIsoLines(a,d,g);
                        drawIsoLines(b,c,g);
                        break;
                    case 6:
                        drawIsoLines(a,c,g);
                        break;
                    case 7:
                        drawIsoLines(a,d,g);
                        break;
                    case 8:
                        drawIsoLines(a,d,g);
                        break;
                    case 9:
                        drawIsoLines(a,c,g);
                        break;
                    case 10:
                        drawIsoLines(a,b,g);
                        drawIsoLines(c,d,g);
                        break;
                    case 11:
                        drawIsoLines(a,b,g);
                        break;
                    case 12:
                        drawIsoLines(b,d,g);
                        break;
                    case 13:
                        drawIsoLines(b,c,g);
                        break;
                    case 14:
                        drawIsoLines(c,d,g);
                        break;
                    case 15:
                        break;
                }
                float rand = (float)Math.random();
                if(field[i][j] > 0.85 && rx.size() < 50 && rand < 0.0005)
                {
                    rx.add((int)(Math.random()*rez)+i*rez);
                    ry.add((int)(Math.random()*rez)+j*rez);
                }
            }
        }
    }
    
    public void drawGrad(float level, Graphics g)
    {
        for(int i = 0;i<cols-1;i++)
        {
            for(int j = 0;j<rows-1;j++)
            {
                int x = i*rez;
                int y = j*rez;
                float val = field[i][j];
                if(val < 0.4)
                {
                    g.setColor(new Color(0,0,(int)(255*(val/0.4f))));
                }
                else if(val < 0.6)
                {
                    g.setColor(new Color(0,(int)(Math.min(255*(1-(val/0.6f)+0.5),255)),0));
                }
                else if(val < 0.8)
                {
                    g.setColor(new Color((int)(Math.max(255*(val/0.8f-0.3),0)),(int)(Math.max(255*(val/0.8f-0.3),0)),(int)(Math.max(255*(val/0.8f-0.3),0))));
                }
                else
                {
                    g.setColor(Color.WHITE);
                }
                Pair<Integer,Integer> a = new Pair<Integer,Integer>(x+rez/2,y);
                Pair<Integer,Integer> b = new Pair<Integer,Integer>(x+rez,y+rez/2);
                Pair<Integer,Integer> c = new Pair<Integer,Integer>(x+rez/2,y+rez);
                Pair<Integer,Integer> d = new Pair<Integer,Integer>(x,y+rez/2);
                int aVal = field[i][j] > level ? 1 : 0;
                int bVal = field[i+1][j] > level ? 1 : 0;
                int cVal = field[i+1][j+1] > level ? 1 : 0;
                int dVal = field[i][j+1] > level ? 1 : 0;
                int state = getState(aVal,bVal,cVal,dVal);
                switch(state)
                {
                    case 0:
                        break;
                    case 1:
                        drawIsoLines(c,d,g);
                        break;
                    case 2:
                        drawIsoLines(b,c,g);
                        break;
                    case 3:
                        drawIsoLines(b,d,g);
                        break;
                    case 4:
                        drawIsoLines(a,b,g);
                        break;
                    case 5:
                        drawIsoLines(a,d,g);
                        drawIsoLines(b,c,g);
                        break;
                    case 6:
                        drawIsoLines(a,c,g);
                        break;
                    case 7:
                        drawIsoLines(a,d,g);
                        break;
                    case 8:
                        drawIsoLines(a,d,g);
                        break;
                    case 9:
                        drawIsoLines(a,c,g);
                        break;
                    case 10:
                        drawIsoLines(a,b,g);
                        drawIsoLines(c,d,g);
                        break;
                    case 11:
                        drawIsoLines(a,b,g);
                        break;
                    case 12:
                        drawIsoLines(b,d,g);
                        break;
                    case 13:
                        drawIsoLines(b,c,g);
                        break;
                    case 14:
                        drawIsoLines(c,d,g);
                        break;
                    case 15:
                        break;
                }
            }
        }
    }
    
    public void drawGField(Graphics g)
    {
        for(float k = 0;k<1;k+=0.01)
        {
            drawGrad(k,g);
        }
    }
    
    public void drawIsoLines(Pair<Integer,Integer> a, Pair<Integer,Integer> b, Graphics g)
    {
        g.drawLine(a.getKey(), a.getValue(), b.getKey(), b.getValue());
    }
    
    public int getState(int a, int b, int c, int d)
    {
        return a*8+b*4+c*2+d*1;
    }
    
    public void drawTerr(Graphics g)
    {
        for(int i = 0;i<xDim-rez;i++)
        {
            for(int j = 0;j<yDim-rez;j++)
            {
                float xc = ((float)i)/((float)rez);
                int x = xc-(i/rez) > 0.5 ? (i/rez)+1 : (i/rez);
                float yc = ((float)j)/((float)rez);
                int y = yc-(j/rez) > 0.5 ? (j/rez)+1 : (j/rez);
                float val = field[x][y];
                if(val < 0.4)
                {
                    g.setColor(new Color(0,0,(int)(255*(val/0.4f))));
                }
                else if(val < 0.6)
                {
                    g.setColor(new Color(0,(int)(Math.min(255*(1-(val/0.6f)+0.5),255)),0));
                }
                else if(val < 0.8)
                {
                    g.setColor(new Color((int)(Math.max(255*(val/0.8f-0.3),0)),(int)(Math.max(255*(val/0.8f-0.3),0)),(int)(Math.max(255*(val/0.8f-0.3),0))));
                }
                else
                {
                    g.setColor(Color.WHITE);
                }
                //g.setColor(new Color((int)(255*val)));
                g.drawRect(i, j, 1, 1);
            }
        }
    }
    public void drawRect(Graphics g)
    {
        g.setColor(Color.red);
        for(int i = 0;i<rx.size();i++)
        {
            for(int j = 0;j<ry.size();j++)
            {
                g.fillRect(rx.get(i)-1, ry.get(j)-1, 2, 2);
            }
        }
        
    }
        
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        if(grad)
        {
            drawGField(g);
        }
        else
        {
            drawField(g);
        }
        if(terr)
        {
            drawTerr(g);
        }
        if(rect)
        {
            drawRect(g);
        }
        repaint();
    }
    
    public boolean inBounds(int x, int y)
    {
        if(x >= 0 && x<cols && y >= 0 && y<rows)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    public void smooth()
    {
        for(int i = 0;i<cols;i++)
        {
            for(int j = 0;j<rows;j++)
            {
                float avg = field[i][j];
                int cnt = 1;
                if(inBounds(i,j+1))
                {
                    avg += field[i][j+1];
                    cnt++;
                }
                if(inBounds(i,j-1))
                {
                    avg += field[i][j-1];
                    cnt++;
                }
                if(inBounds(i+1,j))
                {
                    avg += field[i+1][j];
                    cnt++;
                }
                if(inBounds(i-1,j))
                {
                    avg += field[i-1][j];
                    cnt++;
                }
                field[i][j] = avg/cnt;
            }
        }
    }
    
    public void extrap()
    {
        float max = 0;
        float min = Float.MAX_VALUE;
        float range = 1;
        if(pic)
        {
            range = 16777216/256;
        }
        for(int i = 0;i<cols;i++)
        {
            for(int j = 0;j<rows;j++)
            {
                if(field[i][j] > max)
                {
                    max = field[i][j];
                }
                if(field[i][j] < min)
                {
                    min = field[i][j];
                }
            }
        }
        for(int i = 0;i<cols;i++)
        {
            for(int j = 0;j<rows;j++)
            {
                field[i][j] = ((field[i][j]-min)/(max-min))*(range);
            }
        }
    }
    
    public void reset()
    {
        thresh = 0.5f;
        dots = true;
        grad = false;
        terr = false;
        pic = false;
        rez = 2;
        cols = xDim/rez;
        rows = yDim/rez;
        field = new float[cols][rows];
        for(int i = 0;i<cols;i++)
        {
            for(int j = 0;j<rows;j++)
            {
                field[i][j] = (float)Math.random();
            }
        }
    }
    
    public void interMin()
    {
        for(int i = 0;i<cols;i++)
        {
            for(int j = 0;j<rows;j++)
            {
                float[] dists = new float[4];
                dists[0] = -1;
                dists[1] = -1;
                dists[2] = -1;
                dists[3] = -1;
                if(inBounds(i,j+1))
                {
                    dists[0] = field[i][j+1] - field[i][j];
                }
                if(inBounds(i,j-1))
                {
                    dists[1] = field[i][j-1] - field[i][j];
                }
                if(inBounds(i+1,j))
                {
                    dists[2] = field[i+1][j] - field[i][j];
                }
                if(inBounds(i-1,j))
                {
                    dists[3] = field[i-1][j] - field[i][j];
                }
                float min = -1;
                int index = -1;
                for(int k = 0;k<dists.length;k++)
                {
                    if(min == -1 && dists[k] != -1)
                    {
                        min = Math.abs(dists[k]);
                        index = k;
                    }
                    else if(min != -1 && dists[k] != -1 && Math.abs(dists[k]) > min)
                    {
                        min = Math.abs(dists[k]);
                        index = k;
                    }
                }
                if(index == 0)
                {
                    field[i][j] = field[i][j+1];
                }
                if(index == 1)
                {
                    field[i][j] = field[i][j-1];
                }
                if(index == 2)
                {
                    field[i][j] = field[i+1][j];
                }
                if(index == 3)
                {
                    field[i][j] = field[i-1][j];
                }
            }
        }
    }
    
    public void moveRect(int i)
    {
        float[] dists = new float[4];
        dists[0] = -1;
        dists[1] = -1;
        dists[2] = -1;
        dists[3] = -1;
        if(inBounds(rx.get(i)/rez,ry.get(i)/rez+1))
        {
            dists[0] = field[rx.get(i)/rez][ry.get(i)/rez+1];
        }
        if(inBounds(rx.get(i)/rez,ry.get(i)/rez-1))
        {
            dists[1] = field[rx.get(i)/rez][ry.get(i)/rez-1];
        }
        if(inBounds(rx.get(i)/rez+1,ry.get(i)/rez))
        {
            dists[2] = field[rx.get(i)/rez+1][ry.get(i)/rez];
        }
        if(inBounds(rx.get(i)/rez-1,ry.get(i)/rez))
        {
            dists[3] = field[rx.get(i)/rez-1][ry.get(i)/rez];
        }
        float min = -1;
        int index = -1;
        for(int k = 0;k<dists.length;k++)
        {
            if(min == -1 && dists[k] != -1)
            {
                min = Math.abs(dists[k]);
                index = k;
            }
            else if(min != -1 && dists[k] != -1 && Math.abs(dists[k]) < min)
            {
                min = Math.abs(dists[k]);
                index = k;
            }
        }
        if(index == 0)
        {
            rx.set(i,rx.get(i));
            ry.set(i,ry.get(i)+rez);
        }
        if(index == 1)
        {
            rx.set(i,rx.get(i));
            ry.set(i,ry.get(i)-rez);
        }
        if(index == 2)
        {
            rx.set(i,rx.get(i)+rez);
            ry.set(i,ry.get(i));
        }
        if(index == 3)
        {
            rx.set(i,rx.get(i)-rez);
            ry.set(i,ry.get(i));
        }
    }
    
    public void parsePic()
    {
        try{
            File file = new File("src//paper.jpg");
            BufferedImage img = ImageIO.read(file);
            thresh = 0.5f;
            dots = true;
            grad = false;
            terr = false;
            pic = true;
            rez = 1;
            cols = xDim/rez;
            rows = yDim/rez;
            field = new float[cols][rows];
            for(int i = 0;i<cols;i++)
            {
                for(int j = 0;j<rows;j++)
                {
                    if(true)
                    {
                        field[i][j] = (float)img.getRGB(i, j)/255f;
                    }
                    else
                    {
                        Color col = new Color(img.getRGB(i, j));
                        float r = col.getRed();
                        float g = col.getGreen();
                        float b = col.getBlue();
                        float avg = (r+g+b)/3f;
                        field[i][j] = avg/255f;
                    }
                }
            }
        }
        catch(IOException e)
        {
            System.out.println(e);
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(rect)
        {
            for(int i = 0;i<rx.size();i++)
            {
                moveRect(i);
            }
            if(timer > 0)
            {
                timer--;
            }
            else
            {
                timer = 5;
                if(rx.size() > 0)
                {
                    rx.remove(rx.size()-1);
                    ry.remove(ry.size()-1);
                }
            }
        }
    }
    
    
   public void addNotify(){
       super.addNotify();
         addKeyListener(new KeyHandler());
         addMouseListener(new MouseHandler());
      addMouseMotionListener(new MouseHandler());
   }
    public class KeyHandler extends KeyAdapter implements KeyListener {
      @Override
      public void keyPressed(KeyEvent e) {        
        String key = KeyEvent.getKeyText(e.getKeyCode());
        if(key.equals("Up"))
        {
            thresh = Math.min(thresh+0.01f,1);
        }
        if(key.equals("Down"))
        {
            thresh = Math.max(thresh-0.01f,0);
        }
        if(key.equals("Space"))
        {
            dots = !dots;
        }
        if(key.equals("W"))
        {
            smooth();
        }
        if(key.equals("S"))
        {
            grad = !grad;
        }
        if(key.equals("Q"))
        {
            terr = !terr;
        }
        if(key.equals("E"))
        {
            extrap();
        }
        if(key.equals("R"))
        {
            reset();
        }
        if(key.equals("F"))
        {
            interMin();
        }
        if(key.equals("T"))
        {
            rect = false;
        }
        if(key.equals("G"))
        {
            parsePic();
        }
      }
      @Override
      public void keyReleased(KeyEvent e)
      {
          String key = KeyEvent.getKeyText(e.getKeyCode());
          
      }
    
     
}
     public class MouseHandler extends MouseAdapter
    {
      @Override
      public void mouseMoved(MouseEvent e){
         
      }
      @Override
      public void mousePressed(MouseEvent e)
      {
          rect = true;
      }
     } 
}
