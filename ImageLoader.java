import javafx.scene.image.*;
import java.io.FileNotFoundException;
import java.io.FileInputStream;

public class ImageLoader{
    public static Image load(String file_path){
        try{
            return new Image(new FileInputStream(file_path)); //Load image from disk
        } catch(FileNotFoundException e){
            printErrorAndExit(file_path);
            return null; //This line is needed to keep the compiler happy
        }
    }
    
    
    private static void printErrorAndExit(String invalid_file){
        final int EXIT_FAILURE = 1;
            
        System.out.printf("ERR: Image resource \"%s\" was not found!\n", invalid_file);
        System.out.printf("Due to this fatal issue, the program will now clean up and end.\n");
        System.out.printf("Please redownload the program to fix this issue\n");
        System.out.printf("Exiting ...\n");
            
        System.exit(EXIT_FAILURE);
    }
}
