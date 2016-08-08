package com.CouponSystem.DBDAO;

import java.sql.*;
import java.util.*;

import com.CouponSystem.Beans.*;
import com.CouponSystem.DAO.*;
import com.CouponSystem.FilterDBDao.FilterCouponDBDao;

import DAOException.DAOException;
import DAOException.DAOExceptionErrorType;

//
//Implementation of customer table bean from DB
//

public class CustomerDBDAO implements CustomerDAO 
{
	// ConnectionPool pool
	// All connection are using this connection pool
	
	private DBDAO pool = DBDAO.getInstance();
	private static CustomerDBDAO customerDao = new CustomerDBDAO();
	
	//
	// Constructors
	//
	
	private CustomerDBDAO()
	{

	}
	
	// 
	// methods
	//
	
	public static CustomerDBDAO getInstance()
	{
		return customerDao;
	}
	
	// create new record in customer table according to customer object
	@Override
	public long createCustomer(Customer customer) throws DAOException
	{
		if (customer == null) { throw new DAOException(DAOExceptionErrorType.NEW_CUSTOMER_COUPON_FAILED); }
		
		int aff = 0;
		long newId = 0;
		
		// Insert new customer by predefined name and password
		
		String sql = "INSERT INTO couponsystem.customer(CUST_NAME,PASSWORD) VALUES (?,?)";
		
		// ID is generated automatically by the DB, returning the new ID
		
		try (Connection con = pool.OpenConnection(); PreparedStatement preparedStatement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);)
		{	
		
			// set customer name and password in the query
			
			preparedStatement.setString(1, customer.getCustName());
			preparedStatement.setString(2, customer.getPassword());
			
			// Return number of records been created, should be 0 in case of failure or 1 if success
			aff = preparedStatement.executeUpdate();
			
			//get id of the new customer from the DB and set it in the customer object
			ResultSet rs = preparedStatement.getGeneratedKeys();
			if (rs.next())
			{
				newId = rs.getLong(1);
				customer.setId(newId);
			}
			
			
			// returning the new ID
			return newId;
		}
		catch (SQLException e)
		{
			if (e.getMessage() == "Connection was not established")
			{
				throw new DAOException(DAOExceptionErrorType.CONNECTION_CLOSED, "Connection error, please refer to system admin");
			}
			
			if (aff == 0)
			{
				// no new customer was created
				throw new DAOException(DAOExceptionErrorType.NEW_CUSTOMER_FAILED, "Failed to create customer: " + customer.getCustName());
			}
			else
			{
				// failure in retreive new ID number but creation was done successfully
				throw new DAOException(DAOExceptionErrorType.COMPLETED,"Id of the customer was not idetified but new customer was created in the db");
			}
		}
	}

	// remove customer from customer table according to customer object
	@Override
	public void removeCustomer(Customer customer) throws DAOException
	{		
		if (customer == null) { throw new DAOException(DAOExceptionErrorType.REMOVE_CUSTOMER_FAILED); }
		
		try (Connection con = pool.OpenConnection())
		{
			PreparedStatement preparedStatementDeleteCustomerCouponJoin = null;
			PreparedStatement preparedStatementDeleteCustomer = null;
			
			long customerID = customer.getId();
			
			try
			{
				// allow transaction
				con.setAutoCommit(false);
				
				// Delete all links between the customer i am going to remove and their coupons
				String sqlDeleteCustomerCouponJoin = "DELETE FROM couponsystem.customer_coupon WHERE CUST_ID = ?";
				preparedStatementDeleteCustomerCouponJoin = con.prepareStatement(sqlDeleteCustomerCouponJoin);
				preparedStatementDeleteCustomerCouponJoin.setLong(1, customerID);
				preparedStatementDeleteCustomerCouponJoin.executeUpdate();
				preparedStatementDeleteCustomerCouponJoin.executeUpdate();
				
				// Finally remove the customer
				String sqlDeleteCustomer = "DELETE FROM couponsystem.customer WHERE ID = ?";
				preparedStatementDeleteCustomer = con.prepareStatement(sqlDeleteCustomer);
				preparedStatementDeleteCustomer.setLong(1, customerID);
				preparedStatementDeleteCustomer.executeUpdate();
				
				con.commit();
								
				// once the customer is not exists in the db, the Bean also need to be "removed"
				customer = null;
				
			}
			catch (SQLException eInner)
			{
				// In case of failure undo all DB updates
				con.rollback();
				
				if (eInner.getMessage() == "Connection was not established")
				{
					throw new DAOException(DAOExceptionErrorType.CONNECTION_CLOSED, "Connection error, please refer to system admin");
				}
				
				throw new DAOException(DAOExceptionErrorType.REMOVE_CUSTOMER_FAILED, "Failed to remove customer: " + customer.getCustName());
			}
			finally
			{
				// prevent transaction
				con.setAutoCommit(true);
				if (preparedStatementDeleteCustomerCouponJoin != null) { preparedStatementDeleteCustomerCouponJoin.close(); }
				if (preparedStatementDeleteCustomer != null) { preparedStatementDeleteCustomer.close(); }
			}
			
		}
		catch (SQLException eOuter)
		{
			throw new DAOException(DAOExceptionErrorType.REMOVE_CUSTOMER_FAILED, "Failed to remove customer: " + customer.getCustName());
		}

	}

	// update customer table with details from customer object
	@Override
	public void updateCustomer(Customer customer) throws DAOException
	{
		if (customer == null) { throw new DAOException(DAOExceptionErrorType.UPDATE_CUSTOMER_FAILED); }
		
		// Update customer details
		
		String sql = "UPDATE couponsystem.customer SET CUST_NAME = ? , PASSWORD = ? WHERE ID = ?";
		try (Connection con = pool.OpenConnection(); PreparedStatement preparedStatement = con.prepareStatement(sql);)
		{					
			preparedStatement.setString(1, customer.getCustName());
			preparedStatement.setString(2, customer.getPassword());
			preparedStatement.setLong(3, customer.getId());
			
			// query execution
			preparedStatement.executeUpdate();
		}
		catch (SQLException e)
		{
			if (e.getMessage() == "Connection was not established")
			{
				throw new DAOException(DAOExceptionErrorType.CONNECTION_CLOSED, "Connection error, please refer to system admin");
			}
			
			throw new DAOException(DAOExceptionErrorType.UPDATE_CUSTOMER_FAILED, "Failed to update " + customer.getCustName() + " details");
		}

	}

	// return customer object according to given id from the DB
	@Override
	public Customer getCustomer(long id) throws DAOException
	{
		// retrieve customer by ID
		String sql = "SELECT * FROM couponsystem.customer WHERE ID = ?";
		
		try (Connection con = pool.OpenConnection(); PreparedStatement preparedStatement = con.prepareStatement(sql);)
		{	
			
			preparedStatement.setLong(1, id);
			
			// query execution
			ResultSet rs = preparedStatement.executeQuery();
			
			Customer cust = new Customer();
			if (rs.next())
			{
				//Fill customer object from Customer table				
				cust.setId(rs.getLong("ID"));
				cust.setCustName(rs.getString("CUST_NAME"));
				cust.setPassword(rs.getString("PASSWORD"));
			}	
			else
			{
				cust = null;
			}
			
			return cust;
		}
		catch (SQLException e)
		{
			if (e.getMessage() == "Connection was not established")
			{
				throw new DAOException(DAOExceptionErrorType.CONNECTION_CLOSED, "Connection error, please refer to system admin");
			}
			
			throw new DAOException(DAOExceptionErrorType.CUSTOMER_DETAILS_FAILED_TO_RETRIEVE, "Failed to find customer id: " + Long.toString(id));
		}
	}

	// return all customers into collection from the DB
	@Override
	public Collection<Customer> getAllCustomer() throws DAOException
	{
		try (Connection con = pool.OpenConnection(); Statement stat = con.createStatement();)
		{	
			// Retrieve all customers
			String sql = "SELECT * FROM couponsystem.customer";
						
			// query execution
			ResultSet rs = stat.executeQuery(sql);
			
			//Fill customer object (cust) from Customer table and insert it into collection of Customer
			Collection<Customer> custs = new ArrayList<Customer>();
			Customer cust = null;
					
			while (rs.next())
			{
				cust = new Customer();
				cust.setId(rs.getLong("ID"));
				cust.setCustName(rs.getString("CUST_NAME"));
				cust.setPassword(rs.getString("PASSWORD"));
				custs.add(cust);
			}
			
			return custs;
		}
		catch (SQLException e)
		{
			if (e.getMessage() == "Connection was not established")
			{
				throw new DAOException(DAOExceptionErrorType.CONNECTION_CLOSED, "Connection error, please refer to system admin");
			}
			
			throw new DAOException(DAOExceptionErrorType.CUSTOMER_DETAILS_FAILED_TO_RETRIEVE,"Failed to retreieve data for all customers");
		}
	}

	@Override
	public Collection<Coupon> getCoupons(Customer customer) throws DAOException
	{
		if (customer == null) { throw new DAOException(DAOExceptionErrorType.COUPON_DETAILS_FAILED_TO_RETRIEVE); }
		
		//
		// Request string to retrieve all coupons that related to specific customer by id number
		//
		
			String sql = "SELECT couponsystem.coupon.* FROM couponsystem.customer_coupon LEFT JOIN couponsystem.coupon ON "
					+ "couponsystem.customer_coupon.COUPON_ID = couponsystem.coupon.ID WHERE couponsystem.customer_coupon.CUST_ID = ?";
		
		// Retrieve coupon collection according to the customer
		return getCoupons(customer,sql);
	}
	
	@Override
	public Collection<Coupon> getCoupons(Customer customer, FilterCouponDBDao filters, boolean isAnd) throws DAOException
	{
		if (customer == null || filters == null) { throw new DAOException(DAOExceptionErrorType.UPDATE_CUSTOMER_FAILED); }
		
		String sql = "SELECT couponsystem.coupon.* FROM couponsystem.customer_coupon LEFT JOIN couponsystem.coupon ON "
				+ "couponsystem.customer_coupon.COUPON_ID = couponsystem.coupon.ID WHERE "
				+ "couponsystem.customer_coupon.CUST_ID = ? AND " + (isAnd ? filters.getFiltersAnd() : filters.getFiltersOr());
		
		// Retrieve coupon collection according to the customer and filters
		return getCoupons(customer,sql);
	}


	private Collection<Coupon> getCoupons(Customer customer, String sql) throws DAOException
	{
		if (customer == null) { throw new DAOException(DAOExceptionErrorType.COUPON_DETAILS_FAILED_TO_RETRIEVE); }
		
		try (Connection con = pool.OpenConnection(); PreparedStatement preparedStatement = con.prepareStatement(sql);)
		{	
					
			preparedStatement.setLong(1, customer.getId());
			
			// Fill result set with all coupons belong to customer with or without filter
			ResultSet rs = preparedStatement.executeQuery();
			
			CouponDBDAO couponDao = CouponDBDAO.getInstance();	
			
			// Create coupon collection from data retrieved from the DB
			return couponDao.BuildCoupons(rs);
		}
		catch (SQLException e)
		{
			if (e.getMessage() == "Connection was not established")
			{
				throw new DAOException(DAOExceptionErrorType.CONNECTION_CLOSED, "Connection error, please refer to system admin");
			}
			
			throw new DAOException(DAOExceptionErrorType.COUPON_DETAILS_FAILED_TO_RETRIEVE,"Failed to retrieve data for all coupons");
		}
	}
	
	@Override
	public long login(String custName, String password) throws DAOException
	{	
		// check if there is customer with this name and password
		String sql = "SELECT ID FROM couponsystem.customer WHERE CUST_NAME = ? AND PASSWORD = ?";
		
		try (Connection con = pool.OpenConnection(); PreparedStatement preparedStatement = con.prepareStatement(sql);)
		{	
			
			preparedStatement.setString(1, custName);
			preparedStatement.setString(2, password);
			
			
			ResultSet rs = preparedStatement.executeQuery();
			if (rs.next())
			{
				// if there is customer it returned the id number of tthe customer
				return rs.getLong(1);
			}
			else
			{
				// if there is no customer return -1
				return -1;
			}
			

						
		}
		catch (SQLException e)
		{
			if (e.getMessage() == "Connection was not established")
			{
				throw new DAOException(DAOExceptionErrorType.CONNECTION_CLOSED, "Connection error, please refer to system admin");
			}
			
			throw new DAOException(DAOExceptionErrorType.CUSTOMER_DETAILS_FAILED_TO_RETRIEVE, "Failed to find customer: " + custName);
		}
	}

	// collection will allow get list of customers in case customer name is not unique
	@Override
	public Collection<Customer> getCustomerByName(String customerName) throws DAOException {
		// check if there is customer with this name and password
		String sql = "SELECT * FROM couponsystem.customer WHERE CUST_NAME = ?";
		
		try (Connection con = pool.OpenConnection(); PreparedStatement preparedStatement = con.prepareStatement(sql);)
		{	
			
			preparedStatement.setString(1, customerName);
			
			
			ResultSet rs = preparedStatement.executeQuery();
			
			Customer cust = new Customer();
			Collection<Customer> customers = new ArrayList<Customer>();
			
			while (rs.next())
			{
				//Fill customer object from Customer table				
				cust.setId(rs.getLong("ID"));
				cust.setCustName(rs.getString("CUST_NAME"));
				cust.setPassword(rs.getString("PASSWORD"));
				
				customers.add(cust);
			}	

			
			return customers;
								
		}
		catch (SQLException e)
		{
			if (e.getMessage() == "Connection was not established")
			{
				throw new DAOException(DAOExceptionErrorType.CONNECTION_CLOSED, "Connection error, please refer to system admin");
			}
			
			throw new DAOException(DAOExceptionErrorType.CUSTOMER_DETAILS_FAILED_TO_RETRIEVE, "Failed to find customer: " + customerName);
		}
	}


	
	@Override
	public boolean couponBelongsTo(long couponId, long customerId) throws DAOException
	{
		// query command
		String sql = "SELECT * FROM couponsystem.customer_coupon WHERE COUPON_ID = ? AND CUST_ID = ?";
		
		try (Connection con = pool.OpenConnection(); PreparedStatement preparedStatement = con.prepareStatement(sql);)
		{
					
			preparedStatement.setLong(1, couponId);
			preparedStatement.setLong(2, customerId);
			
			return (preparedStatement.executeQuery().next());
			
		}
		
		catch (SQLException e)
		{
			throw new DAOException(DAOExceptionErrorType.COUPON_DETAILS_FAILED_TO_RETRIEVE,"Failed to verified if the coupon already belongs to the customer");
		}
	}
	
	@Override
	public void LinkNewCustomerCoupon(Customer customer, Coupon coupon) throws DAOException
	{		
		if (customer == null || coupon == null) { throw new DAOException(DAOExceptionErrorType.NEW_COUPON_FAILED); }
		
		// query command
		String sql = "INSERT INTO couponsystem.customer_coupon (CUST_ID, COUPON_ID ) VALUES (? , ?)";
		
		try (Connection con = pool.OpenConnection(); PreparedStatement preparedStatement = con.prepareStatement(sql);)
		{			
			preparedStatement.setLong(1, customer.getId());
			preparedStatement.setLong(2, coupon.getId());
			
			// query execution
			preparedStatement.executeUpdate();
			
		}
		
		catch (SQLException e)
		{
			throw new DAOException(DAOExceptionErrorType.NEW_CUSTOMER_COUPON_FAILED,"Failed to create join between coupon " + coupon.getId() + " and customer " + customer.getCustName());
		}	
	}

	@Override
	public void removeCoupons(Customer customer) throws DAOException 
	{
		if (customer == null) { throw new DAOException(DAOExceptionErrorType.REMOVE_COUPON_FAILED); }
		
		// query command
		String sql = "DELETE FROM couponsystem.customer_coupon WHERE CUST_ID = ?";
		
		try (Connection con = pool.OpenConnection(); PreparedStatement preparedStatement = con.prepareStatement(sql);)
		{
					
			preparedStatement.setLong(1, customer.getId());
			
			// query execution
			preparedStatement.executeUpdate();
			
		}
		
		catch (SQLException e)
		{
			throw new DAOException(DAOExceptionErrorType.REMOVE_COUPON_FAILED, "Failed to remove coupons belong to: " + customer.getCustName());
		}		
	}
	
}
