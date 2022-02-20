
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class Goku implements Tower{

    int x;
    int y;
    int height;
    int width;
    Image img;
    Image projImg;
    int atkDmg;
    int atkSpeed;
    int atkTimer;
    int range;
    int upgrade;
    int projSpeed;
    int projPierce;
    int spiritCharge;
    int spiritTimer;
    boolean spt, atk;
    ArrayList<Projectile> projList;
    
    public Goku(int x, int y){
        this.x = x;
        this.y = y;
        width = 120;
        height = 120;
        File file = new File("src//gokubase.jpg");
        try {
            img = ImageIO.read(file);
        } catch (IOException ex) {
            Logger.getLogger(TowerHolder.class.getName()).log(Level.SEVERE, null, ex);
        }
        file = new File("src//gokuki.png");
        try {
            projImg = ImageIO.read(file);
        } catch (IOException ex) {
            Logger.getLogger(TowerHolder.class.getName()).log(Level.SEVERE, null, ex);
        }
        atkDmg = 2;
        atkSpeed = 170;
        atkTimer = 0;
        spiritCharge = 1000;
        spiritTimer = 0;
        range = 250;
        upgrade = 0;
        projSpeed = 3;
        projPierce = 2;
        atk = true;
        spt = false;
        projList = new ArrayList<Projectile>();
    }
    @Override
    public void update(ArrayList<Enemy> enemyList){
        int tarX = 0;
        int tarY = 0;
        boolean hasTar = false;
        for(int i = 0;i<enemyList.size();i++){
            if(enemyList.get(i).getBounds().intersects(getRange())){
                hasTar = true;
                tarX = enemyList.get(i).getX()+enemyList.get(i).getWidth()/2;
                tarY = enemyList.get(i).getY()+enemyList.get(i).getHeight()/2;
                i = enemyList.size();
            }
        }
        if(upgrade>=4){
            if(spiritTimer>0){
                spiritTimer--;
            }
            if(hasTar && spiritTimer == 0){
                spiritBomb(tarX,tarY);
                spiritTimer = spiritCharge;
            }
        }
        if(atkTimer>0){
            atkTimer--;
        }
        if(hasTar && atkTimer == 0){
            attack(tarX,tarY);
            atkTimer = atkSpeed;
        }
        for(int i = 0;i<projList.size();i++){
            if(projList.get(i).getTarX()==projList.get(i).getX()&&projList.get(i).getTarY()==projList.get(i).getY()){
                projList.remove(i);
            }
            else{
                projList.get(i).move();
            }
        }
        if(!hasTar){
            clearProjList();
        }
    }
    
    @Override
    public int support() {
        return 0;
    }

    public void spiritBomb(int tarX, int tarY){
        projList.add(new Projectile(x,y,300,300,tarX,tarY,1,1000,projImg,Color.black));
    }
    
    @Override
    public void attack(int tarX, int tarY) {
        projList.add(new Projectile(x+width/4,y+height/4,60,60,tarX,tarY,projSpeed,projPierce,projImg,Color.black));
    }

    @Override
    public void upgradeTower() {
        if(upgrade == 0){
            upgrade++;
            atkSpeed = 70;
            atkDmg = 3;
            File file = new File("src//gokussj.jpg");
            try {
                img = ImageIO.read(file);
            } catch (IOException ex) {
                Logger.getLogger(TowerHolder.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else if(upgrade == 1){
            upgrade++;
            range = 450;
            projSpeed = 4;
            File file = new File("src//gokuss3.jpg");
            try {
                img = ImageIO.read(file);
            } catch (IOException ex) {
                Logger.getLogger(TowerHolder.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else if(upgrade == 2){
            upgrade++;
            atkSpeed = 40;
            projPierce = 5;
            File file = new File("src//gokussg.jpg");
            try {
                img = ImageIO.read(file);
            } catch (IOException ex) {
                Logger.getLogger(TowerHolder.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else if(upgrade == 3){
            upgrade++;
            range = 900;
            File file = new File("src//gokussb.jpg");
            try {
                img = ImageIO.read(file);
            } catch (IOException ex) {
                Logger.getLogger(TowerHolder.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else if(upgrade == 4){
            upgrade++;
            atkDmg = 10;
            spiritCharge = 500;
            range = 3000;
            File file = new File("src//gokuui.jpg");
            try {
                img = ImageIO.read(file);
            } catch (IOException ex) {
                Logger.getLogger(TowerHolder.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    @Override
    public ArrayList<Projectile> getProjList(){
        return projList;
    }
    @Override
    public void render(Graphics g) {
        g.drawImage(img, x, y, width, height, null);
        for(int i = 0;i<projList.size();i++){
            projList.get(i).render(g);
        }
    }

    @Override
    public void setX(int x) {
        this.x = x;
    }

    @Override
    public void setY(int y) {
        this.y = y;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }
    @Override
    public int getDmg(){
        return atkDmg;
    }
    @Override
    public void removeProj(int i){
        projList.remove(i);
    }
    @Override
    public Rectangle getBounds() {
        return new Rectangle(x,y,width,height);
    }

    @Override
    public boolean getSpt() {
        return spt;
    }

    @Override
    public boolean getAtk() {
        return atk;
    }
    @Override
    public String upgradeName(){
        if(upgrade == 0){
            return "Super Saiyan";
        }
        else if(upgrade == 1){
            return "Super Saiyan 3";
        }
        else if(upgrade == 2){
            return "Super Saiyan God";
        }
        else if(upgrade == 3){
            return "Super Saiyan Blue";
        }
        else if(upgrade == 4){
            return "Ultra Instinct";
        }
        return "";
    }
    @Override
    public Rectangle getRange() {
        return new Rectangle(x-range,y-range,width+2*range,height+2*range);
    }

    @Override
    public int upgradePrice() {
        if(upgrade == 0){
            return 200;
        }
        else if(upgrade == 1){
            return 400;
        }
        else if(upgrade == 2){
            return 750;
        }
        else if(upgrade == 3){
            return 1900;
        }
        else if(upgrade == 4){
            return 12500;
        }
        return 0;
    }

    @Override
    public boolean findNewTar(ArrayList<Enemy> enemyList, int k) {
        int tarX = 0;
        int tarY = 0;
        boolean hasTar = false;
        for(int i = 1;i<enemyList.size();i++){
            if(enemyList.get(i).getBounds().intersects(getRange())){
                hasTar = true;
                tarX = enemyList.get(i).getX()+enemyList.get(i).getWidth()/2;
                tarY = enemyList.get(i).getY()+enemyList.get(i).getHeight()/2;
                i = enemyList.size();
            }
        }
        if(hasTar){
            System.out.println(projList.get(k).getTarX()+ " " +tarX);
            projList.get(k).setTarX(tarX);
            projList.get(k).setTarY(tarY);
            projList.get(k).move();
        }
        else{
            projList.remove(k);
        }
        return hasTar;
    }

    @Override
    public void clearProjList() {
        projList = new ArrayList<Projectile>();
    }
    
}
