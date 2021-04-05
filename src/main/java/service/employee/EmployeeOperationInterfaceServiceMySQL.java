package service.employee;

import model.Account;
import model.Client_Info;
import repository.account.AccountRepository;
import repository.client_info.Client_InfoRepository;
import repository.employee.EmployeeRepository;
import repository.security.RightsRolesRepository;
import view.EmployeeViewInterface;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static database.Constants.Tables.*;

public class EmployeeOperationInterfaceServiceMySQL implements EmployeeOperationInterfaceService {

    private final EmployeeRepository employeeRepository;
    private final RightsRolesRepository rightsRolesRepository;
    private final Client_InfoRepository client_infoRepository;
    private final AccountRepository accountRepository;
    private final Connection connection;
    public static String reportEmployee;

    public EmployeeOperationInterfaceServiceMySQL( EmployeeRepository employeeRepository,RightsRolesRepository rightsRolesRepository,
                                                  Client_InfoRepository client_infoRepository,AccountRepository accountRepository,Connection connection) {
        this.employeeRepository = employeeRepository;
        this.rightsRolesRepository = rightsRolesRepository;
        this.client_infoRepository = client_infoRepository;
        this.accountRepository = accountRepository;
        this.connection = connection;
        this.reportEmployee = new String();
    }

    public boolean containsChars(String str) {

        if (str == "")
            return true;
        for(int i = 0; i < str.length(); i++)
            if(str.charAt(i) > '9' || str.charAt(i) < '0')
                return true;
        return false;

    }

    public boolean checkCnp(String s){
        if (s.length() < 13 || s.length() > 13 || containsChars(s) == true) {
            System.out.println("CNP invalid !!! ~ It must have 13 numbers ~");
            return false;
        }
        return true;
    }

    @Override
    public void addClientInformation(EmployeeViewInterface employeeViewInterface) {
        Scanner in = new Scanner(System.in);

        try {
            PreparedStatement insertStatement = connection
                    .prepareStatement("INSERT IGNORE INTO `" + CLIENT_INFO + "` values (null, ?, ?, ?, ?, ?)");
            System.out.println("Introduce client Name : ");
            String s = in.nextLine();
            insertStatement.setString(1, s);

            System.out.println("Introduce client Identity Card Number : ");
            String s2 = in.nextLine();
            insertStatement.setString(2, s2);

            System.out.println("Introduce client Personal Numerical Code : ");
            String s3 = in.nextLine();
            if (checkCnp(s3) == false) {
                JOptionPane.showMessageDialog(employeeViewInterface.getContentPane(),
                        "CNP invalid !!! ~ It must have 13 numbers ~");
                reportEmployee += ("CNP invalid !!! ~ It must have 13 numbers ~\n");
                return;
            }
            insertStatement.setString(3, s3);

            System.out.println("Introduce client Address : ");
            String s4 = in.nextLine();
            insertStatement.setString(4, s4);

            System.out.println("Introduce client bills : ");
            String s5 = in.nextLine();
            insertStatement.setString(5, s5);

            insertStatement.executeUpdate();

            JOptionPane.showMessageDialog(employeeViewInterface.getContentPane(),
                    "[Client : " + s + ", " + s2
                            + ", " + s3 + ", " + s4 + ", " + s5 + " ]");
            reportEmployee += ("Created " + "[Client : " + s + ", " + s2
                    + ", " + s3 + ", " + s4 + ", " + s5 + " ]\n");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateClientInformationByName(String name, EmployeeViewInterface employeeViewInterface) {
        Scanner in = new Scanner(System.in);
        PreparedStatement preparedStatement;
        PreparedStatement updateStatement;
        try{
            String sqlSequence = "Select * from " + CLIENT_INFO + " where `name`=\'" + name + "\'";
            preparedStatement = connection.prepareStatement(sqlSequence);
            ResultSet updateResult = preparedStatement.executeQuery(sqlSequence);
            if (updateResult.next() == false){
                JOptionPane.showMessageDialog(employeeViewInterface.getContentPane(),
                        "The client with name : " + name + " does not exists !");
                reportEmployee += ("The client with name : " + name + " does not exists !\n");
                return;
            }
            Client_Info client_info = client_infoRepository.getClient_InfoFromResultSet(updateResult);

            System.out.println("Introduce a new name : ");
            client_info.setName(in.nextLine());
            System.out.println("Introduce a new identity_card_number : ");
            client_info.setIdentity_card_number(in.nextLine());
            System.out.println("Introduce a new personal_numerical_code : ");
            client_info.setPersonal_numerical_code(in.nextLine());
            if (checkCnp(client_info.getPersonal_numerical_code()) == false){
                JOptionPane.showMessageDialog(employeeViewInterface.getContentPane(),
                        "CNP invalid !!! ~ It must have 13 numbers ~" );
                reportEmployee += ("CNP invalid !!! ~ It must have 13 numbers ~\n");
                return;
            }
            System.out.println("Introduce a new address : ");
            client_info.setAddress(in.nextLine());
            String updateSql = "UPDATE " + CLIENT_INFO + " SET " + " name = '" + client_info.getName() + "', identity_card_number = '" + client_info.getIdentity_card_number() +
                     "' , personal_numerical_code = '" + client_info.getPersonal_numerical_code() +
                    "' , address = '" + client_info.getAddress() + "' , bills = '" + client_info.getBills() + "' WHERE name = '" + name + "' ;";
            updateStatement = connection.prepareStatement(updateSql);
            updateStatement.executeUpdate(updateSql);

            JOptionPane.showMessageDialog(employeeViewInterface.getContentPane(),
                    "[Client updated : " + client_info.getId() + ", " + client_info.getName() + ", " + client_info.getIdentity_card_number()
                            + ", " + client_info.getPersonal_numerical_code() + ", " + client_info.getAddress() + ", " + client_info.getBills() + " ]");
            reportEmployee += ("[Client updated : " + client_info.getId() + ", " + client_info.getName() + ", " + client_info.getIdentity_card_number()
                    + ", " + client_info.getPersonal_numerical_code() + ", " + client_info.getAddress() + ", " + client_info.getBills() + " ]\n");

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void viewClientInformationByName(String name, EmployeeViewInterface employeeViewInterface) {
        PreparedStatement preparedStatement;
        try{
            String sqlSequence = "Select * from " + CLIENT_INFO + " where `name`=\'" + name + "\'";
            preparedStatement = connection.prepareStatement(sqlSequence);
            ResultSet updateResult = preparedStatement.executeQuery(sqlSequence);
            if (updateResult.next() == false){
                JOptionPane.showMessageDialog(employeeViewInterface.getContentPane(),
                        "The account with name : " + name + " does not exists !");
                reportEmployee += ("The account with name : " + name + " does not exists !\n");
                return;
            }
            Client_Info client_info = client_infoRepository.getClient_InfoFromResultSet(updateResult);

            JOptionPane.showMessageDialog(employeeViewInterface.getContentPane(),
                    "[Client : " + client_info.getId() + ", " + client_info.getName() + ", " + client_info.getIdentity_card_number()
                            + ", " + client_info.getPersonal_numerical_code() + ", " + client_info.getAddress() + ", " + client_info.getBills() + " ]");
            reportEmployee += ("[Client : " + client_info.getId() + ", " + client_info.getName() + ", " + client_info.getIdentity_card_number()
                    + ", " + client_info.getPersonal_numerical_code() + ", " + client_info.getAddress() + ", " + client_info.getBills() + " ]\n");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void createAccount(EmployeeViewInterface employeeViewInterface) {
        Scanner in = new Scanner(System.in);

        try {
            PreparedStatement insertStatement = connection
                    .prepareStatement("INSERT IGNORE INTO `" + ACCOUNT + "` values (null, ?, ?, ?, ?)");
            System.out.println("Introduce identification_number : ");
            String  str = in.next();
            if (containsChars(str)){
                JOptionPane.showMessageDialog(employeeViewInterface.getContentPane(),
                        "The identification_number must be a number !!!");
                reportEmployee += ("The identification number must be a number !!!\n");
                return;
            }
            Integer s = Integer.parseInt(str);
            insertStatement.setInt(1, s);

            System.out.println("Introduce type : ");
            String s2 = in.next();
            insertStatement.setString(2, s2);

            System.out.println("Introduce amount_of_money : ");
            String str1 = in.next();
            if (containsChars(str1)){
                JOptionPane.showMessageDialog(employeeViewInterface.getContentPane(),
                        "The amount of money must be a number !!!");
                reportEmployee += ("The amount of money must be a number !!!\n");
                return;
            }
            Integer s1 = Integer.parseInt(str1);
            insertStatement.setInt(3, s1);

            Long millis = System.currentTimeMillis();
            Date date = new Date(millis);
            System.out.println("The date_of_creation : " + date);
            insertStatement.setDate(4, date);
            insertStatement.executeUpdate();
            JOptionPane.showMessageDialog(employeeViewInterface.getContentPane(),
                    "[Client_Account : " + s + ", " + s2
                            + ", " + s1 + ", " + date + " ]");
            reportEmployee += ("[Client_Account : " + s + ", " + s2
                    + ", " + s1 + ", " + date + " ]\n");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Account updateAccount(Long id,  EmployeeViewInterface employeeViewInterface) {
        Scanner in = new Scanner(System.in);
        PreparedStatement preparedStatement;
        PreparedStatement updateStatement;
        try{
            String sqlSequence = "Select * from " + ACCOUNT + " where `id`=\'" + id + "\'";
            preparedStatement = connection.prepareStatement(sqlSequence);
            ResultSet updateResult = preparedStatement.executeQuery(sqlSequence);
            if (updateResult.next() == false){
                JOptionPane.showMessageDialog(employeeViewInterface.getContentPane(),
                        "The account with id : " + id + " does not exists !");
                reportEmployee += ( "The account with id : " + id + " does not exists !\n");
                return null;
            }
            Account account = accountRepository.getAccountFromResultSet(updateResult);

            System.out.println("Introduce a new identification_number : ");
            String indetifi = in.nextLine();
            if (containsChars( indetifi )){
                JOptionPane.showMessageDialog(employeeViewInterface.getContentPane(),
                        "The identification_number must be a number !!!");
                reportEmployee += ("The identification_number must be a number !!!\n");
                return null;
            }
            account.setIdentification_number(Integer.parseInt(indetifi));
            Integer s1 = Integer.parseInt(String.valueOf(account.getIdentification_number()));

            System.out.println("Introduce a new type : ");
            account.setType(in.nextLine());

            System.out.println("Introduce a new amount_of_money : ");
            account.setAmount_of_money(Integer.parseInt(in.next()));
            if (containsChars(String.valueOf(account.getAmount_of_money()))){
                JOptionPane.showMessageDialog(employeeViewInterface.getContentPane(),
                        "The amount of money must be a number !!!");
                reportEmployee += ("The amount of money must be a number !!!\n");
                return null;
            }
            Integer s3 = Integer.parseInt(String.valueOf(account.getAmount_of_money()));

            String updateSql = "UPDATE " + ACCOUNT + " SET " + " identification_number = " + account.getIdentification_number() +
                    ", type = '" + account.getType() + "' , amount_of_money = " + account.getAmount_of_money()  + " WHERE id = " + id + " ;";
            updateStatement = connection.prepareStatement(updateSql);
            updateStatement.executeUpdate(updateSql);

            JOptionPane.showMessageDialog(employeeViewInterface.getContentPane(),
                    "[Account_Updated : " + account.getId() + ", " + account.getIdentification_number() +
                            ", " + account.getType() + ", " + account.getAmount_of_money() + ", " + account.getDate_of_creation() + " ]");
            reportEmployee += ("[Account_Updated : " + account.getId() + ", " + account.getIdentification_number() +
                    ", " + account.getType() + ", " + account.getAmount_of_money() + ", " + account.getDate_of_creation() + " ]\n");

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean deleteAccount(Long id, EmployeeViewInterface employeeViewInterface) {
        PreparedStatement preparedStatement;
        try{
            String sqlSequence = "Delete from " + ACCOUNT + " where `id`=\'" + id + "\'";
            preparedStatement = connection.prepareStatement(sqlSequence);
            int resultSet = preparedStatement.executeUpdate();
            if (resultSet == 0) {
                JOptionPane.showMessageDialog(employeeViewInterface.getContentPane(),
                        "No account found to delete with the id = " + id + " !");
                reportEmployee += ( "No account found to delete with the id = " + id + " !\n");
                return false;
            }
            JOptionPane.showMessageDialog(employeeViewInterface.getContentPane(),
                    "The account with id : " + id + " was deleted !");
            reportEmployee += ("The account with id : " + id + " was deleted !\n");
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    @Override
    public void viewAccount(Long id,  EmployeeViewInterface employeeViewInterface) {
        PreparedStatement preparedStatement;
        try{
            String sqlSequence = "Select * from " + ACCOUNT + " where `id`=\'" + id + "\'";
            preparedStatement = connection.prepareStatement(sqlSequence);
            ResultSet updateResult = preparedStatement.executeQuery(sqlSequence);
            if (updateResult.next() == false){
                JOptionPane.showMessageDialog(employeeViewInterface.getContentPane(),
                        "The account with id : " + id + " does not exists !");
                reportEmployee += ("The account with id : " + id + " does not exists !\n");
                return;
            }
            Account account = accountRepository.getAccountFromResultSet(updateResult);

            JOptionPane.showMessageDialog(employeeViewInterface.getContentPane(),
                    "[Account : " + account.getId() + ", " + account.getIdentification_number() + ", " + account.getType()
                    + ", " + account.getAmount_of_money() + ", " + account.getDate_of_creation() + " ]");
            reportEmployee += ("[Account : " + account.getId() + ", " + account.getIdentification_number() + ", " + account.getType()
                    + ", " + account.getAmount_of_money() + ", " + account.getDate_of_creation() + " ]\n");

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public boolean transferMoney(Integer id1, Integer id2, int money, EmployeeViewInterface employeeViewInterface) {
        PreparedStatement preparedStatement1, preparedStatement2;
        try {
            String sqlSequence1 = "Select * from " + ACCOUNT + " where `id`=\'" + id1 + "\'";
            preparedStatement1 = connection.prepareStatement(sqlSequence1);
            ResultSet transferResult1 = preparedStatement1.executeQuery(sqlSequence1);
            if (transferResult1.next() == false){
                JOptionPane.showMessageDialog(employeeViewInterface.getContentPane(),
                        "The account with id : " + id1 + " does not exists !");
                reportEmployee += ("The account with id : " + id1 + " does not exists !\n");
                return false;
            }
            String sqlSequence2 = "Select * from " + ACCOUNT + " where `id`=\'" + id2 + "\'";
            preparedStatement2 = connection.prepareStatement(sqlSequence1);
            ResultSet transferResult2 = preparedStatement2.executeQuery(sqlSequence2);
            if (transferResult2.next() == false){
                JOptionPane.showMessageDialog(employeeViewInterface.getContentPane(),
                        "The account with id : " + id2 + " does not exists !");
                reportEmployee += ("The account with id : " + id2 + " does not exists !\n");
                return false;
            }

            Integer accountAmount1 = transferResult1.getInt(("amount_of_money"));
            Integer accountAmount2 = transferResult2.getInt(("amount_of_money"));

            accountAmount1 = accountAmount1 - money;
            accountAmount2 = accountAmount2 + money;

            if (accountAmount1 < 0){
                JOptionPane.showMessageDialog(employeeViewInterface.getContentPane(),
                        "Not enough money in the account !");
                reportEmployee += ("Not enough money in the account !\n");
                return false;
            }

            String updateSql1 = "UPDATE " + ACCOUNT + " SET " + "amount_of_money = " + accountAmount1 + " WHERE id = " + id1 + " ;";
            preparedStatement1 = connection.prepareStatement(updateSql1);
            preparedStatement1.executeUpdate(updateSql1);

            String updateSql2 = "UPDATE " + ACCOUNT + " SET " + "amount_of_money = " + accountAmount2 + " WHERE id = " + id2 + " ;";
            preparedStatement2 = connection.prepareStatement(updateSql2);
            preparedStatement2.executeUpdate(updateSql2);

            JOptionPane.showMessageDialog(employeeViewInterface.getContentPane(),
                    "Successfully transfer " + money + "$ between id1 : " + id1 + " and id2 : " + id2 + " !");
            reportEmployee += ("Successfully transfer " + money + "$ between id1 : " + id1 + " and id2 : " + id2 + " !\n");
            return true;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    @Override
    public void processBills(String name, EmployeeViewInterface employeeViewInterface) {
        PreparedStatement preparedStatement;
        try{
            String sqlSequence = "Select * from " + CLIENT_INFO + " where `name`=\'" + name + "\'";
            preparedStatement = connection.prepareStatement(sqlSequence);
            ResultSet updateResult = preparedStatement.executeQuery(sqlSequence);
            if (updateResult.next() == false){
                JOptionPane.showMessageDialog(employeeViewInterface.getContentPane(),
                        "The client with name : " + name + " does not exists !");
                reportEmployee += ("The client with name : " + name + " does not exists !\n");
                return;
            }
            Client_Info client_info = client_infoRepository.getClient_InfoFromResultSet(updateResult);
            String []  str = client_info.getBills().split(", ");
            Float sum = 0.f;
            if (str == null)
                sum = 0.f;
            else {
                for (String str1 : str) {
                    String sql = "Select * from " + BILL + " where `name`= '" + str1 + "';";
                    PreparedStatement preparedStatement1 = connection.prepareStatement(sql);
                    ResultSet res = preparedStatement.executeQuery(sql);
                    if (res.next() == false){
                        JOptionPane.showMessageDialog(employeeViewInterface.getContentPane(),
                                "The entred bill for client : " + name + " -> '" + str1 + "' isn't correct !");
                        reportEmployee += ("The entred bill for client : " + name + " -> '" + str1 + "' isn't correct !\n");
                        return;
                    }
                    Long nr = res.getLong("cost");
                    sum += nr;
                }
            }

            JOptionPane.showMessageDialog(employeeViewInterface.getContentPane(),
                    "[Client : " + client_info.getId() + ", " + client_info.getName()+ ", " + client_info.getIdentity_card_number()
                            + ", " + client_info.getPersonal_numerical_code() + ", " + client_info.getAddress() + ", "
                            + client_info.getBills() + " ]\n\t Total sum for all unpaid bills is : " + sum);
            reportEmployee += ("[Client : " + client_info.getId() + ", " + client_info.getName()+ ", " + client_info.getIdentity_card_number()
                    + ", " + client_info.getPersonal_numerical_code() + ", " + client_info.getAddress() + ", "
                    + client_info.getBills() + " ]\n\t Total sum for all unpaid bills is : " + sum + "\n");

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


}
