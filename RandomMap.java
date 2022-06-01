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
//The following class can generate a RandomMap. A random map is a map of randomly generated roadnodes that all connect. A user can
//pick a start and end location of the map, and then route directions between two locations. 


//TODO: edit makeRandomLocations() and makeLocationInQuadrant() to have more consistent spread and better looking maps
//      randomly generate speed limits for makeRoads() function

public class RandomMap {
	//assuming map will be printed in a 900x900 screen. unused so far, might be deleted later 
	private int screenSizeX = 900;
	private int screenSizeY = 900;
	//node colors
	private final Color DEFAULT_NODE_COLOR = Color.RED;
    private final Color DEFAULT_CHOSEN_NODE_COLOR = Color.GREEN;
    //road speed limits
    private final float SPEED_LIMIT_1 = 50;
    private final float SPEED_LIMIT_2 = 40;
	//each quadrant is like a set in a disjoint model
	private ArrayList<RoadNode> quadrant1 = new ArrayList<RoadNode>(); //upper left quadrant
	private ArrayList<RoadNode> quadrant2 = new ArrayList<RoadNode>(); //upper right quadrant
	private ArrayList<RoadNode> quadrant3 = new ArrayList<RoadNode>(); // lower left quadrant
	private ArrayList<RoadNode> quadrant4 = new ArrayList<RoadNode>(); // lower right quadrant	
	
	//TODO: delete println after done testing
	//Makes a randomMap. Splits screen into four quadrants, makes location in each quadrant like
	//a disjoint set, and then later will connect each quadrant together (union/merge)
	public RandomMap() throws FileNotFoundException {
		makeRandomLocations(50, 450, 50, 450, this.quadrant1); 
		makeRoads(quadrant1);
		makeRandomLocations(451, 850, 50, 450, this.quadrant2);
		makeRoads(quadrant2);
		makeRandomLocations(50, 450, 451, 850, this.quadrant3);
		makeRoads(quadrant3);
		makeRandomLocations(451, 850, 451, 850, this.quadrant4);
		makeRoads(quadrant4);
		//these print lines are for testing purposes to show how many locations
		//are printed in each quadrant
		System.out.println(quadrant1.toString()); 
		System.out.println(quadrant2.toString()); 
		System.out.println(quadrant3.toString());
		System.out.println(quadrant4.toString());
		mergeQuadrants(quadrant1, quadrant2, quadrant3, quadrant4);
	}
	
	//This function makes roads connecting the four quadrants to each other, and then
	//merges all of them into quadrant1
	private void mergeQuadrants(ArrayList<RoadNode> quadrantA, ArrayList<RoadNode> quadrantB, 
		ArrayList<RoadNode> quadrantC, ArrayList<RoadNode> quadrantD) {
		int randElementA = randomInt(0, quadrantA.size());
		int randElementB = randomInt(0, quadrantB.size());
		int randElementC = randomInt(0, quadrantC.size());
		int randElementD = randomInt(0, quadrantD.size());
		quadrantA.get(randElementA).AddConnection(quadrantB.get(randElementB), SPEED_LIMIT_1);
		quadrantB.get(randElementB).AddConnection(quadrantC.get(randElementC), SPEED_LIMIT_1);
		quadrantC.get(randElementC).AddConnection(quadrantD.get(randElementD), SPEED_LIMIT_1);
		quadrantC.get(randElementC).AddConnection(quadrantA.get(randElementA), SPEED_LIMIT_1);
		quadrantD.get(randElementD).AddConnection(quadrantA.get(randElementA), SPEED_LIMIT_1);
		quadrantD.get(randElementD).AddConnection(quadrantB.get(randElementB), SPEED_LIMIT_1);
		quadrantA.addAll(quadrantB);
		quadrantA.addAll(quadrantC);
		quadrantA.addAll(quadrantD);
	}
	
	//These functions are used to help draw the map in the Main method. Because all the quadrants get
	//merged into quadrant1, we never need to get information from quadrants 2-4
	public ArrayList<RoadNode> getQuadrant1(){
		return quadrant1;
	}
	
	public int getQuadrant1Size() {
		return quadrant1.size();
	}

	
	//make roads in a quadrant
	private void makeRoads(ArrayList<RoadNode> locations) {
		for (int i = 0; i<locations.size()-1; i++) {
			locations.get(i).AddConnection(locations.get(i+1), SPEED_LIMIT_1);
		}
		locations.get(0).AddConnection(locations.get(locations.size()-1), SPEED_LIMIT_1);
	}
	
	//will make a list of strings from a filename, for use with locations.txt and names.txt
	private static ArrayList<String> makeListFromTextFile(String filename) throws FileNotFoundException{	
		ArrayList<String> listS = new ArrayList<String>();
		Scanner s = new Scanner(ResourceLoader.LoadTextFile(filename));
		while (s.hasNextLine())
			listS.add(s.nextLine());		
		return listS;
	}
	
	//generate a private integer between x and y
	private int randomInt(int x, int y) {		
		int min = x; 
		int max = y; 
		int randomNum = ThreadLocalRandom.current().nextInt(min, max);
		return randomNum;
	}
	
	//generates a random name from names.txt() and locations.txt() to make a location name and return it
	private String randomLocationName() throws FileNotFoundException {
		ArrayList<String> listNames = makeListFromTextFile("names.txt");
		ArrayList<String> listLocations = makeListFromTextFile("locations.txt");
		String locationName;
		int nameElement, locationElement;
		nameElement = randomInt(0, listNames.size()-1);
		locationElement = randomInt(0, listLocations.size());
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
			int number = i+1;
			RoadNode newLocation = makeLocationInQuadrant(minX, maxX, minY, maxY);
			System.out.println(newLocation.toString());
			quadrant.add(newLocation);
		}
	}
}

