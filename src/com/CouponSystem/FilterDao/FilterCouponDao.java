package com.CouponSystem.FilterDao;

import java.time.LocalDateTime;

import com.CouponSystem.Beans.CouponType;
import com.CouponSystem.DBDAO.CouponField;

import DAOException.DAOException;

public interface FilterCouponDao 
{


	String[] filters = null;
	 
	public void addFilter(CouponField couponField, String compareSign, String value) throws DAOException;
	public void addFilter(CouponField couponField, String compareSign, int value) throws DAOException;
	public void addFilter(CouponField couponField, String compareSign, long value) throws DAOException;
	public void addFilter(CouponField couponField, String compareSign, double value) throws DAOException;
	public void addFilter(CouponField couponField, String compareSign, LocalDateTime value) throws DAOException;
	public void addFilter(CouponField couponField, String compareSign, CouponType value) throws DAOException;
	public String getFiltersAnd() throws DAOException;
	public String getFiltersOr() throws DAOException;
	public int length() throws DAOException;
	public CouponField getFilterField(int item) throws DAOException;
	public void removeFilter(int item) throws DAOException;
}
