package com.CouponSystem.DAO;

import java.util.*;

import com.CouponSystem.Beans.*;
import com.CouponSystem.FilterDBDao.FilterCouponDBDao;

import DAOException.DAOException;

//
//Interface to customer table
//

public interface CustomerDAO 
{
	//
	// C.R.U.D actions
	//
	
	public long createCustomer(Customer customer) throws DAOException;
	public void removeCustomer(Customer customer) throws DAOException;
	public void updateCustomer(Customer customer) throws DAOException;
	public Customer getCustomer(long id) throws DAOException;
	
	// get all customer collection from the DAO
	public Collection<Customer> getAllCustomer() throws DAOException;
	
	//// return coupon collection from the DAO by coupon's customer
	public Collection<Coupon> getCoupons(Customer customer) throws DAOException;
	
	// return coupon collection from the DAO by predefined filter (FilterCouponDBDao)
	public Collection<Coupon> getCoupons(Customer customer, FilterCouponDBDao filters, boolean isAnd) throws DAOException;

	// return customer object by customer name
	public Collection<Customer> getCustomerByName(String customerName) throws DAOException;
	
	//
	// Move from CustomerCouponDAO
	//
	
	// return all coupons from the DAO by customer from join customer-coupon
	//public Collection<Coupon> getCoupons(Customer customer) throws DAOException; - is necessery ?
	
	// remove all coupons from the DAO by customer from join customer-coupon
	public void removeCoupons(Customer customer) throws DAOException;
	
	// create new record in join customer-coupon with customer and coupon ids
	public void LinkNewCustomerCoupon(Customer customer, Coupon coupon) throws DAOException;
	
	// check if coupon id belong to specific customer
	public boolean couponBelongsTo(long couponId, long customerId) throws DAOException;
	
	// return id of the company which the coupon belong to
	public long getCompanyIdByCouponId(long id) throws DAOException;
	
	//
	// login flag
	//
	
	public long login(String compName, String password) throws DAOException;
}
