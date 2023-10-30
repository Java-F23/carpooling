package GUI;

import Services.UserService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginFrame extends JFrame {
    private JTextField phoneNumberField;
    private JPasswordField passwordField;
    private UserService userService;
    JButton loginButton;
    private LoginListener loginListener;


    public LoginFrame(UserService userService, LoginListener listener) {
        this.userService = userService;
        this.loginListener = listener;
        this.setLayout(new FlowLayout());
        this.setSize(300, 150);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JLabel phoneLabel = new JLabel("Phone Number:");
        phoneNumberField = new JTextField(15);
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField(15);
        loginButton = new JButton("Login");



        this.add(phoneLabel);
        this.add(phoneNumberField);
        this.add(passwordLabel);
        this.add(passwordField);
        this.add(loginButton);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String phoneNumber = phoneNumberField.getText(); // Get phone number from the text field
                char[] password = passwordField.getPassword();
                String passStr = new String(password);
                int type = userService.login(phoneNumber, passStr);
                loginListener.onLoginResult(type, phoneNumber);
            }
        });
    }
}
