package shared;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.*;

public class protag {
    
    //movement variables
    public double speed = 0.5;
    public double x = 0;
    public double y = 0;
    public double xvel = 0;
    double yvel = 0;
    int dimen = 50;


    //for the health/mana counter at top right:
    final static int boxwidth = 10;
    Color healthColor = new Color(0,255,0);
    Color magicColor = new Color(189, 19, 236);

    double healthPercentage = 1;
    double magicPercentage = 1;

    int healthWidth = (int)(healthPercentage * Options.GAME_WIDTH/4);
    int magicWidth = (int)(healthPercentage * Options.GAME_WIDTH/4);

    int maxHealth = 10;
    int health = 10;

    int maxMagic = 300;
    int magic = 300;
    int magicRechargeWait = 10;

    // spells and their magic cost
    Map<String, Integer> spellCost = Map.of(
            "Fireball",100
    );

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
    public protag(){
        this.moveimg = StaticOptions.TextureAtlas;
    }


    // returns new playerx, playery map coords
    public int[] move(int chunkx, int chunky){
        x+=(xvel*Options.TILE_SIZE)/100;
        y+=(yvel*Options.TILE_SIZE)/100;
        if(this.moving){
            this.ticks ++;

            // moves player to next screen if out of screen.
            if(this.x > StaticOptions.CHUNKSIZE*Options.TILE_SIZE){
                this.x = 0;
                chunkx++;
            } else if (this.x < 0) {
                this.x = StaticOptions.CHUNKSIZE*Options.TILE_SIZE;
                chunkx--;
            } else if (this.y > StaticOptions.CHUNKSIZE *  Options.TILE_SIZE) {
                this.y = 0;
                chunky++;
            } else if (this.y < 0) {
                this.y = StaticOptions.CHUNKSIZE * Options.TILE_SIZE;
                chunky--;
            }
        }
        if(this.ticks >= this.ticksforupdate) {
            this.ticks = 0;
            current_frame++;
            if (current_frame > maxframe) current_frame = 0;
        }
        return new int[] {chunkx,chunky};
    }

    public void draw(Graphics2D g, int x, int y){
        this.image = this.moveimg[current_frame];
        this.image = Utility.rotate(image, (double)angle);
        g.drawImage(this.image, (int)(this.x*Options.TILE_SIZE-x), (int)(this.y*Options.TILE_SIZE-y), Options.TILE_SIZE, Options.TILE_SIZE, null);

        // making health / mana bar at the top right
        // I also tried to move things out of the draw function to speed stuff up and clean this up, so you have to
        // use the changeHealth and changeMagic functions or the bars will not update.


        g.setColor(healthColor);
        g.fillRect(Options.GAME_WIDTH-healthWidth,0,healthWidth,25);

        //mana recovery

        if(magic < maxMagic && magicRechargeWait-- <= 0){
            updateMagic(magic+1);
            magicRechargeWait = 10;
        }


        g.setColor(magicColor);
        g.fillRect(Options.GAME_WIDTH-magicWidth, 25, magicWidth, 25);


        g.setColor(new Color(0,0,0));
        g.setStroke(new BasicStroke(boxwidth));
        g.drawRect(Options.GAME_WIDTH-Options.GAME_WIDTH/4,boxwidth/2,Options.GAME_WIDTH/4-boxwidth,50);
        g.drawRect(Options.GAME_WIDTH-Options.GAME_WIDTH/4,boxwidth/2,Options.GAME_WIDTH/4-boxwidth,25);



    }

    // Use this to change health if you need to decriment health just do updateHealth(health-1) don't do health --;
    public void updateHealth(int newHealth){
        health = newHealth;
        healthPercentage = (double)health/maxHealth;
        healthColor = new Color((int) (255-healthPercentage*255), (int) (healthPercentage*255),0);
        healthWidth = (int)(healthPercentage * Options.GAME_WIDTH/4);

    }
    // Same as above but for Magic
    public void updateMagic(int newMagic){
        magic = newMagic;
        magicPercentage = (double)magic/maxMagic;
        magicWidth = (int)(magicPercentage * Options.GAME_WIDTH/4);
    }


    public void keypressed(KeyEvent e){

        // WASD and arrows move,
        // K shoots a fireball

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
            /* 
            if (key == this.controls.get(8)){
                if(magic > spellCost.get("Fireball")) {
                    System.out.println(this.x + "  " + this.y);
                    Fireball f = new Fireball(this.x, this.y, this.speed * 3,
                            this.angle, this.dimen / 2);
                    updateMagic(magic - spellCost.get("Fireball"));
                }
            }
            */
        }
    }

    public void keyreleased(KeyEvent e){
        int key = e.getKeyCode();

        if (this.movement.contains(key)){
            this.moving = false;
            this.current_frame = 0;
            if(key == (int)this.controls.get(0) || key == (int)this.controls.get(2) ||
                    key == this.controls.get(4) || key == this.controls.get(6)){
                yvel = 0;
            } else {
                xvel = 0;
            }
        }
    }
}