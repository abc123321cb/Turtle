import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.BreakIterator;
import java.util.Arrays;

public class Mapping {
    static BufferedImage[] Backgroundimages = setup.getTextureAtlas("res/tiles/TextureAtlasv20v20v.png");

    int colums = 20;
    int rows = 10;

    int ticksperupdate = 40;
    int ticks = 0;

    int playermapx = 0;
    int playermapy = 1;

    Areas[][] AreaArray;

    Mapping(int mapnumber, int playermapx, int playermapy){
        this.AreaArray = new Areas[colums][rows];
        this.playermapx = playermapx;
        this.playermapy = playermapy;

        switch (mapnumber){
            case 1:
                int i = 0;
                for(Areas[] c : AreaArray){
                    int j = 0;
                    for(Areas r: c){
                        AreaArray[i][j] = new Areas (i, j, 10,10,13,true);
                        j++;
                    }
                    i++;
                } 
            break;
            case 2:
                make_map(this.playermapx,this.playermapy);
            break;
            case 3:
                generateMap(0,this.playermapx,this.playermapy);
            break;
        }
    }

    // moves the current index range forward
    public void update(int colum, int row){
        Areas area = AreaArray[colum][row];
        System.out.println(setup.TEXTUREGROUPS.get(0)[0]);

        int index = -1;
        for(int i = 0; i < setup.TEXTUREGROUPS.size(); i++){
            if (Arrays.equals(new int[]{area.lower_index, area.upper_index, (area.change) ? 1 : 0}, setup.TEXTUREGROUPS.get(i))){
                index = i;
                break;
            }
        }
        if (!(index==-1)){
            index++;
            index = (index==setup.TEXTUREGROUPS.size())? 0 : index;
            System.out.println(index);
            area.lower_index = setup.TEXTUREGROUPS.get(index)[0];
            area.upper_index = setup.TEXTUREGROUPS.get(index)[1];
            area.change = setup.TEXTUREGROUPS.get(index)[2] == 1;
            area.changeState(true, area.lower_index, area.upper_index);
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

    public void mousepressed(MouseEvent a){
        int x = a.getX();
        int y = a.getY();
        System.out.println(x + " " + y);
        int colum = x/50;
        int row = y/50;
        update(colum, row);
    }


    public static BufferedImage get_image(int index){
        return Backgroundimages[index];

    }

    public void generateMap(int seed,int playerx,int playery){
        OpenSimplexNoise simplex = new OpenSimplexNoise(seed);
        for(int x=0; x<20; x++){
            for(int y=0; y<10; y++){
                double scale = 0.1;
                double texture = simplex.eval((x+playerx*20)*scale, (y+playery*10)*scale);
                System.out.println(texture);
                if (texture<-0.3){
                    AreaArray[x][y] = new Areas (x, y, 10,10,13,true);
                }else if(texture<0){
                    AreaArray[x][y] = new Areas (x, y, 20,20,23,false);
                    AreaArray[x][y].changeState(true, 20, 23);
                    AreaArray[x][y].change = false;
                }else if(texture<0.2){
                    AreaArray[x][y] = new Areas (x, y, 30,30,33,false);
                    AreaArray[x][y].changeState(true, 30, 33);
                    AreaArray[x][y].change = false;
                }else if(texture<0.4){
                    AreaArray[x][y] = new Areas (x, y, 40,40,43,false);
                    AreaArray[x][y].changeState(true, 40, 43);
                    AreaArray[x][y].change = false;
                }else{
                    AreaArray[x][y] = new Areas (x, y, 50,50,53,false);
                    AreaArray[x][y].changeState(true, 50, 53);
                    AreaArray[x][y].change = false;  
                }
            }
        }
    }
}