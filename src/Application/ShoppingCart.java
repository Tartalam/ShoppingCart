package Application;

import java.io.*;
import java.util.*;

public class ShoppingCart {
  
    private Stack<CartOperation> operationStack = new Stack<>();
    private Map<Integer, Integer> cart = new HashMap<>();
    private Map<Integer, Product> catalog;

    private static final String CART_FILE = "cart.ser";
    private static final String STACK_FILE = "stack.ser";

    public ShoppingCart(Map<Integer, Product> catalog){
        this.catalog = catalog;
        loadFromFile();
    }

    public void addToCart(int productId, int quantity){

        Product productItem = catalog.get(productId);
        if(productItem == null || productItem.getStockQuantity() < quantity){
            System.out.println("INVALID PRODUCT OR INSUFFICIENT STOCK");
            return;
        }

        productItem.decreaseStock(quantity);
        cart.put(productId, cart.getOrDefault(productId,0) + quantity);
        operationStack.push(new CartOperation(CartOperation.OperationType.ADD, productId, quantity));
        System.out.println("Item added to cart");
        saveToFile();

    }

    public void removeFromCart(int productId){
        if(!cart.containsKey(productId)){
            System.out.println("Item not in cart");
            return;
        }

        int quantity = cart.get(productId);
        cart.remove(productId);
        catalog.get(productId).increaseStock(quantity);
        operationStack.push(new CartOperation(CartOperation.OperationType.REMOVE, productId, quantity));
        System.out.println("Item removed from cart");
        saveToFile();
    }


    public void undoLastAction(){
        if(operationStack.isEmpty()){
            System.out.println("Nothing to undo");
            return;
        }

        CartOperation lastOp = operationStack.pop();
        int productId = lastOp.getProductId();
        int quantity = lastOp.getQuantity();

        if(lastOp.getType() == CartOperation.OperationType.ADD){
            cart.put(productId, cart.get(productId) - quantity);
            if( cart.get(productId) <= 0) cart.remove(productId);
            catalog.get(productId).increaseStock(quantity);
            System.out.println("Undo: Removed item from cart");
        }
        else if(lastOp.getType() == CartOperation.OperationType.REMOVE){
            cart.put(productId, quantity);
            catalog.get(productId).decreaseStock(quantity);
            System.out.println("Undo: Re-added item to cart");
        }

        saveToFile();
    }

    public void viewCart(){
        if (cart.isEmpty()){
            System.out.println("Cart is empty ");
            return;
        }

        System.out.println("Cart Contents:");
        for (Map.Entry<Integer, Integer> entry : cart.entrySet()){
            Product productItem = catalog.get(entry.getKey());
            System.out.println(productItem.getName() + " X "+ entry.getValue() + " ($" + productItem.getPrice() + ")" );
        }
    }

    private void saveToFile(){
        try (ObjectOutputStream cartOut = new ObjectOutputStream(new FileOutputStream("cart.ser"));
            ObjectOutputStream  stackOut = new ObjectOutputStream(new FileOutputStream("stack.ser"))){

                cartOut.writeObject(cart);
                stackOut.writeObject(operationStack);
            }
        catch (IOException e){
            System.out.println("Error saving cart: " + e.getMessage());
        }
    }


    @SuppressWarnings("unchecked")
    private void loadFromFile(){
        try(ObjectInputStream cartIn = new ObjectInputStream(new FileInputStream("cart.ser"));
            ObjectInputStream stackIn = new ObjectInputStream(new FileInputStream("stack.ser"))){
                
                cart = (Map<Integer, Integer>) cartIn.readObject();
                operationStack = (Stack<CartOperation>) stackIn.readObject();
            }
        catch (IOException | ClassNotFoundException e){
            System.out.println("No saved cart data found.");
        }
    }



}
