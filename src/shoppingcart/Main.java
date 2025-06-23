package shoppingcart;
import java.util.*;

public class Main{
    public static void main(String[] args){

        Map<Integer, Product> catalog = new HashMap<>();
        catalog.put(1,new Product(1, "Laptop", "NoteBook laptop", 1200.00, 10) );
        catalog.put(2,new Product(2, "Phone", "Iphone XS MAX", 1200.00, 10) );
        catalog.put(3,new Product(3, "Mouse", "Wireless Mouse", 1200.00, 10) );

        ShoppingCart cart = new ShoppingCart(catalog);
        Scanner scanner = new Scanner(System.in);
        int choice;

        do{
             System.out.println("\nShopping Cart Menu:");
            System.out.println("1. View Products");
            System.out.println("2. Add to Cart");
            System.out.println("3. Remove from Cart");
            System.out.println("4. View Cart");
            System.out.println("5. Undo Last Action");
            System.out.println("0. Exit");
            System.out.print("Choice: ");
            choice = scanner.nextInt();

            switch(choice){
                case 1:
                    for( Product p: catalog.values()){
                        System.out.println(p);
                    }
                    break;
                case 2:
                    System.out.print("Enter Product ID:");
                    int ADDid = scanner.nextInt();
                    System.out.print("Enter Quantity: ");
                    int quantity = scanner.nextInt();
                    cart.addToCart(ADDid, quantity);
                    break;
                case 3:
                    System.out.print("Enter Product ID to remove: ");
                    int removeId = scanner.nextInt();
                    cart.removeFromCart(removeId);
                    break;
                case 4:
                    cart.viewCart();
                    break;
                case 5:
                    cart.undoLastAction();
                    break;
            }

        }while (choice != 0);
        scanner.close();
    }
}