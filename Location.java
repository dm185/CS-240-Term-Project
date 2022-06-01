import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

public class Location {
   private int id;
   private String place;
   
   public String getPlace() {
      return place;
   }
   
   List<String> locations = new ArrayList<>();
   
   // Search function iterates through a list of locations
   // If the location is present the location's index is returned 
   public int findLocation(String place, List<String> locations) {
      int index = 0;
      
      for (int i = 0; i <locations.size(); i++) {
         String location = locations.get(i);
         if (location.equals(place)) 
            return i;
         }
      System.out.println("Location not found");
      return -1;
   }
}
