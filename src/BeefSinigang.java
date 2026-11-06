public class BeefSinigang extends Sinigang {
    public BeefSinigang(double price, int stock) {
        super("Beef Sinigang", price, stock);
    }

    public BeefSinigang(double price) {
        super("Beef Sinigang", price);
    }

    @Override
    public String getDescription() {
        return "Tender beef in sour tamarind broth";
    }
}