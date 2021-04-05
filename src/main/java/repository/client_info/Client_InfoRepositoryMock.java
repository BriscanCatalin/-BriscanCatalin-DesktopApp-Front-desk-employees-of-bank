package repository.client_info;

import model.Client_Info;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Client_InfoRepositoryMock  implements  Client_InfoRepository {

    private List<Client_Info> client_infos;

    public Client_InfoRepositoryMock() {
        client_infos = new ArrayList<>();
    }

    @Override
    public List<Client_Info> findAll() {
        return client_infos;
    }

    @Override
    public Client_Info findClient_InfoById(Long id) {
        List<Client_Info> filteredClient_Infos = client_infos.parallelStream()
                .filter(it -> it.getId().equals(id))
                .collect(Collectors.toList());
        return (filteredClient_Infos.size() > 0 ) ? filteredClient_Infos.get(0) : null;
    }

    @Override
    public Client_Info getClient_InfoFromResultSet(ResultSet resultSet) throws SQLException {
        return null;
    }

    @Override
    public boolean save(Client_Info client_info) {
        return client_infos.add(client_info);
    }

    @Override
    public void removeAll() {
        client_infos.clear();
    }

}
