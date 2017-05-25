package prs.db.request;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import prs.business.Request;
import prs.util.DBUtil;

public class RequestDB implements RequestDAO {

	ArrayList<Request> requests = null;
	
	@Override
	public boolean createRequest(Request inRequest) {

		String insertSql = "INSERT INTO requests (Description, Justification, DateNeeded, UserID, DeliveryMode, DocsAttached, Status, Total, SubmittedDate) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
		Connection connection = DBUtil.getConnection();
		try (PreparedStatement ps = connection.prepareStatement(insertSql))
		{
			ps.setString(1, inRequest.getDescription());
			ps.setString(2, inRequest.getJustification());
			ps.setDate(3, (Date) inRequest.getDateNeeded());
			ps.setInt(4, inRequest.getUserID());	
			ps.setString(5, inRequest.getDeliveryMode());
			ps.setBoolean(6, inRequest.isDocsAttached());
			ps.setString(7, inRequest.getStatus());
			ps.setDouble(8, inRequest.getTotal());
			ps.setDate(9, (Date) inRequest.getSubmittedDate());
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
	public ArrayList<Request> getRequestsByUserID(int userID) {

		requests = new ArrayList<>();
		String sql = "SELECT ID, Description, Justification, DateNeeded, DeliveryMode, DocsAttached, Status, Total, SubmittedDate "
				+ "from requests "
				+ "where UserID = ? "
				+ "order by ID";
        Connection connection = DBUtil.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(sql))
        {
        	ps.setInt(1, userID);
            ResultSet rs = ps.executeQuery();
        	while (rs.next())
        	{
        		Request r = getRequestFromRow(rs);
        		requests.add(r);
        	}
        }
        catch (SQLException e)
        {
        	System.out.println(e);
        	return null;
        }
		return requests;
	}
	
	@Override
	public ArrayList<Request> getPendingRequests() {

		requests = new ArrayList<>();
		String sql = "SELECT ID, Description, Justification, DateNeeded, UserID, DeliveryMode, DocsAttached, Status, Total, SubmittedDate "
				+ "from requests "
				+ "where status = 'Submitted' OR status = 'Pending' "
				+ "order by ID";
        Connection connection = DBUtil.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(sql))
        {
            ResultSet rs = ps.executeQuery();
        	while (rs.next())
        	{
        		Request r = getRequestFromRow(rs);
        		requests.add(r);
        	}
        }
        catch (SQLException e)
        {
        	System.out.println(e);
        	return null;
        }
		return requests;
	}

	@Override
	public int getLastInsertID() {

		String lastInsertIDSql = "SELECT LAST_INSERT_ID()";
		//String lastInsertIDSql = "SELECT ID FROM requests ORDER BY ID DESC";
		Connection connection = DBUtil.getConnection();
		try (PreparedStatement ps = connection.prepareStatement(lastInsertIDSql))
		{
            ResultSet rs = ps.executeQuery();
            if (rs.next())
            {
            	return rs.getInt(1);
            }
            else
            {
            	rs.close();
            	return -1;
            }
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			return -1;
		}
	}

	@Override
	public boolean updateRequestStatus(int inRequestID, String inStatus) {

		String updateSql = "UPDATE requests SET Status = ? WHERE ID = ?";
		Connection connection = DBUtil.getConnection();
		try (PreparedStatement ps = connection.prepareStatement(updateSql))
		{
        	ps.setString(1, inStatus);
        	ps.setInt(2, inRequestID);
			ps.executeUpdate();
		}
		catch (SQLException e)
		{
			System.out.println(e);
			return false;
		}
		return true;
	}

	private static Request getRequestFromRow(ResultSet rs) throws SQLException
	{
        Request request = new Request();

        request.setId(rs.getInt("ID"));
        request.setDescription(rs.getString("Description"));
        request.setJustification(rs.getString("Justification"));
        request.setDateNeeded(rs.getDate("DateNeeded"));
        request.setUserID(rs.getInt("UserID"));
        request.setDeliveryMode(rs.getString("DeliveryMode"));
        request.setDocsAttached(rs.getBoolean("DocsAttached"));
        request.setStatus(rs.getString("Status"));
        request.setTotal(rs.getDouble("Total"));
        request.setSubmittedDate(rs.getDate("SubmittedDate"));
    	return request;
	}

}
