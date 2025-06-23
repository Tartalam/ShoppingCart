import java.io.Serializable;

public class CartOperation implements Serializable{
    private static final long serialVersionUID = 1L;

    public enum OperationType { ADD, REMOVE}
    
    public OperationType type;
    private int productId;
    private int quantity;

    public CartOperation( OperationType type, int productId, int quantity){
        this.type = type;
        this.productId = productId;
        this.quantity = quantity;
    }

    public OperationType getType() { return type; }
    public int getProductId() { return productId; }
    public int getQuantity() { return quantity; }
}
