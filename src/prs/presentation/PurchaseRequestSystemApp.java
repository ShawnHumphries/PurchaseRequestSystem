package prs.presentation;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import prs.business.User;
import prs.db.DAOFactory;
import prs.db.user.UserDAO;
import prs.util.Validator;

/*
 * This class is to test Java Business classes and database objects in the console.
 * The Java Project will utilize a web-based front end when completed. 
 */

public class PurchaseRequestSystemApp {

	private static Scanner sc = new Scanner(System.in);
	private static UserDAO userDAO = DAOFactory.getUserDAO();

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
		System.out.println("add\t- Add a user");
		System.out.println("req\t- Create a request");
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
				addNewUser();
				break;
			case "req":
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

	private static void addNewUser() {
		String userID = Validator.getString(sc, "Enter a user id: ");
		String password = Validator.getString(sc, "Enter password: ");
		String firstName = Validator.getString(sc, "Enter first name: ");
		String lastName = Validator.getString(sc, "Enter last name: ");
		String phoneNumber = Validator.getString(sc, "Enter phone number (xxxyyyzzzz)");
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
}
