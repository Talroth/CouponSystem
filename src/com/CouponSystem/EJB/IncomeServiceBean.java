package com.CouponSystem.EJB;

import java.util.Collection;


import javax.ejb.Stateless;


import com.CouponSystem.Beans.Income;

/**
 * Session Bean implementation class IncomeServiceBean
 */
@Stateless

public class IncomeServiceBean implements IncomeService {


    public IncomeServiceBean() {
       
    	
    }

	@Override
	public void storeIncome(Income income) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Collection<Income> viewAllIncomes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Income> viewIncomeByCustomer(long customerId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Income> viewIncomeByCompany(long companyId) {
		// TODO Auto-generated method stub
		return null;
	}

}
