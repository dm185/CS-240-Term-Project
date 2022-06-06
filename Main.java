import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.shape.*;
import javafx.scene.text.Text; 
import javafx.scene.paint.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.event.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.image.*;
import javafx.scene.input.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JOptionPane;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.Math;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

//NOTE: Most important code is in RoadNode.java and Connection.java


public class Main extends Application {
       
       /*
              Load image from HardDrive and return the Image object.
       */
       public Image LoadImage(String file_path) {
              return ResourceLoader.LoadImage(file_path);
       }
    
    static private final int SCREENX = 900;
    static private final int SCREENY = 900;
    static private int start = 0;
	static private int destination = 11;
    
    //used to make icons in the GUI
    static final String PREMADE_MAP_ICON_PATH = "./premade.png";
    static final String RANDOM_MAP_ICON_PATH = "./random.png";
    static final String EXIT_ICON_PATH = "./exit.png";
    static final double SCALE = 0.20;
    static final int CENTERX = SCREENX / 2;
    static final int CENTERY = SCREENY / 2;
    static final double SPACING = 170;
    static final double HALF_CENTER = (double)(CENTERX / 2.0);
    static final double START_Y = (double)(CENTERY / 2.0);       
       
       //function to draw routes over a random map
       private static void drawRandomMapRoutes(int start, int end) {
    	   MapManager.SwapMap(MapManager.MapType.RANDOM);
          MapManager.GetShortestPath(start, end);
          MapManager.DrawAllNodes();
       }
    
    //function to print the random map
       private static void printRandomMap() {
    	  Drawer.ClearScreen();
          MapManager.SwapMap(MapManager.MapType.RANDOM);
          MapManager.DrawAllNodes();
           
           ImageButton GoBack = new ImageButton("Go back", EXIT_ICON_PATH, SCALE,
                   mouseClicked ->  {
                          Drawer.ClearScreen();
                          try {
				runRandomMapMenu();
						} catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
                }
            );
           Drawer.DrawButton(GoBack, 0, 0);
       }
       
       //function to print the random map with a route drawn
       private static void printRandomMapRoute(int start, int destination) {
    	   Drawer.ClearScreen();
           drawRandomMapRoutes(start, destination);
           ImageButton GoBack = new ImageButton("Go back", EXIT_ICON_PATH, SCALE,
                   mouseClicked ->  {
                          Drawer.ClearScreen();
                          try {
							runRandomMapMenu();
						} catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
                }
            );
           Drawer.DrawButton(GoBack, 0, 0);
       }
       
       
       //function to run the random map menu
       private static void runRandomMapMenu() throws FileNotFoundException {
    	   //generate a random map that can be interacted with

    	   ImageButton ShowMapButton = new ImageButton("Show map", RANDOM_MAP_ICON_PATH, SCALE,
                   mouseClicked ->  {
                          printRandomMap();                
                  }
           );
    	   
    	   ImageButton PrintLocationsButton = new ImageButton("Print Locations List", RANDOM_MAP_ICON_PATH, SCALE,
                   mouseClicked ->  {
                	   MapManager.printMapLocations();               
                  }
           );
    	   
    	   ImageButton PickStartButton = new ImageButton("Pick Start", RANDOM_MAP_ICON_PATH, SCALE,
                   mouseClicked ->  {
                	   String location = JOptionPane.showInputDialog("Enter your start location: ");
                	   start = MapManager.findLocation(location);
               		   JOptionPane.showMessageDialog(null, "User picked: "+ location);
               		   if (start == -1) //if location not found, make start index 0
               			   start = 0;
                  }
           );
    	   
    	   ImageButton PickDestinationsButton = new ImageButton("Pick Destination", RANDOM_MAP_ICON_PATH, SCALE,
                   mouseClicked ->  {
                	   String location = JOptionPane.showInputDialog("Enter your destination: ");
                	   destination = MapManager.findLocation(location);
               		   JOptionPane.showMessageDialog(null, "User picked: "+ location);
               		   if (destination == -1) //if location not found, make start index 11
               			   destination = 11;
                  }               	   
           );
    	   
    	   ImageButton ShowRouteButton = new ImageButton("Show route", RANDOM_MAP_ICON_PATH, SCALE,
                   mouseClicked ->  {
                	   	  System.out.println(start);
                          printRandomMapRoute(start, destination);
                  }
           );
    	   
    	   //this button returns to the main menu
    	   ImageButton GoBack = new ImageButton("Return to Menu", EXIT_ICON_PATH, SCALE,
                   mouseClicked ->  {
                          Drawer.ClearScreen();
                          runMainMenu();
                }
            );
    	   
    	   //draw the buttons that a user will interact with
    	   Drawer.DrawButton(ShowMapButton, HALF_CENTER-100, START_Y  + SPACING * -1);
    	   Drawer.DrawButton(PrintLocationsButton, HALF_CENTER-100, START_Y  + SPACING * 0);
    	   Drawer.DrawButton(PickStartButton, HALF_CENTER+200, START_Y  + SPACING * 0);
    	   Drawer.DrawButton(PickDestinationsButton, HALF_CENTER+200, START_Y  + SPACING * 1);
           Drawer.DrawButton(ShowRouteButton, HALF_CENTER-100, START_Y  + SPACING * 1);
    	   Drawer.DrawButton(GoBack, HALF_CENTER-100, START_Y  + SPACING * 2);
       }

       
       //true = show route, false = show locations only
       private static void runPremadeMap(boolean showPath){
              Drawer.ClearScreen();
              final String WHATCOM_MAP_IMAGE = "./whatcomcc.jpg";
              Image myImage = ResourceLoader.LoadImage(WHATCOM_MAP_IMAGE);
              Drawer.DrawImage(myImage, 0,0,0.83);
              MapManager.SwapMap(MapManager.MapType.PREMADE);
              if(showPath){
                     MapManager.GetShortestPath(start, destination);
              }

              //Draw the nodes
              MapManager.DrawAllNodes();
              ImageButton GoBack = new ImageButton("Return to Menu", EXIT_ICON_PATH, SCALE,
                     mouseClicked ->  {
                            Drawer.ClearScreen();
                            runPreMadeMapMenu();
                     }
              );
              //Draw Return button
              Drawer.DrawButton(GoBack, 0, 0);
       }

       private static void runPreMadeMapMenu() {
    	   //generate a random map that can be interacted with
    	   Drawer.ClearScreen();

    	   ImageButton ShowMapButton = new ImageButton("Show map", RANDOM_MAP_ICON_PATH, SCALE,
                   mouseClicked ->  {
                          runPremadeMap(false);               
                  }
           );
    	   
    	   ImageButton PrintLocationsButton = new ImageButton("Print Locations List", RANDOM_MAP_ICON_PATH, SCALE,
                   mouseClicked ->  {
                	   MapManager.printMapLocations();          
                  }
           );
    	   
    	   ImageButton PickStartButton = new ImageButton("Pick Start", RANDOM_MAP_ICON_PATH, SCALE,
                   mouseClicked ->  {
                	   String location = JOptionPane.showInputDialog("Enter your start location: ");
                	   start = MapManager.findLocation(location);
               		   JOptionPane.showMessageDialog(null, "User picked: "+ location);
               		   if (start == -1) //if location not found, make start index 0
               			   start = 0;                  
                  }
           );
    	   
    	   ImageButton PickDestinationsButton = new ImageButton("Pick Destination", RANDOM_MAP_ICON_PATH, SCALE,
                   mouseClicked ->  {
                	   String location = JOptionPane.showInputDialog("Enter your destination: ");
                	   destination = MapManager.findLocation(location);
               		   JOptionPane.showMessageDialog(null, "User picked: "+ location);
               		   if (destination == -1) //if location not found, make start index 5
               			   destination = 5;              
                  }
           );
    	   ImageButton ShowRouteButton = new ImageButton("Show route", RANDOM_MAP_ICON_PATH, SCALE,
                   mouseClicked ->  {
                	   runPremadeMap(true);  //true means to show paths as well              
                  }
           );
    	   
    	   //this button returns to the main menu
    	   ImageButton GoBack = new ImageButton("Return to Menu", EXIT_ICON_PATH, SCALE,
                   mouseClicked ->  {
                          Drawer.ClearScreen();
                          runMainMenu();
                }
            );
    	   
    	   //draw the buttons that a user will interact with
    	   Drawer.DrawButton(ShowMapButton, HALF_CENTER-100, START_Y  + SPACING * -1);
    	   Drawer.DrawButton(PrintLocationsButton, HALF_CENTER-100, START_Y  + SPACING * 0);
    	   Drawer.DrawButton(PickStartButton, HALF_CENTER+200, START_Y  + SPACING * 0);
    	   Drawer.DrawButton(PickDestinationsButton, HALF_CENTER+200, START_Y  + SPACING * 1);
           Drawer.DrawButton(ShowRouteButton, HALF_CENTER-100, START_Y  + SPACING * 1);
    	   Drawer.DrawButton(GoBack, HALF_CENTER-100, START_Y  + SPACING * 2);
       }
       
       //Display three buttons to interact with, Run Premade Map, go to Random Map screen, Exit program
       private static void runMainMenu(){
    	   
              //button to display premade map
              ImageButton premade = new ImageButton("Load Pre-made map", PREMADE_MAP_ICON_PATH, SCALE,
                     mouseClicked ->  {
                    	 runPreMadeMapMenu();
                    	 MapManager.SwapMap(MapManager.MapType.PREMADE);
                     }
              );
              
              //button to open new random map screen, still testing
              ImageButton randomMapScreen = new ImageButton("Load Random Map", RANDOM_MAP_ICON_PATH, SCALE,
            		  mouseClicked ->{
            			  Drawer.ClearScreen();
            			  try {
							runRandomMapMenu();
						} catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
            			  MapManager.SwapMap(MapManager.MapType.RANDOM);
            		  }
            		  
              );
              
             //button to exit the program
             ImageButton exit = new ImageButton("Exit Program", EXIT_ICON_PATH, SCALE,
                     mouseClicked ->  {
                            //close the application
                            final int EXIT_SUCCESS = 0;
                            System.exit(EXIT_SUCCESS);
                     }
              );
              
             //button to return to main menu screen
              ImageButton ReturnButton = new ImageButton("Return to Menu", EXIT_ICON_PATH, SCALE,
                     mouseClicked ->  {
                            Drawer.ClearScreen();
                            runMainMenu();
                     }
              );
              
              //Draw the buttons that will be clicked on
              Drawer.DrawButton(premade, HALF_CENTER, START_Y  + SPACING * 0);
              Drawer.DrawButton(randomMapScreen, HALF_CENTER, START_Y  + SPACING * 1);
              Drawer.DrawButton(exit, HALF_CENTER, START_Y  + SPACING * 2);
       }


       //DEFINE WHAT TO DRAW HERE
       public void start(Stage primaryStage) throws FileNotFoundException {
              //Main menu will branch out and create the screen based on what button is pressed
    	   	  
              runMainMenu();

              //Draw to screen
              Scene scene = Drawer.getScreen();     

              primaryStage.setScene(scene);
              primaryStage.show(); 
       }
       
       
       public static void main(String[] args){
          launch(args);
       }
};
