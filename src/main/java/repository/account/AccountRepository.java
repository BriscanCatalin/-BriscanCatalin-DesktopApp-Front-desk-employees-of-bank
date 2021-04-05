package repository.account;

import model.Account;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface AccountRepository {

    List<Account> findAll();
    Account findAccountById(Long id);
    Account getAccountFromResultSet(ResultSet resultSet) throws SQLException;
    boolean save(Account account);
    void removeAll();

}
