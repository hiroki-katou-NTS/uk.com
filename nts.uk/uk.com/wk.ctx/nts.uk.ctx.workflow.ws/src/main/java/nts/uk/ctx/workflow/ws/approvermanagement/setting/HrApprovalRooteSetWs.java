package nts.uk.ctx.workflow.ws.approvermanagement.setting;

import java.util.Optional;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.ctx.workflow.app.find.approvermanagement.setting.HrApprovalRootSetEx;
import nts.uk.ctx.workflow.dom.approvermanagement.setting.HrApprovalRouteSettingWF;
import nts.uk.ctx.workflow.dom.approvermanagement.setting.HrApprovalRouteSettingWFRepository;
import nts.uk.shr.com.context.AppContexts;

@Path("hrdev/approvalSet")
@Produces("application/json")
public class HrApprovalRooteSetWs {

	@Inject
	private HrApprovalRouteSettingWFRepository repoHrAppRSet;
	
	//人事承認ルート設定を取得
	@POST
	@Path("appRootSet")
	public HrApprovalRootSetEx getEventMenu(){
		Optional<HrApprovalRouteSettingWF> set =  repoHrAppRSet.getDomainByCid(AppContexts.user().companyId());
		if(!set.isPresent()) return new HrApprovalRootSetEx(false, false, false);
		
		return new HrApprovalRootSetEx(set.get().isComMode(), set.get().isDevMode(), set.get().isEmpMode());
	}
	
	
}
