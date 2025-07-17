package Application;

import java.io.IOException;
import java.util.Map;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
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
	
	@SuppressWarnings("unused")
	private static SceneController mainPageController;
	
	
	@FXML private TextField searchTextField;
	@FXML private Button submitButton;
	@FXML private Label cartAmount;
	@FXML private TextField idTextField;
	@FXML private Label idLabel;
	@FXML private Label warningLabel;
	@FXML private TextField nameTextField ;
	@FXML private TextField priceTextField;
	@FXML private TextField quantityTextField;;
	@FXML private TextArea descriptionTextField;
	@FXML private Label nameLabel;
	@FXML private Label desLabel;
	@FXML private Label priceLabel;
	@FXML private Label  quantityLabel;
	@FXML private Button addProduct;
	@FXML private ImageView productImage;
	@FXML private Button cartButton;
	@FXML private FlowPane productsFlowPane;
	@FXML private TilePane cartTilePane;
	@FXML private Button orderButton;
	@FXML private Label grandTotalLabel;
	@FXML private Label cartAmountLabel;
	@FXML private Label searchLabel;
	@FXML private Label stockLabel;
	@FXML private Spinner<Integer> quantitySpinner;
	@FXML private Label queuePositionLabel;
	@FXML private Button deliverButton;
	@FXML private TextField emailTextField;
	@FXML private PasswordField passwordTextField;
	@FXML private Label userLabel;
	@FXML private PasswordField confirmPasswordTextField;
	@FXML private TextField firstNameTextField;
	@FXML private TextField lastNameTextField;
	@FXML private TextField verificationTextField;


	
	// Switch to the main product page.
	public void switchToMainPage(MouseEvent event) throws IOException {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainPageGUI.fxml"));
        Parent root = loader.load();
        
        SceneController controller = loader.getController();
        // No need to re-inject dependencies since we're using Main.getCatalogManager()
        
        stage.setScene(new Scene(root));
        stage.show();
        
        // Force a refresh of products
        controller.populateMainPage();    // ← Now items will show up!
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
		FXMLLoader loader = new FXMLLoader(getClass().getResource("CartGUI.fxml"));
	    root = loader.load();
	    
	    // Get the controller before showing the scene
	    SceneController controller = loader.getController();
	    
	    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
	    scene = new Scene(root);
	    stage.setScene(scene);
	    stage.show();
	    
	    // Now populate the cart
	    controller.populateCartPage();
		
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
	
	/**
	 * Switches to product page and populates it with the specified product
	 */
	private void switchToProductPageWithProduct(ActionEvent event, Product product) throws IOException {
	    FXMLLoader loader = new FXMLLoader(getClass().getResource("ProductPageGUI.fxml"));
	    root = loader.load();
	    
	    // Get the controller before showing the scene
	    SceneController controller = loader.getController();
	    
	    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
	    scene = new Scene(root);
	    stage.setScene(scene);
	    stage.show();
	    
	    // Populate the product page with the found product
	    controller.populateProductPage(product);
	}
	
	private void switchToProductPageWithProductKey(KeyEvent event, Product product) throws IOException {
	    FXMLLoader loader = new FXMLLoader(getClass().getResource("ProductPageGUI.fxml"));
	    root = loader.load();
	    
	    // Get the controller before showing the scene
	    SceneController controller = loader.getController();
	    
	    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
	    scene = new Scene(root);
	    stage.setScene(scene);
	    stage.show();
	    
	    // Populate the product page with the found product
	    controller.populateProductPage(product);
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
	public void switchToEmailedPasswordPage(ActionEvent event) throws IOException{
		root = FXMLLoader.load(getClass().getResource("EmailedPasswordGUI.fxml"));
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
	        CatalogManager catalogManager = Main.getCatalogManager();
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
	        CatalogManager catalogManager = Main.getCatalogManager();
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
	
	public void deleteExistingProducts(ActionEvent event) throws IOException {
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
	        CatalogManager catalogManager = Main.getCatalogManager();
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
	
    
	/**
	 * Populates the main product display page with product cards loaded from the catalog.
	 * This method:
	 * 1. Clears existing products from the view
	 * 2. Sets up the layout properties
	 * 3. Loads each product from the catalog into individual cards
	 * 4. Sets up event handlers for the "Add to Cart" buttons
	 * 5. Handles errors gracefully at each step
	 */
	@SuppressWarnings("unused")
	public void populateMainPage() {
		
		updateUIForCurrentUser();
		 System.out.println("[DEBUG] populateMainPage() called");
		    
		    if (productsFlowPane == null) {
		        System.err.println("ERROR: productsFlowPane is null!");
		        return;
		    }
		    productsFlowPane.getChildren().clear();

		    // Always get the latest instances from Main
		    CatalogManager catalogManager = Main.getCatalogManager();
		   
			ShoppingCart shoppingCart = Main.getShoppingCart();  // ← Added this line

		    if (catalogManager == null || catalogManager.getLinkedList() == null) {
		        System.err.println("ERROR: Catalog data not loaded!");
		        showErrorAlert("Product catalog not available.");
		        return;
		    }

		    CLinkedList<Product> productList = catalogManager.getLinkedList();
		    if (productList.isEmpty()) {
		        System.out.println("INFO: No products in catalog.");
		        return;
		    }

		    // Configure layout and load product cards
		    productsFlowPane.setHgap(20);
		    productsFlowPane.setVgap(20);
		    productsFlowPane.setPadding(new Insets(15));

		    for (Application.Node<Product> current = productList.getHead(); 
		         current != null; 
		         current = current.getNextNode()) {
		        
		        Product product = current.getData();
		        if (product == null) continue;

		        try {
		            FXMLLoader loader = new FXMLLoader(
		                getClass().getResource("ItemPanelGUI.fxml")
		            );
		            VBox productCard = loader.load();
		            ProductCardController cardController = loader.getController();
		            cardController.setProduct(product);

		            // Set up "Add to Cart" button (uses Main.getShoppingCart())
		            Button cartButton = (Button) productCard.lookup("#cartButton");
		            if (cartButton != null) {
		                cartButton.setOnAction(e -> {
		                    if (Main.getShoppingCart().addToCart(product.getProductId(), 1)) {  // ← Updated
		                        updateCartAmountLabel();
		                        showSuccessAlert("Added to cart: " + product.getName());
		                    }
		                });
		            }

		            productsFlowPane.getChildren().add(productCard);
		        } catch (IOException e) {
		            System.err.println("Failed to load product card: " + product.getName());
		        }
		    }
		    
		    updateCartAmountLabel();
    }
	

	public void populateCartPage(){
		 System.out.println("[DEBUG] populateCartPage() called");
		    
		    // Clear existing items
		    if (cartTilePane == null) {
		        System.err.println("ERROR: cartTilePane is null!");
		        return;
		    }
		    cartTilePane.getChildren().clear();

		    // Get shopping cart instance
		    ShoppingCart shoppingCart = Main.getShoppingCart();
		    CatalogManager catalogManager = Main.getCatalogManager();
		    
		    if (shoppingCart == null || catalogManager == null) {
		        System.err.println("ERROR: Shopping cart or catalog not initialized!");
		        showErrorAlert("Shopping system not available.");
		        return;
		    }

		    // Get cart contents and populate the view
		    Map<Integer, Integer> cartContents = shoppingCart.getCartContents();
		    if (cartContents.isEmpty()) {
		        System.out.println("INFO: Cart is empty.");
		        return;
		    }

		    // Configure layout
		    cartTilePane.setHgap(20);
		    cartTilePane.setVgap(20);
		    cartTilePane.setPadding(new Insets(15));

		    // Create a card for each item in the cart
		    for (Map.Entry<Integer, Integer> entry : cartContents.entrySet()) {
		        int productId = entry.getKey();
		        int quantity = entry.getValue();
		        
		        Product product = catalogManager.searchProduct(productId);
		        if (product == null) {
		            System.err.println("WARNING: Product not found in catalog: " + productId);
		            continue;
		        }

		        try {
		            FXMLLoader loader = new FXMLLoader(
		                getClass().getResource("CartItemPanelGUI.fxml")
		            );
		            HBox cartItemCard = loader.load();
		            
		            // Set up the controller with product data
		            CartCardController cardController = loader.getController();
		            cardController.setCartItem(product, quantity, this::updateGrandTotal);
		            
		            cartTilePane.getChildren().add(cartItemCard);
		        } catch (IOException e) {
		            System.err.println("Failed to load cart item card: " + product.getName());
		            showErrorAlert("Failed to display cart item: " + product.getName());
		        }
		    }
		    
		    // Update the grand total display
		    updateGrandTotal();
		    //0+87++8updateCartAmountLabel();
    }
	
	/**
	 * Handles product search functionality by either product ID or name.
	 * Searches the AVL tree and switches to product page if found.
	 * 
	 * @param event The button action event that triggered this method
	 * @throws IOException If there's an error during scene switching
	 */
	public void ProductSearch(ActionEvent event) throws IOException {
	    String searchTerm = searchTextField.getText().trim();
	    
	    // Clear previous messages
	    searchLabel.setText("");
	    searchLabel.setTextFill(Color.BLACK);
	    
	    if (searchTerm.isEmpty()) {
	        searchLabel.setText("Please enter a product name or ID to begin search");
	        searchLabel.setTextFill(Color.RED);
	        return;
	    }
	    
	    try {
	        CatalogManager catalogManager = Main.getCatalogManager();
	        if (catalogManager == null) {
	            throw new IllegalStateException("Catalog not initialized");
	        }
	        
	        Product foundProduct = null;
	        
	        // Try searching by ID first
	        try {
	            int productId = Integer.parseInt(searchTerm);
	            foundProduct = catalogManager.searchProduct(productId);
	        } catch (NumberFormatException e) {
	            // If not a number, search by name
	            foundProduct = searchProductByName(searchTerm);
	        }
	        
	        if (foundProduct != null) {
	            // Switch to product page with the found product
	            switchToProductPageWithProduct(event, foundProduct);
	        } else {
	            searchLabel.setText("Product not found: " + searchTerm);
	            searchLabel.setTextFill(Color.RED);
	        }
	    } catch (Exception e) {
	        searchLabel.setText("Search error: " + e.getMessage());
	        searchLabel.setTextFill(Color.RED);
	        System.err.println("Search error: " + e.getMessage());
	    }
	}
	
	/**
	 * Handles product search when Enter key is pressed in the search field
	 * @param event The KeyEvent that triggered this method
	 * @throws IOException If there's an error during scene switching
	 */
	public void ProductSearchKey(KeyEvent event) throws IOException {
		
	    // Check if the pressed key was Enter
	    if (event.getCode() != KeyCode.ENTER) {
	        return;
	    }
		
	    String searchTerm = searchTextField.getText().trim();
	    
	    // Clear previous messages
	    searchLabel.setText("");
	    searchLabel.setTextFill(Color.BLACK);
	    
	    if (searchTerm.isEmpty()) {
	        searchLabel.setText("Please enter a product name or ID to begin search");
	        searchLabel.setTextFill(Color.RED);
	        return;
	    }
	    
	    try {
	        CatalogManager catalogManager = Main.getCatalogManager();
	        if (catalogManager == null) {
	            throw new IllegalStateException("Catalog not initialized");
	        }
	        
	        Product foundProduct = null;
	        
	        // Try searching by ID first
	        try {
	            int productId = Integer.parseInt(searchTerm);
	            foundProduct = catalogManager.searchProduct(productId);
	        } catch (NumberFormatException e) {
	            // If not a number, search by name
	            foundProduct = searchProductByName(searchTerm);
	        }
	        
	        if (foundProduct != null) {
	            // Switch to product page with the found product
	            switchToProductPageWithProductKey(event, foundProduct);
	        } else {
	            searchLabel.setText("Product not found: " + searchTerm);
	            searchLabel.setTextFill(Color.RED);
	        }
	    } catch (Exception e) {
	        searchLabel.setText("Search error: " + e.getMessage());
	        searchLabel.setTextFill(Color.RED);
	        System.err.println("Search error: " + e.getMessage());
	    }
	}
	
	/**
	 * Searches for a product by name in the AVL tree
	 * @param name The product name to search for
	 * @return The found Product or null if not found
	 */
	private Product searchProductByName(String name) {
	    CatalogManager catalogManager = Main.getCatalogManager();
	    if (catalogManager == null || catalogManager.getAvl() == null) {
	        return null;
	    }
	    
	    // Perform in-order traversal to find product by name
	    return searchProductByNameHelper(catalogManager.getAvl().getHead(), name.toLowerCase());
	}
	
	/**
	 * Populates the product page with information from the specified product
	 * @param product The product to display
	 */
	public void populateProductPage(Product product) {
		updateUIForCurrentUser();
	    if (product == null) {
	        showErrorAlert("Product information not available");
	        return;
	    }
	    
	    try {
	        // Set basic product information
	        if (nameLabel != null) nameLabel.setText(product.getName());
	        if (desLabel != null) desLabel.setText(product.getDescription());
	        if (priceLabel != null) priceLabel.setText(String.format("$%.2f", product.getPrice()));
	        updateCartAmountLabel();
	        // Configure stock label
	        updateStockLabel(product.getStockQuantity());
	        
	        // Configure quantity spinner
	        configureQuantitySpinner(product.getStockQuantity());
	        
	     // Set up the Add to Cart button
	        if (cartButton != null) {
	            cartButton.setOnAction(e -> {
	                int quantity = quantitySpinner.getValue();
	                ShoppingCart cart = Main.getShoppingCart();
	                
	                // Check if product already exists in cart
	                boolean productExistsInCart = cart.getCartContents().containsKey(product.getProductId());
	                
	                if (cart.addToCart(product.getProductId(), quantity)) {
	                    updateCartAmountLabel();
	                    showSuccessAlert("Added " + quantity + " " + product.getName() + "(s) to cart");
	                    
	                    // If product was already in cart, we need to force refresh the cart page
	                    if (productExistsInCart) {
	                        // This ensures the spinner in cart reflects the new total quantity
	                        cart.updateQuantity(product.getProductId(), 
	                            cart.getCartContents().get(product.getProductId()));
	                    }
	                } else {
	                    showErrorAlert("Failed to add to cart. Check available stock.");
	                }
	            });
	        }
	        
	    } catch (Exception e) {
	        showErrorAlert("Error displaying product: " + e.getMessage());
	        System.err.println("Error populating product page: " + e.getMessage());
	    }
	}
	
	public void OrderProducts(ActionEvent event) throws IOException {
		 ShoppingCart cart = Main.getShoppingCart();
		    Queue orderQueue = Main.getOrderQueue();
		    CatalogManager catalogManager = Main.getCatalogManager();
		    
		    // Check if cart is empty
		    if (cart.isEmpty()) {
		        showErrorAlert("Your cart is empty. Please add items before placing an order.");
		        return;
		    }
		    
		    try {
		        // Generate a single order number for this order instance
		        OrderNumber orderNumber = new OrderNumber();
		        int generatedOrderNum = orderNumber.generateOrderNumber();
		        
		        // Process all items in cart
		        Map<Integer, Integer> cartContents = cart.getCartContents();
		        for (Map.Entry<Integer, Integer> entry : cartContents.entrySet()) {
		            int productId = entry.getKey();
		            int quantity = entry.getValue();
		            
		            // Update product stock in catalog (quantity already deducted by ShoppingCart)
		            Product product = catalogManager.searchProduct(productId);
		            if (product != null) {
		                // Update the product in the file
		                new ProductFileManager().updateProductInFile(product);
		            }
		        }
		        
		        // Add the single order number to queue
		        orderQueue.add(generatedOrderNum);
		        
		        // Save the queue to file
		        orderQueue.saveToFile();
		        
		        // Clear the cart
		        cart.clearCart();
		        
		        // Update UI
		        updateGrandTotal();
		        updateCartAmountLabel();
		        
		        // Show success message with the order number
		        showSuccessAlert("Order #" + generatedOrderNum + " placed successfully!");
		        
		        // Refresh the cart view
		        populateCartPage();
		        
		    } catch (Exception e) {
		        showErrorAlert("Failed to place order: " + e.getMessage());
		        System.err.println("Error placing order: " + e.getMessage());
		        e.printStackTrace();
		    }
		
	}
	
	/**
	 * Handles order position lookup when searching for an order number.
	 * Validates input, searches the queue, and displays the order's position.
	 * Disables/enables the delivery button based on position.
	 * 
	 * @param event The action event that triggered this method
	 */
	public void OrderPosition(ActionEvent event) {
	    String searchText = searchTextField.getText().trim();
	    searchLabel.setText(""); // Clear previous messages
	    searchLabel.setTextFill(Color.BLACK);
	    
	    try {
	        // Validate input
	        if (searchText.isEmpty()) {
	            throw new IllegalArgumentException("Please enter an order number");
	        }
	        
	        int orderNumber;
	        try {
	            orderNumber = Integer.parseInt(searchText);
	        } catch (NumberFormatException e) {
	            throw new IllegalArgumentException("Order number must be a number");
	        }
	        
	        if (orderNumber <= 0) {
	            throw new IllegalArgumentException("Order number must be positive");
	        }
	        
	        // Get queue instance
	        Queue orderQueue = Main.getOrderQueue();
	        if (orderQueue == null) {
	            throw new IllegalStateException("Order queue not initialized");
	        }
	        
	        // Find position in queue
	        int position = orderQueue.findOrderNumberPosition(orderNumber);
	        
	        if (position == -1) {
	            throw new IllegalArgumentException("Order #" + orderNumber + " not found in queue");
	        }
	        
	        // Update UI
	        queuePositionLabel.setText(String.valueOf(position + 1)); // Show 1-based position
	        
	        // Enable/disable delivery button
	        deliverButton.setDisable(position != 0);
	        
	    } catch (IllegalArgumentException e) {
	        searchLabel.setText(e.getMessage());
	        searchLabel.setTextFill(Color.RED);
	        queuePositionLabel.setText("");
	        deliverButton.setDisable(true);
	    } catch (Exception e) {
	        searchLabel.setText("System error processing order");
	        searchLabel.setTextFill(Color.RED);
	        System.err.println("Error processing order position: " + e.getMessage());
	        e.printStackTrace();
	    }
	}
	
	/**
	 * Handles order delivery when the delivery button is pressed.
	 * Dequeues the order from the queue and updates the queue file.
	 * Only works when the order is at position 1 (front of queue).
	 * 
	 * @param event The action event that triggered this method
	 */
	public void OrderDelivery(ActionEvent event) {
	    try {
	        Queue orderQueue = Main.getOrderQueue();
	        if (orderQueue == null) {
	            throw new IllegalStateException("Order queue not initialized");
	        }
	        
	        // Verify there's an order to deliver
	        if (orderQueue.isEmpty()) {
	            throw new IllegalStateException("No orders in queue");
	        }
	        
	        // Get the order number at front of queue
	        OrderNumber deliveredOrder = orderQueue.dequeue();
	        
	        // Save updated queue to file
	        orderQueue.saveToFile();
	        
	        // Update UI
	        searchLabel.setText("Order #" + deliveredOrder.getOrderNumber() + " delivered successfully");
	        searchLabel.setTextFill(Color.GREEN);
	        queuePositionLabel.setText("");
	        searchTextField.clear();
	        deliverButton.setDisable(true);
	        
	    } catch (IllegalStateException e) {
	        searchLabel.setText(e.getMessage());
	        searchLabel.setTextFill(Color.RED);
	    } catch (IOException e) {
	        searchLabel.setText("Failed to update order queue file");
	        searchLabel.setTextFill(Color.RED);
	        System.err.println("Error saving queue file: " + e.getMessage());
	        e.printStackTrace();
	    } catch (Exception e) {
	        searchLabel.setText("System error delivering order");
	        searchLabel.setTextFill(Color.RED);
	        System.err.println("Error delivering order: " + e.getMessage());
	        e.printStackTrace();
	    }
	}
	
	/**
	 * Handles user login functionality by:
	 * 1. Validating admin credentials against hardcoded values
	 * 2. Searching user file for regular user credentials
	 * 3. Setting appropriate user state (ADMIN, USER, NO_USER)
	 * 
	 * @param event The action event that triggered this method
	 * @throws IOException If there's an error during scene switching
	 */
	public void UserLogin(MouseEvent event) throws IOException {
		 // Get input values from text fields
	    String email = emailTextField.getText().trim();
	    String password = passwordTextField.getText().trim();
	    
	    try {
	        // Validate input fields
	        if (email.isEmpty() || password.isEmpty()) {
	            showErrorAlert("Please enter both email and password");
	            return;
	        }
	        
	        // Use PasswordManager for authentication
	        PasswordManager passwordManager = new PasswordManager();
	        Password authenticatedUser = passwordManager.login(email, password);
	        
	        if (authenticatedUser == null) {
	            showErrorAlert("Invalid email or password");
	            return;
	        }
	        
	        // Set the current user
	        Main.setCurrentUser(authenticatedUser);
	        
	        // Update UI based on user type
	        updateUIForCurrentUser();
	        
	        // Switch to main page after successful login
	        switchToMainPage(event);
	        
	    } catch (Exception e) {
	        showErrorAlert("Login error: " + e.getMessage());
	        System.err.println("Login error: " + e.getMessage());
	        e.printStackTrace();
	    }
	}

	/**
	 * Updates all UI elements based on current user state (ADMIN, USER, NO_USER)
	 */
	public void updateUIForCurrentUser() {
	    Password user = Main.getCurrentUser();
	    
	    if (user == null) {
	        handleNoUserState();
	        return;
	    }
	    
	    switch (user.getUserType()) {
	        case ADMIN:
	            if (userLabel != null) {
	                userLabel.setText("Admin");
	                userLabel.setStyle("-fx-text-fill: #FF0000;"); // Red color for admin
	            }
	            enableAdminFunctionality(true);
	            break;
	            
	        case USER:
	            if (userLabel != null) {
	                userLabel.setText(user.getFirstName());
	                userLabel.setStyle("-fx-text-fill: #0000FF;"); // Blue color for regular user
	            }
	            enableUserFunctionality(true);
	            break;
	            
	        case NO_USER:
	        default:
	            handleNoUserState();
	            break;
	    }
	}

	/**
	 * Handles UI updates for no user state (logged out)
	 */
	private void handleNoUserState() {
	    if (userLabel != null) {
	        userLabel.setText("Guest");
	        userLabel.setStyle("-fx-text-fill: #000000;"); // Black color for no user
	    }
	    enableUserFunctionality(false);
	    enableAdminFunctionality(false);
	}

	/**
	 * Enables or disables user functionality based on login state
	 * @param enabled true to enable user features, false to disable
	 */
	private void enableUserFunctionality(boolean enabled) {
	    // Enable/disable cart-related functionality
	    if (cartButton != null) {
	        cartButton.setDisable(!enabled);
	    }
	    if (orderButton != null) {
	        orderButton.setDisable(!enabled);
	    }
	    
	    // Add other user-specific UI controls here
	}

	/**
	 * Enables or disables admin functionality based on login state
	 * @param enabled true to enable admin features, false to disable
	 */
	private void enableAdminFunctionality(boolean enabled) {
	    // Enable/disable admin-specific buttons
	    if (addProduct != null) {
	        addProduct.setDisable(!enabled);
	    }
	    
	    // Add other admin-specific UI controls here
	}

	/**
	 * Handles user logout functionality
	 * @param event The mouse event that triggered logout
	 * @throws IOException If there's an error during scene switching
	 */
	public void handleLogout(MouseEvent event) throws IOException {
	    // Clear current user
	    Main.setCurrentUser(null);
	    
	    // Update UI to no user state
	    updateUIForCurrentUser();
	    
	    // Clear any sensitive fields
	    if (emailTextField != null) emailTextField.clear();
	    if (passwordTextField != null) passwordTextField.clear();
	    
	    // Show logout confirmation
	    showSuccessAlert("You have been logged out successfully");
	    
	    // Switch to login page
	    switchToLoginPage(event);
	}
	
//	private Password tempUser; // Temporary storage for user data during registration
//	private String tempEmail;
	
	public void UserRegistration(MouseEvent event) throws IOException{
		// Get input values
		String firstName = firstNameTextField.getText().trim();
	    String lastName = lastNameTextField.getText().trim();
	    String email = emailTextField.getText().trim();

	    // Validation
	    if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty()) {
	        showErrorAlert("All fields are required");
	        return;
	    }

	    if (!email.contains("@") || !email.contains(".")) {
	        showErrorAlert("Please enter a valid email address");
	        return;
	    }

	    // Check if user exists
	    if (UserFileManager.loadUser(email) != null) {
	        showErrorAlert("User with this email already exists");
	        return;
	    }
	    
	  PasswordManager passwordManager = new PasswordManager();
	  
	  if(passwordManager.registerCustomer(firstName, lastName, email)) {
		  switchToVerificationPage(event);
		
	  }else {
		  showErrorAlert("Failed to send verification code. please try again.");
	  }

//	    // Create temporary user
//	    Password tempUser = new Password(
//	        firstName, lastName, email, 
//	        "", "", Password.LoginStatus.USER, "", ""
//	    );
//	    
//	    // Store in Main instead of saving to file
//	    Main.setRegistrationInProgress(tempUser);
//
//	    // Generate and send OTP
//	    String otp = PasswordUtility.generateOTP();
//	    //PasswordManager passwordManager = new PasswordManager();
//	    
//	    if (Email.sendOTP(email, otp)) {
//	        UserFileManager.saveOTP(email, otp);
//	        switchToVerificationPage(event);
//	    } else {
//	        showErrorAlert("Failed to send verification code. Please try again.");
//	    }
//		
	}
	
	public void userVerification(MouseEvent event) throws IOException{
		Password tempUser = Main.getRegistrationInProgress();
	    
	    // Check session timeout (10 minutes)
	    if (System.currentTimeMillis() - Main.getRegistrationStartTime() > 600000) {
	        showErrorAlert("Session expired. Please start again.");
	        Main.setRegistrationInProgress(null);
	        switchToRegistrationPage(event);
	        return;
	    }

	    if (tempUser == null) {
	        showErrorAlert("Registration session expired. Please start again.");
	        switchToRegistrationPage(event);
	        return;
	    }

	    String enteredOTP = verificationTextField.getText().trim();
	    if (enteredOTP.isEmpty()) {
	        showErrorAlert("Please enter the verification code");
	        return;
	    }

	    // Verify OTP
	    if (UserFileManager.verifyOTP(tempUser.getEmail(), enteredOTP)) {
	        switchToPasswordPage(event);
	    } else {
	        showErrorAlert("Invalid verification code. Please try again.");
	    }
		
	}
	
	public void SetPassword(MouseEvent event) throws IOException {
	    Password tempUser = Main.getRegistrationInProgress();
	    
	    // Check session timeout
	    if (System.currentTimeMillis() - Main.getRegistrationStartTime() > 600000) {
	        showErrorAlert("Session expired. Please start again.");
	        Main.setRegistrationInProgress(null);
	        switchToRegistrationPage(event);
	        return;
	    }

	    if (tempUser == null) {
	        showErrorAlert("Registration session expired. Please start again.");
	        switchToRegistrationPage(event);
	        return;
	    }

	    String password = passwordTextField.getText();
	    String confirmPassword = confirmPasswordTextField.getText();

	    // Validate password
	    if (password.isEmpty() || confirmPassword.isEmpty()) {
	        showErrorAlert("Both password fields are required");
	        return;
	    }

	    if (!password.equals(confirmPassword)) {
	        showErrorAlert("Passwords do not match");
	        return;
	    }

	    if (!PasswordUtility.validatePasswordComplexity(password)) {
	        showErrorAlert("Password must be at least 8 characters with uppercase, lowercase, number, and special character");
	        return;
	    }

	    // Set password and save user
	    tempUser.setHashedPassword(PasswordUtility.hashPassword(password));
	    
	    // Use update instead of save to prevent duplicates
	    if(UserFileManager.saveUser(tempUser)) {
	    	showSuccessAlert("Registration Complete! user saved to the system.");
	    }else {
	    	showErrorAlert("Registration Failed!! due some error user file was to save tot he system.");
	    }

	    // Clear temporary data
	    Main.setRegistrationInProgress(null);

	    // Show success and go to login page
	    showSuccessAlert("Registration successful! Please login with your new account.");
	    switchToLoginPage(event);
	}
	
	
	
	/**
	 * Configures the quantity spinner with proper limits
	 * @param maxQuantity The maximum available quantity (stock)
	 */
	@SuppressWarnings("unused")
	private void configureQuantitySpinner(int maxQuantity) {
	    if (quantitySpinner == null) return;
	    
	    // Set spinner to allow values between 1 and either maxQuantity or 100, whichever is smaller
	    int upperLimit = Math.min(maxQuantity, 100);
	    SpinnerValueFactory.IntegerSpinnerValueFactory factory = 
	        new SpinnerValueFactory.IntegerSpinnerValueFactory(1, upperLimit, 1);
	    
	    quantitySpinner.setValueFactory(factory);
	    
	    // Add listener to handle stock changes
	    quantitySpinner.valueProperty().addListener((obs, oldValue, newValue) -> {
	        if (newValue == null) return;
	        
	        // Ensure we don't exceed available stock
	        if (newValue > maxQuantity) {
	            quantitySpinner.getValueFactory().setValue(maxQuantity);
	        }
	        
	        // Update stock display
	        //updateStockLabel(maxQuantity - newValue);
	    });
	}
	
	/**
	 * Updates the stock label with appropriate message and color
	 * @param stockQuantity The current stock quantity
	 */
	private void updateStockLabel(int stockQuantity) {
	    if (stockLabel == null) return;
	    
	    if (stockQuantity < 20) {
	        stockLabel.setText("Low stock! Only " + stockQuantity + " remaining");
	        stockLabel.setTextFill(Color.RED);
	    } else {
	        stockLabel.setText("In stock: " + stockQuantity + " available");
	        stockLabel.setTextFill(Color.BLUE);
	    }
	}
	
	/**
	 * Recursive helper method for name search
	 */
	private Product searchProductByNameHelper(AVL.Node node, String searchName) {
	    if (node == null) {
	        return null;
	    }
	    
	    // Search left subtree first
	    Product leftResult = searchProductByNameHelper(node.getLeft(), searchName);
	    if (leftResult != null) {
	        return leftResult;
	    }
	    
	    // Check current node
	    if (node.getData().getName().toLowerCase().contains(searchName)) {
	        return node.getData();
	    }
	    
	    // Search right subtree
	    return searchProductByNameHelper(node.getRight(), searchName);
	}
	
	private void updateGrandTotal() {
        if (grandTotalLabel != null) {
            grandTotalLabel.setText(String.format("$%.2f", Main.getShoppingCart().getTotalCost()));
        }
    }
	
	 private void updateCartAmountLabel() {
	        if (cartAmountLabel != null) {
	            cartAmountLabel.setText(String.valueOf(Main.getShoppingCart().getTotalItemCount()));
	        }
	    }
	 
	 private void showSuccessAlert(String message) {
		    Alert alert = new Alert(Alert.AlertType.INFORMATION);
		    alert.setTitle("Success");
		    alert.setHeaderText(null);
		    alert.setContentText(message);
		    alert.showAndWait();
		}
	 
	 private void showErrorAlert(String message) {
		    Alert alert = new Alert(Alert.AlertType.ERROR);
		    alert.setTitle("Error");
		    alert.setHeaderText(null);
		    alert.setContentText(message);
		    alert.showAndWait();
		}


}