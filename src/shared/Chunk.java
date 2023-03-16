package shared;
import client.Game;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.Arrays;
import java.util.Random;


public class Chunk {

    int ticksperupdate = 40;
    int ticks = 0;
    public int xoffset = 0;
    public int yoffset = 0;
    public int chunkx = 0;
    public int chunky = 0;

    Tile[][] TileArray = new Tile[StaticOptions.CHUNKSIZE][StaticOptions.CHUNKSIZE];

    public Chunk(int playermapx, int playermapy, int xoffset, int yoffset,Tile[][] TileArray){
        this.xoffset = xoffset;
        this.yoffset = yoffset;
        this.chunkx = playermapx;
        this.chunky = playermapy;
        this.TileArray = TileArray;
    }

    //this will be removed when server chunk loading is fully implememnted
    public Chunk(int mapnumber, int playermapx, int playermapy, int xoffset, int yoffset){
        this.xoffset = xoffset;
        this.yoffset = yoffset;
        this.chunkx = playermapx;
        this.chunky = playermapy;

        /*
         * Case 1 : none
         * Case 2 : generate Saved map
         * Case 3 : generate map based on seed
         */
        switch (mapnumber) {
            case 1 -> {/* none */}
            case 2 -> load();
            case 3 -> generateMap();
        }

        /*
        Enemy genration needs to moved to elsewhere.
         */

        int numCrabs = (int)(Math.random()*10);
        for(int i = 0; i < 100; i++){
            Game.enemies.add(new GhostCrab(Math.random()*5, Math.random()*5,20,chunkx,chunky));
        }

    }

    // moves the current index range forward
    public void update(int colum, int row){
        Tile area = TileArray[colum][row];

        int index = -1;
        for(int i = 0; i < StaticOptions.TEXTUREGROUPS.size(); i++){
            if (Arrays.equals(new int[]{area.lower_index, area.upper_index, (area.change) ? 1 : 0}, StaticOptions.TEXTUREGROUPS.get(i))){
                index = i;
                break;
            }
        }
        if (index!=-1){
            index++;
            index = (index==StaticOptions.TEXTUREGROUPS.size())? 0 : index;
            area.lower_index = StaticOptions.TEXTUREGROUPS.get(index)[0];
            area.upper_index = StaticOptions.TEXTUREGROUPS.get(index)[1];
            area.change = StaticOptions.TEXTUREGROUPS.get(index)[2] == 1;
            area.changeState(true, area.lower_index, area.upper_index);
        }
    }

    public void draw(Graphics2D g, int x, int y){
        /*
        Draws all the tiles and makes the water change from time to time.
         */

        ticks ++;
        for(Tile[] a : TileArray){
            for(Tile areas: a){
                areas.draw(g, x-xoffset, y-yoffset);
            }
        }
        if (ticks > ticksperupdate){
            ticks = 0;
            for(Tile[] a : TileArray){
                for(Tile areas: a){
                        areas.changeState(areas.change, areas.lower_index, areas.upper_index);
                }
            }
        }
    }

    // Saves the current map. Format is index of background, specific background
    public void save(){
        //in the future save to specific folder in worlds
        String path = Options.root+"worlds\\" + this.chunkx + "_" + this.chunky + ".txt";

        File myFile = new File(path);

        System.out.println(myFile);

        // Try block to check if exception occurs
        try {
            FileOutputStream fileout = new FileOutputStream(Options.root+path);
            ObjectOutputStream out = new ObjectOutputStream(fileout);
            out.writeObject(this.TileArray);
            out.close();
            fileout.close();

            // Display message for successful execution of
            // program on the console
            System.out.println("File is created successfully with the content.");
        } catch (IOException e) {

            // Print the exception
            System.out.println(e.getMessage());
            try {

                System.out.println("Making new file");
                myFile = new File(Options.root+" " + this.chunkx + "_" + this.chunky + ".txt");
                if (myFile.createNewFile()) {
                    System.out.println("Success");
                    save();
                } else {
                    System.out.println(":(");
                }
            } catch (IOException e2){
                System.out.println(e2.getMessage());
            }
        }
    }

    // get data from map
    public void load(){
        File myFile = new File(Options.root+"worlds\\" + chunkx + "_" + chunky + ".txt");
        try {
            FileInputStream filein = new FileInputStream(myFile);
            ObjectInputStream in = new ObjectInputStream(filein);
            this.TileArray = (Tile[][]) in.readObject();
            in.close();
        }catch (IOException e){
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    public void keypressed(KeyEvent e){
        if (e.getKeyCode() == KeyEvent.VK_ENTER){
            save();
        }
    }

    public void mousepressed(MouseEvent a, int xoffset, int yoffset){
        // currently broken idk if we want to fix it.
        int x = a.getX()-xoffset;
        int y = a.getY()-yoffset;
        int colum = x/Options.TILE_SIZE;
        int row = y/Options.TILE_SIZE;
        update(colum, row);
    }

    public void generateMap(){
        long seed = (long) (1);
        Random generator = new Random(seed);
        
        /* General Terrian scales : 
         * Scale             - General terrian scale
         * heightscale       - general height scale
         * finefeaturescale  - fine deviation to Height scale
         * sharpfeaturescale - sharp deviation to Height scale
         * localflatness     - multiplies feature scales
         * 
         * 
         */
        double scale = 0.5;
        double heightscale       = 0.1    * scale;
        double finefeaturescale  = 0.1    * scale;
        double sharpfeaturescale = 5      * scale;
        double localflatness     = 0.01   * scale;
        //double temperature       = 0.005  * scale;
        //double moisture          = 0.005  * scale;
        //double altitude          = 0.0005 * scale;
        //double latitude          = 0.0005 * scale;
        
        OpenSimplexNoise watersimplex         = new OpenSimplexNoise(generator.nextLong());
        OpenSimplexNoise finefeaturesimplex   = new OpenSimplexNoise(generator.nextLong());
        OpenSimplexNoise sharpfeaturesimplex  = new OpenSimplexNoise(generator.nextLong());
        OpenSimplexNoise localflatnesssimplex = new OpenSimplexNoise(generator.nextLong());
        //OpenSimplexNoise temperaturesimplex   = new OpenSimplexNoise(generator.nextLong());
        //OpenSimplexNoise moisturesimplex      = new OpenSimplexNoise(generator.nextLong());
        //OpenSimplexNoise altitudesimplex      = new OpenSimplexNoise(generator.nextLong());
        //OpenSimplexNoise latitudesimplex      = new OpenSimplexNoise(generator.nextLong());
        for(int x=0; x<StaticOptions.CHUNKSIZE; x++){
            for(int y=0; y<StaticOptions.CHUNKSIZE; y++){
                
                double height = watersimplex.eval((x+chunkx*StaticOptions.CHUNKSIZE)*heightscale, (y+chunky*StaticOptions.CHUNKSIZE)*heightscale);
                //generate water
                if (height<-0.3){
                    TileArray[x][y] = new Tile (x, y, 10,10,13,true);
                } else {
                    height += (finefeaturesimplex.eval((x+chunkx*20)*finefeaturescale, (y+chunky*10)*finefeaturescale)
                              +sharpfeaturesimplex.eval((x+chunkx*20)*sharpfeaturescale, (y+chunky*10)*sharpfeaturescale))
                              *(localflatnesssimplex.eval((x+chunkx*20)*localflatness, (y+chunky*10)*localflatness)+1)/2;
                    if(height<0){
                        TileArray[x][y] = new Tile (x, y, 20,20,23,false);
                    }else if(height<0.2){
                        TileArray[x][y] = new Tile (x, y, 30,30,33,false);
                    }else if(height<0.4){
                        TileArray[x][y] = new Tile (x, y, 40,40,43,false);
                    }else{
                        TileArray[x][y] = new Tile (x, y, 50,50,53,false);
                    }
                }
            }
        }
    }
}
