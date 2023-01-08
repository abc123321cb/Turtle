import javax.swing.*;
import java.awt.*;
public class OldMain {

    static final int CELL_WIDTH = 50;
    static final int GAME_WIDTH = 1000;
    static final int GAME_HEIGHT = 500;

    //NEVER CHANGE THIS EVER
    static final int chunksize = 50;
    
    static final Dimension SCREEN_SIZE = new Dimension(GAME_WIDTH, GAME_HEIGHT);
    static final setup game = new setup();
    

    
    public static void main(String[] args){
        //get options and such
        Options.refreshOptions();
        new OldMain();
    }
    
    public OldMain() {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Window();
            }
        });
    }
    public class Window extends JFrame {
        Window(){
            //setup frame
            setTitle("turtle game");
            setResizable(false);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            //a bunch of bs to set up the game
            JPanel contentPane = new JPanel();
            contentPane.setLayout(null);

            //set up width and height of the game
            this.setPreferredSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));
            game.setBounds(0,0,GAME_WIDTH,GAME_HEIGHT);
            contentPane.add(game);
            
            //stuff...?
            pack();
            setVisible(true);
            setLocationRelativeTo(null);
            setContentPane(game);
        }
    }
}