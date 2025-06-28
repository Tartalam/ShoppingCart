module test12 {
	requires javafx.controls;
	requires javafx.graphics;
	
	opens Constroller to javafx.graphics, javafx.fxml;
}
