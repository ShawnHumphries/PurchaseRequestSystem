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
			case "review":
			case "vendor":
				displayAllVendors();
				break;
			case "product":
				displayProductsByVendor();
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
			System.out.println("Code\t\tName\t\t\t\tAddress\t\t\t\tCity\t\tState\tZip\tPhone\t\tEMail\t\tPreApproved?");
			for (Vendor vendor : vendors)
			{
				System.out.print(vendor.getCode() + "\t" + vendor.getName() + "\t" + vendor.getAddress() + "\t" + vendor.getCity() + "\t" + vendor.getState() + "\t" + vendor.getZip());
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
	
	private static void displayProductsByVendor() {
		
		ArrayList<Product> productsByVendor = productDAO.getProductsByVendorID(2);
		if (productsByVendor.size() > 0)
		{
			System.out.println("Name\t\t\t\tPartNumber\tPrice\tUnit\tPhotoPath");
			for (Product product : productsByVendor)
			{
				System.out.print(product.getName() + "\t" + product.getPartNumber() + "\t" + StringUtil.getFormattedDouble(product.getPrice()));
				System.out.println("\t" + product.getUnit() + "\t" + product.getPhotoPath());
			}
		}
		else
			System.out.println("No products found for this vendor.");
		System.out.println();
	}
	
}
