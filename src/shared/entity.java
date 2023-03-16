package shared;

import java.awt.*;

public interface entity {


    
    public void move();

    public void draw(Graphics g, int xoffset, int yoffset);

    public double getX();

    public double getY();

    public int getChunkx();

    public int getChunky();

    public double getXvel();

    public double getYvel();

    public int getdimen();


}
