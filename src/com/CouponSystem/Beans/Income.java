package com.CouponSystem.Beans;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table(name="incomelog")
public class Income implements Serializable {

	// MySql - BIGINT
	// auto works but intefer with localdatetime type


/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//	@GeneratedValue(strategy=GenerationType.AUTO)

	private long id;
	
	private String name;
	
	//TODO: check if works
	@org.hibernate.annotations.Type( type = "LocalDateTimeType" )
	private LocalDateTime date;
	
	// Use the custom translator - transfer int to MySql
	@Type(type="com.CouponType.Beans.IncomeTypeUserType")
	private IncomeType description;
	
	// use DOUBLE in MySql
	private double amount;
	
	private long custId;
	
	private long compId;
	
	public Income() {
		
	}
	
	// for income with coupon price (customer purchase a coupon)
	public Income(long id, String name, LocalDateTime date, IncomeType description, double couponPrice, long custId, long compId) {
		this.id = id;
		this.name = name;
		this.date = date;
		this.description = description;
		this.description = description;
		this.amount = couponPrice;
		this.custId = custId;
		this.compId = compId;
	}
	
	// for income without coupon price 
	public Income(long id, String name, LocalDateTime date, IncomeType description, long custId, long compId) {
		this.id = id;
		this.name = name;
		this.date = date;
		this.description = description;
		this.custId = custId;
		this.compId = compId;
	}
	
	@Id
//	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public long getId() {
		return id;
	}
	
	// not allow id with negative id
	// TODO: auto numbering should be added annotation, generatedvalue doesn't works
//	@Id	
	public void setId(long id) {
//		if (id >= 0)
//		{
//			this.id = id;
//		}
//		else
//		{
//			throw new IllegalArgumentException("Id can't be negative");
//		}
		
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public LocalDateTime getDate() {
		return date;
	}
	
	public void setDate(LocalDateTime date) {
		this.date = date;
	}
	
	public IncomeType getDescription() {
		return description;
	}
	
	public void setDescription(IncomeType description) {
		this.description = description;
	}
	
	public double getAmount() {
		return amount;
	}
	
	// if customer purchase use the price, otherwise use the predefined price
	public void setAmount(double amount) {
//		// if the action is customer coupon purchase it use the amount according to coupon price
//		if (this.description.isEqual(IncomeType.CUSTOMER_PURCHASE))
//		{
			this.amount = amount;
//		}
//		else
//		{
//			amount = description.getPrice();
//		}
	}

	public long getCustId() {
		return custId;
	}

	public void setCustId(long custId) {
		this.custId = custId;
	}

	public long getCompId() {
		return compId;
	}

	public void setCompId(long compId) {
		this.compId = compId;
	}

	@Override
	public String toString() {
		return "Income [id=" + id + ", name=" + name + ", date=" + date + ", description=" + description + ", amount="
				+ amount + "]";
	}
	
	
	
}
