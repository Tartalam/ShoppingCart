/*
  Author: Tyreese Dunbar 
  ID: 2004439
  Module: CIT2004
  Course: Object Oriented Programming-UM2
  Date: 6th June, 2025  
*/

package shopping.cart.system;

class AVLTree{
    private Node root;

    public AVLTree(){
        root = null;
    }
    public Node getroot() {
        return root;
    }
    public void setroot(Node root) {
        this.root = root;
    }
    
    public void InsertNode(Product product){
        root= insertNode(root, product);
    }
    public void DeleteNode(Product product){
        root = deleteNode(root, product);
    }
    public Node SearchNode(Product product){
         Node node = searchNode(root, productID);
        return (node != null) ? node.product : null;
    }
     public void inOrder() {
        inOrderTraversal(root);
    }
     // Get height of a node
    public int height(Node N) {
        return (N == null) ? 0 : N.height;
    }

    // Get balance factor of a node
    public int getBalance(Node N) {
        return (N == null) ? 0 : height(N.left) - height(N.right);
    }

    // Right rotation (used to balance tree)
    public Node rightRotate(Node y) {
        Node x = y.left;
        Node T2 = x.right;

        // Rotate
        x.right = y;
        y.left = T2;

        // Update heights
        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;

        return x; // New root after rotation
    }

    // Left rotation (used to balance tree)
    public Node leftRotate(Node x) {
        Node y = x.right;
        Node T2 = y.left;

        // Rotate
        y.left = x;
        x.right = T2;

        // Update heights
        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;

        return y; // New root after rotation
    }

    // Recursive insert — standard AVL insert with balancing
    public Node insertNode(Node node, Product product) {
        // 1. Normal BST insert
        if (node == null)
            return new Node(product);

        if (product.productID < node.product.productID)
            node.left = insertNode(node.left, product);
        else if (product.productID > node.product.productID)
            node.right = insertNode(node.right, product);
        else
            return node; // Duplicate IDs not allowed

        // 2. Update height
        node.height = 1 + Math.max(height(node.left), height(node.right));

        // 3. Get balance and fix imbalance if needed
        int balance = getBalance(node);

        // 4 cases:

        // Left Left
        if (balance > 1 && product.productID < node.left.product.productID)
            return rightRotate(node);

        // Right Right
        if (balance < -1 && product.productID > node.right.product.productID)
            return leftRotate(node);

        // Left Right
        if (balance > 1 && product.productID > node.left.product.productID) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        // Right Left
        if (balance < -1 && product.productID < node.right.product.productID) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node; // Return unchanged node pointer
    }

    // Find the node with the minimum productID (used during delete)
    public Node minValueNode(Node node) {
        Node current = node;
        while (current.left != null)
            current = current.left;
        return current;
    }

    // Recursive delete — standard AVL delete with balancing
    public Node deleteNode(Node root, int productID) {
        // 1. Standard BST delete
        if (root == null)
            return root;

        if (productID < root.product.productID)
            root.left = deleteNode(root.left, productID);
        else if (productID > root.product.productID)
            root.right = deleteNode(root.right, productID);
        else {
            // Node with only one child or no child
            if ((root.left == null) || (root.right == null)) {
                Node temp = (root.left != null) ? root.left : root.right;

                if (temp == null) {
                    temp = root;
                    root = null;
                } else
                    root = temp; // One child case
            } else {
                // Node with two children
                Node temp = minValueNode(root.right);

                // Copy in-order successor to this node
                root.product = temp.product;

                // Delete the successor
                root.right = deleteNode(root.right, temp.product.productID);
            }
        }

        // If tree had only one node
        if (root == null)
            return root;

        // 2. Update height
        root.height = 1 + Math.max(height(root.left), height(root.right));

        // 3. Get balance and fix imbalance
        int balance = getBalance(root);

        // Left Left
        if (balance > 1 && getBalance(root.left) >= 0)
            return rightRotate(root);

        // Left Right
        if (balance > 1 && getBalance(root.left) < 0) {
            root.left = leftRotate(root.left);
            return rightRotate(root);
        }

        // Right Right
        if (balance < -1 && getBalance(root.right) <= 0)
            return leftRotate(root);

        // Right Left
        if (balance < -1 && getBalance(root.right) > 0) {
            root.right = rightRotate(root.right);
            return leftRotate(root);
        }

        return root;
    }

    // Search for a productID
    public Node searchNode(Node node, int productID) {
        if (node == null)
            return null;

        if (productID == node.product.productID)
            return node;
        else if (productID < node.product.productID)
            return searchNode(node.left, productID);
        else
            return searchNode(node.right, productID);
    }

    // In-order traversal — prints tree in sorted order
    public void inOrderTraversal(Node node) {
        if (node != null) {
            inOrderTraversal(node.left);
            System.out.println(node.product);
            inOrderTraversal(node.right);
        }
    }
}