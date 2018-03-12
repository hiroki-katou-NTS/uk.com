package nts.uk.ctx.at.request.ws.application.hdapplicationsetting;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.request.app.find.setting.applicationapprovalsetting.hdapplicationsetting.TimeHdAppSetDto;
import nts.uk.ctx.at.request.app.find.setting.applicationapprovalsetting.hdapplicationsetting.TimeHdAppSetFinder;

@Path("at/hdapplication/setting")
@Produces("application/json")
public class TimeHdAppSetWebservice extends WebService {
	@Inject
	private TimeHdAppSetFinder timeFinder;
	
	@POST
	@Path("getbycid")
	public TimeHdAppSetDto getByCid() {
		return this.timeFinder.findByCid();
	}
}
