package com.CouponSystem.EJB;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.*;

import org.jboss.ejb3.annotation.DeliveryActive;
import org.jboss.logging.Logger;

import com.CouponSystem.Beans.Income;

/**
 * Message-Driven Bean implementation class for: IncomeConsumerBean
 */
//@MessageDriven(
//		activationConfig = { @ActivationConfigProperty(
//				propertyName = "destinationType", propertyValue = "javax.jms.Queue")
//		})


@MessageDriven(name = "IncomeConsumerBean", activationConfig = {
 @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
// @ActivationConfigProperty(propertyName = "destination", propertyValue = "queue/IncomeConsumerQueue"),
 @ActivationConfigProperty(propertyName = "destination", propertyValue = "queue/TestQ"),
 @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge") })
 
@DeliveryActive(true)

//@ClusteredSingleton

public class IncomeConsumerBean implements MessageListener {

	private final static Logger LOGGER = Logger.getLogger(IncomeConsumerBean.class.toString());
	
	// not sure here, it maybe should be in the IncomeServiceBean
	@EJB(lookup="java:module/IncomeServiceBean!com.CouponSystem.EJB.IncomeService")
	private IncomeServiceBean incomeProcess;
	
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
    	
    	LOGGER.info("Received Message from queue");
    	System.out.println("onMesage ====================================================");
    	// pass the income request to processing by EJB IncomeServiceBean
    	if (message instanceof Income)
    	{
    		incomeProcess.storeIncome((Income)message);
    	}
    	
    }

}
