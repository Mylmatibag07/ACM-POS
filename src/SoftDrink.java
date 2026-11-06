public class SoftDrink extends Drink {
    private String flavor;

    public SoftDrink(String flavor, double price, int stock) {
        super("Soft Drink - " + flavor, price, stock, "330ml");
        this.flavor = flavor;
    }

    public String getFlavor() { return flavor; }
}