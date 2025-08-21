package models;

import java.sql.Timestamp;

public class Customer {
    private int id;
    private String account_number;
    private String name;
    private String address;
    private String phone;
    private Timestamp createdAt;
    
    // Constructor for creating a new customer (id and createdAt handled by DB)
    public Customer(String account_number, String name, String address, String phone) {
        this.account_number = account_number;
        this.name = name;
        this.address = address;
        this.phone = phone;
    }
    
    // Constructor for reading a customer from DB (all fields)
    public Customer(int id, String account_number, String name, String address, String phone, Timestamp createdAt) {
        this.id = id;
        this.account_number = account_number;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.createdAt = createdAt;
    }
    
    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getAccountNumber() { return account_number; }
    public void setAccountNumber(String account_number) { this.account_number = account_number; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    
    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
}
