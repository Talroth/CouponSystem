package com.CouponSystem.CouponSystem;

import java.sql.SQLException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.CouponSystem.CouponExpiryScanner.DailyCouponExpirationTask;
import com.CouponSystem.DBDAO.DBDAO;
import com.CouponSystem.Facade.AdminFacade;
import com.CouponSystem.Facade.CompanyFacade;
import com.CouponSystem.Facade.CouponClientFacade;
import com.CouponSystem.Facade.CustomerFacade;
import com.CouponSystem.FacadeException.FacadeException;

import DAOException.DAOException;
import DAOException.DAOExceptionErrorType;

public class CouponSystem 
{
	//
	// Attributes
	//
	
	private DBDAO dao;
	private DailyCouponExpirationTask task = new DailyCouponExpirationTask();
	private static CouponSystem couponSystem = new CouponSystem();
	private ScheduledExecutorService scheduledPool = Executors.newScheduledThreadPool(1);

	// Default value for the scanner
	
	private final long delay = 1;
	private final TimeUnit units = TimeUnit.DAYS;
	//
	// Constructor
	//
	
	private CouponSystem() 
	{
		// once the singleton initiated a connection pool is activated and the coupon scanner is started also
		
		try 
		{
			dao = DBDAO.getInstance();
			dao.CreatePool();
			
			// from the start the scanner will start to scan and scan every 120 seconds 
	        
			this.setScannerDelay(delay, units);
		} 
		catch (SQLException e) 
		{
			System.out.println("Fail to initiate the system, please refer to the admin");
		}
			
	}
	
	//
	// methods
	//
	
	public static CouponSystem getInstance()
	{
		// Singleton instance in order to use the class
		return couponSystem;
	}
	
	public void shutdown() throws InterruptedException
	{
		//
		// closing connection pool and stop the coupon scanner
		//
		
		try 
		{
			dao.CloseConnection();
		} 
		catch (DAOException e) 
		{
			System.out.println(e.getMessage());
		}
		finally
		{
			// Waiting until the scanner will end the current session prior closing it
			// if it taking too much time it will also be closed

			while (task.isInProgress())
			{
				Thread.sleep(10);
			}
			try
			{
				scheduledPool.shutdownNow();
			}
			catch (Exception ee)
			{
				System.out.println("Error in shutdown scanner");
			}
		}
	}
	

	// According to client type a login method from the facade is used and is been returned
	
	public CouponClientFacade login(String name, String password, String clientType) throws FacadeException
	{
		// depend on client type, a dedicated facade is returned after check login (user name and password is verified against the DB, except admin which is hard code)

			if (clientType == "admin")
			{
				return new AdminFacade().login(name, password);
			}
			else if (clientType == "customer")
			{
				return new CustomerFacade().login(name, password);
			}
			else if (clientType == "company")
			{
				return new CompanyFacade().login(name, password);
			}
			else
			{
				throw new FacadeException(DAOExceptionErrorType.WRONG_ARGUMENT, "Error in login, please contact with the admin");
			}

	}
	

	
	public void setScannerDelay(long delay, TimeUnit units) 
	{
		try
		{
		scheduledPool.scheduleWithFixedDelay(task, 0, delay, units);
		}
		catch (Exception e)
		{
			System.out.println("Error to start scanner, please refer to the system admin");
		}
	}

}

