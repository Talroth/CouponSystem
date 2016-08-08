package com.CouponSystem.DBDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.NoSuchElementException;

import com.CouponSystem.Beans.*;
import com.CouponSystem.DAO.*;
import com.CouponSystem.FilterDBDao.FilterCouponDBDao;

import DAOException.DAOException;
import DAOException.DAOExceptionErrorType;

//
//Implementation of coupon table bean from DB
//

public class CouponDBDAO implements CouponDAO 
{
	private DBDAO pool = DBDAO.getInstance();
	private static CouponDBDAO couponDbDao = new CouponDBDAO();
	
	//
	// Constructors
	//
	
	private CouponDBDAO()
	{
		
	}
	
	// 
	// methods
	//
	
	public static CouponDBDAO getInstance()
	{
		return couponDbDao;
	}
	private void createOrUpdateCoupon(Coupon coupon,String sql,boolean isUpdate,Company company) throws DAOException
	{
		int aff = 0;
		if (coupon == null || (company == null && !isUpdate))
		{
			throw new DAOException(DAOExceptionErrorType.NEW_COUPON_FAILED);
		}
		
		// for the rest there is a default value
		if (coupon.getTitle() == null) { throw new DAOException(DAOExceptionErrorType.NEW_COUPON_FAILED); }
		if (coupon.getStartDate() == null) { throw new DAOException(DAOExceptionErrorType.NEW_COUPON_FAILED); }
		if (coupon.getEndDate() == null) { throw new DAOException(DAOExceptionErrorType.NEW_COUPON_FAILED); }
		if (coupon.getType() == null) { throw new DAOException(DAOExceptionErrorType.NEW_COUPON_FAILED); }
		if (coupon.getMessage() == null) { throw new DAOException(DAOExceptionErrorType.NEW_COUPON_FAILED); }

		
		try (Connection con = pool.OpenConnection();)
		{	
			PreparedStatement newCouponStatement = null;
			PreparedStatement companyCouponStatement = null;
			
			// query command
			con.setAutoCommit(false);
			try
			{
				newCouponStatement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				
				newCouponStatement.setString(1, coupon.getTitle());
				newCouponStatement.setTimestamp(2, Timestamp.valueOf(coupon.getStartDate()));
				newCouponStatement.setTimestamp(3,Timestamp.valueOf(coupon.getEndDate()));
				newCouponStatement.setInt(4,coupon.getAmount());
				newCouponStatement.setString(5,coupon.getType().getText()); 
				newCouponStatement.setString(6,coupon.getMessage());
				newCouponStatement.setDouble(7,coupon.getPrice());
				newCouponStatement.setString(8,coupon.getImage());

				if (isUpdate)
				{
					newCouponStatement.setLong(9,coupon.getId());
				}
				
				// query execution
				aff = newCouponStatement.executeUpdate();
				
				//get id of the new customer from the db
				if (!isUpdate)
				{
					ResultSet rs = newCouponStatement.getGeneratedKeys();
					if (rs.next())
					{
						coupon.setId(rs.getLong(1));
						String sqlJoin = "INSERT INTO couponsystem.company_coupon(COMP_ID,COUPON_ID) VALUES (?,?)";
						companyCouponStatement = con.prepareStatement(sqlJoin);
						companyCouponStatement.setLong(1, company.getId());
						companyCouponStatement.setLong(2, coupon.getId());
						
						companyCouponStatement.executeUpdate();						
					}
					
				}
				
				con.commit();
			}

			catch (DAOException  eInner)
			{
				String message = null;
				if (eInner instanceof DAOException)
				{
					message = coupon.getTitle();
				}
			
				con.rollback();
				
				if (eInner.getMessage() == "Connection was not established")
				{
					throw new DAOException(DAOExceptionErrorType.CONNECTION_CLOSED, "Connection error, please refer to system admin");
				}
				
				if (isUpdate)
				{
					throw new DAOException(DAOExceptionErrorType.UPDATE_COUPON_FAILED, "Fail to update " + message);	
				}
				else
				{
					throw new DAOException(DAOExceptionErrorType.NEW_COUPON_FAILED, "Fail to create " + message);	
				}				
			}

			finally
			{
				con.setAutoCommit(true);
				if (newCouponStatement != null) { newCouponStatement.close(); }
				if (companyCouponStatement != null) { companyCouponStatement.close(); }
			}
		}

			// close prepared statement, report number of new records and return the number	

		catch (SQLException eOuterSQL)
		{
			if (aff == 0)
			{
				throw new DAOException(DAOExceptionErrorType.NEW_COUPON_FAILED, "Fail to create " + coupon.getTitle());
			}
			else
			{
				throw new DAOException(DAOExceptionErrorType.COMPLETED,"Id of the coupon was not idetified but new coupon was created in the db");
			}
		}
	}
	
	@Override
	public void createCoupon(Coupon coupon, Company company) throws DAOException
	{
		if (coupon == null || company == null) { throw new DAOException(DAOExceptionErrorType.NEW_COUPON_FAILED); }
		
		String sql = "INSERT INTO couponsystem.coupon(TITLE,START_DATE,END_DATE,AMOUNT,TYPE,MESSAGE,PRICE,IMAGE) VALUES (?,?,?,?,?,?,?,?)";
		this.createOrUpdateCoupon(coupon, sql,false,company);
	}

	@Override
	public void removeCoupon(Coupon coupon) throws DAOException
	{
		if (coupon == null) { throw new DAOException(DAOExceptionErrorType.REMOVE_COUPON_FAILED); }
		try (Connection con = pool.OpenConnection())
		{
			// Initialize all statements
			PreparedStatement preparedStatementDeleteLinkToCompany = null;
			PreparedStatement preparedStatementDeleteLinkToCustomer = null;
			PreparedStatement preparedStatementDeleteCoupons = null;
			
			long couponID = coupon.getId();
			
			con.setAutoCommit(false);
			try
			{
				
				// query command
				// Remove the link between the coupon and the company who own it	
				String sqlDeleteLinkToCompany = "DELETE FROM couponsystem.company_coupon WHERE COUPON_ID = ?";
				preparedStatementDeleteLinkToCompany = con.prepareStatement(sqlDeleteLinkToCompany);
				preparedStatementDeleteLinkToCompany.setLong(1, couponID);
						
				preparedStatementDeleteLinkToCompany.executeUpdate();
				
				// Remove the link between the coupon and the customers who own it	
				String sqlDeleteLinkToCustomer = "DELETE FROM couponsystem.customer_coupon WHERE COUPON_ID = ?";
				preparedStatementDeleteLinkToCustomer = con.prepareStatement(sqlDeleteLinkToCustomer);
				preparedStatementDeleteLinkToCustomer.setLong(1, couponID);
						
				preparedStatementDeleteLinkToCustomer.executeUpdate();
				
				// Delete the coupon from the coupons table
				String sqlDeleteCoupons = "DELETE FROM couponsystem.coupon WHERE ID = ?";
				preparedStatementDeleteCoupons = con.prepareStatement(sqlDeleteCoupons);
				preparedStatementDeleteCoupons.setLong(1, couponID);
				
				// query execution
				preparedStatementDeleteCoupons.executeUpdate();
				
				con.commit();
				
				// once the coupon is not exists in the db, the Bean also need to be "removed"
				coupon = null;
				
				System.out.println("Coupon id " + couponID + " was removed");
			}
			catch (SQLException eInner)
			{
				con.rollback();
				if (eInner.getMessage() == "Connection was not established")
				{
					throw new DAOException(DAOExceptionErrorType.CONNECTION_CLOSED, "Connection error, please refer to system admin");
				}
				throw new DAOException(DAOExceptionErrorType.REMOVE_COMPANY_FAILED, "Fail to remove " + coupon.getTitle());	
			}
			finally
			{
				con.setAutoCommit(true);
				if (preparedStatementDeleteLinkToCompany != null) { preparedStatementDeleteLinkToCompany.close(); }
				if (preparedStatementDeleteLinkToCustomer != null) { preparedStatementDeleteLinkToCustomer.close(); }
				if (preparedStatementDeleteCoupons != null) { preparedStatementDeleteCoupons.close(); }
			}
			
		}
		
		catch (SQLException eOuter)
		{
			if (eOuter.getMessage() == "Connection was not established")
			{
				throw new DAOException(DAOExceptionErrorType.CONNECTION_CLOSED, "Connection error, please refer to system admin");
			}
			throw new DAOException(DAOExceptionErrorType.REMOVE_COUPON_FAILED, "Fail to remove " + coupon.getTitle());
		}
	}

	@Override
	public void updateCoupon(Coupon coupon) throws DAOException
	{
		if (coupon == null) { throw new DAOException(DAOExceptionErrorType.UPDATE_COUPON_FAILED); }
		
		String sql = "UPDATE couponsystem.coupon SET TITLE = ? , START_DATE = ? , END_DATE = ? , AMOUNT = ? , TYPE = ? , MESSAGE = ? , PRICE = ? , IMAGE = ? WHERE Id = ?";
		this.createOrUpdateCoupon(coupon, sql,true,null);
	}

	
	@Override
	public Coupon getCoupon(long id) throws DAOException
	{
		String sql = "SELECT * FROM couponsystem.coupon WHERE ID = ?";
		try (Connection con = pool.OpenConnection(); PreparedStatement preparedStatement = con.prepareStatement(sql);)
		{	
			// query command
						
			preparedStatement.setLong(1, id);
			
			// query execution
			ResultSet rs = preparedStatement.executeQuery();

			return this.BuildCoupons(rs).iterator().next();

		}
		catch (SQLException | NoSuchElementException e)
		{
			if (e.getMessage() == "Connection was not established")
			{
				throw new DAOException(DAOExceptionErrorType.CONNECTION_CLOSED, "Connection error, please refer to system admin");
			}
			
			throw new DAOException(DAOExceptionErrorType.COUPON_DETAILS_FAILED_TO_RETRIEVE, "Fail to find coupon id: " + Long.toString(id));
		}

	}

	@Override
	public Collection<Coupon> getCoupon(String filters) throws DAOException
	{
		String sql = "SELECT * FROM couponsystem.coupon WHERE " + filters;
		try (Connection con = pool.OpenConnection(); Statement Statement = con.createStatement();)
		{	

			ResultSet rs = Statement.executeQuery(sql);

			return this.BuildCoupons(rs);
		}
		catch (SQLException e)
		{
			if (e.getMessage() == "Connection was not established")
			{
				throw new DAOException(DAOExceptionErrorType.CONNECTION_CLOSED, "Connection error, please refer to system admin");
			}
			
			throw new DAOException(DAOExceptionErrorType.COUPON_DETAILS_FAILED_TO_RETRIEVE,"Failed to retreieve data for coupons by filter");
		}
	}
	
	@Override
	public Collection<Coupon> getCouponByType(CouponType couponType) throws DAOException
	{
		String sql = "SELECT * FROM couponsystem.coupon WHERE TYPE='?'";
		try (Connection con = pool.OpenConnection(); PreparedStatement preparedStatement = con.prepareStatement(sql);)
		{	
			// query command
						
			preparedStatement.setString(1, couponType.getText());
			
			// query execution
			ResultSet rs = preparedStatement.executeQuery();
			return this.BuildCoupons(rs);
		}
		catch (SQLException e)
		{
			if (e.getMessage() == "Connection was not established")
			{
				throw new DAOException(DAOExceptionErrorType.CONNECTION_CLOSED, "Connection error, please refer to system admin");
			}
			
			throw new DAOException(DAOExceptionErrorType.COUPON_DETAILS_FAILED_TO_RETRIEVE, "Failed to retreieve data for coupons from type: " + couponType.getText());
		}
	}

	@Override
	public Collection<Coupon> getAllCoupons() throws DAOException
	{
		String sql = "SELECT * FROM couponsystem.coupon";
		try (Connection con = pool.OpenConnection(); Statement statement = con.createStatement();)
		{	
			// query execution
			ResultSet rs = statement.executeQuery(sql);
			return this.BuildCoupons(rs);
		}
		catch (SQLException e)
		{
			if (e.getMessage() == "Connection was not established")
			{
				throw new DAOException(DAOExceptionErrorType.CONNECTION_CLOSED, "Connection error, please refer to system admin");
			}
			
			throw new DAOException(DAOExceptionErrorType.COUPON_DETAILS_FAILED_TO_RETRIEVE,"Failed to retreieve data for all coupons");
		}
	}


	public Collection<Coupon> BuildCoupons(ResultSet rs) throws DAOException
	{
		if (rs == null) { throw new DAOException(DAOExceptionErrorType.MISSING_ARGUMENT); }
		
		Collection<Coupon> custs = new ArrayList<Coupon>();
		Coupon cust = null;
		
		try
		{
			// Get number of fields for coupon table
			
			ResultSetMetaData rsmd = rs.getMetaData();
			int rsColumnSize = rsmd.getColumnCount();
			
			while (rs.next())
			{
				
				// Check if there are null value for any value
				
				for (int i = 1; i <= rsColumnSize; i++)
				{
					if (rs.getObject(i) == null)
					{
						throw new DAOException(DAOExceptionErrorType.NEW_COUPON_OBJECT_FAILED,"Coupon has missing data");
					}
				}
				
				//Fill coupon object from Coupon table	
				
				cust = new Coupon();
				cust.setId(rs.getLong("ID"));
				cust.setTitle(rs.getString("TITLE"));
				Timestamp ts = rs.getTimestamp("START_DATE");
				cust.setStartDate(ts.toLocalDateTime());
				ts = rs.getTimestamp("END_DATE");
				cust.setEndDate(ts.toLocalDateTime());
				cust.setAmount(rs.getInt("AMOUNT"));	
				cust.setType(CouponType.valueOf(rs.getString("TYPE")));
				cust.setMessage(rs.getString("MESSAGE"));
				cust.setPrice(rs.getDouble("PRICE"));
				cust.setImage(rs.getString("IMAGE"));
				
				custs.add(cust);				
			}	
		
			rs.close();
			return custs;
		
		}
		catch (SQLException e)
		{
			if (e.getMessage() == "Connection was not established")
			{
				throw new DAOException(DAOExceptionErrorType.CONNECTION_CLOSED, "Connection error, please refer to system admin");
			}
			
			throw new DAOException(DAOExceptionErrorType.NEW_COUPON_OBJECT_FAILED, "Faiure in coupon processing");
		}

	}

	@Override
	public Collection<Coupon> getCouponsFiltered(FilterCouponDBDao filters, boolean isAnd) throws DAOException 
	{
		if (filters == null) { throw new DAOException(DAOExceptionErrorType.COUPON_DETAILS_FAILED_TO_RETRIEVE); }
		
		String sql = "SELECT * FROM couponsystem.coupon WHERE " + (isAnd ? filters.getFiltersAnd() : filters.getFiltersOr());
		try (Connection con = pool.OpenConnection(); Statement statement = con.createStatement();)
		{				

			ResultSet rs = statement.executeQuery(sql);
			
			return this.BuildCoupons(rs);
		}
		catch (SQLException e)
		{
			if (e.getMessage() == "Connection was not established")
			{
				throw new DAOException(DAOExceptionErrorType.CONNECTION_CLOSED, "Connection error, please refer to system admin");
			}
			
			throw new DAOException(DAOExceptionErrorType.COUPON_DETAILS_FAILED_TO_RETRIEVE, "Failed to retreieve data for coupons");
		}		
	}

	@Override
	public Collection<Coupon> getCouponByName(String couponName) throws DAOException {
		String sql = "SELECT * FROM couponsystem.coupon WHERE TITLE = ?";
		try (Connection con = pool.OpenConnection(); PreparedStatement preparedStatement = con.prepareStatement(sql);)
		{	

			preparedStatement.setString(1, couponName);
			ResultSet rs = preparedStatement.executeQuery();

			return this.BuildCoupons(rs);
		}
		catch (SQLException e)
		{
			if (e.getMessage() == "Connection was not established")
			{
				throw new DAOException(DAOExceptionErrorType.CONNECTION_CLOSED, "Connection error, please refer to system admin");
			}
			
			throw new DAOException(DAOExceptionErrorType.COUPON_DETAILS_FAILED_TO_RETRIEVE,"Failed to retreieve data for coupons with the title: " + couponName);
		}
	}


	


}
