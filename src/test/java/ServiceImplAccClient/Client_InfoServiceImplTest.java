package ServiceImplAccClient;

import model.Client_Info;
import org.junit.Before;
import org.junit.Test;
import repository.EntityNotFoundException;
import repository.client_info.Client_InfoRepositoryMock;
import service.client_info.Client_InfoService;
import service.client_info.Client_InfoServiceImplementation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class Client_InfoServiceImplTest {



    private Client_InfoService client_infoServic;

    @Before
    public void setup() {
        client_infoServic = new Client_InfoServiceImplementation(new Client_InfoRepositoryMock());
    }

    @Test
    public void findAll() throws Exception {
        assertEquals(0, client_infoServic.findAll().size());
    }

    @Test(expected = EntityNotFoundException.class)
    public void findByIdEx() throws Exception {
        client_infoServic.findClient_InfoById(1L);
    }

    @Test
    public void save() throws Exception {
        assertTrue(client_infoServic.save(new Client_Info()));
    }


}
