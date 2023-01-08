import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.*;

public class Utility {

    // Takes Image and angle returns image roated by angle
    public static BufferedImage rotate(BufferedImage image, Double angle) {
        double angleYComponent = Math.abs(Math.sin(Math.toRadians(angle))),
                angleXComponent = Math.abs(Math.cos(Math.toRadians(angle)));
        int width = image.getWidth(),
                height = image.getHeight();
        int newwidth = (int) (width * angleYComponent + height * angleXComponent),
                newheight = (int) (height * angleYComponent + width * angleXComponent);
        BufferedImage rotated = new BufferedImage(newwidth, newheight, image.getType());
        Graphics2D graphic = rotated.createGraphics();
        graphic.translate((newwidth - width) / 2, (newwidth - newheight) / 2);
        graphic.rotate(Math.toRadians(angle), width / 2, newheight / 2);
        graphic.drawRenderedImage(image, null);
        graphic.dispose();
        return rotated;
    }

    // takes sprite atlas and breaks it up into sections depending on the name of
    // the file
    public static BufferedImage[] getTextureAtlasBasic(String file,int textureWidth, int textureHight) {
        try {

            BufferedImage image;
            image = ImageIO.read(setup.class.getResourceAsStream(file));
            int atlasWidth = image.getWidth(null);
            int atlasHeight = image.getHeight(null);
            int texturesPerRow = atlasWidth / textureWidth;
            int texturesPerColumn = atlasHeight / textureHight;
            BufferedImage[] imglist = new BufferedImage[texturesPerRow * texturesPerColumn];
            System.out.println(texturesPerRow);
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
    //this function is used for prototyping only, dont use in final game
    public static BufferedImage[] getTextureAtlasPrototype(String file,int textureWidth, int textureHight, int atlasWidth, int atlasHeight ) {
        try {

            BufferedImage image;
            image = ImageIO.read(setup.class.getResourceAsStream(file));
            int texturesPerRow = atlasWidth / textureWidth;
            int texturesPerColumn = atlasHeight / textureHight;
            System.out.println("texturesPerRow : "+texturesPerRow);
            System.out.println("texturesPerColumn : "+texturesPerColumn);
            BufferedImage[] imglist = new BufferedImage[texturesPerRow * texturesPerColumn];
            System.out.println(texturesPerRow);
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

}
