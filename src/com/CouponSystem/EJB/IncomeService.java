package com.CouponSystem.EJB;

import java.util.Collection;

import javax.ejb.Remote;

import com.CouponSystem.Beans.Income;
import com.CouponSystem.FacadeException.FacadeException;

@Remote
public interface IncomeService {

	public void storeIncome(Income income) throws FacadeException;
	public Collection<Income> viewAllIncomes();
	public Collection<Income> viewIncomeByCustomer(long customerId);
	public Collection<Income> viewIncomeByCompany(long companyId);
	
}
