package Application;
import java.io.Serializable;
/**
 * AVL Tree implementation for storing and managing Product objects.
 * This self-balancing binary search tree ensures O(log n) time complexity for 
 * insert, delete, and search operations by maintaining height balance.
 */
class AVL implements Serializable{
    
	
	private static final long serialVersionUID = 1L;
    /**
     * Inner Node class representing individual nodes in the AVL tree.
     * Each node contains Product data and maintains balance information.
     */
    public static class Node implements Serializable{
    	private static final long serialVersionUID = 1L;
        private Product data;      // The Product object stored in this node
        private Node left;        // Reference to left child node
        private Node right;       // Reference to right child node
        private int height;       // Height of this node in the tree

        /**
         * Default constructor creates an empty node.
         */
        public Node() {
            this.data = null;
            this.left = null;
            this.right = null;
            this.height = 1;     // New node starts with height 1
        }

        /**
         * Constructor that initializes node with Product data.
         * @param data The Product object to store in this node
         */
        public Node(Product data) {
            this.data = data;
            this.left = null;
            this.right = null;
            this.height = 1;
        }

        /**
         * Constructor that creates a node with new Product from individual fields.
         * @param iD Product ID
         * @param name Product name
         * @param description Product description
         * @param price Product price
         * @param stock Product stock quantity
         */
        public Node(int iD, String name, String description, double price, int stock) {
            this.data = new Product(iD, name, description, price, stock);
            this.left = null;
            this.right = null;
            this.height = 1;
        }

        // Standard getters and setters with documentation
        
        public Node getLeft() { return left; }
        public void setLeft(Node left) { this.left = left; }
        
        public Node getRight() { return right; }
        public void setRight(Node right) { this.right = right; }
        
        public Product getData() { return data; }
        public void setData(Product data) { this.data = data; }

        /**
         * Gets the height of a given node.
         * @param node The node to check height of
         * @return Height of the node, 0 if node is null
         */
        public int getHeight(Node node) {
            if (node == null) {
                return 0;
            }
            return node.height;
        }

        public void setHeight(int height) { this.height = height; }
    }

    private Node head;  // Root node of the AVL tree

    /**
     * Default constructor creates an empty AVL tree.
     */
    public AVL() {
        this.head = null;
    }

    /**
     * Constructor that initializes AVL tree with given root node.
     * @param head The root node to start the tree with
     */
    public AVL(Node head) {
        this.head = head;
    }

    // Standard getters and setters
    
    public Node getHead() { return head; }
    public void setHead(Node head) { this.head = head; }

    /**
     * Public method to insert a Product into the AVL tree.
     * Handles balancing automatically after insertion.
     * @param data The Product to insert
     * @return The new root node of the tree
     */
    public Node insertProduct(Product data) {
        head = insertProduct(data, head);
        return head;
    }

    /**
     * Recursive helper method for product insertion.
     * @param data Product to insert
     * @param head Current node being examined
     * @return The potentially rotated subtree root
     */
    private Node insertProduct(Product data, Node head) {
        // Base case: found empty spot, create new node
        if (head == null) {
            return new Node(data);
        }
        
        // Recursive insertion in left or right subtree
        if (data.getProductId() < head.getData().getProductId()) {
            head.left = insertProduct(data, head.left);
        } else if (data.getProductId() > head.getData().getProductId()) {
            head.right = insertProduct(data, head.right);
        } else {
            // Duplicate ID found - update existing product
            head.data.updateFrom(data);
            return head;
        }

        // Update height and balance the tree
        head.height = Math.max(head.getHeight(head.left), head.getHeight(head.right)) + 1;
        return rotate(head);
    }

    /**
     * Balances the tree by performing rotations if needed.
     * @param node The root of the subtree to balance
     * @return The new root of the balanced subtree
     */
    private Node rotate(Node node) {
        // Left heavy situation
        if (node.getHeight(node.left) - node.getHeight(node.right) > 1) {
            // Left-left case
            if (node.getHeight(node.left.left) - node.getHeight(node.left.right) > 0) {
                return rightRotate(node);
            }
            // Left-right case
            if (node.getHeight(node.left.left) - node.getHeight(node.left.right) < 0) {
                node.left = leftRotate(node.left);
                return rightRotate(node);
            }
        }
        // Right heavy situation
        if (node.getHeight(node.left) - node.getHeight(node.right) < -1) {
            // Right-right case
            if (node.getHeight(node.right.left) - node.getHeight(node.right.right) < 0) {
                return leftRotate(node);
            }
            // Right-left case
            if (node.getHeight(node.right.left) - node.getHeight(node.right.right) > 0) {
                node.right = rightRotate(node.right);
                return leftRotate(node);
            }
        }
        // No rotation needed
        return node;
    }

    /**
     * Performs a right rotation to balance the tree.
     * @param p The root node of the unbalanced subtree
     * @return The new root after rotation
     */
    private Node rightRotate(Node p) {
        Node child = p.left;
        Node t = child.right;
        
        // Perform rotation
        child.right = p;
        p.left = t;
        
        // Update heights
        p.height = Math.max(p.getHeight(p.left), p.getHeight(p.right)) + 1;
        child.height = Math.max(p.getHeight(child.left), p.getHeight(child.right)) + 1;

        return child;
    }

    /**
     * Performs a left rotation to balance the tree.
     * @param child The root node of the unbalanced subtree
     * @return The new root after rotation
     */
    private Node leftRotate(Node child) {
        Node p = child.right;
        Node t = p.left;

        // Perform rotation
        p.left = child;
        child.right = t;

        // Update heights
        p.height = 1 + Math.max(child.getHeight(p.left), child.getHeight(p.right));
        child.height = 1 + Math.max(child.getHeight(child.left), child.getHeight(child.right));

        return p;
    }

    /**
     * Public method to search for a product by ID.
     * @param productId The ID to search for
     * @return The Node containing the product, or null if not found
     */
    public Node productSearch(int productId) {
        return productSearch(head, productId);
    }

    /**
     * Recursive helper method for product search.
     * @param node Current node being examined
     * @param productId The ID to search for
     * @return Node containing the product, or null if not found
     */
    private Node productSearch(Node node, int productId) {
        // Base case: not found
        if (node == null) {
            return null;
        }
        
        // Found match
        if (productId == node.getData().getProductId()) {
            return node;
        }
        
        // Recursive search in left or right subtree
        if (productId < node.getData().getProductId()) {
            return productSearch(node.left, productId);
        }
        return productSearch(node.right, productId);
    }

    /**
     * Public method to delete a product by ID.
     * @param productID The ID of the product to delete
     */
    public void deleteProduct(int productID) {
        head = deleteProduct(head, productID);
    }

    /**
     * Recursive helper method for product deletion.
     * @param root Current node being examined
     * @param productID The ID of the product to delete
     * @return The new root of the subtree after deletion and balancing
     */
    private Node deleteProduct(Node root, int productID) {
        // Base case: product not found
        if (root == null) {
            return null;
        }
        
        // Recursive search for node to delete
        if (productID < root.getData().getProductId()) {
            root.left = deleteProduct(root.left, productID);
        } else if (productID > root.getData().getProductId()) {
            root.right = deleteProduct(root.right, productID);
        } else {
            // Node to delete found
            
            // Case 1: Node with no children or one child
            if (root.left == null) {
                return root.right;
            } else if (root.right == null) {
                return root.left;
            }
            
            // Case 2: Node with two children
            // Find inorder successor (smallest in right subtree)
            Node successor = findMin(root.right);
            // Copy successor data to current node
            root.setData(successor.getData());
            // Delete the successor
            root.right = deleteProduct(root.right, successor.getData().getProductId());
        }
        
        // Update height and balance the tree
        root.height = Math.max(root.getHeight(root.left), root.getHeight(root.right)) + 1;
        return rotate(root);
    }

    /**
     * Finds the node with minimum value in a subtree.
     * @param node The root of the subtree to search
     * @return The node with minimum value
     */
    private Node findMin(Node node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    /**
     * Modifies an existing product in the tree.
     * @param id The ID of the product to modify
     * @param newData The new Product data to store
     * @return true if modification was successful, false if product not found
     */
    public boolean modifyProduct(int id, Product newData) {
        Node node = productSearch(id);
        if (node != null) {
            node.setData(newData);
            return true;
        }
        return false;
    }

    /**
     * Generates a string representation of the tree structure.
     * @return Formatted string showing the tree hierarchy
     */
    public String getTreeStructure() {
        StringBuilder sb = new StringBuilder();
        buildTreeStructure(head, 0, sb);
        return sb.toString();
    }

    /**
     * Recursive helper method to build tree structure string.
     * @param node Current node being processed
     * @param level Current depth in the tree
     * @param sb StringBuilder accumulating the structure string
     */
    private void buildTreeStructure(Node node, int level, StringBuilder sb) {
        if (node == null) {
            return;
        }

        // Process right subtree first (will appear on top in output)
        buildTreeStructure(node.right, level + 1, sb);

        // Add indentation based on level
        for (int i = 0; i < level; i++) {
            sb.append("|   ");
        }
        
        // Add current node information
        if (level == 0) {
            sb.append("Root: ").append(node.getData().getProductId())
              .append(" - ").append(node.getData().getName()).append("\n");
        } else {
            sb.append("|---> ").append(node.getData().getProductId())
              .append(" - ").append(node.getData().getName()).append("\n");
        }

        // Process left subtree (will appear on bottom in output)
        buildTreeStructure(node.left, level + 1, sb);
    }
}