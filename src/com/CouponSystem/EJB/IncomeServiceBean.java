package com.CouponSystem.EJB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;


import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.CouponSystem.Beans.Income;
import com.CouponSystem.Beans.IncomeType;


/**
 * Session Bean implementation class IncomeServiceBean
 */
@Stateless

public class IncomeServiceBean implements IncomeService {
	
	// Factory for hibernate - not working yet
	//private static SessionFactory factory = new Configuration().configure().buildSessionFactory();;
//	@PersistenceContext(unitName = "jpa-example")
//	Session session;
	
    public IncomeServiceBean() {
       
    	
    }

	@Override
	public void storeIncome(Income income) 
	{
		System.out.println("Store item in OncomeServiceBean");
		// hibernate session
//		Session session = factory.openSession();
//		session.save(income);
		

	}

	@Override
	public Collection<Income> viewAllIncomes() {
		
//		Collection<Income> list = session.createCriteria(Income.class).list();
		
		Income in = new Income(3,"la", LocalDateTime.now(), IncomeType.COMPANY_UPDATE_COUPON, 10D);
		Collection<Income> ins = new ArrayList<>();
		ins.add(in);
		return ins;
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
