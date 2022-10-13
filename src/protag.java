import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.security.Key;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class protag {
    int health = 10;
    int magic = 10;
    int speed = 1;

    int x = 100;
    int y = 100;
    int xvel = 0;
    int yvel = 0;

    int ticks = 0;
    int ticksforupdate = 10;
    int current_frame = 0;
    int maxframe = 3;

    BufferedImage image;
    BufferedImage[] moveimg = new BufferedImage[4];
    int angle = 0;

    // clockwise starting with up
    ArrayList<Object> movement = new ArrayList<Object>(Arrays.asList(
        KeyEvent.VK_W, KeyEvent.VK_D, KeyEvent.VK_S, KeyEvent.VK_A));
    boolean moving = false;
    protag(){
        this.moveimg = setup.get2by2("res/turtle.png");
    }



    public void move(){
        x+=xvel;
        y+=yvel;
        if(this.moving){this.ticks ++;}
        if(this.ticks >= this.ticksforupdate){
            this.ticks = 0;
            current_frame ++;
            if (current_frame > maxframe){
                current_frame = 0;
            }

        }

    }

    public void draw(Graphics g){
        this.image = this.moveimg[current_frame];
        this.image = setup.rotate(image, (double)angle);
        g.drawImage(this.image, this.x, this.y, 50, 50, null);
    }

    public void keypressed(KeyEvent e){
        int key = e.getKeyCode();

        if (this.movement.contains(key)) {
            xvel = 0;
            yvel = 0;
            this.moving = true;

            if(key == (int)this.movement.get(0)){
                yvel = - this.speed;
                this.angle = 0;
            } else if (key == (int)this.movement.get(1)) {
                xvel = this.speed;
                this.angle = 90;
            } else if (key == (int)this.movement.get(2)) {
                yvel = this.speed;
                this.angle = 180;
            } else {
                xvel = - this.speed;
                this.angle = 270;
            }
        }

    }

    public void keyreleased(KeyEvent e){
        int key = e.getKeyCode();

        if (this.movement.contains(key)){
            this.moving = false;
            this.current_frame = 0;
            if(key == (int)this.movement.get(0) || key == (int)this.movement.get(2)){
                yvel = 0;
            } else {
                xvel = 0;
            }

        }
    }



}
