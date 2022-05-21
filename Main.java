import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.shape.Line;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text; 
import javafx.scene.paint.*;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JOptionPane;
import java.lang.Math;
import java.util.Random;

//NOTE: Most important code is in RoadNode.java and Connection.java


public class Main extends Application {
	private final int MIDX = 300;
    private final int STARTY = 100;
    private final int ENDY = STARTY + 350;
    private final float SPEED_LIMIT_1 = 50;
    private final float SPEED_LIMIT_2 = 40;
    private final int SCREENX = 600;
    private final int SCREENY = 600;
    private final Color DEFAULT_NODE_COLOR = Color.RED;
    private final Color DEFAULT_CHOSEN_NODE_COLOR = Color.GREEN;
	
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
              l.setStroke(c);
              
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
                     if(path.containsPair(n1, n2)) {
                            AddLine((int)n1.getX(), (int)n1.getY(), (int)n2.getX(), (int)n2.getY(), c);
                     }
              }
       }

       //Given the start and end location, generate a random map of roads
       public void makeRandomRoads(RoadNode start, RoadNode dest, int numberOfRoads) {
    	   ArrayList<RoadNode> roads = new ArrayList<RoadNode>();
    	   
    	   //create start and end nodes. Connected for testing purposes
    	   roads.add(start);
    	   DrawNode(roads.get(0), DEFAULT_NODE_COLOR);
    	   roads.add(dest);
    	   DrawNode(roads.get(1), DEFAULT_NODE_COLOR);
    	   roads.get(0).AddConnection(roads.get(1), SPEED_LIMIT_1);
    	   RoadNode roadMiddle = new RoadNode("middle", 100, 100);
    	   roads.add(roadMiddle);
    	   DrawNode(roads.get(2), DEFAULT_NODE_COLOR);
    	   
    	   
    	   //I think this is breaking it
    	   for (int i = 0; i<numberOfRoads; i++) {
    		   
    		   Random random = new Random(); //random boolean value; if true make left road, false make right road
    		   boolean left = random.nextBoolean(); //get value for left, will build left if true, right if false
    		   String roadname = String.valueOf(i); //roadname is just the value of i
    		   int x, y;        // x and y coordinates for the new road
    		   
    		   //build left if left is true, right if right is false
    		   if (left) {
    			   x = MIDX - (i+1)*50;
    			   y = STARTY + (i+1)*50;
    		   }else {
    			   x = MIDX + (i+1)*50;
    			   y = STARTY + (i+1)*50;
    		   }
    		   
    		   RoadNode road = new RoadNode(roadname, x, y);
    		   roads.add(road);
    		   DrawNode(roads.get(i+3), DEFAULT_NODE_COLOR);
    	   }

    	   
    	   //TODO: Generate code that will connect all the roads with speed assigned to each road
    	   //TODO: Draw nodes other than chosen nodes
    	   
    	   //roads.get(1).AddConnection(roads.get(2), SPEED_LIMIT_1);
    	   /*for (int i = 2; i>roads.size(); i++) {
    		   if(i>roads.size()-1) {
    		    roads.get(i).AddConnection(roads.get(i+1), SPEED_LIMIT_1);
    		   }
    	   }*/
    	   
    	   
    	   //draw shortest path between start and end node, start is indexed at 0, end indexed at 1
    	   Path path = roads.get(0).getShortestPath(roads.get(1));
    	   
    	   for(RoadNode node : path){ //Iterate through the list of chosen nodes, change color to green
               DrawChosenNode(node, path, DEFAULT_CHOSEN_NODE_COLOR);
           }
           
       }
       
       //Create a map of roads that have been pre-designed (Matthew's Roads)
       public void makeMap() {
    	 //Create the starting and ending positions
           RoadNode startnode = new RoadNode("Root", MIDX, STARTY);
           RoadNode end = new RoadNode("END", MIDX, ENDY);

           //Create right path
           RoadNode right1 = new RoadNode("R1", MIDX + 20 + 100, STARTY + 30);
           RoadNode right2 = new RoadNode("R2", MIDX + 40 + 100, STARTY - 10);
           RoadNode right3 = new RoadNode("R3", MIDX + 20 + 100, STARTY + 100);
	
           //Connect right path nodes together
           startnode.AddConnection(right1, SPEED_LIMIT_1);  //root ---> right1
           right1.AddConnection(right2, SPEED_LIMIT_1); //right1 ---> right2
           right2.AddConnection(right3, SPEED_LIMIT_1); //right2 ----> right3
           right3.AddConnection(end, SPEED_LIMIT_1);    //right3 ----> end
	
           //Create left path
           RoadNode left1 = new RoadNode("L1", MIDX - 50, STARTY + 50);
           RoadNode left2 = new RoadNode("L2", 450, 400);
           
           //Connect left path
           startnode.AddConnection(left1, SPEED_LIMIT_2);  //root ---> left1
           left1.AddConnection(left2, SPEED_LIMIT_2);     //left1 ---> end
           left2.AddConnection(end, SPEED_LIMIT_1);
           
           //Connect roads in middle for testing
           left1.AddConnection(right3, SPEED_LIMIT_1);
           
           
           //Draw all of the node regardless if they have been chosen
           DrawNode(startnode, DEFAULT_NODE_COLOR);
           DrawNode(right1, DEFAULT_NODE_COLOR);
           DrawNode(right2, DEFAULT_NODE_COLOR);
           DrawNode(right3, DEFAULT_NODE_COLOR);
           DrawNode(left1, DEFAULT_NODE_COLOR);
           DrawNode(left2,DEFAULT_NODE_COLOR); 
           DrawNode(end, DEFAULT_NODE_COLOR);
           
         //Solve for the shortest path between 'root' and 'end'
           Path path = startnode.getShortestPath(end);
           
           //Draw nodes that were chosen in a Green color
           for(RoadNode node : path){ //Iterate through the list of chosen nodes
                  DrawChosenNode(node, path, DEFAULT_CHOSEN_NODE_COLOR);
           }
       }

       //DEFINE WHAT TO DRAW HERE
       public void start(Stage primaryStage){
              this.root = new Group();

              //code to make a random map for testing purposes
              /*RoadNode startnode = new RoadNode("Root", MIDX, STARTY);
              RoadNode end = new RoadNode("END", MIDX, ENDY);
              makeRandomRoads(startnode, end, 5); */
              
              //code to draw Mathew's Map
              makeMap();
              
              //Draw to screen
              Scene scene = new Scene(this.root, SCREENX, SCREENY);
              
              primaryStage.setScene(scene);
              primaryStage.show();
       }
       
       
       public static void main(String[] args){
    	   
    	   
    	/* String name = JOptionPane.showInputDialog("Enter your name: ");
   		JOptionPane.showMessageDialog(null, "Hello " + name);
   		
   		int age = Integer.parseInt(JOptionPane.showInputDialog("Enter your age"));
   		JOptionPane.showMessageDialog(null, "You are " + age + " years old");
   		
   		double height = Double.parseDouble(JOptionPane.showInputDialog("Enter your height in feet with decimals"));
   		JOptionPane.showMessageDialog(null, "You are " + height + " feet tall");*/
    	   
          launch(args);
       }
};
