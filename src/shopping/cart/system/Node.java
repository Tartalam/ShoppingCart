package shopping.cart.system;

public class Node {

	private ProductCat product;
	private Node nextNode;
	
	public Node() {
		this.product = new ProductCat();
		this.nextNode = null;
	}
	
	public Node(ProductCat product, Node nextNode) {
		this.product = new ProductCat(product);
		this.nextNode = nextNode;
	}
	public Node(ProductCat product) {
		product = new ProductCat(product);
		nextNode = null;
	}
	public Node(int productId, String name, String description, double price, int stockQuantity) {
		product =new ProductCat(productId, name, description, price, stockQuantity);
		nextNode = null;
	}
	public Node(Node node) {
		this.product = node.product;
		this.nextNode = node.nextNode;
	}

	public ProductCat getProduct() {
		return product;
	}

	public void setProduct(ProductCat product) {
		this.product = product;
	}

	public Node getNextNode() {
		return nextNode;
	}

	public void setNextNode(Node nextNode) {
		this.nextNode = nextNode;
	}
}
