package com.example.webapp.util;

import org.mindrot.jbcrypt.BCrypt;

/**
 * Password Security Utility Class
 * Handles password hashing and verification using BCrypt
 */
public class PasswordUtil {
    
    private static final int BCRYPT_ROUNDS = 12;
    
    /**
     * Hash a plain text password using BCrypt
     * @param plainPassword Plain text password
     * @return Hashed password
     */
    public static String hashPassword(String plainPassword) {
        if (plainPassword == null || plainPassword.trim().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt(BCRYPT_ROUNDS));
    }
    
    /**
     * Verify a plain text password against a hashed password
     * @param plainPassword Plain text password
     * @param hashedPassword Hashed password from database
     * @return true if password matches, false otherwise
     */
    public static boolean verifyPassword(String plainPassword, String hashedPassword) {
        if (plainPassword == null || hashedPassword == null) {
            return false;
        }
        try {
            return BCrypt.checkpw(plainPassword, hashedPassword);
        } catch (Exception e) {
            System.err.println("Error verifying password: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Check if password meets basic security requirements
     * @param password Password to check
     * @return true if password is strong enough
     */
    public static boolean isPasswordStrong(String password) {
        if (password == null || password.length() < 6) {
            return false;
        }
        
        // Basic password strength check
        boolean hasUppercase = password.matches(".*[A-Z].*");
        boolean hasLowercase = password.matches(".*[a-z].*");
        boolean hasDigit = password.matches(".*[0-9].*");
        
        return hasUppercase && hasLowercase && hasDigit;
    }
    
    /**
     * Get password requirements message
     * @return Requirements message
     */
    public static String getPasswordRequirements() {
        return "Password must be at least 6 characters with uppercase, lowercase, and numbers";
    }
}
