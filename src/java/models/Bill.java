package models;

import java.sql.Timestamp;

public class Bill {
    private int id;
    private int customer_id;
    private int item_id;
    private int units;
    private float unit_price;
    private float total_amount;
    private Timestamp billing_date;
    private Timestamp createdAt;
    
    // Constructor for creating a new bills (id and createdAt handled by DB)
    public Bill(int customer_id, int item_id, int units, float unit_price, float total_amount, Timestamp billing_date) {
        this.customer_id = customer_id;
        this.item_id = item_id;
        this.units = units;
        this.unit_price = unit_price;
        this.total_amount = total_amount;
        this.billing_date = billing_date;
    }
    
    // Constructor for reading a bills from DB (all fields)
    public Bill(int id, int customer_id, int item_id, int units, float unit_price, float total_amount, Timestamp billing_date, Timestamp createdAt) {
        this.id = id;
        this.customer_id = customer_id;
        this.item_id = item_id;
        this.units = units;
        this.unit_price = unit_price;
        this.total_amount = total_amount;
        this.billing_date = billing_date;
        this.createdAt = createdAt;
    }
    
    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public int getCustomerId() { return customer_id; }
    public void setCustomerId(int customer_id) { this.customer_id = customer_id; }
    
    public int getItemId() { return item_id; }
    public void setItemId(int item_id) { this.item_id = item_id; }
    
    public int getUnits() { return units; }
    public void setUnits(int units) { this.units = units; }
    
    public float getUnitPrice() { return unit_price; }
    public void setUnitPrice(float unit_price) { this.unit_price = unit_price; }
    
    public float getTotalAmount() { return total_amount; }
    public void setTotalAmount(float total_amount) { this.total_amount = total_amount; }
    
    public Timestamp getBillingDate() { return billing_date; }
    public void setBillingDate(Timestamp billing_date) { this.billing_date = billing_date; }
    
    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
}
