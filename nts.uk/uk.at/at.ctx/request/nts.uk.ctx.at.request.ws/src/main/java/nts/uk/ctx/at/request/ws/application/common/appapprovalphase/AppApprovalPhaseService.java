package nts.uk.ctx.at.request.ws.application.common.appapprovalphase;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.request.app.find.application.common.appapprovalphase.AppApprovalPhaseDto;
import nts.uk.ctx.at.request.app.find.application.common.appapprovalphase.AppApprovalPhaseFinder;

@Path("at/request/appapprovalphase")
@Produces("application/json")
public class AppApprovalPhaseService extends WebService {

	@Inject
	private AppApprovalPhaseFinder phaseFinder;
	
	@POST
	@Path("getallphase/{appID}")
	public List<AppApprovalPhaseDto> getAllPhaseByAppID(@PathParam("appID") String appID){
		return this.phaseFinder.findPhaseByAppID(appID);
	}
	
}
