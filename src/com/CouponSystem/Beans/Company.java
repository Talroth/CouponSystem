package com.CouponSystem.Beans;

import java.util.*;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
// Bean class for company
public class Company
{
	
	//
	// Attributes
	//
	
	private long id;
	private String compName;
	private String password;
	private String email;
	private Collection<Coupon> coupons;
	
	//
	// Constructors
	//
	
	public Company()
	{
		
	}
	
	// construct with init company name, password and email address
	public Company(String companyName, String password, String email)
	{
		if (companyName.equals("") || password.equals("") || email.equals(""))
		{
			throw new IllegalArgumentException("All arguments cannot be empty");
		}
		this.compName = companyName;
		this.password = password;
		this.email = email;
	}

	//
	// getters and setters
	//
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		if (id > 0)
		{
			this.id = id;
		}
		else
		{
			throw new IllegalArgumentException("Id can't be negative");
		}
	}

	public String getCompName() {
		return compName;
	}

	public void setCompName(String compName) {
		if (compName.equals(""))
		{
			throw new IllegalArgumentException("Company name can't be empty");
		}
		this.compName = compName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		if (password.equals(""))
		{
			throw new IllegalArgumentException("Password can't be empty");
		}
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		if (email.equals(""))
		{
			throw new IllegalArgumentException("Email can't be empty");
		}
		this.email = email;
	}

	public void setCoupons(Collection<Coupon> coupons)
	{
		this.coupons = coupons;
	}
	
	public Collection<Coupon> getCoupons() {
		return coupons;
	}

	//
	// methods
	//
	
	@Override
	public String toString() {
		return "Company [id=" + id + ", compName=" + compName + ", password=" + password + ", email=" + email + "]";
	}
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((compName == null) ? 0 : compName.hashCode());
		result = prime * result + ((coupons == null) ? 0 : coupons.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result
				+ ((password == null) ? 0 : password.hashCode());
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
		Company other = (Company) obj;
		if (compName == null) {
			if (other.compName != null)
				return false;
		} else if (!compName.equals(other.compName))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (id != other.id)
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		return true;
	}
	

	

	
	
	
	
}
