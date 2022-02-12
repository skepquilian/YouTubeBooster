package com.example.youtubebooster;

public class User {
    private String email;
    private String phone;
    private String money_to_paid;

    public User(String email, String phone, String money_to_paid) {
        this.email = email;
        this.phone = phone;
        this.money_to_paid = money_to_paid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMoney_to_paid() {
        return money_to_paid;
    }

    public void setMoney_to_paid(String money_to_paid) {
        this.money_to_paid = money_to_paid;
    }

    public User() {

    }
}
