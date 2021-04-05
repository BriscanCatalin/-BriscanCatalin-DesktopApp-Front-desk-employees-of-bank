package service.admin;

import model.Employee_User;
import model.Role;
import model.builder.Employee_UserBuilder;
import model.validation.Employee_UserValidator;
import model.validation.Notification;
import repository.employee.EmployeeRepository;
import repository.security.RightsRolesRepository;
import service.employee.EmployeeOperationInterfaceService;
import service.employee.EmployeeOperationInterfaceServiceMySQL;
import view.AdminViewInterface;

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Scanner;

import static database.Constants.Roles.EMPLOYEE;
import static database.Constants.Tables.*;

public class AdminOperationInterfaceServiceMySQL implements AdminOperationInterfaceService{

    private final EmployeeRepository employeeRepository;
    private final RightsRolesRepository rightsRolesRepository;
    private final Connection connection;

    public AdminOperationInterfaceServiceMySQL(EmployeeRepository employeeRepository, RightsRolesRepository rightsRolesRepository, Connection connection) {
        this.employeeRepository = employeeRepository;
        this.rightsRolesRepository = rightsRolesRepository;
        this.connection = connection;
    }

    private String encodePassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte [] hash = digest.digest(password.getBytes("UTF-8"));
            StringBuilder hexStr = new StringBuilder();

            for (int i = 0 ;i < hash.length ;i++){
                String hex = Integer.toHexString(0xff & hash[i]);
                if (hex.length() == 1) hexStr.append('0');
                hexStr.append(hex);
            }

            return hexStr.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Notification<Boolean> createEmployee(AdminViewInterface adminViewInterface) {
        Scanner in = new Scanner(System.in);
        Role employeeRole = rightsRolesRepository.findRoleByTitle(EMPLOYEE);

        try {
            PreparedStatement insertStatement = connection
                    .prepareStatement("INSERT IGNORE INTO `" + EMPLOYEE_USER + "` values (null, ?, ?, ?)");
            System.out.println("Introduce employee Name : ");
            String s = in.nextLine();
            insertStatement.setString(1, s);

            System.out.println("Introduce employee password : ");
            String s1 = in.nextLine();
            insertStatement.setString(2, s1);
            insertStatement.setString(3,EmployeeOperationInterfaceServiceMySQL.reportEmployee);

            Employee_User employee_user = new Employee_UserBuilder()
                    .setUsername(s)
                    .setPassword(s1)
                    .setRoles(Collections.singletonList(employeeRole))
                    .setReport(new String())
                    .build();
            Employee_UserValidator employee_userValidator = new Employee_UserValidator(employee_user);
            boolean employeeValid = employee_userValidator.validate();
            Notification<Boolean> employeeCreateNotification = new Notification<>();

            if (!employeeValid){
                employee_userValidator.getErrors().forEach(employeeCreateNotification::addError);
                employeeCreateNotification.setResult(Boolean.FALSE);
                return employeeCreateNotification;
            }else{
                employee_user.setPassword(encodePassword(s1));
                employeeCreateNotification.setResult(employeeRepository.save(employee_user));
            }

            insertStatement.executeUpdate();
            JOptionPane.showMessageDialog(adminViewInterface.getContentPane(), "[Employee : " + employee_user.getId() +
                    ", " + employee_user.getUsername() + ", " + employee_user.getPassword() + " ]");

            System.out.println("Employee created : " + s);

            return employeeCreateNotification;
        } catch (SQLException e) { }
        return null;
    }

    @Override
    public void retrieveEmployeeByName(String name, AdminViewInterface adminViewInterface) {
        PreparedStatement preparedStatement;
        try{
            String sqlSequence = "Select * from " + EMPLOYEE_USER + " where `username`=\'" + name + "\'";
            preparedStatement = connection.prepareStatement(sqlSequence);
            ResultSet updateResult = preparedStatement.executeQuery(sqlSequence);
            if (updateResult.next() == false){
                JOptionPane.showMessageDialog(adminViewInterface.getContentPane(), "The employee with username : " + name + " does not exists !");
                return ;
            }
            Long employeeId = updateResult.getLong("id");
            String employeeUsername = updateResult.getString(("username"));
            String employeePassword = updateResult.getString("password");
            String employeeReport = updateResult.getString("report");

            JOptionPane.showMessageDialog(adminViewInterface.getContentPane(), "[Employee : " + employeeId +
                    ", " + employeeUsername + ", " + employeePassword +  ", " + employeeReport + " ]");

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public Notification<Boolean> updateEmployeeByName(String name, AdminViewInterface adminViewInterface) {
        Scanner in = new Scanner(System.in);
        Role employeeRole = rightsRolesRepository.findRoleByTitle(EMPLOYEE);
        PreparedStatement preparedStatement;
        PreparedStatement updateStatement;
        try{
            String sqlSequence = "Select * from " + EMPLOYEE_USER + " where `username`=\'" + name + "\'";
            preparedStatement = connection.prepareStatement(sqlSequence);
            ResultSet updateResult = preparedStatement.executeQuery(sqlSequence);
            if (updateResult.next() == false){
                JOptionPane.showMessageDialog(adminViewInterface.getContentPane(), "The employee with username : " + name + " does not exists !");
            }else {

                Long employeeId = updateResult.getLong("id");
                String employeeUsername = updateResult.getString(("username"));
                String employeePassword = updateResult.getString(("password"));

                System.out.println("Introduce a new username : ");
                String s = in.nextLine();
                System.out.println("Introduce a new password : ");
                String s1 = in.nextLine();

                Employee_User employee_user = new Employee_UserBuilder()
                        .setUsername(s)
                        .setPassword(s1)
                        .setRoles(Collections.singletonList(employeeRole))
                        .setReport(new String())
                        .build();
                Employee_UserValidator employee_userValidator = new Employee_UserValidator(employee_user);
                boolean employeeValid = employee_userValidator.validate();
                Notification<Boolean> employeeUpdateNotification = new Notification<>();

                if (!employeeValid) {
                    employee_userValidator.getErrors().forEach(employeeUpdateNotification::addError);
                    employeeUpdateNotification.setResult(Boolean.FALSE);
                } else {
                    employee_user.setPassword(encodePassword(s1));
                    employeeUpdateNotification.setResult(employeeRepository.update(employee_user, employeeId));
                }
                JOptionPane.showMessageDialog(adminViewInterface.getContentPane(), "[Employee : " + employeeId +
                        ", " + employee_user.getUsername() + ", " + employee_user.getPassword() + " ]");
                return employeeUpdateNotification;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean deleteEmployee(String name, AdminViewInterface adminViewInterface) {
        PreparedStatement preparedStatement;
        try{
            String sqlSequence = "Delete from " + EMPLOYEE_USER + " where `username`=\'" + name + "\'";
            preparedStatement = connection.prepareStatement(sqlSequence);
            int resultSet = preparedStatement.executeUpdate();
            if (resultSet == 0) {
                JOptionPane.showMessageDialog(adminViewInterface.getContentPane(), "No account found to delete with the username = " + name + " !");
                return false;
            }
            JOptionPane.showMessageDialog(adminViewInterface.getContentPane(), "Employee deleted : " + name);
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    @Override
    public void generateReports(String name, AdminViewInterface adminViewInterface) {
        FileWriter myObj = null;
        try {
            myObj = new FileWriter("C:\\Users\\Ovi\\Desktop\\filename.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        PreparedStatement preparedStatement;
        try {
        String sqlSequence = "Select * from " + EMPLOYEE_USER + " where `username`=\'" + name + "\'";
        preparedStatement = connection.prepareStatement(sqlSequence);
        ResultSet updateResult = preparedStatement.executeQuery(sqlSequence);

        if (updateResult.next() == false){
            JOptionPane.showMessageDialog(adminViewInterface.getContentPane(),
                    "The employee with name : " + name + " does not exists !");
            return ;
        }

        Employee_User employee_user = employeeRepository.getEmployeeFromResultSet(updateResult);
        employee_user.setReport(EmployeeOperationInterfaceServiceMySQL.reportEmployee);

        myObj.write(EmployeeOperationInterfaceServiceMySQL.reportEmployee);
        JOptionPane.showMessageDialog(adminViewInterface.getContentPane(), EmployeeOperationInterfaceServiceMySQL.reportEmployee);

        myObj.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
