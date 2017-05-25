package prs.db.user;

import prs.business.User;

public interface UserReader {

	User getUserByUserNameAndPassword(String username, String password);
	String getUserNameByID(int userID);
}
