import javafx.scene.Group;
import javafx.scene.Scene;

import javafx.scene.shape.*;
import javafx.scene.control.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.*;
import javafx.scene.text.Text;
import javafx.scene.image.*;

/*
       The draw singleton that manages drawing things to the screen
*/
public class Drawer{

       private Drawer(){
              objectsDrawn = new Group();
              screen = new Scene(objectsDrawn, this.SCREEN_W, this.SCREEN_H);
       }
       
       private static Drawer get(){
              //Only create an instace of draw if it is not made
              if(only_allowed_instance == null){
                     only_allowed_instance = new Drawer();
              }
              
              return only_allowed_instance;
       }
       
       //Draw Text
       static public void DrawString(String text, Number x, Number y){
              if(text == null)
                     return;

              Text t = new Text();
              t.setText(text);
              t.setX(x.doubleValue());
              t.setY(y.doubleValue());
              
              Drawer.get().objectsDrawn.getChildren().add(t);
       }
       
       
       //Button Drawing Code
       static public void DrawButton(Button b, Number x, Number y){
              b.setTranslateX(x.doubleValue());
              b.setTranslateY(y.doubleValue());
              
              Drawer.get().objectsDrawn.getChildren().add(b);
       }
       
       static public void DrawButton(ImageButton b, Number x, Number y){
              DrawButton((Button)b, x, y);
       }
       
       /*
              Draw a 90* curve from {x1, y1} to {x2, y2}
              Used for drawing roads that turn into a curve
       */
       static public void Draw90Curve(Number startX, Number startY, Number endX, Number endY, Color c){
              final double CONTROL_X = startX.doubleValue();
              final double CONTROL_Y = endY.doubleValue();
              
              QuadCurve q = new QuadCurve(startX.doubleValue(), startY.doubleValue(), CONTROL_X, CONTROL_Y, endX.doubleValue(), endY.doubleValue());
              
              //Visual options
              q.setFill(Color.TRANSPARENT);
              q.setStroke(c);
              
              Drawer.get().objectsDrawn.getChildren().add(q);
       }
       
       /*
              Draw circle at x,y with color 'c'. If wireframe = true then the center will be trasnparent
              and the color 'c' will instead be used for the outline of the circle.
              Used for drawing roundabouts
       */
       static public void DrawCircle(Number x, Number y, Number rad, Color c, boolean wireframe){
              Circle cir = new Circle(x.doubleValue(), y.doubleValue(), rad.doubleValue());
              
              if(wireframe){
                     //Wireframe mode
                     cir.setFill(Color.TRANSPARENT);
                     cir.setStroke(c);
              } else {
                     //Fill mode
                     cir.setFill(c);
              }
              
              Drawer.get().objectsDrawn.getChildren().add(cir);
       }
       
       static public void DrawCircle(Number x, Number y, Number rad, Color c){
              Drawer.get().DrawCircle(x, y, rad, c, false);
       }
       
       static public <T extends Number> void DrawLine(T x1, T y1, T x2, T y2, Color c){
              Line l = new Line(x1.doubleValue(), y1.doubleValue(), x2.doubleValue(), y2.doubleValue());
              l.setStroke(c);
              
              Drawer.get().objectsDrawn.getChildren().add(l);
       }
       
       static public <T extends Number> void DrawLine(T x1, T y1, T x2, T y2){
              Drawer.get().DrawLine(x1, y1, x2, y2, Color.BLACK);
       }

       static public void DrawNodeConnection(RoadNode n, Color c){
              for(Connection link : n.getLinks()) {
                     RoadNode n1 = link.getSource();
                     RoadNode n2 = link.getDest();
                     DrawLine((int)n1.getX(), (int)n1.getY(), (int)n2.getX(), (int)n2.getY(), c);
              }
       }

       static public void DrawNodeConnection(RoadNode n){
              DrawNodeConnection(n, Drawer.DEFAULT_NODE_COLOR);
       }

       static public void DrawChosenNodeConnection(RoadNode n, Path path, Color c){
              for(Connection link : n.getLinks()) {
                     RoadNode n1 = link.getSource();
                     RoadNode n2 = link.getDest();
                     //Only print the path if it is chosen
                     if(path.containsPair(n1, n2)) {
                            DrawLine((int)n1.getX(), (int)n1.getY(), (int)n2.getX(), (int)n2.getY(), c);
                     }
              }
       }

       static public void DrawChosenNodeConnection(RoadNode n, Path path){
              DrawNodeConnection(n, Drawer.DEFAULT_CHOSEN_NODE_COLOR);
       }
       
       static public void DrawChosenNode(RoadNode n, Path path, Color c){
              if(n.getName() != null) { //Only show point if it has a name
                     DrawCircle((int)n.getX(), (int)n.getY(), 3, c, false);
                     DrawString(n.getName(), (int)n.getX(), (int)n.getY() + 10);
              }
              
              DrawChosenNodeConnection(n, path, c);
       }
       
       static public void DrawNode(RoadNode n, Color c){
              if(n.getName() != null) {
                     DrawCircle((int)n.getX(), (int)n.getY(), 3, c, false);
                     DrawString(n.getName(), (int)n.getX(), (int)n.getY() + 10);
              }
              
              DrawNodeConnection(n, c);
       }
       
       static public void DrawChosenNode(RoadNode n, Path path){
              Drawer.get().DrawChosenNode(n, path);
       }
       
       static public void DrawNode(RoadNode n, Path path){
              Drawer.get().DrawNode(n, path);
       }
       
       /*
              Draws a image object to the screen. To get a image object, call the LoadImage() function.
              Set scale to ClickableImage.ORIGINAL_SIZE_SCALAR (aka. 1.0) to make the image appear with the
              orignal width and height in the image file
       */
       static public <T extends Number> void DrawImage(Image img, T x, T y, T scale){
              ClickableImage imageView = new ClickableImage(img, ClickableImage.NO_CLICK_ACTION);
              
              DrawImage(imageView, x, y, scale);
       }
       
       static public <T extends Number> void DrawImage(Image img, T x, T y){
              DrawImage(img, x, y, ClickableImage.ORIGINAL_SIZE_SCALAR);
       }
       
       //Overloads for ImageView type
       static public <T extends Number> void DrawImage(ClickableImage imgRenderer, T x, T y, T scale){
              //Set arguments
              imgRenderer.setX(x.doubleValue());
              imgRenderer.setY(y.doubleValue());
              
              //Set widths
              imgRenderer.scale(scale.doubleValue());
              
              DrawImage(imgRenderer);
       }
       
       static public <T extends Number> void DrawImage(ClickableImage imgRenderer, T x, T y){
              DrawImage(imgRenderer, x, y, ClickableImage.ORIGINAL_SIZE_SCALAR);
       }
       
       static public void DrawImage(ClickableImage imgRenderer){
              Drawer.get().objectsDrawn.getChildren().add(imgRenderer);
       }
       
      
       
       static public void Clear(){
              Drawer.get().objectsDrawn.getChildren().clear();
       }
       
       static public void ClearScreen(){
              Drawer.get().Clear();
       }
       
       //Non-drawing functions
       static public Scene getScreen(){
              return Drawer.get().screen;
       }
       
       //Contants
       public static final int SCREEN_W = 900;
       public static final int SCREEN_H = 900;
       public static final Color DEFAULT_NODE_COLOR = Color.RED;
       public static final Color DEFAULT_CHOSEN_NODE_COLOR = Color.GREEN;
       public static final Color DEFAULT_ROAD_NODE_COLOR = Color.DARKMAGENTA;
       public static final Color DEFAULT_CLEAR_COLOR = Color.TRANSPARENT;

       //Private member data used to draw things to the screen.
       private Scene screen;
       private Group objectsDrawn;
       
       //Singleton data that is required
       private static Drawer only_allowed_instance;
};
