package Car;

public class YearValidator implements CarFieldValidator {
    @Override
    public void validate(CarFields carFields) throws FieldValidationException {
        Integer year = carFields.getYear();
        if (year < 1000 || year > 9999) {
            throw new FieldValidationException("Year not valid (too large or too small)");
        }
    }
}
