import java.util.*;

// Creating an ArrayList and using the findLocation method to retrieve the index value 
// of the element in the list
public class FindRoadNode {
    public static void main(String[] args) {
        Location test = new Location();
        //ArrayList<String> Declaration
        List<String> al= new ArrayList<String>();
        //add method for String ArrayList
        al.add("Walmart");
        al.add("Dentist");
        al.add("Whatcom Community College");
        al.add("Fred");
        al.add("Anytime Fitness");
        System.out.println("Elements of ArrayList of String Type: "+al);
        int start = test.findLocation("Fred", al);
        System.out.println(start);
     }
}
