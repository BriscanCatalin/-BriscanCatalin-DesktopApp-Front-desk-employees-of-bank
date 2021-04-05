package model;

import java.util.Date;

public class Account {

    private Long id;
    private int identification_number;
    private String type;
    private int amount_of_money;
    private Date date_of_creation;
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

    public int getIdentification_number() {
        return identification_number;
    }

    public void setIdentification_number(int identification_number) {
        this.identification_number = identification_number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getAmount_of_money() {
        return amount_of_money;
    }

    public void setAmount_of_money(int amount_of_money) {
        this.amount_of_money = amount_of_money;
    }

    public Date getDate_of_creation() {
        return date_of_creation;
    }

    public void setDate_of_creation(Date date_of_creation) {
        this.date_of_creation = date_of_creation;
    }
}
