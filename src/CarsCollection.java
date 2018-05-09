import Car.Car;
import Range.Range;

import java.util.*;

/**
 * Stores manufacturers objects, and performs searches
 * @
 *
 * PUBLIC FEATURES:
 * // Constructors
 *    public CarsCollection()
 *    public CarsCollection(Manufacturer man)
 *
 * // Methods
 *    public int addCar(Car c)
 *    public int carsCount()
 *    public int manufacturerCount()
 *    public Car[] getAllCars()
 *    public Manufacturer[] getAllManufacturers()
 *    public double getAverageAge()
 *    public double getAverageDistance()
 *    public double getAveragePrice()
 *    public void loadCars(String file) throws IOException, ClassNotFoundException
 *    public void saveCars(String file) throws IOException
 *    public Car[] search(int minPrice, int maxPrice, double minDistance, double maxDistance)
 *    public Car[] search(int minAge, int maxAge)
 *
 * COLLABORATORS:
 *    Manufacturer
 *
 * @version 1.0, 16 Oct 2004
 * @author Adam Black
 */
public class CarsCollection implements CarsCollectionInterface
{

    private List<Car> cars = new ArrayList<Car>();
    private CarsCollectionBackendInterface backend;

    public CarsCollection(CarsCollectionBackendInterface backend){
        this.backend = backend;
    }

    /**
     * adds a car to a CarCollection and files it in an appropriate manufacturer, or creates a new
     * manufacturer if none exist for the car
     *
     * @param c car to add to collection
     * @return either one of NO_ERROR, CARS_MAXIMUM_REACHED, or MANUFACTURERS_MAXIMUM_REACHED
     */
    public int addCar(Car c)
    {
        if (!this.cars.contains(c)) {
            this.cars.add(c);
        }
        return NO_ERROR;
    }

    /**
     * get the entire count of cars in the CarsCollection from all manufacturers
     *
     * @return integer representing total number of cars
     */
    public int carsCount()
    {
        return this.cars.size();
    }

    /**
     * get number of manufacturers in CarsCollection
     *
     * @return number of manufacturers
     */
    public int manufacturerCount()
    {
        HashSet<String> mans = new HashSet<String>();
        for (Car car: this.cars) {
            if (!mans.contains(car.getManufacturer())) {
                mans.add(car.getManufacturer());
            }
        }
        return mans.size();
    }

    /**
     * get all cars in the CarsCollection from all manufacturers
     *
     * @return entire collection of cars in CarsCollection from all manufacturers
     */
    public List<Car> getAllCars()
    {
        return this.cars;
    }

    /**
     * calculate the average age from the entire collection of cars
     *
     * @return value indicating the average age of all the cars in the collection
     */
    public double getAverageAge()
    {
        return this.cars.stream().mapToDouble((c) -> c.getAge()).average().orElse(0);
    }

    /**
     * calculate the average distance travelled from the entire collection of cars
     *
     * @return value indicating the average distance travelled of all the cars in the collection
     */
    public double getAverageDistance()
    {
        return this.cars.stream().mapToDouble((c) -> c.getKilometers()).average().orElse(0);
    }

    /**
     * calculate the average price from the entire collection of cars
     *
     * @return value indicating the average price of all the cars in the collection
     */
    public double getAveragePrice()
    {
        return this.cars.stream().mapToDouble((c) -> c.getPrice()).average().orElse(0);
    }

    /**
     * load entire collectoin of cars into the manufacturer object from a data file
     *
     * @param file filename of binary file to load car data from
     */
    public void loadCars(String file) throws CarsCollectionBackendException
    {
        this.cars = backend.loadCars(file);
    }

    /**
     * Save all cars to a binary file
     *
     * @param file of the binary file
     */
    public void saveCars(String file) throws CarsCollectionBackendException
    {
        backend.saveCars(file, this.cars);
    }

    /**
     * search by price and distance travelled
     *
     * @param priceRange price range of car
     * @param distanceRange distance travelled range of car
     * @return array of Car objects that matched the search criteria
     */
    public List<Car> search(Range priceRange, Range distanceRange)
    {
        List<Car> result = new ArrayList<Car>();

        for (Car car: this.cars)
        {
            Double price = car.getPrice();
            Double distance = car.getKilometers();

            if (priceRange.contains(price.intValue()) && distanceRange.contains(distance.intValue())) {
                    result.add(car);
            }
        }

        return result;
    }

    /**
     * search by age
     *
     * @param age age range of car
     * @return array of Car objects that matched the search criteria
     */
    public List<Car> search(Range ageRange)
    {
        List<Car> result = new ArrayList<Car>();
        for (Car car: this.cars) {
            int age = car.getAge();
            if (ageRange.contains(age)) {
                result.add(car);
            }
        }
        return result;
    }

}
