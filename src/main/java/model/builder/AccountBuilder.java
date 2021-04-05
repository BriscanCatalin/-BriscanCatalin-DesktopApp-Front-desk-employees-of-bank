package model.builder;

import model.Account;

import java.util.Date;

public class AccountBuilder {

    private Account account;

    public AccountBuilder() {
        account = new Account();
    }

    public AccountBuilder setId(Long id){
        account.setId(id);
        return this;
    }

    public AccountBuilder setIdentification_number(int identification_number){
        account.setIdentification_number(identification_number);
        return this;
    }

    public AccountBuilder setType(String type){
        account.setType(type);
        return this;
    }

    public AccountBuilder setAmount_of_money(int amount_of_money){
        account.setAmount_of_money(amount_of_money);
        return this;
    }

    public AccountBuilder setDate_of_creation(Date date_of_creation){
        account.setDate_of_creation(date_of_creation);
        return this;
    }
    public AccountBuilder setBill(String bills){
        account.setBills(bills);
        return this;
    }

    public Account build(){
        return account;
    }

}
