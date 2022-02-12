package com.example.youtubebooster;

public class VlogModel {
    private String links;
    private String phone_number;
    private String money_to_paid;
    private String channel__id;

    public VlogModel() {
    }

    public VlogModel(String links, String phone_number, String money_to_paid, String channel__id) {
        this.links = links;
        this.phone_number = phone_number;
        this.money_to_paid = money_to_paid;
        this.channel__id = channel__id;
    }

    public String getLinks() {
        return links;
    }

    public void setLinks(String links) {
        this.links = links;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getMoney_to_paid() {
        return money_to_paid;
    }

    public void setMoney_to_paid(String money_to_paid) {
        this.money_to_paid = money_to_paid;
    }

    public String getChannel__id() {
        return channel__id;
    }

    public void setChannel__id(String channel__id) {
        this.channel__id = channel__id;
    }
}
