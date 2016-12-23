package com.CouponSystem.EJB;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.*;

import com.CouponSystem.Beans.Income;
import com.CouponSystem.FacadeException.FacadeException;

/**
 * Message-Driven Bean implementation class for: IncomeConsumerBean
 */
//@MessageDriven(
//		activationConfig = { @ActivationConfigProperty(
//				propertyName = "destinationType", propertyValue = "javax.jms.Queue")
//		})

//@ActivationConfigProperty(propertyName = "destination", propertyValue = "queue/IncomeConsumerQueue"),
//@ActivationConfigProperty(propertyName  = "connectionFactoryJndiName",propertyValue = "jms/RemoteConnectionFactory"),

@MessageDriven(mappedName = "Income", activationConfig = {
 @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
 @ActivationConfigProperty(propertyName = "destination", propertyValue = "queue/IncomeQueue"),
 @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge") })
 
public class IncomeConsumerBean implements MessageListener {

	
	
	// not sure here, it maybe should be in the IncomeServiceBean
	
//	@EJB(lookup="ejb:/CouponSystemWebTier//IncomeServiceBean!com.CouponSystem.EJB.IncomeService")
//	@EJB(lookup="java:module/IncomeServiceBean!com.CouponSystem.EJB.IncomeService")
//	@EJB(name="com.CouponSystem.EJB.IncomeService")
	@EJB(name="IncomeService")
	private IncomeService incomeProcess;
	
    /**
     * Default constructor. 
     */
    public IncomeConsumerBean() {
        // TODO Auto-generated constructor stub
    	System.out.println("MDB constructor");
    }
	
	/**
     * @see MessageListener#onMessage(Message)
     */
    public void onMessage(Message message) {
    	
    	System.out.println("onMesage = " + message.toString());
    	// pass the income request to processing by EJB IncomeServiceBean
    	if (message instanceof Income)
    	{
    		try 
    		{
				incomeProcess.storeIncome((Income)message);
			} catch (FacadeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	
    }

}
