package nts.uk.ctx.at.record.ws.remaingnumber;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.record.app.command.remainingnumber.paymana.DeletePayoutManagementDataCommand;
import nts.uk.ctx.at.record.app.command.remainingnumber.paymana.DeleteSubstitutionOfHDManaDataCommandHandler;
import nts.uk.ctx.at.record.app.command.remainingnumber.paymana.UpdateSubstitutionOfHDManaDataCommand;
import nts.uk.ctx.at.record.app.command.remainingnumber.paymana.UpdateSubstitutionOfHDManaDataCommandHandler;

@Path("at/record/remainnumber/subsitution_hd")
@Produces("application/json")
public class SubstitutionOfHDManagementDataWebService extends WebService {

	@Inject
	private DeleteSubstitutionOfHDManaDataCommandHandler deleteSub;
	
	@Inject
	private UpdateSubstitutionOfHDManaDataCommandHandler updateSub;
	

	@POST
	@Path("update")
	public void update(UpdateSubstitutionOfHDManaDataCommand command){
		updateSub.handle(command);
	}
	
	@POST
	@Path("delete")
	public void delete(DeletePayoutManagementDataCommand command){
		deleteSub.handle(command);
	}
}
