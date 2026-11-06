public class CartItem implements Receiptable {
    private Product product;
    private int quantity;

    public CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() { return product; }
    public int getQuantity() { return quantity; }

    public void setQuantity(int quantity) {
        if (quantity > 0) this.quantity = quantity;
    }

    // Calls the correct getFinalPrice() for each product type
    public double getSubtotal() {
        return product.getFinalPrice() * quantity;
    }

    @Override
    public String toReceiptLine() {
        return String.format("%-18s x%-3d %8.2f", product.getName(), quantity, getSubtotal());
    }
}