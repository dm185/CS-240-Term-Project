import java.util.ArrayList;
import java.util.LinkedList;

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
         * Takes in a destination node and calcluates the shortest distance
         * from 'this' to 'dest' using the dikjta method.
         *
         * Returns a LinkedList<Node> that contains a in-sequence
         * order of the fastest nodes to traverse
       ***/
       public LinkedList<RoadNode> getShortestPath(RoadNode dest){
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
                     for(int i = 0; i < curNode.links.size(); i++){
                            Connection con = curNode.links.get(i);
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
              
              //Backtrace to create a list of in-order nodes to destination
              LinkedList<RoadNode> ret_list = new LinkedList<RoadNode>();
              RoadNode node = dest;
              while(node != null){
                     ret_list.addFirst(node);
                     node = node.prev;
              }
              
              return ret_list;
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
