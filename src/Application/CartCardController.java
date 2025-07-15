package Application;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class CartCardController {
	
	@FXML private Label nameLabel;
	@FXML private Label desLabel;
	@FXML private ImageView productImage;
	@FXML private Spinner<Integer> quantitySpinner;
	@FXML private Label totalLabel;
	@FXML private Hyperlink removeHyperlink;
	private Product product;
    private Runnable grandTotalCallback;

    public void setCartItem(Product product, int quantity, Runnable grandTotalCallback) {
    	// Validate parameters first
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }
        this.product = product;
        this.grandTotalCallback = grandTotalCallback;
        
        // Initialize UI components
        initializeUIComponents(quantity);
        
        
        // Remove hyperlink action
        removeHyperlink.setOnAction(e -> removeItem());
    }
    
    private void updateTotal(int quantity) {
        if (totalLabel != null && product != null) {
            double total = product.getPrice() * quantity;
            totalLabel.setText(String.format("$%.2f", total));
        }
    }
    
    private void removeItem() {
        try {
            Main.getShoppingCart().removeFromCart(product.getProductId());
            // Remove this card from UI
            ((Pane) removeHyperlink.getParent().getParent().getParent().getParent()).getChildren().remove(0);
            grandTotalCallback.run();
        } catch (Exception e) {
            showError("Removal failed: " + e.getMessage());
        }
    }
    
    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Cart Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    private void initializeUIComponents(int initialQuantity) {
        // Set basic fields first
        if (nameLabel != null) nameLabel.setText(product.getName());
        if (desLabel != null) desLabel.setText(product.getDescription());
        updateTotal(initialQuantity);
        
        // Initialize image
        initializeProductImage();
        
        // Configure spinner last
        configureSpinner(initialQuantity);
    }
    
    private void initializeProductImage() {
        if (productImage != null) {
            try {
                Image image = new Image(getClass().getResourceAsStream("../images/shopping-cart.png"));
                productImage.setImage(image);
            } catch (Exception e) {
                System.err.println("Error loading product image: " + e.getMessage());
                // Optionally set a default/placeholder image
            }
        }
    }
    
    private void configureSpinner(int initialQuantity) {
        if (quantitySpinner == null) return;
        
        SpinnerValueFactory.IntegerSpinnerValueFactory factory = 
            new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, initialQuantity);
        quantitySpinner.setValueFactory(factory);
        
        quantitySpinner.valueProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue == null || product == null) return;
            
            try {
                ShoppingCart cart = Main.getShoppingCart();
                if (cart == null) {
                    throw new IllegalStateException("Shopping cart not initialized");
                }
                
                if (!cart.updateQuantity(product.getProductId(), newValue)) {
                    throw new Exception("Insufficient stock available");
                }
                
                updateTotal(newValue);
                
                if (grandTotalCallback != null) {
                    grandTotalCallback.run();
                }
            } catch (Exception e) {
                showError("Error updating quantity: " + e.getMessage());
                Platform.runLater(() -> quantitySpinner.getValueFactory().setValue(oldValue));
            }
        });
    }
	

}
