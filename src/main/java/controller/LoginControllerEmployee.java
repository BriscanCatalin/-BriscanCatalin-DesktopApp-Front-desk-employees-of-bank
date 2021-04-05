package controller;

import model.Employee_User;
import model.validation.Notification;
import repository.AuthenticationException;
import service.employee.AuthenticationEmployeeService;
import service.employee.EmployeeOperationInterfaceService;
import view.EmployeeViewInterface;
import view.LoginViewEmployee;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginControllerEmployee {

    private final LoginViewEmployee loginViewEmployee;
    private final AuthenticationEmployeeService authenticationEmployeeService;
    private final EmployeeOperationInterfaceService employeeOperationInterfaceService;


    public LoginControllerEmployee(LoginViewEmployee loginViewEmployee, AuthenticationEmployeeService authenticationEmployeeService,
                                   EmployeeOperationInterfaceService employeeOperationInterfaceService) {
        this.loginViewEmployee = loginViewEmployee;
        this.authenticationEmployeeService = authenticationEmployeeService;
        this.employeeOperationInterfaceService = employeeOperationInterfaceService;
        loginViewEmployee.setLoginEmployeeButtonListener(new LoginControllerEmployee.LoginButtonListener());
        loginViewEmployee.setRegisterEmployeeButtonListener(new LoginControllerEmployee.RegisterButtonListener());
    }

    private class LoginButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String username = loginViewEmployee.getUsername();
            String password = loginViewEmployee.getPassword();

            Notification<Employee_User> loginNotification = null;
            try {
                loginNotification = authenticationEmployeeService.login(username, password);
            } catch (AuthenticationException authenticationException) {
                authenticationException.printStackTrace();
            }

            if (loginNotification != null) {
                if (loginNotification.hasErrors()) {
                    JOptionPane.showMessageDialog(loginViewEmployee.getContentPane(), loginNotification.getFormattedErrors());
                } else {
                    JOptionPane.showMessageDialog(loginViewEmployee.getContentPane(), "Login successful!");
                    new ControllerEmployeeInterface(new EmployeeViewInterface() , employeeOperationInterfaceService);
                }
            }
        }
    }

    private class RegisterButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String username = loginViewEmployee.getUsername();
            String password = loginViewEmployee.getPassword();

            Notification<Boolean> registerNotification = authenticationEmployeeService.register(username, password);
            if (registerNotification.hasErrors()) {
                JOptionPane.showMessageDialog(loginViewEmployee.getContentPane(), registerNotification.getFormattedErrors());
            } else {
                if (!registerNotification.getResult()) {
                    JOptionPane.showMessageDialog(loginViewEmployee.getContentPane(), "Registration not successful, please try again later.");
                } else {
                    JOptionPane.showMessageDialog(loginViewEmployee.getContentPane(), "Registration successful!");
                }
            }
        }
    }
}