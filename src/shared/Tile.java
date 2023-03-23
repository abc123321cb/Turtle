package shared;

import java.awt.*;
import java.io.Serializable;

public class Tile implements Serializable {

    int x;
    int y;
    int lower_index;
    int upper_index;
    int current_index;

    boolean change = true;

    public Tile(int column, int row, int current_index, int lower_index, int upper_index, boolean change) {
        this.x = column * Options.TILE_SIZE;
        this.y = row * Options.TILE_SIZE;
        this.lower_index = lower_index;
        this.upper_index = upper_index;
        this.current_index = current_index;
        changeState(true, lower_index, upper_index);
        this.change = change;
    }

    public void draw(Graphics g, int x, int y) {

        if (this.x -x + Options.TILE_SIZE > 0 && this.y-y + Options.TILE_SIZE >0 && (this.y-y) < Options.GAME_HEIGHT && this.x-x < Options.GAME_WIDTH)
            g.drawImage(FinalOptions.TextureAtlas[current_index], (this.x-x), (this.y-y), Options.TILE_SIZE, Options.TILE_SIZE, null);
    }

    public void changeState(boolean change, int lower_index, int upper_index) {
        if (change) this.current_index = Utility.getRandom(lower_index, upper_index);
    }
}
