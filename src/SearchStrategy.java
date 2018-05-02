import javax.swing.*;
import Car.Car;

import java.util.List;

/**
 * defines a search strategy that is designed to be injected into a SearchPanel
 * it provides panel for the controls and any strategy specific information,
 * a method to reset the controls if the user clicks the reset button,
 * and a method to run the search based on it's state and return a list of cars
 */
public interface SearchStrategy {
    List<Car> doSearch();
    JPanel getPanel();
    void reset();
}
