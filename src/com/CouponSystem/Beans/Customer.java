package com.CouponSystem.Beans;

import java.util.*;

public class Customer 
{

	//
	// Attributes
	//
	
	private long id;
	private String custName;
	private String password;
	private Collection<Coupon> coupons = null;
	
	//
	// Constructors
	//
	
	public Customer()
	{
		
	}
	
	public Customer(String name, String password)
	{
		if (name.equals("") || password.equals(""))
		{
			throw new IllegalArgumentException("Name and Password can't be blank");
		}
		this.custName = name;
		this.password = password;
	}

	// 
	// getter and setters
	//
	
	
	public long getId() {
		return id;
	}

	public void setId(long id)
	{
		this.id = id;
	}
	
	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		if (custName.equals(""))
		{
			throw new IllegalArgumentException("Name can't be blank");
		}
		this.custName = custName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		if (password.equals(""))
		{
			throw new IllegalArgumentException("Password can't be blank");
		}
		this.password = password;
	}

	public Collection<Coupon> getCoupons() {
		return coupons;
	}
	
	public void setCoupons(Collection<Coupon> coupons)
	{
		this.coupons = coupons;
	}

	
	//
	// methods
	//
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((custName == null) ? 0 : custName.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Customer other = (Customer) obj;
		if (custName == null) {
			if (other.custName != null)
				return false;
		} else if (!custName.equals(other.custName))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", custName=" + custName + ", password=" + password + "]";

	}
	
	
	
	
}
