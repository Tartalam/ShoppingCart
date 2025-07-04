package project;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

//Email Service Implementation
class EmailService {
// Gmail SMTP server settings
 private static final String SMTP_HOST = "smtp.gmail.com";
 // Email address and password used to send the otp to customers
 private static final String EMAIL = "kerrandre1426@gmail.com";
 private static final String PASSWORD = "aeazdbjjuyhrtvkf";

 // Sends a one-time password (OTP) to users email
public static boolean sendOTP(String toEmail, String otp) {
     return sendEmail(toEmail, "Your OTP Code", "Your One Time Password is: " + otp + "\nThis code will expire in 10 minutes.");
 }

// Sends a new password to user
 public static boolean sendNewPassword(String toEmail, String newPassword) {
     return sendEmail(toEmail, "Password Reset", "Your new password is: " + newPassword + "\nPlease change it after logging in.");
 }

 // Core method that handles email sending logic using JavaMail API
 private static boolean sendEmail(String toEmail, String subject, String body) {
     try {
         Properties props = new Properties();
         props.put("mail.smtp.host", SMTP_HOST);
         props.put("mail.smtp.port", "587");
         props.put("mail.smtp.auth", "true");
         props.put("mail.smtp.starttls.enable", "true");

      // Create a mail session with authentication
         Session session = Session.getInstance(props, new Authenticator() {
             @Override
             protected PasswordAuthentication getPasswordAuthentication() {
                 return new PasswordAuthentication(EMAIL, PASSWORD);
             }
         });

      // Construct the email message
         Message message = new MimeMessage(session);
         message.setFrom(new InternetAddress(EMAIL));
         message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
         message.setSubject(subject);
         message.setText(body);

         Transport.send(message);
         return true;
     } catch (Exception e) {
         System.out.println("Failed to send email: " + e.getMessage());
         return false;
     }
 }
}