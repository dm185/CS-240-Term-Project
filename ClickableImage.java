import javafx.scene.image.*;
import javafx.event.*;
import javafx.scene.input.*;

/*
    Loads an image in memory using the 'ResourceLoader' class and then
    sets up the object to be drawn to the screen. This class returns a object is used in DrawImage
    to draw images to the screen. This also doubles as a 'ImageView' object if you specify the handle
    argument to be 'null'
*/
public class ClickableImage extends ImageView{
    /*
        Constructor
            img/file_path - The image loaded from disk / Load image from specified file location
            scale - the scale that the image should be drawn at
            handle - function pointer to a function that handles what to do when the image is pressed.
                            Make this 'NO_CLICK_ACTION' if you do not want to do anything when the image is clicked
                            
            
            NOTE: Scale is optional. See other constructors for details
    */
    public ClickableImage(Image img, double scale, EventHandler<MouseEvent> handle) {
        super(img); //Call inherited constructor from ImageView
        
        //Resize image
        this.scale(scale);
        
        //Set the clicker handler
        if(handle != null){ //If the user does not specify a handle function then do not assign one
            this.setOnMouseClicked(handle);
        }
    }
    
    public ClickableImage(Image img, EventHandler<MouseEvent> handle){
        this(img, ORIGINAL_SIZE_SCALAR, handle);
    }

    //use this constructor to load from disk. Read full constructor above for argument details
    public ClickableImage(String file_path, double scale, EventHandler<MouseEvent> handle){
        this(ResourceLoader.LoadImage(file_path), scale, handle); //Load image from disk
    }
  
    public ClickableImage(String file_path, EventHandler<MouseEvent>handle){
       this(file_path, ORIGINAL_SIZE_SCALAR, handle);
    }

    
    /*
        Scale the width and height of the image to shrink or grow the image.
        
        Throws IllegalArgumentException if the new scale is less than or
        equal to zero.
    */
    public void scale(double new_scale){
        //Scale is not allowed to be 0 or negitive. That would mean that the width of the image would turn into a negitive number which is impossible
        if(new_scale <= 0){
            throw new IllegalArgumentException("Scale is either negitive or 0. This leads to undefined behavior/crash");
        }
        
        //Set width
        this.setPreserveRatio(true);
        final double NEW_WIDTH = ((double)this.getImage().getWidth()) * new_scale;
        final double NEW_HEIGHT = ((double)this.getImage().getHeight()) * new_scale;
        
        this.resize(NEW_WIDTH, NEW_HEIGHT);
    }
    
    /*
        Scale the width and height of the image to a specified new width and new height
        
        Throws IllegalArgumentException if the new width or height is less than or equal to zero
        equal to zero.
    */
    public void resize(double new_width, double new_height){
        if(new_width <= 0 || new_height <= 0){
            throw new IllegalArgumentException("new width/height is either negitive or 0. This leads to undefined behavior/crash");
        }
        this.setFitWidth(new_width);
        this.setFitHeight(new_height);
    }
  
    public static final double ORIGINAL_SIZE_SCALAR = 1.0; // 5 * 1 = 5 so multiplying the size by 1 will just keep the orignal size
    private static final double DEFAULT_POS = 0;
    
    public static final EventHandler<MouseEvent> NO_CLICK_ACTION = null;
}
