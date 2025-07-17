package Application;

import java.io.IOException;
import java.util.LinkedList;

public class ProductFileManager {
    private FileManagement fileManager;
    private String fileName;
    private static final String Product_file = "products.csv";

    public ProductFileManager() {
        this.fileManager = new FileManagement();
        this.fileName = Product_file;
    }

    // Save the entire linked list to file
    public void saveProducts(CLinkedList<Product> productList) throws IOException {
        // Convert the linked list to a format that FileManagement can handle
        LinkedList<String[]> records = new LinkedList<>();
        
        // Properly traverse your custom LinkedList
        Node<Product> current = productList.getHead();
        while (current != null) {
            Product product = current.getData();
            records.add(product.toString().split(","));
            current = current.getNextNode();
        }
        
        fileManager.updateObjectsInFile(fileName, records);
     // Also save the AVL tree
        new AVLFileManager().saveAVLTree(new CatalogManager().getAvl());
    }

    // Load products from file into a linked list
    public CLinkedList<Product> loadProducts() throws IOException {
        CLinkedList<Product> productList = new CLinkedList<>();
        LinkedList<String[]> records = fileManager.readObjectsFromFile(fileName);
        
        for (String[] record : records) {
            if (record.length >= 5) { // Ensure we have all required fields
                try {
                    int productId = Integer.parseInt(record[0].trim());
                    String name = record[1].trim();
                    String description = record[2].trim();
                    double price = Double.parseDouble(record[3].trim());
                    int stockQuantity = Integer.parseInt(record[4].trim());
                    
                    Product product = new Product(productId, name, description, price, stockQuantity);
                    productList.insertAtBack(product);
                } catch (NumberFormatException e) {
                    System.err.println("Error parsing product record: " + String.join(",", record));
                }
            }
        }
        
        return productList;
    }
    
    /**
     * Updates a specific product in the file while preserving other records
     * @param updatedProduct The product with updated information
     * @throws IOException If file operations fail
     */
    public void updateProductInFile(Product updatedProduct) throws IOException {
        // Input validation
        if (updatedProduct == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }

        // Read all existing products
        LinkedList<String[]> allRecords = fileManager.readObjectsFromFile(fileName);
        LinkedList<String[]> updatedRecords = new LinkedList<>();

        boolean productFound = false;
        
        // Update the specific product record
        for (String[] record : allRecords) {
            if (record.length >= 1 && record[0].trim().equals(String.valueOf(updatedProduct.getProductId()))) {
                // Replace with updated product
                updatedRecords.add(updatedProduct.toString().split(","));
                productFound = true;
            } else {
                // Keep other records as-is
                updatedRecords.add(record);
            }
        }

        if (!productFound) {
            throw new IOException("Product with ID " + updatedProduct.getProductId() + " not found in file");
        }

        // Write all records back to file
        fileManager.updateObjectsInFile(fileName, updatedRecords);
    }
    
    

    // Add a single product to the file
    public void addProduct(Product product) throws IOException {
        fileManager.writeObjectToFile(product, fileName);
    }

    // Delete a product from the file by product ID
    public void deleteProduct(int productId) throws IOException {
    	try {
            fileManager.deleteObjectFromFile(String.valueOf(productId), fileName);
        } catch (IllegalArgumentException e) {
            // Product not found in file is not necessarily an error
            System.out.println("Product not found in file, continuing with data structure deletion");
        } catch (IOException e) {
            System.err.println("File operation failed: " + e.getMessage());
            throw e;
        }
    }
}