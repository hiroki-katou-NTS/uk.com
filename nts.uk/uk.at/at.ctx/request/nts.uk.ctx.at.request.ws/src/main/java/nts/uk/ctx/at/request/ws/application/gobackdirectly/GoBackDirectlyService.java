package nts.uk.ctx.at.request.ws.application.gobackdirectly;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.request.app.find.application.gobackdirectly.GoBackDirectSettingDto;
import nts.uk.ctx.at.request.app.find.application.gobackdirectly.GoBackDirectlyDto;
import nts.uk.ctx.at.request.app.find.application.gobackdirectly.GoBackDirectlyFinder;
import nts.uk.shr.com.context.AppContexts;

@Path("at/request/application/gobackdirectly")
@Produces("application/json")
public class GoBackDirectlyService extends WebService {
	@Inject
	private GoBackDirectlyFinder goBackDirectlyFinder;

	@POST
	@Path("getGoBackDirectlyByAppID")
	public GoBackDirectlyDto getGoBackDirectlyByAppID() {
		String appID = "1445DA47-3CF9-4B0A-B819-96D20721881C";
		return this.goBackDirectlyFinder.getGoBackDirectlyByAppID(appID);
	}

	@POST
	@Path("getGoBackCommonSetting")
	public GoBackDirectSettingDto getGoBackCommonSetting() {
		String SID = AppContexts.user().employeeId();
		return this.goBackDirectlyFinder.getGoBackDirectSettingBySID(SID);
	}

}
