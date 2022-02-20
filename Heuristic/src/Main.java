import javax.swing.JFrame;     //class for main canvas(window)
import java.awt.*;
import javax.swing.WindowConstants;

public class Main {
    
    public static void initializeJFrame(JFrame jf){
        
        jf.getContentPane().setBackground(Color.green);    
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        jf.setSize(2650, 1440);                 
        jf.setLocationRelativeTo(null);                    
        jf.setTitle("Terrain");                  
        jf.setVisible(true);                              
    }
    
    public static void main(String[] args) {
        JFrame window = new JFrame(); 
        Client cl = new Client();
        window.add(cl);
        initializeJFrame(window);          
      
    }
    
}
