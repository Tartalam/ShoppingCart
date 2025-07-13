package Application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class ProductManagementController extends SceneController {
	
	@FXML
	private TextField nameTextField;
	@FXML
	private TextField descriptionTextField;
	@FXML
	private TextField priceTextField;
	@FXML
	private TextField quantityTextField;
	
	//Inherited methods to switch between scenes
	public void mainPage(MouseEvent event) throws IOException{
		switchToMainPage(event);
	}
	
	public void updateProduct(ActionEvent event) throws IOException{
		switchToUpdateProductPage(event);
	}
	
	public void deleteProduct(ActionEvent event) throws IOException{
		switchToDeleteProductPage(event);
	}
	
	


}
