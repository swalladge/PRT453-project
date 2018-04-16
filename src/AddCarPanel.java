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

	    CarForm carForm = new CarForm(
	    		carComponents.getManufacturerText(),
				carComponents.getModelText(),
                carComponents.getInfoText(),
                km,
                price,
                year
		);

	    carForm.cleanAndValidate();

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

        // TODO: implement this with new system
//
//			// begin validation process
//			if (validateString(manufacturer))
//			{
//				if (year >= 1000 && year <= 9999)
//				{
//					if (validateString(model))
//					{
//						if (validateKilometers(carComponents.getKmText().trim()))
//						{
//							valid = true;
//						}
//						else
//							JOptionPane.showMessageDialog(carSystem, "An error has occured due to incorrect \"Km Traveled\" text field data.\nThis text field must contain a number with one decimal place only.", "Invalid field", JOptionPane.ERROR_MESSAGE);
//					}
//					else
//						JOptionPane.showMessageDialog(carSystem, "An error has occured due to incorrect \"Model\" text field data.\nThis text field must contain any string of at least two non-spaced characters.", "Invalid field", JOptionPane.ERROR_MESSAGE);
//				}
//				else
//					JOptionPane.showMessageDialog(carSystem, "An error has occured due to incorrect \"Year\" text field data.\nThis text field must be in the form, YYYY. ie, 2007.", "Invalid field", JOptionPane.ERROR_MESSAGE);
//			}
//			else
//				JOptionPane.showMessageDialog(carSystem, "An error has occured due to incorrect \"Manufacturer\" text field data.\nThis text field must contain any string of at least two non-spaced characters.", "Invalid field", JOptionPane.ERROR_MESSAGE);
//
//		}
//		/* NumberFormatException would usually be thrown if the text fields contain invalid data,
//		for example a price field containing letters.*/
//		catch (NumberFormatException exp)
//		{
//			JOptionPane.showMessageDialog(carSystem, "An unknown error has occured. Please ensure your fields meet the following requirements:\n" +
//			"The \"Year\" field must contain four numeric digits only\nThe \"Price\" field must contain a valid integer with no decimal places\nThe \"Km Traveled\" field must contain a number which can have a maximum of one decimal place", "Invalid field", JOptionPane.ERROR_MESSAGE);
//		}

	}

	/**
	 * checks the argument. It is valid if there is more than 2 non-spaced characters.
	 *
	 * @param arg string to test
	 * @return true if valid, false otherwise
	 */
	private boolean validateString(String arg)
	{
		boolean valid = false;
		String[] splitted = arg.split(" "); // splits argument around spaces and creates an array

		for (int i = 0; i < splitted.length; i++)
		{
			// checks if the number of characters between a space is greater than 2
			valid = (splitted[i].length() > 2);
			if (valid)
				break;
		}

		return valid;
	}

	/**
	 * checks the argument It is valid if it contains a decimal value, with only one decimal place
	 *
	 * @param distance a double value expressed in a string
	 * @return true if valid, false otherwise
	 */
	private boolean validateKilometers(String distance)
	{
		boolean valid = false;
		String rem;
		StringTokenizer tokens = new StringTokenizer(distance, "."); // look for decimal point

		tokens.nextToken();

		if (tokens.hasMoreTokens()) // if true, there is a decimal point present
		{
			// get string representation of all numbers after the decimal point
			rem = tokens.nextToken();
			// if there's only one number after the decimal point, then it's valid
			if (rem.length() == 1)
				valid = true;
			else
			{
				// check if the user has typed something like 3.00, or even 3.00000
				if ((Integer.parseInt(rem)) % (Math.pow(10, rem.length() - 1)) == 0)
					valid = true;
				else
					valid=false;
			}
		}
		else // doesn't have a decimal place
			valid = true;

		return valid;
	}
}