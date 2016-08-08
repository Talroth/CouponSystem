package com.CouponSystem.DAO;

import java.util.*;

import com.CouponSystem.Beans.*;
import com.CouponSystem.FilterDao.FilterCouponDao;

import DAOException.DAOException;

//
//Interface to company  table
//

public interface CompanyDAO 
{
	//
	// C.R.U.D actions
	//
	
	public long createCompany(Company company) throws DAOException;
	public void removeCompany(Company company) throws DAOException;
	public void updateCompany(Company company) throws DAOException;
	
	
	// return company object by id from the DAO
	public Company getCompany(long id) throws DAOException;
	
	// return all companies from the DAO
	public Collection<Company> getAllCompanies() throws DAOException;
	
	// return all coupons from the DAO per company
	public Collection<Coupon> getCoupons(Company company) throws DAOException;
	
	// return all coupons from the DAO per company with filter
	public Collection<Coupon> getCoupons(Company company, FilterCouponDao filters, boolean isAnd) throws DAOException;
	
	// return coupon from the DAO by id and verify it is belong to specific company
	public Coupon getCoupon(Company company, long Id) throws DAOException;
	
	// get company id by it's name from the DAO
	public Long getIdByCompanyName(String name) throws DAOException;
	

	//
	// login flag
	//
	
	// verify user name and password against company list in the DAO
	public long login(String compName, String password) throws DAOException;
}
