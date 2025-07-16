package Application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import Application.PasswordManager;

public class SceneController {
	@FXML
	private Stage stage;
	private Scene scene;
	private Parent root;
	@FXML private TextField firstNameField;
	@FXML private TextField lastNameField;
	@FXML private TextField emailField;
	
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
	
	@FXML
    private void handleRegistration(ActionEvent event) {
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String email = emailField.getText();
        PasswordManager pm = new PasswordManager();
        boolean success = pm.registerCustomer(firstName, lastName, email);
        Alert alert = new Alert(success ? Alert.AlertType.INFORMATION : Alert.AlertType.ERROR);
        alert.setTitle(success ? "Registration Successful" : "Registration Failed");
        alert.setHeaderText(null);
        alert.setContentText(success ? "Registration successful! Please check your email for the OTP." : "Registration failed. Please check your details or try a different email.");
        alert.showAndWait();
    }
	

}
