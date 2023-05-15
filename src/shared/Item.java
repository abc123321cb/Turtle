package shared;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Item {
    private int index;
    // index decides what each item is the indexes are as follows:
    // 0: carb claw
    // 1: crab shell
    //

    private static BufferedImage[] imgs = Utility.getTextureAtlasBasic("resources/ItemAtlas.png", 10, 10);


    public Item(int item){
        this.index = item;
    }

    // Takes a graphics and draws itself in the dimensions given
    public void draw(Graphics g, int x, int y, int dimen){
        g.drawImage(imgs[index], x+3, y+3, dimen-6, dimen-6, null);
    }

}
