import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * The Main class represents the main GUI window of the ATM simulation
 * application.
 */
public class Main extends JFrame {
    private JLabel cardNumLabel, pinNumLabel;
    private JButton signIn, signUp, clear;
    private JTextField cardNumInput;
    private JPasswordField pinNumInput;
    private Validate validator;
    private List<UserData> userDataList;

    /**
     * Constructor for the Main class.
     * Initializes the main GUI window.
     */
    public Main() {
        setTitle("ATM Simulation - Apna Bank");
        setSize(700, 680);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(false);
        try {
            setIconImage(new ImageIcon("src/icon.png").getImage());
        } catch (Exception e) {
            // Icon not found, continue without it
        }
        getContentPane().setBackground(ATMStyle.BACKGROUND);
        initComponents();
    }

    /**
     * Initializes all the components of the main GUI window.
     */
    private void initComponents() {
        // Fetch user data from the file
        userDataList = UserData.fetchUserData();

        // Header Panel
        JPanel headerPanel = ATMStyle.createHeaderPanel("Welcome");
        add(headerPanel, BorderLayout.NORTH);

        // Main Content Panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(ATMStyle.BACKGROUND);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(50, 60, 50, 60));

        // Title Section
        JLabel welcomeTitle = new JLabel("🏦 Welcome to Apna Bank ATM");
        welcomeTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        welcomeTitle.setForeground(ATMStyle.PRIMARY);
        welcomeTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitle = new JLabel("Secure & Fast Banking Services");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        subtitle.setForeground(ATMStyle.TEXT_SECONDARY);
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        subtitle.setBorder(BorderFactory.createEmptyBorder(4, 0, 0, 0));

        // Card Panel (login form)
        JPanel cardPanel = ATMStyle.createCardPanel();
        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));
        cardPanel.setMaximumSize(new Dimension(500, 280));
        cardPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        cardPanel.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(ATMStyle.PRIMARY_LIGHT, 1, true),
                BorderFactory.createEmptyBorder(22, 22, 22, 22)));

        // Card Number
        JPanel cardNumPanel = new JPanel(new BorderLayout(10, 6));
        cardNumPanel.setBackground(ATMStyle.CARD_BG);
        cardNumLabel = new JLabel("💳 Card Number (12 digits)");
        cardNumLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        cardNumLabel.setForeground(ATMStyle.TEXT_PRIMARY);
        cardNumInput = new JTextField(12);
        ATMStyle.styleTextField(cardNumInput);
        cardNumInput.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        cardNumPanel.add(cardNumLabel, BorderLayout.NORTH);
        cardNumPanel.add(cardNumInput, BorderLayout.CENTER);

        // PIN Number
        JPanel pinNumPanel = new JPanel(new BorderLayout(10, 6));
        pinNumPanel.setBackground(ATMStyle.CARD_BG);
        pinNumPanel.setBorder(BorderFactory.createEmptyBorder(14, 0, 0, 0));
        pinNumLabel = new JLabel("🔐 PIN (6 digits)");
        pinNumLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        pinNumLabel.setForeground(ATMStyle.TEXT_PRIMARY);
        pinNumInput = new JPasswordField(12);
        ATMStyle.stylePasswordField(pinNumInput);
        pinNumInput.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        pinNumPanel.add(pinNumLabel, BorderLayout.NORTH);
        pinNumPanel.add(pinNumInput, BorderLayout.CENTER);

        cardPanel.add(cardNumPanel);
        cardPanel.add(pinNumPanel);

        // Buttons Panel
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 18, 0));
        buttonsPanel.setBackground(ATMStyle.BACKGROUND);
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(28, 0, 0, 0));

        signIn = new JButton("Sign In");
        ATMStyle.styleSuccessButton(signIn);
        signIn.setPreferredSize(new Dimension(130, 44));

        signUp = new JButton("Sign Up");
        ATMStyle.styleButton(signUp);
        signUp.setPreferredSize(new Dimension(130, 44));

        clear = new JButton("Clear");
        ATMStyle.styleDangerButton(clear);
        clear.setPreferredSize(new Dimension(130, 44));

        buttonsPanel.add(signIn);
        buttonsPanel.add(signUp);
        buttonsPanel.add(clear);

        mainPanel.add(Box.createVerticalStrut(8));
        mainPanel.add(welcomeTitle);
        mainPanel.add(subtitle);
        mainPanel.add(Box.createVerticalStrut(24));
        mainPanel.add(cardPanel);
        mainPanel.add(Box.createVerticalGlue());
        mainPanel.add(buttonsPanel);

        add(mainPanel, BorderLayout.CENTER);

        // Footer
        add(ATMStyle.createFooterPanel(), BorderLayout.SOUTH);

        // ActionListener for clear button
        clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Clear the card number and PIN input fields
                cardNumInput.setText("");
                pinNumInput.setText("");
            }
        });

        // ActionListener for sign up button
        signUp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open sign up dialog
                SignUp signUpDialog = new SignUp(Main.this, userDataList);
                signUpDialog.setVisible(true);
            }
        });

        // ActionListener for sign in button
        signIn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Fetch user data
                userDataList = UserData.fetchUserData();
                validator = new Validate();
                String cardNumber = cardNumInput.getText();
                String pin = new String(pinNumInput.getPassword());

                if (validator.isCard(cardNumber) && validator.isPin(pin)) {
                    if (validator.authenticateUser(cardNumber, pin, userDataList)) {
                        cardNumInput.setText("");
                        pinNumInput.setText("");
                        JOptionPane.showMessageDialog(null, "Login Successful!");
                        // Open sign in dialog
                        SignIn signInDialog = new SignIn(Main.this, cardNumber, userDataList);
                        signInDialog.setVisible(true);
                    } else {
                        cardNumInput.setText("");
                        pinNumInput.setText("");
                        JOptionPane.showMessageDialog(null, "Authentication Failed. Invalid Card Number or PIN.");
                    }
                } else {
                    cardNumInput.setText("");
                    pinNumInput.setText("");
                    JOptionPane.showMessageDialog(null, "Please Enter Valid Card Number And PIN....");
                }
            }
        });
    }

    /**
     * The main method to start the application.
     * 
     * @param args Command-line arguments (not used)
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Main().setVisible(true);
            }
        });
    }

    /**
     * Static block to display a splash screen on application start.
     */
    static {
        JWindow splashScreen = new JWindow();
        splashScreen.setSize(520, 360);

        ImageIcon splashIcon = null;
        try {
            splashIcon = new ImageIcon(Main.class.getResource("/icon.png"));
        } catch (Exception e) {
            // Icon not found, continue without it
        }

        if (splashIcon != null && splashIcon.getIconWidth() > 0) {
            JLabel splashLabel = new JLabel(splashIcon);
            splashScreen.add(splashLabel);
        } else {
            JLabel splashLabel = new JLabel("ATM Simulation - Loading...", JLabel.CENTER);
            splashLabel.setFont(new Font("Arial", Font.BOLD, 24));
            splashScreen.add(splashLabel);
        }

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        splashScreen.setLocation(
                (screenSize.width - splashScreen.getWidth()) / 2,
                (screenSize.height - splashScreen.getHeight()) / 2);

        splashScreen.setVisible(true);

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        splashScreen.dispose();
    }

    /**
     * Restarts the application.
     */
    private void restartApplication() {
        dispose(); // Close the current window
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Main().setVisible(true); // Launch a new instance of Main
            }
        });
    }
}
