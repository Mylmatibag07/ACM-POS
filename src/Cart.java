import java.util.ArrayList;

public class Cart {
    private ArrayList<CartItem> items = new ArrayList<>();

    // Overloaded: adds 1 item
    public boolean add(Product p) {
        return add(p, 1);
    }

    // Overloaded: adds a given quantity
    public boolean add(Product p, int qty) {
        CartItem existing = null;
        for (CartItem item : items) {
            if (item.getProduct() == p) existing = item;
        }
        int inCart = (existing == null) ? 0 : existing.getQuantity();

        if (inCart + qty > p.getStock()) return false; // not enough stock

        if (existing == null) items.add(new CartItem(p, qty));
        else existing.setQuantity(inCart + qty);
        return true;
    }

    public void remove(int index) { items.remove(index); }
    public void clear() { items.clear(); }
    public boolean isEmpty() { return items.isEmpty(); }
    public ArrayList<CartItem> getItems() { return items; }

    public double getTotal() {
        double total = 0;
        for (CartItem item : items) total += item.getSubtotal();
        return total;
    }
}