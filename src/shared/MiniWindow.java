package shared;


import client.Game;


import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class MiniWindow {
    private int x;
    private int y;
    private String title;
    private static final int exitButtonWidth = 25;
    public boolean visable = true;

    public MiniWindow(int x, int y, String title, boolean visable) {
        this.x = x;
        this.y = y;
        this.visable = visable;
        this.title = title;
        Game.miniWindows.add(this);
    }
    public void draw(Graphics g){
        g.setColor(Color.black);
        g.fillRect(0, 0, Game.SCREEN_SIZE.width, Game.SCREEN_SIZE.height);
        g.setColor(Color.red);
        g.fillRect(Game.SCREEN_SIZE.width-exitButtonWidth-15, 0, exitButtonWidth, exitButtonWidth);
    }

    public void mousePressed(MouseEvent e){
        visable = visable && e.getX()<Game.SCREEN_SIZE.width-exitButtonWidth-15 || e.getY() > exitButtonWidth;
    }

}
