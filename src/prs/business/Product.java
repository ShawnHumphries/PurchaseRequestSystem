package prs.business;

/*
 * The Product class represents a Product that can be ordered by a User via
 * a Request by a User.  Products are kept in stocked by Vendors.
 */

public class Product {

	private int id;
	private String name;
	private String partNumber;
	private double price;
	private String unit;
	private int vendorID;
	private String photoPath;
	
	// Default constructor
	public Product() {
		id = 0;
		name = "";
		partNumber = "";
		price = 0.0;
		unit = "";
		vendorID = 0;
		photoPath = "";
	}
	
	public Product(String name, String partNumber, double price, String unit, int vendorID, String photoPath) {
		this.name = name;
		this.partNumber = partNumber;
		this.price = price;
		this.unit = unit;
		this.vendorID = vendorID;
		this.photoPath = photoPath;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPartNumber() {
		return partNumber;
	}

	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public int getVendorID() {
		return vendorID;
	}

	public void setVendorID(int vendorID) {
		this.vendorID = vendorID;
	}

	public String getPhotoPath() {
		return photoPath;
	}

	public void setPhotoPath(String photoPath) {
		this.photoPath = photoPath;
	}

}
