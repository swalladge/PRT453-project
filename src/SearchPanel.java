import Car.Car;

import java.util.List;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * generic search panel - search strategy injected into it at instantiation time
 */
public class SearchPanel extends JPanel implements ActionListener
{
    private List<Car> carList;
    private CarSalesSystem carSystem;
    private int currentIndex = 0;
    private JButton searchButton = new JButton("Search");
    private JButton resetButton = new JButton("Reset");
    private JButton previousButton = new JButton("Previous");
    private JButton nextButton = new JButton("Next");
    private JPanel topPanel = new JPanel();
    private JPanel searchButtonsPanel = new JPanel();
    private JPanel navigateButtonsPanel = new JPanel();
    private CarDetailsComponents carComponents = new CarDetailsComponents();
    private SearchStrategy strategy;

    /**
     * @param carSys links to a CarSalesSystem object
     * @param strategy the search strategy to be used
     */
    public SearchPanel(CarSalesSystem carSys, SearchStrategy strategy)
    {
        this.strategy = strategy;

        carSystem = carSys;
        setLayout(new BorderLayout());
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));

        previousButton.addActionListener(this);
        nextButton.addActionListener(this);
        resetButton.addActionListener(this);
        searchButton.addActionListener(this);

        searchButtonsPanel.add(searchButton);
        searchButtonsPanel.add(resetButton);
        navigateButtonsPanel.add(previousButton);
        navigateButtonsPanel.add(nextButton);


        // add the strategy panel with controls in at the top of the panel
        topPanel.add(this.strategy.getPanel());

        topPanel.add(searchButtonsPanel);
        topPanel.add(Box.createVerticalStrut(15));


        carComponents.add(navigateButtonsPanel, "Center");
        carComponents.setVisible(false);

        add(topPanel, "North");
        add(carComponents, "Center");
    }

    /**
     * check for button clicks
     *
     * @param ev ActionEvent object
     */
    public void actionPerformed(ActionEvent ev)
    {
        if (ev.getSource() == searchButton)
            searchButtonClicked();
        else if (ev.getSource() == resetButton)
            resetButtonClicked();
        else if (ev.getSource() == previousButton)
            previousButtonClicked();
        else if (ev.getSource() == nextButton)
            nextButtonClicked();
    }

    /**
     * get next index if it exists, and display it visually using CarDetailsComponents
     */
    private void nextButtonClicked()
    {
        if (currentIndex < carList.size() - 1)
        {
            currentIndex++;
            carComponents.displayDetails(carList.get(currentIndex));
        }
        else
            JOptionPane.showMessageDialog(carSystem, "You can't navigate any further", "Alert", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * get previous index if it exists, and display it visually using CarDetailsComponents
     */
    private void previousButtonClicked()
    {
        if (currentIndex > 0)
        {
            currentIndex--;
            carComponents.displayDetails(carList.get(currentIndex));
        }
        else
            JOptionPane.showMessageDialog(carSystem, "You can't navigate any further", "Alert", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * clear search results, begin next search from scratch
     */
    private void resetButtonClicked()
    {
        currentIndex = 0;
        carList = null;
        carComponents.setVisible(false);

        // also reset the search strategy controls
        this.strategy.reset();
    }

    /**
     * search cars based on price and distance travelled
     */
    private void searchButtonClicked()
    {
        // run the search now to get the results...
        this.carList = this.strategy.doSearch();

        // ...and load the results into the carcomponents panel
        if (carList.size() > 0) {
            currentIndex = 0;
            carComponents.setVisible(true);
            carComponents.displayDetails(carList.get(0));

            if (carList.size() == 1)
            {
                nextButton.setEnabled(false);
                previousButton.setEnabled(false);
            }
            else
            {
                nextButton.setEnabled(true);
                previousButton.setEnabled(true);
            }

            carSystem.repaint();
        }
        else {
            JOptionPane.showMessageDialog(carSystem, "Sorry, no search results were returned", "Search failed", JOptionPane.WARNING_MESSAGE);
        }

    }
}
