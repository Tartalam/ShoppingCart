package shopping.cart.system;

public class Driver {
    public static void main(String[] args) {
        CatalogManager catalog = new CatalogManager();// create a CatalogManager object

        // Insert some products
        catalog.insertProductAtBack(new ProductCat(101, "Laptop", "Gaming Laptop", 999.99, 5));
        catalog.insertProductAtBack(new ProductCat(102, "Headphones", "Noise Cancelling", 199.99, 10));
        catalog.insertProductAtBack(new ProductCat(103, "Mouse", "Wireless Mouse", 29.99, 50));
        catalog.insertProductAtFront(new ProductCat(100, "Monitor", "24-inch Monitor", 159.99, 8));
        catalog.insertProductAtBack(new ProductCat(104, "Mouse pad", "10-inch Mouse Pad", 19.99, 15));
        catalog.insertProductAtBack(new ProductCat(105, "Keyboard", "Mechanical RGB Keyboard", 89.99, 25));
        catalog.insertProductAtBack(new ProductCat(106, "Webcam", "1080p HD USB Webcam", 49.99, 15));
        catalog.insertProductAtBack(new ProductCat(107, "USB-C Hub", "6-in-1 Multiport Adapter", 34.99, 40));
        catalog.insertProductAtBack(new ProductCat(108, "Bluetooth Speaker", "Waterproof portable speaker", 59.99, 20));
        catalog.insertProductAtBack(new ProductCat(109, "Power Bank", "10000mAh portable charger", 25.99, 60));
        catalog.insertProductAtBack(new ProductCat(110, "Phone Case", "Shockproof case for iPhone", 14.99, 100));
        catalog.insertProductAtBack(new ProductCat(111, "Wireless Earbuds", "Noise-isolating Bluetooth earbuds", 79.99, 35));
        catalog.insertProductAtBack(new ProductCat(112, "Screen Protector", "Tempered glass, 2-pack", 9.99, 200));
        catalog.insertProductAtBack(new ProductCat(113, "Printer", "All-in-One Wireless Printer", 149.99, 10));
        catalog.insertProductAtBack(new ProductCat(114, "Desk Lamp", "LED Lamp with USB Charging Port", 29.99, 30));
        catalog.insertProductAtBack(new ProductCat(115, "Notebook", "College-ruled spiral notebook", 3.99, 150));
        catalog.displayList();
        catalog.displayTree();
        // Save to file
        FileManagement fm = new FileManagement();
        java.util.LinkedList<String[]> productList = new java.util.LinkedList<>();

        Node current = catalog.getLinkedList().getHead();
        while (current != null) {
            ProductCat p = current.getProduct();
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
