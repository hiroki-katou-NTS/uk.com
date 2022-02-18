package nts.uk.ctx.workflow.ws.approvermanagement.workroot;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.workflow.app.command.approvermanagement.workroot.CopyApproversCommand;
import nts.uk.ctx.workflow.app.command.approvermanagement.workroot.CopyApproversCommandHandler;
import nts.uk.ctx.workflow.app.command.approvermanagement.workroot.DeleteLastHistoryCommand;
import nts.uk.ctx.workflow.app.command.approvermanagement.workroot.DeleteLastHistoryCommandHandler;
import nts.uk.ctx.workflow.app.command.approvermanagement.workroot.RegisterSelfApproverCommand;
import nts.uk.ctx.workflow.app.command.approvermanagement.workroot.RegisterSelfApproverCommandHandler;
import nts.uk.ctx.workflow.app.command.approvermanagement.workroot.UpdateSelfApproverCommand;
import nts.uk.ctx.workflow.app.command.approvermanagement.workroot.UpdateSelfApproverCommandHandler;

@Path("workflow/approvermanagement/workroot/selfapproversetting")
@Produces("application/json")
public class SelfApproverSettingWebService extends WebService {

	@Inject
	private RegisterSelfApproverCommandHandler registerSelfApproverCommandHandler;
	
	@Inject
	private UpdateSelfApproverCommandHandler updateSelfApproverCommandHandler;
	
	@Inject
	private CopyApproversCommandHandler copyApproversCommandHandler;
	
	@Inject
	private DeleteLastHistoryCommandHandler deleteLastHistoryCommandHandler;
	
	@POST
	@Path("register")
	public void register(RegisterSelfApproverCommand command) {
		this.registerSelfApproverCommandHandler.handle(command);
	}
	
	@POST
	@Path("update")
	public void update(UpdateSelfApproverCommand command) {
		this.updateSelfApproverCommandHandler.handle(command);
	}
	
	@POST
	@Path("copy")
	public void copy(CopyApproversCommand command) {
		this.copyApproversCommandHandler.handle(command);
	}
	
	@POST
	@Path("deleteLastHist")
	public void deleteLastHist(DeleteLastHistoryCommand command) {
		this.deleteLastHistoryCommandHandler.handle(command);
	}
}
