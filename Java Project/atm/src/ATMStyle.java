import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

/**
 * ATMStyle - Utility class for consistent ATM-like UI styling
 */
public class ATMStyle {
    // Colors - Modern ATM Theme
    public static final Color PRIMARY_DARK = new Color(10, 63, 117);      // Deep Blue
    public static final Color PRIMARY = new Color(25, 118, 210);          // Professional Blue
    public static final Color PRIMARY_LIGHT = new Color(66, 165, 245);    // Light Blue
    public static final Color ACCENT = new Color(76, 175, 80);            // Modern Green
    public static final Color ACCENT_SECONDARY = new Color(255, 152, 0);  // Orange
    public static final Color ACCENT_GOLD = new Color(255, 193, 7);       // Modern Gold
    public static final Color DANGER = new Color(229, 57, 53);            // Modern Red
    public static final Color WARNING = new Color(251, 140, 0);           // Orange warning
    public static final Color BACKGROUND = new Color(245, 247, 250);      // Modern light background
    public static final Color CARD_BG = new Color(255, 255, 255);         // White card background
    public static final Color TEXT_PRIMARY = new Color(25, 32, 45);       // Dark text
    public static final Color TEXT_SECONDARY = new Color(130, 145, 165);  // Gray text
    public static final Color BORDER_COLOR = new Color(225, 230, 240);    // Light border
    public static final Color SHADOW_COLOR = new Color(0, 0, 0, 15);      // Subtle shadow

    // Fonts
    public static final Font FONT_TITLE = new Font("Segoe UI", Font.BOLD, 26);
    public static final Font FONT_SUBTITLE = new Font("Segoe UI", Font.BOLD, 20);
    public static final Font FONT_BUTTON = new Font("Segoe UI", Font.BOLD, 15);
    public static final Font FONT_LABEL = new Font("Segoe UI", Font.PLAIN, 14);
    public static final Font FONT_INPUT = new Font("Segoe UI", Font.PLAIN, 15);
    public static final Font FONT_SMALL = new Font("Segoe UI", Font.PLAIN, 12);
    public static final Font FONT_LARGE_AMOUNT = new Font("Segoe UI", Font.BOLD, 28);

    /**
     * Style a primary action button (e.g., Submit, Deposit) - with rounded effect
     */
    public static void styleButton(JButton button) {
        button.setFont(FONT_BUTTON);
        button.setBackground(PRIMARY);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createEmptyBorder(14, 28, 14, 28));
        button.setMargin(new Insets(5, 15, 5, 15));
    }

    /**
     * Style a success button (e.g., Confirm, Deposit)
     */
    public static void styleSuccessButton(JButton button) {
        styleButton(button);
        button.setBackground(ACCENT);
    }

    /**
     * Style a cancel/danger button
     */
    public static void styleDangerButton(JButton button) {
        styleButton(button);
        button.setBackground(DANGER);
    }

    /**
     * Style a secondary button
     */
    public static void styleSecondaryButton(JButton button) {
        styleButton(button);
        button.setBackground(new Color(108, 117, 125));
    }

    /**
     * Style a gold/accent button for money operations
     */
    public static void styleGoldButton(JButton button) {
        styleButton(button);
        button.setBackground(ACCENT_GOLD);
        button.setForeground(TEXT_PRIMARY);
    }

    /**
     * Style a text field with better appearance
     */
    public static void styleTextField(JTextField field) {
        field.setFont(FONT_INPUT);
        field.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(BORDER_COLOR, 2, true),
            BorderFactory.createEmptyBorder(12, 16, 12, 16)
        ));
        field.setBackground(Color.WHITE);
        field.setCaretColor(PRIMARY);
    }

    /**
     * Style a password field with better appearance
     */
    public static void stylePasswordField(JPasswordField field) {
        field.setFont(FONT_INPUT);
        field.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(BORDER_COLOR, 2, true),
            BorderFactory.createEmptyBorder(12, 16, 12, 16)
        ));
        field.setBackground(Color.WHITE);
        field.setCaretColor(PRIMARY);
    }

    /**
     * Style a label
     */
    public static void styleLabel(JLabel label) {
        label.setFont(FONT_LABEL);
        label.setForeground(TEXT_PRIMARY);
    }

    /**
     * Style a title label
     */
    public static void styleTitleLabel(JLabel label) {
        label.setFont(FONT_TITLE);
        label.setForeground(Color.WHITE);
    }

    /**
     * Style a subtitle label
     */
    public static void styleSubtitleLabel(JLabel label) {
        label.setFont(FONT_SUBTITLE);
        label.setForeground(TEXT_PRIMARY);
    }

    /**
     * Create a styled panel with card-like appearance
     */
    public static JPanel createCardPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(CARD_BG);
        panel.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(BORDER_COLOR, 1, true),
            BorderFactory.createEmptyBorder(22, 22, 22, 22)
        ));
        return panel;
    }

    /**
     * Create a header panel with ATM branding
     */
    public static JPanel createHeaderPanel(String title) {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(PRIMARY_DARK);
        header.setBorder(BorderFactory.createEmptyBorder(24, 32, 24, 32));
        
        JLabel bankLabel = new JLabel("🏦 APNA BANK", SwingConstants.LEFT);
        bankLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        bankLabel.setForeground(ACCENT_GOLD);
        
        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(FONT_TITLE);
        titleLabel.setForeground(Color.WHITE);
        
        JLabel timeLabel = new JLabel("🔒 Secure Banking", SwingConstants.RIGHT);
        timeLabel.setFont(FONT_SMALL);
        timeLabel.setForeground(new Color(210, 210, 210));
        
        header.add(bankLabel, BorderLayout.WEST);
        header.add(titleLabel, BorderLayout.CENTER);
        header.add(timeLabel, BorderLayout.EAST);
        
        return header;
    }

    /**
     * Create a footer panel
     */
    public static JPanel createFooterPanel() {
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.CENTER));
        footer.setBackground(PRIMARY_DARK);
        footer.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel footerText = new JLabel("© 2026 Apna Bank | Secure Banking");
        footerText.setFont(FONT_SMALL);
        footerText.setForeground(new Color(180, 180, 180));
        footer.add(footerText);
        
        return footer;
    }

    /**
     * Style a frame/dialog with ATM background
     */
    public static void styleFrame(Container container) {
        container.setBackground(BACKGROUND);
    }

    /**
     * Create a styled menu button for the main dashboard
     */
    public static JButton createMenuButton(String text, String emoji) {
        JButton button = new JButton("<html><center>" + emoji + "<br/><b>" + text + "</b></center></html>");
        button.setFont(new Font("Segoe UI", Font.BOLD, 13));
        button.setBackground(CARD_BG);
        button.setForeground(TEXT_PRIMARY);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(BORDER_COLOR, 1, true),
            BorderFactory.createEmptyBorder(18, 14, 18, 14)
        ));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(180, 110));
        button.setMaximumSize(new Dimension(180, 110));
        return button;
    }

    /**
     * Create a money amount button for fast withdrawal
     */
    public static JButton createAmountButton(String amount) {
        JButton button = new JButton(amount);
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setBackground(new Color(55, 71, 96));
        button.setForeground(ACCENT_GOLD);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(ACCENT_GOLD, 2, true),
            BorderFactory.createEmptyBorder(16, 22, 16, 22)
        ));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }
}
