package Application;

public class QueueNode {
	private QueueNode nextNode;
	private QueueNode prevNode;
	private OrderNumber data;
	
	public QueueNode() {
		data = new OrderNumber();
		nextNode = null;
		prevNode = null;
	}
	
	public QueueNode (int orderNum) {
		
		data = new OrderNumber(orderNum);
		nextNode = null;
		prevNode = null;
	}
	
	
	public QueueNode(OrderNumber data) {
		this.data = data;
		nextNode = null;
		prevNode = null;
		
	}
	
	public QueueNode getNextNode() {
		return nextNode;
	}
	
	public void setNextNode(QueueNode nextNode) {
		this.nextNode = nextNode;
		 if (nextNode != null && nextNode.prevNode != this) {
		        nextNode.setNextNode(this);
		    }
		
		
	}
	
	public QueueNode getPrevNode() {
		return prevNode;
	}
	
	public void setPrevNode(QueueNode prevNode) {
		this.prevNode = prevNode;
		
		 if (prevNode != null && prevNode.nextNode != this) {
		        prevNode.setNextNode(this);
		    }
		
	}
	
	public OrderNumber getData() {
		return data;
	}
	
	public void setData(OrderNumber data) {
		this.data = data;
	}
	
	 @Override
	    public String toString() {
	        return String.valueOf(data.getOrderNumber());
	    }
	    
	    // Add a static method to create QueueNode from string
	    public static QueueNode fromString(String str) {
	        if (str == null || str.trim().isEmpty()) {
	            return null;
	        }
	        try {
	            int orderNumber = Integer.parseInt(str.trim());
	            return new QueueNode(orderNumber);
	        } catch (NumberFormatException e) {
	            return null;
	        }catch(Exception e) {
	        	return null;
	        }
	    }
}
