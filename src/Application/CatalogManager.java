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

package Application;

public class CatalogManager {
    private LinkedList<Product> linkedList;
    private AVL avl;

    public CatalogManager() {
        linkedList = new LinkedList<Product>();
        avl = new AVL();
    }

    public LinkedList<Product> getLinkedList() {
        return linkedList;
    }

    public void setLinkedList(LinkedList<Product> linkedList) {
        this.linkedList = linkedList;
    }

    public void setAvl(AVL avl) {
        this.avl = avl;
    }

    public void insertProductAtFront(Product product) {
        linkedList.insertAtFront(product);// insert the product into the linked list
        avl.setHead(avl.insertProduct(product, avl.getHead()));// insert the product into the AVL tree
    }

    public void insertProductAtBack(Product product) {
        linkedList.insertAtBack(product);// insert the product into the linked list
        avl.setHead(avl.insertProduct(product, avl.getHead()));// insert the product into the AVL tree
    }

    public void deleteProduct(int productId) {
        avl.setHead(avl.deleteProduct(avl.getHead(), productId));// delete the product from the AVL tree
        if (avl.getHead() == null) {
            return;
        }else{
        Product removed = linkedList.deleteNode(avl.getHead().getData());// delete the product from the linked list
        if(avl.getHead().getData().getProductId() == removed.getProductId()) { // if the AVL root is the same as the removed product
            System.out.println("Product #"+removed.getProductId()+"- "+removed.getName()+" has been removed from the catalog.");
        }
    }
}

    public void updateProduct(Object identifier, Product updates) {
        AVL.Node found = avl.productSearch(avl.getHead(),(int) identifier);
        if (found != null) {
            found.setData(updates); // updates the AVL node directly
            linkedList.updateNode(found.getData().getProductId(), updates);// updates the linked list
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

    public LinkedList<Product> getList() {
        return linkedList;
    }

    public AVL getAvl() {
        return avl;
    }
     public static void main(String[] args) {
        CatalogManager catalog = new CatalogManager();// create a CatalogManager object

        // Insert some products
        catalog.insertProductAtBack(new Product(101, "Laptop", "Gaming Laptop", 999.99, 5));
        catalog.insertProductAtBack(new Product(102, "Headphones", "Noise Cancelling", 199.99, 10));
        catalog.insertProductAtBack(new Product(103, "Mouse", "Wireless Mouse", 29.99, 50));
        catalog.insertProductAtFront(new Product(100, "Monitor", "24-inch Monitor", 159.99, 8));
        catalog.insertProductAtBack(new Product(104, "Mouse pad", "10-inch Mouse Pad", 19.99, 15));
        catalog.insertProductAtBack(new Product(105, "Keyboard", "Mechanical RGB Keyboard", 89.99, 25));
        catalog.insertProductAtBack(new Product(106, "Webcam", "1080p HD USB Webcam", 49.99, 15));
        catalog.insertProductAtBack(new Product(107, "USB-C Hub", "6-in-1 Multiport Adapter", 34.99, 40));
        catalog.insertProductAtBack(new Product(108, "Bluetooth Speaker", "Waterproof portable speaker", 59.99, 20));
        catalog.insertProductAtBack(new Product(109, "Power Bank", "10000mAh portable charger", 25.99, 60));
        catalog.insertProductAtBack(new Product(110, "Phone Case", "Shockproof case for iPhone", 14.99, 100));
        catalog.insertProductAtBack(new Product(111, "Wireless Earbuds", "Noise-isolating Bluetooth earbuds", 79.99, 35));
        catalog.insertProductAtBack(new Product(112, "Screen Protector", "Tempered glass, 2-pack", 9.99, 200));
        catalog.insertProductAtBack(new Product(113, "Printer", "All-in-One Wireless Printer", 149.99, 10));
        catalog.insertProductAtBack(new Product(114, "Desk Lamp", "LED Lamp with USB Charging Port", 29.99, 30));
        catalog.insertProductAtBack(new Product(115, "Notebook", "College-ruled spiral notebook", 3.99, 150));
        catalog.displayList();
        catalog.displayTree();
        // Save to file
        FileManagement fm = new FileManagement();
        java.util.LinkedList<String[]> productList = new java.util.LinkedList<>();

        Node<Product> current = catalog.getLinkedList().getHead();
        while (current != null) {
            Product p = current.getData();
            if (p != null) {
                String[] productRecord = new String[] {
                        String.valueOf(p.getProductId()),
                        p.getName(),
                        p.getDescription(),
                        String.valueOf(p.getPrice()),
                        String.valueOf(p.getStockQuantity())
                };
                productList.add(productRecord);
            }
            current = current.getNextNode();
        }

        fm.UpdateObjectsInFile("products.txt", productList);
        System.out.println("Products saved to products.txt!");
    }

}
 
