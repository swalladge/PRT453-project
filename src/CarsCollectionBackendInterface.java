import Car.Car;

import java.util.List;

public interface CarsCollectionBackendInterface {
    List<Car> loadCars(String url) throws CarsCollectionBackendException;
    void saveCars(String url, List<Car> cars) throws CarsCollectionBackendException;
}
