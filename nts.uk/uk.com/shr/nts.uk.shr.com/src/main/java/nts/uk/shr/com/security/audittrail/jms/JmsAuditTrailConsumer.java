package nts.uk.shr.com.security.audittrail.jms;

import javax.ejb.MessageDriven;
import javax.enterprise.inject.spi.CDI;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

import lombok.val;
import nts.arc.diagnose.PrintStackTrace;
import nts.uk.shr.com.security.audittrail.AuditTrailTransaction;

import javax.ejb.ActivationConfigProperty;

//@MessageDriven(name = "JmsAuditTrailConsumer", activationConfig = {
//        @ActivationConfigProperty(propertyName = "destination", propertyValue = "java:jboss/jms/queue/UKAuditTrail"),
//        @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge"),
//        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
//})
public class JmsAuditTrailConsumer implements MessageListener {

	@Override
	public void onMessage(Message message) {
//		try {
//		val restoredMessage = JmsAuditTrailMessage.restore((MapMessage) message);
//		
//		val auditTrailTransaction = CDI.current().select(AuditTrailTransaction.class).get();
//		auditTrailTransaction.begin(
//				restoredMessage.getOperationId(),
//				restoredMessage.getProcessorId(),
//				restoredMessage.getParameter());
//		} catch (Exception ex) {
//			PrintStackTrace.toLog(ex);
//		}
	}

}
