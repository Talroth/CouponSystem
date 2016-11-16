package com.CouponSystem.FilterDBDao;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import com.CouponSystem.Beans.CouponType;
import com.CouponSystem.DBDAO.CouponField;
import com.CouponSystem.FilterDao.FilterCouponDao;

import DAOException.DAOException;

public class FilterCouponDBDao implements FilterCouponDao {

	List<String> filters = new ArrayList<String>();
	public final static String EQUAL = "=";
	public final static String NON_EQUAL = "<>";
	public final static String BIGGER_THAN = ">";
	public final static String SMALLER_THAN = "<";
	public final static String BIGGER_OR_EQUAL = ">=";
	public final static String SMALLER_OR_EQUAL = "<=";
	
	// Adding filter to filters list depend on value type
	@Override
	public void addFilter(CouponField couponField, String compareSign, String value) throws DAOException {
		if (!this.equalNonEqualSignValid(compareSign)) { throw new IllegalArgumentException(); }
		filters.add(couponField.getText() + " " + compareSign + " " + "'" + value + "'");
	}

	@Override
	public void addFilter(CouponField couponField, String compareSign, int value) throws DAOException {
		if (!this.allSignValid(compareSign)) { throw new IllegalArgumentException(); }
		filters.add(couponField.getText() + " " + compareSign + " " +  value);

	}

	@Override
	public void addFilter(CouponField couponField, String compareSign, long value) throws DAOException {
		if (!this.allSignValid(compareSign)) { throw new IllegalArgumentException(); }
		filters.add(couponField.getText() + " " + compareSign + " " +  value);
	}

	@Override
	public void addFilter(CouponField couponField, String compareSign, LocalDateTime value) throws DAOException {
		if (!this.allSignValid(compareSign)) { throw new IllegalArgumentException(); }
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		
		filters.add(couponField.getText() + " " + compareSign + " '" + value.format(formatter) + "'");

	}

	
	@Override
	public void addFilter(CouponField couponField, String compareSign, double value) throws DAOException {
		
		if (!this.allSignValid(compareSign)) { throw new IllegalArgumentException(); }
		filters.add(couponField.getText() + " " + compareSign + " " +  value);
		
	}

	@Override
	public void addFilter(CouponField couponField, String compareSign, CouponType value) throws DAOException {
		if (!this.equalNonEqualSignValid(compareSign)) { throw new IllegalArgumentException(); }
		filters.add(couponField.getText() + " " + compareSign + " " + "'" + value.getText() + "'");
		
	}
	
	// call to connect all filters with AND 
	@Override
	public String getFiltersAnd() throws DAOException {
		
		return getFilters("AND");
	}
	
	// call to connect all filters with OR
	@Override
	public String getFiltersOr() throws DAOException {
		return getFilters("OR");
	}
	
	// connect all filters depend if it is "AND" or "OR"
	private String getFilters(String seprator) throws DAOException {
		String finalFilter = "";
		for (int index = 0; index < filters.size(); index++)
		{
			if (index == filters.size() - 1)
			{
				seprator = "";
			}
			finalFilter = finalFilter + filters.get(index) + " " + seprator + " ";
		}
		
		
		return finalFilter;
	}

	@Override
	public int length() throws DAOException {
		// return number of filters in the filters list
		return filters.size();
	}

	@Override
	public CouponField getFilterField(int item) throws DAOException 
	{
		// return the filter type according to index from filters list
		return  CouponField.fromString((filters.get(item).substring(0, filters.get(item).indexOf(' '))));
	}

	@Override
	public void removeFilter(int item) throws DAOException {
		// remove filter according to index from the filters list
		filters.remove(item);
		
	}
	
	private boolean equalNonEqualSignValid(String sign)
	{
		return (sign.equals("=") || sign.equals("<>"));

	}
	
	private boolean allSignValid(String sign)
	{
		// Check if the sign is valid for use
		String[] validSigns = {"=", "<>", ">", "<", "<=", ">="};
		boolean valid = false;
		
		for (String test : validSigns)
		{
			if (test.equals(sign))
			{
				valid = true;
				break;
			}
		}
		
		return valid;
	}








}
