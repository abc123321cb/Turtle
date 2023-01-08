import java.awt.*;
import java.io.Serializable;

public class Tile implements Serializable {

    int x;
    int y;
    int lower_index;
    int upper_index;
    int current_index;

    boolean change = true;

    Tile(int column, int row, int current_index, int lower_index, int upper_index, boolean change) {
        this.x = column;
        this.y = row;
        this.lower_index = lower_index;
        this.upper_index = upper_index;
        this.current_index = current_index;
        changeState(true, lower_index, upper_index);
        this.change = change;
    }

    public void draw(Graphics g) {
        g.drawImage(Chunk.get_image(current_index), this.x*OldMain.CELL_WIDTH, this.y*OldMain.CELL_WIDTH, OldMain.CELL_WIDTH, OldMain.CELL_WIDTH, null);
    }

    public void changeState(boolean change, int lower_index, int upper_index) {
        if (change) this.current_index = setup.getRandom(lower_index, upper_index);
    }
}
