package com.CouponSystem.Facade;

import com.CouponSystem.FacadeException.FacadeException;

public interface CouponClientFacade 
{
	public CouponClientFacade login(String name, String password) throws FacadeException;
}
