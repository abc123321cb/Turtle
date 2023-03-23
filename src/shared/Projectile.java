package shared;

import client.Game;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Projectile {
    private double x = 0;
    private double y = 0;
    private double xvel;
    private double yvel;
    private int chunkx;
    private int chunky;
    private boolean friendly;
    private int lifespan;
    private int imgindex;
    // can add more projectiles when needed
    private static BufferedImage[] projectileimgs = Utility.getTextureAtlasBasic("resources/fireball.png", 10, 10);

    private int damage = 0;

    public Projectile(double x, double y, double xvel, double yvel, int chunkx, int chunky, boolean friendly, int lifespan, int damage, int imgindex){
        this.x = x;
        this.y = y;
        this.xvel = xvel;
        this.yvel = yvel;
        this.chunkx = chunkx;
        this.chunky = chunky;
        this.friendly = friendly;
        this.lifespan = lifespan;
        this.damage = damage;
        this.imgindex = imgindex;
        Game.projectiles.add(this);
    }

    public boolean move(){
        x+=xvel;
        y+=yvel;
        lifespan--;
        if(lifespan<1){
            return true;
        }
        return false;
    }

    public void draw(Graphics g, int xoffset, int yoffset){
        int x = Utility.getPixels(this.x, chunkx, Game.chunks[4].chunkx);
        int y = Utility.getPixels(this.y, chunky, Game.chunks[4].chunky);
        g.drawImage(projectileimgs[imgindex], x-xoffset,y-yoffset,10,10,null);
    }
}
