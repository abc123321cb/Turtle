import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;

public class protag {
    int health = 10;
    int magic = 10;
    int speed = 5;

    int x = 100;
    int y = 100;
    int xvel = 0;
    int yvel = 0;
    int dimen = 50;


    int ticks = 0;
    int ticksforupdate = 10;
    int current_frame = 0;
    int maxframe = 3;

    BufferedImage image;
    BufferedImage[] moveimg = new BufferedImage[4];
    int angle = 0;

    // clockwise starting with up
    ArrayList<Integer> controls = new ArrayList<>(Arrays.asList(
            KeyEvent.VK_W, KeyEvent.VK_D, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_UP, KeyEvent.VK_RIGHT,
            KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_K));
    ArrayList<Integer> movement = new ArrayList<>(Arrays.asList(
            KeyEvent.VK_W, KeyEvent.VK_D, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_UP, KeyEvent.VK_RIGHT,
            KeyEvent.VK_DOWN, KeyEvent.VK_LEFT));

    boolean moving = false;
    protag(){
        this.moveimg = Utility.getTextureAtlasBasic("res/tiles/TextureAtlasv20v20v.png",20,20);
    }


    // returns new playerx, playery map coords
    public int[] move(int[] coord){
        this.x+=this.xvel;
        this.y+=this.yvel;
        if(this.moving){
            this.ticks ++;

            // moves player to next screen if out of screen.
            if(this.x+this.dimen > Main.GAME_WIDTH){
                this.x = 0;
                coord[0]++;
            } else if (this.x < 0) {
                this.x = Main.GAME_WIDTH - this.dimen;
                coord[0]--;
            } else if (this.y + this.dimen > Main.GAME_HEIGHT) {
                this.y = 0;
                coord[1]++;
            } else if(this.y<0){
                this.y = Main.GAME_HEIGHT - this.dimen;
                coord[1]--;
            }
        }
        if(this.ticks >= this.ticksforupdate){
            this.ticks = 0;
            current_frame ++;
            if (current_frame > maxframe) current_frame = 0;
        }

        // moves player to next screen if out of screen.
        if(this.x+this.dimen > Main.GAME_WIDTH){
            this.x = 0;

        }
        return coord;
    }

    public void draw(Graphics g){
        this.image = this.moveimg[current_frame];
        this.image = Utility.rotate(image, (double)angle);
        g.drawImage(this.image, this.x, this.y, this.dimen, this.dimen, null);
    }

    public void keypressed(KeyEvent e){
        int key = e.getKeyCode();

        if (this.controls.contains(key)) {
            if(this.movement.contains(key)) {
                xvel = 0;
                yvel = 0;
                this.moving = true;
                if (key == (int) this.controls.get(0) || key == this.controls.get(0 + 4)) {
                    yvel = -this.speed;
                    this.angle = 0;
                } else if (key == (int) this.controls.get(1) || key == this.controls.get(1 + 4)) {
                    xvel = this.speed;
                    this.angle = 90;
                } else if (key == (int) this.controls.get(2) || key == this.controls.get(2 + 4)) {
                    yvel = this.speed;
                    this.angle = 180;

                } else if (key == this.controls.get(3) || key == this.controls.get(3 + 4)) {
                    xvel = -this.speed;
                    this.angle = 270;
                }
            }
            if (key == this.controls.get(8)){
                Fireball f = new Fireball(this.x+this.dimen/4, this.y+this.dimen/4, this.speed * 3, this.angle);
            }
        }
    }

    public void keyreleased(KeyEvent e){
        int key = e.getKeyCode();

        if (this.controls.contains(key)){
            this.moving = false;
            this.current_frame = 0;
            if(key == (int)this.controls.get(0) || key == (int)this.controls.get(2)){
                yvel = 0;
            } else {
                xvel = 0;
            }
        }
    }
}