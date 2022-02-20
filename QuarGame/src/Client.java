import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Client extends JPanel implements ActionListener {
    
    ArrayList<Wall> walls;
    Survivor sur;
    Goal goal;
    ArrayList<Sniper> snipes;
    ArrayList<Wall> boundaries;
    int score, gameState, sniperTimer, roundBuffer;
    Timer clock;
    
    public Client() {
        Dimension dim = new Dimension(2560, 1440);
        this.setPreferredSize(dim);
        this.setBackground(Color.WHITE);
        this.setVisible(true);
        this.setFocusable(true);
        score = 0;
        resetMap();
        clock = new Timer(20, this);
        clock.start();
    }
    public ArrayList<Wall> createBoundaries(){
        ArrayList<Wall> newBoundaries = new ArrayList<Wall>();
        newBoundaries.add(new Wall(-10,0,10,1440,Color.BLACK));
        newBoundaries.add(new Wall(2560,0,10,1440,Color.BLACK));
        newBoundaries.add(new Wall(0,-10,2560,10,Color.BLACK));
        newBoundaries.add(new Wall(0,1440,2560,10,Color.BLACK));
        return newBoundaries;
    }
    public ArrayList<Wall> createWalls(){
        ArrayList<Wall> newWalls = new ArrayList<Wall>();
        int numWalls = (int)(Math.random()*14)+7;
        for(int i = 0;i<numWalls;i++){
            int randX = (int)(Math.random()*1600);
            int randY = (int)(Math.random()*1000);
            int randW = (int)(Math.random()*400)+80;
            int randH = (int)(Math.random()*200)+20;
            newWalls.add(new Wall(randX,randY,randW,randH,Color.BLACK));
        }
        return newWalls;
    }
    public Survivor createSurvivor(ArrayList<Wall> newWalls){
        boolean safeSpawn = false;
        int randX = 0;
        int randY = 0;
        while(safeSpawn == false){
            randX = (int)(Math.random()*2000)+280;
            randY = (int)(Math.random()*1200)+120;
            Survivor testSpawn = new Survivor(randX,randY,25,25,Color.BLACK);
                if(!isCollision(testSpawn.getBounds(),newWalls)){
                    safeSpawn = true;
                }
        }
        Color randC = new Color((int)(Math.random()*1000000));
        Survivor newSur = new Survivor(randX,randY,25,25,randC);
        return newSur;
    }
    public Goal createGoal(ArrayList<Wall> newWalls,Survivor newSur){
        boolean validGoal = false;
        int randX = 0;
        int randY = 0;
        while(validGoal == false){
            randX = (int)(Math.random()*2000)+280;
            randY = (int)(Math.random()*1200)+120;
            Goal testGoal = new Goal(randX,randY,50,50,Color.BLACK);
            if(goalDistance(newSur,testGoal)>1250){
               if(!isCollision(testGoal.getBounds(),walls)){
                   validGoal = true;
               } 
            }
        }
        Goal newGoal = new Goal(randX,randY,50,50,Color.BLUE);
        return newGoal;
    }
    public ArrayList<Sniper> createSnipers(ArrayList<Wall> newWalls,Survivor newSur,Goal newGoal){
        ArrayList<Sniper> newSnipes = new ArrayList<Sniper>();
        int numSnipers = (int)(Math.random()*12)+4;
        for(int i = 0;i<numSnipers;i++){
            boolean validSpawn = false;
            int randX = 0;
            int randY = 0;
            while(validSpawn == false){
                randX = (int)(Math.random()*2000)+280;
                randY = (int)(Math.random()*1200)+120;
                Sniper testSpawn = new Sniper(randX,randY,25,25,100,Color.BLACK);
                if(spawnCampDist(testSpawn,newSur)>250){
                    if(goalCampDist(testSpawn,newGoal)>250){
                        if(!isCollision(testSpawn.getBounds(),newWalls)){
                            validSpawn = true;
                        }
                    }
                }
            }
           newSnipes.add(new Sniper(randX,randY,25,25,175,Color.RED));
        }
        return newSnipes;
    }
    public int goalDistance(Survivor newSur,Goal newGoal){
        int xDist = (newSur.getX()-newGoal.getX());
        int yDist = (newSur.getY()-newGoal.getY());
        int tDist = (int)(Math.pow(xDist,2)+Math.pow(yDist,2));
        return (int)(Math.sqrt(tDist));
    }
    public int goalCampDist(Sniper newSnipe,Goal newGoal){
        int xDist = (newSnipe.getX()-newGoal.getX());
        int yDist = (newSnipe.getY()-newGoal.getY());
        int tDist = (int)(Math.pow(xDist,2)+Math.pow(yDist,2));
        return (int)(Math.sqrt(tDist));
    }
    public int spawnCampDist(Sniper newSnipe,Survivor newSur){
        int xDist = (newSnipe.getX()-newSur.getX());
        int yDist = (newSnipe.getY()-newSur.getY());
        int tDist = (int)(Math.pow(xDist,2)+Math.pow(yDist,2));
        return (int)(Math.sqrt(tDist));
    }
    public void checkSurvivorBounds(){
        if(isCollision(sur.getTop(),walls)||isCollision(sur.getTop(),boundaries)){
            sur.setFoward(false);
        }
        if(isCollision(sur.getBottom(),walls)||isCollision(sur.getBottom(),boundaries)){
            sur.setBackward(false);
        }
        if(isCollision(sur.getLeft(),walls)||isCollision(sur.getLeft(),boundaries)){
            sur.setLeftward(false);
        }
        if(isCollision(sur.getRight(),walls)||isCollision(sur.getRight(),boundaries)){
            sur.setRightward(false);
        }
    }
    public void checkSniperBounds(){
        for(int i = 0;i<snipes.size();i++){
        if(isCollision(snipes.get(i).getTop(),walls)||isCollision(snipes.get(i).getTop(),boundaries)){
            snipes.get(i).setSpeedY(-snipes.get(i).getSpeedY());
        }
        if(isCollision(snipes.get(i).getBottom(),walls)||isCollision(snipes.get(i).getBottom(),boundaries)){
            snipes.get(i).setSpeedY(-snipes.get(i).getSpeedY());
        }
        if(isCollision(snipes.get(i).getLeft(),walls)||isCollision(snipes.get(i).getLeft(),boundaries)){
            snipes.get(i).setSpeedX(-snipes.get(i).getSpeedX());
        }
        if(isCollision(snipes.get(i).getRight(),walls)||isCollision(snipes.get(i).getRight(),boundaries)){
            snipes.get(i).setSpeedX(-snipes.get(i).getSpeedX());
        }
        }
    }
    public void sniperMovement(){
        checkSniperBounds();
        for(int i = 0;i<snipes.size();i++){
            snipes.get(i).movement();
        }
        if(sniperTimer==0){
            for(int i = 0;i<snipes.size();i++){
                snipes.get(i).randMove();
            }
            sniperTimer = 10;
        }
        else{
            sniperTimer--;
        }
    }
    public void resetMap(){
        gameState = 2;
        sniperTimer = 10;
        roundBuffer = 100;
        boundaries = createBoundaries();
        walls = createWalls();
        sur = createSurvivor(walls);
        goal = createGoal(walls,sur);
        snipes = createSnipers(walls,sur,goal);
    }
    public void drawWalls(Graphics g){
        for(int i = 0;i<walls.size();i++){
            walls.get(i).render(g);
        }
    }
    public void drawSurvivor(Graphics g){
        sur.render(g);
    }
    public void drawGoal(Graphics g){
        goal.render(g);
    }
    public void drawSnipers(Graphics g){
        for(int i = 0;i<snipes.size();i++){
            snipes.get(i).render(g);
        }
    }
    public void drawScore(Graphics g){
        g.setFont(new Font("Courier", Font.PLAIN, 20));
        g.setColor(Color.BLACK);
        g.drawString("Score: " + score, 25, 25);
    }
    public void drawEnd(Graphics g){
        g.setFont(new Font("Courier", Font.PLAIN, 60));
        g.setColor(Color.BLACK);
        g.drawString("You Have Been Spotted",900,500);
        g.drawString("Final Score: " + score, 1000,600);
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(gameState == 0||gameState == 2){
            drawWalls(g);
            drawSurvivor(g);
            drawGoal(g);
            drawSnipers(g);
            drawScore(g);
        }
        if(gameState == 1){
            drawEnd(g);
        }
        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(gameState == 2){
        if(roundBuffer>0){
            roundBuffer--;
        }
        else{
            gameState = 0;
        }
        }
        if(gameState == 0){
        checkSurvivorBounds();
        sur.movement();
        sniperMovement();
        if(isCollision(sur,goal)){
            resetMap();
            score++;
        }
        for(int i = 0;i<snipes.size();i++){
            if(isCollision(sur,snipes.get(i).getRangeBounds())){
                gameState = 1;
            }
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
            if(key.equals("Up")){
                sur.setFoward(true);
            }
            if(key.equals("Down")){
                sur.setBackward(true);
            }
            if(key.equals("Left")){
                sur.setLeftward(true);
            }
            if(key.equals("Right")){
                sur.setRightward(true);
            }
            if(key.equals("Escape")){
                System.exit(0);
            }
            if(key.equals("Space")&&gameState==1){
                score = 0;
                resetMap();
            }
        }
        @Override
        public void keyReleased(KeyEvent e){
            String key = KeyEvent.getKeyText(e.getKeyCode());
            if(key.equals("Up")){
                sur.setFoward(false);
            }
            if(key.equals("Down")){
                sur.setBackward(false);
            }
            if(key.equals("Left")){
                sur.setLeftward(false);
            }
            if(key.equals("Right")){
                sur.setRightward(false);
            }
        }
    }
    public class MouseHandler extends MouseAdapter {

        public void mouseMoved(MouseEvent e) {
            
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            
        }
    }
    private boolean isCollision(Survivor s, Goal g){
        boolean collision;
        collision = s.getBounds().intersects(g.getBounds());
        return collision;
    }
    private boolean isCollision(Survivor s, Rectangle r){
        boolean collision;
        collision = s.getBounds().intersects(r.getBounds());
        return collision;
    }
    private boolean isCollision(Rectangle r, ArrayList<Wall> w) {
        boolean collision = false;
        for(int i = 0;i<w.size();i++){
            if(r.getBounds().intersects(w.get(i).getBounds())){
                collision = true;
            }
        }
        return collision;
    }
    
}
