package prs.db.product;

import java.util.ArrayList;

import prs.business.Product;

public interface ProductReader {

	ArrayList<Product> getAllProducts();
	ArrayList<Product> getProductsByVendorID(int vendorID);
	Product getProductByProductID(int productID);
}
