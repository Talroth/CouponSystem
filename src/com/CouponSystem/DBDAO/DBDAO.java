package com.CouponSystem.DBDAO;

import java.sql.*;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import DAOException.DAOException;


//
// Singleton class to communicate with the DB with connection pool
//

public class DBDAO 
{
	
	//private static Connection con;
	private static ComboPooledDataSource  ds;
	private static DBDAO dbDao = new DBDAO();
	
	
	private DBDAO() 
	{

	}
	
	public void CreatePool() throws SQLException
	{
		// loading the jdbc driver for MySql DB
		try 
		{
			Class.forName("com.mysql.jdbc.Driver");
		} 
		catch (ClassNotFoundException e) 
		{
			System.out.println("MySql driver not found");
		}
		
		System.out.println("----------- DRIVER LOADED -----------------");
		
	
		// URL connection string to the DB
		String url = "jdbc:mysql://localhost:3306/couponsystem";
		
		try
		{
			// config data source object (C3P0 connection pool)
			
			ds = new ComboPooledDataSource ();
			
			// set the URL string
			ds.setJdbcUrl(url);
			
			// set user name of the DB
			ds.setUser("root");       
			
			// set password of the DB
			ds.setPassword("root"); 
			
			// Max number of prepared statement
			ds.setMaxStatements(180);
			
			// Max number of prepared statement per connection
			ds.setMaxStatementsPerConnection(20);
			
			// Number of attempts to connect to the DB
			ds.setAcquireRetryAttempts(1);
			
			// Close pool once connection failed
			ds.setBreakAfterAcquireFailure(true);
						
			//
			// overload for client but more reliable form in connection terms
			//
			
			ds.setTestConnectionOnCheckout(true);
			
			//
			// Those options are for better client performance but overload on server
			//
			
//			ds.setTestConnectionOnCheckin(true);
			
//			ds.setIdleConnectionTestPeriod(30); 
					
			System.out.println("------------ Connection pool established ----------");
			
		}
		
		catch (Exception e)
		{
			System.out.println("Failed to connect");
			throw new SQLException("Failed to connect");
		}
	}
	
	public Connection OpenConnection() throws SQLException
	{
		
		try
		{
			// use one of the opened connection from the pool
			Connection conn = ds.getConnection();
			if (conn != null)
			{
				System.out.println("------------ Connection established ----------");
				return conn;
			}
			
			else
			{
				System.out.println("------------ Connection was not established ----------");
				throw new SQLException("Connection was not established");
			}
		}
		catch (SQLException e)
		{
			System.out.println("------------ Connection was not established ----------");
			throw new SQLException("Connection was not established");
		}

	}
	
	public static DBDAO getInstance()
	{
		return dbDao;
	}
	
	// close the whole connection pool
	public void CloseConnection() throws DAOException
	{
		if (ds == null) { throw new DAOException("Connection is not connected"); }
			
			ds.close();
			System.out.println("------------ Connection pool closed ----------");	

	}
	

}
