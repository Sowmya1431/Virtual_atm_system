import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * ChangePIN class represents a dialog for changing PIN.
 */
public class ChangePIN extends JDialog {

    private JPasswordField currentPinField, newPinField;
    private JButton changeButton, cancelButton;
    private List<UserData> userDataList;
    private String cardNum;
    private Validate v;

    /**
     * Constructor to initialize the Change PIN dialog.
     *
     * @param owner       The owner frame of the dialog.
     * @param cardNum     The card number of the user.
     * @param userDataList The list of user data.
     */
    public ChangePIN(Frame owner, String cardNum, List<UserData> userDataList) {
        super(owner, "Change PIN - Apna Bank", true);
        setIconImage(new ImageIcon("src/icon.png").getImage());
        setSize(480, 360);
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        this.cardNum = cardNum;
        this.userDataList = userDataList;
        v = new Validate();

        // Header
        add(ATMStyle.createHeaderPanel("Change PIN"), BorderLayout.NORTH);

        // Content panel
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(ATMStyle.BACKGROUND);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        // Card panel for inputs
        JPanel cardPanel = ATMStyle.createCardPanel();
        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));
        cardPanel.setMaximumSize(new Dimension(380, 160));
        cardPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Current PIN
        JPanel currentPanel = new JPanel(new BorderLayout(5, 8));
        currentPanel.setBackground(ATMStyle.CARD_BG);
        JLabel currentLabel = new JLabel("Current PIN");
        currentLabel.setFont(ATMStyle.FONT_LABEL);
        currentLabel.setForeground(ATMStyle.TEXT_PRIMARY);
        currentPinField = new JPasswordField();
        ATMStyle.stylePasswordField(currentPinField);
        currentPinField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        currentPanel.add(currentLabel, BorderLayout.NORTH);
        currentPanel.add(currentPinField, BorderLayout.CENTER);

        // New PIN
        JPanel newPanel = new JPanel(new BorderLayout(5, 8));
        newPanel.setBackground(ATMStyle.CARD_BG);
        newPanel.setBorder(BorderFactory.createEmptyBorder(16, 0, 0, 0));
        JLabel newLabel = new JLabel("New PIN (6 digits)");
        newLabel.setFont(ATMStyle.FONT_LABEL);
        newLabel.setForeground(ATMStyle.TEXT_PRIMARY);
        newPinField = new JPasswordField();
        ATMStyle.stylePasswordField(newPinField);
        newPinField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        newPanel.add(newLabel, BorderLayout.NORTH);
        newPanel.add(newPinField, BorderLayout.CENTER);

        cardPanel.add(currentPanel);
        cardPanel.add(newPanel);

        // Buttons panel
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonsPanel.setBackground(ATMStyle.BACKGROUND);
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(24, 0, 0, 0));

        changeButton = new JButton("Change PIN");
        ATMStyle.styleSuccessButton(changeButton);
        cancelButton = new JButton("Cancel");
        ATMStyle.styleDangerButton(cancelButton);

        buttonsPanel.add(changeButton);
        buttonsPanel.add(cancelButton);

        contentPanel.add(cardPanel);
        contentPanel.add(Box.createVerticalGlue());
        contentPanel.add(buttonsPanel);

        add(contentPanel, BorderLayout.CENTER);

        // Action listener for the change button
        changeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String currentPin = new String(currentPinField.getPassword());
                String storedPin = UserData.getData(cardNum, 2, userDataList);

                if (currentPin.equals(storedPin)) { // Check if current PIN matches the stored PIN
                    String newPin = new String(newPinField.getPassword());

                    if (v.isPin(newPin)) { // Check if the new PIN is valid
                        UserData.setData(cardNum, 2, newPin, userDataList); // Update PIN in the userDataList
                        updatePinInFile(cardNum, newPin); // Update PIN in the user_data.txt file
                        JOptionPane.showMessageDialog(null, "PIN changed successfully.");

                        // Email notification disabled
                    } else {
                        JOptionPane.showMessageDialog(null, "Please enter a valid 6-digit PIN.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Incorrect current PIN. Please try again.");
                }
            }
        });

        // Action listener for the cancel button
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    /**
     * Method to update PIN in the userDataList.
     *
     * @param cardNum The card number associated with the PIN.
     * @param newPin  The new PIN to be updated.
     */
    private void updatePinInFile(String cardNum, String newPin) {
        for (int i = 0; i < userDataList.size(); i++) {
            if (userDataList.get(i).getCardNumber().equals(cardNum)) {
                userDataList.get(i).setPin(newPin); // Update the pin in the userDataList
                break;
            }
        }
    }
}
