import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Iterator;

public class RoadNode{
       public RoadNode(String name, float x, float y){
              this.x = x;
              this.y = y;
              this.name = name;
              this.links = new ArrayList<Connection>();
              this.visited = false;
              this.qeued = false;
              this.distance = Float.POSITIVE_INFINITY;
              this.prev = null;
       }
       
       /***
         * Gets the distance between this node and the other node
         * Returns a float
       ***/
       public float distanceBetweenNodes(RoadNode other){
              float xdiff = (other.getX() - this.x);
              float ydiff = (other.getY() - this.y);
              
              return (float)Math.sqrt(xdiff * xdiff + ydiff * ydiff);
       }
       
       public float getX(){
              return x;
       }
       
       public float getY(){
              return y;
       }
       
       public String getName(){
              return this.name;
       }
       
       /***
         * Internally marks the Node as visited so that it is not processed again
         * Returns nothing
       ***/
       public void markVisted(){
              this.visited = true;
       }
       
       /***
         * Internally unmarks the Node as visited so that it is processed again
         * Returns nothing
       ***/
       public void markUnvisited(){
              this.visited = false;
       }
       
       /***
         * Tells you if the node has been visited
         * Returns a boolean
       ***/
       public boolean isVisited(){
              return this.visited;
       }
       
       /***
         * Tells you if the node is currently waiting to be processed
         * Returns a boolean
       ***/
       private boolean isQeued(){
              return this.qeued;
       }
       
       /***
         * Connects this node to a destination node
         * Takes in the node that you are linking to as well as
         * the Miles Per Hour (float) that the road is set to
         * Returns nothing
       ***/
       public void AddConnection(RoadNode destination, float mph){
              //See Connection.java
              Connection con = new Connection(this, destination, mph);
       }
       
       /***
         * Return the list of nodes that are connected to this one
         * Returns a ArrayList
       ***/
       public ArrayList<Connection> getLinks(){
              return this.links;
       }
       
       /***
         * Return the node that last changed this one
         * Returns a RoadNode
       ***/
       public RoadNode getPrev(){
              return this.prev;
       }

       /***
         * Takes in a destination node and calcluates the shortest distance
         * from 'this' to 'dest' using the dikjta method.
         *
         * Returns a LinkedList<Node> that contains a in-sequence
         * order of the fastest nodes to traverse
       ***/
       public Path getShortestPath(RoadNode dest) {
              LinkedList<RoadNode> processingQeue = new LinkedList<RoadNode>();
              
              this.distance = 0;
              processingQeue.addFirst(this);
              
              //Keep checking nodes until all nodes have been processed
              while(processingQeue.peek() != null){
                     RoadNode curNode = processingQeue.pollFirst();
                     //Unmark this node as a node waiting to be processed
                     curNode.qeued = false;
                     
                     //If we have already processed this node then dont process it again
                     if(curNode.isVisited()){
                            continue;
                     }
              
                     //Iterate through all lines connecting this node to another node
                     for(Connection con : curNode.links){
                            //Get the neighboring node to this node
                            RoadNode neighbor = con.getDest(); 
                            //Links go both ways so make sure that the link we are viewing is not the current node
                            if(neighbor == curNode){
                                   neighbor = con.getSource();
                            }
                            
                            //If this node has not been processed then add it to the qeue
                            if(neighbor.isVisited() == false){
                                   //Reset the distance flags from any other time this function was called
                                   if(neighbor.isQeued() == false && neighbor.isVisited() == false){ //Reset if no other node has touched it yet
                                          neighbor.distance = Float.POSITIVE_INFINITY;
                                          neighbor.qeued = true;
                                          neighbor.prev = null;
                                          processingQeue.addLast(neighbor); //Add to the end of the qeue so that it is processed last
                                   }
                            }
                            
                             float new_distance = curNode.distance + con.getCost();
                     
                            //If we had taken the route of the curNode then would we walk a shorter distance?
                            if(new_distance < neighbor.distance){
                                   //Update the smallest path
                                   neighbor.distance = new_distance;
                                   neighbor.prev = curNode;
                            }
                            //Loop to next element
                     }
                     //All links have been added for processing. Set the current node as known
                     curNode.markVisted();
              }
              
              return new Path(dest);
       }
       
       private String name;
       //Position
       private float x;
       private float y;
       
       //Links to this node
       private ArrayList<Connection> links;
       
       //getShortestPath claims these
       private boolean visited;
       private boolean qeued;
       private float distance;
       private RoadNode prev;       //Last node that edited the distance
};


class Path implements Iterable<RoadNode>{
       public Path(RoadNode final_node){
              this.dest_node = final_node;
       }
       
       public Iterator<RoadNode> iterator() {
              return new PathIterator(this.dest_node);
       }
       
       public boolean contains(RoadNode search_node){
              RoadNode node = dest_node;
              
              while(node != null) {
                     if(node == search_node){
                            return true;
                     }
                     //Node has not been found. Keep searching
                     node = node.getPrev();
              }
              
              //Node was not found. Return false
              return false;
       }
       
       //Faster version of contains() but checks if both are in the structure
       public boolean containsPair(RoadNode search_node1, RoadNode search_node2){
              RoadNode node = dest_node;
              boolean n1_found = false;
              boolean n2_found = false;
              
              while(node != null) {
                     if(node == search_node1){
                            n1_found = true;
                     }
                     if(node == search_node2){
                            n2_found = true;
                     }
                     //Stop execution so that we dont waste having to check nodes if we already found a match
                     if(n1_found && n2_found){  
                            return true;
                     }
                     //Node has not been found. Keep searching
                     node = node.getPrev();
              }
              
              //Node was not found. Return false
              return false;
       }
       
       
       private RoadNode dest_node;
};

class PathIterator implements Iterator<RoadNode>{
    public PathIterator(RoadNode dest_node) {
       this.cursorn = dest_node;
    }
      
    // Checks if the next element exists
    public boolean hasNext() {
       return this.cursorn != null;
    }
      
    //Move to next RoadNode in existance
    public RoadNode next() {
       RoadNode ret = cursorn;
       this.cursorn = this.cursorn.getPrev();
       return ret;
    }
    
    private RoadNode cursorn;
    
}
