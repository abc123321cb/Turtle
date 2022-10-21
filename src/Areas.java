import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Serializable;

public class Areas implements Serializable {

    int x;
    int y;
    int lower_index;
    int upper_index;
    int current_index;

    boolean change = true;
    Areas(int column, int row, int current_index, int lower_index, int upper_index){
        this.x = column * 50;
        this.y = row * 50;
        this.lower_index = lower_index;
        this.upper_index = upper_index;
        this.current_index = current_index;
        changeState(this.change, lower_index, upper_index);
    }
    // if a Area isn't changing
    Areas(int column, int row, int current_index){
        this.change = false;

    }


    public void draw(Graphics g){
        g.drawImage(Mapping.get_image(current_index), this.x, this.y, 50, 50, null);
    }

    public void changeState(boolean change, int lower_index, int upper_index) {
        if (change) {
            this.current_index = setup.getRandom(lower_index, upper_index);

        }
    }
}
