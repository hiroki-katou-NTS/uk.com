package nts.uk.shr.com.security.audittrail.jms;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;

import lombok.val;

@ApplicationScoped
public class JmsAuditTrailProducer {

    @Resource(lookup = "java:/ConnectionFactory")
    private static ConnectionFactory connectionFactory;
    
    @Resource(lookup = "java:jboss/jms/queue/UKAuditTrail")
    private static Queue queue;

    private Connection connection = null;

    private Session session = null;

    private MessageProducer producer = null;
    

    @PostConstruct
    private void initialize() {
        try {
            this.connection = connectionFactory.createConnection();
            this.session = this.connection.createSession();
            this.producer = this.session.createProducer(queue);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
    
    public void execute() {
    	
        try {
        	val message = this.session.createMapMessage();
        	
        	message.setString("k", "hoge");
        	
            this.producer.send(message);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
