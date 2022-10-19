import java.awt.*;
import java.awt.image.BufferedImage;

public class Mapping {
    BufferedImage[][] Backgroundimages = new BufferedImage[][]{
            setup.get2by2("res/tiles/Watertiles.png"),
            setup.get2by2("res/tiles/Waterbeachflat.png"),
            setup.get2by2("res/tiles/Waterbeachcorner.png")};

    int colums = 20;
    int rows = 10;
    // 0 for water

    int ticksperupdate = 40;
    int ticks = 0;

    Areas[][] AreaArray;

    Mapping(int mapnumber){
        this.AreaArray = new Areas[colums][rows];

        if (mapnumber == 1){
            int i = 0;
            for(Areas[] c : AreaArray){
                int j = 0;
                for(Areas r: c){
                    AreaArray[i][j] = new Areas (Backgroundimages[0], i, j);
                    j++;
                }
                i++;
            }
        }else if(mapnumber == 2){
            int i = 0;
            for(Areas[] c : AreaArray) {
                int j = 0;
                for (Areas r : c) {
                    AreaArray[i][j] = new Areas(Backgroundimages[0], i, j);
                    j++;
                }
                i++;
            }
        }

    }

    public void draw(Graphics g){
        ticks ++;
        for(Areas[] a : this.AreaArray){
            for(Areas areas: a){
                areas.draw(g);
                if (ticks >= ticksperupdate){
                    areas.changeState();
                }
            }
        }
        if (ticks > ticksperupdate){
            ticks = 0;
        }

    }

}