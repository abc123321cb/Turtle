import javax.swing.*;
import java.awt.*;
public class Main {

    static final int CELL_WIDTH = 50;
    static final int GAME_WIDTH = 1000;
    static final int GAME_HEIGHT = 500;
    
    static final Dimension SCREEN_SIZE = new Dimension(GAME_WIDTH, GAME_HEIGHT);
    
    static final int BLOCKS_WIDTH = Main.GAME_WIDTH/CELL_WIDTH;
    static final int BLOCKS_HEIGHT = Main.GAME_HEIGHT/CELL_WIDTH;

    public static void main(String[] args){
        new Main();
    }
    
    public Main() {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Window();
            }
        });
    }

    public class Window extends JFrame {
        Window(){
            //a bunch of bs to set up the game
            JPanel contentPane = new JPanel();
            contentPane.setLayout(null);
            setup game = new setup();
            game.setBounds(0,0,GAME_WIDTH,GAME_HEIGHT);
            contentPane.add(game);
            this.setPreferredSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));
            setTitle("turtle game");
            setResizable(false);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            pack();
            setVisible(true);
            setLocationRelativeTo(null);
            setContentPane(game);
        }
    }
}