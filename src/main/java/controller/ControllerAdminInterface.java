package controller;

import model.validation.Notification;
import service.admin.AdminOperationInterfaceService;
import view.AdminViewInterface;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

public class ControllerAdminInterface {

    private final AdminViewInterface adminViewInterface;
    private final AdminOperationInterfaceService adminOperationInterfaceService;

    public ControllerAdminInterface(AdminViewInterface adminViewInterface,  AdminOperationInterfaceService employeeOperationInterfaceService){
        this.adminOperationInterfaceService = employeeOperationInterfaceService;
        this.adminViewInterface = adminViewInterface;
        adminViewInterface.setCreateEmployeeListener(new CreateEmployeeListener());
        adminViewInterface.setUpdateEmployeeListener(new UpdateEmployeeListener());
        adminViewInterface.setDeleteEmployeeListener(new DeleteEmployeeListener());
        adminViewInterface.setReadEmployeeListener(new ReadEmployeeListener());
        adminViewInterface.setReportEmployeeListener(new ReportEmployeeListener());
    }

    private class CreateEmployeeListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Notification<Boolean> createEmployeeNotification = adminOperationInterfaceService.createEmployee(adminViewInterface);
            if (createEmployeeNotification.hasErrors()) {
                JOptionPane.showMessageDialog(adminViewInterface.getContentPane(), createEmployeeNotification.getFormattedErrors());
            } else {
                if (!createEmployeeNotification.getResult()) {
                    JOptionPane.showMessageDialog(adminViewInterface.getContentPane(), "Registration not successful, please try again later.");
                } else {
                    JOptionPane.showMessageDialog(adminViewInterface.getContentPane(), "Registration successful!");
                }
            }
        }
    }

    private class UpdateEmployeeListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Scanner in = new Scanner(System.in);
            System.out.println("Introduce the name of the employee that you want to update : ");
            String name = in.nextLine();

            Notification<Boolean> updateNotification = adminOperationInterfaceService.updateEmployeeByName(name, adminViewInterface);
            if (updateNotification != null) {
                if (updateNotification.hasErrors()) {
                    JOptionPane.showMessageDialog(adminViewInterface.getContentPane(), updateNotification.getFormattedErrors());
                } else {
                    if (!updateNotification.getResult()) {
                        JOptionPane.showMessageDialog(adminViewInterface.getContentPane(), "Update not successful, please try again later");
                    } else {
                        JOptionPane.showMessageDialog(adminViewInterface.getContentPane(), "Update successful !");
                    }
                }
            }else {
                JOptionPane.showMessageDialog(adminViewInterface.getContentPane(), "Something went wrong !");
            }
        }
    }

    private class DeleteEmployeeListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Scanner in = new Scanner(System.in);
            System.out.println("Introduce the username of the employee that you want to delete : ");
            String name = in.nextLine();
            adminOperationInterfaceService.deleteEmployee(name, adminViewInterface);
        }
    }

    private class ReadEmployeeListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Scanner in = new Scanner(System.in);
            System.out.println("Introduce the name of the employee that you want to check : ");
            String name = in.nextLine();
            adminOperationInterfaceService.retrieveEmployeeByName(name, adminViewInterface);
        }
    }

    private class ReportEmployeeListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Scanner in = new Scanner(System.in);
            System.out.println("Introduce the username of the employee that you want to get report : ");
            String name = in.nextLine();
            adminOperationInterfaceService.generateReports(name , adminViewInterface);
        }
    }
}
