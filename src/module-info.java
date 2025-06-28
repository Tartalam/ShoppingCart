module test12 {
	requires javafx.controls;
	requires javafx.graphics;
	
	opens Application to javafx.graphics, javafx.fxml;
}
