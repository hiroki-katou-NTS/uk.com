package nts.uk.ctx.at.record.ws.remaingnumber;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.record.app.command.remainingnumber.checkfunc.CheckFuncDataCommand;
import nts.uk.ctx.at.record.app.command.remainingnumber.checkfunc.CheckFuncExecutionRespone;
import nts.uk.ctx.at.record.app.command.remainingnumber.checkfunc.SyncCheckFuncDataCommandHandler;

@Path("at/record/remainnumber/checkFunc")
@Produces("application/json")
public class CheckFunctionWebService extends WebService{

	@Inject
	private SyncCheckFuncDataCommandHandler checkFuncDataCommandHandler;
	
	@POST
	@Path("execution")
	public CheckFuncExecutionRespone csvImportProcess(CheckFuncDataCommand command) {
		CheckFuncExecutionRespone response = new CheckFuncExecutionRespone();
		response.taskInfor = checkFuncDataCommandHandler.handle(command);
		return response;
	}
}
