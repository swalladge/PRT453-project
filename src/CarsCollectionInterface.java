import java.util.List;

import Car.Car;
import Range.Range;

public interface CarsCollectionInterface {

    /**
     * this constant is returned by the addCar method to indicate the car was successfully
     * added to the Car.Car Sales System
     */
    public static final int NO_ERROR = 0;

    /**
     * this constant is returned by the addCar method to indicate the car wasn't successfully
     * added to the Car.Car Sales System because the manufacturer has reached it's maximum of
     * 20 cars
     */
    public static final int CARS_MAXIMUM_REACHED = 1;

    /**
     * this constant is returned by the addCar method to indicate the car wasn't successfully
     * added to the Car.Car Sales System because the system has reached it's maximum of
     * 20 manufacturers
     */
    public static final int MANUFACTURERS_MAXIMUM_REACHED = 2;

    public int addCar(Car c);
    public int carsCount();
    public int manufacturerCount();
    public List<Car> getAllCars();
    public double getAverageAge();
    public double getAverageDistance();
    public double getAveragePrice();
    public void loadCars(String file) throws CarsCollectionBackendException;
    public void saveCars(String file) throws CarsCollectionBackendException;
    public List<Car> search(Range priceRange, Range distanceRange);
    public List<Car> search(Range ageRange);
}
