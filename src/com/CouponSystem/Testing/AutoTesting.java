package com.CouponSystem.Testing;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

import com.CouponSystem.Beans.Company;
import com.CouponSystem.Beans.Coupon;
import com.CouponSystem.Beans.CouponType;
import com.CouponSystem.Beans.Customer;
import com.CouponSystem.CouponSystem.CouponSystem;
import com.CouponSystem.Facade.AdminFacade;
import com.CouponSystem.Facade.CompanyFacade;
import com.CouponSystem.Facade.CustomerFacade;
import com.CouponSystem.FacadeException.FacadeException;

public class AutoTesting 
{
	private static FileWriter out;
	private static Company[] company = new Company[3]; 
	private static Customer[] customer = new Customer[3];
	private static Coupon[] coupon = new Coupon[4];
	private static CouponSystem mainSys = CouponSystem.getInstance();
	
	public static void main(String[] args) 
	{
		try 
		{
			company[0] = new Company("McHoland", "f12e12", "McHoland@gmail.com");
			company[1] = new Company("CafeOfea", "g777", "CafeOfea@gmail.com");
			company[2] = new Company("GymX","uuu", "GymX@yahoo.com");
			
			customer[0] = new Customer("Bill Joe","bj");
			customer[1] = new Customer("Lili Cohen","lc");
			customer[2] = new Customer("Gabi Abutbul","ga");
			
			coupon[0] = new Coupon();
			coupon[0].setTitle("Take 3 Trainings pay for 2");
			coupon[0].setStartDate(LocalDateTime.parse("2014-10-05T00:00"));
			coupon[0].setEndDate(LocalDateTime.parse("2016-12-05T00:00"));
			coupon[0].setAmount(100);
			coupon[0].setType(CouponType.SPORTS);
			coupon[0].setMessage("The coolest GYM in the world");
			coupon[0].setPrice(450D);
			coupon[0].setImage("none");
			
			coupon[1] = new Coupon();
			coupon[1].setTitle("1/2 price burger");
			coupon[1].setStartDate(LocalDateTime.parse("2016-10-08T00:00"));
			coupon[1].setEndDate(LocalDateTime.parse("2017-12-08T00:00"));
			coupon[1].setAmount(70);
			coupon[1].setType(CouponType.FOOD);
			coupon[1].setMessage("The best BURGER in the world");
			coupon[1].setPrice(34.20D);
			coupon[1].setImage("none");
			
			coupon[2] = new Coupon();
			coupon[2].setTitle("2 cafes and mafe for 19.99 NIS only");
			coupon[2].setStartDate(LocalDateTime.parse("2016-09-22T00:00"));
			coupon[2].setEndDate(LocalDateTime.parse("2018-08-22T00:00"));
			coupon[2].setAmount(3000);
			coupon[2].setType(CouponType.FOOD);
			coupon[2].setMessage("The best cafe in the world");
			coupon[2].setPrice(19.99D);
			coupon[2].setImage("none");
			
			coupon[3] = new Coupon();
			coupon[3].setTitle("Breakfast for 69.50 NIS only");
			coupon[3].setStartDate(LocalDateTime.parse("2016-09-22T00:00"));
			coupon[3].setEndDate(LocalDateTime.parse("2018-08-22T00:00"));
			coupon[3].setAmount(3000);
			coupon[3].setType(CouponType.FOOD);
			coupon[3].setMessage("The best breakfast in the world");
			coupon[3].setPrice(69.50D);
			coupon[3].setImage("none");
			
			//*************************************************************************************
			
			String newline = System.getProperty("line.separator");
			File outputFile = new File("test.txt");
			out = new FileWriter(outputFile);
			
				AdminFacade adminUser = (AdminFacade)mainSys.login("admin", "1234", "admin");

				for (int index = 0; index < company.length; index++)
				{
					out.write("Company details:" + newline);
					out.write(company[index].toString() + newline);				
					adminUser.createCompany(company[index]);
					out.write("Company " + company[index].getCompName() + " was created successfully" + newline);
				}
				
				//************************************************************************************************************
				out.write("Updating company e-mail address:" + newline);
				company[0].setEmail("McHoland@walla.com" + newline);
				adminUser.updateCompany(company[0]);
				out.write(company[0].toString() + newline);
				out.write("Company e-mail was updated successfully" + newline);
				
				//************************************************************************************************************
				for (int index = 0; index < customer.length; index++)
				{
					out.write("Customer details:" + newline);
					out.write(customer[index].toString() + newline);				
					adminUser.createCustomer(customer[index]);
					out.write("Customer " + customer[index].getCustName() + " was created successfully" + newline);
				}	
				//************************************************************************************************************
				
				CompanyFacade companyUser = (CompanyFacade)mainSys.login(company[1].getCompName(), company[1].getPassword(), "company");
				out.write(companyUser.getCompanyDetails() + " create 2 coupons");
				companyUser.createCoupon(coupon[2]);
				companyUser.createCoupon(coupon[3]);
								
				out.write("Coupons details for " + companyUser.getCompanyDetails() + ":" + newline);
				for (Coupon c : companyUser.getAllCoupons())
				{
					out.write(c.toString() + newline);
				}
				
				//************************************************************************************************************
				
				companyUser = (CompanyFacade)mainSys.login(company[0].getCompName(), company[0].getPassword(), "company");
				out.write(companyUser.getCompanyDetails() + " create a coupon");
				companyUser.createCoupon(coupon[1]);
				
				out.write("Coupons details for " + companyUser.getCompanyDetails() + ":" + newline);
				for (Coupon c : companyUser.getAllCoupons())
				{
					out.write(c.toString() + newline);
				}
				
				//************************************************************************************************************	
				
				companyUser = (CompanyFacade)mainSys.login(company[2].getCompName(), company[2].getPassword(), "company");
				out.write(companyUser.getCompanyDetails() + " create a coupon");
				companyUser.createCoupon(coupon[0]);
				
				out.write("Coupons details for " + companyUser.getCompanyDetails() + ":" + newline);
				for (Coupon c : companyUser.getAllCoupons())
				{
					out.write(c.toString() + newline);
				}
				
				//************************************************************************************************************	
				CustomerFacade customerUser = (CustomerFacade)mainSys.login(customer[0].getCustName(), customer[0].getPassword(), "customer");
				out.write("Customer " + customer[0].getCustName() + " going to Purchase 1 coupon from the GYM" + newline);
				
				customerUser.purchaseCoupon(coupon[0]);
				out.write("Customer " + customer[0].getCustName() + " coupon list:" + newline);
				for (Coupon c : customerUser.getAllPurchasedCoupons())
				{
					out.write(c.toString() + newline);
				}
				
				out.write("Customer " + customer[0].getCustName() + " going to Purchase 1 coupon from the McHoland" + newline);				
				customerUser.purchaseCoupon(coupon[1]);
				out.write("Customer " + customer[0].getCustName() + " coupon list:" + newline);
				for (Coupon c : customerUser.getAllPurchasedCoupons())
				{
					out.write(c.toString() + newline);
				}
				
				out.write(customer[0].getCustName() + " FOOD coupons:" + newline);
				for (Coupon c : customerUser.getAllPurchasedCouponsByType(CouponType.FOOD))
				{
					out.write(c.toString() + newline);
				}
				
				out.write(customer[0].getCustName() + " coupon with max price of 150:" + newline);
				for (Coupon c : customerUser.getAllPurchasedCouponsByPrice(150D))
				{
					out.write(c.toString() + newline);
				}
				
				out.write("GYM coupon going to be updated to expired 2 days ago" + newline);
				coupon[0].setEndDate(LocalDateTime.now().minusDays(2));
				companyUser.updateCoupon(coupon[0]);
				
				//************************************************************************************************************
				companyUser = (CompanyFacade)mainSys.login(company[1].getCompName(), company[1].getPassword(), "company");
				out.write(companyUser.getCompanyDetails() + " delete one of its coupon");
				companyUser.removeCoupon(coupon[3]);
				out.write("Coupons details for " + companyUser.getCompanyDetails() + ":" + newline);
				for (Coupon c : companyUser.getAllCoupons())
				{
					out.write(c.toString() + newline);
				}
				//************************************************************************************************************	
				
				out.write("Lili decide to stop using our service, therefore will be remove" + newline);
				adminUser.removeCustomer(customer[1]);
				out.write("List of users:" + newline);
				for (Customer cust : adminUser.getAllCustomer())
				{
					out.write(cust.toString() + newline);
				}				//************************************************************************************************************	
				
			} 

		catch (FacadeException e) 
		{
			try 
			{
				out.write(e.getMessage());
			} 
			catch (IOException e1) 
			{
				System.out.println("Failure with file writing");
			}
		} 
		catch (IOException e) 
		{
			System.out.println("Failure with file writing");
		}
		finally
		{
			try 
			{
				out.close();
				mainSys.shutdown();
			} 
			catch (IOException e) 
			{
				System.out.println("Failure with file writing");
			} 
			catch (InterruptedException e) 
			{
				System.out.println("Thread was interupted");
			}
			
		}
		

	}

}
