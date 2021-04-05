package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class AdminViewInterface extends JFrame {

        private JButton createEmployee;
        private JButton updateEmployee;
        private JButton deleteEmployee;
        private JButton readEmployee;
        private JButton reportEmployee;

    public AdminViewInterface() throws HeadlessException {
            setSize(200, 300);
            setLocationRelativeTo(null);
            initializeFields();
            setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
            add(createEmployee);
            add(updateEmployee);
            add(deleteEmployee);
            add(readEmployee);
            add(reportEmployee);

            setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            setVisible(true);
        }

        private void initializeFields() {
            createEmployee = new JButton("Create Employee");
            updateEmployee = new JButton("Update Employee");
            deleteEmployee = new JButton("Delete Employee");
            readEmployee = new JButton("Read Employee");
            reportEmployee = new JButton("Report Employee");
        }

    public void setCreateEmployeeListener(ActionListener  createEmployeeListener) {
        createEmployee.addActionListener(createEmployeeListener);
    }

    public void setUpdateEmployeeListener(ActionListener updateEmployeeListener){
        updateEmployee.addActionListener(updateEmployeeListener);
    }

    public void setDeleteEmployeeListener(ActionListener deleteEmployeeListener){
        deleteEmployee.addActionListener(deleteEmployeeListener);
    }

    public void setReadEmployeeListener(ActionListener readEmployeeListener){
        readEmployee.addActionListener(readEmployeeListener);
    }

    public void setReportEmployeeListener(ActionListener reportEmployeeListener){
        reportEmployee.addActionListener(reportEmployeeListener);
    }



}
