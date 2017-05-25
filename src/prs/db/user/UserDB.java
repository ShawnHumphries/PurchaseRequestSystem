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

	@Override
	public User getUserByUserNameAndPassword(String username, String password) {

		String sql = "SELECT ID, UserName, Password, FirstName, LastName, Phone, Email, Manager "
				+ "from users "
				+ "where UserName = ? AND Password = ?";
        Connection connection = DBUtil.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(sql))
        {
        	ps.setString(1, username);
        	ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
            {
                return getUserFromRow(rs);
            }
            else
            {
            	rs.close();
            	return null;
            }
        }
        catch (SQLException e)
        {
        	System.out.println(e);
        	return null;
        }
	}

	@Override
	public String getUserNameByID(int userID) {

		String sql = "SELECT UserName "
				+ "from users "
				+ "where ID = ?";
        Connection connection = DBUtil.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(sql))
        {
        	ps.setInt(1, userID);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
            {
                return rs.getString("UserName");
            }
            else
            {
            	rs.close();
            	return "";
            }
        }
        catch (SQLException e)
        {
        	System.out.println(e);
        	return "";
        }
	}

	private static User getUserFromRow(ResultSet rs) throws SQLException
	{
        User user = new User();

        user.setId(rs.getInt("ID"));
        user.setUsername(rs.getString("UserName"));
        user.setPassword(rs.getString("Password"));
        user.setFirstName(rs.getString("FirstName"));
        user.setLastName(rs.getString("LastName"));
        user.setPhone(rs.getString("Phone"));
        user.setEmail(rs.getString("EMail"));
        user.setManager(rs.getBoolean("Manager"));
    	
    	return user;
	}

}
