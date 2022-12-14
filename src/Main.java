import javax.swing.*;
import java.awt.*;
public class Main {
    static final int GAME_WIDTH = 1000;
    static final int GAME_HEIGHT = 500;
    static final Dimension SCREEN_SIZE = new Dimension(GAME_WIDTH, GAME_HEIGHT);
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
            JPanel contentPane = new JPanel();
                        
            setup game = new setup();
            contentPane.add(game);
            menu menu = new menu();
            contentPane.add(menu);

            this.setPreferredSize(SCREEN_SIZE);
            setTitle("turtle game");
            setResizable(false);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            pack();
            setVisible(true);
            setLocationRelativeTo(null);
            setContentPane(contentPane);
        }
    }
}