package repository.account;

public abstract class AccountRepositoryDecorator implements AccountRepository {

    protected AccountRepository accountRepository;

    public AccountRepositoryDecorator(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }
}
