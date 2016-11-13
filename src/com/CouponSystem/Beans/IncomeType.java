package com.CouponSystem.Beans;


public enum IncomeType 
{

	CUSTOMER_PURCHASE("Customer coupon purchasing"),
	COMPANY_NEW_COUPON("Company create new coupon",100D),
	COMPANY_UPDATE_COUPON("Coupon is updated by company",10D);
	
	private String description;
	private double price;

	
	private IncomeType(String description)
	{
		if (description.isEmpty())
		{
			this.description = "";
		}
		else
		{
			this.description = description;
		}
	}
	
	private IncomeType(String description,double price)
	{
		if (description.isEmpty())
		{
			this.description = "";
		}
		else
		{
			this.description = description;
		}
		this.price = price;
	}

	 public String getDescription() 
	 {
			return this.description;
	 }
	 
	 public double getPrice()
	 {
		 return price;
	 }
	 
	 // check if this description is the same as other IncomeType object
	 public boolean isEqual(IncomeType compareWith)
	 {
		 return this.getDescription().equals(compareWith.getDescription());
	 }

}
