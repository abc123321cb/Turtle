package shared;

import java.awt.*;

public interface entity {


    
    public boolean move();

    public void draw(Graphics g, int xoffset, int yoffset);

    public double getX();

    public double getY();

    public int getChunkx();

    public int getChunky();

    public double getXvel();

    public double getYvel();

    public int getWidth();

    public int getHeight();


}
