package com.CouponSystem.Facade;

import java.time.LocalDateTime;
import java.util.Collection;
import com.CouponSystem.Beans.*;
import com.CouponSystem.DAO.CouponDAO;
import com.CouponSystem.DAO.CustomerDAO;
import com.CouponSystem.DBDAO.CouponDBDAO;
import com.CouponSystem.DBDAO.CouponField;
import com.CouponSystem.DBDAO.CustomerDBDAO;
import com.CouponSystem.FacadeException.FacadeException;
import com.CouponSystem.FilterDBDao.FilterCouponDBDao;
import DAOException.DAOException;
import DAOException.DAOExceptionErrorType;

public class CustomerFacade implements CouponClientFacade
{

	//
	// attributes
	//
	
	private Customer customer;
	private CustomerDAO custDao = CustomerDBDAO.getInstance();
	private CouponDAO copDao = CouponDBDAO.getInstance();
	
	//
	// Constructors
	//
	public CustomerFacade()
	{
		
	}
	
	
	//
	// methods
	//
	
	public void purchaseCoupon(Coupon coupon) throws FacadeException
	{
		if (coupon == null) { throw new FacadeException(DAOExceptionErrorType.UPDATE_COUPON_FAILED, "Error in coupon idetification"); }
		
		if (coupon.getAmount() == 0) 
		{
			throw new FacadeException(DAOExceptionErrorType.UPDATE_COUPON_FAILED,"There no coupons left");
		}
		else if (coupon.getEndDate().isBefore(LocalDateTime.now()))
		{
			throw new FacadeException(DAOExceptionErrorType.UPDATE_COUPON_FAILED,"The coupon already expired");
		}
		
		
		int amountOfCoupons = coupon.getAmount();
		try 
		{
			if (custDao.couponBelongsTo(coupon.getId(), customer.getId()))
			{
				throw new FacadeException(DAOExceptionErrorType.UPDATE_COUPON_FAILED,"Customer already own this coupon, you can purchase only one");
			}
			else
			{					
				custDao.LinkNewCustomerCoupon(customer, coupon);
				coupon.setAmount(amountOfCoupons - 1);
				copDao.updateCoupon(coupon);
			}
		} 
		catch (DAOException e) 
		{
			throw new FacadeException(e);
		}
		
		
	}
	
	public Collection<Coupon> getAllPurchasedCoupons() throws FacadeException
	{
		try 
		{				
			
			return custDao.getCoupons(customer);
		} 
		catch (DAOException e) 
		{
			throw new FacadeException(e);
		}
	}
	
	public Collection<Coupon> getAllPurchasedCouponsByType(CouponType type) throws FacadeException
	{
		try 
		{
			
			FilterCouponDBDao filters = new FilterCouponDBDao();
			filters.addFilter(CouponField.TYPE, "=", type);
			

			return custDao.getCoupons(customer, filters, true);
		}
		catch (DAOException e) 
		{
			throw new FacadeException(e);
		}		
	}
	
	public Collection<Coupon> getAllPurchasedCouponsByPrice(double price) throws FacadeException
	{
		try 
		{
			
			FilterCouponDBDao filters = new FilterCouponDBDao();
			
			filters.addFilter(CouponField.PRICE, "<=", price);
			
			// Check if customer coupons attribute is empty or is not sync with db 
			
			return custDao.getCoupons(customer, filters, true);
		}
		catch (DAOException e) 
		{
			throw new FacadeException(e);
		}
	}
	
	@Override
	public CouponClientFacade login(String name, String password) throws FacadeException
	{		
		try 
		{
			long customerId = custDao.login(name, password);
			if (customerId != -1)
			{
				customer = custDao.getCustomer(customerId);
				return this;
			}
			else
			{
				throw new FacadeException(DAOExceptionErrorType.WRONG_ARGUMENT,"User name / password is wrong");
			}
		} 
		catch (DAOException e) 
		{
			throw new FacadeException(e);
		}
		
	}
	
	//  get all coupons in the system not only for this specific customer
	public Collection<Coupon> getAllCoupons() throws FacadeException
	{
		try 
		{			
			
			return copDao.getAllCoupons();
		} 
		catch (DAOException e) 
		{
			throw new FacadeException(e);
		}
	}
	
	public Coupon getCoupon(long id) throws FacadeException
	{
		try 
		{
			return copDao.getCoupon(id);
		} 
		catch (DAOException e) 
		{
			throw new FacadeException(e);
		}
	}
	

	

}
