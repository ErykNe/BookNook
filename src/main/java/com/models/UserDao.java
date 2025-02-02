package com.models;

public class UserDao {
	
	public String username;
    public String email;
    public String password;
    public double balance;

    public UserDao(String username, String email, String password) {
    	this.username = username;
        this.email = email;
        this.password = password;
    }

    public void displayUserInfo() {
        System.out.println("Email: " + email);
        System.out.println("Password: " + password);
        System.out.println("Balance: $" + balance);
    }	
}
