package shopping.cart.system;

public class ProductCat {

	private int productId;
	private String name;
	private String description;
	private double price;
	private int stockQuantity;
	
	public ProductCat() {
		productId = 0;
		name = "";
		description = "";
		price = 0.0;
		stockQuantity = 0;
	}
	
	public ProductCat(int productId, String name, String description, double price, int stockQuantity) {
		this.productId = productId;
		this.name = name;
		this.description = description;
		this.price = price;
		this.stockQuantity = stockQuantity;
	}
	
	public ProductCat(ProductCat product) {
		this.productId = product.productId;
		this.name = product.name;
		this.description = product.description;
		this.price = product.price;
		this.stockQuantity = product.stockQuantity;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getStockQuantity() {
		return stockQuantity;
	}

	public void setStockQuantity(int stockQuantity) {
		this.stockQuantity = stockQuantity;
	}
	
	public void display() {
		System.out.println("Product ID: " + productId + "\nName: " +  name 
							+ "Price: " + price + "\tStock Quantity: " + stockQuantity 
							+ "Description: " + description);
	}

	@Override
	public String toString() {
		return "\nProduct ID: " + productId + " \nName: " + name + "\nDescription: " + description + "\nPrice: "
				+ price + "\tStock Quantity: " + stockQuantity ;
	}
	
}
