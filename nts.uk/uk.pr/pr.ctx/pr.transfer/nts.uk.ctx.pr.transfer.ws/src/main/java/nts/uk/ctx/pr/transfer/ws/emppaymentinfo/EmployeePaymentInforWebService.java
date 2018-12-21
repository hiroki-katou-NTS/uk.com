package nts.uk.ctx.pr.transfer.ws.emppaymentinfo;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pr.transfer.app.command.emppaymentinfo.BankIntegrationCommandHandler;
import nts.uk.ctx.pr.transfer.app.command.emppaymentinfo.SourceBankIntegrationCommand;
import nts.uk.ctx.pr.transfer.app.command.emppaymentinfo.BankIntegrationCommand;
import nts.uk.ctx.pr.transfer.app.command.emppaymentinfo.SourceBankIntegrationCommandHandler;

/**
 * 
 * @author HungTT
 *
 */

@Path("ctx/pr/transfer/emppaymentinfo")
@Produces("application/json")
public class EmployeePaymentInforWebService extends WebService {

	@Inject
	private SourceBankIntegrationCommandHandler sourceBankIntegration;

	@Inject
	private BankIntegrationCommandHandler bankIntegration;

	@POST
	@Path("source-bank-integration")
	public void sourceBankIntegration(SourceBankIntegrationCommand command) {
		sourceBankIntegration.handle(command);
	}

	@POST
	@Path("bank-integration")
	public void bankIntegration(BankIntegrationCommand command) {
		bankIntegration.handle(command);
	}

}
