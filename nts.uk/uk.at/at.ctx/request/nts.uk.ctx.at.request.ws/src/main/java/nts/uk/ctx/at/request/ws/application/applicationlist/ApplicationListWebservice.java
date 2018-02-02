package nts.uk.ctx.at.request.ws.application.applicationlist;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.request.app.find.application.applicationlist.AppListExtractConditionDto;
import nts.uk.ctx.at.request.app.find.application.applicationlist.ApplicationListDto;
import nts.uk.ctx.at.request.app.find.application.applicationlist.ApplicationListFinder;
/**
 * 
 * @author hoatt
 *
 */

@Path("at/request/application/applicationlist")
@Produces("application/json")
public class ApplicationListWebservice extends WebService{

	@Inject
	private ApplicationListFinder appListFinder;
	@POST
	@Path("getapplist")
	public ApplicationListDto getApplicationList(AppListExtractConditionDto param) {
		return this.appListFinder.getAppList(param);
	}
}
