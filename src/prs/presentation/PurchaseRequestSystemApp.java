package prs.presentation;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Scanner;

import prs.business.LineItem;
import prs.business.Product;
import prs.business.Request;
import prs.business.User;
import prs.business.Vendor;
import prs.db.DAOFactory;
import prs.db.lineitem.LineItemDAO;
import prs.db.product.ProductDAO;
import prs.db.request.RequestDAO;
import prs.db.user.UserDAO;
import prs.db.vendor.VendorDAO;
import prs.util.StringUtil;
import prs.util.Validator;

/*
 * This class is to test Java Business classes and database objects in the console.
 * The Java Project will utilize a web-based front end when completed. 
 */

public class PurchaseRequestSystemApp {

	private static Scanner sc = new Scanner(System.in);
	private static UserDAO userDAO = DAOFactory.getUserDAO();
	private static VendorDAO vendorDAO = DAOFactory.getVendorDAO();
	private static ProductDAO productDAO = DAOFactory.getProductDAO();
	private static RequestDAO requestDAO = DAOFactory.getRequestDAO();
	private static LineItemDAO lineItemDAO = DAOFactory.getLineItemDAO();

	public static void main(String[] args) {

		String command = "";

		System.out.println("Welcome to the Purchase Request System");
		
		String username = Validator.getString(sc, "Enter a user name: ");
		String password = Validator.getString(sc, "Enter password: ");

		User user = userDAO.getUserByUserNameAndPassword(username, password);
		if (user == null) {
			System.out.println("The username was invalid.  The login failed.");
		}
		else {	// Display the main menu (or go to the home page if this was a web application
			while (!command.equalsIgnoreCase("exit")) {
				displayMenu(user);
				// Get the command from the user
				command = getCommand(sc, "Enter a command: ");
				System.out.println();
				// Perform the command 
				switch (command) {
				case "add":
				case "register":
					registerNewUser();
					break;
				case "request":
					createPurchaseRequest(user);
					break;
				case "review":
					displayPurchaseRequests(user);
					break;
				case "vendor":
					displayAllVendors();
					break;
				case "product":
					displayProductsByVendor(0);
					break;
				case "approve":
					approvePendingRequests();
					break;
				case "help":
					break;
				case "exit":
					break;
				}
			}
		}
	}

	/*
	 * Display a command menu.
	 */
	private static void displayMenu(User user) {
		System.out.println("COMMAND MENU");
		System.out.println("register\t- Register a user");
		System.out.println("request\t- Create a request");
		System.out.println("review\t- Review submitted requests");
		System.out.println("vendor\t- Display vendor info");
		System.out.println("product\t- Display product info per vendor");
		if (user.isManager()) {
			System.out.println("approve\t- Approve a request");
		}
		System.out.println("help\t- Show this menu");
		System.out.println("exit\t- Exit this application");
		System.out.println();
	}

	public static String getCommand(Scanner sc, String prompt) {
		String command = "";
		boolean isValid = false;
		while (isValid == false) {
			command = Validator.getString(sc, prompt);
			// Make sure the command the user entered is valid
			switch (command) {
			case "add":
			case "register":
			case "request":
			case "review":
			case "vendor":
			case "product":
			case "approve":
			case "help":
			case "exit":
				// The command is valid
				isValid = true;
				break;
			default:
				System.out.println("Error!  The command entered is invalid.  Please enter a valid command.\n");
				break;
			}
		}
		return command;
	}

	private static void registerNewUser() {
		String userID = Validator.getString(sc, "Enter a user id: ");
		String password = Validator.getString(sc, "Enter password: ");
		String firstName = Validator.getString(sc, "Enter first name: ");
		String lastName = Validator.getString(sc, "Enter last name: ");
		String phoneNumber = Validator.getString(sc, "Enter phone number (xxxyyyzzzz): ");
		String email = Validator.getString(sc, "Enter email: ");
		String manager = Validator.getString(sc, "Is the user a manager (y/n) ");
		boolean isManager = false;
		if (manager.equalsIgnoreCase("y"))
			isManager = true;
		User newUser = new User(userID, password, firstName, lastName, phoneNumber, email, isManager);
		if (userDAO.addUser(newUser))
			System.out.println("\nUser ID " + userID + " has been added.\n");
		else
			System.out.println("\nUser ID " + userID + " was not added successfully.\n");
	}
	
	private static void displayAllVendors() {
		
		ArrayList<Vendor> vendors = vendorDAO.getAllVendors();
		if (vendors.size() > 0)
		{
			System.out.println("ID\tCode\t\tName\t\t\t\tAddress\t\t\t\tCity\t\tState\tZip\tPhone\t\tEMail\t\tPreApproved?");
			for (Vendor vendor : vendors)
			{
				System.out.print(vendor.getId() + "\t" + vendor.getCode() + "\t" + vendor.getName() + "\t" + vendor.getAddress() + "\t" + vendor.getCity() + "\t" + vendor.getState() + "\t" + vendor.getZip());
				System.out.print("\t" + vendor.getPhone() + "\t" + vendor.getEmail() + "\t");
				if (vendor.isPreapproved())
					System.out.println("Yes");
				else
					System.out.println("No");
			}
		}
		else
			System.out.println("There are no vendors to purchase products from.");
		System.out.println();

	}
	
	private static void displayProductsByVendor(int vendorID) {
		
		// If the vendor ID is zero, we must prompt for one!
		if (vendorID == 0) {
			System.out.println("Here is a list of vendors...");
			displayAllVendors();
			vendorID = Validator.getInt(sc, "Enter a vendor ID: ");
		}
		ArrayList<Product> productsByVendor = productDAO.getProductsByVendorID(vendorID);
		if (productsByVendor.size() > 0)
		{
			System.out.println("\nProducts for this vendor...");
			System.out.println("ID\tName\t\t\t\tPartNumber\tPrice\tUnit\tPhotoPath");
			for (Product product : productsByVendor)
			{
				System.out.print(product.getId() + "\t" + product.getName() + "\t" + product.getPartNumber() + "\t" + StringUtil.getFormattedDouble(product.getPrice()));
				System.out.println("\t" + product.getUnit() + "\t" + product.getPhotoPath());
			}
		}
		else
			System.out.println("No products found for this vendor.");
		System.out.println();
	}
	
	private static void createPurchaseRequest(User user) {

		int productID = -1;
		int quantity = 0;
		ArrayList <LineItem> lineItems = new ArrayList<>();
		
		String description = Validator.getString(sc, "Enter the description: ");
		String justification = Validator.getString(sc, "Enter the justification: ");
		System.out.println("By what date does this request need to be fulfilled? ");
		String neededDate = Validator.getString(sc, "\tBe sure to enter the date ('yyyy-mm-dd') for now: ", 10);
		Date dateNeeded = StringUtil.convertStringToSQLDate(neededDate);
		String deliveryMode = Validator.getString(sc, "Enter the delivery mode (pickup or mail): ");
		String docAttached = Validator.getString(sc, "Is documentation attached? (y/n) ", 1);
		boolean isDocAttached = false;
		if (docAttached.equalsIgnoreCase("Y"))
			isDocAttached = true;
		
		System.out.println("Here is a list of vendors...");
		System.out.println("Please try to work with pre-approved vendors first.");
		displayAllVendors();
		int vendorID = Validator.getInt(sc, "Enter the ID of the vendor to buy products from: ");
		
		displayProductsByVendor(vendorID);
		
		double total = 0.0;
		// To do: loop until user is done picking products and quantity.  Calculate total
		System.out.println("Enter products by ID and then quantity.  Enter '0' for ID when finished.\n");
		while (productID != 0) {
			productID = Validator.getInt(sc, "Enter a product ID: ");
			if (productID > 0) {
				quantity = Validator.getInt(sc, "Enter the quantity: ");
				// Get the price of the product and add that to the total of the purchase request
				if (quantity > 0) {
					LineItem lineitem = new LineItem(productID, quantity);
					lineItems.add(lineitem);
					Product p = productDAO.getProductByProductID(productID);
					total += p.getPrice() * quantity;
				}
				else {
					System.out.println("Error: quantity must be greater than or equal to zero.");
				}
			}
		}
		
		// set the status of the request.  All requests under $50.00 are automatically approved.
		String status = "Submitted";
		if (total < 50.00)
			status = "Approved";
		
		Date submittedDate = StringUtil.convertTodaysDateToSQLDate();

		// Create a request object and add it to the database (this is done at the same time as the lineitems are added...but for now we just want to see if this works
		Request request = new Request(description, justification, dateNeeded, user.getId(), deliveryMode, isDocAttached, status, total, submittedDate);
		if (requestDAO.createRequest(request)) {
			// The INSERT was successful...so now get the ID of the INSERT
			int requestID = requestDAO.getLastInsertID();
			System.out.println("The request ID is: " + requestID);
			for (LineItem lineItem : lineItems) {
				lineItem.setRequestID(requestID);
				lineItemDAO.addLineItem(lineItem);
			}
			System.out.println("\nYour request has been added successfully.\n");
		}
		else
			System.out.println("\nYour request was not added successfully.\n");
		System.out.println();
	}
	
	private static void displayPurchaseRequests(User user) {
		// Get all of the requests in the DB for the user
		ArrayList<Request> requests = requestDAO.getRequestsByUserID(user.getId());
		if (requests != null) {
			System.out.println("Product Name\t\tDate Submitted\tStatus\t\tDate Needed");
			for (Request request : requests) {
				// Get all of the lineitems in the DB for the request
				ArrayList<LineItem> lineItems = lineItemDAO.getLineItemsByRequestID(request.getId());
				for (LineItem lineItem : lineItems) {
					// Get the product information for each line item
					Product product = productDAO.getProductByProductID(lineItem.getProductID());
					// *** We will not display any vendor information ***
					// Display information relevant to the purchase request to the console
					System.out.println(product.getName() + "\t" + request.getSubmittedDate() + "\t" + request.getStatus() + "\t" + request.getDateNeeded());
				}
			}
		}
		else {
			System.out.println("\nUser " + user.getUsername() + " has no requests.");
		}
		System.out.println();
	}
	
	private static void approvePendingRequests() {
		
		// To Do: get a list of pending requests and display them in the console
		ArrayList<Request> pendingRequests = requestDAO.getPendingRequests();
		if (pendingRequests != null) {
			System.out.println("\nHere is a list of pending purchase requests:");
			System.out.println();
			int requestID = Validator.getInt(sc, "Enter a request to act upon: ");
			String status = Validator.getString(sc, "Enter the new request status (Approved or Rejected)");
			if (requestDAO.updateRequestStatus(requestID, status)) {
				System.out.println("The request was updated successfully.  Thank you for your time.");
			}
			else {
				System.out.println("The request was not updated successfully.");
			}
		}
		else {
			System.out.println("\nThere are no requests to approve");
		}
	}
}
