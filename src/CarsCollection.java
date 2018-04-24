import Car.Car;

import java.util.*;
import java.io.*;

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
 *    public int addCar(Car.Car c)
 *    public int carsCount()
 *    public int manufacturerCount()
 *    public Car.Car[] getAllCars()
 *    public Manufacturer[] getAllManufacturers()
 *    public double getAverageAge()
 *    public double getAverageDistance()
 *    public double getAveragePrice()
 *    public void loadCars(String file) throws IOException, ClassNotFoundException
 *    public void saveCars(String file) throws IOException
 *    public Car.Car[] search(int minPrice, int maxPrice, double minDistance, double maxDistance)
 *    public Car.Car[] search(int minAge, int maxAge)
 *
 * COLLABORATORS:
 *    Manufacturer
 *
 * @version 1.0, 16 Oct 2004
 * @author Adam Black
 */
public class CarsCollection implements CarsCollectionInterface
{

    private final int maxManufacturers = 20;
    private final int maxCars = 20;

    private Manufacturer[] manufacturer = new Manufacturer[0];
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
        Manufacturer man;
        String name = c.getManufacturer();
        int index = -1;
        int result = NO_ERROR;

        for (int i = 0; i < manufacturer.length; i++)
        {
            // if manufacturer already exists
            if (manufacturer[i].getManufacturerName().equalsIgnoreCase(name))
                index = i;
        }

        // if manufacturer doesn't exist
        if (index == -1)
        {
            if (manufacturer.length < maxManufacturers)
            {
                man = new Manufacturer(name, c);
                addManufacturer(man);
            }
            else
                result = MANUFACTURERS_MAXIMUM_REACHED;
        }
        else
        {
            if (manufacturer[index].carCount() < maxCars)
                manufacturer[index].addCar(c);
            else
                result = CARS_MAXIMUM_REACHED;
        }

        return result;
    }

    /**
     * add a Manufacturer object to the CarsCollection
     *
     * @param man Manufacturer object to add
     */
    private void addManufacturer(Manufacturer man)
    {
        manufacturer = resizeArray(manufacturer, 1);
        manufacturer[manufacturer.length - 1] = man;
    }

    /**
     * get the entire count of cars in the CarsCollection from all manufacturers
     *
     * @return integer representing total number of cars
     */
    public int carsCount()
    {
        int count = 0;

        for (int i = 0; i < manufacturer.length; i++)
            count += manufacturer[i].carCount();

        return count;
    }

    /**
     * get number of manufacturers in CarsCollection
     *
     * @return number of manufacturers
     */
    public int manufacturerCount()
    {
        return manufacturer.length;
    }

    /**
     * get all cars in the CarsCollection from all manufacturers
     *
     * @return entire collection of cars in CarsCollection from all manufacturers
     */
    public Car[] getAllCars()
    {
        Vector result = new Vector();
        Car[] car;
        for (int i = 0; i < manufacturer.length; i++)
        {
            car = manufacturer[i].getAllCars();
            for (int j = 0; j < car.length; j++)
            {
                result.addElement(car[j]);
            }
        }

        return CarSalesSystem.vectorToCar(result);
    }

    /**
     * get manufacturers
     *
     * @return all manufacturers in the collection
     */
    public Manufacturer[] getAllManufacturers()
    {
        return manufacturer;
    }

    /**
     * calculate the average age from the entire collection of cars
     *
     * @return value indicating the average age of all the cars in the collection
     */
    public double getAverageAge()
    {
        Car[] car;
        double result = 0;
        int count = 0;

        for (int i = 0; i < manufacturer.length; i++)
        {
            car = manufacturer[i].getAllCars();
            for (int j = 0; j < car.length; j++)
            {
                result += car[j].getAge();
                count++;
            }
        }
        if (count == 0)
            return 0;
        else
            return (result / count);
    }

    /**
     * calculate the average distance travelled from the entire collection of cars
     *
     * @return value indicating the average distance travelled of all the cars in the collection
     */
    public double getAverageDistance()
    {
        Car[] car;
        double result = 0;
        int count = 0;

        for (int i = 0; i < manufacturer.length; i++)
        {
            car = manufacturer[i].getAllCars();
            for (int j = 0; j < car.length; j++)
            {
                result += car[j].getKilometers();
                count++;
            }
        }
        if (count == 0)
            return 0;
        else
            return (result / count);
    }

    /**
     * calculate the average price from the entire collection of cars
     *
     * @return value indicating the average price of all the cars in the collection
     */
    public double getAveragePrice()
    {
        Car[] car;
        double result = 0;
        int count = 0;

        for (int i = 0; i < manufacturer.length; i++)
        {
            car = manufacturer[i].getAllCars();
            for (int j = 0; j < car.length; j++)
            {
                result += car[j].getPrice();
                count++;
            }
        }
        if (count == 0)
            return 0;
        else
            return (result / count);
    }

    /**
     * load entire collectoin of cars into the manufacturer object from a data file
     *
     * @param file filename of binary file to load car data from
     */
    public void loadCars(String file) throws CarsCollectionBackendException
    {
        manufacturer = backend.loadCars(file);
    }

    /**
     * resize the manufacturer array while maintaining data integrity
     *
     * @param inArray array to resize
     * @param extendBy indicates how many elements should the array be extended by
     * @return the resized Manufacturer array
     */
    private Manufacturer[] resizeArray(Manufacturer[] inArray, int extendBy)
    {
        Manufacturer[] result = new Manufacturer[inArray.length + extendBy];

        for (int i = 0; i < inArray.length; i++)
        {
            result[i] = inArray[i];
        }

        return result;
    }

    /**
     * Save all cars to a binary file
     *
     * @param file of the binary file
     */
    public void saveCars(String file) throws CarsCollectionBackendException
    {
        backend.saveCars(file, manufacturer);
    }

    /**
     * search by price and distance travelled
     *
     * @param minPrice minimum price of car
     * @param maxPrice maximum price of car
     * @param minDistance minimum distance travelled by car
     * @param maxDistance maximum distance travelled by car
     * @return array of Car.Car objects that matched the search criteria
     */
    public Car[] search(double minPrice, double maxPrice, double minDistance, double maxDistance)
    {
        Vector result = new Vector();
        Double price;
        double distance;
        Car[] car;
        car = getAllCars();

        for (int i = 0; i < car.length; i++)
        {
            price = car[i].getPrice();
            distance = car[i].getKilometers();

            if (price >= minPrice && price <= maxPrice)
                 if (distance >= minDistance && distance <= maxDistance)
                    result.add(car[i]);
        }

        return CarSalesSystem.vectorToCar(result);
    }

    /**
     * search by age
     *
     * @param minAge minimum age of car
     * @param maxAge maximum age of car
     * @return array of Car.Car objects that matched the search criteria
     */
    public Car[] search(int minAge, int maxAge)
    {
        Car[] car;
        car = getAllCars();
        Vector result = new Vector();

        /* Putting the if statement first will increase effeciency since it isn't constantly
        checking the condition for each loop. It does use almost double the amount of code though */
        if (maxAge == -1)
        {
            for (int i = 0; i < car.length; i++)
            {
                if (car[i].getAge() >= minAge)
                {
                    result.addElement(car[i]);
                }
            }
        }
        else
        {
            for (int i = 0; i < car.length; i++)
            {
                if (car[i].getAge() >= minAge && car[i].getAge() <= maxAge)
                {
                    result.addElement(car[i]);
                }
            }
        }

        return CarSalesSystem.vectorToCar(result);
    }
}
