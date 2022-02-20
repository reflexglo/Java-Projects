
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.util.*;

public class Client extends JPanel implements ActionListener {

    Timer clock;
    int xDim,yDim,pix,numTiles,mOffX,mOffY,mX,mY;
    ArrayList<BiomeTile> setBiomes;
    ArrayList<BiomeTile> generate;
    ArrayList<BiomeTile> newBiomes;
    ArrayList<String> coords;
    BiomeTile checker;
    boolean exported;
    Client() {
        generate = new ArrayList<BiomeTile>();
        setBiomes = new ArrayList<BiomeTile>();
        newBiomes = new ArrayList<BiomeTile>();
        coords = new ArrayList<String>();
        pix = 2;
        numTiles = 200000;
        exported = false;
        mOffX = 0;
        mOffY = 0;
        mX = 1280;
        mY = 720;
        checker = new BiomeTile(0,0,pix,pix,0,Color.BLACK,0,0,0,0);
        int numSeeds = (int)(Math.random()*100)+20;
        for(int i = 0;i<numSeeds;i++){
            int randX = (int)(Math.random()*(2000/pix))*pix+320;
            int randY = (int)(Math.random()*(1000/pix))*pix+224;
            int randBiome = (int)(Math.random()*150);
            if(randBiome<50){
                randBiome = 0;
            }
            else if(randBiome<80){
                randBiome = 1;
            }
            else if(randBiome<100){
                randBiome = 2;
            }
            else if(randBiome<125){
                randBiome = 3;
            }
            else if(randBiome<140){
                randBiome = 4;
            }
            else if(randBiome<150){
                randBiome = 5;
            }
            int b1 = (int)(Math.random()*100);
            int b2 = (int)(Math.random()*100);
            int b3 = (int)(Math.random()*100);
            int b4 = (int)(Math.random()*100);
            generate.add(new BiomeTile(randX,randY,pix,pix,randBiome,Color.BLACK,b1,b2,b3,b4));
            coords.add(randX+" "+randY);
        }
        
        xDim = 2650;
        yDim = 1440;   
        Dimension dim = new Dimension(xDim, yDim);
        this.setPreferredSize(dim);
        this.setBackground(Color.blue);
        this.setVisible(true);
        this.setFocusable(true);
        clock = new Timer(30, this);
        clock.start();
    }
    public void drawTiles(Graphics g){
        for(int i = 0;i<setBiomes.size();i++){
            setBiomes.get(i).render(g);
        }
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g;
        if(exported){
        g2d.scale(2, 2);
        mX -= mOffX;
        mY -= mOffY;
        g2d.translate(mX,mY);
        System.out.println(g2d.getTransform().getTranslateX());
        }
        drawTiles(g);
        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(setBiomes.size()<numTiles){
            System.out.println(setBiomes.size());
            for(int i = 0;i<generate.size();i++){
                int newX = generate.get(i).getX();
                int newY = generate.get(i).getY();
                int newBi = generate.get(i).getBiome();
                int makeNewBi = (int)(Math.random()*(5*newBi+10));
                if(makeNewBi == 0){
                    newBi = newBi+1;
                }
                String newCoord = "";
                checker.setX(newX+pix);
                checker.setY(newY);
                newCoord = checker.getX()+" "+checker.getY();
                if(!coords.contains(newCoord)&&isInBounds(checker)){
                    int newBias = (int)(Math.random()*100);
                    if(generate.get(i).getBias(0)<newBias){
                        int b1 = generate.get(i).getBias(0);
                        int b2 = generate.get(i).getBias(1);
                        int b3 = generate.get(i).getBias(2);
                        int b4 = generate.get(i).getBias(3);
                        newBiomes.add(new BiomeTile(checker.getX(),checker.getY(),pix,pix,newBi,Color.BLACK,b1,b2,b3,b4));
                        coords.add(newCoord);
                    }
                }
                checker.setX(newX-pix);
                checker.setY(newY);
                newCoord = checker.getX()+" "+checker.getY();
                if(!coords.contains(newCoord)&&isInBounds(checker)){
                    int newBias = (int)(Math.random()*100);
                    if(generate.get(i).getBias(1)<newBias){
                        int b1 = generate.get(i).getBias(0);
                        int b2 = generate.get(i).getBias(1);
                        int b3 = generate.get(i).getBias(2);
                        int b4 = generate.get(i).getBias(3);
                        newBiomes.add(new BiomeTile(checker.getX(),checker.getY(),pix,pix,newBi,Color.BLACK,b1,b2,b3,b4));
                        coords.add(newCoord);
                    }
                }
                checker.setX(newX);
                checker.setY(newY+pix);
                newCoord = checker.getX()+" "+checker.getY();
                if(!coords.contains(newCoord)&&isInBounds(checker)){
                    int newBias = (int)(Math.random()*100);
                    if(generate.get(i).getBias(2)<newBias){
                        int b1 = generate.get(i).getBias(0);
                        int b2 = generate.get(i).getBias(1);
                        int b3 = generate.get(i).getBias(2);
                        int b4 = generate.get(i).getBias(3);
                        newBiomes.add(new BiomeTile(checker.getX(),checker.getY(),pix,pix,newBi,Color.BLACK,b1,b2,b3,b4));
                        coords.add(newCoord);
                    }
                }
                checker.setX(newX);
                checker.setY(newY-pix);
                newCoord = checker.getX()+" "+checker.getY();
                if(!coords.contains(newCoord)&&isInBounds(checker)){
                    int newBias = (int)(Math.random()*100);
                    if(generate.get(i).getBias(3)<newBias){
                        int b1 = generate.get(i).getBias(0);
                        int b2 = generate.get(i).getBias(1);
                        int b3 = generate.get(i).getBias(2);
                        int b4 = generate.get(i).getBias(3);
                        newBiomes.add(new BiomeTile(checker.getX(),checker.getY(),pix,pix,newBi,Color.BLACK,b1,b2,b3,b4));
                        coords.add(newCoord);
                    }
                }
            }
            for(int i = 0;i<generate.size();i++){
                setBiomes.add(generate.get(i));
                generate.remove(i);
            }
            for(int i = 0;i<newBiomes.size();i++){
                generate.add(newBiomes.get(i));
                newBiomes.remove(i);
            }
        }
        if(setBiomes.size() >= numTiles && !exported){
            System.out.println("exporting");
            try{
                BufferedWriter writer = new BufferedWriter(new FileWriter("src//map.txt"));
                for(int i = 0;i<setBiomes.size();i++){
                    BiomeTile bi = setBiomes.get(i);
                    writer.write(bi.getX()+","+bi.getY()+","+bi.getBiome()+","+pix+"\n");
                    System.out.println(i);
                }
                writer.close();
                exported = true;
                System.out.println("exported");
            }
            catch(IOException ex){
                System.out.println(ex);
            }
        }
    }

    public void addNotify() {
        super.addNotify();
        addKeyListener(new KeyHandler());
        addMouseListener(new MouseHandler());
        addMouseMotionListener(new MouseHandler());
    }

    public class KeyHandler extends KeyAdapter implements KeyListener {

        @Override
        public void keyPressed(KeyEvent e) {
            String key = KeyEvent.getKeyText(e.getKeyCode());
            
        }

    }

    public class MouseHandler extends MouseAdapter {
        //is called when the mouse is moved
        @Override
        public void mouseMoved(MouseEvent e) {
                if(e.getX() < 1280)
                {
                    mOffX = -(1280-e.getX())/200;
                }
                else if(e.getX() > 1280)
                {
                    mOffX = (e.getX()-1280)/200;
                }
                if(e.getY() < 720)
                {
                    mOffY = -(720-e.getY())/100;
                }
                else if(e.getY() > 720)
                {
                    mOffY = (e.getY()-720)/100;
                }
        }
        //is called when the mouse button is pressed
        @Override
        public void mousePressed(MouseEvent e) {
            
            }
        }
    private boolean isInBounds(BiomeTile s){
        Rectangle bounds = new Rectangle(0,0,2560,1440);
        return s.getBounds().intersects(bounds);
    }
    }
