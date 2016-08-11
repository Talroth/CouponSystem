package com.CouponSystem.CouponExpiryScanner;

import java.time.LocalDateTime;
import java.util.Collection;

import com.CouponSystem.Beans.Coupon;
import com.CouponSystem.DAO.CouponDAO;
import com.CouponSystem.DBDAO.CouponDBDAO;
import com.CouponSystem.DBDAO.CouponField;
import com.CouponSystem.FilterDBDao.FilterCouponDBDao;


import DAOException.DAOException;

public class DailyCouponExpirationTask implements Runnable {

	// flag for check if the scanner is active
	private volatile boolean inProgress = false;

	//
	// implements of runnable
	//
	
	@Override
	public void run() 
	{
		// prevent from couponsystem to close the thread during working
		synchronized (this) {
			inProgress = true;
		}
		
		CouponDAO couponDao = CouponDBDAO.getInstance();

		
		try
		{							
			// assign filter with END_DATE field to be before the current date, that's mean each coupon that already expired will be retrieved
						
			FilterCouponDBDao filters = new FilterCouponDBDao();			
			filters.addFilter(CouponField.END_DATE, FilterCouponDBDao.SMALLER_THAN, LocalDateTime.now());
			

				// scan list of expired coupons and delete them from the DAO
				Collection<Coupon> allCoupons = couponDao.getCouponsFiltered(filters, false);
				
				if (allCoupons == null)
				{
					System.out.println("No coupons or connection is not exists");
				}
				else
				{
					for (Coupon coupon : allCoupons)
					{
						couponDao.removeCoupon(coupon);
					}
				}
				
				synchronized (this) {
					inProgress = false;
				}	
		}
		catch (DAOException e)
		{
			System.out.println("Fail to remove expired coupons");
			inProgress = false;
		}
		
	}
	

	//
	// Constructor
	//
	public DailyCouponExpirationTask()
	{
		
	}

	//
	// methods
	//
	
	public boolean isInProgress()
	{
		return inProgress;
	}
			
}
