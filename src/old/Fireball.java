import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Fireball {
    private int x,y,xvel,yvel, angle, dimen;
    private static final BufferedImage img = Utility.getTextureAtlasBasic("res/fireball.png",10,10)[0];

    private static ArrayList<Fireball> fireballs = new ArrayList<Fireball>();
    Fireball(int x, int y, int speed, int angle, int dimen){
        this.angle = angle - 90;
        this.dimen = dimen;
        this.xvel = (int) (Math.cos(Math.toRadians(this.angle)) * speed);
        this.yvel = (int) (Math.sin(Math.toRadians(this.angle)) * speed);
        this.x = (int) (x - 0.5 * dimen);
        this.y = (int) (y - 0.5 * dimen);
        fireballs.add(this);
    }

    // This also moves the fireball so be aware of that.
    public static void draw(Graphics2D g){
        try {
            for (Fireball fireball : fireballs) {
                fireball.x += fireball.xvel;
                fireball.y += fireball.yvel;
                Utility.rotate(img, (double) fireball.angle);
                g.drawImage(Utility.rotate(img, (double) fireball.angle), fireball.x, fireball.y, fireball.dimen,
                        fireball.dimen, null);
                // it doesn't like this for some reason, but it seems to be working also it is a great way to check if it
                // offscreen
                if (Math.abs(fireball.x) + Math.abs(fireball.y) > 10000) {
                    fireballs.remove(fireball);
                }
            }
        }catch (Exception ignored){}
    }




}
