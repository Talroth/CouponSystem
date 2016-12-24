package com.CouponSystem.DAO;

import java.util.*;

import com.CouponSystem.Beans.*;
import com.CouponSystem.FilterDBDao.FilterCouponDBDao;

import DAOException.DAOException;

//
//Interface to coupon  table
//

public interface CouponDAO 
{
	//
	// C.R.U.D actions
	//
	
	public void createCoupon(Coupon coupon, Company company) throws DAOException;
	public void removeCoupon(Coupon coupon) throws DAOException;
	public void updateCoupon(Coupon coupon) throws DAOException;
	
	// return coupon by its id from the DAO
	public Coupon getCoupon(long id) throws DAOException;
	
	// return coupon collection from the DAO by coupon's type
	public Collection<Coupon> getCouponByType(CouponType couponType) throws DAOException;
	
	// return all coupons from the DAO 
	public Collection<Coupon> getAllCoupons() throws DAOException;
	
	// return coupon collection from the DAO by predefined filter (FilterCouponDBDao)
	public Collection<Coupon> getCouponsFiltered(FilterCouponDBDao filters, boolean isAnd) throws DAOException;
	
	// return collection of coupons according to filter
	public Collection<Coupon> getCoupon(String filters) throws DAOException;
	
	// return coupons by coupon name
	public Collection<Coupon> getCouponByName(String couponName) throws DAOException;
	

}
