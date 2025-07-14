package Application;

import java.io.Serializable;
import java.util.Random;

public class Product implements Identifiable, Serializable{
	private static final long serialVersionUID = 1L;
    private int productId;
    private String name;
    private String description;
    private double price;
    private int stockQuantity;
    
    public Product() {
        this.productId = 0;
        this.name = "";
        this.description = "";
        this.price = 0.0;
        this.stockQuantity = 0;
    }
    
    public Product(int productId, String name, String description, double price, int stockQuantity) {
        this.productId = productId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }
    
    public Product(Product product) {
        this.productId = product.productId;
        this.name = product.name;
        this.description = product.description;
        this.price = product.price;
        this.stockQuantity = product.stockQuantity;
    }

    // Getters and Setters
    public int getProductId() { return productId; }
    public void setProductId(int productId) { this.productId = productId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public int getStockQuantity() { return stockQuantity; }
    public void setStockQuantity(int stockQuantity) { this.stockQuantity = stockQuantity; }
    
    public void decreaseStock(int quantity){
        stockQuantity -= quantity;
    }

    public void increaseStock(int quantity){
        stockQuantity += quantity;
    }
    
    
    @Override
    public boolean matchByIdOrPassword(Object identifier) {
        if (identifier instanceof Integer) {
            return this.productId == (Integer) identifier;
        }
        return false;
    }

    @Override
    public boolean updateFrom(Object source) {
        if (source instanceof Product) {
            Product update = (Product) source;
            // Only update fields that have values in the update object
            if (update.getName() != null && !update.getName().isEmpty()) {
                this.name = update.getName();
            }
            if (update.getDescription() != null && !update.getDescription().isEmpty()) {
                this.description = update.getDescription();
            }
            if (update.getPrice() > 0) {
                this.price = update.getPrice();
            }
            if (update.getStockQuantity() >= 0) {
                this.stockQuantity = update.getStockQuantity();
            }
            return true;
        }
        return false;
    }
    
    /**
     * Generates a random 4-digit product ID that's unique in the given linked list
     * @param productList The linked list to check for existing IDs
     * @return A unique 4-digit product ID
     */
    public static int generateUniqueProductId(CLinkedList<Product> productList) {
        Random random = new Random();
        int newId;
        boolean isUnique;
        
        do {
            // Generate a 4-digit number between 1000 and 9999
            newId = 1000 + random.nextInt(9000);
            isUnique = true;
            
            // Check if this ID already exists in the list
            Node<Product> current = productList.getHead();
            while (current != null) {
                if (current.getData().getProductId() == newId) {
                    isUnique = false;
                    break;
                }
                current = current.getNextNode();
            }
        } while (!isUnique); // Keep generating until we find a unique ID
        
        return newId;
    }
    
    @Override
    public String toString() {
        return productId + "," + name + 
               "," + description + "," + price + 
               "," + stockQuantity;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Product product = (Product) obj;
        return productId == product.productId;
    }
}
