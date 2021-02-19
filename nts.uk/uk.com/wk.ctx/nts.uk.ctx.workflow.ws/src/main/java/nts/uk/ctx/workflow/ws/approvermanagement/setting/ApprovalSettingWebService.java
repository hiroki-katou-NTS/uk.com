package nts.uk.ctx.workflow.ws.approvermanagement.setting;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.ctx.workflow.app.command.approvermanagement.setting.ApprovalSettingCommand_Old;
import nts.uk.ctx.workflow.app.command.approvermanagement.setting.UpdateApprovalSettingCommandHandler;
import nts.uk.ctx.workflow.app.find.approvermanagement.setting.ApprovalSettingDto;
import nts.uk.ctx.workflow.app.find.approvermanagement.setting.ApprovalSettingFinder;

@Path("approval/setting")
@Produces("application/json")
public class ApprovalSettingWebService {
	@Inject
	private ApprovalSettingFinder appSetFind;
	@Inject
	private UpdateApprovalSettingCommandHandler update;
	
	@POST
	@Path("approval")
	public ApprovalSettingDto getApproval(){
		return this.appSetFind.findApproSet();
	}
	
	@POST
	@Path("update")
	public void update(ApprovalSettingCommand_Old command){
		this.update.handle(command);
	}
}
