import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Arrays;
import java.util.Random;

public class Chunk {
    static BufferedImage[] Backgroundimages = Utility.getTextureAtlasBasic("res/tiles/TextureAtlasv20v20v.png",20,20);

    int ticksperupdate = 40;
    int ticks = 0;

    int[] playerloc = new int[2];

    Tile[][] TileArray;

    Chunk(int mapnumber, int playermapx, int playermapy){
        this.TileArray = new Tile[Main.CELL_WIDTH][Main.CELL_WIDTH];
        this.playerloc[0] = playermapx;
        this.playerloc[1] = playermapy;

        /*
         * Case 1 : generate a flat empty map
         * Case 2 : generate Saved map
         * Case 3 : generate map based on seed
         */
        switch (mapnumber) {
            case 1 -> {/* make blank map */}
            case 2 -> make_map(this.playerloc);
            case 3 -> generateMap(this.playerloc);
        }
    }

    // moves the current index range forward
    public void update(int colum, int row){
        Tile area = TileArray[colum][row];

        int index = -1;
        for(int i = 0; i < setup.TEXTUREGROUPS.size(); i++){
            if (Arrays.equals(new int[]{area.lower_index, area.upper_index, (area.change) ? 1 : 0}, setup.TEXTUREGROUPS.get(i))){
                index = i;
                break;
            }
        }
        if (index!=-1){
            index++;
            index = (index==setup.TEXTUREGROUPS.size())? 0 : index;
            area.lower_index = setup.TEXTUREGROUPS.get(index)[0];
            area.upper_index = setup.TEXTUREGROUPS.get(index)[1];
            area.change = setup.TEXTUREGROUPS.get(index)[2] == 1;
            area.changeState(true, area.lower_index, area.upper_index);
        }
    }

    public void draw(Graphics2D g){
        ticks ++;
        for(Tile[] a : TileArray){
            for(Tile areas: a){
                areas.draw(g);
                if (ticks >= ticksperupdate){
                    areas.changeState(areas.change, areas.lower_index, areas.upper_index);
                }
            }
        }
        if (ticks > ticksperupdate) ticks = 0;
    }

    // Saves the current map. Format is index of background, specific background
    public void save(){
        String path = Options.root+"res/tiles/Map/map" + this.playerloc[0] + "_" + this.playerloc[1] + ".txt";

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
                myFile = new File(Options.root+" " + this.playerloc[0] + "_" + this.playerloc[1] + ".txt");
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
    public void make_map(int[] loc){
        File myFile = new File(Options.root+"res\\tiles\\Map\\map" + loc[0] + "_" + loc[1] + ".txt");
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

    public void mousepressed(MouseEvent a){
        int x = a.getX();
        int y = a.getY();
        int colum = x/Main.CELL_WIDTH;
        int row = y/Main.CELL_WIDTH;
        update(colum, row);
    }


    public static BufferedImage get_image(int index){
        return Backgroundimages[index];
    }


    public void generateMap(int[] playerloc){
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
        double temperature         = 0.005  * scale;
        double moisture          = 0.005  * scale;
        double altitude          = 0.0005 * scale;
        double latitude          = 0.0005 * scale;
        
        OpenSimplexNoise watersimplex         = new OpenSimplexNoise(generator.nextLong());
        OpenSimplexNoise finefeaturesimplex   = new OpenSimplexNoise(generator.nextLong());
        OpenSimplexNoise sharpfeaturesimplex  = new OpenSimplexNoise(generator.nextLong());
        OpenSimplexNoise localflatnesssimplex = new OpenSimplexNoise(generator.nextLong());
        OpenSimplexNoise temperaturesimplex     = new OpenSimplexNoise(generator.nextLong());
        OpenSimplexNoise moisturesimplex      = new OpenSimplexNoise(generator.nextLong());
        OpenSimplexNoise altitudesimplex      = new OpenSimplexNoise(generator.nextLong());
        OpenSimplexNoise latitudesimplex      = new OpenSimplexNoise(generator.nextLong());
        for(int x=0; x<Main.chunksize; x++){
            for(int y=0; y<Main.chunksize; y++){
                
                double height = watersimplex.eval((x+playerloc[0]*20)*heightscale, (y+playerloc[1]*10)*heightscale);
                //generate water
                if (height<-0.3){
                    TileArray[x][y] = new Tile (x, y, 10,10,13,true);
                } else {
                    height += (finefeaturesimplex.eval((x+playerloc[0]*20)*finefeaturescale, (y+playerloc[1]*10)*finefeaturescale)
                              +sharpfeaturesimplex.eval((x+playerloc[0]*20)*sharpfeaturescale, (y+playerloc[1]*10)*sharpfeaturescale))
                              *(localflatnesssimplex.eval((x+playerloc[0]*20)*localflatness, (y+playerloc[1]*10)*localflatness)+1)/2;
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
