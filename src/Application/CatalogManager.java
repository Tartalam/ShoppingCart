/*
  Author: Tyreese Dunbar 
  ID: 2004439
  Module: CIT2004
  Course: Object Oriented Programming-UM2
  Date: 4th July, 2025  
  Description: CatalogManager class synchronizes and simultaneously calls similar methods between the AVL class and the LinkedList
            class so both data structures maintain consistent data. This class facilitates easy lookup and 
            modification (including delete, add and removal) of product data.
*/
package Application;

import java.io.IOException;

public class CatalogManager {
    private CLinkedList<Product> linkedList;
    private AVL avl;
    private AVLFileManager avlFileManager;

    /**
     * Default constructor initializes both data structures.
     */
    public CatalogManager() {
        avlFileManager = new AVLFileManager();
        try {
            avl = avlFileManager.loadAVLTree();
            // Add this line to rebuild the linked list from the AVL tree
            rebuildLinkedListFromAVL();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading AVL tree: " + e.getMessage());
            avl = new AVL();
            linkedList = new CLinkedList<Product>();
        }
    }
    
    
    /**
     * Saves the current AVL tree to file
     */
    public void saveAVLTree() {
        try {
            avlFileManager.saveAVLTree(avl);
        } catch (IOException e) {
            System.err.println("Error saving AVL tree: " + e.getMessage());
        }
    }

    // Getters and setters
    public CLinkedList<Product> getLinkedList() {
        return linkedList;
    }

    public void setLinkedList(CLinkedList<Product> linkedList) {
        this.linkedList = linkedList;
    }

    public AVL getAvl() {
        return avl;
    }

    public void setAvl(AVL avl) {
        this.avl = avl;
    }

    /**
     * Inserts a product at the front of both data structures.
     * @param product The product to insert
     */
    public void insertProductAtFront(Product product) {
    	if (product == null)
    	{
    		throw new IllegalArgumentException("Product cant be null");
    	}
    	try 
    	{
    		 
    		linkedList.insertAtFront(product);
    	    avl.insertProduct(product);
    		
    	}catch(Exception e) {
    		System.err.println("error Inserting product: " + e.getMessage());
    		throw e;
    	}
       
    }

    /**
     * Inserts a product at the back of both data structures.
     * @param product The product to insert
     */
    public void insertProductAtBack(Product product) {
    	if (product == null)
    	{
    		throw new IllegalArgumentException("Product cant be null");
    	}
    	try 
    	{
    		 
    		linkedList.insertAtBack(product);
    	    avl.insertProduct(product);
    		
    	}catch(Exception e) {
    		System.err.println("error Inserting product: " + e.getMessage());
    		throw e;
    	}
    }

    /**
     * Deletes a product from both data structures by ID.
     * @param productId The ID of the product to delete
     */
    public void deleteProduct(int productId) {
        try {
            // First find the product in AVL tree
            AVL.Node node = avl.productSearch(productId);
            if (node != null) {
                Product productToRemove = node.getData();
                
                // Delete from AVL tree
                avl.deleteProduct(productId);
                
                // Delete from linked list using the product object from AVL
                Product removed = linkedList.deleteNode(productToRemove);
                
                if (removed != null) {
                    System.out.println("Product #"+removed.getProductId()+" deleted from both structures");
                } else {
                    // If not found in linked list, rebuild the list from AVL
                    System.out.println("Rebuilding linked list from AVL tree...");
                    rebuildLinkedListFromAVL();
                    // Try deletion again
                    linkedList.deleteNode(productToRemove);
                }
            } else {
                System.err.println("Product with ID " + productId + " not found");
            }
        } catch (Exception e) {
            System.err.println("Error deleting product: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Rebuilds the linked list from the AVL tree to ensure synchronization
     */
    public void rebuildLinkedListFromAVL() {
    	linkedList = new CLinkedList<>();
        if (avl != null && avl.getHead() != null) {
            inOrderTraversal(avl.getHead());
        }
    }

    private void inOrderTraversal(AVL.Node node) {
        if (node != null) {
            inOrderTraversal(node.getLeft());
            linkedList.insertAtBack(node.getData());
            inOrderTraversal(node.getRight());
        }
    }

    /**
     * Updates a product in both data structures.
     * @param productId The ID of the product to update
     * @param updates The updated product data
     * @return true if update was successful, false otherwise
     */
    public boolean updateProduct(int productId, Product updates) {
        // Update in AVL tree
    	try 
    	{
    		boolean avlUpdated = avl.modifyProduct(productId, updates);
    		if (avlUpdated) {
                // Update in linked list
                linkedList.updateNode(productId, updates);
                return true;
            }
            return false;
    		
    	}catch( Exception e) {
    		System.err.println("error updating product: " + e.getMessage());
    		throw e;
    	}
        
        
    }

    /**
     * Displays all products in the linked list.
     */
    public void displayList() {
        linkedList.displayList();
    }

    /**
     * Displays the AVL tree structure.
     */
    public void displayTree() {
        System.out.println(avl.getTreeStructure());
    }

    /**
     * Searches for a product by ID.
     * @param productId The ID to search for
     * @return The found product, or null if not found
     */
    public Product searchProduct(int productId) {
    	try {
            AVL.Node node = avl.productSearch(productId);
            if (node != null) {
                System.out.println("Found: " + node.getData());
                return node.getData();
            }
            System.out.println("Product not found.");
            return null;
    		
    	} catch (Exception e) {
    		System.err.println("Error Product cannot be found: "+ e.getMessage());
    		throw e;
    	}

    }

    /**
     * Gets the linked list of products.
     * @return The linked list containing all products
     */
    public CLinkedList<Product> getList() {
        return linkedList;
    }
}