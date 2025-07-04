package shopping.cart.system;

public class LinkedList {

	private Node head;
	
	public LinkedList() {
		this.head = null;
	}
	public LinkedList(Node head) {
		this.head = head;		
	}
	public Node getHead() {
		return head;
	}
	public void setHead(Node head) {
		this.head = head;
	}
	public void insertAtFront(ProductCat productToInsert) {
		Node temp;
		temp = new Node();
		if(temp != null) {
			temp.setProduct(productToInsert);
			temp.setNextNode(null);
			
		if(head == null) {
			head = temp;
		}
		else {
			temp.setNextNode(head);
			head = temp;
		}
		}
	else {
		System.err.println("Error! List is full. Cannot add a new node");
	}
}

public void insertAtBack(ProductCat productToInsert) {
	Node temp1;
	temp1 = new Node();
	if(temp1 != null) {
		temp1.setProduct(productToInsert);
		temp1.setNextNode(null);
		
		if(head == null) {
			head = temp1;
		}
		else {
			Node temp2 = head;
			while(temp2.getNextNode() != null){
				temp2 = temp2.getNextNode();
				}
		temp2.setNextNode(temp1);
	 } 
	}
    
else{ 
    System.err.println("Error! List is full (Out of Memory), can NOT add a new node"); 
} 
} 


public void insertAtBack(int productId, String name, String description, double price, int stockQuantity) { 
Node temp1 = new Node(productId, name, description, price, stockQuantity);
if(temp1 != null){ 
    if(head == null){ 
        head = temp1; 
    } 
    else{ 
        Node temp2 = head;
        while(temp2.getNextNode() !=  null) 
        { 
            temp2 = temp2.getNextNode();
        } 
        temp2.setNextNode(temp1);
    }
}
else { 
   System.err.println("Error! List is full (Out of Memory), can NOT add a new node"); 
} 
} 


public int countNodes() 
{ 
int count = 0;
Node curr = head;
while(curr != null){ 
    count++; //increment counter 
    curr = curr.getNextNode(); //point curr to IT'S next node 
} 
return count; //return number of elements in list 
} 


public boolean searchForANode(int productId){ 
boolean isFound = false; 
 
Node curr = head;  
while (curr != null){ 
    if(curr.getProduct().getProductId() == productId){ 
        isFound = true; 
        break; 
    } 
    curr = curr.getNextNode(); 
} 
 
return isFound; 
} 

public boolean updateNode(int productId, ProductCat updatedProduct, String fileName) {
    Node current = head;
    boolean updated = false;

    while (current != null) {
        if (current.getProduct().getProductId() == productId) {
            current.getProduct().setName(updatedProduct.getName());
            current.getProduct().setDescription(updatedProduct.getDescription());
            current.getProduct().setPrice(updatedProduct.getPrice());
            current.getProduct().setStockQuantity(updatedProduct.getStockQuantity());
            updated = true;
            break;
        }
        current = current.getNextNode();
    }

    if (updated) {
        // Build updated list for saving
        java.util.LinkedList<String[]> productList = new java.util.LinkedList<>();
        Node temp = head;
        while (temp != null) {
            ProductCat p = temp.getProduct();
            String[] record = new String[]{
                String.valueOf(p.getProductId()),
                p.getName(),
                p.getDescription(),
                String.valueOf(p.getPrice()),
                String.valueOf(p.getStockQuantity())
            };
            productList.add(record);
            temp = temp.getNextNode();
        }

        // Write to file
        FileManagement fm = new FileManagement();
        fm.UpdateObjectsInFile(fileName, productList);

        System.out.println("Product updated and saved successfully!");
    } else {
        System.out.println("Product with ID " + productId + " not found.");
    }

    return updated;
}


public void displayList() 
{ 
Node curr = head;   
while (curr != null){ 
    System.out.print(curr.getProduct() + "\n"); 
    curr = curr.getNextNode(); 
} 
System.out.println("NULL"); 
} 


public boolean isEmpty(){ 
if(head == null)
{ 
    return true;
} 
return false; 
} 

public ProductCat deleteNode(int productId){ 
 
ProductCat dataToReturn = new ProductCat(); 
 
if(!isEmpty()) 
{ 
    Node curr = head, prev=null; 
     
    while(curr != null){ 
        if(curr.getProduct().getProductId() == productId) 
        { 
            if(curr == head){ 
                head = head.getNextNode(); //point head to IT's next node 
            } 
            else 
            { 
                prev.setNextNode(curr.getNextNode());
            } 
            dataToReturn = curr.getProduct();  
            curr = null; 
            break; 
        } 
        prev = curr; 
        curr = curr.getNextNode();
    } 
 
} 
else{ 
    System.err.println("The Catalog is empty; There is nothing to delete!"); 
} 
 
return dataToReturn; 
} 

}
