import Car.Car;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * search strategy that searches by age of the car
 */
public class SearchByAgeStrategy implements SearchStrategy {

    private final String[] age = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
            "11-15", "16-20", "21-25", "26-30", "31+"};
    private JLabel headingLabel = new JLabel("Search on age");
    private JLabel ageLabel = new JLabel("Car Age");
    private JComboBox ageCombo = new JComboBox(age);
    private JPanel agePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

    private JPanel mainPanel = new JPanel();

    private CarsCollectionInterface carCollection;

    public SearchByAgeStrategy(CarsCollectionInterface carCollection) {
        this.carCollection = carCollection;

        // build the panel/controls
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        agePanel.add(ageLabel);
        agePanel.add(ageCombo);
        agePanel.setBorder(new javax.swing.border.EmptyBorder(new Insets(0, 5, 0, 0)));
        headingLabel.setAlignmentX(0.5f);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(headingLabel);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(agePanel);

    }


    @Override
    public List<Car> doSearch() {
        double[] range = CarSalesSystem.convertToRange((String)ageCombo.getSelectedItem());
        return this.carCollection.search((int)range[0], (int)range[1]);
    }

    @Override
    public JPanel getPanel() {
        return this.mainPanel;
    }

    @Override
    public void reset() {
        ageCombo.setSelectedIndex(0);
    }

}
