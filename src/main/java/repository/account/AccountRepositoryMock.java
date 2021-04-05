package repository.account;

import model.Account;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AccountRepositoryMock implements AccountRepository {

    private List<Account> accounts;

    public AccountRepositoryMock() {
        accounts = new ArrayList<>();
    }

    @Override
    public List<Account> findAll() {
        return accounts;
    }

    @Override
    public Account findAccountById(Long id) {
        List<Account> filteredAccounts = accounts.parallelStream()
                .filter(it -> it.getId().equals(id))
                .collect(Collectors.toList());
        return (filteredAccounts.size() > 0) ? filteredAccounts.get(0) : null;
    }

    @Override
    public Account getAccountFromResultSet(ResultSet resultSet) throws SQLException {
        return null;
    }

    @Override
    public boolean save(Account account) {
        return accounts.add(account);
    }

    @Override
    public void removeAll() {
        accounts.clear();
    }
}
