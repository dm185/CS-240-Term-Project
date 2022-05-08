import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.shape.Line;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text; 
import javafx.scene.paint.*;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

import java.util.LinkedList;


//NOTE: Most important code is in Node.java and Connection.java


public class Main extends Application {
       private Group root;
       
       //Helper functions to add things to the screen to display
       
       public void AddText(int x, int y, String text){
              Text t = new Text();
              t.setText(text);
              t.setX(x);
              t.setY(y);
              
              this.root.getChildren().add(t);
       }
       
       public void AddCircle(int x, int y, int rad, Color c){
              Circle cir = new Circle(x, y, rad);
              cir.setFill(c);
              
              this.root.getChildren().add(cir);
       }
       
       public void AddLine(int x1, int y1, int x2, int y2, Color c){
              Line l = new Line(x1, y1, x2, y2);
              l.setFill(c);
              
              this.root.getChildren().add(l);
       }
       
       public void DrawNode(RoadNode n, Color c){
              AddCircle((int)n.getX(), (int)n.getY(), 20, c);
              AddText((int)n.getX(), (int)n.getY() + 20, n.getName());
              
              for(Connection link : n.getLinks()) {
                     RoadNode n1 = link.getSource();
                     RoadNode n2 = link.getDest();
                     AddLine((int)n1.getX(), (int)n1.getY(), (int)n2.getX(), (int)n2.getY(), c);
              }
       }
       
       public void DrawChosenNode(RoadNode n, Path path, Color c){
              AddCircle((int)n.getX(), (int)n.getY(), 20, c);
              AddText((int)n.getX(), (int)n.getY() + 20, n.getName());
              
              for(Connection link : n.getLinks()) {
                     RoadNode n1 = link.getSource();
                     RoadNode n2 = link.getDest();
                     //Only print the path if it is chosen
                     if(path.contains(n1) && path.contains(n2)) {
                            AddLine((int)n1.getX(), (int)n1.getY(), (int)n2.getX(), (int)n2.getY(), c);
                     }
              }
       }


       //DEFINE WHAT TO DRAW HERE
       public void start(Stage primaryStage){
              this.root = new Group();
       
              final int MIDX = 150;
              final int STARTY = 25;
              final int ENDY = STARTY + 350;
              final float SPEED_LIMIT_1 = 50;
              final float SPEED_LIMIT_2 = 40;
       
              //Create the starting and ending postions
              RoadNode startnode = new RoadNode("Root", MIDX, STARTY);
              RoadNode end = new RoadNode("END", MIDX, ENDY);
	
              //Create right path
              RoadNode right1 = new RoadNode("R1", MIDX + 20 + 100, STARTY + 30);
              RoadNode right2 = new RoadNode("R2", MIDX + 40 + 150, STARTY - 10);
              RoadNode right3 = new RoadNode("R3", MIDX + 20 + 100, STARTY + 100);
	
              //Connect right path nodes together
              startnode.AddConnection(right1, SPEED_LIMIT_1);  //root ---> right1
              right1.AddConnection(right2, SPEED_LIMIT_1); //right1 ---> right2
              right2.AddConnection(right3, SPEED_LIMIT_1); //right2 ----> right3
              right3.AddConnection(end, SPEED_LIMIT_1);    //right3 ----> end
	
              //Create left path
              RoadNode left1 = new RoadNode("L1", MIDX - 50, STARTY + 50);
              
              //Connect left path
              startnode.AddConnection(left1, SPEED_LIMIT_2);  //root ---> left1
              left1.AddConnection(end, SPEED_LIMIT_2);     //left1 ---> end
              
              //Solve for the shortest path between 'root' and 'end'
              Path path = startnode.getShortestPath(end);
              
              //Draw all of the node regardless if they have been chosen
              final Color DEFAULT_NODE_COLOR = Color.RED;
              DrawNode(startnode, DEFAULT_NODE_COLOR);
              DrawNode(right1, DEFAULT_NODE_COLOR);
              DrawNode(right2, DEFAULT_NODE_COLOR);
              DrawNode(right3, DEFAULT_NODE_COLOR);
              DrawNode(left1, DEFAULT_NODE_COLOR);
              DrawNode(end, DEFAULT_NODE_COLOR);
              
              //Draw nodes that were chosen in a Green color
              final Color DEFAULT_CHOSEN_NODE_COLOR = Color.GREEN;
              for(RoadNode node : path){ //Iterate through the list of chosen nodes
                     DrawChosenNode(node, path, DEFAULT_CHOSEN_NODE_COLOR);
              }
              
              
              //Draw to screen
              Scene scene = new Scene(this.root, 400, 400);
              
              primaryStage.setScene(scene);
              
              primaryStage.show();
       }
       
       public static void main(String[] args){
              launch(args);
       }
};
