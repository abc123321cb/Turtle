import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Map {
    BufferedImage[][] Backgroundimages = new BufferedImage[][]{
            setup.get2by2("res/tiles/Watertiles.png"),
            setup.get2by2("res/tiles/Waterbeachflat.png"),
            setup.get2by2("res/tiles/Waterbeachcorner.png")};

    int colums = 20;
    int rows = 10;
    // 0 for water

    int ticksperupdate = 40;
    int ticks = 0;

    int playermapx = 0;
    int playermapy = 0;


    Areas[][] AreaArray;

    //ArrayList<Areas> AreaArray = ;

    Map(int mapnumber){
        Integer[][] BackgroundArray = new Integer[colums][rows];
        this.AreaArray = new Areas[colums][rows];

        if (mapnumber == 1){
            int i = 0;
            for(Areas[] c : AreaArray){
                int j = 0;
                for(Areas r: c){
                    AreaArray[i][j] = new Areas (Backgroundimages[0], i, j, (int)(Math.random()*4), true);
                    j++;
                }
                i++;
            }
        }else {
            make_map(this.playermapx, this.playermapy);
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

    // Saves the current map. Format is index of background, specific background
    public void save(){
        String path = "res/Map/map" + this.playermapx + this.playermapy + ".txt";

        File myFile = new File(path);

        System.out.println(myFile);

        String save_data;

        save_data = this.encode();

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
                myFile = new File("src\\res\\Map\\map" + this.playermapx + this.playermapy + ".txt");
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

    // Helps save map
    public String encode(){
        StringBuilder encoded = new StringBuilder();
        for (Areas[] areacolumns : this.AreaArray) {
            for(Areas area : areacolumns){
                int a = Arrays.asList(Backgroundimages).indexOf(area.background);
                encoded.append(Arrays.asList(Backgroundimages).indexOf(area.background));
                encoded.append(",");
                encoded.append(area.change);
                encoded.append(",");
                encoded.append(area.start_image);
                encoded.append(";\n");
            }
        }
        return encoded.toString();

    }

    // get data from map
    public void make_map(int x, int y){
        File myFile = new File("src\\res\\Map\\map" + x + y + ".txt");
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

}