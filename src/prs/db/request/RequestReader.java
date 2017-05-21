package prs.db.request;

import java.util.ArrayList;

import prs.business.Request;

public interface RequestReader {

	ArrayList <Request> getRequestsByUserID(int userID);

}
