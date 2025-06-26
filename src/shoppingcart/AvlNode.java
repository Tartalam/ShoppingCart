/*
  Author: Tyreese Dunbar 
  ID: 2004439
  Module: CIT2004
  Course: Object Oriented Programming-UM2
  Date: 6th June, 2025  
*/
class AVL{
    public class Node{
        private Product Data; // product object
        private Node left; // left child
        private Node right; // right child
        private int height; // height of the node
        
    

        public Node(){// default constructor
            this.Data= new Product(); // initialize Data with a new Product object
            this.left = null;
            this.right = null;
            this.height = 1; // height of a new node is 1
        }
        public Node(Product Data){ // constructor to pass in Product Data
            this.Data = new Product(Data); // assign the product data
            this.left = null;
            this.right = null;
            this.height = 1; // height of a new node is 1
        }
        public Product getData() { // getter for Data
            return Data;
        }
        public void setData(Product Data) { // setter for Data
            this.Data = Data;
        } 
        public int getHeight(Node node) { // method to get the height of a node
            if (node == null) {
                return 0; // if node is null, height is 0
            }
            return node.Height(); 
        }
        public void setHeight(int height) { // setter for height
            this.height = height;
        }
        public int getId(){
            return id;
        }
    }
    private Node head; // point called head for the AVL tree

    public AVL(){// initialize head to null
        this.head = null; 
    }
    public Node getHead() { // return position
        return head;
    }
    
    public Node insert(Product Data, Node head) { // method to insert a product into the AVL tree
        if(head==null){// if head is null,avl tree is empty, create a new node
            head = new Node(Data);
            return node;
        }
        if (Data.getId()<head.getData().getId()) {
            // if Data's ID is less than head's(base node) id, insert into left subtree of base node
            head.left = insert(Data, head.left);
        }
        if(Data.getId()>head.getData().getId()){
            // if Data's ID is greater than head's(base node) id, insert into right subtree of base node
            head.right=insert(Data, head.right);
        }
        head.Height= Math.max(getHeight(head.left), getHeight(head.right)) + 1; // update height of the node
        return rotate(head); // return the rotated node
    }
    public Node rotate(Node node){
        if (height(node.left)-height(node.right) > 1) { 
            /*if left subtree is heavier than right subtree
            node is left heavy */
            
            if(height(node.left.left) - height(node.left.right) > 0) {
                 // left left case
                return rightRotate(node);
            }
            if(height(node.left.left) - height(node.left.right) < 0) {
                // left right case
                node.left = leftRotate(node.left); 
                return rightRotate(node);
            }
        }
        if (height(node.left) - height(node.right) < -1) {
                // if right subtree is heavier than left subtree, node is right heavy
                if(height(node.right.left) - height(node.right.right) < 0) {
                    // right right case
                    return leftRotate(node);
                }
                if(height(node.right.left) - height(node.right.right) > 0) {
                    // left right case
                    node.right = rightRotate(node.right);
                    return leftRotate(node);
                }
            }
            return node;            
    }
     public Node rightRotate(Node p) {
        /*In a Parent, Child Grand-Child Structure on a left heavy tree,
        the right rotation will make the child the new parent and the parent the new child.*/
        Node child = p.left; // Leftchild of parent node
        Node t = child.right; // right child of child
        /* rotate the nodes
            the right of parent always remains the same , 
            the parent become the right of child
            the right of child becomes the left of parent */
        child.right = p; //parent become child
        p.left = t;// right child of child becomes left of parent
        // update the height of the nodes assigned
        // since parents is not the lowest node due to rotation we update that height first
        // then we update the child height
        p.height = Math.max(height(p.left), height(p.right) + 1);
        child.height = Math.max(height(child.left), height(child.right) + 1);

        return child; // return the new parent node (root of the subtree)
  }

  public Node leftRotate(Node child) {
  
    Node p = child.right;
    Node t = p.left;

    p.left = child;
    child.right = t;
    
    p.height = Math.max(height(p.left), height(p.right) + 1);
    child.height = Math.max(height(child.left), height(child.right) + 1);

    return p;
  }
  // Search for a productID
    public Node searchNode(Node node, int productId) {
        if (node == null){
            
            return node;
    }
        if (productID == node.getData().getId()){
            return node;
        }
        if (productID < node.getData().getId()){
            return searchNode(node.left, productId);
        }
        if (productID > node.getData().getId()){
            return searchNode(node.right, productId);
    }

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

            
}