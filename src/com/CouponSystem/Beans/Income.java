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

	private static final long serialVersionUID = 1L;

	
	private long id;
	
	private String name;
	
	@Type(type="LocalDateTimeType")
	private LocalDateTime date;
	
	// Use the custom translator - transfer int to MySql
	@Type(type="com.CouponType.Beans.IncomeTypeUserType")
	private IncomeType description;

	private double amount;
	
	private long custId;
	
	private long compId;
	
	public Income() {
		
	}
	

	public Income(String name, LocalDateTime date, IncomeType description, double amount, long custId, long compId) {
		this.name = name;
		this.date = date;
		this.description = description;
		this.amount = amount;
		this.custId = custId;
		this.compId = compId;
	}
	

	
	@Id
	public long getId() {
		return id;
	}
	

	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private void setId(long id) {
		
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
	
	public void setAmount(double amount) 
	{
			this.amount = amount;
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
