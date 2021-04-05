package model;

public class Client_Info {

    private Long id;
    private String name;
    private String identity_card_number;
    private String personal_numerical_code;
    private String address;
    private String bills;

    public String getBills() {
        return bills;
    }

    public void setBills(String bills) {
        this.bills = bills;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdentity_card_number() {
        return identity_card_number;
    }

    public void setIdentity_card_number(String identity_card_number) {
        this.identity_card_number = identity_card_number;
    }

    public String getPersonal_numerical_code() {
        return personal_numerical_code;
    }

    public void setPersonal_numerical_code(String personal_numerical_code) {
        this.personal_numerical_code = personal_numerical_code;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
