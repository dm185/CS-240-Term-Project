import javafx.scene.image.*;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.FileInputStream;

import java.io.File;

//This class has the capiblity to load files from .jar and the hard drive if needed.
//Corrects any minor user input mistakes and will print out an error if anything goes wrong.
public class ResourceLoader{
    final static int EXIT_FAILURE = 1;
    final static String JAR_ROOT = "/";

    //Load .png, .jpg, .jpeg, .gif, .bmp files if needed.
    public static Image LoadImage(String file_path){
        return new Image(getFileStream(file_path));
    }

    //Load files like .txt
    public static InputStream LoadTextFile(String file_path){
        return getFileStream(file_path);
    }


    //Get a InputStream object to load data from .jar and HD files
    private static InputStream getFileStream(String file_path){
        //Avoids any issues with windows file names
        file_path = file_path.replace("\\", "/");

        final String CURRENT_DIRECTORY = "./";
        final int CHARS_TO_REMOVE = CURRENT_DIRECTORY.length();

        //JAR SUPPORT
        if(file_path.length() >= CHARS_TO_REMOVE) { //Make sure there is at least 2 chars
            //Check if string starts with "./"
            if(file_path.startsWith(CURRENT_DIRECTORY)){
                //Move characters to the left by 1 to remove '.' character
                file_path = file_path.substring(CHARS_TO_REMOVE);
            }
        }

        //This is the other form of relative paths. This will make the path absolute
        final String ROOT_DIR = JAR_ROOT;
        if(file_path.length() >= ROOT_DIR.length()){ //Make sure there is at least 1 char
            //If we do not have a direct path then make it one
            if(!file_path.startsWith(ROOT_DIR)){
                //Add '/' character before the file path
                file_path = ROOT_DIR + file_path;
            }
        }

        //Avoids any issues with windows file names
        file_path = file_path.replace("\\", "/");
        
        try{
            final String CLASS_NAME = "ResourceLoader"; //Used to get a Class object. Any class name will work as long as it is a valid class
            final Class LOADER = Class.forName(CLASS_NAME);
            if(LOADER == null){
                throw new ClassNotFoundException("ERR: Class named \"" + CLASS_NAME + "\" Was not defined!");
            }

            InputStream file_stream = LOADER.getResourceAsStream(file_path);
            if(file_stream == null){
                throw new FileNotFoundException(
                    "File resource \"" + file_path + "\" not found in \"" + System.getProperty("user.dir") + "\"!\n" +
                    "Please redownload the software or contact the devs");
            }

            //If everything went as planned this will run
            return file_stream;

        }catch (FileNotFoundException fException){
            System.out.println(fException);
        } catch(ClassNotFoundException cException){
            System.out.println(cException);
        }

        //If this point was reached then execution failed :(
        System.out.printf("Program now exiting ...\n");
        System.exit(EXIT_FAILURE);

        return null; //This line is needed to keep the compiler happy
    }
}
