import java.io.FileWriter;
import java.io.IOException;
import java.security.SecureRandom;

public class PasswordStrengthChecker {

    public static String evaluateStrength(String password) {
        int score = 0;
        StringBuilder tips = new StringBuilder();

        if (password.length() >= 8) score++;
        else tips.append("Password should be at least 8 characters long.\n");

        if (password.matches(".*[A-Z].*")) score++;
        else tips.append("Add at least one uppercase letter.\n");

        if (password.matches(".*[a-z].*")) score++;
        else tips.append("Add at least one lowercase letter.\n");

        if (password.matches(".*[0-9].*")) score++;
        else tips.append("Include at least one digit.\n");

        if (password.matches(".*[!@#$%^&*()_+=|<>?{}\\[\\]~-].*")) score++;
        else tips.append("Include at least one special character.\n");

        String rating;
        if (score <= 2) rating = "Weak";
        else if (score == 3 || score == 4) rating = "Moderate";
        else rating = "Strong";

        return rating + "\n" + (tips.length() > 0 ? "Tips:\n" + tips.toString() : "Great job!");
    }

    public static String generatePassword(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()-_+=<>?";
        SecureRandom rnd = new SecureRandom();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(rnd.nextInt(chars.length())));
        }
        return sb.toString();
    }

    public static void saveToFile(String password, String evaluation) {
        try (FileWriter writer = new FileWriter("password_log.txt", true)) {
            writer.write("Password: " + password + "\n" + evaluation + "\n-------------------------\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
