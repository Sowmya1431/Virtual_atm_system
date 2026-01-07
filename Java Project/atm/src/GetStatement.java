import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.time.LocalDate;
import java.util.List;

/**
 * GetStatement class represents a dialog for obtaining account statements.
 */
class GetStatement extends JDialog {

    private String cardNum;
    private List<UserData> userDataList;
    private JRadioButton emailOption, localOption;
    private JButton getStatementButton, cancelButton;
    private ButtonGroup optionGroup;
    private String accNum;

    /**
     * Constructor for GetStatement dialog.
     *
     * @param owner       The parent frame.
     * @param cardNum     Card number of the user.
     * @param userDataList List of user data.
     */
    public GetStatement(Frame owner, String cardNum, List<UserData> userDataList) {
        super(owner, "Get Statement - Apna Bank", true);
        setIconImage(new ImageIcon("src/icon.png").getImage());
        setSize(480, 320);
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        this.cardNum = cardNum;
        this.userDataList = userDataList;
        this.accNum = UserData.getData(cardNum, 15, userDataList);

        // Header
        add(ATMStyle.createHeaderPanel("Account Statement"), BorderLayout.NORTH);

        // Content panel
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(ATMStyle.BACKGROUND);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        // Card panel
        JPanel cardPanel = ATMStyle.createCardPanel();
        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));
        cardPanel.setMaximumSize(new Dimension(380, 120));
        cardPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel selectLabel = new JLabel("📄 Select delivery method:");
        selectLabel.setFont(ATMStyle.FONT_LABEL);
        selectLabel.setForeground(ATMStyle.TEXT_PRIMARY);
        selectLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel optionPanel = new JPanel();
        optionPanel.setLayout(new BoxLayout(optionPanel, BoxLayout.Y_AXIS));
        optionPanel.setBackground(ATMStyle.CARD_BG);
        optionPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        
        emailOption = new JRadioButton("📧 Send via Email (Disabled)");
        emailOption.setEnabled(false);
        emailOption.setFont(ATMStyle.FONT_LABEL);
        emailOption.setBackground(ATMStyle.CARD_BG);
        
        localOption = new JRadioButton("💾 Save Locally as Text");
        localOption.setFont(ATMStyle.FONT_LABEL);
        localOption.setBackground(ATMStyle.CARD_BG);
        localOption.setSelected(true);
        
        optionGroup = new ButtonGroup();
        optionGroup.add(emailOption);
        optionGroup.add(localOption);
        
        optionPanel.add(localOption);
        optionPanel.add(Box.createVerticalStrut(8));
        optionPanel.add(emailOption);

        cardPanel.add(selectLabel);
        cardPanel.add(Box.createVerticalStrut(10));
        cardPanel.add(optionPanel);

        // Buttons panel
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonsPanel.setBackground(ATMStyle.BACKGROUND);
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(24, 0, 0, 0));

        getStatementButton = new JButton("Get Statement");
        ATMStyle.styleSuccessButton(getStatementButton);
        cancelButton = new JButton("Cancel");
        ATMStyle.styleDangerButton(cancelButton);

        buttonsPanel.add(getStatementButton);
        buttonsPanel.add(cancelButton);

        contentPanel.add(cardPanel);
        contentPanel.add(Box.createVerticalGlue());
        contentPanel.add(buttonsPanel);

        add(contentPanel, BorderLayout.CENTER);

        getStatementButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (emailOption.isSelected()) {
                    sendStatementByEmail();
                } else if (localOption.isSelected()) {
                    saveStatementLocally();
                } else {
                    JOptionPane.showMessageDialog(null, "Please select an option.");
                }
                dispose();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    /**
     * Method to send the account statement by email.
     * Email functionality is disabled - saves locally instead.
     */
    private void sendStatementByEmail() {
        // Email functionality disabled - save locally instead
        JOptionPane.showMessageDialog(null, "Email service is disabled. Saving statement locally instead.");
        saveStatementLocally();
    }

    /**
     * Method to save the account statement locally as a text file.
     */
    private void saveStatementLocally() {
        String fileName = "account_statement_" + cardNum + "_" + LocalDate.now() + ".txt";
        try (FileWriter writer = new FileWriter(fileName)) {
            try (BufferedReader reader = new BufferedReader(new FileReader("records.txt"))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.startsWith("Account Number: " + accNum)) {
                        writer.write(line + "\n");
                        for (int i = 0; i < 3; i++) {
                            writer.write(reader.readLine() + "\n");
                        }
                    }
                }
            }
            JOptionPane.showMessageDialog(null, "Account statement saved as " + fileName + ".");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
