package shared;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;

public class FinalOptions {
    public static final BufferedImage[] TextureAtlas = Utility.getTextureAtlasBasic("resources/tiles/TextureAtlasv20v20v.png",20,20);
    public static final int CHUNKSIZE = 50;
    
    // has texture groups start index, stop index, change
    public static final ArrayList<int[]> TEXTUREGROUPS = new ArrayList<int[]>(
            Arrays.asList(new int[] { 10, 13, 1 }, new int[] { 20, 23, 0 }, new int[] { 30, 33, 0 },
                    new int[] { 40, 43, 0 }, new int[] { 50, 53, 0 }));
}
