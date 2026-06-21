package com.bookify.app.model;

// --------------------------------------------------
// Customer – a *Model* representing a customer row.
// Plain POJO with id, name, email, phone, address.
// Part of MVC: used by DAO & Controller.
// --------------------------------------------------

public class Customer {
    private int id; // primary key
    private String name; // full name
    private String email; // contact email
    private String phone; // phone number
    private String address; // billing address

    public Customer() {
    }

    public Customer(int id, String name, String email, String phone, String address) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return name + " (" + email + ")";
    }
}