package Car;

import java.util.ArrayList;
import java.util.List;


public class CarForm {
    private boolean isValid;
    private CarFields carFields;
    private List<String> errors;

    public CarForm(String manufacturerText, String modelText, String infoText, Double kmText, Integer priceText, Integer yearText) {
        this.carFields = new CarFields(manufacturerText, modelText, infoText, kmText, priceText, yearText);
        this.isValid = false;
        this.errors = new ArrayList<>();
    }

    public void cleanAndValidate() {
        this.clean();
        this.validate();
    }

    public void clean() {
        List<CarFieldCleaner> rules = new ArrayList<>();
        rules.add(new PriceCleaner());

        for ( CarFieldCleaner cleaner : rules){
            cleaner.clean(this.carFields);
        }
    }

    public void validate() {
        // assume valid, override below if not
        this.isValid = true;
        this.errors = new ArrayList<>();

        List<CarFieldValidator> rules = new ArrayList<>();
        rules.add(new PriceValidator());

        // run all the validator rules
        for ( CarFieldValidator validator : rules){
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
