package prs.db.lineitem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import prs.business.LineItem;
import prs.util.DBUtil;

public class LineItemDB implements LineItemDAO {

	@Override
	public boolean addLineItem(LineItem inLineItem) {

		String sql = "INSERT INTO lineitems (RequestID, ProductID, Quantity) VALUES (?, ?, ?)";
		Connection connection = DBUtil.getConnection();
		try (PreparedStatement ps = connection.prepareStatement(sql))
		{
			ps.setInt(1, inLineItem.getRequestID());
			ps.setInt(2, inLineItem.getProductID());
			ps.setInt(3, inLineItem.getQuantity());	
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
