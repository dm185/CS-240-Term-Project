import java.util.ArrayList;
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


    private static ArrayList<RoadNode> currentMap = null;
    private static Path currentPath = null;
};
