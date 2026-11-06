public abstract class Sinigang extends Product {
    private String size;

    // Overloaded constructors
    public Sinigang(String name, double price, int stock, String size) {
        super(name, price, stock);
        this.size = size;
    }

    public Sinigang(String name, double price, int stock) {
        this(name, price, stock, "Regular");
    }

    public Sinigang(String name, double price) {
        this(name, price, 50);
    }

    public String getSize() { return size; }

    @Override
    public String getCategory() { return "Sinigang"; }

    // Each sinigang type describes itself differently
    public abstract String getDescription();

    // Overloaded methods
    public String getDescription(boolean showSize) {
        if (showSize) return getDescription() + " (" + size + ")";
        return getDescription();
    }
}