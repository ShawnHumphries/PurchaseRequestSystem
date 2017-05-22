package prs.presentation;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import prs.business.Product;
import prs.business.User;
import prs.business.Vendor;
import prs.db.DAOFactory;
import prs.db.product.ProductDAO;
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

	public static void main(String[] args) {

		String command = "";

		System.out.println("Welcome to the Purchase Request System");
		
		/*
		Date myDate = new Date(); 
		System.out.println(myDate); 
		System.out.println(new SimpleDateFormat("MM-dd-yyyy").format(myDate)); 
		System.out.println(new SimpleDateFormat("yyyy-MM-dd").format(myDate)); 
		*/

		while (!command.equalsIgnoreCase("exit")) {
			displayMenu();
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
				createPurchaseRequest();
				break;
			case "review":
			case "vendor":
				displayAllVendors();
				break;
			case "product":
				displayProductsByVendor(0);
			case "help":
				break;
			case "exit":
				break;
			}
		}
	}

	/*
	 * Display a command menu.
	 */
	private static void displayMenu() {
		System.out.println("COMMAND MENU");
		System.out.println("register\t- Register a user");
		System.out.println("request\t- Create a request");
		System.out.println("review\t- Review submitted requests");
		System.out.println("vendor\t- Display vendor info");
		System.out.println("product\t- Display product info per vendor");
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
	
	private static void createPurchaseRequest() {
		
		String username = Validator.getString(sc, "Enter a user name: ");
		String password = Validator.getString(sc, "Enter password: ");
		User user = userDAO.getUserByUserNameAndPassword(username, password);
		if (user == null) {
			System.out.println("The username was invalid.  The purchase request cannot continue.");
		}
		else {
			String description = Validator.getString(sc, "Enter the description: ");
			String justification = Validator.getString(sc, "Enter the justification: ");
			System.out.println("By what date does this request need to be fulfilled? ");
			String dateNeeded = Validator.getString(sc, "\tBe sure to enter the date ('yyyy-mm-dd') for now: ", 10);
			String deliveryMode = Validator.getString(sc, "Enter the delivery mode (pickup or mail): ");
			String docAttached = Validator.getString(sc, "Is documentation attached? (y/n) ", 1);
			boolean isdocAttached = false;
			if (docAttached.equalsIgnoreCase("Y"))
				isdocAttached = true;
			String status = "Submitted";
			
			System.out.println("Here is a list of vendors...");
			System.out.println("Please try to work with pre-approved vendors first.");
			displayAllVendors();
			int vendorID = Validator.getInt(sc, "Enter the ID of the vendor to buy products from: ");
			
			displayProductsByVendor(vendorID);
			
			// To do: loop until user is done picking products and quantity.  Calculate total
			
			System.out.println("Enter today's date...");
			String submittedDate = Validator.getString(sc, "\tBe sure to enter the date ('yyyy-mm-dd') for now: ", 10);
		}
		System.out.println();
	}
}
