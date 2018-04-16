package Car;

public class KmValidator implements CarFieldValidator {
    @Override
    public void validate(CarFields carFields) throws FieldValidationException {
        Double km = carFields.getKm();
            if (km < 0) {
                throw new FieldValidationException("km cannot be negative");
            }
        }
    }
}
