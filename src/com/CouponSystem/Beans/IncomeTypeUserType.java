//package com.CouponSystem.Beans;
//
//import java.io.Serializable;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Types;
//
//import org.hibernate.HibernateException;
//import org.hibernate.engine.spi.SharedSessionContractImplementor;
//import org.hibernate.usertype.UserType;
//
//import com.mchange.v2.lang.ObjectUtils;
//
//
////
//// The class translate IncomeType to string in the DB with hibernate
////
//
//
//public class IncomeTypeUserType implements UserType {
//
//	// TODO: this is from the tutorial, replace it with something of mine
//	@Override
//	public Object assemble(Serializable cached, Object owner) throws HibernateException {
//		return deepCopy(cached);
//	}
//
//	// Create same object as new copy new.x = old.y
//	@Override
//	public Object deepCopy(Object arg0) throws HibernateException {
//		// TODO Auto-generated method stub
//		return null;
//		
////		 if(value==null)
////			   return null;
////			  else{
////			   Phone newObj=new Phone();
////			   Phone existObj=(Phone)value;
////			   
////			   newObj.setAreaCode(existObj.getAreaCode());
////			   newObj.setPhoneNum(existObj.getPhoneNum());
////			   
////			   return newObj;
////			  }
//		 
//	}
//
//	// TODO: this is from the tutorial, replace it with something of mine
//	@Override
//	public Serializable disassemble(Object value) throws HibernateException {
//		  Object  deepCopy=deepCopy(value);
//		  
//		  if(!(deepCopy instanceof Serializable))
//		   return (Serializable)deepCopy;
//		  
//		  return null;
//	}
//
//	// TODO: check what this thing does and it is ok to use this return instead the auto one
//	@Override
//	public boolean equals(Object x, Object y) throws HibernateException {
//		return ObjectUtils.eqOrBothNull(x, y);
//		
//	}
//
//	@Override
//	public int hashCode(Object x) throws HibernateException {
//		  if (x!=null)
//			   return x.hashCode();
//			  else
//			   return 0;
//	}
//
//	@Override
//	public boolean isMutable() {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	// TODO: to check if this does something and if not look for better tutorial
//	@Override
//	public Object nullSafeGet(ResultSet rs, String[] names, SharedSessionContractImplementor arg2, Object arg3)
//			throws HibernateException, SQLException {
//		  
//		  String nameVal = rs.getString(names[0]);		   
//		  
//		  return nameVal;
//	}
//
//	// TODO: to check if this does something and if not look for better tutorial
//	@Override
//	public void nullSafeSet(PreparedStatement st, Object value, int index, SharedSessionContractImplementor arg3)
//			throws HibernateException, SQLException {
//		
//		  if(value == null) 
//		  {
//			   st.setNull(index, Types.VARCHAR);
//		  }
//		  else
//		  {
//			   st.setString(index, ((IncomeType)value).toString());
//		  }
//		
//	}
//
//	// TODO: this is from the tutorial, replace it with something of mine
//	@Override
//	public Object replace(Object original, Object target, Object owner) throws HibernateException {
//		return deepCopy(original);
//	}
//
//
//	// Class  details of object which is going to be used
//	// TODO: the <> was not in the auto creator - check if works well
//	
//	@Override
//	public Class<IncomeType> returnedClass() {
//		return IncomeType.class;
//	}
//
//	// Type in the sql table which IncomeType will translate into
//	@Override
//	public int[] sqlTypes() {
//		return new int[]{Types.VARCHAR};
//	}
//
//}
