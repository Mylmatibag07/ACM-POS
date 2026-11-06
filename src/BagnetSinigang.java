public class BagnetSinigang extends Sinigang {
    public BagnetSinigang(double price, int stock) {
        super("Pork Bagnet Sinigang", price, stock);
    }

    public BagnetSinigang(double price) {
        super("Pork Bagnet Sinigang", price);
    }

    @Override
    public String getDescription() {
        return "Crispy pork bagnet in sour tamarind broth";
    }
}