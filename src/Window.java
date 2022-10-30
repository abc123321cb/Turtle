import javax.swing.*;
import java.awt.*;
public class Window extends JFrame {
    Window(){
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
