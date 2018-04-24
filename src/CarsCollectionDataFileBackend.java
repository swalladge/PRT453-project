import java.io.*;

public class CarsCollectionDataFileBackend implements CarsCollectionBackendInterface {
    @Override
    public Manufacturer[] loadCars(String file) throws CarsCollectionBackendException {
        Manufacturer[] manufacturers = null;
        try {
            ObjectInputStream inp = new ObjectInputStream(new FileInputStream(file));
            manufacturers = (Manufacturer[]) inp.readObject();
            inp.close();
        } catch (java.io.EOFException e){
            // ignore, since this just means the file was empty (probably)
            manufacturers = new Manufacturer[0];
        } catch (IOException e) {
            throw new CarsCollectionBackendException("IO error loading from file. Please create a new empty '" + file + "' file.");
        } catch (ClassNotFoundException e) {
            throw new CarsCollectionBackendException("ClassNotFoundException: invalid data in data file.");
        }

        return manufacturers;
    }

    @Override
    public void saveCars(String file, Manufacturer[] manufacturers) throws CarsCollectionBackendException {
        int flag = 0;
        int items = manufacturers.length;
        Manufacturer temp;

        if (manufacturers.length > 0)
        {
            do
            {
                flag = 0;
                for (int i = 0; i < items; i++)
                {
                    if (i + 1 < items)
                    {
                        if (manufacturers[i].getManufacturerName().compareTo(manufacturers[i + 1].getManufacturerName()) > 0)
                        {
                            temp = manufacturers[i];
                            manufacturers[i] = manufacturers[i + 1];
                            manufacturers[i + 1] = temp;
                            flag++;
                        }
                    }
                }
            }
            while (flag > 0);

            try {
                ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
                out.writeObject(manufacturers);
                out.close();
            } catch (IOException e) {
                throw new CarsCollectionBackendException("Error saving to file.");
            }
        }
    }
}
