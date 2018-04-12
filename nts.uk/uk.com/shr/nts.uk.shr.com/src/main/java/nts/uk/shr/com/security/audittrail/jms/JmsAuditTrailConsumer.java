package nts.uk.shr.com.security.audittrail.jms;

import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

import lombok.val;

import javax.ejb.ActivationConfigProperty;

@MessageDriven(name = "JmsAuditTrailConsumer", activationConfig = {
        @ActivationConfigProperty(propertyName = "destination", propertyValue = "java:jboss/jms/queue/HealthCareAuditTrail"),
        @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge"),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
})
public class JmsAuditTrailConsumer implements MessageListener {

	@Override
	public void onMessage(Message message) {
		val mapMessage = (MapMessage) message;
		
		try {
			String k = mapMessage.getString("k");
			k.toString();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
