// FileManager.java
package Application;

import java.io.*;
import java.util.*;

import Application.Password.LoginStatus;

class UserFileManager {
    private static final String USER_FILE = "users.txt";
    private static final String OTP_FILE = "otps.txt";

    public static void saveUser(Password user) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(USER_FILE, true))) {
            writer.println(user.toString()); // Now includes history
        } catch (IOException e) {
            System.out.println("Error saving user: " + e.getMessage());
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
                    line = user.getFirstName() + "," + user.getLastName() + "," +
                          user.getEmail() + "," + user.getUserType() + "," +
                          user.getHashedPassword();
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
                    if (currentTime - otpTime < 600000) {
                        if (parts[0].equals(email) && parts[1].equals(enteredOTP)) {
                            verified = true;
                        } else {
                            lines.add(line);
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error verifying OTP: " + e.getMessage());
        }
        
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