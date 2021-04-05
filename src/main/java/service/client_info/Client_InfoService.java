package service.client_info;

import model.Client_Info;

import java.util.List;

public interface Client_InfoService {

    List<Client_Info> findAll();

    Client_Info findClient_InfoById(Long id);

    boolean save(Client_Info client_info);

}
