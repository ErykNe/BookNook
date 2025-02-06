package com.models;

public class AccessoryDao {
    public int accessoryID;
    public String accessoryName;
    public double accessoryPrice;
    public int quantity;

    public AccessoryDao(int accessoryID, String accessoryName, double accessoryPrice, int quantity) {
        this.accessoryID = accessoryID;
        this.accessoryName = accessoryName;
        this.accessoryPrice = accessoryPrice;
        this.quantity = quantity;
    }

    public int getAccessoryID() {
        return accessoryID;
    }

    public void setAccessoryID(int accessoryID) {
        this.accessoryID = accessoryID;
    }

    public String getAccessoryName() {
        return accessoryName;
    }

    public void setAccessoryName(String accessoryName) {
        this.accessoryName = accessoryName;
    }

    public double getAccessoryPrice() {
        return accessoryPrice;
    }

    public void setAccessoryPrice(double accessoryPrice) {
        this.accessoryPrice = accessoryPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}