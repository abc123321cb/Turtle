package shared;

import client.Game;

import java.awt.*;

public class Inventory extends MiniWindow{
    private static int box_size = 10;
    Item[][] box;
    private int x;
    private int y;

    public Inventory(int x, int y, String title, boolean visable){
        super(x,y,title,visable);
        box = new Item[box_size][box_size];

    }

    public void draw(Graphics g){
        super.draw(g);
        g.setColor(Color.green);
        int columnWidth = Math.min((Game.SCREEN_SIZE.width - 100)/box.length,(Game.SCREEN_SIZE.height-50)/box.length);
        for(int i = 0; i < box.length+1; i++){
            g.drawLine(i*columnWidth,0,i*columnWidth,columnWidth*box.length);
            g.drawLine(0, i*columnWidth, columnWidth*box.length, i*columnWidth);
        }
        for(int i = 0; i < box.length; i++){
            for(int j = 0; j < box.length; j++){
                if(box[i][j] != null){
                    box[i][j].draw(g,j*columnWidth,i*columnWidth,columnWidth);
                }
            }
        }

    }

    // returns true if added and adds the item to the array. If inventory is full fails to add
    public boolean add(Item item){
        for(int i = 0; i < box_size*box_size; i++){
            if(box[i/box_size][i%box_size]==null){
                box[i/box_size][i%box_size] = item;
                return true;
            }
        }
        return false;
    }

}
