import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.*;
import javax.swing.*;


public class setup extends JPanel implements Runnable {

    static final Dimension SCREEN_SIZE = new Dimension(Main.GAME_WIDTH, Main.GAME_HEIGHT);


    
    // has texture groups start index, stop index, change
    static final ArrayList<int[]> TEXTUREGROUPS = new ArrayList<int[]>(
            Arrays.asList(new int[]{10, 13, 1},new int[]{20,23,0},new int[]{30,33,0},new int[]{40,43,0},new int[]{50,53,0}));

    Thread gameThread;
    Image image;
    Graphics graphics;
    static Random random = new Random();
    protag player;
    Chunk chunk;
    Chunk[] surrondingChunks;
    int xoffset = Main.TILE_SIZE*Main.chunksize;
    int yoffset = Main.TILE_SIZE*Main.chunksize;

    public GameClient socketClient;
    public GameServer socketServer;

    setup(){
        this.setFocusable(true);
        this.addKeyListener(new ActionListner());
        this.addMouseListener(new MouseListen());
        this.setPreferredSize(SCREEN_SIZE);

        player = new protag();
        chunk = new Chunk(3, 0, 0);
        surrondingChunks = makeSurrondingChunks(chunk);
        gameThread = new Thread(this);
        gameThread.start();
    }


    public Chunk[] makeSurrondingChunks(Chunk chunk){
        // 3 to generate using the opensimplex
        // then x,y, xoffset, yoffset
        return new Chunk[]{new Chunk(3, chunk.playerloc[0]+1, chunk.playerloc[1],Main.TILE_SIZE*Main.chunksize,0),
            new Chunk(3, chunk.playerloc[0]-1, chunk.playerloc[1], -Main.TILE_SIZE*Main.chunksize, 0),
            new Chunk(3, chunk.playerloc[0], chunk.playerloc[1]+1,0,Main.TILE_SIZE*Main.chunksize),
            new Chunk(3, chunk.playerloc[0], chunk.playerloc[1]-1,0,-Main.TILE_SIZE*Main.chunksize),
            new Chunk(3,chunk.playerloc[0]-1, chunk.playerloc[1]-1, -Main.TILE_SIZE*Main.chunksize,-Main.TILE_SIZE*Main.chunksize),
            new Chunk(3,chunk.playerloc[0]+1, chunk.playerloc[1]-1, Main.TILE_SIZE*Main.chunksize,-Main.TILE_SIZE*Main.chunksize),
            new Chunk(3,chunk.playerloc[0]-1, chunk.playerloc[1]+1, -Main.TILE_SIZE*Main.chunksize,Main.TILE_SIZE*Main.chunksize),
            new Chunk(3,chunk.playerloc[0]+1, chunk.playerloc[1]+1, Main.TILE_SIZE*Main.chunksize,Main.TILE_SIZE*Main.chunksize)
        };
    }

    public void draw(Graphics g){
        Graphics2D g2 = (Graphics2D)g;
        xoffset = (int) (player.x)-Main.GAME_WIDTH/2;
        yoffset = (int)(player.y)-Main.GAME_HEIGHT/2;

        chunk.draw(g2, xoffset, yoffset);
        for(Chunk c: surrondingChunks){
            c.draw(g2,xoffset,yoffset);
        }
        player.draw(g2,xoffset,yoffset);
        Fireball.draw(g2,xoffset,yoffset);

    }

    public void paint(Graphics g) {
        image = createImage(getWidth(), getHeight());
        graphics = image.getGraphics();
        draw(graphics);
        g.drawImage(image, 0, 0, this);
    }

    public void step(){
        int[] a = new int[] {chunk.playerloc[0], chunk.playerloc[1]};
        int[] newloc = player.move(chunk.playerloc);
        if(!Arrays.equals(newloc, a)){
            reloadChunks(newloc);
        }
    }

    public void reloadChunks(int[] playercordiantes){
            chunk.generateMap(playercordiantes);
            surrondingChunks = makeSurrondingChunks(chunk);
    }


    //main game loop
    //put new commands in the if statement just trust me
    public void run(){
        long lastTime = System.nanoTime();
        double amountofticks = 60.0;
        double ns = 1000000000/amountofticks;
        double delta = 0;
        while(true){
            long  now = System.nanoTime();
            delta+= (now-lastTime)/ns;
            lastTime = now;
            if (delta>=1){
                repaint();
                step();
                delta--;
            }
        }
    }

    //Generates random number between 2 values
    public static int getRandom(int min, int max) {
        return random.nextInt(max+1-min)+min;
    }

    //Create mouse listener
    public class MouseListen implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {}
        @Override
        public void mousePressed(MouseEvent e) {
            chunk.mousepressed(e,xoffset,yoffset);
        }
        @Override
        public void mouseReleased(MouseEvent e) {}
        @Override
        public void mouseEntered(MouseEvent e) {}
        @Override
        public void mouseExited(MouseEvent e) {}
    }

    //Create key listener
    public class ActionListner extends KeyAdapter {
        public void keyPressed(KeyEvent e){
            player.keypressed(e);
            chunk.keypressed(e);
            if(e.getKeyCode() == KeyEvent.VK_N){
                Main.zoom(2);
                reloadChunks(new int[] {chunk.playerloc[0], chunk.playerloc[1]});
            } else if (e.getKeyCode() == KeyEvent.VK_M) {
                Main.zoom(-2);
                reloadChunks(new int[] {chunk.playerloc[0], chunk.playerloc[1]});
            }

        }
        public void keyReleased(KeyEvent e){
            player.keyreleased(e);
        }
        public void move(){

        }
        public void draw(Graphics g){

        }
    }
}
