package nts.uk.ctx.hr.develop.ws.sysoperationset.businessrecognition;

import java.util.Optional;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.ctx.hr.develop.dom.sysoperationset.businessrecognition.HrApprovalRouteSetting;
import nts.uk.ctx.hr.develop.dom.sysoperationset.businessrecognition.HrApprovalRouteSettingRepository;
import nts.uk.shr.com.context.AppContexts;

@Path("hrdev/approvalSet")
@Produces("application/json")
public class HrApprovalRooteSetWs {

	@Inject
	private HrApprovalRouteSettingRepository repoHrAppRSet;
	
	//人事承認ルート設定を取得
	@POST
	@Path("appRootSet")
	public HrApprovalRootSetEx getEventMenu(){
		Optional<HrApprovalRouteSetting> set =  repoHrAppRSet.getDomainByCid(AppContexts.user().companyId());
		if(!set.isPresent()) return new HrApprovalRootSetEx(true, false, false);
		
		return new HrApprovalRootSetEx(set.get().isComMode(), set.get().isDevMode(), set.get().isEmpMode());
	}
	
	
}
