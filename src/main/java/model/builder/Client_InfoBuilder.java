package model.builder;

import model.Client_Info;

public class Client_InfoBuilder {

    private Client_Info client_info;

    public Client_InfoBuilder(){
        client_info = new Client_Info();
    }

    public Client_InfoBuilder setId(Long id){
        client_info.setId(id);
        return this;
    }

    public Client_InfoBuilder setName(String name){
        client_info.setName(name);
        return this;
    }

    public Client_InfoBuilder setIdentity_card_number(String identity_card_number){
        client_info.setIdentity_card_number(identity_card_number);
        return this;
    }

    public Client_InfoBuilder setPersonal_numerical_code(String personal_numerical_code){
        client_info.setPersonal_numerical_code(personal_numerical_code);
        return this;
    }

    public Client_InfoBuilder setAdress(String adress){
        client_info.setAddress(adress);
        return this;
    }
    public Client_InfoBuilder setBill(String bills){
        client_info.setBills(bills);
        return this;
    }

    public Client_Info build(){
        return client_info;
    }

}
