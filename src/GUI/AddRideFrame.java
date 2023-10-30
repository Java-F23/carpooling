package GUI;

import Models.Captain;
import Models.Location;
import Services.RideService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddRideFrame extends JFrame {

    private JTextField startLocationField;
    private JTextField startTimeField;
    private JTextField priceField;
    private Captain captain;
    private RideService rideService;

    public AddRideFrame(Captain captain, RideService rideService) {
        this.captain = captain;
        this.rideService = rideService;

        setTitle("Add Ride");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null); // Center the frame on the screen
        setLayout(new GridLayout(4, 2, 10, 10));

        JLabel startLocationLabel = new JLabel("Start Location:");
        startLocationField = new JTextField();
        JLabel startTimeLabel = new JLabel("Start Time:");
        startTimeField = new JTextField();
        JLabel priceLabel = new JLabel("Price:");
        priceField = new JTextField();

        JButton addButton = new JButton("Add Ride");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addRide();
            }
        });

        add(startLocationLabel);
        add(startLocationField);
        add(startTimeLabel);
        add(startTimeField);
        add(priceLabel);
        add(priceField);
        add(new JLabel()); // Placeholder for empty space
        add(addButton);

        setVisible(true);
    }

    private void addRide() {
        String startLocation = startLocationField.getText();
        String startTimeStr = startTimeField.getText();
        String priceStr = priceField.getText();

        if (startLocation.isEmpty() || startTimeStr.isEmpty() || priceStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            try {
                int startTime = Integer.parseInt(startTimeStr);
                float price = Float.parseFloat(priceStr);

                Location startRoute = new Location(startLocation);
                int rideId = rideService.getAllRides().size() + 1; // Generate a unique ride ID

                // Create the ride and add it to the ride service
                rideService.createRide(rideId, startRoute, captain, startTime, price);

                // Clear the input fields after adding the ride
                dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid input. Please enter valid numbers.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void disposeParentFrame() {
        Window parentWindow = SwingUtilities.windowForComponent(this);
        parentWindow.dispose();
    }
}
