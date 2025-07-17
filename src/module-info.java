module test12 {
	requires javafx.controls;
	requires javafx.graphics;
	requires javafx.fxml;
	requires javafx.base;
	requires jakarta.mail;
	
	opens Application to javafx.graphics, javafx.fxml;
}
