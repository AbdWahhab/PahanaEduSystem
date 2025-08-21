package models;

import java.sql.Timestamp;

public class Item {
    private int id;
    private String code;
    private String title;
    private String description;
    private String category;
    private float price;
    private int stock;
    private Timestamp createdAt;
    
    // Constructor for creating a new item (id and createdAt handled by DB)
    public Item(String code,String title, String description, String category, float price, int stock) {
        this.code = code;
        this.title = title;
        this.description = description;
        this.category = category;
        this.price = price;
        this.stock = stock;
    }
    
    // Constructor for reading a item from DB (all fields)
    public Item(int id, String code, String title, String description, String category, float price, int stock, Timestamp createdAt) {
        this.id = id;
        this.code = code;
        this.title = title;
        this.description = description;
        this.category = category;
        this.price = price;
        this.stock = stock;
        this.createdAt = createdAt;
    }
    
    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    
    public float getPrice() { return price; }
    public void setPrice(float price) { this.price = price; }
    
    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }
    
    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
}
