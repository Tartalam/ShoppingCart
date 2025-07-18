// Author: Jahmari Harrison
// ID: 2304204
package Application;

import java.io.IOException;
import java.util.LinkedList;

/**
 * A thread-safe queue implementation for managing order numbers with bidirectional navigation.
 * Supports standard queue operations (enqueue/dequeue) and position tracking.
 * 
 * Features:
 * - Bidirectional node links (next/previous)
 * - Thread-safe operations (when properly synchronized)
 * - Position tracking for orders
 * - Comprehensive error handling
 */
public class Queue {
    private QueueNode front;
    private QueueNode rear;
    private static final String Queue_File = "Queue.csv";

    /**
     * Constructs an empty queue.
     */
    public Queue() {
        this.front = null;
        this.rear = null;
    }

    /**
     * Checks if the queue is empty.
     * @return true if queue is empty, false otherwise
     */
    public synchronized boolean isEmpty() {
        return front == null;
    }

    /**
     * Adds an order number to the end of the queue.
     * @param orderNumber The order number to add (must be positive)
     * @throws IllegalArgumentException if orderNumber is not positive
     */
    public synchronized void add(int orderNumber) {
        if (orderNumber <= 0) {
            throw new IllegalArgumentException("Order number must be positive");
        }

        try {
            QueueNode newNode = new QueueNode(orderNumber);
            
            if (rear == null) {
                front = rear = newNode;
            } else {
                newNode.setPrevNode(rear);
                rear.setNextNode(newNode);
                rear = newNode;
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to add order to queue", e);
        }
    }

    /**
     * Removes and returns the order at the front of the queue.
     * @return The order number that was removed
     * @throws IllegalStateException if queue is empty
     */
    public synchronized OrderNumber dequeue() {
        if (isEmpty()) {
            throw new IllegalStateException("Cannot dequeue from empty queue");
        }

        try {
            QueueNode temp = front;
            OrderNumber data = temp.getData();
            
            front = front.getNextNode();
            if (front == null) {
                rear = null;
            } else {
                front.setPrevNode(null);
            }
            
            // Clean up references
            temp.setNextNode(null);
            temp.setPrevNode(null);
            
            return data;
        } catch (Exception e) {
            throw new RuntimeException("Failed to dequeue order", e);
        }
    }

    /**
     * Finds the position of an order number in the queue.
     * @param orderNumber The order number to search for
     * @return Zero-based position if found, -1 if not found
     * @throws IllegalArgumentException if orderNumber is not positive
     */
    public synchronized int findOrderNumberPosition(int orderNumber) {
        if (orderNumber <= 0) {
            throw new IllegalArgumentException("Order number must be positive");
        }

        try {
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
        } catch (Exception e) {
            throw new RuntimeException("Failed to find order position", e);
        }
    }

    /**
     * Gets the current size of the queue.
     * @return Number of elements in the queue
     */
    public synchronized int countNodes() {
    	int count = 0;
        QueueNode current = front;
        while (current != null) {
            count++;
            current = current.getNextNode();
        }
        return count;

    }

    /**
     * Gets the front node of the queue.
     * @return Front node or null if empty
     */
    public synchronized QueueNode getFront() {
        return front;
    }

    /**
     * Gets the rear node of the queue.
     * @return Rear node or null if empty
     */
    public synchronized QueueNode getRear() {
        return rear;
    }

    /**
     * Sets the front node (for advanced use cases).
     * @param node The new front node
     */
    public synchronized void setFront(QueueNode node) {
        this.front = node;
        if (node == null) {
            this.rear = null;
            
        }
    }

    /**
     * Sets the rear node (for advanced use cases).
     * @param node The new rear node
     */
    public synchronized void setRear(QueueNode node) {
        this.rear = node;
        if (node == null) {
            this.front = null;
            
        }
    }
    
    /**
     * Converts the queue to a LinkedList of strings for file storage
     * @return LinkedList containing all order numbers as strings
     */
    public synchronized LinkedList<String> toLinkedList() {
        LinkedList<String> list = new LinkedList<>();
        QueueNode current = front;
        while (current != null) {
            list.add(current.toString());
            current = current.getNextNode();
        }
        return list;
    }

    /**
     * Reconstructs the queue from a LinkedList of strings
     * @param list LinkedList containing order numbers as strings
     */
    public synchronized void fromLinkedList(LinkedList<String> list) {
        this.front = null;
        this.rear = null;
        
        if (list == null || list.isEmpty()) {
            return;
        }
        
        for (String orderStr : list) {
            QueueNode node = QueueNode.fromString(orderStr);
            if (node != null) {
                if (rear == null) {
                    front = rear = node;
                } else {
                    node.setPrevNode(rear);
                    rear.setNextNode(node);
                    rear = node;
                }
            }
        }
    }
    
    /**
     * Loads queue data from file
     * @param fileName File to load from
     * @throws IOException if file operations fail
     */
    public synchronized void loadFromFile() throws IOException {
        FileManagement fileManager = new FileManagement();
        LinkedList<String[]> fileData = fileManager.readObjectsFromFile(Queue_File);
        
        LinkedList<String> orderNumbers = new LinkedList<>();
        for (String[] record : fileData) {
            if (record != null && record.length > 0) {
                orderNumbers.add(record[0]);
            }
        }
        
        this.fromLinkedList(orderNumbers);
    }
    
    /**
     * Saves queue data to file
     * @param fileName File to save to
     * @throws IOException if file operations fail
     */
    public synchronized void saveToFile() throws IOException {
        FileManagement fileManager = new FileManagement();
        LinkedList<String> orderNumbers = this.toLinkedList();
        
        // Convert to format FileManagement expects (LinkedList<String[]>)
        LinkedList<String[]> fileData = new LinkedList<>();
        for (String order : orderNumbers) {
            fileData.add(new String[]{order});
        }
        
        fileManager.updateObjectsInFile(Queue_File, fileData);
    }
}