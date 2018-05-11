import Car.Car;
import java.util.*;
package Car;

//Context
##############################################################
public class Booking {
  private State state;
  private Booking(State state) {
    this.state= state;
  }
  public void addState() {
    state.StockState(this);
  }
  public void setState(State state) {
    this.state = state;
  }
  public State getState() {
    return state;
  }

//Orignator for Momento Pattern
##############################################################  
  public String BookingContents;
  public void setState(String contents) {
    this.BookingContents = contents;
  }
  public Recall save() {
    return new Recall(BookingContents);
  }
  public void restoreToState(Recall memento) {
    BookingContents = memento.getSavedState();
  }
}

##############################################################
//State
private interface State {
  public void StockState(Booking context);
}



##############################################################
//Concrete State 1
public class ColourCount implements State {
  public void StockState(Booking context) {
    context.setState(new StockCount());
  }
}

//Concrete State 2
public class StockCount implements State {
  public void StockState(Booking context) {
    context.setState(new ColourCount());
  }
}