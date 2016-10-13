package com.CouponSystem.FacadeException;

import javax.xml.bind.annotation.XmlRootElement;

import DAOException.DAOException;
import DAOException.DAOExceptionErrorType;

@XmlRootElement
public class FacadeException extends Exception 
{
	public String message;
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
		this.message = message;
		daoException = null;
	}
	
	public FacadeException(DAOException DAOException, String message)
	{
		super(message);
		this.message = message;
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
		this.message = message;
		this.daoException = errorType;
	}
	
	public String getMessage()
	{
		return this.message;
	}
	
}
