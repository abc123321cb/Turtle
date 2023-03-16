package shared;

import client.Game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Enemy implements entity {

    private double x;
    private double y;
    private double xvel;
    private double yvel;
    private int dimen;
    private int chunkx;
    private int chunky;
    private double[] distance;



    // 0th index is for ghost crabs

    private static final BufferedImage[] images = new BufferedImage[]{
                Utility.getTextureAtlasBasic("resources/GhostCrab.png", 20, 20)[0]

        };

    private int imgIndex;

    private int health;
    private int maxHealth;


    Enemy(double x, double y, int dimen, int chunkx, int chunky, int maxHealth){
        this.x = x;
        this.y = y;
        this.dimen = dimen;
        this.chunkx = chunkx;
        this.chunky = chunky;
        this.maxHealth = maxHealth;
        this.health = maxHealth;
    }



    private double[] getDirectiontoPlayer(){
        // returns a array containing the distance to the nearest player and the direction that player is in degrees
        double dis = Integer.MAX_VALUE;
        double angle = 0;
        for (protag p: Game.playerList) {

            double newdis = Math.pow(Math.pow((p.localx-(chunkx-p.chunkx)*StaticOptions.CHUNKSIZE)-x,2)
                    + Math.pow((p.localy - (chunky-p.chunky)*StaticOptions.CHUNKSIZE) - y, 2),0.5);
            if(newdis<dis){
                dis = newdis;
                angle = Math.signum((p.localy - (chunky-p.chunky)*StaticOptions.CHUNKSIZE) - y)
                        *Math.acos(((p.localx - (chunkx-p.chunkx)*StaticOptions.CHUNKSIZE)-x)/dis);
            }
        }
        return new double[]{dis,angle};
    }


    // everything must have a move method because it is needed to reduce visual bugs
    @Override
    public void move() {
        x +=xvel;
        y +=yvel;
        distance = getDirectiontoPlayer();
    }

    @Override
    public void draw(Graphics g, int xoffset, int yoffset) {
        int x = (int)(this.x+(chunkx-Game.chunks[4].chunkx)*StaticOptions.CHUNKSIZE)*Options.TILE_SIZE;
        int y = (int)(this.y+(chunky-Game.chunks[4].chunky)*StaticOptions.CHUNKSIZE)*Options.TILE_SIZE;
        if(x-xoffset>0 && y-yoffset>0 && x-xoffset < Options.GAME_WIDTH && y-yoffset <Options.GAME_HEIGHT)
            g.drawImage(Utility.rotate(images[imgIndex],distance[1]), x-xoffset, y-yoffset, this.dimen, this.dimen, null);
    }

    @Override
    public double getX() {
        return x;
    }

    @Override
    public double getY() {
        return y;
    }

    @Override
    public int getChunkx() {
        return chunkx;
    }

    @Override
    public int getChunky() {
        return chunky;
    }

    @Override
    public double getXvel() {
        return xvel;
    }

    @Override
    public double getYvel() {
        return yvel;
    }

    @Override
    public int getdimen() {
        return dimen;
    }

    public double[] getDistance(){
        return distance;
    }

    public void setXvel(double xvel){
        this.xvel = xvel;
    }

    public void setYvel(double yvel){
        this.yvel = yvel;
    }

    public int getHealth(){
        return health;
    }

    public int getMaxHealth(){
        return maxHealth;
    }

    public void setImgIndex(int imgIndex){
        this.imgIndex = imgIndex;
    }
}
