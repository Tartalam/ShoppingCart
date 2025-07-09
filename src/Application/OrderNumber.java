package Application;

import java.util.Random;

public class OrderNumber {
    private int orderNumber;
    
    public OrderNumber() {
        orderNumber = 0;
    }
    
    public OrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }
    
    public int generateOrderNumber() {
        Random randomNum = new Random();
        setOrderNumber(randomNum.nextInt(999 - 111 + 1) + 111);
        return getOrderNumber();
    }
    
    @Override
    public String toString() {
        return String.valueOf(orderNumber);
    }
}