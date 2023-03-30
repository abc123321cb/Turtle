package shared;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Obsticle {
    public double x;
    public double y;
    public int width = 20;
    public int height = 20;
    private int imgIndex;
    private static BufferedImage[] img = new BufferedImage[]{
            Utility.getTextureAtlasBasic("resources/Rock.png", 20, 20)[0]};

    public Obsticle(double x, double y, int imgIndex){
        this.x = x;
        this.y = y;
        this.imgIndex = imgIndex;
    }

    public void draw(Graphics g, int x, int y){
        // this if statement doensn't work yet.
        if(Utility.onScreen((int)(this.x*Options.TILE_SIZE), (int)(this.y*Options.TILE_SIZE), width, height,1 ,1))
            g.drawImage(img[imgIndex],(int)(this.x*Options.TILE_SIZE-x),(int)(this.y*Options.TILE_SIZE-y),width,height,null);

    }





}
