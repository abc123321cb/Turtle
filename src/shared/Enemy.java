package shared;

import client.Game;


import java.awt.*;
import java.awt.image.BufferedImage;

public class Enemy implements entity, Loopers {

    private double x;
    private double y;
    private double xvel;
    private double yvel;
    private int width,height;
    private int chunkx;
    private int chunky;
    private boolean alive;
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
        this.width = dimen;
        this.height = dimen;
        this.chunkx = chunkx;
        this.chunky = chunky;
        this.maxHealth = maxHealth;
        this.health = maxHealth;
        this.alive = true;
        this.distance = getDirectiontoPlayer();
    }



    private double[] getDirectiontoPlayer(){
        // returns a array containing the distance to the nearest player and the direction that player is in degrees
        double dis = Integer.MAX_VALUE;
        double angle = 0;
        for (protag p: Game.playerList) {

            double newdis = Math.pow(Math.pow((p.localx-(chunkx-p.chunkx)*FinalOptions.CHUNKSIZE)-x,2)
                    + Math.pow((p.localy - (chunky-p.chunky)*FinalOptions.CHUNKSIZE) - y, 2),0.5);
            if(newdis<dis){
                dis = newdis;
                angle = Math.signum((p.localy - (chunky-p.chunky)*FinalOptions.CHUNKSIZE) - y)
                        *Math.acos(((p.localx - (chunkx-p.chunkx)*FinalOptions.CHUNKSIZE)-x)/dis);
            }
        }
        return new double[]{dis,angle};
    }


    // everything must have a move method because it is needed to reduce visual bugs
    @Override
    public boolean move() {
        x +=xvel;
        y +=yvel;
        distance = getDirectiontoPlayer();
        return !alive;
    }

    @Override
    public void draw(Graphics g, int xoffset, int yoffset) {
        int x = (int)((this.x+(chunkx-Game.chunks[4].chunkx)*FinalOptions.CHUNKSIZE)*Options.TILE_SIZE);
        int y = (int)((this.y+(chunky-Game.chunks[4].chunky)*FinalOptions.CHUNKSIZE)*Options.TILE_SIZE);
        if(x-xoffset>0 && y-yoffset>0 && x-xoffset < Options.GAME_WIDTH && y-yoffset <Options.GAME_HEIGHT)
            g.drawImage(Utility.rotate(images[imgIndex],distance[1]), x-xoffset, y-yoffset, this.width, this.height, null);
    }

    public void damage(int damage){
        health-=damage;
        if(health<=0){
            alive = false;
        }
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

   public int getWidth(){return width;}

    public int getHeight(){return height;}

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
