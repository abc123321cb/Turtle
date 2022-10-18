import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Serializable;

public class Areas implements Serializable {
    BufferedImage[] background;
    BufferedImage image;

    int x;
    int y;
    int start_image;

    boolean change = true;
    Areas(BufferedImage[] background, int column, int row, int start_image, boolean change){
        this.background = background;
        this.start_image = start_image;
        this.image = background[start_image];
        this.change = change;

        this.x = column * 50;
        this.y = row * 50;
        changeState();
    }

    public void draw(Graphics g){
        g.drawImage(this.image, this.x, this.y, 50, 50, null);
    }

    public void changeState() {
        if (this.change) {
            this.image = setup.getRandom(background);

        }
    }
}
