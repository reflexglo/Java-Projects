import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class EnemyHolder {
    public Enemy pilaf(){
        File file = new File("src//pilaf.png");
        BufferedImage img = null;
        try {
            img = ImageIO.read(file);
        } catch (IOException ex) {
            Logger.getLogger(TowerHolder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new Enemy(0,170,80,80,1,1,3,20,img,Color.black);
    }
    public Enemy commander(){
        File file = new File("src//commander.png");
        BufferedImage img = null;
        try {
            img = ImageIO.read(file);
        } catch (IOException ex) {
            Logger.getLogger(TowerHolder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new Enemy(0,170,80,80,4,1,3,40,img,Color.black);
    }
    public Enemy takeo(){
        File file = new File("src//takeo.png");
        BufferedImage img = null;
        try {
            img = ImageIO.read(file);
        } catch (IOException ex) {
            Logger.getLogger(TowerHolder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new Enemy(0,170,80,80,6,3,3,60,img,Color.black);
    }
    public Enemy tien(){
        File file = new File("src//tien.jpg");
        BufferedImage img = null;
        try {
            img = ImageIO.read(file);
        } catch (IOException ex) {
            Logger.getLogger(TowerHolder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new Enemy(0,170,80,80,10,2,3,120,img,Color.black);
    }
}
