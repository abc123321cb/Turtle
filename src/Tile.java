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
        this.x = column * Main.TILESIZE;
        this.y = row * Main.TILESIZE;
        this.lower_index = lower_index;
        this.upper_index = upper_index;
        this.current_index = current_index;
        changeState(true, lower_index, upper_index);
        this.change = change;
    }

    public void draw(Graphics g, int x, int y) {
        // draws tiles note it only draws tiles if they would appear in the players screen thats why there is a if
        if (this.x -x + Main.CELL_WIDTH > 0 && this.y-y + Main.CELL_WIDTH >0 && (this.y-y) < Main.GAME_HEIGHT && this.x-x < Main.GAME_WIDTH)
            g.drawImage(Chunk.get_image(current_index), (this.x-x), (this.y-y), Main.CELL_WIDTH, Main.CELL_WIDTH, null);
    }

    public void changeState(boolean change, int lower_index, int upper_index) {
        if (change) this.current_index = setup.getRandom(lower_index, upper_index);
    }
}
