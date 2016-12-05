package com.CouponSystem.EJB;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.*;

import com.CouponSystem.Beans.Income;

/**
 * Message-Driven Bean implementation class for: IncomeConsumerBean
 */
//@MessageDriven(
//		activationConfig = { @ActivationConfigProperty(
//				propertyName = "destinationType", propertyValue = "javax.jms.Queue")
//		})


@MessageDriven(name = "HelloWorldQueueMDB", activationConfig = {
 @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
 @ActivationConfigProperty(propertyName = "destination", propertyValue = "queue/IncomeConsumerQueue"),
 @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge") })
 
//@ClusteredSingleton

public class IncomeConsumerBean implements MessageListener {

	// not sure here, it maybe should be in the IncomeServiceBean
	@EJB(lookup="java:module/IncomeServiceBean!com.CouponSystem.EJB.IncomeService")
	private IncomeServiceBean incomeProcess;
	
    /**
     * Default constructor. 
     */
    public IncomeConsumerBean() {
        // TODO Auto-generated constructor stub
    }
	
	/**
     * @see MessageListener#onMessage(Message)
     */
    public void onMessage(Message message) {
    	
    	System.out.println("on message");
    	// pass the income request to processing by EJB IncomeServiceBean
    	if (message instanceof Income)
    	{
    		incomeProcess.storeIncome((Income)message);
    	}
    	
    }

}
