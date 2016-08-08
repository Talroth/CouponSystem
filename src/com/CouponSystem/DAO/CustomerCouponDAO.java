package com.CouponSystem.DAO;

//
//Interface to customer-coupon join table
//

import java.util.Collection;

import com.CouponSystem.Beans.Coupon;
import com.CouponSystem.Beans.Customer;

import DAOException.DAOException;

public interface CustomerCouponDAO 
{
	// return all coupons from the DAO by customer from join customer-coupon
	public Collection<Coupon> getCoupons(Customer customer) throws DAOException;
	
	// remove all coupons from the DAO by customer from join customer-coupon
	public void removeCoupons(Customer customer) throws DAOException;
	
	// create new record in join customer-coupon with customer and coupon ids
	public void LinkNewCustomerCoupon(Customer customer, Coupon coupon) throws DAOException;
	
	// check if coupon id belong to specific customer
	public boolean couponBelongsTo(long couponId, long customerId) throws DAOException;
}
