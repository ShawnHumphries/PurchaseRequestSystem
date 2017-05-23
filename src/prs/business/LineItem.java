package prs.business;

/*
 * The LineItem class links a Request with a Product.
 */

public class LineItem {

	private int id;
	private int requestID;
	private int productID;
	private int quantity;
	
	// Default constructor
	public LineItem() {
		id = 0;
		requestID = 0;
		productID = 0;
		quantity = 0;
	}
	
	public LineItem(int requestID, int productID, int quantity) {
		this.requestID = requestID;
		this.productID = productID;
		this.quantity = quantity;
	}
	
	public LineItem(int productID, int quantity) {
		this.productID = productID;
		this.quantity = quantity;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getRequestID() {
		return requestID;
	}

	public void setRequestID(int requestID) {
		this.requestID = requestID;
	}

	public int getProductID() {
		return productID;
	}

	public void setProductID(int productID) {
		this.productID = productID;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
}
