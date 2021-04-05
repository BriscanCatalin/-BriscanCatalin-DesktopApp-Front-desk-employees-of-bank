package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class EmployeeViewInterface extends JFrame {

    private JButton addClientInformation;
    private JButton updateClientInformation;
    private JButton viewClientInformation;
    private JButton createClientAccount;
    private JButton updateClientAccount;
    private JButton deleteClientAccount;
    private JButton viewClientAccount;
    private JButton transferMoney;
    private JButton processBills;

    public EmployeeViewInterface() throws HeadlessException {
        setSize(200, 300);
        setLocationRelativeTo(null);
        initializeFields();
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        add(addClientInformation);
        add(updateClientInformation);
        add(viewClientInformation);
        add(createClientAccount);
        add(updateClientAccount);
        add(deleteClientAccount);
        add(viewClientAccount);
        add(transferMoney);
        add(processBills);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void initializeFields() {
        addClientInformation = new JButton("Add Client Information");
        updateClientInformation = new JButton("Update Client Information");
        viewClientInformation = new JButton("View Client Information");
        createClientAccount = new JButton("Create Client Account");
        updateClientAccount = new JButton("Update Client Account");
        deleteClientAccount = new JButton("Delete Client Account");
        viewClientAccount = new JButton("View Client Account");
        transferMoney = new JButton("Transfer Money");
        processBills = new JButton("Process Bills");
    }

    public void setAddClientInformationListener(ActionListener addClientInformationListener) {
        addClientInformation.addActionListener(addClientInformationListener);
    }

    public void setUpdateClientInformationListener(ActionListener updateClientInformationListelerListener){
        updateClientInformation.addActionListener(updateClientInformationListelerListener);
    }

    public void setViewClientInformationListener(ActionListener viewClientInformationListelerListener){
        viewClientInformation.addActionListener(viewClientInformationListelerListener);
    }

    public void setCreateClientAccountListener(ActionListener createClientAccountListener){
        createClientAccount.addActionListener(createClientAccountListener);
    }

    public void setUpdateClientAccountListener(ActionListener updateClientAccountListener){
        updateClientAccount.addActionListener(updateClientAccountListener);
    }

    public void setDeleteClientAccountListener(ActionListener deleteClientAccountListener){
        deleteClientAccount.addActionListener(deleteClientAccountListener);
    }

    public void setViewClientAccountListener(ActionListener viewClientAccountListener) {
        viewClientAccount.addActionListener(viewClientAccountListener);
    }

    public void setTransferMoneyListener(ActionListener transferMoneyListener){
        transferMoney.addActionListener(transferMoneyListener);
    }
    public void setProcessBillsListener(ActionListener processBillsListener){
        processBills.addActionListener(processBillsListener);
    }
}