package shared;

import java.awt.*;
import java.io.Serializable;

import server.Server;

public class Tile implements Serializable {

    int x;
    int y;
    int lower_index;
    int upper_index;
    int current_index;

    boolean change = true;

    public Tile(int column, int row, int current_index, int lower_index, int upper_index, boolean change) {
        this.x = column * Server.CHUNKSIZE;
        this.y = row * Server.CHUNKSIZE;
        this.lower_index = lower_index;
        this.upper_index = upper_index;
        this.current_index = current_index;
        changeState(true, lower_index, upper_index);
        this.change = change;
    }

    public void draw(Graphics g, int x, int y) {
        if (this.x -x + Server.CHUNKSIZE > 0 && this.y-y + Server.CHUNKSIZE >0 && (this.y-y) < Server.CHUNKSIZE && this.x-x < Server.CHUNKSIZE)
            g.drawImage(StaticOptions.Backgroundimages[current_index].get_image(current_index), (this.x-x), (this.y-y), Server.CHUNKSIZE, Server.CHUNKSIZE, null);
    }

    public void changeState(boolean change, int lower_index, int upper_index) {
        if (change) this.current_index = Utility.getRandom(lower_index, upper_index);
    }
}
