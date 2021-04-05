package service.account;

import model.Account;
import repository.account.AccountRepository;

import java.util.List;

public class AccountServiceImplementation implements AccountService {

    private final AccountRepository accountRepository;

    public AccountServiceImplementation(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    @Override
    public Account findAccountById(Long id) {
        return accountRepository.findAccountById(id);
    }

    @Override
    public boolean save(Account account) {
        return accountRepository.save(account);
    }
}
