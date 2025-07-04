package project;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

//File manager class that handles user data and OTP storage/retrieval
class UserFileManager {
 private static final String USER_FILE = "users.txt";
 private static final String OTP_FILE = "otps.txt";

//Saves a new user to the user file
 public static void saveUser(User user) {
     try (PrintWriter writer = new PrintWriter(new FileWriter(USER_FILE, true))) {
    	// Store user data as comma-separated values, with password history separated by semicolons
    	 writer.println(user.getFirstName() + "," + user.getLastName() + "," +
                 user.getEmail() + "," + user.getUserType() + "," +
                 user.getHashedPassword() + "," +
                 String.join(";", user.getPasswordHistory()));
     } catch (IOException e) {
         System.out.println("Error saving user: " + e.getMessage());
     }
 }

//Loads a user based on their email address
 public static User loadUser(String email) {
     try (BufferedReader reader = new BufferedReader(new FileReader(USER_FILE))) {
         String line;
         while ((line = reader.readLine()) != null) {
             String[] parts = line.split(",");
             if (parts.length >= 5 && parts[2].equals(email)) {
                 User user = new User(parts[0], parts[1], parts[2], parts[3]);
                 user.setHashedPassword(parts[4]);
                 if (parts.length > 5 && !parts[5].isEmpty()) {
                     String[] history = parts[5].split(";");
                     for (String pwd : history) {
                         user.getPasswordHistory().add(pwd);
                     }
                 }
                 return user;
             }
         }
     } catch (IOException e) {
         System.out.println("Error loading user: " + e.getMessage());
     }
     return null; // User not found
 }

//Finds a user based on their first and last name
 public static User findUserByName(String firstName, String lastName) {
     try (BufferedReader reader = new BufferedReader(new FileReader(USER_FILE))) {
         String line;
         while ((line = reader.readLine()) != null) {
             String[] parts = line.split(",");
             if (parts.length >= 5 && parts[0].equals(firstName) && parts[1].equals(lastName)) {
                 User user = new User(parts[0], parts[1], parts[2], parts[3]);
                 user.setHashedPassword(parts[4]);
              // Add password history if present
                 if (parts.length > 5 && !parts[5].isEmpty()) {
                     String[] history = parts[5].split(";");
                     for (String pwd : history) {
                         user.getPasswordHistory().add(pwd);
                     }
                 }
                 return user;
             }
         }
     } catch (IOException e) {
         System.out.println("Error finding user: " + e.getMessage());
     }
     return null;
 }

//Updates an existing user's information in the file
 public static void updateUser(User user) {
     List<String> lines = new ArrayList<>();
  // Read all lines and update the one matching the user's email
     try (BufferedReader reader = new BufferedReader(new FileReader(USER_FILE))) {
         String line;
         while ((line = reader.readLine()) != null) {
             String[] parts = line.split(",");
             if (parts.length >= 3 && parts[2].equals(user.getEmail())) {
            	// Replace the line with updated user information
            	 line = user.getFirstName() + "," + user.getLastName() + "," +
                         user.getEmail() + "," + user.getUserType() + "," +
                         user.getHashedPassword() + "," +
                         String.join(";", user.getPasswordHistory());
             }
             lines.add(line);
         }
     } catch (IOException e) {
         System.out.println("Error reading user file: " + e.getMessage());
         return;
     }

  // Write the updated list of users back to the file
     try (PrintWriter writer = new PrintWriter(new FileWriter(USER_FILE))) {
         for (String line : lines) {
             writer.println(line);
         }
     } catch (IOException e) {
         System.out.println("Error updating user file: " + e.getMessage());
     }
 }

 // Stores a newly generated OTP for a user's email
 public static void saveOTP(String email, String otp) {
     try (PrintWriter writer = new PrintWriter(new FileWriter(OTP_FILE, true))) {
         writer.println(email + "," + otp + "," + System.currentTimeMillis());
     } catch (IOException e) {
         System.out.println("Error saving OTP: " + e.getMessage());
     }
 }

//Verifies if an OTP is valid (within 10 minutes) and matches the one stored
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
              // Check if OTP is still valid (within 10 minutes)
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
     
     // Rewrite the file excluding the used/expired OTP
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