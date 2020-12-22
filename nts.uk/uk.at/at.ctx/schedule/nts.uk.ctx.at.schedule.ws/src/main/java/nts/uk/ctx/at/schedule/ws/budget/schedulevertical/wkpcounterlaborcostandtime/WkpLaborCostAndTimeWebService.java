package nts.uk.ctx.at.schedule.ws.budget.schedulevertical.wkpcounterlaborcostandtime;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.schedule.app.command.budget.schedulevertical.wkpcounterlaborcostandtime.RegisterWkpLaborCostAndTimeCommand;
import nts.uk.ctx.at.schedule.app.command.budget.schedulevertical.wkpcounterlaborcostandtime.RegisterWkpLaborCostAndTimeCommandHandler;
import nts.uk.ctx.at.schedule.app.find.budget.schedulevertical.wkplaborcostandtime.WkpLaborCostAndTimeDto;
import nts.uk.ctx.at.schedule.app.find.budget.schedulevertical.wkplaborcostandtime.WkpLaborCostAndTimeFinder;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.List;

/**
 * KML002 Screen D
 */
@Path("ctx/at/schedule/budget/wkpLaborCostAndTime")
@Produces("application/json")
public class WkpLaborCostAndTimeWebService extends WebService {
	@Inject
	private WkpLaborCostAndTimeFinder wkpLaborCostAndTimeFinder;
	
	@Inject
	private RegisterWkpLaborCostAndTimeCommandHandler costAndTimeCommandHandler;

	@Path("getById")
	@POST
	public List<WkpLaborCostAndTimeDto> findByCid() {
		return wkpLaborCostAndTimeFinder.findById();
	}

	@Path("register")
	@POST
	public void registerCostAndTime(RegisterWkpLaborCostAndTimeCommand command) {
		this.costAndTimeCommandHandler.handle(command);
	}

	
}
