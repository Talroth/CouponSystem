package com.CouponSystem.EJB;

import java.util.Collection;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import com.CouponSystem.Beans.Income;
import com.CouponSystem.FacadeException.FacadeException;


@Stateless
public class IncomeServiceBean implements IncomeService {
		
	@PersistenceContext(unitName="jpa-example")

	private EntityManager em;
	
    public IncomeServiceBean() {
       
    	
    }

	@Override
	public void storeIncome(Income income) throws FacadeException
	{
		System.out.println("Store item in OncomeServiceBean");
		if (income == null)
			throw new FacadeException("No income");
		
		em.persist(income);
	}

	@Override
	public Collection<Income> viewAllIncomes() {		
		Collection<Income> ins =  em.createQuery("from Income i").getResultList();						
		return ins;
	}

	@Override
	public Collection<Income> viewIncomeByCustomer(long customerId) {		
		Collection<Income> ins =  em.createQuery("from Income i where i.custId = :customerId").setParameter("customerId", customerId).getResultList();						
		return ins;
	}

	@Override
	public Collection<Income> viewIncomeByCompany(long companyId) {
		Collection<Income> ins =  em.createQuery("from Income i where i.compId = :companyId").setParameter("companyId", companyId).getResultList();						
		return ins;
	}

}
