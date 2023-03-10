package com.example.jobportalapp;

public class Pay {
   private String phone;
   private String amount;

    public Pay() {
    }

    public Pay(String phone, String amount) {
        this.phone = phone;
        this.amount = amount;
    }

    public String getPhone() {
        return phone;
    }

    public String getAmount() {
        return amount;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}



