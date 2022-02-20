import java.awt.Color;
import java.awt.Graphics;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Map {
    ArrayList<Track> tracks;
    String mapName;
    Image img;
    public Map(ArrayList<Track> tracks, String mapName, BufferedImage img){
        this.tracks = tracks;
        this.mapName = mapName;
        this.img = img;
    }
    public Track getTrack(int k){
        return tracks.get(k);
    }
    public String getName(){
        return mapName;
    }
     public void render(Graphics g){
         g.drawImage(img, 0, 0, 2000, 1440, null);
         for(int i = 0;i<tracks.size();i++){
             tracks.get(i).render(g);
         }
     }
     public int size(){
         return tracks.size();
     }
}
