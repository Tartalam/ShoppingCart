/*
  Author: Tyreese Dunbar 
  ID: 2004439
  Module: CIT2004
  Course: Object Oriented Programming-UM2
  Date: 4th July, 2025  
  Description: CatalogManager class is used to syncronize and simultaniously call similar methods between the AVL class and the Linkedlist
            class so both classes will have updated data and the same data. This class fosters the easy lookup and 
            modification(including delete, add and removal) of data.
*/

package shopping.cart.system;

public class CatalogManager {
    private LinkedList linkedList;
    private AVL avl;

    public CatalogManager() {
        linkedList = new LinkedList();
        avl = new AVL();
    }

    public LinkedList getLinkedList() {
        return linkedList;
    }

    public void setLinkedList(LinkedList linkedList) {
        this.linkedList = linkedList;
    }

    public void setAvl(AVL avl) {
        this.avl = avl;
    }

    public void insertProductAtFront(ProductCat product) {
        linkedList.insertAtFront(product);// insert the product into the linked list
        avl.setHead(avl.insertProduct(product, avl.getHead()));// insert the product into the AVL tree
    }

    public void insertProductAtBack(ProductCat product) {
        linkedList.insertAtBack(product);// insert the product into the linked list
        avl.setHead(avl.insertProduct(product, avl.getHead()));// insert the product into the AVL tree
    }

    public void deleteProduct(int productId) {
        avl.setHead(avl.deleteProduct(avl.getHead(), productId));// delete the product from the AVL tree
        if (avl.getHead() == null) {
            return;
        }else{
        ProductCat removed = linkedList.deleteNode(productId);// delete the product from the linked list
        if(avl.getHead().getData().getProductId() == removed.getProductId()) { // if the AVL root is the same as the removed product
            System.out.println("Product #"+removed.getProductId()+"- "+removed.getName()+" has been removed from the catalog.");
        }
    }
}

    public void updateProduct(int productId, ProductCat updatedProduct, String fileName) {
        linkedList.updateNode(productId, updatedProduct, fileName);
        AVL.Node found = avl.productSearch(avl.getHead(), productId);
        if (found != null) {
            found.setData(updatedProduct); // updates the AVL node directly
        }
    }

    public void displayList() {
        getLinkedList().displayList();
    }

    public void displayTree() {
        getAvl().display();
    }

    public boolean searchProduct(int productId) {
        AVL.Node node = avl.productSearch(avl.getHead(), productId); //search for product using avl tree and return it to a node of the avl class
        if (node != null) {
            System.out.println("Found: " + node.getData());
            return true;
        }
        System.out.println("Product not found.");
        return false;
    }

    public LinkedList getList() {
        return linkedList;
    }

    public AVL getAvl() {
        return avl;
    }
}