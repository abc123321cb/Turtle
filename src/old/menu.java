import javax.swing.*;
import java.awt.image.BufferedImage;

public class menu extends JPanel{
    static final BufferedImage[] MenuButtons = Utility.getTextureAtlasPrototype("res/ButtonAtlas.png",256,48,768,48);
    menu(){
        JLabel button = new JLabel(new ImageIcon(MenuButtons[1]));
        button.setBounds(100,100,256,48);  
        Main.game.add(button,1);
    }
}


