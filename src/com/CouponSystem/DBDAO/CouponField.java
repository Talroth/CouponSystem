package com.CouponSystem.DBDAO;

//
// Enum for fields of coupon table
//


public enum CouponField 
{

	ID("ID"),
	TITLE("TITLE"),
	START_DATE("START_DATE"),
	END_DATE("END_DATE"),
	AMOUNT("AMOUNT"),
	TYPE("TYPE"),
	MESSAGE("MESSAGE"),
	PRICE("PRICE"),
	IMAGE("IMAGE");
	
	private String name;

	CouponField(String name)
	{
		if (name.isEmpty())
		{
			this.name = "";
		}
		else
		{
			this.name = name;
		}
	}

	 public String getText() 
	 {
			return this.name;
	 }
	 
	 //
	 // Return the Enum from the text value of the enum
	 //
	 
	  public static CouponField fromString(String text) 
	  {
		   if (text != null) 
		   {
		      for (CouponField cf : CouponField.values()) 
		      {
		        if (text.equalsIgnoreCase(cf.name)) 
		        {
		          return cf;
		        }
		      }
		      return null;
		    }
		   else
		   {
			   return null;
		   }
	  }
}


