import javax.swing.*;
import java.awt.image.BufferedImage;

public class menu extends JPanel{
    static final BufferedImage[] MenuButtons = Utility.getTextureAtlasPrototype("res/ButtonAtlas.png",256,48,768,48);
    menu(){
    JButton button = new JButton(new ImageIcon(MenuButtons[1]));
    button.setBorderPainted(false);
    button.setFocusPainted(false);
    button.setContentAreaFilled(false);
    button.setBounds(100,100,256,48);  
    this.add(button);
    }
}


