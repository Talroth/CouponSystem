package com.CouponSystem.Tests;

import com.CouponSystem.Beans.Company;
import com.CouponSystem.CouponSystem.*;
import com.CouponSystem.Facade.AdminFacade;

public class Test2 
{
	public static void main(String[] args) 
	{
		CouponSystem mainControl = CouponSystem.getInstance();
		
		try
		{
			//
			// login with admin user
			//
			AdminFacade admin = (AdminFacade)mainControl.login("admin", "1234", "admin");
			
			//
			// init company object with company id 6 from the db
			//
			Company comp = admin.getCompany(8);
			
			//
			//load company details
			//
			
			System.out.println("Load company id 8");
			if (comp != null)
			{
				System.out.println(comp.toString());
			}
			else
			{
				System.out.println("Company not exist");
			}
		}
		catch (Exception e)
		{
			
		}
	}
		
//			
//			//
//			// Show all customers from the db
//			//
//			
//			System.out.println("Show all customers in the system");
//			for (Customer c : admin.getAllCustomer())
//			{
//				System.out.println(c.toString());
//			}
//			
//			//
//			// Show all companies in from the db
//			//
//			
//			System.out.println("Show all companies in the system");
//			for (Company c : admin.getAllCompanies())
//			{
//				System.out.println(c.toString());
//			}
//			
//			//
//			// Updating email address for company id 6
//			//
//			
//			String newEmail = "info@gmh.com";
//			System.out.println("Updating " + comp.getCompName() + ", change email from " + comp.getEmail() + " to " + newEmail);
//			comp.setEmail(newEmail);
//			admin.updateCompany(comp);
//			System.out.println("New company details: ");
//			System.out.println(comp.toString());
//			System.out.println("--------------------------------------------------------------");
//			
//			//
//			// Trying change company's name of company id 6
//			//
//			
//			System.out.println("Updating " + comp.getCompName() + ", changing the name to yosef");
//			comp.setCompName("yosef");
//			admin.updateCompany(comp);
//			System.out.println("New company details: ");
//			System.out.println(admin.getCompany(6).toString());
//			
//			//
//			// Create new customer
//			//
//			
//			Customer cust = new Customer();
//			cust.setCustName("Testing9");
//			cust.setPassword("abcd");
//
//			admin.createCustomer(cust);
//			
//			if (cust.getId() !=0)
//			{
//				System.out.println("New customer details:");
//				System.out.println(cust.toString());
//			}
//
//			
//			//
//			// Remove the new customer from the db
//			//
//			System.out.println("***************************************************************************************");
//			System.out.println("Removing customer id " + cust.getId());
//			admin.removeCustomer(cust);
//			System.out.println("Customer " + cust.getId() + " details:");
//			if (admin.getCustomer(cust.getId()) != null)
//			{
//				System.out.println(admin.getCustomer(cust.getId()));
//			}
//			else
//			{
//				System.out.println("Customer not exists anymore");
//			}
//			System.out.println("*****************************************************************************************");
//			
//			
//
//			//
//			// Shut down the system
//			//
//			
//			mainControl.shutdown();
//			 
//		}
//		catch (IllegalArgumentException e)
//		{
//			System.out.println("User type is not legal");
//		}
//		catch (DAOException eDAO)
//		{
//			System.out.println(eDAO.getMessage());
//		}
//		catch (NullPointerException eNull)
//		{
//			System.out.println(eNull.getMessage());
//		}
//	
//	}
}
