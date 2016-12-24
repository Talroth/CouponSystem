package com.CouponSystem.EJB;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.*;

import com.CouponSystem.Beans.Income;
import com.CouponSystem.FacadeException.FacadeException;


@MessageDriven(mappedName = "Income", activationConfig = {
 @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
 @ActivationConfigProperty(propertyName = "destination", propertyValue = "queue/IncomeQueue"),
 @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge") })
 
public class IncomeConsumerBean implements MessageListener {

	@EJB(name="IncomeService")
	private IncomeService incomeProcess;
	

    public IncomeConsumerBean() {

    }

    public void onMessage(Message message) {
    	   	
    	// pass the income request to processing by EJB IncomeServiceBean
    	ObjectMessage objMsg = (ObjectMessage) message;

    		try 
    		{
    			Income recvMsg = (Income) objMsg.getObject();
				incomeProcess.storeIncome(recvMsg);
			} 
    		catch (FacadeException | JMSException e) 
    		{
    			// no client interaction (MDB)
			}
    	
    }

}
