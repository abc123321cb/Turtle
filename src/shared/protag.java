package shared;
import client.Game;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.*;

public class protag {
    
    //movement variables
    public double speed = 0.25;
    public double localx = 0;
    public double localy = 0;
    public int chunkx = 0;
    public int chunky = 0;
    public double xvel = 0;
    double yvel = 0;
    int dimen = 50;


    MiniWindow m = new MiniWindow(0,0,100,100,"aahhh");



    private boolean casting = false;
    private ArrayList<Integer> currentSpell = new ArrayList<>();


    //for the health/mana counter at top right:
    final static int boxwidth = 10;
    Color healthColor = new Color(0,255,0);
    Color magicColor = new Color(189, 19, 236);

    double healthPercentage = 1;
    double magicPercentage = 1;

    int healthWidth = (int)(healthPercentage * Options.GAME_WIDTH/4);
    int magicWidth = (int)(healthPercentage * Options.GAME_WIDTH/4);

    int maxHealth = 100;
    int health = 100;

    int maxMagic = 300;
    int magic = 300;
    int magicRechargeWait = 10;


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

    final ArrayList<ArrayList<Integer>> spells = new ArrayList<>(Arrays.asList(
            new ArrayList<Integer>(Arrays.asList(KeyEvent.VK_K)), // fireball
            new ArrayList<Integer>(Arrays.asList(KeyEvent.VK_K, KeyEvent.VK_K, KeyEvent.VK_K)), // firering
            new ArrayList<Integer>(Arrays.asList(KeyEvent.VK_T, KeyEvent.VK_U, KeyEvent.VK_R, KeyEvent.VK_T, KeyEvent.VK_L, KeyEvent.VK_E))
    ));

    final ArrayList<Runnable> cast = new ArrayList<Runnable>(Arrays.asList(
            this::fireball,
            this::firering,
            this::turtle
    ));

    final int[] magicCost = new int[]{
            33, // fireball
            100, // firering
            200,
    };

    boolean moving = false;
    public protag(){

        this.moveimg = FinalOptions.TextureAtlas;
    }


    // returns new playerx, playery map coords
    public void move(){
        localx+=(xvel*Options.TILE_SIZE)/100;
        localy+=(yvel*Options.TILE_SIZE)/100;
        if(Game.chunks[4].collide(localx,localy,dimen,dimen)) {
            localx -= 2 * (xvel * Options.TILE_SIZE) / 100;
            localy -= 2 * (yvel*Options.TILE_SIZE)/100;
        }
        if(this.moving){
            this.ticks ++;

            // moves player to next screen if out of screen.
            if(localx > FinalOptions.CHUNKSIZE){
                localx = 0;
                chunkx++;
            } else if (this.localx < 0) {
                localx = FinalOptions.CHUNKSIZE;
                chunkx--;
            } else if (this.localy > FinalOptions.CHUNKSIZE) {
                localy = 0;
                chunky++;
            } else if (this.localy < 0) {
                localy = FinalOptions.CHUNKSIZE;
                chunky--;
            }
        }
        if(this.ticks >= this.ticksforupdate) {
            this.ticks = 0;
            current_frame++;
            if (current_frame > maxframe) current_frame = 0;
        }
    }

    public void draw(Graphics2D g, int x, int y){

        m.repaint();


        this.image = this.moveimg[current_frame];
        this.image = Utility.rotate(image, Math.toRadians(angle));
        g.drawImage(this.image, (int)(localx*Options.TILE_SIZE-x), (int)(this.localy*Options.TILE_SIZE-y), this.dimen, this.dimen, null);
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
        if(newHealth<0)
            System.exit(0);
        health = newHealth;
        healthPercentage = (double)health/maxHealth;
        System.out.println(health);
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
        } else if (KeyEvent.VK_0==key) {
            casting = !casting;
            if(!casting){
                boolean real = false;
                for(int i = 0; i<spells.size(); i++){
                    if(spells.get(i).equals(currentSpell)){
                        real = true;

                        cast.get(i).run();
                        break;
                    }
                }
                if(!real){
                    updateHealth(health-25);
                }
                currentSpell.clear();
            }
        } else if (casting) {
            currentSpell.add(key);
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

    // Spells

    public void fireball(){
        int fireballSlowdown = 4;
        if(magic>=magicCost[0]) {
            Game.projectiles.add(new Projectile(localx, localy, Math.cos(Math.toRadians(angle-90))/fireballSlowdown, Math.sin(Math.toRadians(angle-90))/fireballSlowdown, chunkx, chunky, true, 100, 20, 0, Math.toRadians(angle-90)));
            updateMagic(magic-magicCost[0]);
        }
    }

    public void firering(){
        if(magic>=magicCost[1]){
            magic-=magicCost[1];
            for(int i = 0; i < 360; i+=4)
                Game.projectiles.add(new Projectile(localx, localy, Math.cos(Math.toRadians(angle-90+i))/4,Math.sin(Math.toRadians(angle-90+i))/4,chunkx,chunky,true,100,20,0,Math.toRadians(angle-90+i)));
        }
    }

    public void turtle(){
        if(magic>=magicCost[2]) {
            magic -= magicCost[2];
            updateHealth(maxHealth);
            speed += 1;
        }
    }

}