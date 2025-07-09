package Application;

public class Queue {
    private QueueNode front;
    private QueueNode rear;
    
    public Queue() {
        front = null;
        rear = null;
    }
    
    public boolean isEmpty() {
        return front == null;
    }
    
    public void add(int orderNumber) {
        QueueNode temp = new QueueNode(orderNumber);
        if (rear == null) {
            front = rear = temp;
        } else {
            rear.setNextNode(temp);
            temp.setPrevNode(rear);
            rear = temp;
        }
    }
    
    public OrderNumber dequeue() {
        if (isEmpty()) {
            return null;
        }
        
        QueueNode temp = front;
        OrderNumber data = temp.getData();
        front = front.getNextNode();
        
        if (front == null) {
            rear = null;
        } else {
            front.setPrevNode(null);
        }
        
        // Help garbage collection
        temp.setNextNode(null);
        temp.setPrevNode(null);
        
        return data;
    }
    
 // Linear search version that maintains original positions
    public int findOrderNumberPosition(int orderNumber) {
        if (isEmpty()) {
            return -1;
        }
        
        int position = 0;
        QueueNode current = front;
        
        while (current != null) {
            if (current.getData().getOrderNumber() == orderNumber) {
                return position;
            }
            position++;
            current = current.getNextNode();
        }
        
        return -1;
    }
    
    
    public int countNodes() {
        int count = 0;
        QueueNode current = front;
        while (current != null) {
            count++;
            current = current.getNextNode();
        }
        return count;
    }
    
    // Getters and setters...
    public QueueNode getFront() { return front; }
    public QueueNode getRear() { return rear; }
    public void setFront(QueueNode n) { front = n; }
    public void setRear(QueueNode n) { rear = n; }
}