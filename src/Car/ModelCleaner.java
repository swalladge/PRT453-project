package Car;

public class ModelCleaner implements CarFieldCleaner {
    @Override
    public void clean(CarFields carFields) {
        String model = carFields.getModel();
        model = model.trim();
        carFields.setModel(model);
    }
}
