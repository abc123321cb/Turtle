import java.awt.*;
import java.awt.image.BufferedImage;

public class Areas {
    BufferedImage[] background;
    BufferedImage image;

    int x;
    int y;
    int lower_index;
    int upper_index;

    boolean change = true;
    Areas(BufferedImage[] background, int column, int row, int lower_index, int upper_index){
        this.background = background;
        if (background.length==1){
            this.image = background[0];
            this.change = false;
        }
        this.x = column * 50;
        this.y = row * 50;
        this.lower_index = lower_index;
        this.upper_index = upper_index;
        changeState(this.change, lower_index, upper_index);
    }

    public void draw(Graphics g){
        g.drawImage(this.image, this.x, this.y, 50, 50, null);
    }

    public void changeState(boolean change, int lower_index, int upper_index) {
        if (change) {
            this.image = setup.getRandom(background, lower_index, upper_index);

        }
    }
}
