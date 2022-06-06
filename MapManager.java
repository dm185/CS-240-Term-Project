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
        if(MapManager.currentMap != PremadeMap.getNodeList() || MapManager.currentPath == null){ //Pre-made map does not print out paths if the quickest path is shown
            for(RoadNode n : MapManager.currentMap){
                Drawer.DrawNode(n, Drawer.DEFAULT_NODE_COLOR);
            }
        }

        if(currentPath != null){
            for(RoadNode n : MapManager.currentPath){
                Drawer.DrawChosenNode(n, MapManager.currentPath, Drawer.DEFAULT_CHOSEN_NODE_COLOR);
            }
        }
    }



    public static void SwapMap(MapType type){
        currentPath = null;
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
        MapManager.currentPath = start.getShortestPath(end);
        return MapManager.currentPath;
    }

    //Gets a node at a certain index
    public static RoadNode getNode(int index){
        return MapManager.currentMap.get(index);
    }

    public static int getNumberOfLocations() {
    	return currentMap.size();
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
