
/* 
This class allows the 'src' node and the 'dest' node to be linked and 
Given a MPH speed that must be followed to cross that area.
It also stores the time it would take to go from 'src' to 'dest'
based on the distance between the nodes and the MPH
*/
public class Connection{
       public Connection(RoadNode src, RoadNode destination, float mph){
              this.src = src;
              this.dest = destination;
              this.mph = mph;
              
              //Get the distance between the nodes and divide it by the mph
              //This means that the higher the MPH, the lower the cost and
              //the greater the distance, the greater the cost
              this.cost  = this.src.distanceBetweenNodes(this.dest) / this.mph;
              
              //Add this link to the link records of src and dest
              src.getLinks().add(this);
              dest.getLinks().add(this);
       }
       
       public RoadNode getSource(){
              return src;
       }
       
       public RoadNode getDest(){
              return dest;
       }
       
       public float getCost(){
              return cost;
       }
       
       public float getMPH(){
              return mph;
       }
       
       private RoadNode src;
       private RoadNode dest;
       private float mph;
       private float cost;
};
