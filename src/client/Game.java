package client;

import shared.*;
import java.util.ArrayList;
import java.util.Random;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

//game contains everything the client sees in and iteracts with
public class Game extends JPanel implements Runnable {

    static final Dimension SCREEN_SIZE = new Dimension(Options.GAME_WIDTH, Options.GAME_HEIGHT);

    Thread gameThread;
    Image image;
    Graphics graphics;
    protag player;
    public static Chunk[] chunks;
    static Random random = new Random();
    int xcamera = Options.TILE_SIZE * StaticOptions.CHUNKSIZE;
    int ycamera = Options.TILE_SIZE * StaticOptions.CHUNKSIZE;
    public static ArrayList<protag> playerList = new ArrayList<protag>();
    public static ArrayList<Enemy> enemies = new ArrayList<Enemy>();

    public Game() {
        this.setFocusable(true);
        this.addKeyListener(new ActionListner());
        this.addMouseListener(new MouseListen());
        this.setPreferredSize(SCREEN_SIZE);

        player = new protag();
        playerList.add(player);
        chunks = makeSurrondingChunks();

        gameThread = new Thread(this);
        gameThread.start();
    }

    // main game loop
    // put new commands in the if statement just trust me
    public void run() {
        long lastTime = System.nanoTime();
        final double amountofticks = 60.0;
        final double ns = 1000000000 / amountofticks;
        double delta = 0;
        while (true) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            if (delta >= 1) {
                step();
                repaint();
                delta--;
            }
        }
    }

    public void step() {
        player.move();
        for(Enemy e: enemies)
            e.move();
        if (!(chunks[4].chunkx == player.chunkx && chunks[4].chunky == player.chunky)) {
            reloadChunks();
        }
    }

    public void reloadChunks() {
        chunks = makeSurrondingChunks();
        // idealy we could catch if we are teleporting cross chunk grid and therefore
        // nead to load entierly new chunks, or if we can just reasign loaded chunks to
        // slots in the array thus cutting loading time in 3
    }

    public Chunk[] makeSurrondingChunks() {
        int ChunkWidth = Options.TILE_SIZE * StaticOptions.CHUNKSIZE;
        return new Chunk[] {
                new Chunk(3, player.chunkx - 1, player.chunky - 1, -ChunkWidth, -ChunkWidth), // Left Top
                new Chunk(3, player.chunkx, player.chunky - 1, 0, -ChunkWidth), // Top
                new Chunk(3, player.chunkx + 1, player.chunky - 1, ChunkWidth, -ChunkWidth), // Right Top

                new Chunk(3, player.chunkx - 1, player.chunky, -ChunkWidth, 0), // Left Mid
                new Chunk(3, player.chunkx, player.chunky, 0, 0), // Center
                new Chunk(3, player.chunkx + 1, player.chunky, ChunkWidth, 0), // Right Mid

                new Chunk(3, player.chunkx - 1, player.chunky + 1, -ChunkWidth, ChunkWidth), // Left Bottom
                new Chunk(3, player.chunkx, player.chunky + 1, 0, ChunkWidth), // Bottom
                new Chunk(3, player.chunkx + 1, player.chunky + 1, ChunkWidth, ChunkWidth) // Right Bottom
        };
    }

    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        xcamera = (int) (player.localx * Options.TILE_SIZE) - Options.GAME_WIDTH / 2;
        ycamera = (int) (player.localy * Options.TILE_SIZE) - Options.GAME_HEIGHT / 2;
        for (Chunk c : chunks) {
            c.draw(g2, xcamera, ycamera);
        }
        player.draw(g2, xcamera, ycamera);

        for(Enemy e: enemies){
            e.draw(g,xcamera,ycamera);
        }
        // Fireball.draw(g2,xoffset,yoffset);
    }

    public void paint(Graphics g) {
        image = createImage(getWidth(), getHeight());
        graphics = image.getGraphics();
        try {
            draw(graphics);
        }catch (Exception ignored){}
        g.drawImage(image, 0, 0, this);
    }

    // Create mouse listener
    public class MouseListen implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {
        }

        @Override
        public void mousePressed(MouseEvent e) {
            // chunk.mousepressed(e,xoffset,yoffset);
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }
    }

    // Create key listener
    public class ActionListner extends KeyAdapter {

        public void keyPressed(KeyEvent e) {
            player.keypressed(e);
            /*
             * if(e.getKeyCode() == KeyEvent.VK_N){
             * Main.zoom(2);
             * reloadChunks(new int[] {chunk.playerloc[0], chunk.playerloc[1]});
             * Debug();
             * } else if (e.getKeyCode() == KeyEvent.VK_M) {
             * Main.zoom(-2);
             * reloadChunks(new int[] {chunk.playerloc[0], chunk.playerloc[1]});
             * Debug();
             * }
             */
        }

        public void keyReleased(KeyEvent e) {
            player.keyreleased(e);
        }
    }
}