package Application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.fxml.FXML;

public class SceneController {
	@FXML
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	@FXML
	private TextField nameTextField;
	@FXML
	private TextField descriptionTextField;
	@FXML
	private TextField priceTextField;
	@FXML
	private TextField quantityTextField;
	
	// Switch to the main product page.
	public void switchToMainPage(MouseEvent event) throws IOException{
		root = FXMLLoader.load(getClass().getResource("MainPageGUI.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		
	}
	
	// Switch to the page to add product.
	public void switchToAddProductPage(ActionEvent event) throws IOException{
		root = FXMLLoader.load(getClass().getResource("AddProductGUI.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	   
		
	
	// Switch to the cart page.
	public void switchToCartPage(MouseEvent event) throws IOException{
		root = FXMLLoader.load(getClass().getResource("CartGUI.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		
	}
	
	//switch to the reset password page.
	public void switchToResetPasswordPage(MouseEvent event) throws IOException{
		root = FXMLLoader.load(getClass().getResource("ChangeOldPasswordGUI.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		
	}
	
	//switch to password page.
	public void switchToPasswordPage(MouseEvent event) throws IOException{
		root = FXMLLoader.load(getClass().getResource("ChangePasswordGUI.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		
	}
	
	//Switch the the Delete Product page.
	public void switchToDeleteProductPage(ActionEvent event) throws IOException{
		root = FXMLLoader.load(getClass().getResource("DeleteProductGUI.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		
	}
	
	//Switch to the Login page.
	public void switchToLoginPage(MouseEvent event) throws IOException{
		root = FXMLLoader.load(getClass().getResource("LoginGUI.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		
	}
	
	//Switch to the order processing page
	public void switchToOrderProccessingPage(MouseEvent event) throws IOException{
		root = FXMLLoader.load(getClass().getResource("OrderProccessingGUI.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		
	}
	
	// switch to product management page.
	public void switchToProductManagementPage(MouseEvent event) throws IOException{
		root = FXMLLoader.load(getClass().getResource("ProductManagementGUI.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		
	}
	
	//switch to product page.
	public void switchToProductPage(MouseEvent event) throws IOException{
		root = FXMLLoader.load(getClass().getResource("ProductPageGUI.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		
	}
	
	//switch to registration page
	public void switchToRegistrationPage(MouseEvent event) throws IOException{
		root = FXMLLoader.load(getClass().getResource("RegistrationGUI.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		
	}
	
	//switch to update product page.
	public void switchToUpdateProductPage(ActionEvent event) throws IOException{
		root = FXMLLoader.load(getClass().getResource("UpdateProductGUI.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		
	}
	
	//Switch to the verification page.
	public void switchToVerificationPage(MouseEvent event) throws IOException{
		root = FXMLLoader.load(getClass().getResource("VerificationGUI.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		
	}
	
	public void switchToForgotPasswordPage(ActionEvent event) throws IOException{
		root = FXMLLoader.load(getClass().getResource("ForgetPasswordGUI.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		
	}
	

}