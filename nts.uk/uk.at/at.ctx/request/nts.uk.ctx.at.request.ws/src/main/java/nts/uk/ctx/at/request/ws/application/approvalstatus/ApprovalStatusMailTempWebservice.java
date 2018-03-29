package nts.uk.ctx.at.request.ws.application.approvalstatus;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.request.app.command.application.approvalstatus.ApprovalStatusMailTempCommand;
import nts.uk.ctx.at.request.app.command.application.approvalstatus.RegisterApprovalStatusMailTempCommandHandler;
import nts.uk.ctx.at.request.app.find.application.approvalstatus.ApprovalStatusMailTempDto;
import nts.uk.ctx.at.request.app.find.application.approvalstatus.ApprovalStatusMailTempFinder;

@Path("at/request/application/approvalstatus")
@Produces("application/json")
public class ApprovalStatusMailTempWebservice extends WebService {
	@Inject
	private ApprovalStatusMailTempFinder approvalMailTempFinder;

	@Inject
	private RegisterApprovalStatusMailTempCommandHandler registerApprovalStatusMailTempCommandHandler;

	@POST
	@Path("getMail/{mailType}")
	public ApprovalStatusMailTempDto getMail(@PathParam("mailType") int mailType) {
		return approvalMailTempFinder.findByType(mailType);
	}
	
	@POST
	@Path("getMailBySetting")
	public List<ApprovalStatusMailTempDto> findBySetting() {
		return approvalMailTempFinder.findBySetting();
	}

	@POST
	@Path("registerMail")
	public void registerMail(List<ApprovalStatusMailTempCommand> command) {
		registerApprovalStatusMailTempCommandHandler.handle(command);
	}
}
