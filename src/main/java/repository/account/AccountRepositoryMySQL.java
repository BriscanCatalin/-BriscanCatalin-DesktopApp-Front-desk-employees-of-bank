package repository.account;

import database.JDBConnectionWrapper;
import model.Account;
import model.builder.AccountBuilder;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountRepositoryMySQL implements AccountRepository{

    private final Connection connectionWrapper;

    public AccountRepositoryMySQL(Connection connectionWrapper) {
        this.connectionWrapper = connectionWrapper;
    }

    @Override
    public List<Account> findAll() {
        Connection connection = connectionWrapper;
        List<Account> accounts = new ArrayList<>();

        try{
            Statement statement = connection.createStatement();
            String sql = "Select * from client_account";
            ResultSet resultSet = statement.executeQuery(sql);

            while(resultSet.next()){
                accounts.add(getAccountFromResultSet(resultSet));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return accounts;
    }

    public Account getAccountFromResultSet(ResultSet resultSet) throws SQLException {
        return new AccountBuilder()
                .setIdentification_number(resultSet.getInt("identification_number"))
                .setId(resultSet.getLong("id"))
                .setType(resultSet.getString("type"))
                .setAmount_of_money(resultSet.getInt("amount_of_money"))
                .setDate_of_creation(new java.util.Date(resultSet.getDate("date_of_creation").getTime()))
                .build();
    }

    @Override
    public Account findAccountById(Long id) {
        return null;
    }

    @Override
    public boolean save(Account account) {
        Connection connection = connectionWrapper;
        try{
            PreparedStatement preparedStatement = connection.
                    prepareStatement("insert into client_account  (null, ?, ?, ?, ?, ?)");
            preparedStatement.setInt(1 , account.getIdentification_number());
            preparedStatement.setString(2 , account.getType());
            preparedStatement.setInt(3 , account.getAmount_of_money());
            preparedStatement.setDate(4 , new java.sql.Date(account.getDate_of_creation().getTime()));
            preparedStatement.setString(5, account.getBills());

            return true;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    @Override
    public void removeAll() {

    }
}
