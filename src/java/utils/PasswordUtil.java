package utils;

import java.security.MessageDigest;

public class PasswordUtil {

    // Hash password with SHA-256
    public static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes("UTF-8"));

            // Convert byte[] to hex string
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b)); // 2-digit hex
            }
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }

    // (Optional) Add salt to password before hashing
    public static String hashPasswordWithSalt(String password, String salt) {
        return hashPassword(password + salt);
    }
}
