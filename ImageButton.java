import javafx.scene.image.*;
import javafx.event.*;
import javafx.scene.input.*;
import javafx.scene.control.*;

/*
    HOW TO USE THIS CLASS
    All text contained inside of "[]" is explaining what each parameter does

                                         [Text displayed]     [File path to image]  [Image scale (aka. shrink/grow)]

    ImageButton premade = new ImageButton("Load Pre-made map", "./images/premade.png", SCALE,
             [Function to run when the button is pressed] (see Java lambadas for extended syntax or just copy and paste this)
             mouseClicked ->  {
                    System.out.printf("Loading Pre-Made map ...\n");
             }
    );
*/


/*
    Loads an image in memory using the 'ImageLoader' class and then
    sets up the object to be drawn to the screen. This class returns a object is used in DrawButton
    to draw Image Buttons to the screen. This also doubles as a 'Button' object if you specify the handle
    argument to be 'null' (aka. NO_CLICK_ACTION)
*/
public class ImageButton extends Button{    
    public ImageButton(String text, ClickableImage imageObject, EventHandler<MouseEvent> handle){
        super(text, imageObject);
        this.img = imageObject;
        
        if(handle != null){
            this.setOnMouseClicked(handle);
        }
    }
    
    public ImageButton(String text, ClickableImage imageObject){
        this(text, imageObject, NO_CLICK_ACTION);
    }
    
    
    //String mode overloads (MOST USEFUL)
    
    public ImageButton(String text, String image_path, double scale, EventHandler<MouseEvent> handle){
        this(text, new ClickableImage(image_path, scale, ClickableImage.NO_CLICK_ACTION), handle);
    }
    
    public ImageButton(String text, String image_path, double scale){
        this(text, image_path, scale, NO_CLICK_ACTION);
    }
    
    public ImageButton(String text, String image_path){
        //Load image into ram and register handle
        this(text, image_path, ORIGINAL_SIZE_SCALAR);
    }
    
    public ImageButton(String text, String image_path, EventHandler<MouseEvent> handle){
        //Load image into ram and register handle
        this(text, image_path, ORIGINAL_SIZE_SCALAR, handle);
    }
    
    static final EventHandler<MouseEvent> NO_CLICK_ACTION = null;
    public static final double ORIGINAL_SIZE_SCALAR = 1.0; // 5 * 1 = 5 so multiplying the size by 1 will just keep the orignal size
    
    static ClickableImage img; //Contains the image contained in the button
}


