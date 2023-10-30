package GUI;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.Border;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WelcomeComponent extends JFrame {
    public JButton loginButton;
    public JButton signupButton; // Added signup button

    public WelcomeComponent() {
        this.setLayout(null);
        this.setSize(609, 500);

        // Panel for the welcome message and image
        JPanel welcomePanel = new JPanel();
        welcomePanel.setLayout(new BorderLayout());
        welcomePanel.setBounds(0, 0, 594, 300);

        JLabel welcomeLabel = new JLabel("Welcome to AUC's Carpooling App");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 20));

        ImageIcon imageIcon = new ImageIcon("pics/welcome.png"); // Replace with your image file path
        Image image = imageIcon.getImage();
        Image newimg = image.getScaledInstance(260, 120, Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(newimg);
        welcomeLabel.setIcon(imageIcon);

        welcomeLabel.setHorizontalTextPosition(JLabel.CENTER);
        welcomeLabel.setVerticalTextPosition(JLabel.TOP);
        welcomeLabel.setHorizontalAlignment(JLabel.CENTER);
        welcomeLabel.setVerticalAlignment(JLabel.CENTER);

        Border border = BorderFactory.createLineBorder(Color.black, 3);
        welcomeLabel.setBorder(border);
        welcomePanel.add(welcomeLabel);

        // Panel for login and signup buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBounds(0, 400, 600, 100);
        buttonPanel.setLayout(new FlowLayout());

        loginButton = new JButton("Login");
        signupButton = new JButton("Sign Up"); // Added signup button
        buttonPanel.add(loginButton);
        buttonPanel.add(signupButton); // Added signup button

        this.add(welcomePanel);
        this.add(buttonPanel);
    }

    public void setLoginButtonListener(ActionListener listener) {
        loginButton.addActionListener(listener);
    }

    public void setSignupButtonListener(ActionListener listener) {
        signupButton.addActionListener(listener);
    }
}
