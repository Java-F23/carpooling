package GUI;

import Models.*;
import Services.UserService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignUpFrame extends JFrame {
    private JTextField nameField;
    private JTextField emailField;
    private JTextField phoneNumberField;
    private JPasswordField passwordField;
    private JRadioButton riderRadioButton;
    private JRadioButton captainRadioButton;
    private JPanel captainPanel;
    private JTextField plateField;
    private JTextField colorField;
    private JTextField carBrandField;
    private UserService userService;

    public SignUpFrame(UserService userService) {
        this.userService = userService;
        setTitle("Sign Up");
        setSize(400, 800);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame on the screen
        setLayout(new GridLayout(8, 2));

        JLabel nameLabel = new JLabel("Name:");
        nameField = new JTextField();
        JLabel emailLabel = new JLabel("Email:");
        emailField = new JTextField();
        JLabel phoneLabel = new JLabel("Phone Number:");
        phoneNumberField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();
        JLabel userTypeLabel = new JLabel("User Type:");
        riderRadioButton = new JRadioButton("Rider");
        captainRadioButton = new JRadioButton("Captain");
        ButtonGroup userTypeGroup = new ButtonGroup();
        userTypeGroup.add(riderRadioButton);
        userTypeGroup.add(captainRadioButton);

        captainPanel = new JPanel(new GridLayout(3, 2));
        captainPanel.setBorder(BorderFactory.createTitledBorder("Captain Details"));
        captainPanel.setVisible(false);
        JLabel plateLabel = new JLabel("Car Plate:");
        plateField = new JTextField();
        JLabel colorLabel = new JLabel("Car Color:");
        colorField = new JTextField();
        JLabel brandLabel = new JLabel("Car Brand:");
        carBrandField = new JTextField();

        captainPanel.add(plateLabel);
        captainPanel.add(plateField);
        captainPanel.add(colorLabel);
        captainPanel.add(colorField);
        captainPanel.add(brandLabel);
        captainPanel.add(carBrandField);

        add(nameLabel);
        add(nameField);
        add(emailLabel);
        add(emailField);
        add(phoneLabel);
        add(phoneNumberField);
        add(passwordLabel);
        add(passwordField);
        add(userTypeLabel);
        add(riderRadioButton);
        add(captainRadioButton);
        add(captainPanel);

        riderRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                captainPanel.setVisible(false);
            }
        });

        captainRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                captainPanel.setVisible(true);
            }
        });

        JButton signupButton = new JButton("Sign Up");
        signupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String email = emailField.getText();
                String phoneNumber = phoneNumberField.getText();
                char[] password = passwordField.getPassword();
                String passStr = new String(password);

                if (riderRadioButton.isSelected()) {
                    Location riderLocation = new Location("Default Region"); // Set default region for rider
                    if(userService.doesUserExist(phoneNumber)){
                        JOptionPane.showMessageDialog(null, "User Already exists");

                    }
                     userService.signUp(name, email, phoneNumber, passStr, riderLocation);
                    JOptionPane.showMessageDialog(null, "Rider signed up successfully!");
                } else if (captainRadioButton.isSelected()) {
                    String plate = plateField.getText();
                    String color = colorField.getText();
                    String brand = carBrandField.getText();
                    if (!plate.isEmpty() && !color.isEmpty() && !brand.isEmpty()) {
                        CarType carType = new CarType(0, 4, brand); // Assuming default number of seats is 4
                        Car car = new Car(0, plate, carType, color);
                        if(userService.doesUserExist(phoneNumber)){
                            JOptionPane.showMessageDialog(null, "User Already exists");

                        }
                        userService.signUp(name, email, phoneNumber,passStr, car);
                        JOptionPane.showMessageDialog(null, "Captain signed up successfully!");
                    } else {
                        JOptionPane.showMessageDialog(null, "Please fill in all the captain details!");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please select user type!");
                }
            }
        });

        add(signupButton);
        setVisible(true);
    }
}
