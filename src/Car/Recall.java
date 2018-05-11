import Car.Car;
import java.util.*;
package Car;

//Momento of the Momento Pattern - Orignator is in the Booking.java
###################################################################
public class Recall {
  private final String BookingState;
  public Recall(String state) {
    BookingState = state;
  }
  public String getSavedState() {
    return BookingState;
  }
}
