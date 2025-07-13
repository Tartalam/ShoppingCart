package Application;

/**
 * The PasswordManager class handles all password-related operations including:
 * - User registration with OTP verification
 * - Password setting and changes
 * - User authentication (login)
 * - Password recovery
 * - Admin password management
 */
public class PasswordManager {

    /**
     * Registers a new customer by validating inputs, generating an OTP, and saving temporary user data.
     * 
     * @param firstName User's first name (must not be empty)
     * @param lastName User's last name (must not be empty)
     * @param email User's email (must contain "@" and ".")
     * @return true if registration succeeds, false if inputs are invalid or user exists
     */
    public boolean registerCustomer(String firstName, String lastName, String email) {
        // Step 1: Validate required fields
        if (firstName.isEmpty() || lastName.isEmpty() || 
            email.isEmpty() || !email.contains("@") || !email.contains(".")) {
            return false;
        }

        // Step 2: Check if user already exists in the system
        if (UserFileManager.loadUser(email) != null) {
            return false;
        }

        // Step 3: Generate OTP and send via email
        String otp = PasswordUtility.generateOTP();
        if (Email.sendOTP(email, otp)) {
            // Step 4: Save OTP to file for verification later
            UserFileManager.saveOTP(email, otp);
            
            // Step 5: Create temporary user record (password will be set after OTP verification)
            Password user = new Password(
            	    firstName, lastName, email, 
            	    "", "", Password.LoginStatus.USER, "", ""
            	);
            UserFileManager.saveUser(user);
            return true;
        }
        return false;
    }

    /**
     * Verifies OTP and sets the user's password if validation passes.
     * 
     * @param email User's email for identification
     * @param enteredOTP OTP entered by the user
     * @param password New password to set
     * @param confirmPassword Confirmation of new password
     * @return Password object if successful, null if OTP or password is invalid
     */
    public Password verifyOTPAndSetPassword(String email, String enteredOTP, String password, String confirmPassword) {
        // Step 1: Verify OTP matches what was sent to the user
        if (!UserFileManager.verifyOTP(email, enteredOTP)) {
            return null;
        }

        // Step 2: Load user from storage
        Password user = UserFileManager.loadUser(email);
        
        // Step 3: Check password and confirmation match
        if (user != null && password.equals(confirmPassword)) {
            // Step 4: Hash password and update user record
            user.setHashedPassword(PasswordUtility.hashPassword(password));
            UserFileManager.saveUser(user);
            return user;
        }
        return null;
    }

    /**
     * Authenticates a user (either admin or regular user) based on credentials.
     * 
     * @param email User's email (or admin username)
     * @param password User's password
     * @return Password object if authentication succeeds, null otherwise
     */
    public Password login(String email, String password) {
        // Step 1: Handle admin login separately
        if (email.equals(Password.ADMIN_USERNAME)) {
            if (Password.verifyAdmin(email, password)) {
            	return new Password(
            		    "Admin", "Root", "admin@system.com",
            		    "", "", Password.LoginStatus.ADMIN, 
            		    PasswordUtility.hashPassword(password), ""
            		);
            }
            return null;
        }

        // Step 2: Handle regular user login
        Password user = UserFileManager.loadUser(email);
        if (user != null) {
            // Check if user has set a password yet (new registrations)
            if (user.getHashedPassword().isEmpty()) {
                return null;
            }
            
            // Verify password hash matches stored hash
            if (user.getHashedPassword().equals(PasswordUtility.hashPassword(password))) {
                return user;
            }
        }
        return null;
    }

    /**
     * Allows users to change their password after verifying current credentials.
     * 
     * @param user The user changing their password
     * @param currentPassword Current password for verification
     * @param newPassword New password to set
     * @param confirmPassword Confirmation of new password
     * @return true if password was changed successfully, false otherwise
     */
    public boolean changePassword(Password user, String currentPassword, String newPassword, String confirmPassword) {
        // Step 1: Verify current password is correct
        if (!user.getHashedPassword().equals(PasswordUtility.hashPassword(currentPassword))) {
            return false;
        }

        // Step 2: Check if new password was used before (from history)
        if (user.checkPasswordInHistory(PasswordUtility.hashPassword(newPassword))) {
            return false;
        }

        // Step 3: Verify new password and confirmation match
        if (newPassword.equals(confirmPassword)) {
            // Step 4: Update password and save to file
            user.setHashedPassword(PasswordUtility.hashPassword(newPassword));
            UserFileManager.saveUser(user);
            return true;
        }
        return false;
    }

    /**
     * Handles password recovery by generating a new random password and emailing it.
     * 
     * @param firstName User's first name for identification
     * @param lastName User's last name for identification
     * @return true if password was reset and emailed successfully
     */
    public boolean forgotPassword(String firstName, String lastName) {
        // Step 1: Find user by name
        Password user = UserFileManager.findUserByName(firstName, lastName);
        if (user != null) {
            // Step 2: Generate a secure random password
            String newPassword = PasswordUtility.generateRandomPassword();
            
            // Step 3: Email the new password to user
            if (Email.sendNewPassword(user.getEmail(), newPassword)) {
                // Step 4: Update password in system
                user.setHashedPassword(PasswordUtility.hashPassword(newPassword));
                UserFileManager.saveUser(user);
                return true;
            }
        }
        return false;
    }

    /**
     * Allows administrators to reset passwords for regular users.
     * 
     * @param email Email of user whose password needs resetting
     * @param newPassword New password to set
     * @return true if password was reset successfully, false otherwise
     */
    public boolean adminChangeCustomerPassword(String email, String newPassword) {
        // Step 1: Load user and verify they're a regular user (not admin)
        Password customer = UserFileManager.loadUser(email);
        if (customer != null && customer.getUserType() == Password.LoginStatus.USER) {
            // Step 2: Update password directly (no current password required for admin)
            customer.setHashedPassword(PasswordUtility.hashPassword(newPassword));
            UserFileManager.saveUser(customer);
            return true;
        }
        return false;
    }
}