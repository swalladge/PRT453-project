package Car;

import java.util.*;
/**
 * Stores information about cars. Also calculates the cars age from the current date.
 * @
 *
 * PUBLIC FEATURES:
 * // Constructors
 *    public Car.Car()
 *    public Car.Car(String man, String mod, String info)
 *
 * // Methods
 *    public int getAge()
 *    public String getInformation()
 *    public String getInformation()
 *    public double getKilometers()
 *    public String getManufacturer()
 *    public String getModel()
 *    public int getPrice()
 *    public int getYear()
 *    public void setInformation(String info)
 *    public void setKilometers(double km)
 *    public void setManufacturer(String man)
 *    public void setModel(String mod)
 *    public void setPrice(int cost)
 *    public void setYear(int yr)
 *
 * COLLABORATORS:
 *
 * @version 1.0, 16 Oct 2004
 * @author Adam Black
 */
public class Car implements java.io.Serializable
{
    private String model;
    private Manufacturer manufacturer;
    private String information;
    private int year;
    private double price;
    private double kilometers;

    public Car(){}

    /**
     * @param manName manufacturers name
     * @param mod model name
     * @param info extra information about the car
     */
    public Car(String manName, String mod, String info)
    {
        model = mod;
        manufacturer = new Manufacturer(manName);
        information = info;
    }

    /**
     * calculates using current year - model year
     */
    public int getAge()
    {
        GregorianCalendar calendar = new GregorianCalendar();
        return (calendar.get(Calendar.YEAR) - year);
    }

    public String getInformation()
    {
        return information;
    }

    public double getKilometers()
    {
        return kilometers;
    }

    public String getManufacturer()
    {
        return manufacturer.toString();
    }

    public String getModel()
    {
        return model;
    }

    public double getPrice()
    {
        return price;
    }

    public int getYear()
    {
        return year;
    }

    public void setInformation(String info)
    {
        information = info;
    }

    public void setKilometers(double km)
    {
        kilometers = km;
    }

    public void setManufacturer(String man)
    {
        manufacturer = new Manufacturer(man);
    }

    public void setManufacturerObj(Manufacturer man) {
        this.manufacturer = man;
    }

    public Manufacturer getManufacturerObj() {
        return this.manufacturer;
    }

    public void setModel(String mod)
    {
        model = mod;
    }

    public void setPrice(double cost)
    {
        price = cost;
    }

    public void setYear(int yr)
    {
        year = yr;
    }
}
