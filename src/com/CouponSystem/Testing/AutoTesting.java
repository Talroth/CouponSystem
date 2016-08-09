package com.CouponSystem.Testing;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

import com.CouponSystem.Beans.Company;
import com.CouponSystem.CouponSystem.CouponSystem;
import com.CouponSystem.Facade.AdminFacade;
import com.CouponSystem.FacadeException.FacadeException;

public class AutoTesting 
{
	private static FileWriter out;
	
	public static void main(String[] args) 
	{
		try 
		{
			String newline = System.getProperty("line.separator");
			File outputFile = new File("test.txt");
			out = new FileWriter(outputFile);
			CouponSystem mainSys = CouponSystem.getInstance();
			try 
			{
				AdminFacade adminUser = (AdminFacade)mainSys.login("admin", "1234", "admin");
				Company company = new Company();
				
				company.setCompName("AutoCompany1");
				company.setPassword("h1h1");
				company.setEmail("AutoCompany1@coupon.com");
				out.write("Company details:" + newline);
				out.write(company.toString() + newline);
				
				adminUser.createCompany(company);
				out.write("Company " + company.getCompName() + " was created successfully" + newline);
				
				//************************************************************************************************************
				out.write("Updating company e-mail address:" + newline);
				company.setEmail("AutoCompany1@couponsystem.com" + newline);
				adminUser.updateCompany(company);
				out.write(company.toString() + newline);
				out.write("Company e-mail was updated successfully" + newline);
				//************************************************************************************************************
				
				out.close();
				mainSys.shutdown();
				
			} 
			catch (FacadeException e) 
			{
				out.write(e.getMessage());
			} catch (InterruptedException e) 
			{
				out.write(e.getMessage());
			}
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		

	}

}
