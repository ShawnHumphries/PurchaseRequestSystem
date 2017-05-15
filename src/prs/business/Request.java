package prs.business;

/*
 * The Request class represents a request object that contains information about
 * a purchase request that a user has made for a product.
 */

public class Request {

	private int id;
	private String description;
	private String justification;
	private String dateNeeded;
	private int userID;
	private String deliveryMode;
	private boolean docAttached;
	private String status;
	private double total;
	private String submittedDate;
	
	// Default constructor
	public Request() {
		id = 0;
		description = "";
		justification = "";
		dateNeeded = "";
		userID = 0;
		deliveryMode = "";
		docAttached = false;
		status = "";
		total = 0.0;
		submittedDate = "";
	}
	
	public Request(String description, String justification, String dateNeeded, int userID, String deliveryMode, boolean docAttached, String status, double total, String submittedDate) {
		this.description = description;
		this.justification = justification;
		this.dateNeeded = dateNeeded;
		this.userID = userID;
		this.deliveryMode = deliveryMode;
		this.docAttached = docAttached;
		this.status = status;
		this.total = total;
		this.submittedDate = submittedDate;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getJustification() {
		return justification;
	}

	public void setJustification(String justification) {
		this.justification = justification;
	}

	public String getDateNeeded() {
		return dateNeeded;
	}

	public void setDateNeeded(String dateNeeded) {
		this.dateNeeded = dateNeeded;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public String getDeliveryMode() {
		return deliveryMode;
	}

	public void setDeliveryMode(String deliveryMode) {
		this.deliveryMode = deliveryMode;
	}

	public boolean isDocAttached() {
		return docAttached;
	}

	public void setDocAttached(boolean docAttached) {
		this.docAttached = docAttached;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public String getSubmittedDate() {
		return submittedDate;
	}

	public void setSubmittedDate(String submittedDate) {
		this.submittedDate = submittedDate;
	}
	
}
