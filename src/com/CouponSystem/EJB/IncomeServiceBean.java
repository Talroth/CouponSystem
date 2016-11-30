package com.CouponSystem.EJB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collection;


import javax.ejb.Stateless;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.CouponSystem.Beans.Income;


/**
 * Session Bean implementation class IncomeServiceBean
 */
@Stateless

public class IncomeServiceBean implements IncomeService {
	
	// Factory for hibernate
	private static SessionFactory factory = new Configuration().configure().buildSessionFactory();;
	
    public IncomeServiceBean() {
       
    	
    }

	@Override
	public void storeIncome(Income income) {

		// hibernate session
		Session session = factory.openSession();
		session.save(income);
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
