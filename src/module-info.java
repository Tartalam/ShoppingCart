module test12 {
	requires javafx.controls;
	requires javafx.graphics;
	requires javafx.fxml;
	requires javafx.base;
	requires java.mail;
	
	opens Application to javafx.graphics, javafx.fxml;
}
