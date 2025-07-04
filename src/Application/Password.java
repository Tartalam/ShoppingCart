package Application;

public class Password implements Identifiable {
	
	// enum class for user login
	public enum LoginStatus{ADMIN, USER, NO_USER}
	
    private String firstName;
    private String lastName;
    private String email;
    private LoginStatus userType;
    private String password;
    private String confirmPassword;
    
    // Admin credentials (hardcoded)
    public static final String ADMIN_USERNAME = "root";
    public static final String ADMIN_PASSWORD = "admin";
    
    public Password() {
        this.firstName = "";
        this.lastName = "";
        this.email = "";
        this.password = "";
        this.confirmPassword = "";
        this.userType = LoginStatus.NO_USER;
    }
    
    public Password(String firstName, String lastName, String email, 
                   String password, String confirmPassword, LoginStatus userType) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.userType = userType;
    }
    
    public Password(Password password) {
        this.firstName = password.firstName;
        this.lastName = password.lastName;
        this.email = password.email;
        this.password = password.password;
        this.confirmPassword = password.confirmPassword;
        this.userType = password.userType;
    }

    // Getters and Setters
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getConfirmPassword() { return confirmPassword; }
    public void setConfirmPassword(String confirmPassword) { this.confirmPassword = confirmPassword; }
    public LoginStatus getUserType() {return userType;}
    public void setUSerType(LoginStatus userType) {this.userType = userType;}
    @Override
    public boolean matchByIdOrPassword(Object identifier) {
        if (identifier instanceof String) {
            String oldPassword = (String) identifier;
            return this.password.equals(oldPassword);
        }
        return false;
    }

    @Override
    public boolean updateFrom(Object source) {
        if (source instanceof Password) {
            Password update = (Password) source;
            
            // For password updates, we need both new password and confirmation
            if (update.getPassword() != null && !update.getPassword().isEmpty() &&
                update.getConfirmPassword() != null && !update.getConfirmPassword().isEmpty()) {
                
                // Verify new password matches confirmation
                if (update.getPassword().equals(update.getConfirmPassword())) {
                    this.password = update.getPassword();
                    this.confirmPassword = update.getPassword();
                    return true;
                }
            }
            
            // Update other fields if provided
            if (update.getFirstName() != null && !update.getFirstName().isEmpty()) {
                this.firstName = update.getFirstName();
            }
            if (update.getLastName() != null && !update.getLastName().isEmpty()) {
                this.lastName = update.getLastName();
            }
            if (update.getEmail() != null && !update.getEmail().isEmpty()) {
                this.email = update.getEmail();
            }
            
            return true;
        }
        return false;
    }
    
    // Method to verify admin credentials
    public static boolean verifyAdmin(String username, String password) {
        return ADMIN_USERNAME.equals(username) && ADMIN_PASSWORD.equals(password);
    }
    
    @Override
    public String toString() {
        return firstName + "," + lastName + 
               "," + email + "," + password;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Password other = (Password) obj;
        return email.equals(other.email);
    }
}
