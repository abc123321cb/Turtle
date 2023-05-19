package shared;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.util.Map;

public class Music {
    /*
    current music numbers
    0:basic background music made with easy :)
     */
    private static Map<Integer,String> map = Map.of(0, "src/shared/resources/music/backgroundmusic.wav",1,"src/res/music/lock.wav");

    /*
    current song playing
     */
    private static Clip song;

    /*
    overrides the current song playing
     */
    public static void play(int n){
        try {
            song = AudioSystem.getClip();
            song.open(AudioSystem.getAudioInputStream(new File(map.get(n)).getAbsoluteFile()));
            song.start();
            song.loop(Clip.LOOP_CONTINUOUSLY);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    /*
    leaves the current song playing and overlaps this for things like damage sound effects.
     */
    public static void effect(){

    }


}
