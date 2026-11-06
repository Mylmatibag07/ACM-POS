public class BulalakSinigang extends Sinigang {
    public BulalakSinigang(double price, int stock) {
        super("Chicharon Bulalak Sinigang", price, stock);
    }

    public BulalakSinigang(double price) {
        super("Chicharon Bulalak Sinigang", price);
    }

    @Override
    public String getDescription() {
        return "Chicharon bulalak in sour tamarind broth";
    }
}