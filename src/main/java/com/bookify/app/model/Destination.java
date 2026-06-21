package com.bookify.app.model;

// --------------------------------------------------
// Destination – Model class describing a travel spot.
// Fields: id, name, country, description, price.
// --------------------------------------------------

public class Destination {
    private int id; // primary key
    private String name; // city / place name
    private String country; // country
    private String description; // short marketing text
    private double pricePerPerson; // base cost

    public Destination() {
    }

    public Destination(int id, String name, String country, String description, double pricePerPerson) {
        this.id = id;
        this.name = name;
        this.country = country;
        this.description = description;
        this.pricePerPerson = pricePerPerson;
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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPricePerPerson() {
        return pricePerPerson;
    }

    public void setPricePerPerson(double pricePerPerson) {
        this.pricePerPerson = pricePerPerson;
    }

    @Override
    public String toString() {
        return name + ", " + country + " ($" + pricePerPerson + ")";
    }
}