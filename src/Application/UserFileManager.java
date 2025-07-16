// FileManager.java
package Application;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.*;

import Application.Password.LoginStatus;

class UserFileManager {
    private static final String USER_FILE = "users.txt";
    private static final String OTP_FILE = "otps.txt";

    public static void saveUser(Password user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }

        // Create backup of existing file
        File userFile = new File(USER_FILE);
        File backupFile = new File(USER_FILE + ".bak");
        if (userFile.exists()) {
            try {
                Files.copy(userFile.toPath(), backupFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                System.err.println("Failed to create backup: " + e.getMessage());
            }
        }

        // Save user data
        try (PrintWriter writer = new PrintWriter(new FileWriter(USER_FILE, true))) {
            writer.println(user.toString());
        } catch (IOException e) {
            // Restore from backup if save failed
            if (backupFile.exists()) {
                try {
                    Files.copy(backupFile.toPath(), userFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException ex) {
                    System.err.println("Failed to restore from backup: " + ex.getMessage());
                }
            }
            System.err.println("Error saving user: " + e.getMessage());
            throw new RuntimeException("User save failed", e);
        } finally {
            // Clean up backup
            if (backupFile.exists()) {
                backupFile.delete();
            }
        }
    }

    public static Password loadUser(String email) {
        try (BufferedReader reader = new BufferedReader(new FileReader(USER_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 5 && parts[2].equals(email)) {
                    LoginStatus userType = LoginStatus.valueOf(parts[3]);
                    String hashedPassword = parts[4];
                    String historyStr = (parts.length > 5) ? parts[5] : ""; // Load history if exists
                    return new Password(
                        parts[0], parts[1], parts[2], 
                        "", "", userType, hashedPassword, historyStr
                    );
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading user: " + e.getMessage());
        }
        return null;
    }

    public static Password findUserByName(String firstName, String lastName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(USER_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 4 && parts[0].equals(firstName) && parts[1].equals(lastName)) {
                    Password.LoginStatus userType = Password.LoginStatus.valueOf(parts[3]);
                    return new Password(
                    	    parts[0], parts[1], parts[2], 
                    	    "", "", userType, parts[4], ""
                    	);
                }
            }
        } catch (IOException e) {
            System.out.println("Error finding user: " + e.getMessage());
        }
        return null;
    }

    public static void updateUser(Password user) {
    	 List<String> lines = new ArrayList<>();
    	    try (BufferedReader reader = new BufferedReader(new FileReader(USER_FILE))) {
    	        String line;
    	        while ((line = reader.readLine()) != null) {
    	            String[] parts = line.split(",");
    	            if (parts.length >= 3 && parts[2].equals(user.getEmail())) {
    	                // Preserve password history if exists
    	                String history = parts.length > 5 ? parts[5] : "";
    	                line = user.getFirstName() + "," + user.getLastName() + "," +
    	                      user.getEmail() + "," + user.getUserType() + "," +
    	                      user.getHashedPassword() + "," + history;
    	            }
    	            lines.add(line);
    	        }
    	    } catch (IOException e) {
    	        System.out.println("Error reading user file: " + e.getMessage());
    	        return;
    	    }

    	    try (PrintWriter writer = new PrintWriter(new FileWriter(USER_FILE))) {
    	        for (String line : lines) {
    	            writer.println(line);
    	        }
    	    } catch (IOException e) {
    	        System.out.println("Error updating user file: " + e.getMessage());
    	    }
    }

    public static void saveOTP(String email, String otp) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(OTP_FILE, true))) {
            writer.println(email + "," + otp + "," + System.currentTimeMillis());
        } catch (IOException e) {
            System.out.println("Error saving OTP: " + e.getMessage());
        }
    }

    public static boolean verifyOTP(String email, String enteredOTP) {
    	List<String> lines = new ArrayList<>();
        boolean verified = false;
        long currentTime = System.currentTimeMillis();

        try (BufferedReader reader = new BufferedReader(new FileReader(OTP_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    long otpTime = Long.parseLong(parts[2]);
                    
                    // Keep non-expired OTPs
                    if (currentTime - otpTime < 600000) {
                        // Check for match
                        if (parts[0].equals(email) && parts[1].equals(enteredOTP)) {
                            verified = true;
                        } else {
                            lines.add(line); // Keep valid non-matching OTPs
                        }
                    }
                    // Expired OTPs are discarded
                }
            }
        } catch (IOException e) {
            System.out.println("Error verifying OTP: " + e.getMessage());
        }
        
        // Save remaining OTPs
        try (PrintWriter writer = new PrintWriter(new FileWriter(OTP_FILE))) {
            for (String line : lines) {
                writer.println(line);
            }
        } catch (IOException e) {
            System.out.println("Error updating OTP file: " + e.getMessage());
        }

        return verified;
    }
}