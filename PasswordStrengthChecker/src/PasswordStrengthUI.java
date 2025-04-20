import javax.swing.*;
import java.awt.*;

public class PasswordStrengthUI extends JFrame {
    private final JPasswordField passwordField = new JPasswordField();
    private final JTextArea feedbackArea = new JTextArea();
    private final JCheckBox showPassword = new JCheckBox("👁 Show Password");

    public PasswordStrengthUI() {
        setTitle("🔐 Password Strength Tester");
        setSize(550, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel label = new JLabel("Enter your password:", JLabel.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 16));

        passwordField.setFont(new Font("Arial", Font.PLAIN, 18));
        passwordField.setHorizontalAlignment(JTextField.CENTER);

        feedbackArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        feedbackArea.setEditable(false);
        feedbackArea.setLineWrap(true);
        feedbackArea.setWrapStyleWord(true);

        passwordField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) { updateFeedback(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { updateFeedback(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { updateFeedback(); }
        });

        showPassword.addActionListener(e -> passwordField.setEchoChar(showPassword.isSelected() ? (char) 0 : '•'));

        JButton generateButton = new JButton("🎲 Generate Password");
        generateButton.addActionListener(e -> {
            String newPass = PasswordStrengthChecker.generatePassword(12);
            passwordField.setText(newPass);
        });

        JButton saveButton = new JButton("💾 Save to Log");
        saveButton.addActionListener(e -> {
            String pass = new String(passwordField.getPassword());
            String result = PasswordStrengthChecker.evaluateStrength(pass);
            PasswordStrengthChecker.saveToFile(pass, result);
            JOptionPane.showMessageDialog(this, "Saved to password_log.txt");
        });

        JPanel controlPanel = new JPanel(new GridLayout(1, 3));
        controlPanel.add(showPassword);
        controlPanel.add(generateButton);
        controlPanel.add(saveButton);

        add(label, BorderLayout.NORTH);
        add(passwordField, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);
        add(new JScrollPane(feedbackArea), BorderLayout.EAST);
    }

    private void updateFeedback() {
        String password = new String(passwordField.getPassword());
        String result = PasswordStrengthChecker.evaluateStrength(password);
        feedbackArea.setText(result);
        feedbackArea.setForeground(getColorForStrength(result));
    }

    private Color getColorForStrength(String result) {
        if (result.startsWith("Strong")) return new Color(0, 128, 0);
        if (result.startsWith("Moderate")) return new Color(255, 165, 0);
        return Color.RED;
    }
}
