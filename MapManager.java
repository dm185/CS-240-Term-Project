import java.util.ArrayList;

import javax.swing.JOptionPane;

import java.lang.IllegalArgumentException;
import java.io.FileNotFoundException;

public class MapManager{
    public enum MapType{
        RANDOM,
        PREMADE
    }
  
    public static void DrawAllNodes(){
        for(RoadNode n : MapManager.currentMap){
            Drawer.DrawNode(n, Drawer.DEFAULT_NODE_COLOR);
        }
        if(currentPath != null){
            for(RoadNode n : MapManager.currentPath){
                Drawer.DrawChosenNode(n, MapManager.currentPath, Drawer.DEFAULT_CHOSEN_NODE_COLOR);
            }
        }
    }



    public static void SwapMap(MapType type){
        MapManager.currentPath = null;
        switch(type){
            case RANDOM:
                try{
                    MapManager.currentMap = RandomMap.get().getQuadrant1();
                } catch(FileNotFoundException e){
                    System.out.printf("Files required for RandomMap not found! Please redownload the program ...\n");
                    final int EXIT_FAILURE = 1;
                    System.exit(1);
                }
            break;

            case PREMADE:
                MapManager.currentMap = PremadeMap.getNodeList();
            break;
        }
    }

    //Wrapper for RoadNode.getShortestPath
    public static Path GetShortestPath(RoadNode start, RoadNode end){
        for(RoadNode n : MapManager.currentMap){
            n.resetPathData();
        }
        MapManager.currentPath = start.getShortestPath(end);
        return MapManager.currentPath;
    }

    //Wrapper for MapManager.GetShortestPath
    public static Path GetShortestPath(int start, int end){
        RoadNode source = MapManager.getNode(start);
        RoadNode target = MapManager.getNode(end);
        GetShortestPath(source, target);
        return MapManager.currentPath;
    }

    //Gets a node at a certain index
    public static RoadNode getNode(int index){
        return MapManager.currentMap.get(index);
    }

    public static int getNumberOfLocations() {
    	return currentMap.size();
    }

    //function to print all the locations in a map in a JOptionPane dialog box
    public static void printMapLocations() {
       int size = MapManager.getNumberOfLocations();
       String output = "";
       int listIndex = 1;
       for (int i = 0; i < size; i++) {
            RoadNode location = MapManager.getNode(i);
    	    String name = location.getName();
            if(name == null){ //Dont print the location if this is a intercetion
                continue;
            }
    	    String locationNumber = String.valueOf(listIndex);
              listIndex++;
    	    output += locationNumber + " " + name + "\n";
       }
       JOptionPane.showMessageDialog(null, output);
    }
    
    
    public static int findLocation(String location) {
 	   String compare;
 	   RoadNode road;
 	   int size = currentMap.size();
 	   for (int i = 0; i < size; i++) {
 		   road = MapManager.getNode(i);
 		   compare = road.getName();
 		   if (location.equalsIgnoreCase(compare) == true) {
 			   JOptionPane.showMessageDialog(null, "location found");
 			   return i;
 		   }    		  
 	   }
 	   JOptionPane.showMessageDialog(null, "location not found");
		   return -1; 
    }
    

    private static ArrayList<RoadNode> currentMap = null;
    private static Path currentPath = null;
};
