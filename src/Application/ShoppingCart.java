package Application;

import java.io.*;
import java.util.*;

/**
 * ShoppingCart class manages a user's shopping cart with undo functionality.
 * Maintains synchronization with product catalog and persists cart state.
 */
public class ShoppingCart {
    private Stack<CartOperation> operationStack = new Stack<>();
    private Map<Integer, Integer> cart = new HashMap<>(); // productId -> quantity
    private Map<Integer, Product> catalog;
    private static final String CART_FILE = "cart.ser";
    private static final String STACK_FILE = "stack.ser";

    /**
     * Constructor initializes the shopping cart with product catalog reference.
     * @param catalog Map of available products (productId -> Product)
     */
    public ShoppingCart(Map<Integer, Product> catalog) {
        this.catalog = catalog;
        loadFromFile();
    }

    /**
     * Adds a product to the shopping cart.
     * @param productId ID of the product to add
     * @param quantity Quantity to add
     * @return true if successful, false if product not found or insufficient stock
     */
    public boolean addToCart(int productId, int quantity) {
        Product productItem = catalog.get(productId);
        if (productItem == null || productItem.getStockQuantity() < quantity) {
            return false;
        }

        productItem.decreaseStock(quantity);
        cart.put(productId, cart.getOrDefault(productId, 0) + quantity);
        operationStack.push(new CartOperation(CartOperation.OperationType.ADD, productId, quantity));
        saveToFile();
        return true;
    }

    /**
     * Removes a product from the shopping cart.
     * @param productId ID of the product to remove
     * @return true if successful, false if product not in cart
     */
    public boolean removeFromCart(int productId) {
        if (!cart.containsKey(productId)) {
            return false;
        }

        int quantity = cart.get(productId);
        cart.remove(productId);
        catalog.get(productId).increaseStock(quantity);
        operationStack.push(new CartOperation(CartOperation.OperationType.REMOVE, productId, quantity));
        saveToFile();
        return true;
    }

    /**
     * Undoes the last cart operation (add/remove).
     * @return true if undo was performed, false if nothing to undo
     */
    public boolean undoLastAction() {
        if (operationStack.isEmpty()) {
            return false;
        }

        CartOperation lastOp = operationStack.pop();
        int productId = lastOp.getProductId();
        int quantity = lastOp.getQuantity();

        if (lastOp.getType() == CartOperation.OperationType.ADD) {
            cart.put(productId, cart.get(productId) - quantity);
            if (cart.get(productId) <= 0) cart.remove(productId);
            catalog.get(productId).increaseStock(quantity);
        } else if (lastOp.getType() == CartOperation.OperationType.REMOVE) {
            cart.put(productId, quantity);
            catalog.get(productId).decreaseStock(quantity);
        }

        saveToFile();
        return true;
    }

    /**
     * Gets the current cart contents.
     * @return Map of product IDs to quantities
     */
    public Map<Integer, Integer> getCartContents() {
        return new HashMap<>(cart);
    }

    /**
     * Gets the total number of items in the cart.
     * @return Total quantity of all items
     */
    public int getTotalItemCount() {
        return cart.values().stream().mapToInt(Integer::intValue).sum();
    }

    /**
     * Gets the total cost of items in the cart.
     * @return Total cost as double
     */
    public double getTotalCost() {
        double total = 0.0;
        for (Map.Entry<Integer, Integer> entry : cart.entrySet()) {
            Product product = catalog.get(entry.getKey());
            total += product.getPrice() * entry.getValue();
        }
        return total;
    }

    /**
     * Checks if the cart is empty.
     * @return true if cart is empty, false otherwise
     */
    public boolean isEmpty() {
        return cart.isEmpty();
    }

    /**
     * Clears the shopping cart completely.
     */
    public void clearCart() {
        // Restore all stock before clearing
        for (Map.Entry<Integer, Integer> entry : cart.entrySet()) {
            catalog.get(entry.getKey()).increaseStock(entry.getValue());
        }
        cart.clear();
        operationStack.clear();
        saveToFile();
    }

    /**
     * Saves cart state to files.
     */
    private void saveToFile() {
        try (ObjectOutputStream cartOut = new ObjectOutputStream(new FileOutputStream(CART_FILE));
             ObjectOutputStream stackOut = new ObjectOutputStream(new FileOutputStream(STACK_FILE))) {
            cartOut.writeObject(cart);
            stackOut.writeObject(operationStack);
        } catch (IOException e) {
            // Error handling can be done by GUI
        }
    }

    /**
     * Loads cart state from files.
     */
    @SuppressWarnings("unchecked")
    private void loadFromFile() {
        try (ObjectInputStream cartIn = new ObjectInputStream(new FileInputStream(CART_FILE));
             ObjectInputStream stackIn = new ObjectInputStream(new FileInputStream(STACK_FILE))) {
            cart = (Map<Integer, Integer>) cartIn.readObject();
            operationStack = (Stack<CartOperation>) stackIn.readObject();
        } catch (IOException | ClassNotFoundException e) {
            // Initialize fresh cart if no saved data exists
            cart = new HashMap<>();
            operationStack = new Stack<>();
        }
    }
}