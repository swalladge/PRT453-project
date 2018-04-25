import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import Car.Car;

public class CarsCollectionDataFileBackend implements CarsCollectionBackendInterface {
    @Override
    public List<Car> loadCars(String file) throws CarsCollectionBackendException {
        ArrayList<Car> cars = null;
        try {
            ObjectInputStream inp = new ObjectInputStream(new FileInputStream(file));
            cars = (ArrayList<Car>) inp.readObject();
            inp.close();
        } catch (java.io.EOFException e){
            // ignore, since this just means the file was empty (probably)
            cars = new ArrayList<Car>();
        } catch (IOException e) {
            throw new CarsCollectionBackendException("IO error loading from file. Please create a new empty '" + file + "' file.");
        } catch (ClassNotFoundException e) {
            throw new CarsCollectionBackendException("ClassNotFoundException: invalid data in data file.");
        }

        return cars;
    }

    @Override
    public void saveCars(String file, List<Car> cars) throws CarsCollectionBackendException {

        // original version sorted as well, so
        cars.sort(Comparator.comparing(car -> car.getManufacturer() + car.getModel()));

        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
            out.writeObject(cars);
            out.close();
        } catch (IOException e) {
            throw new CarsCollectionBackendException("Error saving to file.");
        }
    }
}
