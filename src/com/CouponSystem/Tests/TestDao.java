package com.CouponSystem.Tests;

import java.util.Collection;

import com.CouponSystem.Facade.AdminFacade;
import com.CouponSystem.FacadeException.FacadeException;
import com.CouponSystem.Beans.*;

public class TestDao {

	public static void main(String[] args) 
	{
//		Customer cust = new Customer();	
//		cust.setId(54);
//		cust.setCustName("Tal");
//		cust.setPassword("Boola2");
//		CustomerDAO custDb = new CustomerDBDAO();
//		try 
//		{
//			custDb.login("Tal", "Boo");
////			custDb.createCustomer(cust);
//		}
//
//	catch (DAOException e1) 
//	{
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//	}
//		cust.setCustName("Moshe");
//		cust.setPassword("Boo12");
//		custDb.updateCustomer(cust);
//		Customer cust2 = custDb.getCustomer(10);
//		System.out.println(cust2.getPassword());
//		
//		custDb.removeCustomer(cust);
//		
//		Collection<Customer> custs = custDb.getAllCustomer();
//		for (Customer c : custs)
//		{
//			System.out.println(c.getId() + " " + c.getCustName() + " " + c.getPassword());
//		}
		
		AdminFacade admin = new AdminFacade();
		
		try 
		{
			admin.login("admin", "1234");
			Collection<Company> companies = admin.getAllCompanies();
			for (Company c : companies)
			{
				System.out.println(c.getCompName());
			}
		} 
		catch (FacadeException e) {

			System.out.println("Failed");
		}
//		admin.createCustomer(cust);
//		admin.updateCustomer(cust);
//		cust.setCustName("hh");
//		admin.updateCustomer(cust);
//		System.out.println("[]" + admin.getCustomer(8).getId());
//		System.out.println(admin.getAllCustomer().size());
		
//		Coupon cop = new Coupon();
//		cop.setTitle("H1");
//		cop.setStartDate(LocalDateTime.of(2016, 6, 25, 22, 43));
//		cop.setEndDate(LocalDateTime.of(2016, 7, 25, 22, 43));
//		CouponDBDAO copDb = new CouponDBDAO();
//		try 
//		{
//			cop.setType(CouponType.CAMPING);
//			cop.setImage("c:\\bla\\bla");
//			cop.setPrice(4.35);
//			copDb.createCoupon(cop);
//			cop.setAmount(100);
//			cop.setType(CouponType.CAMPING);
//			copDb.updateCoupon(cop);
//			System.out.println(cop.toString());
//		} 
//		catch (DAOException e) 
//		{
//			// TODO Auto-generated catch block
//			System.out.println(e.getMessage());
//		}
		
//		Company comp = new Company();
//		comp.setCompName("G.M.H9");
//		comp.setEmail("info@gmh.com");
//		comp.setPassword("aaa");
//
//		try
//		{
////			admin.createCompany(comp);
////			System.out.println("ID: " + comp.getId());
////			comp.setEmail("bb@bb.com");
////			admin.updateCompany(comp);
//			System.out.println(admin.getAllCompanies());
//			System.out.println(admin.getCompany(7));
//			System.out.println(admin.getAllCustomer());
//			System.out.println(admin.getCustomer(50));
//			comp = admin.getCompany(10);
//			admin.removeCompany(comp);
//		}
//		catch (DAOException e)
//		{
//			System.out.println(e.getMessage());
//		}
		
//		try
//		{
//			Customer cust = admin.getCustomer(3);
//			Company comp;
//			try {
//				comp = admin.getCompany(5);
//				admin.removeCompany(comp);
//				
//			} catch (DAOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			
//			System.out.println(admin.login("admin", "1234"));
			
			//Collection<Coupon> lst = admin.getCoupons(admin.getCustomer(1));
			
//			Collection<Coupon> lst = admin.ge
//			
//			ArrayList<Coupon> has = (ArrayList<Coupon>)lst;
			
			
//			for (Coupon c : has)
//			{
//				System.out.println(c);
//			}

//			admin.removeCustomer(cust);
			
//		}
//		catch (DAOException e)
//		{
//			System.out.println(e.getMessage());
//		}
	}
}
