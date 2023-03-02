package server;

import shared.OpenSimplexNoise;
import shared.StaticOptions;
import shared.Tile;
import java.util.Random;

public class MapGenerator {
    
    public Tile[][] generateMap(int cx, int cy, long seed){

        Tile[][] TileArray = new Tile[StaticOptions.CHUNKSIZE][StaticOptions.CHUNKSIZE];
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

        double scale             = 0.5;
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
                
                double height = watersimplex.eval((x*StaticOptions.CHUNKSIZE)*heightscale, (y*StaticOptions.CHUNKSIZE)*heightscale);
                //generate water
                if (height<-0.3){
                    TileArray[x][y] = new Tile (x, y, 10,10,13,true);
                } else {
                    height += (finefeaturesimplex.eval((x+cx*20)*finefeaturescale, (y+cy*10)*finefeaturescale)
                              +sharpfeaturesimplex.eval((x+cx*20)*sharpfeaturescale, (y+cy*10)*sharpfeaturescale))
                              *(localflatnesssimplex.eval((x+cx*20)*localflatness, (y+cy*10)*localflatness)+1)/2;
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
        return TileArray;
    }
}
