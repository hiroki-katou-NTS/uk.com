package nts.uk.ctx.office.ws.goout;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.office.app.command.goout.GoOutEmployeeInformationCommand;
import nts.uk.ctx.office.app.command.goout.GoOutEmployeeInformationDelCommand;
import nts.uk.ctx.office.app.command.goout.GoOutEmployeeInformationDeleteCommandHandler;
import nts.uk.ctx.office.app.command.goout.GoOutEmployeeInformationCommandHandler;

@Path("ctx/office/goout/employee/information")
@Produces("application/json")
public class GoOutEmployeeInformationWs extends WebService {

	@Inject
	private GoOutEmployeeInformationDeleteCommandHandler gouOutDeleteHandler;
	
	@Inject
	private GoOutEmployeeInformationCommandHandler goOutHandler;

	@POST
	@Path("delete")
	public void deleteGoOutEmployeeInformation(GoOutEmployeeInformationDelCommand command) {
		gouOutDeleteHandler.handle(command);
	}
	
	@POST
	@Path("save")
	public void saveOrUpdateGoOutEmployeeInformation(GoOutEmployeeInformationCommand command) {
		goOutHandler.handle(command);
	}
}
