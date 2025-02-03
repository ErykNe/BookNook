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

    public String getAccessoryID() {
        return String.valueOf(accessoryID);
    }

    public String getAccessoryName() {
        return accessoryName;
    }

    public String getAccessoryPrice() {
        return String.format("%.2f", accessoryPrice);
    }

    public String getQuantity() {
        return String.valueOf(quantity);
    }

}
