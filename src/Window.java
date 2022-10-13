import javax.swing.*;
import java.awt.*;
public class Window extends JFrame {
    Window(){
        setup panel = new setup();
        this.add(panel);
        this.setTitle("game");
        this.setResizable(false);
        this.setBackground(Color.BLUE);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
}
