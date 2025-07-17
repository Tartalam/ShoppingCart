package Application;

import java.util.Properties;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

/**
 * Handles sending emails for OTP and password reset functionality.
 * Uses Gmail's SMTP server for sending emails.
 */
public class Email {
    private static final String SMTP_HOST = "smtp.gmail.com";
    private static final String FROM_EMAIL = "jahmariharrison2@gmail.com";
    private static final String APP_PASSWORD = "jidv fdcb sbhk xxic";

    /**
     * Sends OTP code to user's email.
     * @param toEmail Recipient email address
     * @param otp One-time password to send
     * @return true if email sent successfully, false otherwise
     * @throws IllegalArgumentException if email is invalid or OTP is empty
     */
    public static boolean sendOTP(String toEmail, String otp) {
        if (toEmail == null || !toEmail.contains("@") || !toEmail.contains(".")) {
            throw new IllegalArgumentException("Invalid email address");
        }
        if (otp == null || otp.trim().isEmpty()) {
            throw new IllegalArgumentException("OTP cannot be empty");
        }
        
        String subject = "Your OTP Code";
        String body = "Your One Time Password is: " + otp + "\nThis code will expire in 10 minutes.";
        return sendEmail(toEmail, subject, body);
    }

    /**
     * Sends new password to user's email.
     * @param toEmail Recipient email address
     * @param newPassword New password to send
     * @return true if email sent successfully, false otherwise
     * @throws IllegalArgumentException if email is invalid or password is empty
     */
    public static boolean sendNewPassword(String toEmail, String newPassword) {
        if (toEmail == null || !toEmail.contains("@") || !toEmail.contains(".")) {
            throw new IllegalArgumentException("Invalid email address");
        }
        if (newPassword == null || newPassword.trim().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty");
        }
        
        String subject = "Password Reset";
        String body = "Your new password is: " + newPassword + "\nPlease change it after logging in.";
        return sendEmail(toEmail, subject, body);
    }

    /**
     * Internal method to send email using JavaMail API.
     * @param toEmail Recipient email
     * @param subject Email subject
     * @param body Email body content
     * @return true if sent successfully, false otherwise
     */
    private static boolean sendEmail(String toEmail, String subject, String body) {
        try {
            Properties props = new Properties();
            props.put("mail.smtp.host", SMTP_HOST);
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");

            	Authenticator authenticator = new Authenticator() { 
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(FROM_EMAIL, APP_PASSWORD);
                }
            	};
            Session session = Session.getInstance(props, authenticator);
            try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(FROM_EMAIL));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject(subject);
            message.setText(body);
            

            Transport.send(message); // Simplified transport handling
            return true;
            }catch(MessagingException e) {
            	throw new RuntimeException(e);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}