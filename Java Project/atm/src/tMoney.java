import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

class tMoney extends JDialog {
    private JTextField accNo, moneyT;
    private JButton transButton, cancelButton;
    private List<UserData> userDataList;
    private String cardNum;
    private Validate v;

    /**
     * Constructor to initialize the Transfer Money dialog.
     *
     * @param owner        The owner frame of the dialog.
     * @param cardNum      The card number of the user.
     * @param userDataList The list of user data.
     */
    public tMoney(Frame owner, String cardNum, List<UserData> userDataList) {
        super(owner, "Transfer Money - Apna Bank", true);
        setIconImage(new ImageIcon("src/icon.png").getImage());
        setSize(450, 350);
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        this.cardNum = cardNum;
        this.userDataList = userDataList;
        v = new Validate();

        // Header
        add(ATMStyle.createHeaderPanel("Transfer"), BorderLayout.NORTH);

        // Content panel
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(ATMStyle.BACKGROUND);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        // Card panel for inputs
        JPanel cardPanel = ATMStyle.createCardPanel();
        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));
        cardPanel.setMaximumSize(new Dimension(370, 160));

        // Receiver Account
        JPanel accPanel = new JPanel(new BorderLayout(5, 5));
        accPanel.setBackground(ATMStyle.CARD_BG);
        JLabel accLabel = new JLabel("Receiver's Account Number (16 digits)");
        ATMStyle.styleLabel(accLabel);
        accNo = new JTextField();
        ATMStyle.styleTextField(accNo);
        accPanel.add(accLabel, BorderLayout.NORTH);
        accPanel.add(accNo, BorderLayout.CENTER);

        // Amount
        JPanel amountPanel = new JPanel(new BorderLayout(5, 5));
        amountPanel.setBackground(ATMStyle.CARD_BG);
        amountPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));
        JLabel amountLabel = new JLabel("Amount to Transfer (₹)");
        ATMStyle.styleLabel(amountLabel);
        moneyT = new JTextField();
        ATMStyle.styleTextField(moneyT);
        moneyT.setFont(new Font("Segoe UI", Font.BOLD, 16));
        amountPanel.add(amountLabel, BorderLayout.NORTH);
        amountPanel.add(moneyT, BorderLayout.CENTER);

        cardPanel.add(accPanel);
        cardPanel.add(amountPanel);

        // Buttons panel
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonsPanel.setBackground(ATMStyle.BACKGROUND);
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        transButton = new JButton("Transfer");
        ATMStyle.styleSuccessButton(transButton);
        cancelButton = new JButton("Cancel");
        ATMStyle.styleDangerButton(cancelButton);

        buttonsPanel.add(transButton);
        buttonsPanel.add(cancelButton);

        contentPanel.add(cardPanel);
        contentPanel.add(buttonsPanel);

        add(contentPanel, BorderLayout.CENTER);

        // Action listener for the change button
        transButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!v.isAccountNumber(accNo.getText()) && UserData.exitsAccNo(accNo.getText())) { // Fixed closing parenthesis
                    JOptionPane.showMessageDialog(null, "Please enter valid account number..");
                }
                if (!v.isAmount(moneyT.getText())) {
                    JOptionPane.showMessageDialog(null, "Please enter valid money number..");
                }
                double myOldBal = Double.parseDouble(UserData.getData(cardNum, 5, userDataList));
                double reqAmount = Double.parseDouble(moneyT.getText());
                String rCard = UserData.getData(accNo.getText(), userDataList);
                double reOldBal = Double.parseDouble(UserData.getData(rCard, 5, userDataList));
                double d = myOldBal - reqAmount;
                if (1000 >= d) {
                    JOptionPane.showMessageDialog(null, "You must contain at least 1000 rupees in your account..");
                } else {
                    double myNewBal = myOldBal - reqAmount;
                    double reNewBal = reOldBal + reqAmount;
                    UserData.setData(cardNum, 5, Double.toString(myNewBal), userDataList);
                    UserData.setData(rCard, 5, Double.toString(reNewBal), userDataList);
                    recordTransaction(UserData.getData(cardNum, 15, userDataList),reqAmount,accNo.getText(),reqAmount);
                    // Email notifications disabled
                    JOptionPane.showMessageDialog(null,"Money Transfer Successful");
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

    private void recordTransaction(String accNumS,double amountS,String accNumR,double amountR ) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("records.txt", true))) {
            writer.write("Account Number: " + accNumS + "  TO  " + accNumR +"\n");
            writer.write(  "Transfer" +" Time: " + java.time.LocalDateTime.now() + "\n");
            writer.write( "Transferred"+" Amount: " + amountS + " ₹\n\n");
            writer.write("Account Number: " + accNumR + "  FROM  " + accNumS +"\n");
            writer.write(  "Received" +" Time: " + java.time.LocalDateTime.now() + "\n");
            writer.write( "Received"+" Amount: " + amountR + " ₹\n\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
