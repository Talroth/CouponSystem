package com.CouponSystem.FacadeException;

import DAOException.DAOException;
import DAOException.DAOExceptionErrorType;

public class FacadeException extends Exception 
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DAOExceptionErrorType daoException;
	
	public FacadeException()
	{
		super();
		daoException = null;
	}
	
	public FacadeException(String message)
	{
		super(message);
		daoException = null;
	}
	
	public FacadeException(DAOException DAOException, String message)
	{
		super(message);
		daoException = DAOException.getReason();
	}
	
	public FacadeException(DAOException ex)
	{
		super(ex.getMessage());
		this.daoException = ex.getReason();
	}
	
	public DAOExceptionErrorType getDAOExceptionType()
	{
		return daoException;
	}
	
	public FacadeException(DAOExceptionErrorType errorType)
	{
		super();
		this.daoException = errorType;
	}
	
	public FacadeException(DAOExceptionErrorType errorType, String message)
	{
		super(message);
		this.daoException = errorType;
	}
	
}
