import java.awt.Color;
import javax.swing.JFrame;
import java.io.File;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
public class Main {

    public static void initializeJFrame(JFrame jf){
        
        jf.getContentPane().setBackground(Color.WHITE);    //set color of main window
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //makes the exit icon work correctly
        jf.setSize(1800, 1000);                 //sets the size of the window
   
        jf.setLocationRelativeTo(null);                    //centers the window
        jf.setTitle("Platformer");                  // sets the title bar
        jf.setVisible(true);                              // makes the window visible
    }
    
    public static void main(String[] args) {
           JFrame window = new JFrame();            //makes a jframe
        
        Client mb = new Client();
        window.add(mb);
        initializeJFrame(window);          //initializes the window to your settings

        }
    
}