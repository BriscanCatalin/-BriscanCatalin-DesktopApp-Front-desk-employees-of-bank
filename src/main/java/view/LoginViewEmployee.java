package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class LoginViewEmployee extends JFrame {

    private JTextField tfUsername;
    private JPasswordField tfPassword;
    private JButton btnLogin;
    private JButton btnRegister;

    public LoginViewEmployee() throws HeadlessException {
        setSize(300, 300);
        setLocationRelativeTo(null);
        initializeFields();
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        add(tfUsername);
        add(tfPassword);
        add(btnLogin);
        add(btnRegister);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void initializeFields() {
        tfUsername = new JTextField();
        tfPassword = new JPasswordField();
        btnLogin = new JButton("Login Employee");
        btnRegister = new JButton("Register Employee");
    }

    public String getUsername() {
        return tfUsername.getText();
    }

    public String getPassword() {
        return tfPassword.getText();
    }

    public void setLoginEmployeeButtonListener(ActionListener loginButtonListener) {
        btnLogin.addActionListener(loginButtonListener);
    }

    public void setRegisterEmployeeButtonListener(ActionListener registerButtonListener) {
        btnRegister.addActionListener(registerButtonListener);
    }
}