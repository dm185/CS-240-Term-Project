import java.util.ArrayList;

public class PremadeMap{
    private final float SPEED_LIMIT_1 = 50;
    private final float SPEED_LIMIT_2 = 40;


    private PremadeMap(){
        this.nodes = new ArrayList<RoadNode>();

        //Points of interest (Replace with startnode and end for shortest-path)
        RoadNode startnode = this.AddRoadNode("WhatcomCC", 150, 200); //whatcomcc         <----- CURRENT STARTNODE
        RoadNode mcdonalds = this.AddRoadNode("McDonalds", 300, 525); //mcdonalds
        RoadNode storage = this.AddRoadNode("Storage", 521, 550); //storage
        RoadNode walmart = this.AddRoadNode("Walmart", 370, 30); //walmart
        RoadNode jewelry = this.AddRoadNode("Jewelry", 500, 200); //jewelry
        RoadNode apartments = this.AddRoadNode("Apartments", 520, 400); //apartments
        RoadNode dental = this.AddRoadNode("Dental", 350, 325); //dental
        RoadNode end = this.AddRoadNode("Target", 140, 430); //target				<----- CURRENT END POINT
        RoadNode winco = this.AddRoadNode("Winco", 370, 440); //winco
    
        //All the nodes on the roads
        RoadNode t1 = this.AddRoadNode(null, 140, 418);               RoadNode m1 = this.AddRoadNode(null, 309, 518);
        RoadNode t2 = this.AddRoadNode(null, 210, 418);               RoadNode m2 = this.AddRoadNode(null, 310, 440);
        RoadNode t3 = this.AddRoadNode(null, 209, 360);			      RoadNode m3 = this.AddRoadNode(null, 311, 360);
        RoadNode t4 = this.AddRoadNode(null, 208, 298);		 	      RoadNode m4 = this.AddRoadNode(null, 311, 308);
        RoadNode t5 = this.AddRoadNode(null, 202, 292);               RoadNode m5 = this.AddRoadNode(null, 311, 200);
        RoadNode t6 = this.AddRoadNode(null, 214, 292);        	      RoadNode m6 = this.AddRoadNode(null, 312, 40);
        RoadNode t101 = this.AddRoadNode(null, 118, 430);             RoadNode j1 = this.AddRoadNode(null, 500, 204);
        RoadNode t102 = this.AddRoadNode(null, 118, 420);             RoadNode j2 = this.AddRoadNode(null, 443, 203);
        RoadNode t103 = this.AddRoadNode(null, 100, 416);             RoadNode j3 = this.AddRoadNode(null, 443, 308);
        RoadNode t104 = this.AddRoadNode(null, 100, 360);             RoadNode j4 = this.AddRoadNode(null, 458, 340);
        RoadNode t105 = this.AddRoadNode(null, 95, 314);              RoadNode j5 = this.AddRoadNode(null, 458, 362);
        RoadNode t106 = this.AddRoadNode(null, 100, 298);             RoadNode j6 = this.AddRoadNode(null, 458, 398);
        RoadNode wcc1 = this.AddRoadNode(null, 195, 200);             RoadNode j7 = this.AddRoadNode(null, 458, 416);
        RoadNode wcc2 = this.AddRoadNode(null, 200, 210);             RoadNode wnc1 = this.AddRoadNode(null, 335, 440);
        RoadNode wcc3 = this.AddRoadNode(null, 208, 240);             RoadNode wnc2 = this.AddRoadNode(null, 370, 416);
        RoadNode wcc4 = this.AddRoadNode(null, 208, 287);             RoadNode wnc3 = this.AddRoadNode(null, 338, 420);
        RoadNode wcc5 = this.AddRoadNode(null, 210, 200);             RoadNode wnc4 = this.AddRoadNode(null, 345, 416);
        RoadNode wcc6 = this.AddRoadNode(null, 202, 195);             RoadNode b1 = this.AddRoadNode(null, 521, 493);
        RoadNode d1 = this.AddRoadNode(null, 350, 308);           	  RoadNode b2 = this.AddRoadNode(null, 475, 492);
        RoadNode d2 = this.AddRoadNode(null, 365, 308);           	  RoadNode b3 = this.AddRoadNode(null, 458, 500);
        RoadNode d3 = this.AddRoadNode(null, 365, 337);           	  RoadNode b4 = this.AddRoadNode(null, 432, 513);
        RoadNode d4 = this.AddRoadNode(null, 377, 347);           	  RoadNode b5 = this.AddRoadNode(null, 400, 520);
        RoadNode d5 = this.AddRoadNode(null, 379, 360);           	  RoadNode apt1 = this.AddRoadNode(null, 512, 399);
        RoadNode d6 = this.AddRoadNode(null, 400, 361);           	  RoadNode apt2 = this.AddRoadNode(null, 512, 364);
        RoadNode d7 = this.AddRoadNode(null, 400, 375);           	  RoadNode tm1 = this.AddRoadNode(null, 300, 438);
        RoadNode d8 = this.AddRoadNode(null, 417, 379);           	  RoadNode tm2 = this.AddRoadNode(null, 288, 418);
        RoadNode d9 = this.AddRoadNode(null, 416, 416);           	  RoadNode wm1 = this.AddRoadNode(null, 370, 40);  
        RoadNode mcd1 = this.AddRoadNode(null, 300, 518);	     	  RoadNode wcc101 = this.AddRoadNode(null, 115, 219);
        RoadNode mcd2 = this.AddRoadNode(null, 280, 518);	     	  RoadNode wcc102 = this.AddRoadNode(null, 100, 219);
        RoadNode mcd3 = this.AddRoadNode(null, 250, 540);
        RoadNode mcd4 = this.AddRoadNode(null, 195, 535);
        RoadNode mcd5 = this.AddRoadNode(null, 158, 537);
        RoadNode mcd6 = this.AddRoadNode(null, 157, 503);
        RoadNode mcd7 = this.AddRoadNode(null, 140, 467);
        RoadNode mcd8 = this.AddRoadNode(null, 118, 445);        
            
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
    }

    //Allocates a new road node and returns it. Makes sure to register the node with the master node list.
    //This allows aliasing
    private RoadNode AddRoadNode(String name, float x, float y){
        RoadNode new_node = new RoadNode(name, x, y);
        this.nodes.add(new_node);
        return new_node;
    }
    
    private static PremadeMap get(){
        if(only_allowed_instance == null){
            only_allowed_instance = new PremadeMap();
        }

        return only_allowed_instance;
    }

    public static ArrayList<RoadNode> getNodeList(){
        return PremadeMap.get().nodes;
    }

    private static PremadeMap only_allowed_instance = null;
    private ArrayList<RoadNode> nodes;
};
