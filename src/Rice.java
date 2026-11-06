public class Rice extends Product {
    public Rice(double price, int stock) {
        super("Rice", price, stock);
    }

    @Override
    public String getCategory() { return "Side Dish"; }
}