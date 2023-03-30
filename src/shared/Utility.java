package shared;

import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;

public class Utility {

    

    // Takes Image and angle returns image roated by angle
    public static BufferedImage rotate(BufferedImage image, Double angle) {
        double angleYComponent = Math.abs(Math.sin(angle)),
                angleXComponent = Math.abs(Math.cos(angle));
        int width = image.getWidth(),
                height = image.getHeight();
        int newwidth = (int) (width * angleYComponent + height * angleXComponent),
                newheight = (int) (height * angleYComponent + width * angleXComponent);
        BufferedImage rotated = new BufferedImage(newwidth, newheight, image.getType());
        Graphics2D graphic = rotated.createGraphics();
        graphic.translate((newwidth - width) / 2, (newwidth - newheight) / 2);
        graphic.rotate(angle, width / 2, newheight / 2);
        graphic.drawRenderedImage(image, null);
        graphic.dispose();
        return rotated;
    }

    // takes sprite atlas and breaks it up into sections depending on the name of
    // the file
    public static BufferedImage[] getTextureAtlasBasic(String file,int textureWidth, int textureHight) {
        try {

            BufferedImage image;
            image = ImageIO.read(Utility.class.getResourceAsStream(file));
            int atlasWidth = image.getWidth(null);
            int atlasHeight = image.getHeight(null);
            int texturesPerRow = atlasWidth / textureWidth;
            int texturesPerColumn = atlasHeight / textureHight;
            BufferedImage[] imglist = new BufferedImage[texturesPerRow * texturesPerColumn];
            for (int y = 0; y < texturesPerColumn; y++) {
                for (int x = 0; x < texturesPerRow; x++) {
                    imglist[x + y * texturesPerRow] = image.getSubimage(x * textureWidth, y * textureHight,
                            textureWidth, textureHight);
                }
            }
            return imglist;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new BufferedImage[0];
    }

    //Generates random number between 2 values
    public static int getRandom(int min, int max) {
        return (int)(min+Math.random()*(max-min));
    }


    // need to use this instead in some places
    // given x and chunkx and player.chunkx or coorsponding variables for other dimensions
    public static int getPixels(double n, int chunk1, int playerChunk){
        return (int)((FinalOptions.CHUNKSIZE*(chunk1-playerChunk)+n)*Options.TILE_SIZE);
    }

    public static boolean rectCollide(double x1, double y1, double width1, double height1, double x2, double y2, double width2, double height2) {
        return ((x1 > x2 && x2 + width2 > x1) || (x2 > x1 && x1 + width1 > x2))
            && ((y1 > y2 && y2 + height2 > y1) || (y2> y1 && y1 + height1 >y2));
    }

    public static void Runner(ArrayList<?> o){

        ArrayList n = o;
        ArrayList<Loopers> a = n;

        ArrayList<Integer> IndexestoRemove = new ArrayList<Integer>();
        for(int i = 0; i < o.size();i++){
            if(a.get(i).move())
                IndexestoRemove.add(i);
        }
        IndexestoRemove.sort(Comparator.reverseOrder());
        for(int i=0; i<IndexestoRemove.size();i++){
            a.remove((int)IndexestoRemove.get(i));
        }

    }

    // update things to use this and
    public static boolean onScreen(int x, int y, int width, int height, int xoffset, int yoffset){
        return true;
    }

}
