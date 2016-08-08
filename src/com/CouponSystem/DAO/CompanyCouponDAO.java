package com.CouponSystem.DAO;

import com.CouponSystem.Beans.Company;


import com.CouponSystem.Beans.Coupon;

import DAOException.DAOException;

//
// Interface to company-coupon join table
//

public interface CompanyCouponDAO 
{
	// remove all coupons related to specific company from the join table - company coupon
	public void removeCoupons(Company company) throws DAOException;
	// remove specific coupon and verify it is belong to specific company from the join table - company coupon
	public void removeCoupon(Company company, Coupon coupon) throws DAOException;
}
