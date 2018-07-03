package nts.uk.ctx.at.request.ws.applicationreason;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.request.app.find.setting.applicationreason.ApplicationReasonDto;
import nts.uk.ctx.at.request.app.find.setting.applicationreason.ApplicationReasonFinder;

@Path("at/request/application-reason")
@Produces("application/json")
public class ApplicationReasonService extends WebService{
	@Inject
	ApplicationReasonFinder reasonFinder;
	// find list application reason for KAF022
	@POST
	@Path("find/reason/{appType}")
	public List<ApplicationReasonDto> getReason(@PathParam("appType") int appType){
		return reasonFinder.getListApplicationReason(appType);
	}
}
