package GUI;
import Applications.RiderApplication;
import Filters.RideFilters;
import Models.Captain;
import Models.Ride;
import Models.Rider;
import Services.CaptainService;
import Services.PaymentService;
import Services.RideService;
import Services.RiderService;
import com.sun.tools.javac.Main;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


public class MainFrame extends JFrame {

    private JPanel mainPanel;
    private JPanel sidePanel;
    private JPanel filterPanel;
    private JTextField regionFilter;
    private Rider rider;
    private Captain captain;
    private JTextField startTimeFilter;
    ArrayList<Ride> availableRides;
    private RiderApplication riderApplication;
    private RideService rs;

    public MainFrame(CaptainService cs, RideService rs, String phoneNumber, PaymentService ps, LogoutListener listener){
        this.setTitle("AUC Car Pool");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.rs = rs;
        this.setSize(1000, 600);
        this.captain = cs.getCaptainByPhoneNumber(phoneNumber);

        // Main content panel with BorderLayout
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        // Navigation side panel
        sidePanel = createSidePanel(rs, listener, 1);
        mainPanel.add(sidePanel, BorderLayout.WEST);

        // Add the main content panel to the frame
        this.add(mainPanel);
    }
    public MainFrame(RiderService rrs, RideService rs, String phoneNumber, PaymentService ps, LogoutListener listener) {
        this.setTitle("AUC Car Pool");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000, 600);
        this.rider = rrs.getRiderByPhoneNumber(phoneNumber);
        this.riderApplication = new RiderApplication(rs, rrs, rider.getId(), ps);

        // Main content panel with BorderLayout
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        // Navigation side panel
        sidePanel = createSidePanel(rs, listener, 0);
        mainPanel.add(sidePanel, BorderLayout.WEST);

        // Initialize the content panel with an empty panel
        JPanel initialContentPanel = new JPanel();
        mainPanel.add(initialContentPanel, BorderLayout.CENTER);

        filterPanel = new JPanel();
        filterPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        // Add filter components
        JLabel regionLabel = new JLabel("Region:");
        regionFilter = new JTextField();
        regionFilter.setPreferredSize(new Dimension(100, 25)); // Set preferred size

        JLabel startTimeLabel = new JLabel("Start Time:");
        startTimeFilter = new JTextField();
        startTimeFilter.setPreferredSize(new Dimension(100, 25));

        JButton filterButton = new JButton("Filter");
        filterButton.addActionListener(e -> {
            // Handle filter button click
            String region = regionFilter.getText();
            String startTime = startTimeFilter.getText();
            RideFilters rf;
            availableRides = riderApplication.getAvailableRidesForRider();



            if(!region.isEmpty() && isInteger(startTime)){
                int stInteger = Integer.parseInt(startTime);
                rf = new RideFilters(region, stInteger);
                availableRides =riderApplication.searchAvailableRidesByFilter(rf);
            } else if (!region.isEmpty()) {
                rf = new RideFilters(region);
                availableRides =riderApplication.searchAvailableRidesByFilter(rf);
            } else if (isInteger(startTime)) {
                int stInteger = Integer.parseInt(startTime);
                rf = new RideFilters(stInteger);
                availableRides =riderApplication.searchAvailableRidesByFilter(rf);
            }


            JPanel rideCardsPanel = new JPanel();
            rideCardsPanel.setLayout(new BoxLayout(rideCardsPanel, BoxLayout.Y_AXIS));

            // Create a card for each available ride
            for (Ride ride : availableRides) {
                JPanel rideCard = createRideCard(ride);
                rideCardsPanel.add(rideCard);
            }

            // Create a scroll pane and add the ride cards panel to it
            JScrollPane scrollPane = new JScrollPane(rideCardsPanel);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

            // Update the main panel with the scroll pane
            updateMainPanel(scrollPane, true);



            // Perform filtering based on region and startTime values
            // Update the displayed rides accordingly
        });
        filterPanel.add(regionLabel);

        filterPanel.add(regionFilter);
        filterPanel.add(startTimeLabel);
        filterPanel.add(startTimeFilter);
        filterPanel.add(filterButton);



        // Add the main content panel to the frame
        this.add(mainPanel);
    }

    private JPanel createSidePanel(RideService rs, LogoutListener listener, int userType) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(51, 102, 204));
        panel.setPreferredSize(new Dimension(200, 600));

        JButton availableRidesButton = createStyledButton("Available Rides");
        JButton myRidesButton = createStyledButton("My Rides");
        JButton logoutButton = createStyledButton("Logout");
        JButton addRideButton = createStyledButton("Add Ride");


        myRidesButton.addActionListener(e -> {
            if(userType == 1){
                viewMyRidesCaptain();
            }else if(userType == 0){
                viewMyRides();
            }
        });

        availableRidesButton.addActionListener(e -> {
            // Handle available rides button click

            viewAvailbleRides();
        });

        logoutButton.addActionListener(e->{
            listener.onLogout();
        });

        addRideButton.addActionListener(e -> {
            // Handle adding a new ride when the button is clicked
            AddRideFrame addRideFrame = new AddRideFrame(captain, rs);
            addRideFrame.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                    // Refresh the MainFrame after AddRideFrame is closed
                    viewMyRidesCaptain();
                }
            });
            addRideFrame.setVisible(true);
        });

        // Add buttons to the side panel
        if(userType == 0){
            panel.add(availableRidesButton);
        }else{
            panel.add(addRideButton);
        }
        panel.add(myRidesButton);
        panel.add(Box.createVerticalGlue());
        panel.add(logoutButton);

        return panel;
    }

    private void viewMyRides() {
        // Handle my rides button click
        ArrayList<Ride> myRides = riderApplication.getRidesBookedByRider(rider.getId());

        JPanel rideCardsPanel = new JPanel();
        rideCardsPanel.setLayout(new BoxLayout(rideCardsPanel, BoxLayout.Y_AXIS));

        // Create a card for each booked ride
        for (Ride ride : myRides) {
            System.out.println(ride.getId());
            JPanel rideCard = createMyRideCard(ride);
            rideCardsPanel.add(rideCard);
        }

        // Create a scroll pane and add the ride cards panel to it
        JScrollPane scrollPane = new JScrollPane(rideCardsPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        // Update the main panel with the scroll pane
        updateMainPanel(scrollPane, false);
    }

    private void viewAvailbleRides() {
        availableRides = riderApplication.getAvailableRidesForRider();
        JPanel rideCardsPanel = new JPanel();
        rideCardsPanel.setLayout(new BoxLayout(rideCardsPanel, BoxLayout.Y_AXIS));

        // Create a card for each available ride
        for (Ride ride : availableRides) {
            JPanel rideCard = createRideCard(ride);
            rideCardsPanel.add(rideCard);
        }

        // Create a scroll pane and add the ride cards panel to it
        JScrollPane scrollPane = new JScrollPane(rideCardsPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        // Update the main panel with the scroll pane
        updateMainPanel(scrollPane, true);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(new Color(100, 200, 255));
        button.setForeground(new Color(51, 102, 204));
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setFocusPainted(false);
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, button.getPreferredSize().height));
        return button;
    }

    private JPanel createCaptainRideCard(Ride ride) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 139))); // Set border color
        card.setBackground(new Color(240, 240, 240)); // Set background color
        card.setPreferredSize(new Dimension(700, 150)); // Increased card height for better visibility

        JLabel rideInfoLabel = new JLabel("<html><div style='text-align: center; font-size: 18px; color: #333333;'>" +
                "Region: " + ride.getRoute().get(0).getRegion() +
                " | Start Time: " + ride.getStartTime() + ":00" +
                " | Price: $" + ride.getPrice() +
                "</div></html>");

        // Apply CSS styles
        rideInfoLabel.setOpaque(true); // Make the label opaque
        rideInfoLabel.setBackground(new Color(240, 240, 240)); // Set background color
        rideInfoLabel.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 139))); // Set border color
        rideInfoLabel.setForeground(new Color(51, 102, 204)); // Set text color
        rideInfoLabel.setFont(new Font("Arial", Font.BOLD, 16)); // Set font
        rideInfoLabel.setPreferredSize(new Dimension(700, 40)); // Set label size
        rideInfoLabel.setHorizontalAlignment(SwingConstants.CENTER); // Align text horizontally to center
        rideInfoLabel.setVerticalAlignment(SwingConstants.CENTER); // Align text vertically to center

        rideInfoLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Set padding
        card.add(rideInfoLabel, BorderLayout.CENTER);

        JButton cancelRideButton = new JButton("Cancel");
        JButton viewRideButton = new JButton("View");
        cancelRideButton.setBackground(new Color(51, 102, 204)); // Set button background color
        cancelRideButton.setForeground(Color.WHITE); // Set button text color
        cancelRideButton.setFont(new Font("Arial", Font.BOLD, 20)); // Set button font
        cancelRideButton.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0)); // Set button padding
        viewRideButton.setBackground(new Color(51, 102, 204)); // Set button background color
        viewRideButton.setForeground(Color.WHITE); // Set button text color
        viewRideButton.setFont(new Font("Arial", Font.BOLD, 20)); // Set button font
        viewRideButton.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0)); // Set button padding

        // Action listener for Cancel Ride button
        cancelRideButton.addActionListener(e -> {
            // Handle canceling the ride when the button is clicked
            int dialogResult = JOptionPane.showConfirmDialog(this, "Are you sure you want to cancel this ride?", "Confirmation", JOptionPane.YES_NO_OPTION);
            if (dialogResult == JOptionPane.YES_OPTION) {
                int rideId = ride.getId(); // Replace 'ride.getId()' with the actual way to get the ride ID

                // Call the RideService method to cancel the ride
                rs.removeRideById(rideId);

                // Close the current frame after canceling the ride
                viewMyRidesCaptain();
            }
        });

        // Action listener for View Ride button
        viewRideButton.addActionListener(e -> {
            // Handle viewing ride statistics when the button is clicked
            // Implement logic to show ride statistics in a separate frame
            ViewRideStatisticsFrame viewRideStatisticsFrame = new ViewRideStatisticsFrame(ride, rs);
            viewRideStatisticsFrame.setVisible(true);
        });

        // Panel for the "Cancel" and "View" buttons at the bottom
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        buttonPanel.setBackground(new Color(240, 240, 240)); // Set background color
        buttonPanel.add(cancelRideButton); // Add the cancel button to the panel
        buttonPanel.add(viewRideButton); // Add the view button to the panel
        card.add(buttonPanel, BorderLayout.SOUTH); // Add the button panel to the card's SOUTH position

        return card;
    }




    private void updateMainPanel(Component newComponent, boolean filter) {
        mainPanel.removeAll();
        if (filter) {
            mainPanel.add(filterPanel, BorderLayout.NORTH);
        }
        mainPanel.add(sidePanel, BorderLayout.WEST);
        mainPanel.add(newComponent, BorderLayout.CENTER);
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    public boolean isInteger(String input) {
        try {
            // Attempt to parse the input string to an integer
            Integer.parseInt(input);
            return true; // If successful, the input is an integer
        } catch (NumberFormatException e) {
            return false; // If an exception is thrown, the input is not an integer
        }
    }


    private void viewMyRidesCaptain() {
        // Handle my rides button click for Captain
        ArrayList<Ride> myRides = rs.getRidesByCaptain(captain);

        JPanel rideCardsPanel = new JPanel();
        rideCardsPanel.setLayout(new BoxLayout(rideCardsPanel, BoxLayout.Y_AXIS));

        // Create a card for each booked ride
        for (Ride ride : myRides) {
            JPanel rideCard = createCaptainRideCard(ride);
            rideCardsPanel.add(rideCard);
        }

        // Create a scroll pane and add the ride cards panel to it
        JScrollPane scrollPane = new JScrollPane(rideCardsPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        // Update the main panel with the scroll pane
        updateMainPanel(scrollPane, false);
    }



    private JPanel createMyRideCard(Ride ride) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 139))); // Set border color
        card.setBackground(new Color(240, 240, 240)); // Set background color
        card.setPreferredSize(new Dimension(700, 150)); // Increased card height for better visibility

        JLabel rideInfoLabel = new JLabel("<html><div style='text-align: center; font-size: 18px; color: #333333;'>" +
                "Region: " + ride.getRoute().get(0).getRegion() +
                " | Start Time: " + ride.getStartTime() + ":00"+
                " | Price: $" + ride.getPrice() +
                "</div></html>");

        // Apply CSS styles
        rideInfoLabel.setOpaque(true); // Make the label opaque
        rideInfoLabel.setBackground(new Color(240, 240, 240)); // Set background color
        rideInfoLabel.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 139))); // Set border color
        rideInfoLabel.setForeground(new Color(51, 102, 204)); // Set text color
        rideInfoLabel.setFont(new Font("Arial", Font.BOLD, 16)); // Set font
        rideInfoLabel.setPreferredSize(new Dimension(700, 40)); // Set label size
        rideInfoLabel.setHorizontalAlignment(SwingConstants.CENTER); // Align text horizontally to center
        rideInfoLabel.setVerticalAlignment(SwingConstants.CENTER); // Align text vertically to center

        rideInfoLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Set padding
        card.add(rideInfoLabel, BorderLayout.CENTER);

        JButton payNowButton = new JButton("Pay");
        payNowButton.setBackground(new Color(51, 102, 204)); // Set button background color
        payNowButton.setForeground(Color.WHITE); // Set button text color
        payNowButton.setFont(new Font("Arial", Font.BOLD, 20)); // Set button font
        payNowButton.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0)); // Set button padding
        payNowButton.setEnabled(!riderApplication.hasRiderPaidForRide(ride.getId()));
        // Creating a dropdown menu for payment methods
        String[] paymentMethods = {"Credit Card", "Debit Card"};
        JComboBox<String> paymentMethodDropdown = new JComboBox<>(paymentMethods);

        payNowButton.addActionListener(e -> {
            // Handle booking the ride when the button is clicked
            // Show a dialog with the payment method dropdown menu
            Object[] message = {"Select Payment Method:", paymentMethodDropdown};
            int option = JOptionPane.showConfirmDialog(null, message, "Payment Method", JOptionPane.OK_CANCEL_OPTION);

            if (option == JOptionPane.OK_OPTION) {
                String selectedPaymentMethod = (String) paymentMethodDropdown.getSelectedItem();
                int dialogResult = JOptionPane.showConfirmDialog(this, "Are you sure you want to make payment using " + selectedPaymentMethod + "?", "Confirmation", JOptionPane.YES_NO_OPTION);

                if (dialogResult == JOptionPane.YES_OPTION) {
                    // User clicked yes, proceed with making the payment
                    String paymentType;
                    if (selectedPaymentMethod.equals("Credit Card")) {
                        paymentType = "credit card";
                    } else {
                        paymentType = "debit card";
                    }
                    // Implement your payment logic here based on the selected payment type
                    riderApplication.makePayment(ride.getId(), paymentType);
                    viewMyRides();
                }
            }
        });

        // Panel for the "Book Now" button at the bottom
        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.setBackground(new Color(240, 240, 240)); // Set background color
        buttonPanel.add(payNowButton, BorderLayout.CENTER); // Add the button to the panel
        card.add(buttonPanel, BorderLayout.SOUTH); // Add the button panel to the card's SOUTH position

        return card;
    }


    private JPanel createRideCard(Ride ride) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 139))); // Set border color
        card.setBackground(new Color(240, 240, 240)); // Set background color
        card.setPreferredSize(new Dimension(700, 150)); // Increased card height for better visibility

        JLabel rideInfoLabel = new JLabel("<html><div style='text-align: center; font-size: 18px; color: #333333;'>" +
                "Region: " + ride.getRoute().get(0).getRegion() +
                " | Start Time: " + ride.getStartTime() + ":00"+
                " | Price: $" + ride.getPrice() +
                "</div></html>");

        // Apply CSS styles
        rideInfoLabel.setOpaque(true); // Make the label opaque
        rideInfoLabel.setBackground(new Color(240, 240, 240)); // Set background color
        rideInfoLabel.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 139))); // Set border color
        rideInfoLabel.setForeground(new Color(51, 102, 204)); // Set text color
        rideInfoLabel.setFont(new Font("Arial", Font.BOLD, 16)); // Set font
        rideInfoLabel.setPreferredSize(new Dimension(700, 40)); // Set label size
        rideInfoLabel.setHorizontalAlignment(SwingConstants.CENTER); // Align text horizontally to center
        rideInfoLabel.setVerticalAlignment(SwingConstants.CENTER); // Align text vertically to center

        rideInfoLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Set padding
        card.add(rideInfoLabel, BorderLayout.CENTER);

        JButton bookNowButton = new JButton("Book Now");
        bookNowButton.setBackground(new Color(51, 102, 204)); // Set button background color
        bookNowButton.setForeground(Color.WHITE); // Set button text color
        bookNowButton.setFont(new Font("Arial", Font.BOLD, 20)); // Set button font
        bookNowButton.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0)); // Set button padding
        bookNowButton.addActionListener(e -> {
            // Handle booking the ride when the button is clicked
            int dialogResult = JOptionPane.showConfirmDialog(this, "Are you sure you want to book this ride?", "Confirmation", JOptionPane.YES_NO_OPTION);
            if (dialogResult == JOptionPane.YES_OPTION) {
                // User clicked yes, proceed with booking the ride
                // Implement your booking logic here
                riderApplication.bookRide(ride.getId());
                availableRides = riderApplication.getAvailableRidesForRider();
                viewAvailbleRides();
            }
        });

        // Panel for the "Book Now" button at the bottom
        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.setBackground(new Color(240, 240, 240)); // Set background color
        buttonPanel.add(bookNowButton, BorderLayout.CENTER); // Add the button to the panel
        card.add(buttonPanel, BorderLayout.SOUTH); // Add the button panel to the card's SOUTH position

        return card;
    }


}
