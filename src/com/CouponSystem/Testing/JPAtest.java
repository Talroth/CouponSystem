//package com.CouponSystem.Testing;
//
//import java.time.LocalDateTime;
//
//import javax.persistence.EntityManager;
//import javax.persistence.EntityManagerFactory;
//import javax.persistence.Persistence;
//
//import com.CouponSystem.Beans.Income;
//import com.CouponSystem.Beans.IncomeType;
//
//// working locally with persitence.xml for local setup
//
//public class JPAtest {
//public static void main(String[] args) {
//	
//	
//    EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa-example");
//    EntityManager em = emf.createEntityManager();
//    
//    Income income = new Income(3,"t", LocalDateTime.now(), IncomeType.COMPANY_NEW_COUPON,10D);
//    em.getTransaction().begin();
//
//    em.persist(income);
//    em.getTransaction().commit();
//
//	
//}
//}
