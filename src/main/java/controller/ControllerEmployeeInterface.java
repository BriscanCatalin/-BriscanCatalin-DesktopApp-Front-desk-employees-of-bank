package controller;

import service.employee.EmployeeOperationInterfaceService;
import view.EmployeeViewInterface;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

public class ControllerEmployeeInterface {

    private final EmployeeViewInterface employeeViewInterface;
    private final EmployeeOperationInterfaceService employeeOperationInterfaceService;

    public ControllerEmployeeInterface(EmployeeViewInterface employeeViewInterface,  EmployeeOperationInterfaceService employeeOperationInterfaceService){
        this.employeeOperationInterfaceService = employeeOperationInterfaceService;
        this.employeeViewInterface = employeeViewInterface;
        employeeViewInterface.setAddClientInformationListener(new AddClientListener());
        employeeViewInterface.setUpdateClientInformationListener(new UpdateClientListener());
        employeeViewInterface.setViewClientInformationListener(new ViewClientListener());
        employeeViewInterface.setCreateClientAccountListener(new CreateAccountListener());
        employeeViewInterface.setDeleteClientAccountListener(new DeleteAccountListener());
        employeeViewInterface.setUpdateClientAccountListener(new UpdateAccountListener());
        employeeViewInterface.setViewClientAccountListener(new ViewAccountListener());
        employeeViewInterface.setTransferMoneyListener(new TransferMoneyListener());
        employeeViewInterface.setProcessBillsListener(new ProcessBillsListener());
    }

    private class AddClientListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            employeeOperationInterfaceService.addClientInformation(employeeViewInterface);
        }
    }

    private class UpdateClientListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Scanner in = new Scanner(System.in);
            System.out.println("Introduce client name to be updated : ");
            String s = in.nextLine();
            employeeOperationInterfaceService.updateClientInformationByName(s, employeeViewInterface);
        }
    }

    private class ViewClientListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Scanner in = new Scanner(System.in);
            System.out.println("Introduce client name to be viewed : ");
            String s = in.nextLine();
            employeeOperationInterfaceService.viewClientInformationByName(s, employeeViewInterface);
        }
    }

    private class CreateAccountListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            employeeOperationInterfaceService.createAccount(employeeViewInterface);
        }
    }

    public static boolean containsChars(String str) {

        if (str == "")
            return true;
        for(int i = 0; i < str.length(); i++)
            if(str.charAt(i) > '9' || str.charAt(i) < '0')
                return true;
        return false;

    }

    private class DeleteAccountListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String id;
            System.out.println("Introduce the account id that you want to delete : ");
            Scanner in = new Scanner(System.in);
            id = in.nextLine();
            if (containsChars(id) == true){
                JOptionPane.showMessageDialog(employeeViewInterface.getContentPane(), "The id must contains only numbers !");
                return ;
            }
            Long id1 = Long.parseLong(id);
            employeeOperationInterfaceService.deleteAccount(id1, employeeViewInterface);
        }
    }

    private class UpdateAccountListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("Introduce the id of the account to update it's information : ");
            Scanner in = new Scanner(System.in);
            String ids = in.nextLine();
            if (containsChars(ids) == true){
                JOptionPane.showMessageDialog(employeeViewInterface.getContentPane(), "The id must contains only numbers !");
                return ;
            }
            Long id = Long.parseLong(ids);
            employeeOperationInterfaceService.updateAccount(id, employeeViewInterface);
        }
    }

    private class ViewAccountListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("Introduce the id of the account to be viewed : ");
            Scanner in = new Scanner(System.in);
            String ids = in.nextLine();
            if (containsChars(ids) == true){
                JOptionPane.showMessageDialog(employeeViewInterface.getContentPane(), "The id must contains only numbers !");
                return ;
            }
            Long id = Long.parseLong(ids);
            employeeOperationInterfaceService.viewAccount(id , employeeViewInterface);
        }
    }

    private class TransferMoneyListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Scanner in = new Scanner(System.in);
            System.out.println("Insert Id1 : ");
            String ids = in.nextLine();
            if (containsChars(ids) == true){
                JOptionPane.showMessageDialog(employeeViewInterface.getContentPane(),
                        "The first id must contains only numbers !");
                return ;
            }
            Integer id1 = Integer.parseInt(ids);

            System.out.println("Insert Id2 : ");
            ids = in.nextLine();
            if (containsChars(ids) == true){
                JOptionPane.showMessageDialog(employeeViewInterface.getContentPane(),
                        "The second id must contains only numbers !");
                return ;
            }
            Integer id2 = Integer.parseInt(ids);
            System.out.println("Insert the sum to be transferred : ");
            ids = in.nextLine();
            if (containsChars(ids) == true){
                JOptionPane.showMessageDialog(employeeViewInterface.getContentPane(),
                        "The input for sum isn't correct !");
                return ;
            }
            Integer money = Integer.parseInt(ids);
            employeeOperationInterfaceService.transferMoney(id1 ,id2, money, employeeViewInterface);

        }
    }

    private class ProcessBillsListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Scanner in = new Scanner(System.in);
            System.out.println("Insert the name of the client to process the bills : ");
            String id = in.nextLine();
            employeeOperationInterfaceService.processBills(id ,employeeViewInterface);
        }
    }

}
