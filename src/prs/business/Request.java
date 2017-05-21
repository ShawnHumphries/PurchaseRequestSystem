package prs.business;

import java.util.Date;

/*
 * The Request class represents a request object that contains information about
 * a purchase request that a user has made for a product.
 */

public class Request {

	private int id;
	private String description;
	private String justification;
	private Date dateNeeded;
	private int userID;
	private String deliveryMode;
	private boolean docsAttached;
	private String status;
	private double total;
	private Date submittedDate;
	
	// Default constructor
	public Request() {
		id = 0;
		description = "";
		justification = "";
		dateNeeded = null;
		userID = 0;
		deliveryMode = "";
		docsAttached = false;
		status = "";
		total = 0.0;
		submittedDate = null;
	}
	
	public Request(String description, String justification, Date dateNeeded, int userID, String deliveryMode, boolean docAttached, String status, double total, Date submittedDate) {
		this.description = description;
		this.justification = justification;
		this.dateNeeded = dateNeeded;
		this.userID = userID;
		this.deliveryMode = deliveryMode;
		this.docsAttached = docAttached;
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

	public Date getDateNeeded() {
		return dateNeeded;
	}

	public void setDateNeeded(Date dateNeeded) {
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

	public boolean isDocsAttached() {
		return docsAttached;
	}

	public void setDocsAttached(boolean docsAttached) {
		this.docsAttached = docsAttached;
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

	public Date getSubmittedDate() {
		return submittedDate;
	}

	public void setSubmittedDate(Date submittedDate) {
		this.submittedDate = submittedDate;
	}
	
}
