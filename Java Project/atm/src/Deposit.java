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
 * The Deposit class represents the dialog window for depositing money into the account.
 */
public class Deposit extends JDialog {

    // Components
    private JTextField textField;
    private JButton depositButton, cancelButton;

    // Data
    private List<UserData> userDataList;
    private String cardNum;
    private Validate v;
    private String accNum;

    /**
     * Constructor for Deposit class.
     *
     * @param owner       The owner frame of the dialog.
     * @param cardNum     The card number of the user.
     * @param userDataList The list of user data.
     */
    public Deposit(Frame owner, String cardNum, List<UserData> userDataList) {
        super(owner, "Deposit - Apna Bank", true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setIconImage(new ImageIcon("src/icon.png").getImage());
        setSize(480, 340);
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        this.cardNum = cardNum;
        this.userDataList = userDataList;
        v = new Validate();

        // Header
        add(ATMStyle.createHeaderPanel("Deposit Money"), BorderLayout.NORTH);

        // Content panel
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(ATMStyle.BACKGROUND);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        // Amount input panel
        JPanel inputPanel = ATMStyle.createCardPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        inputPanel.setMaximumSize(new Dimension(380, 110));
        inputPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel amountLabel = new JLabel("Enter Amount to Deposit (₹)");
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

        depositButton = new JButton("Deposit");
        ATMStyle.styleSuccessButton(depositButton);
        cancelButton = new JButton("Cancel");
        ATMStyle.styleDangerButton(cancelButton);

        buttonsPanel.add(depositButton);
        buttonsPanel.add(cancelButton);

        contentPanel.add(inputPanel);
        contentPanel.add(Box.createVerticalGlue());
        contentPanel.add(buttonsPanel);

        add(contentPanel, BorderLayout.CENTER);

        // Action listeners
        depositButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                accNum = UserData.getData(cardNum, 15, userDataList); // Initialize accNum here
                if (v.isAmount(textField.getText())) {
                    double oldBalance = Double.parseDouble(UserData.getData(cardNum, 5, userDataList));
                    double depositAmount = Double.parseDouble(textField.getText());
                    double newBalance = oldBalance + depositAmount;
                    UserData.setData(cardNum, 5, Double.toString(newBalance), userDataList); // Update balance

                    // Record the deposit in the file
                    FileHandler.recordTransaction(accNum, depositAmount, "Deposit");

                    JOptionPane.showMessageDialog(null, textField.getText() + " ₹ deposited successfully into your account.");

                    // Email notification disabled

                    dispose(); // Dispose dialog after depositing
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
