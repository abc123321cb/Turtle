import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Fireball {
    private int angle, dimen;
    private double x, y,xvel, yvel;
    private static final BufferedImage img = Utility.getTextureAtlasBasic("res/fireball.png",10,10)[0];

    private static ArrayList<Fireball> fireballs = new ArrayList<Fireball>();
    Fireball(double x, double y, double speed, int angle, int dimen){
        this.angle = angle - 90;
        this.dimen = dimen;
        this.xvel = (Math.cos(Math.toRadians(this.angle)) * speed);
        this.yvel = (Math.sin(Math.toRadians(this.angle)) * speed);
        this.x = (x - 0.5 * dimen);
        this.y = (y - 0.5 * dimen);
        fireballs.add(this);
    }

    // This also moves the fireball so be aware of that.
    public static void draw(Graphics2D g, int x, int y){
        try {
            for (Fireball fireball : fireballs) {
                fireball.x += fireball.xvel*Main.TILE_SIZE;
                fireball.y += fireball.yvel*Main.TILE_SIZE;
                Utility.rotate(img, (double) fireball.angle);
                g.drawImage(Utility.rotate(img, (double) fireball.angle), (int)(fireball.x*Main.TILE_SIZE-x+Main.GAME_WIDTH/2),
                        (int)(fireball.y-y+Main.GAME_HEIGHT/2), fireball.dimen,
                        fireball.dimen, null);
                // it doesn't like this for some reason, but it seems to be working also it is a great way to check if it
                // offscreen
                if (Math.abs(fireball.x-x) + Math.abs(fireball.y-y) > 10000) {
                    fireballs.remove(fireball);
                }
            }
        }catch (Exception ignored){}
    }




}
