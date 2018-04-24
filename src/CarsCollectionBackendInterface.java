
public interface CarsCollectionBackendInterface {
    Manufacturer[] loadCars(String url) throws CarsCollectionBackendException;
    void saveCars(String url, Manufacturer[] manufacturers) throws CarsCollectionBackendException;
}
