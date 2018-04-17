package Car;

import java.util.ArrayList;
import java.util.List;


public class CarForm {
    private boolean isValid;
    private CarFields carFields;
    private List<String> errors;

    public CarForm(String manufacturerText, String modelText, String infoText, Double kmText, Double priceText, Integer yearText) {
        this.carFields = new CarFields(manufacturerText, modelText, infoText, kmText, priceText, yearText);
        this.isValid = false;
        this.errors = new ArrayList<>();
    }

    public void cleanAndValidate() {
        this.clean();
        this.validate();
    }

    public void clean() {
        List<CarFieldCleaner> cleaners = new ArrayList<>();
        cleaners.add(new PriceCleaner());
        cleaners.add(new ManufacturerCleaner());
        cleaners.add(new ModelCleaner());
        cleaners.add(new InfoCleaner());

        for ( CarFieldCleaner cleaner : cleaners){
            cleaner.clean(this.carFields);
        }
    }

    public void validate() {
        // assume valid, override below if not
        this.isValid = true;
        this.errors = new ArrayList<>();

        // validators that we want to run here
        List<CarFieldValidator> validators = new ArrayList<>();
        validators.add(new PriceValidator());
        validators.add(new YearValidator());
        validators.add(new KmValidator());

        // run all the validator rules
        for ( CarFieldValidator validator : validators){
            try {
                validator.validate(this.carFields);
            } catch (FieldValidationException e) {
                this.isValid = false;
                this.errors.add(e.getMessage());
            }
        }

    }

    public boolean isValid() {
        return this.isValid;
    }

    public List<String> getValidationErrors() {
        return this.errors;
    }

    public CarFields getFields() {
        return this.carFields;
    }
}
