import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Arrays;

public class Mapping {
    static BufferedImage[] Backgroundimages = setup.getTextureAtlas("res/tiles/TextureAtlasv20v20v.png");

    int colums = 20;
    int rows = 10;
    // 0 for water

    int ticksperupdate = 40;
    int ticks = 0;

    int playermapx = 0;
    int playermapy = 0;

    Areas[][] AreaArray;

    Mapping(int mapnumber){
        this.AreaArray = new Areas[colums][rows];

        if (mapnumber == 1){
            int i = 0;
            for(Areas[] c : AreaArray){
                int j = 0;
                for(Areas r: c){
                    AreaArray[i][j] = new Areas (i, j, 10,10,13);
                    j++;
                }
                i++;
            }
        }else{
            make_map(0,0);
        }
    }

    public void draw(Graphics g){
        ticks ++;
        for(Areas[] a : this.AreaArray){
            for(Areas areas: a){
                areas.draw(g);
                if (ticks >= ticksperupdate){
                    areas.changeState(areas.change, areas.lower_index, areas.upper_index);
                }
            }
        }
        if (ticks > ticksperupdate){
            ticks = 0;
        }

    }

    // Saves the current map. Format is index of background, specific background
    public void save(){
        String path = "res/tiles/Map/map" + this.playermapx + this.playermapy + ".txt";

        File myFile = new File(path);

        System.out.println(myFile);

        // Try block to check if exception occurs
        try {

            FileOutputStream fileout = new FileOutputStream("src/"+path);
            ObjectOutputStream out = new ObjectOutputStream(fileout);
            out.writeObject(this.AreaArray);

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
                myFile = new File("src\\res\\tiles\\Map\\map" + this.playermapx + this.playermapy + ".txt");
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
    public void make_map(int x, int y){
        File myFile = new File("src\\res\\tiles\\Map\\map" + x + y + ".txt");
        try {
            FileInputStream filein = new FileInputStream(myFile);
            ObjectInputStream in = new ObjectInputStream(filein);
            this.AreaArray = (Areas[][]) in.readObject();


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

    public static BufferedImage get_image(int index){
        return Backgroundimages[index];

    }

}