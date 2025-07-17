package Application;
import java.io.Serializable;

/**
 * Represents an operation performed on a shopping cart (add/remove product).
 * Implements Serializable to support saving/loading cart operations.
 */
public class CartOperation implements Serializable{
    private static final long serialVersionUID = 1L;

    // Enum defining possible operation types
    public enum OperationType { ADD, REMOVE}
    
    // Operation details
    public OperationType type;
    private int productId;
    private int quantity;

    /**
     * Constructs a new CartOperation.
     * @param type Type of operation (ADD/REMOVE)
     * @param productId ID of the product affected
     * @param quantity Quantity involved in the operation
     * @throws IllegalArgumentException if productId is negative or quantity is non-positive
     */
    public CartOperation(OperationType type, int productId, int quantity) {
        if (productId < 0) {
            throw new IllegalArgumentException("Product ID cannot be negative");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        
        this.type = type;
        this.productId = productId;
        this.quantity = quantity;
    }

    // Getters with documentation
    public OperationType getType() { return type; }
    public int getProductId() { return productId; }
    public int getQuantity() { return quantity; }
}