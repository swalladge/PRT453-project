package Car;

public class CarFields {
    private Integer year;
    private String manufacturer;
    private String model;
    private String info;
    private Double km;
    private Integer price;

    public CarFields(String manufacturer, String model, String info, Double km, Integer price, Integer year) {
        this.year = year;
        this.manufacturer = manufacturer;
        this.model = model;
        this.info = info;
        this.km = km;
        this.price = price;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Double getKm() {
        return km;
    }

    public void setKm(Double km) {
        this.km = km;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }
}
