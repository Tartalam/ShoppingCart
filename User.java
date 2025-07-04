package project;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

//User class represents each user in the system (either admin or customer)
class User {
 private String firstName;
 private String lastName;
 private String email;
 private String hashedPassword;
 private String userType; // Can be "admin" or "customer"
 private List<String> passwordHistory; // Stores up to 3 previous passwords

//Constructor to create a new user object with basic info
 public User(String firstName, String lastName, String email, String userType) {
     this.firstName = firstName;
     this.lastName = lastName;
     this.email = email;
     this.userType = userType;
     this.passwordHistory = new ArrayList<>(); // Start with an empty history
 }

//--- Getters ---
 public String getFirstName() { return firstName; }
 public String getLastName() { return lastName; }
 public String getEmail() { return email; }
 public String getHashedPassword() { return hashedPassword; }
 public String getUserType() { return userType; }
 public List<String> getPasswordHistory() { return passwordHistory; }

 // --- Setters ---

 /**
  * Sets the new hashed password for the user.
  * If there's an old password already, it gets saved to the password history.
  * The history keeps only the last 3 passwords to prevent reuse.
  */
 public void setHashedPassword(String hashedPassword) {
     if (this.hashedPassword != null) {
    	// Save the current password to history before changing it
         passwordHistory.add(0, this.hashedPassword);
         
      // Limit history to the 3 most recent passwords
         if (passwordHistory.size() > 3) {
             passwordHistory.remove(3);
         }
     }
     this.hashedPassword = hashedPassword;
 }

 /**
  * Checks if the given hashed password has been used recently.
  * Helps enforce password reuse policy.
  */
 public boolean checkPasswordInHistory(String hashedPassword) {
     return passwordHistory.contains(hashedPassword);
 }
}
