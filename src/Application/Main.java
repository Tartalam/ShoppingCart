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
                
                // Step 1: Initialize CatalogManager which loads AVL tree automatically
                catalogManager = new CatalogManager();
                
                // If AVL tree is empty (new file), load from products.csv
                if (catalogManager.getAvl().getHead() == null && Files.exists(Path.of(PRODUCTS_FILE))) {
                    updateMessage("Building AVL tree from product list...");
                    ProductFileManager productFileManager = new ProductFileManager();
                    CLinkedList<Product> productList = productFileManager.loadProducts();
                    catalogManager.setLinkedList(productList);
                    
                    // Build AVL tree from product list
                    Node<Product> current = productList.getHead();
                    while (current != null) {
                        catalogManager.getAvl().insertProduct(current.getData());
                        current = current.getNextNode();
                    }
                    
                    // Add this to unsure linked list is in sync
                    catalogManager.rebuildLinkedListFromAVL();
                    
                    // Save the newly built AVL tree
                    catalogManager.saveAVLTree();
                }
                updateProgress(1, 4);

                updateMessage("Preparing product catalog...");
                Map<Integer, Product> productCatalog = createProductCatalog();
                updateProgress(2, 4);

                updateMessage("Initializing shopping cart...");
                shoppingCart = new ShoppingCart(productCatalog);
                updateProgress(3, 4);

                updateMessage("Loading order queue...");
                orderQueue = new Queue();
                if (Files.exists(Path.of(QUEUE_FILE))) {
                    orderQueue.loadFromFile();
                }
                updateProgress(4, 4);

                return null;
            }
        };

        // Bind UI to task progress
        progressBar.progressProperty().bind(loadingTask.progressProperty());
        statusLabel.textProperty().bind(loadingTask.messageProperty());

        loadingTask.setOnSucceeded(e -> {
            loadingStage.close();
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("MainPageGUI.fxml"));
                Parent root = loader.load();
                
                // Get controller and populate products
                SceneController controller = loader.getController();
                controller.populateMainPage();
                
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

        new Thread(loadingTask).start();
    }

    private Map<Integer, Product> createProductCatalog() {
        Map<Integer, Product> productMap = new HashMap<>();
        if (catalogManager.getAvl().getHead() != null) {
            addProductsToMap(catalogManager.getAvl().getHead(), productMap);
        }
        return productMap;
    }

    private void addProductsToMap(AVL.Node node, Map<Integer, Product> productMap) {
        if (node == null) return;
        productMap.put(node.getData().getProductId(), node.getData());
        addProductsToMap(node.getLeft(), productMap);
        addProductsToMap(node.getRight(), productMap);
    }

    private void handleStartupError(Throwable e) {
        System.err.println("Startup error: " + e.getMessage());
        e.printStackTrace();
        
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Startup Error");
        alert.setHeaderText("Failed to initialize application");
        alert.setContentText(e instanceof IOException ? 
            "File system error. Check data files." : 
            "Unexpected error: " + e.getMessage());
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static CatalogManager getCatalogManager() { return catalogManager; }
    public static ShoppingCart getShoppingCart() { return shoppingCart; }
    public static Queue getOrderQueue() { return orderQueue; }
}