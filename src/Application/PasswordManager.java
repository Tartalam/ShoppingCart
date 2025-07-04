package project;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

//Main logic that handles registration, login, password change for user and admin tasks
class PasswordManager {
 private Scanner scanner;

//Constructor initializes the scanner and ensures an admin user exists
 public PasswordManager() {
     this.scanner = new Scanner(System.in);
     initializeAdmin();
 }

//Creates a default admin user if one doesn't already exist
 private void initializeAdmin() {
     User admin = UserFileManager.loadUser("admin@system.com");
     if (admin == null) {
         admin = new User("Admin", "Root", "admin@system.com", "admin");
         admin.setHashedPassword(PasswordUtils.hashPassword("admin"));
         UserFileManager.saveUser(admin);
     }
 }

 // Handles customer registration flow
 public boolean registerCustomer() {
 System.out.print("Enter first name: ");
 String firstName = scanner.nextLine().trim();
 if (firstName.isEmpty()) {
     System.out.println("First name cannot be empty.");
     return false;
 }

 System.out.print("Enter last name: ");
 String lastName = scanner.nextLine().trim();
 if (lastName.isEmpty()) {
     System.out.println("Last name cannot be empty.");
     return false;
 }

 System.out.print("Enter email address: ");
 String email = scanner.nextLine().trim();
 if (email.isEmpty() || !email.contains("@") || !email.contains(".")) {
     System.out.println("Invalid email address.");
     return false;
 }

 // Check if user already exists
 if (UserFileManager.loadUser(email) != null) {
     System.out.println("User with this email already exists!");
     return false;
 }

 // Generate and send OTP for verification
 String otp = PasswordUtils.generateOTP();
 if (EmailService.sendOTP(email, otp)) {
     UserFileManager.saveOTP(email, otp);

     // Create user (password will be set after OTP verification)
     User user = new User(firstName, lastName, email, "customer");
     UserFileManager.saveUser(user);

     System.out.println("Registration successful! OTP sent to your email.");
     return true;
 } else {
     System.out.println("Failed to send OTP. Registration failed.");
     return false;
 }
}

//Verifies the OTP entered by the user and allows them to set a password
 public User verifyOTPAndSetPassword(String email) {
     System.out.print("Enter OTP sent to your email: ");
     String enteredOTP = scanner.nextLine().trim();

     if (UserFileManager.verifyOTP(email, enteredOTP)) {
         System.out.println("OTP verified successfully!");
         User user = UserFileManager.loadUser(email);
         if (user != null) {
             return setInitialPassword(user); // Sets password
         }
     } else {
         System.out.println("Invalid or expired OTP!");
     }
     return null;
 }

//Prompts the user to create and confirm a password
 private User setInitialPassword(User user) {
     System.out.println("Please set your password:");
     System.out.print("Enter new password: ");
     String password = scanner.nextLine();
     System.out.print("Confirm password: ");
     String confirmPassword = scanner.nextLine();

  // If passwords match, hash and save it
     if (password.equals(confirmPassword)) {
         user.setHashedPassword(PasswordUtils.hashPassword(password));
         UserFileManager.updateUser(user);
         System.out.println("Password set successfully!");
         return user;
     } else {
         System.out.println("Passwords don't match!");
         return setInitialPassword(user);
     }
 }

//Handles login for both admin and customers
 public User login() {
     System.out.print("Enter email (or 'root' for admin): ");
     String identifier = scanner.nextLine().trim();

  // Admin login check
     if (identifier.equals("root")) {
         System.out.print("Enter password: ");
         String password = scanner.nextLine();
         User admin = UserFileManager.loadUser("admin@system.com");
         if (admin != null && admin.getHashedPassword().equals(PasswordUtils.hashPassword(password))) {
             System.out.println("Admin login successful!");
             return admin;
         } else {
             System.out.println("Invalid admin credentials!");
             return null;
         }
     }

  // Customer login check
     User user = UserFileManager.loadUser(identifier);
     if (user != null) {
         if (user.getHashedPassword() == null) {
             System.out.println("Please complete registration by verifying OTP first.");
             return verifyOTPAndSetPassword(identifier);
         }

         System.out.print("Enter password: ");
         String password = scanner.nextLine();
         if (user.getHashedPassword().equals(PasswordUtils.hashPassword(password))) {
             System.out.println("Login successful!");
             return user;
         } else {
             System.out.println("Invalid password!");
         }
     } else {
         System.out.println("User not found!");
     }
     return null;
 }

//Allows a logged-in user to change their password securely
 public boolean changePassword(User user) {
     System.out.print("Enter current password: ");
     String currentPassword = scanner.nextLine();
     String hashedCurrent = PasswordUtils.hashPassword(currentPassword);

  // Check if current password matches
     if (!user.getHashedPassword().equals(hashedCurrent)) {
         System.out.println("Current password is incorrect!");
         return false;
     }

     System.out.print("Enter new password: ");
     String newPassword = scanner.nextLine();
     String hashedNew = PasswordUtils.hashPassword(newPassword);

  // Prevent password reuse
     if (hashedNew.equals(user.getHashedPassword()) || user.checkPasswordInHistory(hashedNew)) {
         System.out.println("Cannot use previous passwords! Please choose a different password.");
         return false;
     }

     System.out.print("Confirm new password: ");
     String confirmPassword = scanner.nextLine();

     if (newPassword.equals(confirmPassword)) {
         user.setHashedPassword(hashedNew);
         UserFileManager.updateUser(user);
         System.out.println("Password changed successfully!");
         return true;
     } else {
         System.out.println("Passwords don't match!");
         return false;
     }
 }

//Handles the forgot password flow for users
 public boolean forgotPassword() {
     System.out.print("Enter first name: ");
     String firstName = scanner.nextLine().trim();
     System.out.print("Enter last name: ");
     String lastName = scanner.nextLine().trim();

  // Try to find the user by name
     User user = UserFileManager.findUserByName(firstName, lastName);
     if (user != null) {
         String newPassword = PasswordUtils.generateRandomPassword();
         String hashedNew = PasswordUtils.hashPassword(newPassword);

      // Send the new password via email
         if (EmailService.sendNewPassword(user.getEmail(), newPassword)) {
             user.setHashedPassword(hashedNew);
             UserFileManager.updateUser(user);
             System.out.println("New password sent to your email address!");
             return true;
         } else {
             System.out.println("Failed to send email!");
         }
     } else {
         System.out.println("User not found!");
     }
     return false;
 }

//Allows the admin to reset a customer’s password manually
 public boolean adminChangeCustomerPassword() {
     System.out.print("Enter customer email: ");
     String email = scanner.nextLine().trim();

     User customer = UserFileManager.loadUser(email);
     if (customer != null && customer.getUserType().equals("customer")) {
         System.out.print("Enter new password for customer: ");
         String newPassword = scanner.nextLine();
         String hashedNew = PasswordUtils.hashPassword(newPassword);

         customer.setHashedPassword(hashedNew);
         UserFileManager.updateUser(customer);
         System.out.println("Customer password changed successfully!");
         return true;
     } else {
         System.out.println("Customer not found!");
         return false;
     }
 }
}

