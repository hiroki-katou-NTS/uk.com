package nts.uk.ctx.office.ws.goout;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.office.app.command.goout.GoOutEmployeeInformationCommand;
import nts.uk.ctx.office.app.command.goout.GoOutEmployeeInformationDelCommand;
import nts.uk.ctx.office.app.command.goout.GoOutEmployeeInformationDeleteCommandHandler;
import nts.uk.ctx.office.app.command.goout.GoOutEmployeeInformationInsertCommandHandler;
import nts.uk.ctx.office.app.command.goout.GoOutEmployeeInformationUpdateCommandHandler;

@Path("ctx/office/goout/employee/information")
@Produces("application/json")
public class GoOutEmployeeInformationWs extends WebService {

	@Inject
	private GoOutEmployeeInformationDeleteCommandHandler gouOutDeleteHandler;
	
	@Inject
	private GoOutEmployeeInformationUpdateCommandHandler gouOutUpdateHandler;
	
	@Inject
	private GoOutEmployeeInformationInsertCommandHandler goOutInsertHandler;

	@POST
	@Path("delete")
	public void searchUser(GoOutEmployeeInformationDelCommand command) {
		gouOutDeleteHandler.handle(command);
	}
	
	@POST
	@Path("save")
	public void searchUser(GoOutEmployeeInformationCommand command) {
		goOutInsertHandler.handle(command);
	}
}
