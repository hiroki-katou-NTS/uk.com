package nts.uk.ctx.at.request.ws.application.gobackdirectly;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.request.app.find.application.gobackdirectly.GoBackDirectlyFinder;
import nts.uk.ctx.at.request.app.find.application.gobackdirectly.InforGoBackCommonDirectDto;
import nts.uk.ctx.at.request.app.find.application.gobackdirectly.ParamChangeDate;
import nts.uk.ctx.at.request.app.find.application.gobackdirectly.ParamStartMobile;

@Path("at/request/application/gobackdirectly/mobile")
@Produces("application/json")
public class GoBackDirectlyMobileService extends WebService{
	
	@Inject
	private GoBackDirectlyFinder goBackDirectlyFinder;
	
	@POST
	@Path("start")
	public InforGoBackCommonDirectDto getGoBackCommonSettingNew(ParamStartMobile param) {	
		return this.goBackDirectlyFinder.getStartKAFS09(param);
	}
	@POST
	@Path("getAppDataByDate")
	public InforGoBackCommonDirectDto changeDataByDate(ParamChangeDate param) {
		return this.goBackDirectlyFinder.getChangeDateKAFS09(param);
	}
}
