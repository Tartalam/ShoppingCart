package project;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

//Utility class
class PasswordUtils {
	/**
     * Hashes a password.
     * This converts the password into a fixed-length string.
     */
 public static String hashPassword(String password) {
     try {
         MessageDigest md = MessageDigest.getInstance("SHA-256");
         byte[] hashedBytes = md.digest(password.getBytes());
      // Convert hashed bytes to hexadecimal string format
         StringBuilder sb = new StringBuilder();
         for (byte b : hashedBytes) {
             sb.append(String.format("%02x", b));
         }
         return sb.toString();
     } catch (NoSuchAlgorithmException e) {
         throw new RuntimeException("Error hashing password", e);
     }
 }
 
 /**
  * Generates a 6-digit numeric OTP (One-Time Password).
  * Is used for verifying user identity during registration or password reset.
  */

 public static String generateOTP() {
     Random random = new Random();
     int otp = 100000 + random.nextInt(900000);
     return String.valueOf(otp);
 }

 public static String generateRandomPassword() {
     String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%";
     Random random = new Random();
     StringBuilder sb = new StringBuilder();
  // Pick 8 random characters from the allowed set
     for (int i = 0; i < 8; i++) {
         sb.append(chars.charAt(random.nextInt(chars.length())));
     }
     return sb.toString();
 }
}


