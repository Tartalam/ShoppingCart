package Application;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import java.util.HashMap;
import java.util.Map;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main extends Application {
    private static CatalogManager catalogManager;
    private static ShoppingCart shoppingCart;
    private static Queue orderQueue;
    
    // File paths
    private static final String PRODUCTS_FILE = "products.csv";
    private static final String CART_FILE = "cart.ser";
    private static final String STACK_FILE = "stack.ser";
    private static final String QUEUE_FILE = "Queue.csv";

    @Override
    public void start(Stage primaryStage) {
        
    	// Create loading screen
        ProgressBar progressBar = new ProgressBar();
        Label statusLabel = new Label("Initializing application...");
        VBox loadingRoot = new VBox(20, progressBar, statusLabel);
        loadingRoot.setAlignment(Pos.CENTER);
        loadingRoot.setStyle("-fx-padding: 50; -fx-background-color: #f0f0f0;");

        Stage loadingStage = new Stage();
        loadingStage.setScene(new Scene(loadingRoot, 300, 200));
        loadingStage.setTitle("Loading");
        loadingStage.show();

        // Create background loading task
        Task<Void> loadingTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                updateProgress(0, 4);
                updateMessage("Loading product catalog...");
                
                // Step 1: Load products if file exists
                if (Files.exists(Path.of(PRODUCTS_FILE))) {
                    ProductFileManager productFileManager = new ProductFileManager();
                    CLinkedList<Product> productList = productFileManager.loadProducts();
                    catalogManager = new CatalogManager();
                    catalogManager.setLinkedList(productList);
                    
                    // Build AVL tree from loaded products
                    buildAvlTreeFromLinkedList(productList);
                } else {
                    catalogManager = new CatalogManager();
                    System.out.println("No products file found. Starting with empty catalog.");
                }
                updateProgress(1, 4);

                updateMessage("Preparing product catalog...");
                // Create product catalog map (empty if no products loaded)
                Map<Integer, Product> productCatalog = createProductCatalog();
                updateProgress(2, 4);

                updateMessage("Loading shopping cart...");
                // Initialize shopping cart
                shoppingCart = new ShoppingCart(productCatalog);
                
                // Only try to load cart if files exist
                if (Files.exists(Path.of(CART_FILE)) && Files.exists(Path.of(STACK_FILE))) {
                    System.out.println("Loading existing shopping cart state");
                } else {
                    System.out.println("No shopping cart files found. Starting with empty cart.");
                }
                updateProgress(3, 4);

                updateMessage("Loading order queue...");
                // Initialize order queue
                orderQueue = new Queue();
                if (Files.exists(Path.of(QUEUE_FILE))) {
                    orderQueue.loadFromFile();
                    System.out.println("Loaded existing order queue");
                } else {
                    System.out.println("No order queue file found. Starting with empty queue.");
                }
                updateProgress(4, 4);

                return null;
            }
        };

        // Bind UI to task progress
        progressBar.progressProperty().bind(loadingTask.progressProperty());
        statusLabel.textProperty().bind(loadingTask.messageProperty());

        // Handle task completion
        loadingTask.setOnSucceeded(e -> {
            loadingStage.close();
            try {
                Parent root = FXMLLoader.load(getClass().getResource("MainPageGUI.fxml"));
                Scene scene = new Scene(root);
                scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
                primaryStage.setScene(scene);
                primaryStage.show();
            } catch (Exception ex) {
                handleStartupError(ex);
            }
        });

        loadingTask.setOnFailed(e -> {
            loadingStage.close();
            handleStartupError(loadingTask.getException());
        });

        // Start the background task
        new Thread(loadingTask).start();
    }
    
    
    /**
     * Builds AVL tree from linked list in a background-friendly way
     * 
     * @param productList The linked list containing all products
     */
    private void buildAvlTreeFromLinkedList(CLinkedList<Product> productList) {
        Node<Product> current = productList.getHead();
        while (current != null) {
            catalogManager.getAvl().insertProduct(current.getData());
            current = current.getNextNode();
        }
    }

    /**
     * Creates a product catalog map from the AVL tree.
     * Returns empty map if no products loaded.
     */
    private Map<Integer, Product> createProductCatalog() {
        Map<Integer, Product> productMap = new HashMap<>();
        if (catalogManager.getAvl().getHead() != null) {
            addProductsToMap(catalogManager.getAvl().getHead(), productMap);
        }
        return productMap;
    }

    /**
     * Recursively adds products from AVL tree to a map.
     */
    private void addProductsToMap(AVL.Node node, Map<Integer, Product> productMap) {
        if (node == null) return;
        
        productMap.put(node.getData().getProductId(), node.getData());
        addProductsToMap(node.getLeft(), productMap);
        addProductsToMap(node.getRight(), productMap);
    }
    
    /**
     * Handles startup errors by displaying an alert to the user.
     * 
     * @param e The exception that occurred during startup
     */
    private void handleStartupError(Throwable e) {
        System.err.println("Error during application startup: " + e.getMessage());
        e.printStackTrace();
        
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Startup Error");
        alert.setHeaderText("Failed to initialize application");
        
        // Provide more specific messages for common error cases
        if (e.getCause() instanceof IOException) {
            alert.setContentText("Failed to load data files. Please check file permissions and paths.");
        } else {
            alert.setContentText("An unexpected error occurred: " + e.getMessage());
        }
        
        alert.showAndWait();
    }

    // ... [rest of the methods remain the same as previous version]

    public static void main(String[] args) {
        launch(args);
    }

    public static CatalogManager getCatalogManager() {
        return catalogManager;
    }

    public static ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public static Queue getOrderQueue() {
        return orderQueue;
    }
}