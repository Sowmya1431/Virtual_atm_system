import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Withdrawal class represents a dialog for withdrawing money from an account.
 */
public class Withdrawal extends JDialog {

    private JTextField textField;
    private JButton withdrawButton, cancelButton;
    private List<UserData> userDataList;
    private String cardNum;
    private Validate v;
    private String accNum;

    /**
     * Constructor to initialize the Withdrawal dialog.
     *
     * @param owner        The parent frame.
     * @param cardNum      The card number for the account.
     * @param userDataList The list of user data.
     */
    public Withdrawal(Frame owner, String cardNum, List<UserData> userDataList) {
        super(owner, "Withdrawal - Apna Bank", true);
        setIconImage(new ImageIcon("src/icon.png").getImage());
        setSize(480, 360);
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        this.cardNum = cardNum;
        this.userDataList = userDataList;
        v = new Validate();

        // Fetch account number from userDataList
        for (UserData userData : userDataList) {
            if (userData.getCardNumber().equals(cardNum)) {
                accNum = userData.getAccNum();
                break;
            }
        }

        // Header
        add(ATMStyle.createHeaderPanel("Withdraw Money"), BorderLayout.NORTH);

        // Content panel
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(ATMStyle.BACKGROUND);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(25, 50, 25, 50));

        // Info label
        JLabel infoLabel = new JLabel("⚠ Max: ₹10,000 | Min Balance: ₹1,000");
        infoLabel.setFont(ATMStyle.FONT_SMALL);
        infoLabel.setForeground(ATMStyle.WARNING);
        infoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Amount input panel
        JPanel inputPanel = ATMStyle.createCardPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        inputPanel.setMaximumSize(new Dimension(380, 110));
        inputPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel amountLabel = new JLabel("Enter Amount to Withdraw (₹)");
        amountLabel.setFont(ATMStyle.FONT_LABEL);
        amountLabel.setForeground(ATMStyle.TEXT_PRIMARY);
        amountLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        textField = new JTextField();
        ATMStyle.styleTextField(textField);
        textField.setFont(ATMStyle.FONT_LARGE_AMOUNT);
        textField.setHorizontalAlignment(JTextField.CENTER);
        textField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

        inputPanel.add(amountLabel);
        inputPanel.add(Box.createVerticalStrut(10));
        inputPanel.add(textField);

        // Buttons panel
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonsPanel.setBackground(ATMStyle.BACKGROUND);
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        withdrawButton = new JButton("Withdraw");
        ATMStyle.styleButton(withdrawButton);
        cancelButton = new JButton("Cancel");
        ATMStyle.styleDangerButton(cancelButton);

        buttonsPanel.add(withdrawButton);
        buttonsPanel.add(cancelButton);

        contentPanel.add(infoLabel);
        contentPanel.add(Box.createVerticalStrut(15));
        contentPanel.add(inputPanel);
        contentPanel.add(Box.createVerticalGlue());
        contentPanel.add(buttonsPanel);

        add(contentPanel, BorderLayout.CENTER);

        withdrawButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (v.isAmount(textField.getText())) {
                    double oldBalance = Double.parseDouble(UserData.getData(cardNum, 5, userDataList));
                    double withdrawalAmount = Double.parseDouble(textField.getText());

                    // Maximum withdrawal limit is 10,000₹
                    if (withdrawalAmount <= 10000) {
                        // Check if the withdrawal amount doesn't cause the balance to go below the start amount of 1000₹
                        if (oldBalance - withdrawalAmount >= 1000) {
                            double newBalance = oldBalance - withdrawalAmount;
                            UserData.setData(cardNum, 5, Double.toString(newBalance), userDataList); // Update balance

                            // Record the withdrawal in the file
                            FileHandler.recordTransaction(accNum, withdrawalAmount, "Withdrawal");

                            JOptionPane.showMessageDialog(null, "Please collect your  money...");

                            // Email notification disabled

                            dispose(); // Close the dialog after withdrawal
                        } else {
                            JOptionPane.showMessageDialog(null, "Your balance cannot go below 1000₹.");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Maximum withdrawal limit is 10,000₹.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please enter a valid amount.");
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }
}
