import Car.Car;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * search strategy that searches on price and distance (cars that match both params are returned)
 */
public class SearchByOtherStrategy implements SearchStrategy {
    private final String[] price = {"5001-10000", "10001-15000", "15001-20000", "20001-50000",
            "50001-100000", "100001-200000", "200001-300000", "300001+"};
    private final String[] distance = {"0", "1-10000", "10001-20000", "20001-30000", "30001-40000",
            "40001-50000", "50001-80000", "80001-100000", "100001-200000", "200001-300000", "300001+"};

    private JLabel headingLabel = new JLabel("Search on Price and Distance Traveled");
    private JLabel priceLabel = new JLabel("Price");
    private JLabel distanceLabel = new JLabel("Distance traveled");

    private JComboBox priceCombo = new JComboBox(price);
    private JComboBox distanceCombo = new JComboBox(distance);

    private JPanel pricePanel = new JPanel();
    private JPanel distancePanel = new JPanel();
    private JPanel priceDistancePanel = new JPanel();

    private JPanel mainPanel = new JPanel();

    private CarsCollectionInterface carCollection;

    public SearchByOtherStrategy(CarsCollectionInterface carCollection) {
        this.carCollection = carCollection;

        // build the panel controls
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        pricePanel.add(priceLabel);
        pricePanel.add(priceCombo);
        distancePanel.add(distanceLabel);
        distancePanel.add(distanceCombo);
        priceDistancePanel.add(pricePanel);
        priceDistancePanel.add(distancePanel);

        headingLabel.setAlignmentX(0.5f);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(headingLabel);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(priceDistancePanel);

    }

    @Override
    public List<Car> doSearch() {
        // convert distance and price combo box text into a range
        double[] distanceRange = CarSalesSystem.convertToRange((String)distanceCombo.getSelectedItem());
        double[] priceRange = CarSalesSystem.convertToRange((String)priceCombo.getSelectedItem());

        if (priceRange[0] >= 0 && distanceRange[0] >= 0)
        {
            return carCollection.search((int)priceRange[0], (int)priceRange[1], (double)distanceRange[0], (double)distanceRange[1]);
        } else {
            // if the lower bounds happened to be negative, retain old behaviour of returning zero results
            return new ArrayList<Car>();
        }
    }

    @Override
    public JPanel getPanel() {
        return this.mainPanel;
    }

    @Override
    public void reset() {
        priceCombo.setSelectedIndex(0);
        distanceCombo.setSelectedIndex(0);
    }
}
