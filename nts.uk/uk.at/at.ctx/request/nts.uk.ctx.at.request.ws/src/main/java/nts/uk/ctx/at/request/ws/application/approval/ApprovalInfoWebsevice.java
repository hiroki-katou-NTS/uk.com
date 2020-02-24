package nts.uk.ctx.at.request.ws.application.approval;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.ctx.at.request.app.find.setting.request.application.AppUseAtrDto;
import nts.uk.ctx.at.request.app.find.setting.request.application.ApplicationUseAtrFinder;
import nts.uk.ctx.at.request.app.find.setting.request.application.ApproverRegisterSetDto;
import nts.uk.ctx.at.request.dom.application.approval.ApprovalInfoInterface;
import nts.uk.shr.com.context.AppContexts;

@Path("at/request/application/approval")
@Produces("application/json")
public class ApprovalInfoWebsevice {

	@Inject
	private ApprovalInfoInterface apprInfo;
	@Inject
	private ApplicationUseAtrFinder appUseAtrFinder;
	
	@POST
	@Path("judgmentuser")
	public int judgmentUser(String appId){
		return apprInfo.judgmentUser(AppContexts.user().companyId(), appId, AppContexts.user().employeeId()).value;
	}
	//HOATT - CMM018_2
	@POST
	@Path("app-useAtr")
	public List<AppUseAtrDto> appUseAtr(AppUserAtrParam param){
		return appUseAtrFinder.getAppUseAtr(param.getTab(), param.getWorkplaceID(), param.getEmployeeId());
	}
	@POST
	@Path("appSet")
	public ApproverRegisterSetDto getAppSet(){
		return appUseAtrFinder.getAppSet(AppContexts.user().companyId());
	}
}
