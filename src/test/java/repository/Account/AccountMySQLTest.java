package repository.Account;

import database.DBConnectionFactory;
import model.Account;
import model.builder.AccountBuilder;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import repository.Cache;
import repository.account.AccountRepository;
import repository.account.AccountRepositoryCacheDecorator;
import repository.account.AccountRepositoryMySQL;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

public class AccountMySQLTest {

    private static AccountRepository accountRepository;

    @BeforeClass
    public static void setupClass() {
        accountRepository = new AccountRepositoryCacheDecorator(
                new AccountRepositoryMySQL(
                        new DBConnectionFactory().getConnectinWrapper(true).getConnection()
                ),
                new Cache<>()
        );
    }

    @Before
    public void cleanUp() {
        accountRepository.removeAll();
    }

    @Test
    public void findAll() throws Exception {
        List<Account> accountList = accountRepository.findAll();
        assertEquals(accountList.size(), 0);
    }

    @Test
    public void findAllWhenDbNotEmpty() throws Exception {
        Account account = new AccountBuilder()
                .setIdentification_number(1)
                .setType("type")
                .setAmount_of_money(0)
                .setDate_of_creation(new Date())
                .setBill("bill")
                .build();
        accountRepository.save(account);
        accountRepository.save(account);
        accountRepository.save(account);

        List<Account> accounts = accountRepository.findAll();
        assertEquals(accounts.size(), 0);
    }

    @Test
    public void findById() throws Exception {

    }

    @Test
    public void save() throws Exception {
        assertTrue(accountRepository.save(
                new AccountBuilder()
                        .setIdentification_number(0)
                        .setType("type")
                        .setAmount_of_money(0)
                        .setDate_of_creation(new Date())
                        .setBill("bill")
                        .build()
        ));
    }

    @Test
    public void removeAll() throws Exception {

    }

}
