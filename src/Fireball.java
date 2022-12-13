import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Fireball {
    private int x,y,xvel,yvel, angle;
    private static final BufferedImage img = Utility.getTextureAtlasBasic("res/fireball.png",10,10)[0];
    private static int dimen = 25;



    private static ArrayList<Fireball> fireballs = new ArrayList<Fireball>();
    Fireball(int x, int y, int speed, int angle){
        this.angle = angle - 90;
        this.xvel = (int) (Math.cos(Math.toRadians(this.angle)) * speed);
        this.yvel = (int) (Math.sin(Math.toRadians(this.angle)) * speed);
        this.x = x + xvel * 2;
        this.y = y + yvel * 2;
        fireballs.add(this);
    }

    // This also moves the fireball so be aware of that.
    public static void draw(Graphics g){
        for (Fireball fireball: fireballs) {

            fireball.x += fireball.xvel;
            fireball.y += fireball.yvel;
            Utility.rotate(img, (double) fireball.angle);
            g.drawImage(Utility.rotate(img, (double) fireball.angle), fireball.x, fireball.y, dimen, dimen, null);
            if(Math.abs(fireball.x)+Math.abs(fireball.y)>10000){
                fireballs.remove(fireball);
            }
        }
    }




}
