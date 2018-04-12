package nts.uk.shr.sample.audittrail;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.shr.com.security.audittrail.jms.JmsAuditTrailProducer;

@Path("/sample/audit")
@Produces("application/json")
public class SampleAuditTrailWebService {

	@Inject
	private JmsAuditTrailProducer producer;
	
	@POST
	@Path("test")
	public void test() {
		this.producer.execute();
	}
}
