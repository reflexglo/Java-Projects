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
    int xDim,yDim,gameState,diff,map,round,money,selTow,lives;
    boolean roundStart, fastFoward, select, upgrade;
    Menu play, exit, difficultyChoice, mapChoice, startGame;
    RoundStartMenu startRound, speedUp;
    Mouse mouse;
    ArrayList<Map> mapList;
    MapHolder mapHolder;
    ArrayList<TowerMenu> towerMenuList;
    TowerHolder towerHolder;
    ArrayList<Enemy> enemyList;
    RoundHolder roundHolder;
    ArrayList<Tower> towerList;
    Tower towerPicked;
    UpgradeMenu upgradeTower;
    RoundStats roundStats;

    Client() {
        mapList = new ArrayList<Map>();
        towerMenuList = new ArrayList<TowerMenu>();
        enemyList = new ArrayList<Enemy>();
        towerList = new ArrayList<Tower>();
        mapHolder = new MapHolder();
        towerHolder = new TowerHolder();
        roundHolder = new RoundHolder();
        mapList.add(mapHolder.SnakeWay());
        towerMenuList.add(towerHolder.gokuMenu());
        diff = 1;
        map = 0;
        round = 1;
        money = 0;
        selTow = 0;
        lives = 0;
        roundStart = false;
        fastFoward = false;
        select = false;
        upgrade = false;
        play = new Menu(980,380,600,100,"Play",Color.lightGray);
        exit = new Menu(980,860,600,100,"Exit",Color.lightGray);
        difficultyChoice = new Menu(980,380,600,100,"Diff: "+diff,Color.lightGray);
        mapChoice = new Menu(980,620,600,100,"Map: "+mapList.get(map).getName(),Color.lightGray);
        startGame = new Menu(980,860,600,100,"Start",Color.lightGray);
        startRound = new RoundStartMenu(2130,1200,300,50,"Start",Color.lightGray);
        speedUp = new RoundStartMenu(2130,1300,300,50,"Normal",Color.lightGray);
        upgradeTower = new UpgradeMenu(2130,1100,300,75,"",0,Color.lightGray);
        roundStats = new RoundStats(10,10,100,100,money,round,lives,Color.WHITE);
        mouse = new Mouse(0,0,1,1,Color.black);
        towerPicked = null;
        gameState = 0;
        xDim = 2650;
        yDim = 1440;   
        Dimension dim = new Dimension(xDim, yDim);
        this.setPreferredSize(dim);
        this.setBackground(Color.black);
        this.setVisible(true);
        this.setFocusable(true);
        clock = new Timer(15, this);
        clock.start();
    }
    public void drawStartMenu(Graphics g){
        play.render(g);
        exit.render(g);
    }
    public void drawChoiceMenu(Graphics g){
        difficultyChoice.render(g);
        mapChoice.render(g);
        startGame.render(g);
    }
    public void drawMap(Graphics g){
        mapList.get(map).render(g);
    }
    public void drawTowerMenu(Graphics g){
        g.drawRect(2000, 0, 560, 1440);
        g.setColor(Color.gray);
        g.fillRect(2000, 0, 560, 1440);
        for(int i = 0;i<towerMenuList.size();i++){
            towerMenuList.get(i).render(g);
        }
        startRound.render(g);
        speedUp.render(g);
    }
    public void drawEnemies(Graphics g){
        for(int i = 0;i<enemyList.size();i++){
            enemyList.get(i).render(g);
        }
    }
    public void drawTowers(Graphics g){
        for(int i = 0;i<towerList.size();i++){
            towerList.get(i).render(g);
            for(int k = 0;k<towerList.get(i).getProjList().size();k++){
                towerList.get(i).getProjList().get(k).render(g);
            }
        }
    }
    public void drawPicked(Graphics g){
        towerPicked.render(g);
    }
    public void drawUpgrade(Graphics g){
        upgradeTower.render(g);
    }
    public void drawRoundStats(Graphics g){
        roundStats.render(g);
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(gameState == 0){
            drawStartMenu(g);
        }
        if(gameState == 1){
            drawChoiceMenu(g);
        }
        if(gameState == 2){
            drawMap(g);
            drawTowerMenu(g);
            drawTowers(g);
            drawRoundStats(g);
            if(roundStart){
                drawEnemies(g);
            }
            if(select){
                drawPicked(g);
            }
            if(upgrade){
                drawUpgrade(g);
            }
        }
        repaint();
    }
    public void prepareRound(){
        enemyList = roundHolder.round(round);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(roundStart == true && enemyList.size() == 0){
            round++;
            roundStart = false;
            if(round>1){
            money += 50*(3-diff)+100;
            }
            for(int i = 0;i<towerList.size();i++){
                towerList.get(i).clearProjList();
            }
        }
        if(roundStart == true && enemyList.size()>0){
            for(int i = 0;i<towerList.size();i++){
                for(int k = 0;k<towerList.get(i).getProjList().size();k++){
                    for(int j = 0;j<enemyList.size();j++){
                        if(isCollision(enemyList.get(j),towerList.get(i).getProjList().get(k))){
                            enemyList.get(j).setHealth(enemyList.get(j).getHealth()-towerList.get(i).getDmg());
                            towerList.get(i).getProjList().get(k).setPierce(towerList.get(i).getProjList().get(k).getPierce()-1);
                            if(towerList.get(i).getProjList().get(k).getPierce()==0){
                                towerList.get(i).removeProj(k);
                                j = enemyList.size();
                                k--;
                            }
                            else{
                                boolean newTar = towerList.get(i).findNewTar(enemyList, k);
                                if(!newTar){
                                    j = enemyList.size();
                                    k--;
                                }
                            }
                        }
                    }
                }
                towerList.get(i).update(enemyList);
            }
            for(int i = 0;i<enemyList.size();i++){
                boolean reachedEnd = false;
                enemyList.get(i).move();
                for(int k = 0;k<mapList.get(map).size();k++){
                    if(isCollision(enemyList.get(i),mapList.get(map).getTrack(k))){
                        if(mapList.get(map).getTrack(k).getDirection()!=4){
                            enemyList.get(i).setDirection(mapList.get(map).getTrack(k).getDirection());
                        }
                        else{
                            lives-=enemyList.get(i).getHealth();
                            enemyList.remove(i);
                            k = mapList.get(map).size();
                            i--;
                            reachedEnd = true;
                        }   
                    }
                }
                if(!reachedEnd){
                    if(enemyList.get(i).getHealth()<=0){
                        money += enemyList.get(i).getPrice();
                        enemyList.remove(i);
                        i--;
                    }
                }
            }
        }
        if(select){
            towerPicked.setX(mouse.getX());
            towerPicked.setY(mouse.getY());
        }
        roundStats.setMoney(money);
        roundStats.setRound(round);
        roundStats.setLives(lives);
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
            mouse.setX(e.getX());
            mouse.setY(e.getY());
        }
        //is called when the mouse button is pressed
        @Override
        public void mousePressed(MouseEvent e) {
            if(gameState == 0){
                if(isCollision(mouse,play)){
                    gameState = 1;
                }
                if(isCollision(mouse,exit)){
                    System.exit(0);
                }
            }
            if(gameState == 1){
                if(isCollision(mouse,difficultyChoice)){
                    diff++;
                    if(diff == 4){
                        diff = 1;
                    }
                    difficultyChoice.setText("Diff: "+diff);
                }
                if(isCollision(mouse,mapChoice)){
                    map++;
                    if(map >= mapList.size()){
                        map = 0;
                    }
                    mapChoice.setText("Map: "+mapList.get(map).getName());
                }
                if(isCollision(mouse,startGame)){
                    money = 100*(3-diff)+30000;
                    round = 1;
                    lives = 50*(3-diff)+100;
                    gameState = 2;
                }
            }
            if(gameState == 2){
                if(isCollision(mouse,startRound) && roundStart == false){
                    roundStart = true;
                    prepareRound();
                }
                if(isCollision(mouse,speedUp) && roundStart == true){
                    if(fastFoward){
                        fastFoward = false;
                        clock.setDelay(15);
                        speedUp.setText("Normal");
                    }
                    else{
                        fastFoward = true;
                        clock.setDelay(5);
                        speedUp.setText("Faster");
                    }
                }
                for(int i = 0;i<towerList.size();i++){
                    if(isCollision(mouse,towerList.get(i))){
                        upgrade = true;
                        upgradeTower.setText(towerList.get(i).upgradeName());
                        upgradeTower.setPrice(towerList.get(i).upgradePrice());
                        selTow = i;
                    }
                }
                if(upgrade && isCollision(mouse,upgradeTower) && money>=towerList.get(selTow).upgradePrice()){
                    money -= towerList.get(selTow).upgradePrice();
                    towerList.get(selTow).upgradeTower();
                    upgradeTower.setText(towerList.get(selTow).upgradeName());
                    upgradeTower.setPrice(towerList.get(selTow).upgradePrice());
                }
                if(select){
                    boolean placeable = true;
                    for(int i = 0;i<mapList.get(map).size();i++){
                        if(isCollision(towerPicked,mapList.get(map).getTrack(i))){
                            placeable = false;
                        }
                    }
                    if(isCollision(towerPicked,new Rectangle(2000, 0, 560, 1440))){
                        placeable = false;
                    }
                    if(placeable){
                        towerList.add(towerPicked);
                        towerPicked = null;
                        select = false;
                    }
                }
                else{
                for(int i = 0;i<towerMenuList.size();i++){
                    if(isCollision(mouse,towerMenuList.get(i))&&towerMenuList.get(i).getPrice()<=money&&!select){
                        money -= towerMenuList.get(i).getPrice();
                        if(i == 0){
                            towerPicked = new Goku(mouse.getX(),mouse.getY());
                            select = true;
                        }
                    }
                }
                }
            }
            }
        }
    private boolean isCollision(Mouse Mouse, Menu Option) {
        boolean collision = Mouse.getBounds().intersects(Option.getBounds());
        return collision;
    }
    private boolean isCollision(Mouse Mouse, RoundStartMenu Option) {
        boolean collision = Mouse.getBounds().intersects(Option.getBounds());
        return collision;
    }
    private boolean isCollision(Mouse Mouse, TowerMenu Option) {
        boolean collision = Mouse.getBounds().intersects(Option.getBounds());
        return collision;
    }
    private boolean isCollision(Mouse Mouse, UpgradeMenu Option) {
        boolean collision = Mouse.getBounds().intersects(Option.getBounds());
        return collision;
    }
    private boolean isCollision(Mouse Mouse, Tower Option) {
        boolean collision = Mouse.getBounds().intersects(Option.getBounds());
        return collision;
    }
    private boolean isCollision(Enemy Enemy, Track Track) {
        boolean highCollision = Enemy.getHighBound().intersects(Track.getBounds());
        boolean lowCollision = Enemy.getLowBound().intersects(Track.getBounds());
        return highCollision && lowCollision;
    }
    private boolean isCollision(Enemy Enemy, Projectile Projectile) {
        boolean collision = Enemy.getBounds().intersects(Projectile.getBounds());
        return collision;
    }
    private boolean isCollision(Tower Tower, Track Track) {
        boolean collision = Tower.getBounds().intersects(Track.getBounds());
        return collision;
    }
    private boolean isCollision(Tower Tower, Rectangle Rect) {
        boolean collision = Tower.getBounds().intersects(Rect);
        return collision;
    }
    }
