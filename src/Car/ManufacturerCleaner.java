package Car;

public class ManufacturerCleaner implements CarFieldCleaner {
    @Override
    public void clean(CarFields carFields) {
        String man = carFields.getManufacturer();
        man = man.trim();
        carFields.setManufacturer(man);
    }
}
