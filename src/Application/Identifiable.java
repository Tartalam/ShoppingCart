package Application;

public interface Identifiable {
	
    // For Products: match by ID
    // For Passwords: match by old password
    boolean matchByIdOrPassword(Object identifier);
    
    // Update fields from source object
    // For Products: update non-empty fields
    // For Passwords: update password if new password matches confirmation
    boolean updateFrom(Object source);

}
