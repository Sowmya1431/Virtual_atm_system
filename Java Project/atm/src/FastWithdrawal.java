import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * FastWithdrawal class represents a dialog for fast withdrawal functionality.
 */
public class FastWithdrawal extends JDialog {

    // Buttons for withdrawal
    private JButton button100, button200, button500, button1000, button2000, button5000, button10000, cancelButton;

    // User data list and card number
    private List<UserData> userDataList;
    private String cardNum;

    // Validator
    private Validate v;

    /**
     * Constructor to initialize the Fast Withdrawal dialog.
     *
     * @param owner       The owner frame of the dialog.
     * @param cardNum     The card number of the user.
     * @param userDataList The list of user data.
     */
    public FastWithdrawal(Frame owner, String cardNum, List<UserData> userDataList) {
        super(owner, "Fast Withdrawal - Apna Bank", true);
        setResizable(false);
        setSize(520, 420);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setIconImage(new ImageIcon("src/icon.png").getImage());
        this.cardNum = cardNum;
        this.userDataList = userDataList;
        v = new Validate();
        initComponents();
    }

    // Initialize components
    private void initComponents() {
        // Header
        JPanel headerPanel = ATMStyle.createHeaderPanel("Fast Cash");
        add(headerPanel, BorderLayout.NORTH);

        // Info panel
        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        infoPanel.setBackground(ATMStyle.BACKGROUND);
        infoPanel.setBorder(BorderFactory.createEmptyBorder(18, 0, 8, 0));
        JLabel infoLabel = new JLabel("💰 Select Amount to Withdraw");
        infoLabel.setFont(ATMStyle.FONT_SUBTITLE);
        infoLabel.setForeground(ATMStyle.TEXT_PRIMARY);
        infoPanel.add(infoLabel);

        // Buttons panel with better layout
        JPanel buttonsPanel = new JPanel(new GridLayout(3, 3, 16, 16));
        buttonsPanel.setBackground(ATMStyle.BACKGROUND);
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(16, 32, 20, 32));

        // Initializing styled buttons with better sizing
        button100 = ATMStyle.createAmountButton("₹100");
        button200 = ATMStyle.createAmountButton("₹200");
        button500 = ATMStyle.createAmountButton("₹500");
        button1000 = ATMStyle.createAmountButton("₹1,000");
        button2000 = ATMStyle.createAmountButton("₹2,000");
        button5000 = ATMStyle.createAmountButton("₹5,000");
        button10000 = ATMStyle.createAmountButton("₹10,000");
        cancelButton = new JButton("Cancel");
        ATMStyle.styleDangerButton(cancelButton);

        // Adding buttons to the panel
        buttonsPanel.add(button100);
        buttonsPanel.add(button200);
        buttonsPanel.add(button500);
        buttonsPanel.add(button1000);
        buttonsPanel.add(button2000);
        buttonsPanel.add(button5000);
        buttonsPanel.add(button10000);
        buttonsPanel.add(new JLabel("")); // Empty space
        buttonsPanel.add(cancelButton);

        // Content panel
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(ATMStyle.BACKGROUND);
        contentPanel.add(infoPanel, BorderLayout.NORTH);
        contentPanel.add(buttonsPanel, BorderLayout.CENTER);

        add(contentPanel, BorderLayout.CENTER);

        // Action listeners for withdrawal buttons
        button100.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processWithdrawal(100); // Process withdrawal of ₹100
            }
        });

        button200.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processWithdrawal(200); // Process withdrawal of ₹200
            }
        });

        button500.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processWithdrawal(500); // Process withdrawal of ₹500
            }
        });

        button1000.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processWithdrawal(1000); // Process withdrawal of ₹1000
            }
        });

        button2000.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processWithdrawal(2000); // Process withdrawal of ₹2000
            }
        });

        button5000.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processWithdrawal(5000); // Process withdrawal of ₹5000
            }
        });

        button10000.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processWithdrawal(10000); // Process withdrawal of ₹10000
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close dialog when cancel button is clicked
            }
        });
    }

    // Method to process withdrawal
    private void processWithdrawal(double withdrawalAmount) {
        String accNum = "";
        for (UserData userData : userDataList) {
            if (userData.getCardNumber().equals(cardNum)) {
                accNum = userData.getAccNum(); // Get account number associated with the card
                break;
            }
        }

        double oldBalance = Double.parseDouble(UserData.getData(cardNum, 5, userDataList));
        double newBalance = oldBalance - withdrawalAmount;

        // Check if withdrawal amount is valid and balance is sufficient
        if (withdrawalAmount >= 100 && withdrawalAmount <= 10000 && newBalance >= 1000) {
            UserData.setData(cardNum, 5, Double.toString(newBalance), userDataList); // Update balance

            // Record the withdrawal in the file
            FileHandler.recordTransaction(accNum, withdrawalAmount, "Fast Withdrawal");

            JOptionPane.showMessageDialog(null, "Please collect your money...");

            // Email notification disabled
        } else {
            JOptionPane.showMessageDialog(null, "Withdrawal amount must be between 100₹ and 10,000₹, and your balance should remain above 1000₹.");
        }
    }
}
