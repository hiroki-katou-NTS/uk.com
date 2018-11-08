package nts.uk.ctx.pr.transfer.ws.emppaymentinfo;

import javax.inject.Inject;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
/**
 * 
 * @author HungTT
 *
 */
import nts.uk.ctx.pr.transfer.app.command.emppaymentinfo.IntegrationProcessCommandHandler;

@Path("ctx/pr/transfer/emppaymentinfo")
@Produces("application/json")
public class EmployeePaymentInforWebService extends WebService {

	@Inject
	private IntegrationProcessCommandHandler integrationHandler;
	
}
