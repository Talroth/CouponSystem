package com.CouponSystem.Facade;

import java.util.*;
import com.CouponSystem.Beans.*;
import com.CouponSystem.DAO.*;
import com.CouponSystem.DBDAO.*;
import com.CouponSystem.FacadeException.FacadeException;

import DAOException.DAOException;
import DAOException.DAOExceptionErrorType;

public class AdminFacade implements CouponClientFacade
{
	// 
	// attributes
	//
	
	private CustomerDAO customerDao = CustomerDBDAO.getInstance();
	private CompanyDAO compDao = CompanyDBDAO.getInstance();
	
	//
	// Constructors
	//
	public AdminFacade()
	{
		
	}

	//
	// methods
	//
	
	public Customer createCustomer(Customer customer) throws FacadeException
	{
		if (customer == null) { throw new FacadeException(DAOExceptionErrorType.NEW_CUSTOMER_FAILED, "Customer details to create don't exist"); }
		// create new customer, prevent from creating customer with a name already exists
		try
		{
			
			String customerName = customer.getCustName();
			
			if (customerDao.getCustomerByName(customerName).size() > 0)
			{
				throw new FacadeException(DAOExceptionErrorType.NEW_CUSTOMER_FAILED, "Customer exists");
			}
							
			customer.setId(customerDao.createCustomer(customer));
			
		}
		
		catch (DAOException e)
		{
			if (e.getMessage() == "Connection was not established")
			{
				throw new FacadeException(DAOExceptionErrorType.CONNECTION_CLOSED);
			}
			
			if (e.isError(DAOExceptionErrorType.NEW_CUSTOMER_FAILED))
			{
				throw new FacadeException(e);
			}

		}
		return customer;
	}


	public void removeCustomer(Customer customer) throws FacadeException
	{
		try
		{			
			// delete customer
			customerDao.removeCustomer(customer);
		}
		catch (DAOException e)
		{
			throw new FacadeException(e);
		}
	}


	public void updateCustomer(Customer customer) throws FacadeException
	{
		if (customer == null) { throw new FacadeException(DAOExceptionErrorType.UPDATE_CUSTOMER_FAILED,"Customer not exists in the data storing system"); }
		
		// check if customer name in the customer database is different from the one user trying to update (it is not valid)
		try
		{
			Customer cust = customerDao.getCustomer(customer.getId());
			if (!(cust.getCustName().equals(customer.getCustName())))
			{
				throw new FacadeException(DAOExceptionErrorType.UPDATE_CUSTOMER_FAILED,"Updating customer name is not valid");
			}
			else
			{
				customerDao.updateCustomer(customer);
			}
		}
		catch (DAOException e)
		{
			throw new FacadeException(e);
		}
	}


	public Customer getCustomer(long id) throws FacadeException
	{
		try 
		{
			return customerDao.getCustomer(id);
		} 
		catch (DAOException e) 
		{
			throw new FacadeException(e);			
		}
	}


	public Collection<Customer> getAllCustomer() throws FacadeException
	{
		try 
		{
			return customerDao.getAllCustomer();
		} 
		catch (DAOException e) 
		{
			throw new FacadeException(e);
			
		}
	}


	public void createCompany(Company company) throws FacadeException
	{
		if (company == null) { throw new FacadeException(DAOExceptionErrorType.NEW_COMPANY_FAILED,"Faild to create new company due to lack in data of the new company"); }
		
		// create new company, prevent from creating company with a name already exists
		try
		{
			if (compDao.getIdByCompanyName(company.getCompName()) != 0)
			{
				throw new FacadeException(DAOExceptionErrorType.COMPLETED,"Company exists");
			}
						
			company.setId(compDao.createCompany(company));

		}
		catch (DAOException e)
		{			
			throw new FacadeException(e);
		}
		
		
	}


	public void removeCompany(Company company) throws FacadeException
	{
		try
		{
			if (company == null) { throw new FacadeException(DAOExceptionErrorType.REMOVE_COMPANY_FAILED, "Faild to remove company due to lack in data of the company"); }
			 			
			compDao.removeCompany(company);
		}
		catch (DAOException e)
		{			
			throw new FacadeException(e);
		}		
	}


	public void updateCompany(Company company) throws FacadeException
	{
		if (company == null) { throw new FacadeException(DAOExceptionErrorType.UPDATE_COMPANY_FAILED, "Faild to update company due to lack in data of the company"); }
		
		try
		{
			Company comp = compDao.getCompany(company.getId());
			if (!(comp.getCompName().equals(company.getCompName())))
			{
				throw new FacadeException(DAOExceptionErrorType.UPDATE_COMPANY_FAILED, "Updating company name is not valid");
			}
			else
			{
				compDao.updateCompany(company);
			}
		}
		catch (DAOException e)
		{
			throw new FacadeException(e);
		}		
	}


	public Company getCompany(long id) throws FacadeException
	{
		try 
		{
			return compDao.getCompany(id);
		} 
		catch (DAOException e) 
		{
			throw new FacadeException(e);
		}
	}


	public Collection<Company> getAllCompanies() throws FacadeException
	{
		try 
		{
			return compDao.getAllCompanies();
		} 
		catch (DAOException e) 
		{
			throw new FacadeException(e);
		}

	}
	public Collection<Coupon> getCoupons(Customer customer) throws FacadeException
	{
		if (customer == null) { throw new FacadeException(DAOExceptionErrorType.COUPON_DETAILS_FAILED_TO_RETRIEVE, "Faild to get coupons list for this company due to lack in identification of the this company"); }
		
		try
		{
			return customerDao.getCoupons(customer);
		}
		catch (DAOException e)
		{
			throw new FacadeException(e);
		}
	}


	public CouponClientFacade login(String compName, String password) throws FacadeException
	{
			if (compName.equals("admin") && password.equals("1234"))
			{
				return this;
			}
			else
			{				
				throw new FacadeException(DAOExceptionErrorType.ADMIN_FAIL_LOGIN,"Login failed, user name or password are not exists or wrong");
			}
	}
	

	

}
