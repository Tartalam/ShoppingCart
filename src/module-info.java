module test12 {
	requires javafx.controls;
	requires javafx.graphics;
	requires javafx.fxml;
	
	opens Application to javafx.graphics, javafx.fxml;
}
