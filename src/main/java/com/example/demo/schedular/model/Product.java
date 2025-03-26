package com.example.demo.schedular.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Product{
    @Id
    private String id;
    private String title;
    private double mrp;
    private double currentPrice;
    private int inventorycount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getMrp() {
        return mrp;
    }

    public void setMrp(double mrp) {
        this.mrp = mrp;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public int getInventorycount() {
        return inventorycount;
    }

    public void setInventorycount(int inventorycount) {
        this.inventorycount = inventorycount;
    }
}
