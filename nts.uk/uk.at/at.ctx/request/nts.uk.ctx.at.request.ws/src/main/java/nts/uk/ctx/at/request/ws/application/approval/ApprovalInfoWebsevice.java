package nts.uk.ctx.at.request.ws.application.approval;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.ctx.at.request.dom.application.approval.ApprovalInfoInterface;
import nts.uk.shr.com.context.AppContexts;

@Path("at/request/application/approval")
@Produces("application/json")
public class ApprovalInfoWebsevice {

	@Inject
	private ApprovalInfoInterface apprInfo;
	
	@POST
	@Path("judgmentuser")
	public int judgmentUser(String appId){
		return apprInfo.judgmentUser(AppContexts.user().companyId(), appId, AppContexts.user().employeeId()).value;
	}
}
