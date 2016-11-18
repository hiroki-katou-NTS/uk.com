package nts.uk.ctx.pr.proto.ws.paymentdata;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pr.proto.app.paymentdata.command.CreatePaymentDataCommand;
import nts.uk.ctx.pr.proto.app.paymentdata.command.CreatePaymentDataCommandHandler;
import nts.uk.ctx.pr.proto.app.paymentdata.command.InsertPaymentDataCommand;
import nts.uk.ctx.pr.proto.app.paymentdata.command.InsertPaymentDataCommandHandler;
import nts.uk.ctx.pr.proto.app.paymentdata.command.UpdatePaymentDataCommand;
import nts.uk.ctx.pr.proto.app.paymentdata.command.UpdatePaymentDataCommandHandler;

@Path("/ctx/pr/proto/paymentdata")
@Produces("application/json")
public class PaymentDataWebService extends WebService {
	@Inject
	private CreatePaymentDataCommandHandler importData;

	@Inject
	private InsertPaymentDataCommandHandler insertData;

	@Inject
	private UpdatePaymentDataCommandHandler updateData;

	@POST
	@Path("add")
	public void add(CreatePaymentDataCommand command) {
		this.importData.handle(command);
	}

	@POST
	@Path("insert")
	public void add(InsertPaymentDataCommand command) {
		this.insertData.handle(command);
	}

	@POST
	@Path("update")
	public void add(UpdatePaymentDataCommand command) {
		this.updateData.handle(command);
	}

}
