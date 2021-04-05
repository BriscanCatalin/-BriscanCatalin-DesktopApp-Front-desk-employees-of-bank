package repository.client_info;

import model.Client_Info;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface Client_InfoRepository {

    List<Client_Info> findAll();

    Client_Info findClient_InfoById(Long id);

    Client_Info getClient_InfoFromResultSet(ResultSet resultSet) throws SQLException;

    boolean save(Client_Info client_info);

    void removeAll();
}
