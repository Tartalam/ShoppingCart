package Application;

public class CLinkedList<T extends Identifiable> {
    private Node<T> head;
    
    public CLinkedList() {
        this.head = null;
    }
    
    public CLinkedList(Node<T> head) {
        this.head = head;        
    }
    
    public Node<T> getHead() {
        return head;
    }
    
    public void setHead(Node<T> head) {
        this.head = head;
    }
    
    public void insertAtFront(T dataToInsert) {
        Node<T> temp = new Node<>(dataToInsert);
        
        if(head == null) {
            head = temp;
        }
        else {
            temp.setNextNode(head);
            head = temp;
        }
    }

    public void insertAtBack(T dataToInsert) {
        Node<T> temp1 = new Node<>(dataToInsert);
        
        if(head == null) {
            head = temp1;
        }
        else {
            Node<T> temp2 = head;
            while(temp2.getNextNode() != null) {
                temp2 = temp2.getNextNode();
            }
            temp2.setNextNode(temp1);
        }
    }
    
    public int countNodes() {
        int count = 0;
        Node<T> curr = head;
        while(curr != null) {
            count++;
            curr = curr.getNextNode();
        }
        return count;
    }
    
    public void displayList() {
        Node<T> curr = head;   
        while (curr != null) {
            System.out.print(curr.getData() + "\n");
            curr = curr.getNextNode();
        }
        System.out.println("NULL");
    }
    
    public boolean isEmpty() {
        return head == null;
    }
    
    /**
     * Updates a node in the list that matches the given identifier
     * @param identifier For Products: the product ID (Integer)
     *                  For Passwords: the old password (String)
     * @param updates The object containing updates
     * @return true if update was successful, false if node wasn't found or update failed
     */
    public boolean updateNode(Object identifier, T updates) {
        Node<T> current = head;
        
        while (current != null) {
            if (current.getData().matchByIdOrPassword(identifier)) {
                // Found the node, perform the update
                return current.getData().updateFrom(updates);
            }
            current = current.getNextNode();
        }
        
        return false; // Node with given identifier not found
    }
    
    public T deleteNode(T dataToDelete) {
        if(isEmpty()) {
            System.err.println("The list is empty; There is nothing to delete!");
            return null;
        }
        
        Node<T> curr = head, prev = null;
        
        while(curr != null) {
            if(curr.getData().equals(dataToDelete)) {
                if(curr == head) {
                    head = head.getNextNode();
                }
                else {
                    prev.setNextNode(curr.getNextNode());
                }
                return curr.getData();
            }
            prev = curr;
            curr = curr.getNextNode();
        }
        
        System.err.println("Data not found in the list");
        return null;
    }
}