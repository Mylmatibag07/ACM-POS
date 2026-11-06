public class Drink extends Product {
    private String size;

    public Drink(String name, double price, int stock, String size) {
        super(name, price, stock);
        this.size = size;
    }

    public String getSize() { return size; }

    @Override
    public String getCategory() { return "Drink"; }
}