import Car.Car;
import Car.CarForm;
import Car.CarFields;

import java.util.*;
import java.awt.event.*;
import javax.swing.*;
/**
 * The panel used for adding cars to the CarSalesSystem
 * @
 *
 * PUBLIC FEATURES:
 * // Constructors
 *    public AddCarPanel(CarSalesSystem carSys, JPanel dest)
 *
 * // Methods
 *    public void actionPerformed(ActionEvent ev)
 *
 * COLLABORATORS:
 *    CarDetailComponents
 *
 * @version 1.0, 16 Oct 2004
 * @author Adam Black
 */
public class AddCarPanel extends JPanel implements ActionListener
{
	private CarSalesSystem carSystem;
	private JLabel headingLabel = new JLabel("Add a Car.Car");
	private JButton resetButton = new JButton("Reset");
	private JButton saveButton = new JButton("Save");
	private JPanel buttonPanel = new JPanel();
	private CarDetailsComponents carComponents = new CarDetailsComponents();

	/**
	 * @param carSys links to a CarSalesSystem object
	 * @param dest where the components will be placed
	 */
	public AddCarPanel(CarSalesSystem carSys)
	{
		carSystem = carSys;

		resetButton.addActionListener(this);
		saveButton.addActionListener(this);
		headingLabel.setAlignmentX(0.5f);

		buttonPanel.add(resetButton);
		buttonPanel.add(saveButton);

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		add(Box.createVerticalStrut(10));
		add(headingLabel);
		add(Box.createVerticalStrut(15));
		carComponents.add(buttonPanel, "Center");
		add(carComponents);
	}

	/**
	 * check which buttons were pressed
	 *
	 * @param ev ActionEvent object
	 */
	public void actionPerformed(ActionEvent ev)
	{
		if (ev.getSource() == resetButton)
			resetButtonClicked();
		else if (ev.getSource() == saveButton)
			saveButtonClicked();
	}

	private void resetButtonClicked()
	{
		carComponents.clearTextFields();
	}

	// someone clicked the save button - now we need to extract the car form fields,
    // clean and validate them, and finally save to the database
	private void saveButtonClicked() {
	    // initial converting to numbers from raw fields to make it easier to work with in cleaning/validation later
		Double km;
		Integer price;
		Integer year;
		try {
			km = Double.parseDouble(carComponents.getKmText());
		} catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(carSystem, "KM not valid number!");
            return;
        }
        try {
            price = Integer.parseInt(carComponents.getPriceText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(carSystem, "Price not valid whole number!");
            return;
        }
        try {
            year = Integer.parseInt(carComponents.getYearText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(carSystem, "Year not valid whole number!");
            return;
        }

        // init a new carform object that handles validating, collecting errors, etc.
	    CarForm carForm = new CarForm(
	    		carComponents.getManufacturerText(),
				carComponents.getModelText(),
                carComponents.getInfoText(),
                km,
                price,
                year
		);

		// run all cleaning and validating methods
	    carForm.cleanAndValidate();

	    // if it's valid, then we want to save the car, otherwise abort and show the errors
	    if (carForm.isValid()) {
            // create a car object from validated data.
            CarFields carFields = carForm.getFields();
            Car myCar = new Car(carFields.getManufacturer(), carFields.getModel(), carFields.getInfo());
            myCar.setKilometers(carFields.getKm());
            myCar.setPrice(carFields.getPrice());
            myCar.setYear(carFields.getYear());

            // attempt to add the new car to the system.
            int result = carSystem.addNewCar(myCar);

            // if the car was added successfully
            if (result == CarsCollection.NO_ERROR)
            {
                carSystem.setCarsUpdated();
                JOptionPane.showMessageDialog(carSystem, "Record added.", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
                resetButtonClicked();
                carComponents.setFocusManufacturerTextField();
            }
            // for that manufacturer, the limit has been reached
            else if (result == CarsCollection.CARS_MAXIMUM_REACHED)
                JOptionPane.showMessageDialog(carSystem, "The maximum amount of cars for that manufacturer has been reached.\nUnfortunately you cannot add any further cars to this manufacturer", "Problem adding car", JOptionPane.WARNING_MESSAGE);
                // the car system has reached the maximum number of manufacturers allowed
            else if (result == CarsCollection.MANUFACTURERS_MAXIMUM_REACHED)
                JOptionPane.showMessageDialog(carSystem, "The maximum amount of manufacturers in the car system has been reached.\nUnfortunately you cannot add any further manufacturers to this system", "Problem adding car", JOptionPane.WARNING_MESSAGE);
        } else {
	        String msgs = String.join("\n", carForm.getValidationErrors());
            JOptionPane.showMessageDialog(carSystem, msgs);
        }
	}
}