package repository.Account;

import model.Account;
import model.builder.AccountBuilder;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import repository.Cache;
import repository.EntityNotFoundException;
import repository.account.AccountRepository;
import repository.account.AccountRepositoryCacheDecorator;
import repository.account.AccountRepositoryMock;

import java.util.Date;
import static org.junit.Assert.assertTrue;

public class AccountMockTest {

    private static AccountRepository accountRepository;

    @BeforeClass
    public static void setupClass() {
        accountRepository = new AccountRepositoryCacheDecorator(
                new AccountRepositoryMock(),
                new Cache<>()
        );
    }

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void findAll() throws Exception {
        assertTrue(accountRepository.findAll().size() == 0);
    }

    @Test(expected = EntityNotFoundException.class)
    public void findByIdEx() throws Exception {
        accountRepository.findAccountById(1L);
    }

    @Test
    public void save() throws Exception {
        Account account = new AccountBuilder()
                .setId(1L)
                .setIdentification_number(1)
                .setType("type")
                .setAmount_of_money(0)
                .setDate_of_creation(new Date())
                .setBill("bills")
                .build();

        assertTrue(accountRepository.save(account));
    }

}
