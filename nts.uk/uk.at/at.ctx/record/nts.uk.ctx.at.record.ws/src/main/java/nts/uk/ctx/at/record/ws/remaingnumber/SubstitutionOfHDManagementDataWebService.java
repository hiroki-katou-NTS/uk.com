package nts.uk.ctx.at.record.ws.remaingnumber;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.ctx.at.record.app.command.remainingnumber.paymana1.DeletePayoutManagementDataCommand;
import nts.uk.ctx.at.record.app.command.remainingnumber.paymana1.DeleteSubstitutionOfHDManaDataCommandHandler;
import nts.uk.ctx.at.record.app.command.remainingnumber.paymana1.UpdateSubstitutionOfHDManaDataCommand;
import nts.uk.ctx.at.record.app.command.remainingnumber.paymana1.UpdateSubstitutionOfHDManaDataCommandHandler;

@Path("at/record/remainnumber/subsitution_hd")
@Produces("application/json")
public class SubstitutionOfHDManagementDataWebService {

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
