package repository.Client;

import database.DBConnectionFactory;
import model.Client_Info;
import model.builder.Client_InfoBuilder;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import repository.Cache;
import repository.client_info.Client_InfoRepository;
import repository.client_info.Client_InfoRepositoryCacheDecorator;
import repository.client_info.Client_InfoRepositoryMySQL;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ClientMySQLTest {

    private static Client_InfoRepository client_infoRepository;

    @BeforeClass
    public static void setupClass() {
        client_infoRepository = new Client_InfoRepositoryCacheDecorator(
                new Client_InfoRepositoryMySQL(
                        new DBConnectionFactory().getConnectinWrapper(true).getConnection()
                ),
                new Cache<>()
        );
    }

    @Before
    public void cleanUp() {
        client_infoRepository.removeAll();
    }

    @Test
    public void findAll() throws Exception {
        List<Client_Info> accountList = client_infoRepository.findAll();
        assertEquals(accountList.size(), 0);
    }

    @Test
    public void findAllWhenDbNotEmpty() throws Exception {
        Client_Info client_info = new Client_InfoBuilder()
                .setName("name")
                .setIdentity_card_number("identity_card_number")
                .setPersonal_numerical_code("personal_numerical_code")
                .setAdress("address")
                .setBill("bill")
                .build();
        client_infoRepository.save(client_info);
        client_infoRepository.save(client_info);
        client_infoRepository.save(client_info);

        List<Client_Info> client_infos = client_infoRepository.findAll();
        assertEquals(client_infos.size(), 0);
    }

    @Test
    public void findById() throws Exception {

    }

    @Test
    public void save() throws Exception {
        assertTrue(client_infoRepository.save(
                new Client_InfoBuilder()
                        .setName("name")
                        .setIdentity_card_number("identity_card_number")
                        .setPersonal_numerical_code("personal_numerical_code")
                        .setAdress("address")
                        .setBill("bill")
                        .build()
        ));
    }

    @Test
    public void removeAll() throws Exception {

    }

}
