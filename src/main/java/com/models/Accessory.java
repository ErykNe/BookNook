package com.models;

public class Accessory extends Item {

    public int accessoryID;
    public int quantity;

    public Accessory(int accessoryID, String accessoryName, double accessoryPrice, int quantity) {
        super(accessoryName, accessoryPrice,quantity);
        this.accessoryID = accessoryID;
        this.quantity = quantity;
    }

    public int getAccessoryID() {
        return accessoryID;
    }

}