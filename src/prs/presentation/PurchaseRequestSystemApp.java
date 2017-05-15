package prs.presentation;

import java.text.SimpleDateFormat;
import java.util.Date;

/*
 * This class is to test Java Business classes and database objects in the console.
 * The Java Project will utilize a web-based front end when completed. 
 */

public class PurchaseRequestSystemApp {

	public static void main(String[] args) {

		System.out.println("Welcome to the Purchase Request System");
		Date myDate = new Date(); 
		System.out.println(myDate); 
		System.out.println(new SimpleDateFormat("MM-dd-yyyy").format(myDate)); 
		System.out.println(new SimpleDateFormat("yyyy-MM-dd").format(myDate)); 
		
	}

}
