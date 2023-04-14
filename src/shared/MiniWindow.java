/* 
package shared;


import client.Game;



import javax.swing.*;
import java.awt.*;

public class MiniWindow extends JPanel {
    private int x;
    private int y;
    private Dimension SCREEN_SIZE;
    JInternalFrame frame;
    Image image;
    Graphics graphics;

    public MiniWindow(int x, int y, int width, int height, String title) {
        this.setVisible(true);
        this.x = x;
        this.y = y;
        this.SCREEN_SIZE = new Dimension(width, height);
        frame = new JInternalFrame(title,true,true);
        frame.add(this);
        frame.setVisible(true);
        this.setFocusable(true);
        this.setBounds(0, 0, width, height);
        frame.pack();
        Game.window.add(frame);
        add(new JLabel("Hello"));

    }


    public void paint(Graphics g){
        System.out.println("ahh");
        image = createImage(getWidth(), getHeight());
        graphics = image.getGraphics();
        draw(graphics);
        g.drawImage(image, 0, 0, this);
    }

    public void draw(Graphics g){
        System.out.println("ah");
        g.drawRect(10, 10, 100, 100);
        g.drawString("hello", 10, 10);
    }



}
*/