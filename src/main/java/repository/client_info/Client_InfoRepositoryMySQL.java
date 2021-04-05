package repository.client_info;

import model.Client_Info;
import model.builder.Client_InfoBuilder;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Client_InfoRepositoryMySQL implements Client_InfoRepository{

    private final Connection connectionWrapper;

    public Client_InfoRepositoryMySQL(Connection connectionWrapper) {
        this.connectionWrapper = connectionWrapper;
    }

    @Override
    public List<Client_Info> findAll() {
        Connection connection = connectionWrapper;
        List<Client_Info> client_infos = new ArrayList<>();

        try{
            Statement statement = connection.createStatement();
            String sql = "Select * from client";
            ResultSet resultSet = statement.executeQuery(sql);

            while(resultSet.next()){
                client_infos.add(getClient_InfoFromResultSet(resultSet));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return client_infos;
    }

    public Client_Info getClient_InfoFromResultSet(ResultSet resultSet) throws SQLException {
        return new Client_InfoBuilder()
                .setName(resultSet.getString("name"))
                .setId(resultSet.getLong("id"))
                .setIdentity_card_number(resultSet.getString("identity_card_number"))
                .setPersonal_numerical_code(resultSet.getString("personal_numerical_code"))
                .setAdress(resultSet.getString("address"))
                .setBill(resultSet.getString("bills"))
                .build();
    }

    @Override
    public Client_Info findClient_InfoById(Long id) {
        return null;
    }

    @Override
    public boolean save(Client_Info client_info) {
        Connection connection = this.connectionWrapper;
        try{
            PreparedStatement preparedStatement = connection.
                    prepareStatement("insert into client  (null, ?, ?, ?, ?, ?)");
            preparedStatement.setString(1 , client_info.getName());
            preparedStatement.setString(2 , client_info.getIdentity_card_number());
            preparedStatement.setString(3 , client_info.getPersonal_numerical_code());
            preparedStatement.setString(4 , client_info.getAddress());
            preparedStatement.setString(5 , client_info.getBills());

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
