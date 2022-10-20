import java.awt.*;
import java.awt.image.BufferedImage;

public class Areas {
    BufferedImage[] background;
    BufferedImage image;

    int x;
    int y;

    boolean change = true;
    Areas(BufferedImage[] background, int column, int row){
        this.background = background;
        if (background.length==1){
            this.image = background[0];
            this.change = false;
        }
        this.x = column * 50;
        this.y = row * 50;
        changeState(this.change);
    }

    public void draw(Graphics g){
        g.drawImage(this.image, this.x, this.y, 50, 50, null);
    }

    public void changeState(boolean change) {
        if (this.change) {
            this.image = setup.getRandom(background,10,13);

        }
    }
}
