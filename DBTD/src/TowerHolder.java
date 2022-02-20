import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class TowerHolder {
    public TowerMenu gokuMenu(){
        File file = new File("src//gokubase.jpg");
        BufferedImage img = null;
        try {
            img = ImageIO.read(file);
        } catch (IOException ex) {
            Logger.getLogger(TowerHolder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new TowerMenu(2060,100,160,160,400,"Goku",img,Color.black);
    }
}
