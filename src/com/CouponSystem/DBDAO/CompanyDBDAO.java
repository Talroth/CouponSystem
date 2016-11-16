package com.CouponSystem.DBDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import com.CouponSystem.Beans.*;
import com.CouponSystem.DAO.*;
import com.CouponSystem.FilterDBDao.FilterCouponDBDao;
import com.CouponSystem.FilterDao.FilterCouponDao;



import DAOException.DAOException;
import DAOException.DAOExceptionErrorType;

//
//Implementation of company  table bean from DB
//


public class CompanyDBDAO implements CompanyDAO
{
	// get pool connection in order to use it for different actions on the DB
	private DBDAO pool = DBDAO.getInstance();
	private static CompanyDBDAO companyDbDao = new CompanyDBDAO();
	//
	// Constructors
	//
	
	private CompanyDBDAO()
	{

	}

	
	// 
	// methods
	//

	public static CompanyDBDAO getInstance()
	{
		return companyDbDao;
	}
	
	@Override
	public long createCompany(Company company) throws DAOException
	{
		int aff = 0;
		long newId = 0;
		
		String sql = "INSERT INTO couponsystem.company(COMP_NAME,PASSWORD,EMAIL) VALUES (?,?,?)";
		// open connection via pool to the DB and assign sql query in the prepareStaTement and ask to return the new id which created automatically by the DB
		try (Connection con = pool.OpenConnection();  PreparedStatement preparedStatement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);)
		{	
			// query command
			// assign company name, password and email for the new company
			preparedStatement.setString(1, company.getCompName());
			preparedStatement.setString(2, company.getPassword());
			preparedStatement.setString(3, company.getEmail());
			
			// query execution
			aff = preparedStatement.executeUpdate();
			
			//get id of the new customer from the db
			ResultSet rs = preparedStatement.getGeneratedKeys();
			if (rs.next())
			{
				newId = rs.getLong(1);
				// set company id to company object (until this stage the id attribute of the object is null
				company.setId(newId);
			}
			
			rs.close();
			return newId;
			
		}
		catch (SQLException e)
		{
			if (e.getMessage() == "Connection was not established")
			{
				throw new DAOException(DAOExceptionErrorType.CONNECTION_CLOSED,"Connection error, please refer to system admin");
			}
			
			if (aff == 0)
			{
				throw new DAOException(DAOExceptionErrorType.NEW_COMPANY_FAILED, "Faild to create " + company.getCompName());
			}
			else
			{
				throw new DAOException(DAOExceptionErrorType.COMPLETED,"Id of the company was not idetified but new company was created in the db");
			}
		}
		
	}

	@Override
	public void removeCompany(Company company) throws DAOException
	{
		try (Connection con = pool.OpenConnection())
		{
			
			// query command
			long companyID = company.getId();	
			
			// allow transaction for use several update, in case one fail all aborted
			con.setAutoCommit(false);
			
			PreparedStatement preparedStatementDeleteCoupons = null;
			PreparedStatement preparedStatementDeleteCompanyCouponJoin = null;
			PreparedStatement preparedStatementDeleteCompany = null;
			
			try 
			{
				// Delete all coupon belongs to company i going to remove
				String sqlDeleteCoupons = "DELETE couponDel FROM couponsystem.coupon couponDel LEFT JOIN couponsystem.company_coupon"
						+ " ON couponsystem.company_coupon.COUPON_ID = couponDel.ID WHERE couponsystem.company_coupon.COMP_ID = ?";
				preparedStatementDeleteCoupons = con.prepareStatement(sqlDeleteCoupons);
				preparedStatementDeleteCoupons.setLong(1, companyID);
				
				// query execution
				preparedStatementDeleteCoupons.executeUpdate();
				
				// Delete all links between the company i am going to remove and their coupons
				String sqlDeleteCompanyCouponJoin = "DELETE FROM couponsystem.company_coupon WHERE COMP_ID = ?";
			    preparedStatementDeleteCompanyCouponJoin = con.prepareStatement(sqlDeleteCompanyCouponJoin);
				preparedStatementDeleteCompanyCouponJoin.setLong(1, companyID);
				preparedStatementDeleteCompanyCouponJoin.executeUpdate();
				
				// Finally remove the company
				String sqlDeleteCompany = "DELETE FROM couponsystem.company WHERE ID = ?";
				preparedStatementDeleteCompany = con.prepareStatement(sqlDeleteCompany);
				preparedStatementDeleteCompany.setLong(1, companyID);
				preparedStatementDeleteCompany.executeUpdate();
				
				con.commit();
				
				System.out.println("Company " + company.getCompName() + " was removed");
				
				// once the company is not exists in the db, the Bean also need to be "removed"
				company = null;
			}
			catch (SQLException eInner)
			{
				con.rollback();
				
				if (eInner.getMessage() == "Connection was not established")
				{
					throw new DAOException(DAOExceptionErrorType.CONNECTION_CLOSED, "Connection error, please refer to system admin");
				}
				
				throw new DAOException(DAOExceptionErrorType.REMOVE_COMPANY_FAILED, "Failed to remove " + company.getCompName());				
			}
			finally
			{
				con.setAutoCommit(true);
				if (preparedStatementDeleteCoupons != null) { preparedStatementDeleteCoupons.close(); }
				if (preparedStatementDeleteCompanyCouponJoin != null) { preparedStatementDeleteCompanyCouponJoin.close(); }
				if (preparedStatementDeleteCompany != null) { preparedStatementDeleteCompany.close(); }
			}
			
		}
		catch (SQLException eOuter)
		{
			throw new DAOException(DAOExceptionErrorType.REMOVE_COMPANY_FAILED, "Failed to remove " + company.getCompName());
		}
		
	}

	@Override
	public void updateCompany(Company company) throws DAOException
	{
		String sql = "UPDATE couponsystem.company SET COMP_NAME = ? , PASSWORD = ? , EMAIL = ? WHERE ID = ?";
		
		try (Connection con = pool.OpenConnection(); PreparedStatement preparedStatement = con.prepareStatement(sql);)
		{		
			// query command		
			preparedStatement.setString(1, company.getCompName());
			preparedStatement.setString(2, company.getPassword());
			preparedStatement.setString(3, company.getEmail());
			preparedStatement.setLong(4, company.getId());
			
			// query execution
			preparedStatement.executeUpdate();
		}
		catch (SQLException e)
		{
			if (e.getMessage() == "Connection was not established")
			{
				throw new DAOException(DAOExceptionErrorType.CONNECTION_CLOSED, "Connection error, please refer to system admin");
			}
			
			throw new DAOException(DAOExceptionErrorType.UPDATE_COMPANY_FAILED, "Failed to update " + company.getCompName());
		}
		
	}

	@Override
	public Company getCompany(long id) throws DAOException
	{
		String sql = "SELECT * FROM couponsystem.company WHERE ID = ?";
		try (Connection con = pool.OpenConnection(); PreparedStatement preparedStatement = con.prepareStatement(sql);)
		{	
			// query command

			preparedStatement.setLong(1, id);

			// query execution
			
			ResultSet rs =  preparedStatement.executeQuery();
			
			
			Company comp = new Company();
			
			if (rs.next())
			{
				//Fill customer object from Customer table				
				comp.setId(rs.getLong("ID"));
				comp.setCompName(rs.getString("COMP_NAME"));
				comp.setPassword(rs.getString("PASSWORD"));
				comp.setEmail(rs.getString("EMAIL"));
				
			}	
			else
			{
				comp = null;
			}
			
			rs.close();
			return comp;
		}
		catch (SQLException e)
		{
			System.out.println("DBDao getCompany sql exception zone");
			if (e.getMessage() == "Connection was not established")
			{
				throw new DAOException(DAOExceptionErrorType.CONNECTION_CLOSED, "Connection error, please refer to system admin");
			}
			
			throw new DAOException(DAOExceptionErrorType.CUSTOMER_DETAILS_FAILED_TO_RETRIEVE,  "Company id: " + Long.toString(id) + " was not found");
		}
	}


	
	@Override
	public Long getIdByCompanyName(String name) throws DAOException
	{
		String sql = "SELECT ID FROM couponsystem.company WHERE COMP_NAME = ?";
		try (Connection con = pool.OpenConnection(); PreparedStatement preparedStatement = con.prepareStatement(sql);)
		{	
			// query command
						
			preparedStatement.setString(1, name);
			
			// query execution
			ResultSet rs = preparedStatement.executeQuery();
			
			if (rs.next())
			{
				//Fill customer object from Customer table				
				return rs.getLong("ID");
			}	
			else
			{
				rs.close();
				return 0L;
			}			
			
		}
		catch (SQLException e)
		{
			if (e.getMessage() == "Connection was not established")
			{
				throw new DAOException(DAOExceptionErrorType.CONNECTION_CLOSED, "Connection error, please refer to system admin");
			}
			
			throw new DAOException(DAOExceptionErrorType.COMPANY_DETAILS_FAILED_TO_RETRIEVE, "Company  " + name + " was not found");
		}
	}

	@Override
	public Collection<Company> getAllCompanies() throws DAOException
	{
		String sql = "SELECT * FROM couponsystem.company";
		try (Connection con = pool.OpenConnection(); Statement stat = con.createStatement();)
		{	
						
			// query execution
			ResultSet rs = stat.executeQuery(sql);
			
			//Fill company object (comp) from Company table and insert it into collection of Company
			Collection<Company> comps = new ArrayList<Company>();
			while (rs.next())
			{
				Company comp = new Company();
				comp.setId(rs.getLong("ID"));
				comp.setCompName(rs.getString("COMP_NAME"));
				comp.setPassword(rs.getString("PASSWORD"));
				comp.setEmail(rs.getString("EMAIL"));
				comps.add(comp);
			}
			
			rs.close();
			return comps;
		}
		catch (SQLException e)
		{
			if (e.getMessage() == "Connection was not established")
			{
				throw new DAOException(DAOExceptionErrorType.CONNECTION_CLOSED, "Connection error, please refer to system admin");
			}
			
			throw new DAOException(DAOExceptionErrorType.COMPANY_DETAILS_FAILED_TO_RETRIEVE,"Failed to retreieve data for all companies");
		}
	}

	@Override
	public Collection<Coupon> getCoupons(Company company) throws DAOException
	{		
		return this.getCouponsMain(company, null);
	}
	
	@Override
	public Coupon getCoupon(Company company, long Id) throws DAOException
	{
		String sql = "SELECT couponsystem.coupon.* FROM couponsystem.company_coupon LEFT JOIN couponsystem.coupon ON "
				+ "couponsystem.company_coupon.COUPON_ID = couponsystem.coupon.ID WHERE couponsystem.company_coupon.COMP_ID = ? AND couponsystem.coupon.ID = ?";
		
		try (Connection con = pool.OpenConnection(); PreparedStatement preparedStatement = con.prepareStatement(sql))
		{	
			// query command
			
			if (company == null)
			{
				throw new DAOException(DAOExceptionErrorType.COUPON_DETAILS_FAILED_TO_RETRIEVE);
			}
			
			preparedStatement.setLong(1, company.getId());
			preparedStatement.setLong(2, Id);
			
			
			ResultSet rs = preparedStatement.executeQuery();
			
				
			CouponDBDAO couponDao = CouponDBDAO.getInstance();	
			
			return couponDao.BuildCoupons(rs).iterator().next();

		}
		catch (SQLException e)
		{
			if (e.getMessage() == "Connection was not established")
			{
				throw new DAOException(DAOExceptionErrorType.CONNECTION_CLOSED, "Connection error, please refer to system admin");
			}
			
			throw new DAOException(DAOExceptionErrorType.COUPON_DETAILS_FAILED_TO_RETRIEVE,"Company id: " + Long.toString(Id) + " was not found");
		}
		// In case of failure in the result set assigning
		catch (NoSuchElementException eElement)
		{
			throw new DAOException(DAOExceptionErrorType.COUPON_DETAILS_FAILED_TO_RETRIEVE);
		}
	}
	


	@Override
	public long login(String compName, String password) throws DAOException
	{
		String sql = "SELECT ID FROM couponsystem.company WHERE COMP_NAME = ? AND PASSWORD = ?";
		try (Connection con = pool.OpenConnection(); PreparedStatement preparedStatement = con.prepareStatement(sql);)
		{	
			// query command						
			preparedStatement.setString(1, compName);
			preparedStatement.setString(2, password);
			
			// query execution
			ResultSet rs = preparedStatement.executeQuery();
			
			if (rs.next())
			{
				System.out.println("log-in was successfuly performed");
				return rs.getLong(1);
			}
			else
			{
				System.out.println("Failed to login, company: " + compName);
				rs.close();
				return -1;
			}
									
		}
		catch (SQLException e)
		{
			if (e.getMessage() == "Connection was not established")
			{
				throw new DAOException(DAOExceptionErrorType.CONNECTION_CLOSED, "Connection error, please refer to system admin");
			}
			throw new DAOException(DAOExceptionErrorType.COMPANY_DETAILS_FAILED_TO_RETRIEVE, "Login for " + compName + " was failed");
		}
		
	}


	@Override
	public Collection<Coupon> getCoupons(Company company, FilterCouponDao filters, boolean isAnd) throws DAOException {
		// call general method to filter the coupon list according to the filter and if the filter with 'AND' or 'OR'
		if (company == null || filters == null)
		{
			throw new DAOException(DAOExceptionErrorType.COUPON_DETAILS_FAILED_TO_RETRIEVE);
		}
		
		return getCouponsMain(company, isAnd ? ((FilterCouponDBDao)filters).getFiltersAnd() : ((FilterCouponDBDao)filters).getFiltersOr());
	}
	
	private Collection<Coupon> getCouponsMain(Company company, String filters) throws DAOException
	{
		String sql = null;
		

		if (filters != null)
		{
			sql = "SELECT couponsystem.coupon.* FROM couponsystem.company_coupon LEFT JOIN couponsystem.coupon ON "
				+ "couponsystem.company_coupon.COUPON_ID = couponsystem.coupon.ID WHERE couponsystem.company_coupon.COMP_ID = ? AND " + filters;
		}
		else
		{		
			 sql = "SELECT couponsystem.coupon.* FROM couponsystem.company_coupon LEFT JOIN couponsystem.coupon ON "
				+ "couponsystem.company_coupon.COUPON_ID = couponsystem.coupon.ID WHERE couponsystem.company_coupon.COMP_ID = ?";			
		}
		
		if (company == null)
		{
			throw new DAOException(DAOExceptionErrorType.COUPON_DETAILS_FAILED_TO_RETRIEVE, "Coupon/s was/were not found"); 	
		}
		
		try (Connection con = pool.OpenConnection(); PreparedStatement preparedStatement = con.prepareStatement(sql);)
		{	
			// query command

			preparedStatement.setLong(1, company.getId());

			ResultSet rs = preparedStatement.executeQuery();

  			CouponDBDAO couponDao = CouponDBDAO.getInstance();	
								
			return couponDao.BuildCoupons(rs);

		}
		catch (SQLException e)
		{
			if (e.getMessage() == "Connection was not established")
			{
				throw new DAOException(DAOExceptionErrorType.CONNECTION_CLOSED, "Connection error, please refer to system admin");
			}
			
			throw new DAOException(DAOExceptionErrorType.COUPON_DETAILS_FAILED_TO_RETRIEVE,"Coupon/s was/were not found");
		}
	}


	

}
