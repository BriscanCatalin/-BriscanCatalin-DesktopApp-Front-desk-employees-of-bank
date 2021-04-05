package service.client_info;

import model.Client_Info;
import repository.client_info.Client_InfoRepository;

import java.util.List;

public class Client_InfoServiceImplementation implements Client_InfoService {

    private final Client_InfoRepository client_infoRepository;

    public Client_InfoServiceImplementation(Client_InfoRepository client_infoRepository) {
        this.client_infoRepository = client_infoRepository;
    }

    @Override
    public List<Client_Info> findAll() {
        return client_infoRepository.findAll();
    }

    @Override
    public Client_Info findClient_InfoById(Long id) {
        return client_infoRepository.findClient_InfoById(id);
    }

    @Override
    public boolean save(Client_Info client_info) {
        return client_infoRepository.save(client_info);
    }
}
