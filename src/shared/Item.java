package shared;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Item {
    private int index;
    // index decides what each item is the indexes are as follows:
    // 0: carb claw
    // 1: crab shell
    //

    private double x = -999;
    private double y = -999;
    private static BufferedImage[] imgs = Utility.getTextureAtlasBasic("resources/ItemAtlas.png", 10, 10);


    public Item(int item){
        this.index = item;
    }

    public Item(int item, double x, double y){
        this.index=item;
        this.x=x;
        this.y=y;
    }

    // Takes a graphics and draws itself in the dimensions given
    public void draw(Graphics g, int x, int y){
        draw(g, (int) (this.x*Options.TILE_SIZE)-x, (int) (this.y*Options.TILE_SIZE)-y,20);
    }
    public void draw(Graphics g, int x, int y, int dimen){
        g.drawImage(imgs[index], x+3, y+3, dimen-6, dimen-6, null);
    }

    public double getX(){
        return x;
    }

    public double getY(){
        return y;
    }

}
