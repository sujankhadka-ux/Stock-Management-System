package model;

public class Product {

    private int id;
    private String name;
    private String sku;
    private String category;
    private int quantity;
    private double price;
    private int minLevel;

    public Product(int id, String name, String sku, String category,
                   int quantity, double price, int minLevel) {
        this.id       = id;
        this.name     = name;
        this.sku      = sku;
        this.category = category;
        this.quantity = quantity;
        this.price    = price;
        this.minLevel = minLevel;
    }

    public int    getId()       { return id; }
    public String getName()     { return name; }
    public String getSku()      { return sku; }
    public String getCategory() { return category; }
    public int    getQuantity() { return quantity; }
    public double getPrice()    { return price; }
    public int    getMinLevel() { return minLevel; }
}