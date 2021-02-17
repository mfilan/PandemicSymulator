package entity;

public class Product {
    private int ID;
    private String name;
    private String brand;
    private long beforeDate;
    private double price;
    private boolean onSale = false;


    Product(int ID, String name, String brand, long date, double price) {
        this.ID = ID;
        this.name = name;
        this.brand = brand;
        this.beforeDate = date;
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double p) {
        this.price = p;
    }

    public long checkDate() {
        return beforeDate;
    }

    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public void onSale() {
        onSale = true;
    }

    public boolean getOnSale() {
        return onSale;
    }

    public String getBrand() {
        return brand;
    }

    public long getBeforeDate() {
        return beforeDate;
    }
}
