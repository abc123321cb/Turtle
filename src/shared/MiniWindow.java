package shared;

import javax.swing.*;

public class MiniWindow extends JPanel {
    private int x;
    private int y;
    private int width;
    private int height;
    private boolean draggable = true;
    private boolean selected;
    private int[] select = new int[2];

    public MiniWindow(int x, int y, int width, int height){
        this.x=x;
        this.y=y;
        this.width = width;
        this.height = height;
    }

    public MiniWindow(int x, int y, int width, int height, boolean draggable){
        this.x=x;
        this.y=y;
        this.width = width;
        this.height = height;
        this.draggable = draggable;
    }


    public void mousePressed(){}




}
