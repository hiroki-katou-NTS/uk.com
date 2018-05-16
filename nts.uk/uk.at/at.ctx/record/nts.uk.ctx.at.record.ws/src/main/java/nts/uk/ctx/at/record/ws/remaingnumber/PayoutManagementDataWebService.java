package nts.uk.ctx.at.record.ws.remaingnumber;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import nts.uk.ctx.at.record.app.command.remainingnumber.paymana1.DeletePayoutManagementDataCommand;
import nts.uk.ctx.at.record.app.command.remainingnumber.paymana1.DeletePayoutManagementDataCommandHandler;
import nts.uk.ctx.at.record.app.command.remainingnumber.paymana1.PayoutManagementDataCommand;
import nts.uk.ctx.at.record.app.command.remainingnumber.paymana1.UpdatePayoutManagementDataCommandHandler;


@Path("at/record/remainnumber/payoutmanagement")
@Produces("application/json")
public class PayoutManagementDataWebService {
   
	@Inject
    private DeletePayoutManagementDataCommandHandler deletePayout;
	
	@Inject
    private UpdatePayoutManagementDataCommandHandler updatePayout;

	
	@POST
	@Path("update")
	public void update(PayoutManagementDataCommand command){
		updatePayout.handle(command);
	}
	
	@POST
	@Path("delete")
	public void delete(DeletePayoutManagementDataCommand command){
		deletePayout.handle(command);
	}
	
}
