package client;

import shared.StaticOptions;
import shared.Options;
import shared.Chunk;
import shared.protag;

import java.util.Arrays;
import java.util.Random;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.*;//cut this down


import javax.swing.JPanel;

//game contains everything the client sees in and iteracts with
public class Game extends JPanel implements Runnable {

    static final Dimension SCREEN_SIZE = new Dimension(Options.GAME_WIDTH, Options.GAME_HEIGHT);

    

    
    Thread gameThread;
    Image image;
    Graphics graphics;
    protag player;
    Chunk chunk;
    Chunk[] surrondingChunks;
    static Random random = new Random();
    int xoffset = Options.TILE_SIZE*StaticOptions.CHUNKSIZE;
    int yoffset = Options.TILE_SIZE*StaticOptions.CHUNKSIZE;

    public Game(){
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
            int ChunkWidth = Options.TILE_SIZE*StaticOptions.CHUNKSIZE;
            return new Chunk[]{
                new Chunk(3,chunk.chunkx-1,chunk.chunky-1, -ChunkWidth,-ChunkWidth),  //Left Top
                new Chunk(3,chunk.chunkx,  chunk.chunky-1,0,-ChunkWidth),     //Top
                new Chunk(3,chunk.chunkx+1,chunk.chunky-1,  ChunkWidth,-ChunkWidth),  //Right Top
    
                new Chunk(3,chunk.chunkx-1,chunk.chunky,   -ChunkWidth,0),     //Left Mid
                new Chunk(3,chunk.chunkx,  chunk.chunky,    0,0),     //Center
                new Chunk(3,chunk.chunkx+1,chunk.chunky,    ChunkWidth,0),     //Right Mid
    
                new Chunk(3,chunk.chunkx-1,chunk.chunky+1, -ChunkWidth,ChunkWidth),    //Left Bottom
                new Chunk(3,chunk.chunkx,  chunk.chunky+1,0,ChunkWidth),       //Bottom
                new Chunk(3,chunk.chunkx+1,chunk.chunky+1,  ChunkWidth,ChunkWidth)     //Right Bottom
            };
        }
        public void draw(Graphics g){
            Graphics2D g2 = (Graphics2D)g;
            xoffset = (int)(player.x*Options.TILE_SIZE)-Options.GAME_WIDTH/2;
            yoffset = (int)(player.y*Options.TILE_SIZE)-Options.GAME_HEIGHT/2;
            for(Chunk c: surrondingChunks){
                c.draw(g2,xoffset,yoffset);
            }
            player.draw(g2,xoffset,yoffset);
            //Fireball.draw(g2,xoffset,yoffset);
    
        }
    
        public void paint(Graphics g) {
            image = createImage(getWidth(), getHeight());
            graphics = image.getGraphics();
            draw(graphics);
            g.drawImage(image, 0, 0, this);
        }
    
        public void step(){
            int[] a = new int[] {chunk.chunkx, chunk.chunky};
            int[] newloc = player.move(chunk.chunkx,chunk.chunky);
            if(!Arrays.equals(newloc, a)){
                reloadChunks(newloc);
            }
            
        }
    
        public void reloadChunks(int[] playercordiantes){
                chunk.chunkx = playercordiantes[0];
                chunk.chunky = playercordiantes[1];
                chunk.generateMap();
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
                /* 
                if(e.getKeyCode() == KeyEvent.VK_N){
                    Main.zoom(2);
                    reloadChunks(new int[] {chunk.playerloc[0], chunk.playerloc[1]});
                    Debug();
                } else if (e.getKeyCode() == KeyEvent.VK_M) {
                    Main.zoom(-2);
                    reloadChunks(new int[] {chunk.playerloc[0], chunk.playerloc[1]});
                    Debug();
                }
                */
            }
            
            public void Debug(){
                System.out.println("Xoffset : "+xoffset+" Yoffset : "+yoffset);
                System.out.println("Player X : "+player.x+" Player Y : "+player.y);
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