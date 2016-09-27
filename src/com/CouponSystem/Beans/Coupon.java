package com.CouponSystem.Beans;

import java.time.*;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import com.CouponSystem.JSONSerial.*;

@XmlRootElement
public class Coupon 
{
	
	//
	// Attributes
	//
	
	private long id;
	private String title = null;
	@XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
	private LocalDateTime startDate;
	@XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
	private LocalDateTime endDate;
	private int amount = 0;
	private CouponType type = CouponType.OTHER;
	private String message = null;
	private double price = 0;
	private String image = "None";
	
	// 
	// Constructor
	//
	
	public Coupon()
	{
		
	}


	
	//
	// getter and setters
	//

	public long getId() {
		return id;
	}
	
	public void setId(long id)
	{
		if (id > 0)
		{
			this.id = id;
		}
		else
		{
			throw new IllegalArgumentException("Id can't be negative");
		}
	}

	public String getTitle() 
	{
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public LocalDateTime getStartDate() 
	{
		return startDate;
	}

	public void setStartDate(LocalDateTime startDate) {
		if (this.endDate != null && this.endDate.isBefore(startDate))
		{
			throw new IllegalArgumentException("Start date must to be before End date");
		}
		this.startDate = startDate;
	}

	public LocalDateTime getEndDate() 
	{
		return endDate;
	}

	public void setEndDate(LocalDateTime endDate) {
		if (this.startDate != null && this.startDate.isAfter(endDate))
		{
			throw new IllegalArgumentException("Start date must to be before End date");
		}
		this.endDate = endDate;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		if (amount < 0)
		{
			throw new IllegalArgumentException("Amount must to be 0 or bigger");
		}
		this.amount = amount;
	}

	public CouponType getType() {
		return type;
	}

	public void setType(CouponType type) {
		this.type = type;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		if (price < 0)
		{
			throw new IllegalArgumentException("Price must to be 0 or more");
		}
		this.price = price;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		if (image.equals(""))
		{
			image = "None";
		}
		this.image = image;
	}

	//
	// methods
	//

	@Override
	public String toString() 
	{
		return "Coupon [id=" + id + ", title=" + title + ", startDate=" + startDate + ", endDate=" + endDate
				+ ", amount=" + amount + ", type=" + type + ", message=" + message + ", price=" + price + ", image="
				+ image + "]";
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + amount;
		result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((image == null) ? 0 : image.hashCode());
		result = prime * result + ((message == null) ? 0 : message.hashCode());
		long temp;
		temp = Double.doubleToLongBits(price);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((startDate == null) ? 0 : startDate.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		Coupon other = (Coupon) obj;
		if (amount != other.amount)
			return false;
		if (endDate == null) {
			if (other.endDate != null)
				return false;
		} else if (!endDate.equals(other.endDate))
			return false;
		if (id != other.id)
			return false;
		if (image == null) {
			if (other.image != null)
				return false;
		} else if (!image.equals(other.image))
			return false;
		if (message == null) {
			if (other.message != null)
				return false;
		} else if (!message.equals(other.message))
			return false;
		if (Double.doubleToLongBits(price) != Double.doubleToLongBits(other.price))
			return false;
		if (startDate == null) {
			if (other.startDate != null)
				return false;
		} else if (!startDate.equals(other.startDate))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		if (type != other.type)
			return false;
		return true;
	}
	

	

	
}
