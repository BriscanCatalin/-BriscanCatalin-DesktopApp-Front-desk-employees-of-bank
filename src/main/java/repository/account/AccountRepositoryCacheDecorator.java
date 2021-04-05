package repository.account;

import model.Account;
import repository.Cache;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class AccountRepositoryCacheDecorator extends AccountRepositoryDecorator {

    private Cache<Account> cache;

    public AccountRepositoryCacheDecorator(AccountRepository accountRepository, Cache<Account> cache) {
        super(accountRepository);
        this.cache = cache;
    }

    @Override
    public List<Account> findAll() {
        if (cache.hasResult())
            return cache.load();

        List<Account> accounts = accountRepository.findAll();
        cache.save(accounts);
        return accounts;
    }

    @Override
    public Account findAccountById(Long id) {
        return accountRepository.findAccountById(id);
    }

    @Override
    public Account getAccountFromResultSet(ResultSet resultSet) throws SQLException {
        return null;
    }

    @Override
    public boolean save(Account account) {
        cache.invalidateCache();
        return accountRepository.save(account);
    }

    @Override
    public void removeAll() {
        cache.invalidateCache();
        accountRepository.removeAll();
    }
}
