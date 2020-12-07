package nts.uk.ctx.at.request.ws.application.overtime;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.request.app.find.application.overtime.AppOvertimeFinder;
import nts.uk.ctx.at.request.app.find.application.overtime.DisplayInfoOverTimeDto;
import nts.uk.ctx.at.request.app.find.application.overtime.DisplayInfoOverTimeMobileDto;
import nts.uk.ctx.at.request.app.find.application.overtime.ParamChangeDateMobile;
import nts.uk.ctx.at.request.app.find.application.overtime.ParamStartMobile;

@Path("at/request/application/overtime/mobile")
@Produces("application/json")
public class OvertimeMobileWebService extends WebService {
	
	@Inject
	private AppOvertimeFinder appOvertimeFinder;
	
	@POST
	@Path("start")
	public DisplayInfoOverTimeMobileDto start(ParamStartMobile param) {
		return appOvertimeFinder.startMobile(param);
	}
	
	@POST
	@Path("changeDate")
	public DisplayInfoOverTimeDto changeDate(ParamChangeDateMobile param) {
		return appOvertimeFinder.changeDateMobile(param);
	}
	
	
}
