package Application;

import java.util.Date;
import java.util.Properties;

import jakarta.mail.*;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

/**
 * Handles sending emails for OTP and password reset functionality.
 * Uses Gmail's SMTP server for sending emails.
 */
class Email {
    private static final String SMTP_HOST = "smtp.gmail.com";
    private static final String EMAIL = "jahmariharrison2@gmail.com";
    private static final String PASSWORD = "zhng hpnn byqg gyqs";
    private static final int SMTP_PORT = 587;

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
            props.put("mial.smtp.starttls.required", "true");
            props.put("mail.smtp.ssl.ciphersuites", "TLS_AES_128_GCM_SHA256");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.ssl.protocols", "TLSv1.2");
            
            // Debugging properties (remove in production)
            props.put("mail.debug", "true");
            props.put("mail.smtp.debug", "true");
            
            
            Session session = Session.getInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(EMAIL, PASSWORD);
                }
            });

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(EMAIL));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject(subject);
            message.setText(body);
            message.setSentDate(new Date());

            Transport transport = session.getTransport("smtp");
            transport.connect(SMTP_HOST, SMTP_PORT, EMAIL, PASSWORD);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
            
            return true;
        } catch (AddressException e) {
        	e.printStackTrace();
            System.err.println("Invalid email address: " + e.getMessage());
            return false;
        } catch (MessagingException e) {
        	e.printStackTrace();
            System.err.println("Email sending failed: " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.err.println("Unexpected error sending email: " + e.getMessage());
            e.printStackTrace();
            System.err.println("Email sending error: " + e.getMessage());
            return false;
        }
        
    }
}