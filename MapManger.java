public class MapManager{
    public enum MapType{
        RANDOM,
        PREMADE
    }
  
    public static void DrawAllNodes(){
        for(RoadNode n : this.nodes){
            Drawer.DrawNode(n, Drawer.DEFAULT_NODE_COLOR);
        }

        if(currentPath != null){
            for(RoadNode n : currentPath){
                Drawer.DrawChosenNode(n, Drawer.DEFAULT_CHOSEN_NODE_COLOR);
            }
        }
    }

    public static void SwapMap(MapType type){
        switch(type){
            case RANDOM:
                this.nodes = RandomMap.get().getQuadrant1();
            break;

            case PREMADE:
                this.nodes = PremadeMap.getNodeList();
            break;

            default:
                throw new IllegalArugmentException("ERR: Legal map types are 'RANDOM' and 'PREMADE'");
            break;
        }
    }

    //Wrapper for RoadNode.getShortestPath
    public static Path GetShortestPath(RoadNode start, RoadNode end){
        this.currentPath = start.getShortestPath(end);
        return this.currentPath;
    }


    private static ArrayList<RoadNode> currentMap = null;
    private static Path currentPath = null;
};
