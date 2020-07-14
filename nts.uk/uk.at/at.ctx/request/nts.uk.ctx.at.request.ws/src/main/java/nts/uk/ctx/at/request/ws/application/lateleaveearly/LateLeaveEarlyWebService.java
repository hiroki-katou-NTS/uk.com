package nts.uk.ctx.at.request.ws.application.lateleaveearly;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.request.app.find.application.lateleaveearly.LateLeaveEarlyFinder;
import nts.uk.ctx.at.request.app.find.application.lateorleaveearly.ArrivedLateLeaveEarlyInfoDto;

@Path("at/request/application/lateorleaveearly")
@Produces("application/json")
public class LateLeaveEarlyWebService extends WebService {
	
	private LateLeaveEarlyFinder finder;
	
	@POST
	@Path("initPage")
	public ArrivedLateLeaveEarlyInfoDto initPage(String appId) {
		return this.finder.getLateLeaveEarly(appId);
	}
}
