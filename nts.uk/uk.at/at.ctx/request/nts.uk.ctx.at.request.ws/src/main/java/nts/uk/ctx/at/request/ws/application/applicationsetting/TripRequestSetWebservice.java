package nts.uk.ctx.at.request.ws.application.applicationsetting;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.request.app.find.application.triprequestsetting.TripRequestSetDto;
import nts.uk.ctx.at.request.app.find.application.triprequestsetting.TripRequestSetFinder;

@Path("at/request/application/triprequest")
@Produces("application/json")
public class TripRequestSetWebservice extends WebService{
	@Inject
	private TripRequestSetFinder tripFinder;
	@POST
	@Path("disp")
	public TripRequestSetDto getAppSet(){
		 return tripFinder.findByCid();
	}
}
