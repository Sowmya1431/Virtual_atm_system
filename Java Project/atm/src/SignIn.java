import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * The SignIn class represents the dialog window for signed-in users.
 */
class SignIn extends JDialog {
    private String cardNum;
    private List<UserData> userDataList;
    private JButton depositB, withdrawB, changePinB, fastWithdrawalB, requestBalanceB, requestStatementB, trasB, logOutB;
    private JLabel welcomeLabel, balanceLabel;
    private JPanel buttonsPanel, welcomePanel;

    /**
     * Constructor for SignIn class.
     *
     * @param owner       The owner frame of the dialog.
     * @param cardNum     The card number of the signed-in user.
     * @param userDataList The list of user data.
     */
    public SignIn(Frame owner, String cardNum, List<UserData> userDataList) {
        super(owner, true);
        setTitle("ATM Dashboard - Apna Bank");
        setResizable(false);
        setSize(780, 640);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setIconImage(new ImageIcon("src/icon.png").getImage());
        this.cardNum = cardNum;
        this.userDataList = userDataList;
        initComponents();
    }

    /**
     * Initializes all the components of the SignIn dialog.
     */
    private void initComponents() {
        // Header Panel
        JPanel headerPanel = ATMStyle.createHeaderPanel("Dashboard");
        add(headerPanel, BorderLayout.NORTH);

        // Main content panel with scroll support
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(ATMStyle.BACKGROUND);

        // Welcome panel with user info - styled as a card
        welcomePanel = new JPanel();
        welcomePanel.setLayout(new BoxLayout(welcomePanel, BoxLayout.Y_AXIS));
        welcomePanel.setBackground(ATMStyle.BACKGROUND);
        welcomePanel.setBorder(BorderFactory.createEmptyBorder(24, 50, 18, 50));

        // Card for welcome and balance
        JPanel welcomeCardPanel = ATMStyle.createCardPanel();
        welcomeCardPanel.setLayout(new BoxLayout(welcomeCardPanel, BoxLayout.Y_AXIS));
        welcomeCardPanel.setMaximumSize(new Dimension(700, 160));
        welcomeCardPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        welcomeCardPanel.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(ATMStyle.PRIMARY_LIGHT, 2, true),
            BorderFactory.createEmptyBorder(28, 32, 28, 32)
        ));

        welcomeLabel = new JLabel("👤 Welcome, " + UserData.getData(cardNum, 4, userDataList));
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        welcomeLabel.setForeground(ATMStyle.TEXT_PRIMARY);
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel amountLabel = new JLabel("Account Balance");
        amountLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        amountLabel.setForeground(ATMStyle.TEXT_SECONDARY);
        amountLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        amountLabel.setBorder(BorderFactory.createEmptyBorder(12, 0, 6, 0));

        balanceLabel = new JLabel("₹ " + UserData.getData(cardNum, 5, userDataList));
        balanceLabel.setFont(new Font("Segoe UI", Font.BOLD, 36));
        balanceLabel.setForeground(ATMStyle.ACCENT);
        balanceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        welcomeCardPanel.add(welcomeLabel);
        welcomeCardPanel.add(amountLabel);
        welcomeCardPanel.add(balanceLabel);

        welcomePanel.add(welcomeCardPanel);

        // Buttons panel with grid layout - 2 rows x 4 columns
        JPanel buttonsContainerPanel = new JPanel(new BorderLayout());
        buttonsContainerPanel.setBackground(ATMStyle.BACKGROUND);
        buttonsContainerPanel.setBorder(BorderFactory.createEmptyBorder(0, 50, 24, 50));

        JLabel servicesLabel = new JLabel("💼 Available Services");
        servicesLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        servicesLabel.setForeground(ATMStyle.TEXT_PRIMARY);
        servicesLabel.setBorder(BorderFactory.createEmptyBorder(12, 4, 14, 0));

        buttonsPanel = new JPanel(new GridLayout(2, 4, 20, 20));
        buttonsPanel.setBackground(ATMStyle.BACKGROUND);

        depositB = ATMStyle.createMenuButton("Deposit", "💰");
        withdrawB = ATMStyle.createMenuButton("Withdraw", "💵");
        changePinB = ATMStyle.createMenuButton("Change PIN", "🔐");
        fastWithdrawalB = ATMStyle.createMenuButton("Fast Cash", "⚡");
        requestBalanceB = ATMStyle.createMenuButton("Balance", "📊");
        requestStatementB = ATMStyle.createMenuButton("Statement", "📄");
        trasB = ATMStyle.createMenuButton("Transfer", "↔️");
        logOutB = ATMStyle.createMenuButton("Log Out", "🚪");
        logOutB.setBackground(new Color(255, 240, 240));
        logOutB.setForeground(ATMStyle.DANGER);

        buttonsPanel.add(depositB);
        buttonsPanel.add(withdrawB);
        buttonsPanel.add(fastWithdrawalB);
        buttonsPanel.add(requestBalanceB);
        buttonsPanel.add(changePinB);
        buttonsPanel.add(requestStatementB);
        buttonsPanel.add(trasB);
        buttonsPanel.add(logOutB);

        buttonsContainerPanel.add(servicesLabel, BorderLayout.NORTH);
        buttonsContainerPanel.add(buttonsPanel, BorderLayout.CENTER);

        contentPanel.add(welcomePanel, BorderLayout.NORTH);
        contentPanel.add(buttonsContainerPanel, BorderLayout.CENTER);
        add(contentPanel, BorderLayout.CENTER);

        // Footer
        add(ATMStyle.createFooterPanel(), BorderLayout.SOUTH);

        depositB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Deposit d = new Deposit(((Frame) SignIn.this.getParent()), cardNum, userDataList);
                d.setVisible(true);
            }
        });
        withdrawB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Withdrawal w = new Withdrawal(((Frame) SignIn.this.getParent()), cardNum, userDataList);
                w.setVisible(true);
            }
        });
        requestBalanceB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Your current Amount is ₹" + UserData.getData(cardNum, 5, userDataList));
            }
        });
        changePinB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ChangePIN c = new ChangePIN(((Frame) SignIn.this.getParent()), cardNum, userDataList);
                c.setVisible(true);
            }
        });
        fastWithdrawalB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FastWithdrawal f = new FastWithdrawal(((Frame) SignIn.this.getParent()), cardNum, userDataList);
                f.setVisible(true);
            }
        });
        requestStatementB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GetStatement gs = new GetStatement(((Frame) SignIn.this.getParent()), cardNum, userDataList);
                gs.setVisible(true);
            }
        });
        trasB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tMoney t=new tMoney(((Frame) SignIn.this.getParent()), cardNum, userDataList);
                t.setVisible(true);
            }
        });
        logOutB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(SignIn.this, "Logging out, please wait a few seconds...");

                // Create a new thread to handle the logout delay
                Thread logoutThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            // Set the thread priority to low
                            Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
                            // Wait for 5 seconds
                            Thread.sleep(5000);
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        } finally {
                            // Dispose the dialog in the Event Dispatch Thread
                            SwingUtilities.invokeLater(new Runnable() {
                                @Override
                                public void run() {
                                    dispose();
                                }
                            });
                        }
                    }
                });

                // Start the thread
                logoutThread.start();
            }
        });
    }


}
