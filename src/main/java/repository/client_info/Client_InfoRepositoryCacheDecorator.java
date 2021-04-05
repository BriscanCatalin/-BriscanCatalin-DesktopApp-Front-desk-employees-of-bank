package repository.client_info;

import model.Client_Info;
import repository.Cache;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class Client_InfoRepositoryCacheDecorator extends Client_InfoRepositoryDecorator {

    private Cache<Client_Info> cache;

    public Client_InfoRepositoryCacheDecorator(Client_InfoRepository client_infoRepository, Cache<Client_Info> cache) {
        super(client_infoRepository);
        this.cache = cache;
    }

    @Override
    public List<Client_Info> findAll() {
        if (cache.hasResult())
            return cache.load();

        List<Client_Info> client_infos = client_infoRepository.findAll();
        cache.save(client_infos);
        return client_infos;    }

    @Override
    public Client_Info findClient_InfoById(Long id) {
        return client_infoRepository.findClient_InfoById(id);
    }

    @Override
    public Client_Info getClient_InfoFromResultSet(ResultSet resultSet) throws SQLException {
        return null;
    }

    @Override
    public boolean save(Client_Info client_info) {
        cache.invalidateCache();
        return client_infoRepository.save(client_info);
    }

    @Override
    public void removeAll() {
        cache.invalidateCache();
        client_infoRepository.removeAll();
    }

}
