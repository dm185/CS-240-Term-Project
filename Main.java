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
    private final int MIDX = 300;
    private final int STARTY = 100;
    private final int ENDY = STARTY + 350;
    private final float SPEED_LIMIT_1 = 50;
    private final float SPEED_LIMIT_2 = 40;
    private final int SCREENX = 900;
    private final int SCREENY = 900;
    
    //used to make icons in the GUI
    final String PREMADE_MAP_ICON_PATH = "./premade.png";
    final String RANDOM_MAP_ICON_PATH = "./random.png";
    final String EXIT_ICON_PATH = "./exit.png";
    final double SCALE = 0.20;
    final int CENTERX = SCREENX / 2;
    final int CENTERY = SCREENY / 2;
    final double SPACING = 170;
    final double HALF_CENTER = (double)(CENTERX / 2.0);
    final double START_Y = (double)(CENTERY / 2.0);
   
    
    	//function used to make an arraylist from a text file. functions calling this function require 
        // a throws FileNotFoundException
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
   
       
       /*
              Load image from HardDrive and return the Image object.
       */
       public Image LoadImage(String file_path) {
              return ResourceLoader.LoadImage(file_path);
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
           RoadNode left1 = new RoadNode("Target", MIDX - 50, STARTY + 100);
           RoadNode left2 = new RoadNode("L2", 450, 400);
           
           //Connect left path
           startnode.AddConnection(left1, SPEED_LIMIT_2);  //root ---> left1
           left1.AddConnection(left2, SPEED_LIMIT_2);     //left1 ---> end
           left2.AddConnection(end, SPEED_LIMIT_1);
           
           //Connect roads in middle for testing
           left1.AddConnection(right3, SPEED_LIMIT_1);
            
           //Draw all of the node regardless if they have been chosen
           Drawer.DrawNode(startnode, Drawer.DEFAULT_NODE_COLOR);
           Drawer.DrawNode(right1, Drawer.DEFAULT_NODE_COLOR);
           Drawer.DrawNode(right2, Drawer.DEFAULT_NODE_COLOR);
           Drawer.DrawNode(right3, Drawer.DEFAULT_NODE_COLOR);
           Drawer.DrawNode(left1, Drawer.DEFAULT_NODE_COLOR);
           Drawer.DrawNode(left2,Drawer.DEFAULT_NODE_COLOR); 
           Drawer.DrawNode(end, Drawer.DEFAULT_NODE_COLOR);
           
         //Solve for the shortest path between 'root' and 'end'
           Path path = startnode.getShortestPath(end);
           
           //Draw nodes that were chosen in a Green color
           for(RoadNode node : path){ //Iterate through the list of chosen nodes
              Drawer.DrawChosenNode(node, path, Drawer.DEFAULT_CHOSEN_NODE_COLOR);
           }
       }
       

      
       
       //This a premadeMap that opens a map with a manual placed start and end. T
       public void premadeMap() {
    	  
           //Points of interest (Replace with startnode and end for shortest-path)
           RoadNode startnode = new RoadNode("WhatcomCC", 150, 200); //whatcomcc         <----- CURRENT STARTNODE
           RoadNode mcdonalds = new RoadNode("McDonalds", 300, 525); //mcdonalds
           RoadNode storage = new RoadNode("Storage", 521, 550); //storage
           RoadNode walmart = new RoadNode("Walmart", 370, 30); //walmart
           RoadNode jewelry = new RoadNode("Jewelry", 500, 200); //jewelry
           RoadNode apartments = new RoadNode("Apartments", 520, 400); //apartments
           RoadNode dental = new RoadNode("Dental", 350, 325); //dental
           RoadNode end = new RoadNode("Target", 140, 430); //target				<----- CURRENT END POINT
           RoadNode winco = new RoadNode("Winco", 370, 440); //winco
   
           //All the nodes on the roads
           RoadNode t1 = new RoadNode("t1", 140, 418);               RoadNode m1 = new RoadNode("m1", 309, 518);
           RoadNode t2 = new RoadNode("t2", 210, 418);               RoadNode m2 = new RoadNode("m2", 310, 440);
           RoadNode t3 = new RoadNode("t3", 209, 360);			     RoadNode m3 = new RoadNode("m3", 311, 360);
           RoadNode t4 = new RoadNode("t4", 208, 298);		 	     RoadNode m4 = new RoadNode("m4", 311, 308);
           RoadNode t5 = new RoadNode("t5", 202, 292);               RoadNode m5 = new RoadNode("m5", 311, 200);
           RoadNode t6 = new RoadNode("t6", 214, 292);        	     RoadNode m6 = new RoadNode("m6", 312, 40);
           RoadNode t101 = new RoadNode("t101", 118, 430);           RoadNode j1 = new RoadNode("j1", 500, 204);
           RoadNode t102 = new RoadNode("t102", 118, 420);           RoadNode j2 = new RoadNode("j2", 443, 203);
           RoadNode t103 = new RoadNode("t103", 100, 416);           RoadNode j3 = new RoadNode("j3", 443, 308);
           RoadNode t104 = new RoadNode("t104", 100, 360);           RoadNode j4 = new RoadNode("j4", 458, 340);
           RoadNode t105 = new RoadNode("t105", 95, 314);            RoadNode j5 = new RoadNode("j5", 458, 362);
           RoadNode t106 = new RoadNode("t106", 100, 298);           RoadNode j6 = new RoadNode("j6", 458, 398);
           RoadNode wcc1 = new RoadNode("wwc1", 195, 200);           RoadNode j7 = new RoadNode("j7", 458, 416);
           RoadNode wcc2 = new RoadNode("wcc2", 200, 210);           RoadNode wnc1 = new RoadNode("wnc1", 335, 440);
           RoadNode wcc3 = new RoadNode("wcc3", 208, 240);           RoadNode wnc2 = new RoadNode("wnc2", 370, 416);
           RoadNode wcc4 = new RoadNode("wcc4", 208, 287);           RoadNode wnc3 = new RoadNode("wnc3", 338, 420);
           RoadNode wcc5 = new RoadNode("wcc5", 210, 200);           RoadNode wnc4 = new RoadNode("wnc4", 345, 416);
           RoadNode wcc6 = new RoadNode("wcc6", 202, 195);           RoadNode b1 = new RoadNode("b1", 521, 493);
           RoadNode d1 = new RoadNode("d1", 350, 308);           	 RoadNode b2 = new RoadNode("b2", 475, 492);
           RoadNode d2 = new RoadNode("d2", 365, 308);           	 RoadNode b3 = new RoadNode("b3", 458, 500);
           RoadNode d3 = new RoadNode("d3", 365, 337);           	 RoadNode b4 = new RoadNode("b4", 432, 513);
           RoadNode d4 = new RoadNode("d4", 377, 347);           	 RoadNode b5 = new RoadNode("b5", 400, 520);
           RoadNode d5 = new RoadNode("d5", 379, 360);           	 RoadNode apt1 = new RoadNode("apt1", 512, 399);
           RoadNode d6 = new RoadNode("d6", 400, 361);           	 RoadNode apt2 = new RoadNode("apt2", 512, 364);
           RoadNode d7 = new RoadNode("d7", 400, 375);           	 RoadNode tm1 = new RoadNode("tm1", 300, 438);
           RoadNode d8 = new RoadNode("d8", 417, 379);           	 RoadNode tm2 = new RoadNode("tm2", 288, 418);
           RoadNode d9 = new RoadNode("d9", 416, 416);           	 RoadNode wm1 = new RoadNode("wm", 370, 40);  
           RoadNode mcd1 = new RoadNode("mcd1", 300, 518);	     	 RoadNode wcc101 = new RoadNode("wcc101", 115, 219);
           RoadNode mcd2 = new RoadNode("mcd2", 280, 518);	     	 RoadNode wcc102 = new RoadNode("wcc102", 100, 219);
           RoadNode mcd3 = new RoadNode("mcd3", 250, 540);
           RoadNode mcd4 = new RoadNode("mcd4", 195, 535);
           RoadNode mcd5 = new RoadNode("mcd5", 158, 537);
           RoadNode mcd6 = new RoadNode("mcd6", 157, 503);
           RoadNode mcd7 = new RoadNode("mcd7", 140, 467);
           RoadNode mcd8 = new RoadNode("mcd8", 118, 445);        
           
           //All of the road connections. Replace Point of interest nodes with startnode and end for the shortest-path between the two.
           startnode.AddConnection(wcc1, SPEED_LIMIT_1); //startnode (whatcomcc)      <----- CURRENT STARTNODE
           wcc1.AddConnection(wcc6, SPEED_LIMIT_1); 
           wcc1.AddConnection(wcc2, SPEED_LIMIT_1); 
           wcc2.AddConnection(wcc3, SPEED_LIMIT_1); 
           wcc2.AddConnection(wcc5, SPEED_LIMIT_1);  
           wcc3.AddConnection(wcc4, SPEED_LIMIT_1); 
           wcc5.AddConnection(wcc6, SPEED_LIMIT_1); 
           t6.AddConnection(wcc4, SPEED_LIMIT_1); 
           t5.AddConnection(wcc4, SPEED_LIMIT_1);
           t4.AddConnection(t5, SPEED_LIMIT_1);
           t4.AddConnection(t6, SPEED_LIMIT_1);
           t4.AddConnection(wcc4, SPEED_LIMIT_1);
           t3.AddConnection(t4, SPEED_LIMIT_1);
           t3.AddConnection(m3, SPEED_LIMIT_1);
           t2.AddConnection(tm2, SPEED_LIMIT_1);
           tm2.AddConnection(tm1, SPEED_LIMIT_1);
           tm1.AddConnection(m2, SPEED_LIMIT_1);
           t2.AddConnection(t3, SPEED_LIMIT_1);
           t1.AddConnection(t2, SPEED_LIMIT_1); 
           t1.AddConnection(end, SPEED_LIMIT_1); //end (target)     <----- CURRENT END POINT
           t101.AddConnection(end, SPEED_LIMIT_1); //end (target)     <----- CURRENT END POINT
           t101.AddConnection(t102, SPEED_LIMIT_1);
           t102.AddConnection(t103, SPEED_LIMIT_1);
           t102.AddConnection(t1, SPEED_LIMIT_1);
           t103.AddConnection(t104, SPEED_LIMIT_1);
           t104.AddConnection(t105, SPEED_LIMIT_1);
           t104.AddConnection(t3, SPEED_LIMIT_1);
           t105.AddConnection(t106, SPEED_LIMIT_1);
           t106.AddConnection(wcc102, SPEED_LIMIT_1);
           wcc102.AddConnection(wcc101, SPEED_LIMIT_1);
           wcc101.AddConnection(startnode, SPEED_LIMIT_1); //startnode (whatcomcc)      <----- CURRENT STARTNODE
           m1.AddConnection(m2, SPEED_LIMIT_1); 
           m1.AddConnection(b5, SPEED_LIMIT_1);
           m1.AddConnection(mcd1, SPEED_LIMIT_1);
           m2.AddConnection(m3, SPEED_LIMIT_1); 
           m2.AddConnection(wnc1, SPEED_LIMIT_1);
           m3.AddConnection(m4, SPEED_LIMIT_1); 
           m3.AddConnection(d5, SPEED_LIMIT_1);
           m4.AddConnection(m5, SPEED_LIMIT_1); 
           m4.AddConnection(d1, SPEED_LIMIT_1);
           m5.AddConnection(m6, SPEED_LIMIT_1); 
           m5.AddConnection(j1, SPEED_LIMIT_1);
           m5.AddConnection(wcc5, SPEED_LIMIT_1);
           m6.AddConnection(wm1, SPEED_LIMIT_1);
           wm1.AddConnection(walmart, SPEED_LIMIT_1); //walmart - Point of interest
           j1.AddConnection(jewelry, SPEED_LIMIT_1); //jewelry - Point of interest
           j1.AddConnection(j2, SPEED_LIMIT_1); 
           j2.AddConnection(m5, SPEED_LIMIT_1);
           j2.AddConnection(j3, SPEED_LIMIT_1);
           j3.AddConnection(d1, SPEED_LIMIT_1);
           j3.AddConnection(j4, SPEED_LIMIT_1);
           j4.AddConnection(j5, SPEED_LIMIT_1);
           j5.AddConnection(j6, SPEED_LIMIT_1);
           j5.AddConnection(d6, SPEED_LIMIT_1);
           j6.AddConnection(j7, SPEED_LIMIT_1);
           j7.AddConnection(b3, SPEED_LIMIT_1);
           b1.AddConnection(storage, SPEED_LIMIT_1); //storage - Point of interest
           b1.AddConnection(b2, SPEED_LIMIT_1);
           b2.AddConnection(b3, SPEED_LIMIT_1);
           b3.AddConnection(b4, SPEED_LIMIT_1);
           b4.AddConnection(b5, SPEED_LIMIT_1);
           wnc1.AddConnection(winco, SPEED_LIMIT_1);
           wnc1.AddConnection(wnc3, SPEED_LIMIT_1);
           wnc2.AddConnection(winco, SPEED_LIMIT_1); //winco - Point of interst
           wnc2.AddConnection(d9, SPEED_LIMIT_1);
           wnc2.AddConnection(wnc4, SPEED_LIMIT_1);
           wnc3.AddConnection(wnc4, SPEED_LIMIT_1);
           d1.AddConnection(dental, SPEED_LIMIT_1); //dental - Point of interest
           d1.AddConnection(d2, SPEED_LIMIT_1);
           d2.AddConnection(d3, SPEED_LIMIT_1);
           d3.AddConnection(d4, SPEED_LIMIT_1);
           d4.AddConnection(d5, SPEED_LIMIT_1);
           d5.AddConnection(d6, SPEED_LIMIT_1);
           d6.AddConnection(d7, SPEED_LIMIT_1);
           d7.AddConnection(d8, SPEED_LIMIT_1);
           d8.AddConnection(d9, SPEED_LIMIT_1);
           d9.AddConnection(j7, SPEED_LIMIT_1);
           apt1.AddConnection(apartments, SPEED_LIMIT_1); //apartments - Point of interest
           apt1.AddConnection(j6, SPEED_LIMIT_1);
           apt1.AddConnection(apt2, SPEED_LIMIT_1);
           apt2.AddConnection(j5, SPEED_LIMIT_1);
           mcd1.AddConnection(mcdonalds, SPEED_LIMIT_1); //mcdonalds - Point of interest
           mcd1.AddConnection(mcd2, SPEED_LIMIT_1);
           mcd2.AddConnection(mcd3, SPEED_LIMIT_1);
           mcd3.AddConnection(mcd4, SPEED_LIMIT_1);
           mcd4.AddConnection(mcd5, SPEED_LIMIT_1);
           mcd5.AddConnection(mcd6, SPEED_LIMIT_1);
           mcd6.AddConnection(mcd7, SPEED_LIMIT_1);
           mcd7.AddConnection(mcd8, SPEED_LIMIT_1);
           mcd8.AddConnection(t101, SPEED_LIMIT_1);
                 
           //Draw all of the node regardless if they have been chosen
           Drawer.DrawNode(startnode, Drawer.DEFAULT_NODE_COLOR); //whatcomcc      <----- CURRENT STARTNODE
           Drawer.DrawNode(storage, Drawer.DEFAULT_NODE_COLOR); //storage
           Drawer.DrawNode(walmart, Drawer.DEFAULT_NODE_COLOR); //walmart
           Drawer.DrawNode(jewelry, Drawer.DEFAULT_NODE_COLOR); //jewelry
           Drawer.DrawNode(apartments, Drawer.DEFAULT_NODE_COLOR); //apartments
           Drawer.DrawNode(dental, Drawer.DEFAULT_NODE_COLOR); //dental
           Drawer.DrawNode(end, Drawer.DEFAULT_NODE_COLOR); //target               <----- CURRENT END POINT
           Drawer.DrawNode(winco,Drawer.DEFAULT_NODE_COLOR); //winco
           Drawer.DrawNode(mcdonalds, Drawer.DEFAULT_NODE_COLOR); //mcdonalds          

           //Solve for the shortest path between 'root' and 'end'
           Path path = startnode.getShortestPath(end);
           
           //Boolean if true will show only the Chosen path from startnode and end node.
           //Boolean if false will display all the nodes on the map
           boolean flipNodes = true;
           
           //Draw nodes that were chosen in a Green color
           if (flipNodes) {
        	   for(RoadNode node : path){ //Iterate through the list of chosen nodes
                	  Drawer.DrawChosenNode(node, path, Drawer.DEFAULT_CHOSEN_NODE_COLOR);
           	   }
           } else {
               Drawer.DrawNode(t1, Drawer.DEFAULT_ROAD_NODE_COLOR);	  Drawer.DrawNode(t101, Drawer.DEFAULT_ROAD_NODE_COLOR);	  Drawer.DrawNode(m1, Drawer.DEFAULT_ROAD_NODE_COLOR);
               Drawer.DrawNode(t2, Drawer.DEFAULT_ROAD_NODE_COLOR);	  Drawer.DrawNode(t102, Drawer.DEFAULT_ROAD_NODE_COLOR);	  Drawer.DrawNode(m2, Drawer.DEFAULT_ROAD_NODE_COLOR);
               Drawer.DrawNode(t3, Drawer.DEFAULT_ROAD_NODE_COLOR);	  Drawer.DrawNode(t103, Drawer.DEFAULT_ROAD_NODE_COLOR);	  Drawer.DrawNode(m3, Drawer.DEFAULT_ROAD_NODE_COLOR);
               Drawer.DrawNode(t4, Drawer.DEFAULT_ROAD_NODE_COLOR);	  Drawer.DrawNode(t104, Drawer.DEFAULT_ROAD_NODE_COLOR);	  Drawer.DrawNode(m4, Drawer.DEFAULT_ROAD_NODE_COLOR);
               Drawer.DrawNode(t5, Drawer.DEFAULT_ROAD_NODE_COLOR);	  Drawer.DrawNode(t105, Drawer.DEFAULT_ROAD_NODE_COLOR);	  Drawer.DrawNode(m5, Drawer.DEFAULT_ROAD_NODE_COLOR);
               Drawer.DrawNode(t6, Drawer.DEFAULT_ROAD_NODE_COLOR);	  Drawer.DrawNode(t106, Drawer.DEFAULT_ROAD_NODE_COLOR);	  Drawer.DrawNode(m6, Drawer.DEFAULT_ROAD_NODE_COLOR);
               Drawer.DrawNode(wcc1, Drawer.DEFAULT_ROAD_NODE_COLOR);   Drawer.DrawNode(b1, Drawer.DEFAULT_ROAD_NODE_COLOR);      Drawer.DrawNode(d1, Drawer.DEFAULT_ROAD_NODE_COLOR);
               Drawer.DrawNode(wcc2, Drawer.DEFAULT_ROAD_NODE_COLOR);   Drawer.DrawNode(b2, Drawer.DEFAULT_ROAD_NODE_COLOR);      Drawer.DrawNode(d2, Drawer.DEFAULT_ROAD_NODE_COLOR);
               Drawer.DrawNode(wcc3, Drawer.DEFAULT_ROAD_NODE_COLOR);   Drawer.DrawNode(b3, Drawer.DEFAULT_ROAD_NODE_COLOR);	  Drawer.DrawNode(d3, Drawer.DEFAULT_ROAD_NODE_COLOR);
               Drawer.DrawNode(wcc4, Drawer.DEFAULT_ROAD_NODE_COLOR);   Drawer.DrawNode(b4, Drawer.DEFAULT_ROAD_NODE_COLOR);	  Drawer.DrawNode(d4, Drawer.DEFAULT_ROAD_NODE_COLOR);
               Drawer.DrawNode(wcc5, Drawer.DEFAULT_ROAD_NODE_COLOR);   Drawer.DrawNode(b5, Drawer.DEFAULT_ROAD_NODE_COLOR);	  Drawer.DrawNode(d5, Drawer.DEFAULT_ROAD_NODE_COLOR);
               Drawer.DrawNode(wcc6, Drawer.DEFAULT_ROAD_NODE_COLOR);   Drawer.DrawNode(wm1, Drawer.DEFAULT_ROAD_NODE_COLOR);     Drawer.DrawNode(d6, Drawer.DEFAULT_ROAD_NODE_COLOR);
               Drawer.DrawNode(j1, Drawer.DEFAULT_ROAD_NODE_COLOR); 	  Drawer.DrawNode(wnc1, Drawer.DEFAULT_ROAD_NODE_COLOR);	  Drawer.DrawNode(d7, Drawer.DEFAULT_ROAD_NODE_COLOR);
               Drawer.DrawNode(j2, Drawer.DEFAULT_ROAD_NODE_COLOR);	  Drawer.DrawNode(wnc2, Drawer.DEFAULT_ROAD_NODE_COLOR);	  Drawer.DrawNode(d8, Drawer.DEFAULT_ROAD_NODE_COLOR);
               Drawer.DrawNode(j3, Drawer.DEFAULT_ROAD_NODE_COLOR);	  Drawer.DrawNode(wnc3, Drawer.DEFAULT_ROAD_NODE_COLOR);	  Drawer.DrawNode(d9, Drawer.DEFAULT_ROAD_NODE_COLOR);
               Drawer.DrawNode(j4, Drawer.DEFAULT_ROAD_NODE_COLOR);  	  Drawer.DrawNode(wnc4, Drawer.DEFAULT_ROAD_NODE_COLOR);    Drawer.DrawNode(apt1, Drawer.DEFAULT_ROAD_NODE_COLOR);
               Drawer.DrawNode(j5, Drawer.DEFAULT_ROAD_NODE_COLOR);	  Drawer.DrawNode(wcc101, Drawer.DEFAULT_ROAD_NODE_COLOR);  Drawer.DrawNode(apt2, Drawer.DEFAULT_ROAD_NODE_COLOR);
               Drawer.DrawNode(j6, Drawer.DEFAULT_ROAD_NODE_COLOR);	  Drawer.DrawNode(wcc102, Drawer.DEFAULT_ROAD_NODE_COLOR);  Drawer.DrawNode(tm1, Drawer.DEFAULT_ROAD_NODE_COLOR);
               Drawer.DrawNode(j7, Drawer.DEFAULT_ROAD_NODE_COLOR);	  Drawer.DrawNode(wcc1, Drawer.DEFAULT_ROAD_NODE_COLOR);    Drawer.DrawNode(tm2, Drawer.DEFAULT_ROAD_NODE_COLOR);
               Drawer.DrawNode(mcd1, Drawer.DEFAULT_ROAD_NODE_COLOR);
               Drawer.DrawNode(mcd2, Drawer.DEFAULT_ROAD_NODE_COLOR);
               Drawer.DrawNode(mcd3, Drawer.DEFAULT_ROAD_NODE_COLOR);
               Drawer.DrawNode(mcd4, Drawer.DEFAULT_ROAD_NODE_COLOR);
               Drawer.DrawNode(mcd5, Drawer.DEFAULT_ROAD_NODE_COLOR);
               Drawer.DrawNode(mcd6, Drawer.DEFAULT_ROAD_NODE_COLOR);
               Drawer.DrawNode(mcd7, Drawer.DEFAULT_ROAD_NODE_COLOR);
               Drawer.DrawNode(mcd8, Drawer.DEFAULT_ROAD_NODE_COLOR);
           }

       }
  
       
       //function to draw a random map, no routes
       private void drawRandomMap(RandomMap rMap) {
    	   for (int i = 0; i < rMap.getQuadrant1Size(); i++) {
           	  Drawer.DrawNode(rMap.getQuadrant1().get(i), Drawer.DEFAULT_NODE_COLOR);
             }
       }
       
       
       
       //function to draw routes over a random map
       private void drawRandomMapRoutes(RandomMap rMap) {
    	   drawRandomMap(rMap);
    	   RoadNode start = rMap.getQuadrant1().get(0);
           RoadNode end = rMap.getQuadrant1().get(11);
           Path path = start.getShortestPath(end);
           for (RoadNode node : path) {
         	  Drawer.DrawChosenNode(node, path, Drawer.DEFAULT_CHOSEN_NODE_COLOR);
           }
       }
       
       
       //function to print the random map
       private void printRandomMap(RandomMap rMap) {
    	   Drawer.ClearScreen();
           drawRandomMap(rMap);
           
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
       private void printRandomMapRoute(RandomMap rMap) {
    	   Drawer.ClearScreen();
           drawRandomMapRoutes(rMap);
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
       private void runRandomMapMenu() throws FileNotFoundException {
    	   //generate a random map that can be interacted with
    	   
    	   RandomMap randomMap = RandomMap.get();

    	   ImageButton ShowMapButton = new ImageButton("Show map", RANDOM_MAP_ICON_PATH, SCALE,
                   mouseClicked ->  {
                          printRandomMap(randomMap);                
                  }
           );
    	   
    	   ImageButton ShowRouteButton = new ImageButton("Show route", RANDOM_MAP_ICON_PATH, SCALE,
                   mouseClicked ->  {
                          printRandomMapRoute(randomMap);                
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
    	   Drawer.DrawButton(ShowMapButton, HALF_CENTER, START_Y  + SPACING * 0);
           Drawer.DrawButton(ShowRouteButton, HALF_CENTER, START_Y  + SPACING * 1);
    	   Drawer.DrawButton(GoBack, HALF_CENTER, START_Y  + SPACING * 2);
       }
       
       //function to run the premademap screen
       private void runPremadeMap() {
    	   Drawer.ClearScreen();
           //Use path seperator so that it is cross platform
           final String WHATCOM_MAP_IMAGE = "./whatcomcc.jpg";
           Image myImage = LoadImage(WHATCOM_MAP_IMAGE);
           Drawer.DrawImage(myImage, 0,0,0.83);
           
           //Draw the nodes
           premadeMap();
           ImageButton GoBack = new ImageButton("Return to Menu", EXIT_ICON_PATH, SCALE,
                   mouseClicked ->  {
                          Drawer.ClearScreen();
                          runMainMenu();
                }
            );
           //Draw Return button
           Drawer.DrawButton(GoBack, 0, 0);
       }
       
       //Display three buttons to interact with, Run Premade Map, go to Random Map screen, Exit program
       private void runMainMenu(){
    	   
    	   
    	   
              //button to display premade map
              ImageButton premade = new ImageButton("Load Pre-made map", PREMADE_MAP_ICON_PATH, SCALE,
                     mouseClicked ->  {
                    	 	runPremadeMap();
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
