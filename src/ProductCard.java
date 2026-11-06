import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

public class ProductCard extends JPanel {
    private static final Color DARK = new Color(30, 30, 30);
    private static final Color ORANGE = new Color(232, 119, 34);
    private static final Color HOVER_BG = new Color(255, 242, 228);

    private final Product product;
    private final JLabel lblImage = new JLabel("", SwingConstants.CENTER);

    // Overloaded constructor: default orange accent
    public ProductCard(Product product, Runnable onAdd) {
        this(product, ORANGE, onAdd);
    }

    public ProductCard(Product product, Color accent, Runnable onAdd) {
        this.product = product;

        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(150, 176));
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Picture
        lblImage.setOpaque(true);
        lblImage.setPreferredSize(new Dimension(150, 122));
        if (!product.getImagePath().isEmpty()) {
            java.net.URL url = getClass().getResource(product.getImagePath());
            if (url != null) {
                ImageIcon icon = new ImageIcon(url);
                int w = icon.getIconWidth();
                int h = icon.getIconHeight();
                if (w > 0 && h > 0) {
                    double scale = Math.min(140.0 / w, 112.0 / h);
                    Image img = icon.getImage().getScaledInstance(
                            (int) (w * scale), (int) (h * scale), Image.SCALE_SMOOTH);
                    lblImage.setIcon(new ImageIcon(img));
                }
            }
        }
        add(lblImage, BorderLayout.CENTER);

        // Name and price strip
        JLabel lblInfo = new JLabel("<html><body style='width:125px'><b>" + product.getName()
                + "</b><br>₱" + String.format("%.2f", product.getFinalPrice()) + "</body></html>");
        lblInfo.setOpaque(true);
        lblInfo.setBackground(accent);
        lblInfo.setForeground(Color.WHITE);
        lblInfo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblInfo.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
        lblInfo.setPreferredSize(new Dimension(150, 54));
        add(lblInfo, BorderLayout.SOUTH);

        setHover(false);

        // Hover + click on the card and on everything inside it
        MouseAdapter listener = new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setHover(true);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // Only un-hover if the pointer really left the whole card
                Point p = SwingUtilities.convertPoint(e.getComponent(), e.getPoint(), ProductCard.this);
                if (!ProductCard.this.contains(p)) {
                    setHover(false);
                }
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                onAdd.run();
            }
        };
        addMouseListener(listener);
        lblImage.addMouseListener(listener);
        lblInfo.addMouseListener(listener);
    }

    private void setHover(boolean hover) {
        Color bg = hover ? HOVER_BG : Color.WHITE;
        setBackground(bg);
        lblImage.setBackground(bg);
        if (hover) {
            setBorder(BorderFactory.createLineBorder(ORANGE, 3));
        } else {
            setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(DARK, 1),
                    BorderFactory.createEmptyBorder(2, 2, 2, 2)));
        }
    }

    public Product getProduct() { return product; }
}