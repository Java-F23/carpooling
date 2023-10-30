package GUI;

import Models.Ride;
import Services.RideService;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ViewRideStatisticsFrame extends JFrame {

    private RideService rideService;

    public ViewRideStatisticsFrame(Ride ride, RideService rideService) {
        this.rideService = rideService;

        setTitle("Ride Statistics");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null); // Center the frame on the screen
        setLayout(new BorderLayout());

        // Display ride details
        JPanel rideDetailsPanel = new JPanel();
        rideDetailsPanel.setLayout(new GridLayout(6, 1));
        rideDetailsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel regionLabel = new JLabel("Region: " + ride.getRoute().get(0).getRegion());
        JLabel startTimeLabel = new JLabel("Start Time: " + ride.getStartTime() + ":00");
        JLabel priceLabel = new JLabel("Price: $" + ride.getPrice());
        JLabel totalMoneyLabel = new JLabel("Total Money Accumulated: $" + calculateTotalMoney(ride));
        JLabel reservedUsersLabel = new JLabel("Reserved Users: " + rideService.getTotalNumberOfUsers(ride.getId())
                + " out of " + ride.getCaptain().getCar().getCarType().getNumberOfseats());

        rideDetailsPanel.add(regionLabel);
        rideDetailsPanel.add(startTimeLabel);
        rideDetailsPanel.add(priceLabel);
        rideDetailsPanel.add(totalMoneyLabel);
        rideDetailsPanel.add(reservedUsersLabel);

        add(rideDetailsPanel, BorderLayout.NORTH);

        // Display riders who paid and who did not
        JPanel ridersPanel = new JPanel();
        ridersPanel.setLayout(new GridLayout(2, 1));
        ridersPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        int rideId = ride.getId();
        List<String> paidRiders = rideService.getUsersWhoPaid(rideId);
        List<String> unpaidRiders = rideService.getUsersWhoDidNotPay(rideId);

        JList<String> paidRidersList = new JList<>(paidRiders.toArray(new String[0]));
        JList<String> unpaidRidersList = new JList<>(unpaidRiders.toArray(new String[0]));

        JScrollPane paidRidersScrollPane = new JScrollPane(paidRidersList);
        JScrollPane unpaidRidersScrollPane = new JScrollPane(unpaidRidersList);

        ridersPanel.add(new JLabel("Riders Who Paid:"));
        ridersPanel.add(paidRidersScrollPane);
        ridersPanel.add(new JLabel("Riders Who Did Not Pay:"));
        ridersPanel.add(unpaidRidersScrollPane);

        add(ridersPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    private float calculateTotalMoney(Ride ride) {
        // Implement logic to calculate total money accumulated for the ride
        // This could involve summing up prices paid by riders or other calculation based on your requirements
        // For now, return a placeholder value of 0.0
        int numberOfPaidUsers = 0;
        float totalPrice = ride.getPrice();

        // Iterate through the payments list and count the number of users who paid
        for (boolean isPaid : ride.getPayments()) {
            if (isPaid) {
                numberOfPaidUsers++;
            }
        }

        // Calculate total money as the number of paid users times the ride price
        float totalMoney = numberOfPaidUsers * totalPrice;
        return totalMoney;
    }
}
