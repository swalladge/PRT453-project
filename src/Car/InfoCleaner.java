package Car;

public class InfoCleaner implements CarFieldCleaner {
    @Override
    public void clean(CarFields carFields) {
        String info = carFields.getInfo();
        info = info.trim();
        carFields.setInfo(info);
    }
}
