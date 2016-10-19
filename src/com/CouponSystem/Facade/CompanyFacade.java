package com.CouponSystem.Facade;

import java.time.LocalDateTime;
import java.util.Collection;

import com.CouponSystem.Beans.*;
import com.CouponSystem.DAO.*;
import com.CouponSystem.DBDAO.CompanyDBDAO;
import com.CouponSystem.DBDAO.CouponDBDAO;
import com.CouponSystem.DBDAO.CouponField;
import com.CouponSystem.FacadeException.FacadeException;
import com.CouponSystem.FilterDBDao.FilterCouponDBDao;

import DAOException.DAOException;
import DAOException.DAOExceptionErrorType;

public class CompanyFacade implements CouponClientFacade 
{

	// 
	// attributes
	//
	
	private CouponDAO couponDao = CouponDBDAO.getInstance();
	private CompanyDAO compDao = CompanyDBDAO.getInstance();
	private Company companyLog = new Company();
	
	//
	// Constructors
	//
	public CompanyFacade()
	{
		
	}
	
	
	//
	// methods
	//
	
	public void createCoupon(Coupon coupon) throws FacadeException
	{
		try 
		{
			if (coupon == null) { throw new FacadeException(DAOExceptionErrorType.NEW_COUPON_FAILED); }
			
			if (couponDao.getCouponByName(coupon.getTitle()).size() != 0)
			{
				throw new FacadeException(DAOExceptionErrorType.NEW_COUPON_FAILED,"Coupon already exists");
			}
			
//			Collection<Coupon> coupons = couponDao.getAllCoupons();
//			for (Coupon c : coupons)
//			{
//				if (c.getTitle().equals(coupon.getTitle()))
//				{
//					throw new FacadeException(DAOExceptionErrorType.NEW_COUPON_FAILED,"Coupon already exists");
//				}
//			}
			couponDao.createCoupon(coupon,companyLog);
		} 
		catch (DAOException e) 
		{
			if (e.isError(DAOExceptionErrorType.CONNECTION_CLOSED))
			{
				throw new FacadeException(e);
			}
			
			throw new FacadeException(e);
		}
	}
	
	public void removeCoupon(Coupon coupon) throws FacadeException
	{
		try
		{
		
		// delete coupons
		if (coupon != null)
		{
			boolean couponBelongToCompany = false;
			Collection<Coupon> companyCoupons = compDao.getCoupons(companyLog);

			for (Coupon c : companyCoupons)
			{
				if (c.getId() == coupon.getId())
				{
					couponBelongToCompany = true;
					break;
				}
			}
			if (couponBelongToCompany)
			{
				couponDao.removeCoupon(coupon);
			}
			else
			{
				throw new FacadeException(DAOExceptionErrorType.REMOVE_COUPON_FAILED,"You try to remove coupon which doesn't belong to " + companyLog.getCompName());
			}
		}
		else
		{
			throw new FacadeException(DAOExceptionErrorType.REMOVE_COUPON_FAILED, "Coupon to remove wasn't chosen");
		}

		}
		catch (DAOException e)
		{
			if (e.isError(DAOExceptionErrorType.CONNECTION_CLOSED))
			{
				throw new FacadeException(e);
			}
			
			throw new FacadeException(e);
		}
	}


	public void updateCoupon(Coupon coupon) throws FacadeException
	{
		try 
		{
			Coupon updatedStatusCoupon = couponDao.getCoupon(coupon.getId());
			if (updatedStatusCoupon.getId()!=coupon.getId() || !updatedStatusCoupon.getTitle().equals(coupon.getTitle()) ||
					!updatedStatusCoupon.getStartDate().isEqual(coupon.getStartDate()) ||
					updatedStatusCoupon.getAmount()!=coupon.getAmount() || updatedStatusCoupon.getType()!=coupon.getType() || 
					!updatedStatusCoupon.getMessage().equals(coupon.getMessage()) ||
					!updatedStatusCoupon.getImage().equals(coupon.getImage()))
			{
				throw new FacadeException(DAOExceptionErrorType.UPDATE_COUPON_FAILED,"Coupon can be updated only for End date and the price of the coupon");
			}
			couponDao.updateCoupon(coupon);
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
			return compDao.getCoupon(companyLog, id);
		} 
		catch (DAOException e) 
		{
			throw new FacadeException(e);
		}
	}
	
	public Collection<Coupon> getAllCoupons() throws FacadeException
	{
		try 
		{
						
			return compDao.getCoupons(companyLog);
		} 
		catch (DAOException e) 
		{
			throw new FacadeException(e.getMessage());
		}
	}
	
	public Collection<Coupon> getCouponByType(CouponType couponType) throws FacadeException
	{
		try 
		{	

		
			// Create filter to retrieve only coupons with type 'couponType'
			FilterCouponDBDao filters = new FilterCouponDBDao();
			filters.addFilter(CouponField.TYPE, FilterCouponDBDao.EQUAL, couponType);
				
			// return all coupons that belong to current company (companyLog) filtered according to 'couponType'
			return compDao.getCoupons(companyLog, filters, true);
		} 
		catch (DAOException e) 
		{
			throw new FacadeException(e);
		}
		
	}
	
	public Collection<Coupon> getCouponByPrice(double minPrice, double maxPrice) throws FacadeException
	{

		try 
		{		
			
			// Create filter to retrieve only coupons with price between minPrice and maxPrice
			FilterCouponDBDao filters = new FilterCouponDBDao();
			filters.addFilter(CouponField.PRICE, FilterCouponDBDao.BIGGER_OR_EQUAL, minPrice);
			filters.addFilter(CouponField.PRICE, FilterCouponDBDao.SMALLER_OR_EQUAL, maxPrice);
					
			
			// return all coupons that belong to current company (companyLog) filtered according to 'price'
			return compDao.getCoupons(companyLog, filters, true);
		} 
		catch (DAOException e) 
		{
			throw new FacadeException(e);
		}
		
	}
	
	public Collection<Coupon> getCouponByEndDate(LocalDateTime endDate) throws FacadeException
	{

		try 
		{		
			
			// Create filter to retrieve only coupons with end date prior to endDate argument
			FilterCouponDBDao filters = new FilterCouponDBDao();
			filters.addFilter(CouponField.END_DATE, FilterCouponDBDao.SMALLER_OR_EQUAL, endDate);
			
		
			// return all coupons that belong to current company (companyLog) filtered according to 'filters'
			return compDao.getCoupons(companyLog, filters, true);
		} 
		catch (DAOException e) 
		{
			throw new FacadeException(e);
		}
		
	}
	
	public Company getCompanyDetails() throws FacadeException
	{
		//TODO: non editable object need to be return
		return companyLog;
	}
	
	
	
	@Override
	public CouponClientFacade login(String name, String password) throws FacadeException
	{
		try 
		{
			long companyId = compDao.login(name, password);
			if (companyId != -1)
			{
				companyLog = compDao.getCompany(companyId);
				return this;
			}
			else
			{
				throw new FacadeException(DAOExceptionErrorType.COMPANY_DETAILS_FAILED_TO_RETRIEVE,"Login error, name or password not exists");
			}
		} 
		catch (DAOException e) 
		{
			throw new FacadeException(e);
		}

	}

}
