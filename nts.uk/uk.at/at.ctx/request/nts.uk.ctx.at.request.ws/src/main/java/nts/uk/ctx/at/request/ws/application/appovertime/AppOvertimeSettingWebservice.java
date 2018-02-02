package nts.uk.ctx.at.request.ws.application.appovertime;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.request.app.find.setting.applicationapprovalsetting.appovertime.AppOvertimeSettingDto;
import nts.uk.ctx.at.request.app.find.setting.applicationapprovalsetting.appovertime.AppOvertimeSettingFinder;

@Path("at/request/application/overtime")
@Produces("application/json")
public class AppOvertimeSettingWebservice extends WebService{
	@Inject
	private AppOvertimeSettingFinder overTimeFinder;
	
	@POST
	@Path("ot")
	public AppOvertimeSettingDto getOvertime(){
		 return overTimeFinder.findByCom();
	}
}
