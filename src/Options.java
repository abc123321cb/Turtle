import java.io.*;
import java.util.*;

public class Options {
        //declare all static variables
        public static final String root = System.getProperty("user.dir")+"\\src\\";



    public static void refreshOptions(){
        File file = new File(root+"options.txt");
        try (Scanner scan = new Scanner(file)) {
            











            scan.close();
        } catch (FileNotFoundException e) {
            System.out.println("Turtle game : Unable to locate options.txt");
            e.printStackTrace();
        }
    }
}






/* Notes for later
 * we can get general user key input using KeyPress class for key re-assignment
 * 
 * 
 */