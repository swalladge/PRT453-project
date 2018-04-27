import Car.Car;

import java.util.*;

/*The Decorator Pattern is used here to dynamically extend behaviour without sub classing*/
/*Abstract base class- component*/
public abstract class CarOptionals{
	String description;

	public String getDescription(){
		return description;
	}
}