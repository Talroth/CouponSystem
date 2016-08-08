package com.CouponSystem.Beans;

// Enum of coupon types

public enum CouponType 
{
	RESTURANS("RESTURANS"),
	ELECTRICTY("ELECTRICTY"),
	FOOD("FOOD"),
	HEALTH("HEALTH"),
	SPORTS("SPORTS"),
	CAMPING("CAMPING"),
	TRAVELLING("TRAVELLING"),
	OTHER("OTHER");
	
	private String type;
	
	// in case type is blank string it is by default become OTHER
	CouponType(String type)
	{
		if (type.isEmpty())
		{
			this.type = "OTHER";
		}
		else
		{
			this.type = type;
		}
	}
	
	 public String getText() 
	 {
		return this.type;
	 }
}
