import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;
import javax.imageio.ImageIO;
import javax.swing.*;

public class setup extends JPanel implements Runnable {

    static final int GAME_WIDTH = 1000;
    static final int GAME_HEIGHT = GAME_WIDTH / 2;
    static final Dimension SCREEN_SIZE = new Dimension(GAME_WIDTH, GAME_HEIGHT);
    Thread gameThread;
    Image image;
    Graphics graphics;
    Random random;
    Map map;

    setup() {
        this.setFocusable(true);
        this.setPreferredSize(SCREEN_SIZE);
        this.addKeyListener(new AL());


        map = new Map(1);

        gameThread = new Thread(this);
        gameThread.start();
    }


    public void draw(Graphics g){
        map.draw(g);
    }

    public void paint(Graphics g) {
        image = createImage(getWidth(), getHeight());
        graphics = image.getGraphics();
        draw(graphics);
        g.drawImage(image, 0, 0, this);
    }


    @Override
    public void run(){
        long lastTime = System.nanoTime();
        double amountofticks = 60.0;
        double ns = 1000000000/amountofticks;
        double delta = 0;
        while(true){
            long now = System.nanoTime();
            delta+= (now-lastTime)/ns;
            lastTime = now;
            if (delta>=1){
                repaint();
                delta--;

            }
        }
    }

    public static BufferedImage[] get2by2(String file){
        try{
            BufferedImage[] imglist = new BufferedImage[4];
            BufferedImage image;
            image = ImageIO.read(setup.class.getResourceAsStream(file));
            int x = 0;
            int y = 0;
            for(int i = 0; i<4; i++){
                x = image.getWidth(null)/2;
                if (i%2==0){
                    x = 0;
                }
                if (i == 2){
                    y = image.getHeight(null)/2;
                }
                imglist[i] = image.getSubimage(x,y,image.getWidth(null)/2, image.getHeight(null)/2);
            }
            return imglist;

        }catch (IOException e){
            e.printStackTrace();
        }
        return new BufferedImage[0];
    }

    public static BufferedImage getRandom(BufferedImage[] array) {
        int rnd = new Random().nextInt(array.length);
        return array[rnd];
    }
    public class AL extends KeyAdapter {
        public void keyPressed(KeyEvent e) {

            map.keypressed(e);
        }
    }
}