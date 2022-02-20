
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class MapHolder {
    public Map SnakeWay(){
        BufferedImage img = null;
        File file = new File("src//snakeway.jpg");
        try {
            img = ImageIO.read(file);
        } catch (IOException ex) {
            Logger.getLogger(TowerHolder.class.getName()).log(Level.SEVERE, null, ex);
        }
        ArrayList<Track> map = new ArrayList<Track>();
        map.add(new Track(0,150,1900,150,3,Color.LIGHT_GRAY));
        map.add(new Track(1800,150,150,400,2,Color.LIGHT_GRAY));
        map.add(new Track(0,450,1950,150,1,Color.LIGHT_GRAY));
        map.add(new Track(0,450,150,400,2,Color.LIGHT_GRAY));
        map.add(new Track(0,750,1900,150,3,Color.LIGHT_GRAY));
        map.add(new Track(1800,750,150,400,2,Color.LIGHT_GRAY));
        map.add(new Track(0,1050,1950,150,1,Color.LIGHT_GRAY));
        map.add(new Track(-200,1050,200,150,4,Color.LIGHT_GRAY));
        Map newMap = new Map(map,"Snake Way",img);
        return newMap;
    }
}
