package shared;

import client.Game;

import java.awt.*;
import java.awt.image.BufferedImage;

public class NPC implements entity{
    private double x;
    private double y;
    private double xvel;
    private double yvel;
    private int chunkx;
    private int chunky;
    private int width;
    private int height;
    private int imgIndex;

    private static BufferedImage[] images = new BufferedImage[]{

    };

    NPC(double x, double y, double xvel, double yvel, int chunkx, int chunky, int width, int height, int imgIndex){
        this.x = x;
        this.y = y;
        this.xvel = xvel;
        this.yvel = yvel;
        this.chunkx = chunkx;
        this.chunky = chunky;
        this.width = width;
        this.height = height;
        this.imgIndex = imgIndex;
    }


    @Override
    public boolean move() {
        return false;
    }

    @Override
    public void draw(Graphics g, int xoffset, int yoffset) {
        int x = (int)((this.x+(chunkx- Game.chunks[4].chunkx)*FinalOptions.CHUNKSIZE)*Options.TILE_SIZE);
        int y = (int)((this.y+(chunky-Game.chunks[4].chunky)*FinalOptions.CHUNKSIZE)*Options.TILE_SIZE);
        if(x-xoffset>0 && y-yoffset>0 && x-xoffset < Options.GAME_WIDTH && y-yoffset <Options.GAME_HEIGHT)
            g.drawImage(images[imgIndex], x-xoffset, y-yoffset, this.width, this.height, null);
    }

    public String interact(){
        return "...";
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
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }



}
