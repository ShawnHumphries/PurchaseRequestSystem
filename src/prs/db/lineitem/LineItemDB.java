package prs.db.lineitem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import prs.business.LineItem;
import prs.business.Request;
import prs.util.DBUtil;

public class LineItemDB implements LineItemDAO {

	ArrayList<LineItem> lineItems = null;
	
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

	@Override
	public ArrayList<LineItem> getLineItemsByRequestID(int inRequestID) {

		lineItems = new ArrayList<>();
		String sql = "SELECT ID, RequestID, ProductID, Quantity "
				+ "from lineitems "
				+ "where RequestID = ? "
				+ "order by ID";
        Connection connection = DBUtil.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(sql))
        {
        	ps.setInt(1, inRequestID);
            ResultSet rs = ps.executeQuery();
        	while (rs.next())
        	{
        		LineItem li = getLineItemFromRow(rs);
        		lineItems.add(li);
        	}
        }
        catch (SQLException e)
        {
        	System.out.println(e);
        	return null;
        }
		return lineItems;
	}

	private static LineItem getLineItemFromRow(ResultSet rs) throws SQLException {
		
		LineItem lineItem = new LineItem();
		
		lineItem.setId(rs.getInt("ID"));
		lineItem.setRequestID(rs.getInt("RequestID"));
		lineItem.setProductID(rs.getInt("ProductID"));
		lineItem.setQuantity(rs.getInt("Quantity"));
		
		return lineItem;
	}
}
