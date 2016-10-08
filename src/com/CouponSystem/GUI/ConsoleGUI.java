package com.CouponSystem.GUI;

import com.CouponSystem.Beans.Company;
import com.CouponSystem.Beans.Coupon;
import com.CouponSystem.Beans.CouponType;
import com.CouponSystem.Beans.Customer;
import com.CouponSystem.CouponSystem.CouponSystem;
import com.CouponSystem.Facade.AdminFacade;
import com.CouponSystem.Facade.CompanyFacade;
import com.CouponSystem.Facade.CustomerFacade;
import com.CouponSystem.FacadeException.FacadeException;

import DAOException.DAOExceptionErrorType;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.concurrent.TimeUnit;


public class ConsoleGUI 
{
	public static void main(String[] args) 
	{
		CouponSystem mainSystem = CouponSystem.getInstance();
		
		// Allow change the default of the scanner intervals - should be for admin only or by loading from config file, this is for the test only
		mainSystem.setScannerDelay(30, TimeUnit.SECONDS);
		
		Scanner sc = new Scanner(System.in);
		boolean logout = false;
		boolean quit = false;
		String name = null;
		String password = null;
		AdminFacade adminUser = null;
		CustomerFacade customerUser = null;
		CompanyFacade companyUser = null;
		
		System.out.println("Welcome to coupon system");
		
		// set user type
		String userType = "company";
		
		while (!quit)
		{
			System.out.println("User type: " + userType);
			System.out.println("By type 'quit' you can quit from the system");
			System.out.print("User name: ");

			name = sc.nextLine();
			
			if (name.equals("quit"))
			{
				logout = true;
				quit = true;
			}
			else
			{
				System.out.print("Password: ");
				password = sc.nextLine();
				try
				{
					if (userType == "admin") 
					{
						 adminUser = (AdminFacade) mainSystem.login(name, password, userType);
						 if (adminUser == null)
						 {
							System.out.println("Login failed, user name / password is wrong");
							logout = true;
							break;
						 }
					}
					else if (userType == "company")
					{
						companyUser = (CompanyFacade) mainSystem.login(name, password, userType);
						if (companyUser == null)
						{
							System.out.println("Login failed, user name / password is wrong");
							logout = true;
							break;
						}
					}
					else if (userType == "customer")
					{
						customerUser = (CustomerFacade) mainSystem.login(name, password, userType);
						if (customerUser == null)
						{
							System.out.println("Login failed, user name / password is wrong");
							logout = true;
							break;
						}
					}
				}
				catch (FacadeException e)
				{

					System.out.println("Login failed, user name / password is wrong");
					logout = true;
					if (e.getDAOExceptionType() == DAOExceptionErrorType.CONNECTION_CLOSED)
					{
						System.out.println("Connection failed");
					}
					break;
				}
			}
			
			
		while (!logout)
		{
		try
		{
	
		switch (userType)
		{
			case "admin": 
			{  
												
				System.out.println("List of commands:");
				System.out.println("1: add new company");
				System.out.println("2: delete company");
				System.out.println("3: update company (without company name)");
				System.out.println("4: show all companies");
				System.out.println("5: show specific company details");
				System.out.println("6: add new customer");
				System.out.println("7: delete customer");
				System.out.println("8: update customer (without customer name)");
				System.out.println("9: show all customers");
				System.out.println("10: show specific customer details");
				System.out.println("11: logout");
				
				int choice = sc.nextInt();

				
				if (choice == 1)
				{
						try
						{
							System.out.print("Company name: ");
							String companyName =  sc.next();
							System.out.print("Company password: ");
							String companyPassword = sc.next();
							System.out.print("Company email: ");
							String companyEmail = sc.next();
							Company company = new Company(companyName, companyPassword, companyEmail);
							adminUser.createCompany(company);
						}
						catch (FacadeException eF1)
						{
							if (eF1.getDAOExceptionType() == DAOExceptionErrorType.NEW_COMPANY_FAILED)
							{
								System.out.println("Failed to create company " + eF1.getMessage());
							}
							else if (eF1.getDAOExceptionType() == DAOExceptionErrorType.COMPLETED)
							{
								System.out.println("Success to create new company but an error occured");
							}
							else
							{
								System.out.println(eF1.getMessage());
							}
						}
				}
				else if (choice == 2) 
					{
						try
						{
							long idChosen = showAllCompanies(adminUser, sc);
							adminUser.removeCompany(adminUser.getCompany(idChosen));		
						}
						catch (FacadeException eF2)
						{
							if (eF2.getDAOExceptionType() == DAOExceptionErrorType.REMOVE_COMPANY_FAILED)
							{
								System.out.println("Failed to delete company");
							}
						}
					}
				else if (choice == 3)
				{
						long idChosen = showAllCompanies(adminUser, sc);
						Company companyToEdit = adminUser.getCompany(idChosen);
						System.out.println("New password: ");
						String newPassword = sc.next();
						System.out.println("New email: ");
						String newEmail = sc.next();
						companyToEdit.setEmail(newEmail);
						companyToEdit.setPassword(newPassword);
						adminUser.updateCompany(companyToEdit);

				}
				else if (choice == 4)
				{
					System.out.println(adminUser.getAllCompanies());
				}
				else if (choice == 5)
				{
					long idChosen = showAllCompanies(adminUser, sc);
					System.out.println(adminUser.getCompany(idChosen));
				}
				else if (choice == 6)
				{
					System.out.print("Customer name: ");
					String customerName =  sc.next();
					System.out.print("Customer password: ");
					String customerPassword = sc.next();
					Customer customer = new Customer(customerName, customerPassword);
					System.out.println(customer);
					adminUser.createCustomer(customer);
				}
				else if (choice == 7)
				{
					long idChosen = showAllCustomers(adminUser, sc);
					adminUser.removeCustomer(adminUser.getCustomer(idChosen));
				}
				
				else if (choice == 8)
				{
					long idChosen = showAllCustomers(adminUser, sc);
					System.out.print("New password:");
					String newPassword = sc.next();
					Customer cust = new Customer();
					cust = adminUser.getCustomer(idChosen);
					cust.setPassword(newPassword);
					adminUser.updateCustomer(cust);
				}
				
				else if (choice == 9)
				{
					System.out.println(adminUser.getAllCustomer());
				}
				
				else if (choice == 10)
				{
					long idChosen = showAllCustomers(adminUser, sc);
					Customer cust = new Customer();
					cust = adminUser.getCustomer(idChosen);
					System.out.println(cust);
				}
				
				else if (choice == 11)
				{
					logout = true;
				}	
				break;
			}
			
			case "customer": 
			{ 

				System.out.println("1: Purchase coupon");
				System.out.println("2: Purchased coupons list");
				System.out.println("3: Purchased coupons list according to coupon type");
				System.out.println("4: Purchased coupons list according to max price");
				System.out.println("5: logout");
					
				int choice = sc.nextInt();
					
				if (choice == 1)
				{
						long idChosen = showAllCoupons(customerUser,sc);
						Coupon cop = customerUser.getCoupon(idChosen);
						customerUser.purchaseCoupon(cop);

				}
					
				else if (choice == 2)
					{
						for (Coupon c : customerUser.getAllPurchasedCoupons())
						{
							System.out.println(c);
						}
												
					}
					
				else if (choice == 3)
					{		
						for (Coupon c : customerUser.getAllPurchasedCouponsByType(couponFilterChooser(choice,sc)))
						{
							System.out.println(c);
						}
					}
					
				else if (choice == 4)
					{
						System.out.println("Max price:");
						double maxPrice = sc.nextDouble();
						
						for (Coupon c : customerUser.getAllPurchasedCouponsByPrice(maxPrice))
						{
							System.out.println(c);
						}
					}
					
				else if (choice == 5)
					{
						logout = true;
					}
					
				break;	
			}	
			
			case "company": 
			{  

					System.out.println("1: Adding new coupon");
					System.out.println("2: Delete coupon");
					System.out.println("3: Updating coupon price or expiry date");
					System.out.println("4: Show my company's details");
					System.out.println("5: Show all my company's coupons");
					System.out.println("6: Show  my company's coupons by type");
					System.out.println("7: Show  my company's coupons by price");
					System.out.println("8: Show  my company's coupons by expiry date");
					System.out.println("9: logout");
					
					int choice = sc.nextInt();
					
					if (choice == 1)
						{
							CreateNewCouponForCompany(companyUser, sc, choice);
							System.out.println("Coupon was purchased");
						}
						
					else if (choice == 2)
						{
							long idChosen = showAllCoupons(companyUser,sc);							
							companyUser.removeCoupon(companyUser.getCoupon(idChosen));
						}
						
					else if (choice == 3)
						{
							long idChosen = showAllCoupons(companyUser,sc);	
							Coupon coupon = companyUser.getCoupon(idChosen);
							System.out.print("New price:");
							double newPrice = sc.nextDouble();
							System.out.print("New expiry date (yyyy-MM-dd):");
							String newExpiryStr = sc.next();
							newExpiryStr = newExpiryStr + "T00:00";
							LocalDateTime newExpiry = LocalDateTime.parse(newExpiryStr);
							coupon.setPrice(newPrice);
							coupon.setEndDate(newExpiry);
							companyUser.updateCoupon(coupon);
						}
						
					else if (choice == 4)
					{
							System.out.println(companyUser.getCompanyDetails());
						}
						
					else if (choice == 5)
						{
							for (Coupon cop : companyUser.getAllCoupons())
							{
								System.out.println(cop);
							}
						}
						
					else if (choice == 6)
						{						
							System.out.println(companyUser.getCouponByType(couponFilterChooser(choice,sc)));
						}
						
					else if (choice == 7)
						{
							System.out.print("Insert minimal price:");
							double minPrice = sc.nextDouble();
							System.out.print("Insert maximal price:");
							double maxPrice = sc.nextDouble();
							System.out.println(companyUser.getCouponByPrice(minPrice, maxPrice));
						}
						
					else if (choice == 8)
						{
							System.out.print("Insert expiry date (yyyy-MM-dd):");
							String expiryDateStr = sc.next();
							expiryDateStr = expiryDateStr + "T00:00";
							LocalDateTime endDate = LocalDateTime.parse(expiryDateStr);
							System.out.println(companyUser.getCouponByEndDate(endDate));
						}
						
					else if (choice == 9)
						{
							logout = true;
							break;
						}
						
					break;
			}	
			default: 
				{
					System.out.println("Wrong user type, please type again");
					break;
				}	
		}	
		}

		catch (FacadeException e)
		{
			if (e.getDAOExceptionType() == DAOExceptionErrorType.CONNECTION_CLOSED)
			{
				System.out.println("Connection failed");
			}
				
			System.out.println(e.getDAOExceptionType());
		}
		catch (IllegalArgumentException eIllegal)
		{
			System.out.println(eIllegal.getMessage());
		}
		
		catch (NullPointerException eNull)
		{
			System.out.println(eNull.getMessage());
		}
		catch (InputMismatchException eMismatch)
		{
			System.out.println("You try to enter non valid value");
		}
		catch (DateTimeParseException eDateTime)
		{
			System.out.println("Date / Time format is not applicable");
		}

		
			System.out.println("You have logoff");
		}	
		logout = false;
	}
		sc.close();
		try 
		{
			mainSystem.shutdown();
		} 
		catch (InterruptedException e) 
		{
			System.out.println("Shutdown may not successfully executed");
		}
		System.out.println("You have quit from the system");
	}
	
	//
	// Routines for different choices
	//
	
	private static long showAllCompanies(AdminFacade adminUser, Scanner sc)
	{
		try
		{
		long idChosen = 0;
		while (idChosen == 0)
		{
			System.out.println("Enter company id (if you press 0 company list will be shown): ");
			idChosen = sc.nextLong();
			if (idChosen == 0)
			{
				System.out.println(adminUser.getAllCompanies());
			}
		}
		return idChosen;
		}
		catch (NullPointerException | FacadeException e)
		{
			System.out.println(e.getMessage());
			return 0;
		}
	}
	
	
	private static long showAllCustomers(AdminFacade adminUser, Scanner sc)
	{
		try
		{
		long idChosen = 0;
		while (idChosen == 0)
		{
			System.out.println("Enter customer id (if you press 0 customer list will be shown): ");
			idChosen = sc.nextLong();
			if (idChosen == 0)
			{
				System.out.println(adminUser.getAllCustomer());
			}
		}
		return idChosen;
		}
		catch (NullPointerException | FacadeException e)
		{
			System.out.println(e.getMessage());
			return 0;
		}
	}
	
	private static long showAllCoupons(CustomerFacade customerUser, Scanner sc)
	{
		try
		{
		long idChosen = 0;
		while (idChosen == 0)
		{
			System.out.println("Enter coupon id (if you press 0 coupon list will be shown): ");
			idChosen = sc.nextLong();
			if (idChosen == 0)
			{
				System.out.println(customerUser.getAllCoupons());
			}
		}
		return idChosen;
		}
		catch (NullPointerException | FacadeException e)
		{
			System.out.println(e.getMessage());
			return 0;
		}
	}
	
	private static long showAllCoupons(CompanyFacade companyUser, Scanner sc)
	{
		try
		{
		long idChosen = 0;
		while (idChosen == 0)
		{
			System.out.println("Enter coupon id (if you press 0 coupon list will be shown): ");
			idChosen = sc.nextLong();
			if (idChosen == 0)
			{
				System.out.println(companyUser.getAllCoupons());
			}
		}
		return idChosen;
		}
		catch (NullPointerException | FacadeException e)
		{
			System.out.println(e.getMessage());
			return 0;
		}
	}
	
	private static CouponType couponFilterChooser(int choice, Scanner sc)
	{
		CouponType type;
		
		System.out.println("Choose type of coupon:");
		System.out.println("1: RESTURANS");
		System.out.println("2: ELECTRICTY");
		System.out.println("3: FOOD");
		System.out.println("4: HEALTH");
		System.out.println("5: SPORTS");
		System.out.println("6: CAMPING");
		System.out.println("7: TRAVELLING");
		System.out.println("8: OTHER");
		
		choice = sc.nextInt();
		
		switch (choice)
		{
			case 1: { type = CouponType.RESTURANS; break;}
			case 2: { type = CouponType.ELECTRICITY; break;}
			case 3: { type = CouponType.FOOD; break;}
			case 4: { type = CouponType.HEALTH; break;}
			case 5: { type = CouponType.SPORTS; break;}
			case 6: { type = CouponType.CAMPING; break;}
			case 7: { type = CouponType.TRAVELLING; break;}
			case 8: { type = CouponType.OTHER; break;}
			default: { System.out.println("No such type, other will be chosen"); type = CouponType.OTHER; break;}
		}
		return type;
	}
	
	private static void CreateNewCouponForCompany(CompanyFacade companyUser, Scanner sc, int choice) throws FacadeException
	{
		System.out.print("Coupon title:");
		String newTitle = sc.next();
		System.out.print("Starting date and time of the coupon (yyyy-MM-dd):");
		String startDateStr = sc.next();
		startDateStr = startDateStr + "T00:00";
		LocalDateTime startDate = LocalDateTime.parse(startDateStr);
		System.out.print("Ending date and time of the coupon (yyyy-MM-dd):");
		String endDateStr = sc.next();
		endDateStr = endDateStr + "T00:00";
		LocalDateTime endDate = LocalDateTime.parse(endDateStr);
		System.out.print("Amount of coupon for marketing:");
		int newAmount = sc.nextInt();
		System.out.println("Type of coupon:");
		CouponType newType = couponFilterChooser(choice,sc);
		System.out.print("Description of the coupon:");
		String newMessage = sc.next();
		System.out.print("Price of the coupon per unit:");
		double newPrice = sc.nextDouble();
		System.out.print("Path for coupon image:");
		String newImage = sc.next();
		
		Coupon newCoupon = new Coupon();
		newCoupon.setTitle(newTitle);
		newCoupon.setStartDate(startDate);
		newCoupon.setEndDate(endDate);
		newCoupon.setAmount(newAmount);
		newCoupon.setType(newType);
		newCoupon.setMessage(newMessage);
		newCoupon.setPrice(newPrice);
		newCoupon.setImage(newImage);
		
		companyUser.createCoupon(newCoupon);
	}
}
