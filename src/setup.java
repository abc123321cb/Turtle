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
    static final int GAME_HEIGHT = 500;
    static final Dimension SCREEN_SIZE = new Dimension(GAME_WIDTH, GAME_HEIGHT);
    Thread gameThread;
    Image image;
    Graphics graphics;
    Random random;
    protag player;
    Mapping map;

    setup(){
        this.setFocusable(true);
        this.addKeyListener(new ActionListner());
        this.setPreferredSize(SCREEN_SIZE);

        player = new protag();
        map = new Mapping(1);

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

    //main game loop
    //put new commands in the if statement just trust me
    public void run(){
        long lastTime = System.nanoTime();
        double amountofticks = 60.0;
        double ns = 1000000000/amountofticks;
        double delta = 0;
        while(true){
            long  now = System.nanoTime();
            delta+= (now-lastTime)/ns;
            lastTime = now;
            if (delta>=1){
                repaint();
                move();
                delta--;

            }
        }
    }

    //Takes Image and angle returns image roated by angle
    public static BufferedImage rotate(BufferedImage image, Double angle) {
        double angleYComponent = Math.abs(Math.sin(Math.toRadians(angle))),
               angleXComponent = Math.abs(Math.cos(Math.toRadians(angle)));
        int width  = image.getWidth(),
            height = image.getHeight();
        int newwidth = (int) (width*angleYComponent + height*angleXComponent),
            newheight = (int) (height*angleYComponent + width*angleXComponent);
        BufferedImage rotated = new BufferedImage(newwidth, newheight, image.getType());
        Graphics2D graphic = rotated.createGraphics();
        graphic.translate((newwidth-width)/2, (newwidth-newheight)/2);
        graphic.rotate(Math.toRadians(angle), width/2, newheight/2);
        graphic.drawRenderedImage(image, null);
        graphic.dispose();
        return rotated;
    }

    //takes sprite atlas and breaks it up into sections depending on the name of the file
    public static BufferedImage[] getTextureAtlas(String file){
        try{
            int textureWidth =Integer.parseInt((file.split("v")[1]));
            int textureHight =Integer.parseInt((file.split("v")[2]));
            BufferedImage image;
            image = ImageIO.read(setup.class.getResourceAsStream(file));
            int atlasWidth = image.getHeight(null);
            int atlasHeight = image.getHeight(null);
            int texturesPerRow    = atlasWidth / textureWidth;
            int texturesPerColumn = atlasHeight / textureHight;
            BufferedImage[] imglist = new BufferedImage[texturesPerRow*texturesPerColumn];
            System.out.println(texturesPerRow);
            for(int y = 0; y<texturesPerColumn; y++){
                for(int x = 0; x<texturesPerRow; x++){
                    imglist[x+y*texturesPerRow] = image.getSubimage(x*textureWidth,y*textureHight,textureWidth,textureHight);
                }
            }
            return imglist;
        }catch (IOException e){
            e.printStackTrace();
        }
        return new BufferedImage[0];
    }

    //Takes Image Array and returns a random image from said array
    public static BufferedImage getRandom(BufferedImage[] array,int min, int max) {
        int rnd = new Random().nextInt(max+1-min)+min;
        return array[rnd];
    }

    public class ActionListner extends KeyAdapter {
        public void keyPressed(KeyEvent e){
            map.keypressed(e);
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
