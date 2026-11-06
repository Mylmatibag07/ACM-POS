public abstract class Product implements Receiptable {
    private String name;
    private double price;
    private int stock;
    private String imagePath = "";

    // Constructor
    public Product(String name, double price, int stock) {
        this.name = name;
        setPrice(price);
        setStock(stock);
    }

    // Overloaded constructor (default stock of 100)
    public Product(String name, double price) {
        this(name, price, 100);
    }

    // Encapsulation: getters and validated setters
    public String getName() { return name; }
    public double getPrice() { return price; }
    public int getStock() { return stock; }
    public String getImagePath() { return imagePath; }

    public void setPrice(double price) {
        if (price >= 0) this.price = price;
    }

    public void setStock(int stock) {
        if (stock >= 0) this.stock = stock;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    // Abstract method: every subclass must define it
    public abstract String getCategory();

    // Subclasses may override this (polymorphism)
    public double getFinalPrice() {
        return price;
    }

    @Override
    public String toReceiptLine() {
        return String.format("%-18s %8.2f", name, getFinalPrice());
    }
}