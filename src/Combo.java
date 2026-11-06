public class Combo extends Product {
    private double discountRate; // e.g., 0.10 = 10% off

    public Combo(String name, double price, int stock, double discountRate) {
        super(name, price, stock);
        this.discountRate = discountRate;
    }

    @Override
    public String getCategory() { return "Combo"; }

    // Polymorphism: different pricing behavior
    @Override
    public double getFinalPrice() {
        return super.getFinalPrice() * (1 - discountRate);
    }
}