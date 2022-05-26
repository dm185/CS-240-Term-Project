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
    private final Color DEFAULT_NODE_COLOR = Color.RED;
    private final Color DEFAULT_CHOSEN_NODE_COLOR = Color.GREEN;
    private final Color DEFAULT_ROAD_NODE_COLOR = Color.DARKMAGENTA;
	private final Color DEFAULT_CLEAR_COLOR = Color.TRANSPARENT;
    
    private Group root;
       
    
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
    	
       //Helper functions to add things to the screen to display
       
       /*
              Draw a 90* curve from {x1, y1} to {x2, y2}
              Used for drawing roads that turn into a curve
       */
       public void Add90Curve(double startX, double startY, double endX, double endY, Color c){
              final double CONTROL_X = startX;
              final double CONTROL_Y = endY;
              
              QuadCurve q = new QuadCurve(startX, startY, CONTROL_X, CONTROL_Y, endX, endY);
              
              //Visual options
              q.setFill(Color.TRANSPARENT);
              q.setStroke(c);
              
              this.root.getChildren().add(q);
       }
       
       public void AddText(int x, int y, String text){
              Text t = new Text();
              t.setText(text);
              t.setX(x);
              t.setY(y);
              
              this.root.getChildren().add(t);
       }
       
       /*
              Draw circle at x,y with color 'c'. If wireframe = true then the center will be trasnparent
              and the color 'c' will instead be used for the outline of the circle.
              Used for drawing roundabouts
       */
       public void AddCircle(int x, int y, int rad, Color c, boolean wireframe){
              Circle cir = new Circle(x, y, rad);
              
              if(wireframe){
                     //Wireframe mode
                     cir.setFill(Color.TRANSPARENT);
                     cir.setStroke(c);
              } else {
                     //Fill mode
                     cir.setFill(c);
              }
              
              this.root.getChildren().add(cir);
       }
       
       public void AddLine(int x1, int y1, int x2, int y2, Color c){
              Line l = new Line(x1, y1, x2, y2);
              l.setStroke(c);
              
              this.root.getChildren().add(l);
       }
       
       public void DrawNode(RoadNode n, Color c){
              AddCircle((int)n.getX(), (int)n.getY(), 3, c, false);
              AddText((int)n.getX(), (int)n.getY() + 10, n.getName());
              
              for(Connection link : n.getLinks()) {
                     RoadNode n1 = link.getSource();
                     RoadNode n2 = link.getDest();
                     AddLine((int)n1.getX(), (int)n1.getY(), (int)n2.getX(), (int)n2.getY(), c);
              }
       }
       
       public void DrawChosenNode(RoadNode n, Path path, Color c){
              AddCircle((int)n.getX(), (int)n.getY(), 0, c, false);
              //AddText((int)n.getX(), (int)n.getY() + 0, n.getName());
              
              for(Connection link : n.getLinks()) {
                     RoadNode n1 = link.getSource();
                     RoadNode n2 = link.getDest();
                     //Only print the path if it is chosen
                     if(path.containsPair(n1, n2)) {
                            AddLine((int)n1.getX(), (int)n1.getY(), (int)n2.getX(), (int)n2.getY(), c);
                     }
              }
       }
       
       public void ClearScreen(){
              this.root.getChildren().clear();
       }
       
       /*
              Load image from HardDrive and return the Image object.
       */
       public Image LoadImage(String file_path) {
              return ImageLoader.load(file_path);
       }
       
       /*
              Draws a image object to the screen. To get a image object, call the LoadImage() function.
              Set scale to ClickableImage.ORIGINAL_SIZE_SCALAR (aka. 1.0) to make the image appear with the
              orignal width and height in the image file
       */
       public void DrawImage(Image img, double x, double y, double scale){
              ClickableImage imageView = new ClickableImage(img, ClickableImage.NO_CLICK_ACTION);
              
              DrawImage(imageView, x, y, scale);
       }
       
       public void DrawImage(Image img, double x, double y){
              DrawImage(img, x, y, ClickableImage.ORIGINAL_SIZE_SCALAR);
       }
       
       //Overloads for ImageView type
       
       public void DrawImage(ClickableImage imgRenderer, double x, double y, double scale){
              //Set arguments
              imgRenderer.setX(x);
              imgRenderer.setY(y);
              
              //Set widths
              imgRenderer.scale(scale);
              
              DrawImage(imgRenderer);
       }
       
       public void DrawImage(ClickableImage imgRenderer, double x, double y){
              DrawImage(imgRenderer, x, y, ClickableImage.ORIGINAL_SIZE_SCALAR);
       }
       
       public void DrawImage(ClickableImage imgRenderer){
              this.root.getChildren().add(imgRenderer);
       }
       
       
       
       public void DrawButton(Button b, double x, double y){
              b.setTranslateX(x);
              b.setTranslateY(y);
              
              this.root.getChildren().add(b);
       }
       
       public void DrawButton(ImageButton b, double x, double y){
              DrawButton((Button)b, x, y);
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
       

       //this code generates a random map, draws it, makes a path between two points, then prints that map
       //Currently, the randomMap class needs generate greater spread between locations, 
       //Also, trying to decide if a node should have multiple roads from single locations. Might make map too messy
       public void makeRandomMap() throws FileNotFoundException {
    	   RandomMap randomMap = new RandomMap();
           for (int i = 0; i < randomMap.getQuadrant1Size(); i++) {
         	  DrawNode(randomMap.getQuadrant1().get(i), DEFAULT_NODE_COLOR);
           }
           RoadNode start = randomMap.getQuadrant1().get(0);
           RoadNode end = randomMap.getQuadrant1().get(11);
           Path path = start.getShortestPath(end);
           for (RoadNode node : path) {
         	  DrawChosenNode(node, path, DEFAULT_CHOSEN_NODE_COLOR);
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
           DrawNode(startnode, DEFAULT_NODE_COLOR); //whatcomcc      <----- CURRENT STARTNODE
           DrawNode(storage, DEFAULT_NODE_COLOR); //storage
           DrawNode(walmart, DEFAULT_NODE_COLOR); //walmart
           DrawNode(jewelry, DEFAULT_NODE_COLOR); //jewelry
           DrawNode(apartments, DEFAULT_NODE_COLOR); //apartments
           DrawNode(dental, DEFAULT_NODE_COLOR); //dental
           DrawNode(end, DEFAULT_NODE_COLOR); //target               <----- CURRENT END POINT
           DrawNode(winco,DEFAULT_NODE_COLOR); //winco
           DrawNode(mcdonalds, DEFAULT_NODE_COLOR); //mcdonalds          

           //Solve for the shortest path between 'root' and 'end'
           Path path = startnode.getShortestPath(end);
           
           //Boolean if true will show only the Chosen path from startnode and end node.
           //Boolean if false will display all the nodes on the map
           boolean flipNodes = true;
           
           //Draw nodes that were chosen in a Green color
           if (flipNodes) {
        	   for(RoadNode node : path){ //Iterate through the list of chosen nodes
                	  DrawChosenNode(node, path, DEFAULT_CHOSEN_NODE_COLOR);
           	   }
           } else {
               DrawNode(t1, DEFAULT_ROAD_NODE_COLOR);	  DrawNode(t101, DEFAULT_ROAD_NODE_COLOR);	  DrawNode(m1, DEFAULT_ROAD_NODE_COLOR);
               DrawNode(t2, DEFAULT_ROAD_NODE_COLOR);	  DrawNode(t102, DEFAULT_ROAD_NODE_COLOR);	  DrawNode(m2, DEFAULT_ROAD_NODE_COLOR);
               DrawNode(t3, DEFAULT_ROAD_NODE_COLOR);	  DrawNode(t103, DEFAULT_ROAD_NODE_COLOR);	  DrawNode(m3, DEFAULT_ROAD_NODE_COLOR);
               DrawNode(t4, DEFAULT_ROAD_NODE_COLOR);	  DrawNode(t104, DEFAULT_ROAD_NODE_COLOR);	  DrawNode(m4, DEFAULT_ROAD_NODE_COLOR);
               DrawNode(t5, DEFAULT_ROAD_NODE_COLOR);	  DrawNode(t105, DEFAULT_ROAD_NODE_COLOR);	  DrawNode(m5, DEFAULT_ROAD_NODE_COLOR);
               DrawNode(t6, DEFAULT_ROAD_NODE_COLOR);	  DrawNode(t106, DEFAULT_ROAD_NODE_COLOR);	  DrawNode(m6, DEFAULT_ROAD_NODE_COLOR);
               DrawNode(wcc1, DEFAULT_ROAD_NODE_COLOR);   DrawNode(b1, DEFAULT_ROAD_NODE_COLOR);      DrawNode(d1, DEFAULT_ROAD_NODE_COLOR);
               DrawNode(wcc2, DEFAULT_ROAD_NODE_COLOR);   DrawNode(b2, DEFAULT_ROAD_NODE_COLOR);      DrawNode(d2, DEFAULT_ROAD_NODE_COLOR);
               DrawNode(wcc3, DEFAULT_ROAD_NODE_COLOR);   DrawNode(b3, DEFAULT_ROAD_NODE_COLOR);	  DrawNode(d3, DEFAULT_ROAD_NODE_COLOR);
               DrawNode(wcc4, DEFAULT_ROAD_NODE_COLOR);   DrawNode(b4, DEFAULT_ROAD_NODE_COLOR);	  DrawNode(d4, DEFAULT_ROAD_NODE_COLOR);
               DrawNode(wcc5, DEFAULT_ROAD_NODE_COLOR);   DrawNode(b5, DEFAULT_ROAD_NODE_COLOR);	  DrawNode(d5, DEFAULT_ROAD_NODE_COLOR);
               DrawNode(wcc6, DEFAULT_ROAD_NODE_COLOR);   DrawNode(wm1, DEFAULT_ROAD_NODE_COLOR);     DrawNode(d6, DEFAULT_ROAD_NODE_COLOR);
               DrawNode(j1, DEFAULT_ROAD_NODE_COLOR); 	  DrawNode(wnc1, DEFAULT_ROAD_NODE_COLOR);	  DrawNode(d7, DEFAULT_ROAD_NODE_COLOR);
               DrawNode(j2, DEFAULT_ROAD_NODE_COLOR);	  DrawNode(wnc2, DEFAULT_ROAD_NODE_COLOR);	  DrawNode(d8, DEFAULT_ROAD_NODE_COLOR);
               DrawNode(j3, DEFAULT_ROAD_NODE_COLOR);	  DrawNode(wnc3, DEFAULT_ROAD_NODE_COLOR);	  DrawNode(d9, DEFAULT_ROAD_NODE_COLOR);
               DrawNode(j4, DEFAULT_ROAD_NODE_COLOR);  	  DrawNode(wnc4, DEFAULT_ROAD_NODE_COLOR);    DrawNode(apt1, DEFAULT_ROAD_NODE_COLOR);
               DrawNode(j5, DEFAULT_ROAD_NODE_COLOR);	  DrawNode(wcc101, DEFAULT_ROAD_NODE_COLOR);  DrawNode(apt2, DEFAULT_ROAD_NODE_COLOR);
               DrawNode(j6, DEFAULT_ROAD_NODE_COLOR);	  DrawNode(wcc102, DEFAULT_ROAD_NODE_COLOR);  DrawNode(tm1, DEFAULT_ROAD_NODE_COLOR);
               DrawNode(j7, DEFAULT_ROAD_NODE_COLOR);	  DrawNode(wcc1, DEFAULT_ROAD_NODE_COLOR);    DrawNode(tm2, DEFAULT_ROAD_NODE_COLOR);
               DrawNode(mcd1, DEFAULT_ROAD_NODE_COLOR);
               DrawNode(mcd2, DEFAULT_ROAD_NODE_COLOR);
               DrawNode(mcd3, DEFAULT_ROAD_NODE_COLOR);
               DrawNode(mcd4, DEFAULT_ROAD_NODE_COLOR);
               DrawNode(mcd5, DEFAULT_ROAD_NODE_COLOR);
               DrawNode(mcd6, DEFAULT_ROAD_NODE_COLOR);
               DrawNode(mcd7, DEFAULT_ROAD_NODE_COLOR);
               DrawNode(mcd8, DEFAULT_ROAD_NODE_COLOR);
           } 

       }

       //DEFINE WHAT TO DRAW HERE
       public void start(Stage primaryStage) throws FileNotFoundException {
              this.root = new Group();


              //code to make RandomMap
              makeRandomMap();

	      //Use path seperator so that it is cross platform
              Image myImage = LoadImage("." + File.separator + "whatcomcc.jpg");
              DrawImage(myImage, 0,0,0.83);

              
              
              //code to draw Mathew's Map
              //makeMap();
              
              //code to launch the premadeMap
              premadeMap();

              
              //Draw to screen
              Scene scene = new Scene(this.root, SCREENX, SCREENY);     

              primaryStage.setScene(scene);
              primaryStage.show();

              
       }
       
       
       public static void main(String[] args){
          launch(args);
       }
};
