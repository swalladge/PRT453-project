package Car;

public class PriceValidator implements CarFieldValidator {
    @Override
    public void validate(CarFields carFields) throws FieldValidationException {
        Integer price = carFields.getPrice();
        if (price < 0) {
            throw new FieldValidationException("price cannot be negative");
        }
    }
}
