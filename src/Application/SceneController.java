package Application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.fxml.FXML;

public class SceneController {
	@FXML
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	@FXML
	private TextField searchTextField;
	@FXML
	private Button submitButton;
	@FXML
	private Label cartAmount;
	@FXML
	private Label userLabel;
	@FXML
	private TextField idTextField;
	@FXML
	private Label idLabel;
	@FXML
	private Label warningLabel;
	@FXML
	private TextField nameTextField ;
	@FXML
	private TextField priceTextField;
	@FXML
	private TextField quantityTextField;;
	@FXML
	private TextArea descriptionTextField;
	@FXML
	private Label nameLabel;
	@FXML
	private Label desLabel;
	@FXML
	private Label priceLabel;
	@FXML
	private Label  quantityLabel;
	@FXML
	private Button addProduct;
	@FXML
	private ImageView productImage;
	@FXML
	private Button cartButton;
	@FXML
	private FlowPane productsFlowPane;
	@FXML private TilePane cartTilePane;
	@FXML private Button orderButton;
	@FXML private Label grandTotalLabel;

	
	
	// Switch to the main product page.
	public void switchToMainPage(MouseEvent event) throws IOException{
		root = FXMLLoader.load(getClass().getResource("MainPageGUI.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		
		//Populate products after loading
		populateMainPage();
		
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
	

	
	
	/**
	 * Handles the addition of a new product to both the linked list and AVL tree,
	 * with data persistence to file. Performs comprehensive validation of all input fields
	 * and provides user feedback through error labels.
	 * 
	 * @param event The button action event that triggered this method
	 * @throws IOException If there's an error during file operations
	 */
	public void addNewProduct(ActionEvent event) throws IOException {
	    // Initialize variables for product data
	    String name;
	    String description;
	    double price = 0.0;
	    int quantity = 0;
	    int productId;
	    
	    // Clear any previous error messages
	    nameLabel.setText("");
	    desLabel.setText("");
	    priceLabel.setText("");
	    quantityLabel.setText("");
	    
	    try {
	        // 1. Load existing products from file or create new file if none exists
	        ProductFileManager fileManager = new ProductFileManager();
	        CLinkedList<Product> productList;
	        
	        try {
	            productList = fileManager.loadProducts();
	        } catch (IOException e) {
	            // If file doesn't exist, create a new empty list
	            System.out.println("No existing product file found. Creating new catalog.");
	            productList = new CLinkedList<>();
	        }
	        
	        // 2. Generate unique product ID
	        productId = Product.generateUniqueProductId(productList);
	        System.out.println("Generated Product ID: " + productId);
	        
	        // 3. Validate and collect product name
	        name = nameTextField.getText().trim();
	        if (name.isEmpty()) {
	            nameLabel.setText("You must enter a product name.");
	            return; // Stop execution if validation fails
	        }
	        
	        // 4. Validate and collect product description
	        description = descriptionTextField.getText().trim();
	        if (description.isEmpty()) {
	            desLabel.setText("Description cannot be empty.");
	            return;
	        }
	        
	        // 5. Validate and collect product price
	        try {
	            price = Double.parseDouble(priceTextField.getText().trim());
	            if (price <= 0) {
	                priceLabel.setText("Price must be greater than 0.");
	                return;
	            }
	        } catch (NumberFormatException e) {
	            priceLabel.setText("Invalid price format. Enter a valid number.");
	            return;
	        }
	        
	        // 6. Validate and collect product quantity
	        try {
	            quantity = Integer.parseInt(quantityTextField.getText().trim());
	            if (quantity <= 0) {
	                quantityLabel.setText("Quantity must be greater than 0.");
	                return;
	            }
	        } catch (NumberFormatException e) {
	            quantityLabel.setText("Invalid quantity. Enter whole numbers only.");
	            return;
	        }
	        
	        // 7. Create new product with validated data
	        Product newProduct = new Product(productId, name, description, price, quantity);
	        
	        // 8. Add product to data structures
	        CatalogManager catalogManager = new CatalogManager();
	        catalogManager.insertProductAtFront(newProduct);
	        
	        // 9. Save updated product list to file
	        try {
	        	
	            fileManager.addProduct(newProduct);
	            catalogManager.saveAVLTree();
	            System.out.println("Product successfully added to file.");
	            
	            // Clear form fields after successful submission
	            nameTextField.clear();
	            descriptionTextField.clear();
	            priceTextField.clear();
	            quantityTextField.clear();
	            
	            // Show success message (you could add a success label to your FXML if needed)
	            nameLabel.setText("Product added successfully!");
	            nameLabel.setTextFill(Color.GREEN);
	            
	        } catch (IOException e) {
	            System.err.println("Error saving product to file: " + e.getMessage());
	            nameLabel.setText("Error saving product. Please try again.");
	            nameLabel.setTextFill(Color.RED);
	        }
	        
	    } catch (Exception e) {
	        System.err.println("Unexpected error submitting the form: " + e.getMessage());
	        nameLabel.setText("An unexpected error occurred. Please try again.");
	        nameLabel.setTextFill(Color.RED);
	    }
	}
	

	
	/**
	 * Handles updating an existing product in both the AVL tree and linked list.
	 * Validates all input fields and only updates fields with valid values.
	 * Provides user feedback through error labels and the warningLabel.
	 * 
	 * @param event The button action event that triggered this method
	 * @throws IOException If there's an error during file operations
	 */
	
	public void updatExistingProduct(ActionEvent event) throws IOException{
		
		String name;
	    String description;
	    double price = 0.0;
	    int quantity = 0;
	    int productId;
	    
	 // Clear any previous error messages
	    nameLabel.setText("");
	    desLabel.setText("");
	    priceLabel.setText("");
	    quantityLabel.setText("");
	    idLabel.setText("");
	    warningLabel.setText("");
	    
	    
	    try {
	        // 1. Validate and collect product ID
	        try {
	            productId = Integer.parseInt(idTextField.getText().trim());
	            if (productId <= 0) {
	                idLabel.setText("Product ID must be greater than 0.");
	                return;
	            }
	        } catch (NumberFormatException e) {
	            idLabel.setText("Invalid Product ID. Enter whole numbers only.");
	            return;
	        }
	        
	        // 2. Load existing catalog data
	        CatalogManager catalogManager = new CatalogManager();
	        ProductFileManager fileManager = new ProductFileManager();
	        
	        // 3. Search for the product in AVL tree
	        Product existingProduct = catalogManager.searchProduct(productId);
	        if (existingProduct == null) {
	            warningLabel.setText("Product is not found in the system");
	            warningLabel.setTextFill(Color.RED);
	            return;
	        }
	        
	        // 4. Create update object with existing values (as fallback)
	        Product updates = new Product(existingProduct);
	        
	        // 5. Validate and collect updated name (only if field is not empty)
	        name = nameTextField.getText().trim();
	        if (!name.isEmpty()) {
	            updates.setName(name);
	        }
	        
	        // 6. Validate and collect updated description (only if field is not empty)
	        description = descriptionTextField.getText().trim();
	        if (!description.isEmpty()) {
	            updates.setDescription(description);
	        }
	        
	        // 7. Validate and collect updated price (only if field is not empty and valid)
	        String priceText = priceTextField.getText().trim();
	        if (!priceText.isEmpty()) {
	            try {
	                price = Double.parseDouble(priceText);
	                if (price <= 0) {
	                    priceLabel.setText("Price must be greater than 0.");
	                    return;
	                }
	                updates.setPrice(price);
	            } catch (NumberFormatException e) {
	                priceLabel.setText("Invalid price format. Enter a valid number.");
	                return;
	            }
	        }
	        
	        // 8. Validate and collect updated quantity (only if field is not empty and valid)
	        String quantityText = quantityTextField.getText().trim();
	        if (!quantityText.isEmpty()) {
	            try {
	                quantity = Integer.parseInt(quantityText);
	                if (quantity <= 0) {
	                    quantityLabel.setText("Quantity must be greater than 0.");
	                    return;
	                }
	                updates.setStockQuantity(quantity);
	            } catch (NumberFormatException e) {
	                quantityLabel.setText("Invalid quantity. Enter whole numbers only.");
	                return;
	            }
	        }
	        
	        // 9. Update the product in both data structures
	        boolean updateSuccess = catalogManager.updateProduct(productId, updates);
	        
	        if (updateSuccess) {
	            // 10. Save updated data to files
	            try {
	                // Save the updated linked list to products.csv
	                fileManager.updateProductInFile(updates);
	                // Save the AVL tree
	                catalogManager.saveAVLTree();
	                
	                // Clear form fields after successful update
	                idTextField.clear();
	                nameTextField.clear();
	                descriptionTextField.clear();
	                priceTextField.clear();
	                quantityTextField.clear();
	                
	                // Show success message
	                warningLabel.setText("Product updated successfully!");
	                warningLabel.setTextFill(Color.GREEN);
	            } catch (IOException e) {
	                System.err.println("Error saving updated product: " + e.getMessage());
	                warningLabel.setText("Error saving updates. Please try again.");
	                warningLabel.setTextFill(Color.RED);
	            }
	        } else {
	            warningLabel.setText("Failed to update product. Please try again.");
	            warningLabel.setTextFill(Color.RED);
	        }
	        
	    } catch (Exception e) {
	        System.err.println("Unexpected error during product update: " + e.getMessage());
	        warningLabel.setText("An unexpected error occurred. Please try again.");
	        warningLabel.setTextFill(Color.RED);
	    }
	    
		
	}
	
	public void deleteExistingProducts(ActionEvent event) {
	    int productId;
	    
	    // Clear previous messages
	    idLabel.setText("");
	    warningLabel.setText("");
	    
	    try {
	        // Validate product ID
	        try {
	            productId = Integer.parseInt(idTextField.getText().trim());
	            if (productId <= 0) {
	                idLabel.setText("Product ID must be greater than 0.");
	                return;
	            }
	        } catch (NumberFormatException e) {
	            idLabel.setText("Invalid Product ID. Enter whole numbers only.");
	            return;
	        }
	        
	        // Load AVL tree from file
	        AVLFileManager avlFileManager = new AVLFileManager();
	        AVL avl = avlFileManager.loadAVLTree();
	        
	        // Check if product exists
	        AVL.Node node = avl.productSearch(productId);
	        if (node == null) {
	            warningLabel.setText("Product not found in system");
	            warningLabel.setTextFill(Color.RED);
	            return;
	        }
	        
	        // Delete from AVL tree
	        avl.deleteProduct(productId);
	        
	        // Save updated AVL tree back to file
	        avlFileManager.saveAVLTree(avl);
	        
	        // Rebuild linked list from updated AVL tree
	        CatalogManager catalogManager = new CatalogManager();
	        catalogManager.setAvl(avl);
	        catalogManager.rebuildLinkedListFromAVL();
	        
	        // Update CSV file
	        new ProductFileManager().saveProducts(catalogManager.getLinkedList());
	        
	        idTextField.clear();
	        warningLabel.setText("Product deleted successfully!");
	        warningLabel.setTextFill(Color.GREEN);
	        
	    } catch (Exception e) {
	        warningLabel.setText("Error during deletion: " + e.getMessage());
	        warningLabel.setTextFill(Color.RED);
	    }
	}
	
    
	public void populateMainPage() {
        try {
            CatalogManager catalogManager = Main.getCatalogManager();
            if (catalogManager == null) {
                System.out.println("CatalogManager not initialized");
                return;
            }

            CLinkedList<Product> productList = catalogManager.getLinkedList();
            if (productList == null || productList.isEmpty()) {
                System.out.println("No products to display");
                return;
            }

            // Clear existing products
            productsFlowPane.getChildren().clear();
            
            productsFlowPane.setHgap(15); // Horizontal space (left/right)
            productsFlowPane.setVgap(10);  // Vertical space (top/bottom)
           

            // Load and display each product
            Application.Node<Product> current = productList.getHead();
            while (current != null) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("ItemPanelGUI.fxml"));
                    VBox itemPanel = loader.load();
                    
                    // Set margins (top, right, bottom, left)
                    VBox.setMargin(itemPanel, new Insets(10, 15, 10, 15)); // 10px top/bottom, 15px left/right
                    
                    // Get the controller for the product card
                    ProductCardController cardController = loader.getController();
                    
                    // Set product data through the controller
                    Product product = current.getData();
                    cardController.setProduct(product);
                    
                 // Set margins (top, right, bottom, left)
                    VBox.setMargin(itemPanel, new Insets(10, 15, 10, 15)); // 10px top/bottom, 15px left/right
                    
                    
                    // Add to flow pane
                    productsFlowPane.getChildren().add(itemPanel);
                    
                } catch (IOException ex) {
                    System.err.println("Error loading product panel: " + ex.getMessage());
                }
                current = current.getNextNode();
            }
        } catch (Exception e) {
            System.err.println("Error populating main page: " + e.getMessage());
            e.printStackTrace();
        }
    }

   


}