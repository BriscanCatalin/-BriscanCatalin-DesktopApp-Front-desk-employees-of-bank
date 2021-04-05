package controller;

import model.Admin_User;
import model.validation.Notification;
import repository.AuthenticationException;
import service.admin.AdminOperationInterfaceService;
import service.admin.AuthenticationAdminService;
import view.AdminViewInterface;
import view.LoginViewAdmin;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginControllerAdmin {

    private final LoginViewAdmin loginView;
    private final AuthenticationAdminService authenticationAdminService;
    private final AdminOperationInterfaceService adminOperationInterfaceService;

    public LoginControllerAdmin(LoginViewAdmin loginView, AuthenticationAdminService authenticationAdminService
                                ,AdminOperationInterfaceService adminOperationInterfaceService) {
        this.loginView = loginView;
        this.authenticationAdminService = authenticationAdminService;
        this.adminOperationInterfaceService = adminOperationInterfaceService;
        loginView.setLoginButtonListener(new LoginButtonListener());
        loginView.setRegisterButtonListener(new RegisterButtonListener());
    }

    private class LoginButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String username = loginView.getUsername();
            String password = loginView.getPassword();

            Notification<Admin_User> loginNotification = null;
            try {
                loginNotification = authenticationAdminService.login(username, password);
            } catch (AuthenticationException authenticationExeception) {
                authenticationExeception.printStackTrace();
            }

            if (loginNotification != null) {
                if (loginNotification.hasErrors()) {
                    JOptionPane.showMessageDialog(loginView.getContentPane(), loginNotification.getFormattedErrors());
                } else {
                    JOptionPane.showMessageDialog(loginView.getContentPane(), "Login successful!");
                    new ControllerAdminInterface(new AdminViewInterface() , adminOperationInterfaceService);
                }
            }
        }
    }

    private class RegisterButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String username = loginView.getUsername();
            String password = loginView.getPassword();

            Notification<Boolean> registerNotification = authenticationAdminService.register(username, password);
            if (registerNotification.hasErrors()) {
                JOptionPane.showMessageDialog(loginView.getContentPane(), registerNotification.getFormattedErrors());
            } else {
                if (!registerNotification.getResult()) {
                    JOptionPane.showMessageDialog(loginView.getContentPane(), "Registration not successful, please try again later.");
                } else {
                    JOptionPane.showMessageDialog(loginView.getContentPane(), "Registration successful!");
                }
            }
        }
    }
}
