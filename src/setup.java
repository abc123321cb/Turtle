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
    protag player;
    Map map;

    setup(){
        this.setFocusable(true);
        this.addKeyListener(new AL());
        this.setPreferredSize(SCREEN_SIZE);

        player = new protag();
        map = new Map(1);

        gameThread = new Thread(this);
        gameThread.start();



    }


    public void draw(Graphics g){
        map.draw(g);
        player.draw(g);
    }

    public void paint(Graphics g) {
        image = createImage(getWidth(), getHeight());
        graphics = image.getGraphics();
        draw(graphics);
        g.drawImage(image, 0, 0, this);
    }

    public void move(){
        player.move();
    }

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
                move();
                delta--;

            }
        }
    }

    public static BufferedImage rotate(BufferedImage bimg, Double angle) {
        double sin = Math.abs(Math.sin(Math.toRadians(angle))),
                cos = Math.abs(Math.cos(Math.toRadians(angle)));
        int w = bimg.getWidth();
        int h = bimg.getHeight();
        int neww = (int) Math.floor(w*cos + h*sin),
                newh = (int) Math.floor(h*cos + w*sin);
        BufferedImage rotated = new BufferedImage(neww, newh, bimg.getType());
        Graphics2D graphic = rotated.createGraphics();
        graphic.translate((neww-w)/2, (newh-h)/2);
        graphic.rotate(Math.toRadians(angle), w/2, h/2);
        graphic.drawRenderedImage(bimg, null);
        graphic.dispose();
        return rotated;
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
        public void keyPressed(KeyEvent e){

            player.keypressed(e);
        }
        public void keyReleased(KeyEvent e){
            player.keyreleased(e);
        }
        public void move(){

        }
        public void draw(Graphics g){

        }
    }
}
