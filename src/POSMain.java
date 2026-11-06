import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.swing.*;

public class POSMain extends JFrame {

    private static final Color ORANGE = new Color(232, 119, 34);
    private static final Color DARK = new Color(30, 30, 30);
    private static final Color RED = new Color(191, 87, 0); // deep orange

    private final ArrayList<Product> products = new ArrayList<>();
    private final Cart cart = new Cart();

    private final JPanel pnlProducts = new JPanel(new GridLayout(0, 3, 10, 10));
    private final JPanel pnlOrder = new JPanel();
    private final JLabel lblTotal = new JLabel("₱0.00");
    private final JTextArea txtReceipt = new JTextArea();

    private int queueNo = 1;
    private int orderNo = 1001;

    public POSMain() {
        setTitle("ACM Kalye Sinigang - Kiosk POS");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1280, 720);
        setMinimumSize(new Dimension(1100, 650));
        setLocationRelativeTo(null);

        JPanel root = new JPanel(new GridBagLayout());
        root.setBackground(ORANGE);
        root.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        setContentPane(root);

        // Menu items (prices are placeholders: change them)
        addProduct(new BeefSinigang(180.00, 50), "/Image/beef.jpg");
        addProduct(new BagnetSinigang(200.00, 50), "/Image/bagnet.jpg");
        addProduct(new BulalakSinigang(220.00, 50), "/Image/bulalak.jpg");
        addProduct(new Rice(20.00, 200), "/Image/rice.jpg");
        addProduct(new SoftDrink("Coke", 35.00, 100), "/Image/softdrink.jpg");
        addProduct(new Water(20.00, 100), "/Image/water.jpg");

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1;
        gbc.insets = new Insets(0, 6, 0, 6);

        gbc.gridx = 0;
        gbc.weightx = 0.44;
        root.add(buildMenuPanel(), gbc);
        gbc.gridx = 1;
        gbc.weightx = 0.28;
        root.add(buildOrderPanel(), gbc);
        gbc.gridx = 2;
        gbc.weightx = 0.28;
        root.add(buildDetailsPanel(), gbc);

        buildProductCards();
        refreshCart();
    }

    // ----- Overloaded methods -----
    private void addProduct(Product p) {
        products.add(p);
    }

    private void addProduct(Product p, String imagePath) {
        p.setImagePath(imagePath);
        products.add(p);
    }

    private void buildProductCards() {
        buildProductCards("All");
    }

    private void buildProductCards(String category) {
        pnlProducts.removeAll();
        for (Product p : products) {
            if (category.equals("All") || p.getCategory().equals(category)) {
                pnlProducts.add(new ProductCard(p, () -> addToCart(p)));
            }
        }
        pnlProducts.revalidate();
        pnlProducts.repaint();
    }

    private String buildReceipt() {
        StringBuilder r = new StringBuilder();
        r.append("   ACM KALYE SINIGANG\n");
        r.append("      Ubod ng ACM!\n");
        r.append(line());
        r.append("Date  : ").append(LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).append("\n");
        r.append(String.format("Queue : %03d%n", queueNo));
        r.append(String.format("Order : %d%n", orderNo));
        r.append(line());
        r.append(String.format("%-15s %3s %9s%n", "ITEM", "QTY", "AMOUNT"));
        for (CartItem item : cart.getItems()) {
            r.append(String.format("%-15.15s %3d %9.2f%n",
                    item.getProduct().getName(), item.getQuantity(), item.getSubtotal()));
        }
        r.append(line());
        r.append(String.format("%-19s %9.2f%n", "TOTAL:", cart.getTotal()));
        return r.toString();
    }

    private String buildReceipt(double paid) {
        return buildReceipt()
                + String.format("%-19s %9.2f%n", "PAID:", paid)
                + String.format("%-19s %9.2f%n", "CHANGE:", paid - cart.getTotal())
                + "\n   Thank you! Come again!\n";
    }

    private String line() {
        return "-----------------------------\n";
    }

    // ----- Panels -----
    private JPanel buildMenuPanel() {
        JPanel panel = whitePanel(new BorderLayout(0, 10));

        JLabel logo = new JLabel("ACM Kalye Sinigang");
        logo.setFont(new Font("Segoe UI", Font.BOLD, 26));
        logo.setForeground(ORANGE);

        JPanel top = new JPanel(new BorderLayout(0, 8));
        top.setOpaque(false);
        top.add(logo, BorderLayout.NORTH);
        top.add(buildFilterBar(), BorderLayout.CENTER);
        panel.add(top, BorderLayout.NORTH);

        pnlProducts.setBackground(Color.WHITE);
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(Color.WHITE);
        wrapper.add(pnlProducts, BorderLayout.NORTH);

        JScrollPane scroll = new JScrollPane(wrapper);
        scroll.setBorder(BorderFactory.createLineBorder(DARK, 2));
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        panel.add(scroll, BorderLayout.CENTER);

        panel.setPreferredSize(new Dimension(100, 100));
        return panel;
    }

    private JPanel buildFilterBar() {
        JPanel bar = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 0));
        bar.setOpaque(false);
        String[] categories = {"All", "Sinigang", "Side Dish", "Drink"};
        for (String c : categories) {
            JButton b = makeButton(c, DARK, Color.WHITE);
            b.addActionListener(e -> buildProductCards(c));
            bar.add(b);
        }
        return bar;
    }

    private JPanel buildOrderPanel() {
        JPanel panel = whitePanel(new BorderLayout(0, 10));

        JLabel title = new JLabel("Current Order");
        title.setFont(new Font("Segoe UI", Font.BOLD, 16));
        JButton btnClearAll = makeButton("CLEAR ALL", RED, Color.WHITE);
        btnClearAll.addActionListener(e -> {
            cart.clear();
            refreshCart();
        });

        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        header.add(title, BorderLayout.WEST);
        header.add(btnClearAll, BorderLayout.EAST);
        panel.add(header, BorderLayout.NORTH);

        pnlOrder.setLayout(new BoxLayout(pnlOrder, BoxLayout.Y_AXIS));
        pnlOrder.setBackground(Color.WHITE);
        JScrollPane scroll = new JScrollPane(pnlOrder);
        scroll.setBorder(null);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        panel.add(scroll, BorderLayout.CENTER);

        JLabel totalText = new JLabel("Total:");
        totalText.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTotal.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTotal.setForeground(ORANGE);

        JPanel totalRow = new JPanel(new BorderLayout());
        totalRow.setOpaque(false);
        totalRow.add(totalText, BorderLayout.WEST);
        totalRow.add(lblTotal, BorderLayout.EAST);

        JButton btnPlace = makeButton("PLACE ORDER", DARK, Color.WHITE);
        btnPlace.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnPlace.setPreferredSize(new Dimension(0, 46));
        btnPlace.addActionListener(e -> placeOrder());

        JPanel bottom = new JPanel(new BorderLayout(0, 10));
        bottom.setOpaque(false);
        bottom.add(totalRow, BorderLayout.NORTH);
        bottom.add(btnPlace, BorderLayout.SOUTH);
        panel.add(bottom, BorderLayout.SOUTH);

        panel.setPreferredSize(new Dimension(100, 100));
        return panel;
    }

    private JPanel buildDetailsPanel() {
        JPanel panel = whitePanel(new BorderLayout(0, 10));

        JLabel title = new JLabel("Order Details (Proceed to Cashier)");
        title.setFont(new Font("Segoe UI", Font.BOLD, 16));
        panel.add(title, BorderLayout.NORTH);

        txtReceipt.setEditable(false);
        txtReceipt.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        txtReceipt.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        JScrollPane scroll = new JScrollPane(txtReceipt);
        scroll.setBorder(BorderFactory.createLineBorder(DARK, 1));
        panel.add(scroll, BorderLayout.CENTER);

        panel.setPreferredSize(new Dimension(100, 100));
        return panel;
    }

    private JPanel buildOrderRow(CartItem item) {
        JPanel row = new JPanel(new BorderLayout(8, 0));
        row.setBackground(Color.WHITE);
        row.setBorder(BorderFactory.createLineBorder(DARK, 1));
        row.setPreferredSize(new Dimension(0, 56));
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 56));

        JLabel qty = new JLabel(String.valueOf(item.getQuantity()), SwingConstants.CENTER);
        qty.setOpaque(true);
        qty.setBackground(ORANGE);
        qty.setForeground(Color.WHITE);
        qty.setFont(new Font("Segoe UI", Font.BOLD, 20));
        qty.setPreferredSize(new Dimension(54, 0));
        row.add(qty, BorderLayout.WEST);

        JLabel info = new JLabel("<html><b>" + item.getProduct().getName() + "</b><br>₱"
                + String.format("%.2f", item.getProduct().getFinalPrice()) + "</html>");
        info.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        row.add(info, BorderLayout.CENTER);

        JButton minus = makeButton("-", RED, Color.WHITE);
        minus.setPreferredSize(new Dimension(40, 28));
        minus.addActionListener(e -> {
            if (item.getQuantity() > 1) {
                item.setQuantity(item.getQuantity() - 1);
            } else {
                cart.getItems().remove(item);
            }
            refreshCart();
        });
        JPanel east = new JPanel(new GridBagLayout());
        east.setOpaque(false);
        east.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 8));
        east.add(minus);
        row.add(east, BorderLayout.EAST);

        return row;
    }

    // ----- Helpers -----
    private JPanel whitePanel(LayoutManager layout) {
        JPanel p = new JPanel(layout);
        p.setBackground(Color.WHITE);
        p.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        return p;
    }

    private JButton makeButton(String text, Color bg, Color fg) {
        JButton b = new JButton(text);
        b.setBackground(bg);
        b.setForeground(fg);
        b.setFont(new Font("Segoe UI", Font.BOLD, 13));
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setContentAreaFilled(false);
        b.setOpaque(true);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return b;
    }

    // ----- Cart actions -----
    private void addToCart(Product p) {
        if (!cart.add(p)) {
            JOptionPane.showMessageDialog(this, "Sorry, not enough stock for " + p.getName() + ".");
            return;
        }
        refreshCart();
    }

    private void renderCart() {
        pnlOrder.removeAll();
        for (CartItem item : cart.getItems()) {
            pnlOrder.add(buildOrderRow(item));
            pnlOrder.add(Box.createVerticalStrut(6));
        }
        pnlOrder.add(Box.createVerticalGlue());
        lblTotal.setText("₱" + String.format("%.2f", cart.getTotal()));
        pnlOrder.revalidate();
        pnlOrder.repaint();
    }

    private void refreshCart() {
        renderCart();
        txtReceipt.setText(buildReceipt());
        txtReceipt.setCaretPosition(0);
    }

    private void placeOrder() {
        if (cart.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Your order is empty.");
            return;
        }

        double total = cart.getTotal();
        String input = JOptionPane.showInputDialog(this,
                String.format("Total: P%.2f%nEnter amount paid:", total),
                "Payment", JOptionPane.QUESTION_MESSAGE);
        if (input == null) return; // cancelled

        double paid;
        try {
            paid = Double.parseDouble(input.trim());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Enter a valid amount.");
            return;
        }

        if (paid < total) {
            JOptionPane.showMessageDialog(this, "Insufficient payment.");
            return;
        }

        String finalReceipt = buildReceipt(paid);

        for (CartItem item : cart.getItems()) {
            Product p = item.getProduct();
            p.setStock(p.getStock() - item.getQuantity());
        }
        cart.clear();
        queueNo++;
        orderNo++;

        renderCart();
        txtReceipt.setText(finalReceipt);
        txtReceipt.setCaretPosition(0);
        JOptionPane.showMessageDialog(this,
                String.format("Order placed! Change: P%.2f", paid - total));
    }
}