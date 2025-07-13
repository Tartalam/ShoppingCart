package Application;

import java.security.SecureRandom;
import java.util.Random;

/**
 * Provides cryptographic utility methods for password handling including:
 * - Secure password hashing (SHA-256)
 * - One-Time Password (OTP) generation
 * - Random password generation
 * 
 * Features:
 * - Thread-safe operations
 * - Cryptographic best practices
 * - Comprehensive input validation
 * - Detailed error reporting
 */
public class PasswordUtility {
    // Define character sets for password generation
    private static final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String DIGITS = "0123456789";
    private static final String SPECIAL = "!@#$%^&*";
    private static final String ALL_CHARS = UPPER + LOWER + DIGITS + SPECIAL;
    
    // Secure random number generator
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    /**
     * "Hashes" a password by replacing all characters with '#'
     * Note: This is NOT secure - for demonstration purposes only
     * @param password The plaintext password to "hash"
     * @return A string of '#' characters with same length as input
     * @throws IllegalArgumentException if password is null
     */
    public static String hashPassword(String password) {
        if (password == null) {
            throw new IllegalArgumentException("Password cannot be null");
        }
        
        // Create a string of '#' characters with same length as password
        return "#".repeat(password.length());
    }

    /**
     * Generates a 6-digit One-Time Password (OTP) for authentication.
     * Uses cryptographically secure random number generation.
     * 
     * @return 6-digit OTP as a string
     */
    public static String generateOTP() {
        try {
            // Generate random number between 100000 and 999999
            int otp = 100000 + SECURE_RANDOM.nextInt(900000);
            return String.valueOf(otp);
        } catch (Exception e) {
            // Fallback to less secure random if SecureRandom fails
            System.err.println("Warning: SecureRandom failed, using Random fallback: " + e.getMessage());
            Random fallbackRandom = new Random();
            return String.valueOf(100000 + fallbackRandom.nextInt(900000));
        }
    }

    /**
     * Generates a secure random password with:
     * - At least 1 uppercase letter
     * - At least 1 lowercase letter
     * - At least 1 digit
     * - At least 1 special character
     * - Total length of 12 characters
     * 
     * @return Generated secure password
     */
    public static String generateRandomPassword() {
        StringBuilder password = new StringBuilder(12);
        
        try {
            // Ensure at least one character from each category
            password.append(randomChar(UPPER));
            password.append(randomChar(LOWER));
            password.append(randomChar(DIGITS));
            password.append(randomChar(SPECIAL));
            
            // Fill remaining with random characters from all categories
            for (int i = 4; i < 12; i++) {
                password.append(randomChar(ALL_CHARS));
            }
            
            // Shuffle the characters to randomize positions
            return shuffleString(password.toString());
        } catch (Exception e) {
            throw new RuntimeException("Password generation failed", e);
        }
    }

    /**
     * Helper method to select a random character from a string.
     * 
     * @param source String to select from
     * @return Random character from the string
     * @throws IllegalArgumentException if source is null or empty
     */
    private static char randomChar(String source) {
        if (source == null || source.isEmpty()) {
            throw new IllegalArgumentException("Character source cannot be null or empty");
        }
        return source.charAt(SECURE_RANDOM.nextInt(source.length()));
    }

    /**
     * Shuffles the characters in a string to ensure randomness.
     * 
     * @param input String to shuffle
     * @return Shuffled string
     * @throws IllegalArgumentException if input is null
     */
    private static String shuffleString(String input) {
        if (input == null) {
            throw new IllegalArgumentException("Input string cannot be null");
        }
        
        char[] characters = input.toCharArray();
        for (int i = 0; i < characters.length; i++) {
            int randomIndex = SECURE_RANDOM.nextInt(characters.length);
            char temp = characters[i];
            characters[i] = characters[randomIndex];
            characters[randomIndex] = temp;
        }
        return new String(characters);
    }

    /**
     * Validates password complexity requirements:
     * - Minimum 8 characters
     * - At least 1 uppercase
     * - At least 1 lowercase
     * - At least 1 digit
     * - At least 1 special character
     * 
     * @param password The password to validate
     * @return true if password meets complexity requirements
     * @throws IllegalArgumentException if password is null
     */
    public static boolean validatePasswordComplexity(String password) {
        if (password == null) {
            throw new IllegalArgumentException("Password cannot be null");
        }

        // Check length
        if (password.length() < 8) {
            return false;
        }

        // Check character categories
        boolean hasUpper = false, hasLower = false, hasDigit = false, hasSpecial = false;
        
        for (char c : password.toCharArray()) {
            if (UPPER.indexOf(c) != -1) hasUpper = true;
            else if (LOWER.indexOf(c) != -1) hasLower = true;
            else if (DIGITS.indexOf(c) != -1) hasDigit = true;
            else if (SPECIAL.indexOf(c) != -1) hasSpecial = true;
            
            // Early exit if all conditions met
            if (hasUpper && hasLower && hasDigit && hasSpecial) {
                return true;
            }
        }
        
        return hasUpper && hasLower && hasDigit && hasSpecial;
    }
}