import javax.swing.*;
import java.awt.*;
public class Main extends JFrame{
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
            //panel setup
            setup panel = new setup();
            add(panel);
            setTitle("game");
            setResizable(false);
            setBackground(Color.BLUE);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            pack();
            setVisible(true);
            setLocationRelativeTo(null);
        }
    }
}