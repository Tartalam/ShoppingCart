package project;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

//User class
class User {
 private String firstName;
 private String lastName;
 private String email;
 private String hashedPassword;
 private String userType; // "admin" or "customer"
 private List<String> passwordHistory;

 public User(String firstName, String lastName, String email, String userType) {
     this.firstName = firstName;
     this.lastName = lastName;
     this.email = email;
     this.userType = userType;
     this.passwordHistory = new ArrayList<>();
 }

 public String getFirstName() { return firstName; }
 public String getLastName() { return lastName; }
 public String getEmail() { return email; }
 public String getHashedPassword() { return hashedPassword; }
 public String getUserType() { return userType; }
 public List<String> getPasswordHistory() { return passwordHistory; }

 public void setHashedPassword(String hashedPassword) {
     if (this.hashedPassword != null) {
         passwordHistory.add(0, this.hashedPassword);
         if (passwordHistory.size() > 3) {
             passwordHistory.remove(3);
         }
     }
     this.hashedPassword = hashedPassword;
 }

 public boolean checkPasswordInHistory(String hashedPassword) {
     return passwordHistory.contains(hashedPassword);
 }
}
