package prs.db.user;

import java.sql.*;

import prs.business.User;
import prs.util.DBUtil;

public class UserDB implements UserDAO {

	@Override
	public boolean addUser(User inUser) {
		String sql = "INSERT INTO users (UserName, Password, FirstName, LastName, Phone, EMail, Manager) VALUES (?, ?, ?, ?, ?, ?, ?)";
		Connection connection = DBUtil.getConnection();
		try (PreparedStatement ps = connection.prepareStatement(sql))
		{
			ps.setString(1, inUser.getUsername());
			ps.setString(2, inUser.getPassword());
			ps.setString(3, inUser.getFirstName());	
			ps.setString(4, inUser.getLastName());
			ps.setString(5, inUser.getPhone());
			ps.setString(6, inUser.getEmail());
			ps.setBoolean(7, inUser.isManager());
			ps.executeUpdate();
		}
		catch (SQLException e)
		{
			System.out.println(e);
			return false;
		}
		return true;
	}
	
}
