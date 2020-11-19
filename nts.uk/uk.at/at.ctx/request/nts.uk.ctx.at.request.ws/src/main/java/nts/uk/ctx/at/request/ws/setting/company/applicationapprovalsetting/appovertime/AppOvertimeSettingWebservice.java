package nts.uk.ctx.at.request.ws.setting.company.applicationapprovalsetting.appovertime;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.request.app.command.setting.company.applicationapprovalsetting.appovertime.OvertimeQuotaSetCommandHandler;
import nts.uk.ctx.at.request.app.command.setting.company.applicationapprovalsetting.appovertime.OvertimeQuotaSetUseCommand;
import nts.uk.ctx.at.request.app.find.setting.applicationapprovalsetting.appovertime.AppOvertimeSettingDto;
import nts.uk.ctx.at.request.app.find.setting.applicationapprovalsetting.appovertime.AppOvertimeSettingFinder;
import nts.uk.ctx.at.request.app.find.setting.company.applicationapprovalsetting.appovertime.OvertimeQuotaSetFinder;
import nts.uk.ctx.at.request.app.find.setting.company.applicationapprovalsetting.appovertime.OvertimeQuotaSetUseDto;

import java.util.List;
import java.util.Map;

@Path("at/request/setting/company/applicationapproval/appovertime")
@Produces("application/json")
public class AppOvertimeSettingWebservice extends WebService{
	@Inject
	private AppOvertimeSettingFinder overTimeFinder;

	@Inject
	private OvertimeQuotaSetCommandHandler overtimeQuotaSetCommandHandler;

	@Inject
	private OvertimeQuotaSetFinder overtimeQuotaSetFinder;

	@POST
	@Path("ot")
	public AppOvertimeSettingDto getOvertime(){
		 return overTimeFinder.findByCom();
	}

	@POST
	@Path("registerOTQuota")
	public void registerOvertimeQuota(List<OvertimeQuotaSetUseCommand> data) {
		overtimeQuotaSetCommandHandler.handle(data);
	}

	@POST
	@Path("getOTQuota")
	public List<OvertimeQuotaSetUseDto> getOvertimeQuota() {
		return overtimeQuotaSetFinder.getOvertimeQuotaSettings();
	}

	@POST
	@Path("getOTQuotaByAtr")
	public List<OvertimeQuotaSetUseDto> getOvertimeQuotaByAtr(Map<String, Integer> params) {
		Integer overtimeAtr = params.get("overtimeAtr");
		Integer flexWorkAtr = params.get("flexWorkAtr");
		return overtimeQuotaSetFinder.getOvertimeQuotaSettings(overtimeAtr, flexWorkAtr);
	}

}
