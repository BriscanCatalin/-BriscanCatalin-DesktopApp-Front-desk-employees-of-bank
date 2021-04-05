package repository.Client;

import model.Client_Info;
import model.builder.Client_InfoBuilder;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import repository.Cache;
import repository.EntityNotFoundException;
import repository.client_info.Client_InfoRepository;
import repository.client_info.Client_InfoRepositoryCacheDecorator;
import repository.client_info.Client_InfoRepositoryMock;

import static org.junit.Assert.assertTrue;

public class ClientMockTest {


    private static Client_InfoRepository client_infoRepository;

    @BeforeClass
    public static void setupClass() {
        client_infoRepository = new Client_InfoRepositoryCacheDecorator(
                new Client_InfoRepositoryMock(),
                new Cache<>()
        );
    }

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void findAll() throws Exception {
        assertTrue(client_infoRepository.findAll().size() == 0);
    }

    @Test(expected = EntityNotFoundException.class)
    public void findByIdEx() throws Exception {
        client_infoRepository.findClient_InfoById(1L);
    }

    @Test
    public void save() throws Exception {
        Client_Info client_info = new Client_InfoBuilder()
                .setId(1L)
                .setName("name")
                .setIdentity_card_number("identity_card_number")
                .setPersonal_numerical_code("personal_numerical_code")
                .setAdress("address")
                .setBill("bills")
                .build();

        assertTrue(client_infoRepository.save(client_info));
    }

}
