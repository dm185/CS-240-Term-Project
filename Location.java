import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

public class Location {
   private int id;
   private String place;
   
   public String getPlace() {
      return place;
   }
   
   List<Location> locations = new ArrayList<>();
   
   // Search function iterates through a list of locations
   // If the location is present the location is returned 
   public Location findLocation(String place, List<Location> locations) {
      Iterator<Location> iterator = locations.iterator();
      while (iterator.hasNext()) {
         Location location = iterator.next();
         if (location.getPlace().equals(place)) {
            return location;
         }
      }
      return null;
   }
}