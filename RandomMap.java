import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

public class RandomMap {
	//map will be printed in an 800 by 800 screen. 
	private int screenSizeX = 800;
	private int screenSizeY = 800;
	//node colors
	private final Color DEFAULT_NODE_COLOR = Color.RED;
    private final Color DEFAULT_CHOSEN_NODE_COLOR = Color.GREEN;
	//each quadrant is like a set in a disjoint model
	private ArrayList<RoadNode> quadrant1 = new ArrayList<RoadNode>(); //upper left quadrant
	private ArrayList<RoadNode> quadrant2 = new ArrayList<RoadNode>(); //upper right quadrant
	private ArrayList<RoadNode> quadrant3 = new ArrayList<RoadNode>(); // lower left quadrant
	private ArrayList<RoadNode> quadrant4 = new ArrayList<RoadNode>(); // lower right quadrant	
	
	//Makes a randomMap. Splits screen into four quadrants, makes location in each quadrant like
	//a disjoint set, and then later will connect each quadrant together (union)
	public RandomMap() throws FileNotFoundException {
		makeRandomLocations(0, 400, 0, 400, this.quadrant1);
		makeRandomLocations(401, 800, 0, 400, this.quadrant2);
		makeRandomLocations(0, 400, 401, 800, this.quadrant3);
		makeRandomLocations(401, 800, 401, 800, this.quadrant4);
		System.out.println(quadrant1.toString());
		System.out.println(quadrant2.toString());
		System.out.println(quadrant3.toString());
		System.out.println(quadrant4.toString());
	}
	
	public void printMap() {
		AddCircle(100, 100, 100, DEFAULT_NODE_COLOR);
	}
	
	//will make a list of strings from a filename, for use with locations.txt and names.txt
	private static ArrayList<String> makeListFromTextFile(String filename) throws FileNotFoundException{
		ArrayList<String> listS = new ArrayList<String>();
		try {
		Scanner s = new Scanner(new File(filename));
		while (s.hasNextLine())
			listS.add(s.nextLine());		
		return listS;
		}catch(FileNotFoundException e) {
			System.out.println("FILE NOT FOUND");
		}
		return listS;
	}
	
	//generate a private integer between x and y
	private int randomInt(int x, int y) {		
		int min = x; 
		int max = y; 
		int randomNum = ThreadLocalRandom.current().nextInt(min, max + 1);
		return randomNum;
	}
	
	//generates a random name from names.txt(Bob) and locations.txt(library) to make "Bob's library) and return it
	private String randomLocationName() throws FileNotFoundException {
		ArrayList<String> listNames = makeListFromTextFile("names.txt");
		ArrayList<String> listLocations = makeListFromTextFile("locations.txt");
		String locationName;
		int nameElement, locationElement;
		nameElement = randomInt(0, listNames.size()-1);
		locationElement = randomInt(0, listLocations.size()-1);
		locationName = listNames.get(nameElement) + "'s " + listLocations.get(locationElement);
		return locationName;
	}
	
	//generates a random location within a quadrant and returns it
	//minX and maxX map out the minimum/maximum possible x coordinate
	//minY and maxY map out the minimum/maximum possible y coordinate
	private RoadNode makeLocationInQuadrant(int minX, int maxX, int minY, int maxY) throws FileNotFoundException {
		String locationName = randomLocationName(); //name the location
		int xCoordinate = randomInt(minX, maxX);    //generate the x value where location is plotted
		int yCoordinate = randomInt(minY, maxY);    //generate the y value where the location is plotted
		RoadNode newLocation = new RoadNode(locationName, xCoordinate, yCoordinate);
		return newLocation;
	}
	
	//create a list of roads in a quadrant
	private void makeRandomLocations(int minX, int maxX, int minY, int maxY, ArrayList<RoadNode> quadrant) throws FileNotFoundException{
		int minimumLocations = 3;
		int maximumLocations = 6;
		int randomNum = randomInt(minimumLocations, maximumLocations);
		for (int i = 0; i < randomNum; i++) {
			RoadNode newLocation = makeLocationInQuadrant(minX, maxX, minY, maxY);
			quadrant.add(newLocation);
		}
	}
	
	//helper functions to draw the map
	private void AddText(int x, int y, String text){
        Text t = new Text();
        t.setText(text);
        t.setX(x);
        t.setY(y);
	}
	
	private void AddCircle(int x, int y, int rad, Color c){
        Circle cir = new Circle(x, y, rad);
        	cir.setFill(c);
	}
	
	private void AddLine(int x1, int y1, int x2, int y2, Color c){
        Line l = new Line(x1, y1, x2, y2);
        l.setStroke(c);
	}
	
	private void DrawNode(RoadNode n, Color c){
        AddCircle((int)n.getX(), (int)n.getY(), 20, c);
        AddText((int)n.getX(), (int)n.getY() + 20, n.getName());
        
        for(Connection link : n.getLinks()) {
               RoadNode n1 = link.getSource();
               RoadNode n2 = link.getDest();
               AddLine((int)n1.getX(), (int)n1.getY(), (int)n2.getX(), (int)n2.getY(), c);
        }
	}
}

//TODO: delete this code when RandomMap() works
//Currently referencing this to work on code above, delete later
/*
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
	   }
	   
	   
	   //draw shortest path between start and end node, start is indexed at 0, end indexed at 1
	   Path path = roads.get(0).getShortestPath(roads.get(1));
	   
	   for(RoadNode node : path){ //Iterate through the list of chosen nodes, change color to green
        DrawChosenNode(node, path, DEFAULT_CHOSEN_NODE_COLOR);
    }
    
}*/
