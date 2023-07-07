package com.example.sqlcontacttest.models;

public class DbContact {
    int id;
    String contact_name,contact_email ;
    int contact_phone,contact_age,contact_img;

    public DbContact() {
    }

    public DbContact(int id, String contact_name, String contact_email, int contact_phone, int contact_age,int contact_img) {
        this.id = id;
        this.contact_name = contact_name;
        this.contact_email = contact_email;
        this.contact_phone = contact_phone;
        this.contact_age = contact_age;
        this.contact_img = contact_img;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContact_name() {
        return contact_name;
    }

    public void setContact_name(String contact_name) {
        this.contact_name = contact_name;
    }

    public String getContact_email() {
        return contact_email;
    }

    public void setContact_email(String contact_email) {
        this.contact_email = contact_email;
    }

    public int getContact_phone() {
        return contact_phone;
    }

    public void setContact_phone(int contact_phone) {
        this.contact_phone = contact_phone;
    }

    public int getContact_age() {
        return contact_age;
    }

    public void setContact_age(int contact_age) {
        this.contact_age = contact_age;
    }

    public int getContact_img() {
        return contact_img;
    }

    public void setContact_img(int contact_img) {
        this.contact_img = contact_img;
    }

}
