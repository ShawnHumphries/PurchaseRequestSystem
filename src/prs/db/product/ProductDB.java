package prs.db.product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import prs.business.Product;
import prs.util.DBUtil;

public class ProductDB implements ProductDAO {

	private ArrayList<Product> products = null;
	
	@Override
	public ArrayList<Product> getAllProducts() {

		products = new ArrayList<>();
		String sql = "SELECT ID, Name, PartNumber, Price, Unit, VendorID, PhotoPath " 
				+ "from products "
				+ "order by Name, ID";
        Connection connection = DBUtil.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(sql);
                ResultSet rs = ps.executeQuery())
        {
        	while (rs.next())
        	{
        		Product p = getProductFromRow(rs);
        		products.add(p);
        	}
        }
        catch (SQLException e)
        {
        	System.out.println(e);
        	return null;
        }
		return products;
	}

	@Override
	public ArrayList<Product> getProductsByVendorID(int vendorID) {

		products = new ArrayList<>();
		String sql = "SELECT ID, Name, PartNumber, Price, Unit, VendorID, PhotoPath "
				+ "from products "
				+ "where VendorID = ? "
				+ "order by Name";
        Connection connection = DBUtil.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(sql))
        {
        	ps.setInt(1, vendorID);
            ResultSet rs = ps.executeQuery();
        	while (rs.next())
        	{
        		Product p = getProductFromRow(rs);
        		products.add(p);
        	}
        }
        catch (SQLException e)
        {
        	System.out.println(e);
        	return null;
        }
		return products;
	}

	@Override
	public Product getProductByProductID(int productID) {

		String sql = "SELECT ID, Name, PartNumber, Price, Unit, VendorID, PhotoPath "
				+ "from products "
				+ "where ID = ?";
        Connection connection = DBUtil.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(sql))
        {
        	ps.setInt(1, productID);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
            {
                return getProductFromRow(rs);
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

	private static Product getProductFromRow(ResultSet rs) throws SQLException
	{
        Product product = new Product();

        product.setId(rs.getInt("ID"));
        product.setName(rs.getString("Name"));
        product.setPartNumber(rs.getString("PartNumber"));
        product.setPrice(rs.getDouble("Price"));
        product.setUnit(rs.getString("Unit"));
        product.setVendorID(rs.getInt("VendorID"));
        product.setPhotoPath(rs.getString("PhotoPath"));
    	
    	return product;
	}

}
