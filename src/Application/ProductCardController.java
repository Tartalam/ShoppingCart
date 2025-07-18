// Author: Jahmari Harrison
// ID: 2304204
package Application;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ProductCardController {
    @FXML private Label nameLabel;
    @FXML private Label desLabel;
    @FXML private Label priceLabel;
    @FXML private ImageView productImage;

    public void setProduct(Product product) {
        nameLabel.setText(product.getName());
        desLabel.setText(product.getDescription());
        priceLabel.setText(String.format("$%.2f", product.getPrice()));
        
//        // Set fixed image as specified
//        try {
//            productImage.setImage(new Image(getClass().getResourceAsStream("../images/shopping-cart.png")));
//        } catch (Exception e) {
//            System.err.println("Error loading product image: " + e.getMessage());
//            // Optionally set a placeholder image
//        }
    }
}