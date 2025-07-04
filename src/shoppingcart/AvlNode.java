/*
  Author: Tyreese Dunbar 
  ID: 2004439
  Module: CIT2004
  Course: Object Oriented Programming-UM2
  Date: 6th June, 2025  
*/
package shoppingcart;

import java.util.Scanner;

class AVL {
    private final Scanner input = new Scanner(System.in);
    // Node class for AVL tree
    public class Node {
        private Product data; // product object
        private Node left; // left child
        private Node right; // right child
        private int height; // height of the node

        public Node() {// default constructor
            this.data = null;// new Product(); // initialize data with a new Product object
            this.left = null;
            this.right = null;
            this.height = 1; // height of a new node is 1
        }

        public Node(Product data) { // constructor to pass in Product data
            this.data = data; // assign the product data
            this.left = null;
            this.right = null;
            this.height = 1; // height of a new node is 1
        }

        public Node(int iD, String name, String description, double price, int stock) { // constructor to pass in
                                                                                        // Product data
            this.data = new Product(iD, name, description, price, stock); // assign the product data
            this.left = null;
            this.right = null;
            this.height = 1; // height of a new node is 1
        }

        public Node getLeft() {
            return left;
        }

        public void setLeft(Node left) {
            this.left = left;
        }

        public Node getRight() {
            return right;
        }

        public void setRight(Node right) {
            this.right = right;
        }

        public Product getData() { // getter for data
            return data;
        }

        public void setData(Product data) { // setter for data
            this.data = data;
        }

        public int getHeight(Node node) { // method to get the height of a node
            if (node == null) {
                return 0; // if node is null, height is 0
            }
            return node.height;
        }

        public void setHeight(int height) { // setter for height
            this.height = height;
        }
    }

    private Node head; // point called head for the AVL tree

    public AVL() {// initialize head to null
        this.head = null;
    }

    public Node getHead() { // return position
        return head;
    }

    public Node insertProduct(Product data, Node head) { // method to insert a product into the AVL tree
        if (head == null) {// if head is null,avl tree is empty, create a new node
            Node node = new Node(data);
            head = node; // assign the new node to position head
            return node;
        }
        if (data.getId() < head.getData().getId()) {
            // if data's ID is less than head's(base node) id, insertProduct into leftsubtree of base node
            head.left = insertProduct(data, head.left);
        }
        if (data.getId() > head.getData().getId()) {
            // if data's ID is greater than head's(base node) id, insertProduct into rightsubtree of base node
            head.right = insertProduct(data, head.right);
        }
        head.height = Math.max(head.getHeight(head.left), head.getHeight(head.right)) + 1; // update height of the node
        return rotate(head); // return the rotated node
    }

    public Node rotate(Node node) {
        if (node.getHeight(node.left) - node.getHeight(node.right) > 1) {
            /*
             * if left subtree is heavier than right subtree
             * node is left heavy
             */

            if (node.getHeight(node.left.left) - node.getHeight(node.left.right) > 0) {
                // left left case equals >0
                return rightRotate(node);
            }
            if (node.getHeight(node.left.left) - node.getHeight(node.left.right) < 0) {
                // left right case
                node.left = leftRotate(node.left);
                return rightRotate(node);
            }
        }
        if (node.getHeight(node.left) - node.getHeight(node.right) < -1) {
            // if right subtree is heavier than left subtree, node is right heavy
            if (node.getHeight(node.right.left) - node.getHeight(node.right.right) < 0) {
                // right right case
                return leftRotate(node);
            }
            if (node.getHeight(node.right.left) - node.getHeight(node.right.right) > 0) {
                // left right case
                node.right = rightRotate(node.right);
                return leftRotate(node);
            }
        }
        return node;
    }

    public Node rightRotate(Node p) {
        /*
         * In a Parent, Child Grand-Child Structure on a left heavy tree,
         * the right rotation will make the child the new parent and the parent the new
         * child.
         */
        Node child = p.left; // Leftchild of parent node
        Node t = child.right; // right child of child
        /*
         * rotate the nodes
         * the right of parent always remains the same ,
         * the parent become the right of child
         * the right of child becomes the left of parent
         */
        child.right = p; // parent become child
        p.left = t;// right child of child becomes left of parent
        // update the height of the nodes assigned
        // since parents is not the lowest node due to rotation we update that height
        // first
        // then we update the child height
        p.height = Math.max(p.getHeight(p.left), p.getHeight(p.right)) + 1;
        child.height = Math.max(p.getHeight(child.left), p.getHeight(child.right)) + 1;

        return child; // return the new parent node (root of the subtree)
    }

    public Node leftRotate(Node child) {

        Node p = child.right;// right child of child
        Node t = p.left; // left child of parent

        p.left = child; // child becomes parent
        child.right = t; // left child of parent becomes right child of child

        p.height = Math.max(child.getHeight(p.left), child.getHeight(p.right)) + 1;// update height
        child.height = Math.max(child.getHeight(child.left), child.getHeight(child.right)) + 1;// update height

        return p;// return the new parent
    }

    // Search for a productID
    public Node productSearch(Node node, int productId) {
        if (node == null) {
            return node; // if node is null, return null
        }
        if (productId == node.getData().getId()) {
            return node;
        }
        if (productId < node.getData().getId()) {
            return productSearch(node.left, productId);
        }
        if (productId > node.getData().getId()) {
            return productSearch(node.right, productId);
        }
        return node;
    }

    public Node deleteProduct(Node root, int productID) {
        // 1. If the tree is empty, return null
        if (root == null) {
            return root;
        }
        // 2. If the tree is not empty
        if (productID < root.getData().getId()) {// if productID is less than root's ID, traverse left subtree
            root.left = deleteProduct(root.left, productID);
        }
        if (productID > root.getData().getId()) {// if productID is greater than root's ID, traverse right subtree
            root.right = deleteProduct(root.right, productID);
        }
        // 3. At Node to delete
        if (root.left == null && root.right == null) {// Node with no child
            root = null; // assign root to null
            return root; // if node has no child, return null
        }
        if (root.left != null || root.right != null) {// Node with atleast one child
            if ((root.left != null) && (root.right == null)) {// Node with one child on left
                Node replacementNode = root.left; // assign left child to replacement node
                while (replacementNode.right != null) { // find the rightmost node in the left subtree
                    replacementNode = replacementNode.right; // traverse to the rightmost node to find the replacement
                                                             // node
                }
                root.setData(replacementNode.getData()); // replace the data of the node to be deleted with the
                                                         // replacement node's data
                root.left=deleteProduct(root.left, replacementNode.getData().getId()); // assign the last node of the left subtree to null
                return rotate(root); // return the root with left child removed and balanced
            }
            if ((root.left == null) && (root.right != null)) {// Node with one child on right
                Node replacementNode = root.right; // assign right child to replacement node
                while (replacementNode.left != null) { // find the leftmost node in the right subtree
                    replacementNode = replacementNode.left; // traverse to the leftmost node to find the replacement
                                                            // node
                }
                root.setData(replacementNode.getData()); // replace the data of the node to be deleted with the
                                                         // replacement node's data
                root.right=deleteProduct(root.right, replacementNode.getData().getId()); // root.right; // assign the last node of the right subtree to null
                return rotate(root); // return the root with right child removed and balanced
            }
            if (root.left != null && root.right != null) {// Node with child on both sides
                // Find the in-order successor (minimum node in the right subtree)
                Node successor = root.right; // assign right child to replacement node
                while (successor.left != null) { // find the leftmost node in the right subtree
                    successor = successor.left; // traverse to the leftmost node to find the replacement
                                                             // node
                }
                root.setData(successor.getData()); // replace the data of the node to be deleted with the(successor)
                                                         // replacement node's data
                    // Recursively delete the successor node from the right subtree
                root.right = deleteProduct(root.right, successor.getData().getId());
                return rotate(root); // return the root with left child removed and balanced
            }

        }
        return root;
    }

    public Node modifyNodeLookUp(Node node, int id) {
        // 1. Search for the product with the given id
        Node productFound = productSearch(node, id);
        if (productFound != null) {// 2. If product is found, modify the product details
            Product data = modifiedInput(productFound); // get the modified product details from user input
            productFound.setData(data); // set the modified product data to the node
        }
        return productFound; // return the node with the product found
    }

    public Product modifiedInput(Node node) {
        Product product = null;
        int idChange = node.getData().getId();
        char choice;
        boolean validInput = false;
        do {
            System.out.println("Input y to modify ID number or n to keep the same " + node.getData().getId());
            choice = input.next().charAt(0);
            if (choice == 'y' || choice == 'Y') {
                idChange = input.nextInt();
            }
        } while (choice != 'y' && choice != 'Y' && choice != 'n' && choice != 'N');// loop until user input is valid

        while (validInput==false) {
            try {
                System.out.println("Enter the product name:");
                String name = input.nextLine();
                System.out.println("Enter the product description:");
                String description = input.nextLine();
                System.out.println("Enter the product price:");
                double price = input.nextDouble();
                System.out.println("Enter the product stock:");
                int stock = input.nextInt();

                // Add input validation here
                if (name.isEmpty() || description.isEmpty() || price < 0 || stock < 0) {
                    System.out.println("Invalid input. Please try again.");
                } else {
                    product = new Product(idChange, name, description, price, stock);// create a new product object with modifications done
                    node.setData(product); //set old object to new object contents
                    validInput = true;
                }
                // Clear the input buffer
                input.nextLine();
            } catch (Exception e) {
                System.out.println("An error occurred while modifying the product: " + e.getMessage());
                // Clear the input buffer
                input.nextLine();
            }
        }
        return product;
    }

    public void display() {
        display(this.head, "Root Node: ");
    }

    private void display(Node node, String details) {
        if (node != null) {
            System.out.println(details + node.getData().getId() + " - " + node.getData().getName());
            display(node.left, details + "Left Child: ");
            display(node.right, details + "Right Child: ");
        }
    }
    public Product acceptInput() {
        Product data = null;
        boolean validInput = false;
        
        while (validInput==false) 
        {
                System.out.println("Enter the product ID:");
                int id = input.nextInt(); input.nextLine();
                System.out.println("Enter the product name:");
                String name = input.nextLine();
                System.out.println("Enter the product description:");
                String description = input.nextLine();
                System.out.println("Enter the product price:");
                double price = input.nextDouble(); input.nextLine();
                System.out.println("Enter the product stock:");
                int stock = input.nextInt();

                // Add input validation here
                if (name.isEmpty() || description.isEmpty() || price < 0 || stock < 0) {
                    System.out.println("Invalid input. Please try again.");
                } else {
                    data = new Product(id, name, description, price, stock);
                    validInput = true;
                }    
        }
        return data;
}

    public void mainMenu() {
        int choice = 0;
        do {
            System.out.println("Please select an option:");
            System.out.println("1. Add a new product to the catalog.");
            System.out.println("2. Remove a product from the catalog.");
            System.out.println("3. Modify a product in the catalog.");
            System.out.println("4. Search for a product in the catalog.");
            System.out.println("5. Exit.");
            choice = input.nextInt();

            switch (choice) {
                case 1:// Add new product
                    Product data = acceptInput();
                    this.head = insertProduct(data, head);// insert the new product into the AVL tree and return the new head
                    break;
                case 2:
                      if (head == null) {
                        System.out.println("The catalog is empty.");
                        break;
                    }
                    System.out.println("Enter the ID of the product you want to remove:");
                    int id = input.nextInt();
                    this.head= deleteProduct(head, id); // delete the product with the given id from the AVL tree and return the new head
                    break;
                case 3:
                      if (head == null) {
                        System.out.println("The catalog is empty.");
                        break;
                    }
                    System.out.println("Enter the ID of the product you want to modify:");
                    id = input.nextInt();
                    Node nodeFound=modifyNodeLookUp(head, id);
                    if(nodeFound==null){
                        System.out.println("Product not found");
                    }
                    break;
                case 4:
                      if (head == null) {
                        System.out.println("The catalog is empty.");
                        break;
                    }
                    System.out.println("Enter the ID of the product you want to search for:");
                    id = input.nextInt();
                    Node dataFound=productSearch(head, id);
                    if(dataFound==null){
                        System.out.println("Product not found");
                    }else{
                        System.out.println("Product found: "+dataFound.getData().getId()+" "+dataFound.getData().getName());
                    }
                    break;
                case 5:
                    break;
            }
        } while (choice != 5);

    }
      public void closeInput() {
        input.close();
    }
    public static void main(String[] args) {
        AVL avl = new AVL();
        //test with user input
        //avl.mainMenu();
       // test without user input Manually create product objects
        Product p1 = new Product(30, "Mouse", "Wireless optical mouse", 25.99, 100);
        Product p2 = new Product(10, "Keyboard", "Mechanical keyboard", 89.99, 50);
        Product p3 = new Product(20, "Monitor", "24 inch LED monitor", 149.99, 25);
        Product p4 = new Product(40, "Webcam", "HD webcam", 59.99, 15);

        // Insert products
        avl.head = avl.insertProduct(p1, avl.getHead());
        avl.head = avl.insertProduct(p2, avl.getHead());
        avl.head = avl.insertProduct(p3, avl.getHead());
        avl.head = avl.insertProduct(p4, avl.getHead());

        // Display the AVL tree structure
        System.out.println("=== AVL Tree After Insertion ===");
        avl.display();

        // Search for a product
        System.out.println("\n=== Searching for Product ID 20 ===");
        AVL.Node result = avl.productSearch(avl.getHead(), 20);
        if (result != null) {
            System.out.println("Product found: " + result.getData().getName());
        } else {
            System.out.println("Product not found");
        }

        // Delete a product
        System.out.println("\n=== Deleting Product ID 10 ===");
        avl.deleteProduct(avl.getHead(), 10);
        avl.display();

        // Modify a product (you can invoke modifyNodeLookUp manually with fake input)
        // For automated test, simulate a Product replacement instead:
        System.out.println("\n=== Modifying Product ID 30 ===");
        Product updated = new Product(30, "Gaming Mouse", "RGB gaming mouse", 49.99, 80);
        AVL.Node modifyNode = avl.productSearch(avl.getHead(), 30);
        if (modifyNode != null) {
            modifyNode.setData(updated);
        }

        // Final display
        System.out.println("\n=== Final AVL Tree ===");
        avl.display();
    }
        

}