package Car;

import java.time.Year;

public class YearValidator implements CarFieldValidator {
    @Override
    public void validate(CarFields carFields) throws FieldValidationException {
        Integer year = carFields.getYear();

        // ensure sane values - should be ok for a few thousand years
        if (year < 1000 || year > 9999) {
            throw new FieldValidationException("Year not valid (too large or too small)");
        }

        // can't add something from the future!
        if (year > (Year.now().getValue())) {
            throw new FieldValidationException("Year cannot be in the future.");
        }
    }
}
