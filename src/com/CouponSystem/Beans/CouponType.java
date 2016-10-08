package com.CouponSystem.Beans;

import javax.xml.bind.annotation.XmlRootElement;

// Enum of coupon types

@XmlRootElement
public enum CouponType 
{
	RESTURANS("RESTURANS"),
	ELECTRICITY("ELECTRICITY"),
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
