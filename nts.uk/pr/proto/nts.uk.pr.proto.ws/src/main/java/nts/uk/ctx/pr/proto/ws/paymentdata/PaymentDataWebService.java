package nts.uk.ctx.pr.proto.ws.paymentdata;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pr.proto.app.paymentdata.command.CreatePaymentDataCommand;
import nts.uk.ctx.pr.proto.app.paymentdata.command.CreatePaymentDataCommandHandler;

@Path("/ctx/pr/proto/paymentdata")
@Produces("application/json")
public class PaymentDataWebService extends WebService {
	@Inject
	private CreatePaymentDataCommandHandler importData;
	
	@POST
	@Path("add")
	public void add(CreatePaymentDataCommand command) {
		this.importData.handle(command);
	}
}
