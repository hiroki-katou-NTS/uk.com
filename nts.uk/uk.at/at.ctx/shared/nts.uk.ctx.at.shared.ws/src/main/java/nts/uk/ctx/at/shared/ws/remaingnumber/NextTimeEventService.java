package nts.uk.ctx.at.shared.ws.remaingnumber;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.record.app.find.remainingnumber.annualleave.nexttime.NextTimeEventDto;
import nts.uk.ctx.at.record.app.find.remainingnumber.annualleave.nexttime.NextTimeEventFinder;
import nts.uk.ctx.at.record.app.find.remainingnumber.annualleave.nexttime.NextTimeEventParam;

@Path("at/record/remainnumber/annlea/event")
@Produces("application/json")
public class NextTimeEventService extends WebService{
	
	@Inject
	private NextTimeEventFinder nextTimeFinder;
	
	@POST
	@Path("nextTime")
	public NextTimeEventDto getNextTimeData(NextTimeEventParam param) {
		return nextTimeFinder.getNextTimeData(param);
	}

}
