package prs.db.vendor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import prs.business.Vendor;
import prs.util.DBUtil;

public class VendorDB implements VendorDAO {

	private ArrayList<Vendor> vendors = null;
	
	@Override
	public ArrayList<Vendor> getAllVendors() {

		vendors = new ArrayList<>();
		String sql = "SELECT ID, Code, Name, Address, City, State, Zip, Phone, EMail, PreApproved " 
				+ "from vendors "
				+ "order by ID";
        Connection connection = DBUtil.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(sql);
                ResultSet rs = ps.executeQuery())
        {
        	while (rs.next())
        	{
        		Vendor v = getVendorFromRow(rs);
        		vendors.add(v);
        	}
        }
        catch (SQLException e)
        {
        	System.out.println(e);
        	return null;
        }
		return vendors;
	}

	private static Vendor getVendorFromRow(ResultSet rs) throws SQLException
	{
        Vendor vendor = new Vendor();

        vendor.setId(rs.getInt("ID"));
        vendor.setCode(rs.getString("Code"));
        vendor.setName(rs.getString("Name"));
        vendor.setAddress(rs.getString("Address"));
        vendor.setCity(rs.getString("City"));
        vendor.setState(rs.getString("State"));
        vendor.setZip(rs.getString("Zip"));
        vendor.setPhone(rs.getString("Phone"));
        vendor.setEmail(rs.getString("EMail"));
        vendor.setPreapproved(rs.getBoolean("PreApproved"));
    	
    	return vendor;
	}

}
