
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.util.*;

public class Client extends JPanel implements ActionListener {

    Timer clock;
    int xDim,yDim,xBound,yBound,xStart,yStart,speed;
    ArrayList<Snake> rattle = new ArrayList<Snake>();
    Food food;
    Rectangle bound;
    Client() {
        xDim = 2650;
        yDim = 1440;
        xBound = 1800;
        yBound = 1000;
        xStart = 1000;
        yStart = 200;
        speed = 5;
        int startX = (int)((Math.random()*(xBound-xStart))+xStart);
        int startY = (int)((Math.random()*(yBound-yStart))+yStart);
        int foodX = (int)((Math.random()*(xBound-xStart))+xStart);
        int foodY = (int)((Math.random()*(yBound-yStart))+yStart);
        rattle.add(new Snake(startX,startY,10,10,Color.BLACK));
        food = new Food(foodX,foodY,10,10,Color.RED);
        bound = new Rectangle(xStart,yStart,xBound-xStart,yBound-yStart);
        
        
        Dimension dim = new Dimension(xDim, yDim);
        this.setPreferredSize(dim);
        this.setBackground(Color.white);
        this.setVisible(true);
        this.setFocusable(true);
        clock = new Timer(20, this);
        clock.start();
    }
    public void drawSnake(Graphics g){
        for(int i = 0;i<rattle.size();i++){
            rattle.get(i).render(g);
        }
    }
    public void drawFood(Graphics g){
        food.render(g);
    }
    public void drawBound(Graphics g){
        g.drawRect(xStart,yStart,xBound-xStart,yBound-yStart);
        g.setColor(Color.GREEN);
        g.fillRect(xStart,yStart,xBound-xStart,yBound-yStart);
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBound(g);
        drawSnake(g);
        drawFood(g);
        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        rattle.get(0).moveSnake(speed, rattle);
        if(isCollision(rattle.get(0),food)){
            int foodX = (int)((Math.random()*(xBound-xStart))+xStart);
        int foodY = (int)((Math.random()*(yBound-yStart))+yStart);
            food = new Food(foodX,foodY,10,10,Color.RED);
            rattle.add(new Snake(0,0,10,10,Color.BLACK));
    }
        if(!isCollision(rattle.get(0),bound)){
            System.exit(0);
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
            if(key.equals("W")&&!rattle.get(0).getDir().equals("S")){
                rattle.get(0).setDir(key);
            }
            if(key.equals("S")&&!rattle.get(0).getDir().equals("W")){
                rattle.get(0).setDir(key);
            }
            if(key.equals("A")&&!rattle.get(0).getDir().equals("D")){
                rattle.get(0).setDir(key);
            }
            if(key.equals("D")&&!rattle.get(0).getDir().equals("A")){
                rattle.get(0).setDir(key);
            }
        }

    }

    public class MouseHandler extends MouseAdapter {
        //is called when the mouse is moved
        @Override
        public void mouseMoved(MouseEvent e) {
            
            
        }
        //is called when the mouse button is pressed
        @Override
        public void mousePressed(MouseEvent e) {
            
            }
        }
    private boolean isCollision(Snake s, Food f) {
        boolean collision = s.getBounds().intersects(f.getBounds());
        return collision;
    }
    private boolean isCollision(Snake s, Rectangle r) {
        boolean collision = s.getBounds().intersects(r.getBounds());
        return collision;
    }
    }
