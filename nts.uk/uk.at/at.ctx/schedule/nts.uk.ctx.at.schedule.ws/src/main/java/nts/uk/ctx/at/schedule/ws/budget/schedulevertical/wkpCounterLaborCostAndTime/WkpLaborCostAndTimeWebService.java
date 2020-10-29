package nts.uk.ctx.at.schedule.ws.budget.schedulevertical.wkpCounterLaborCostAndTime;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.schedule.app.command.budget.schedulevertical.personalCounter.RegisterPersonalCounterCommand;
import nts.uk.ctx.at.schedule.app.command.budget.schedulevertical.personalCounter.RegisterPersonalCounterCommandHandler;
import nts.uk.ctx.at.schedule.app.command.budget.schedulevertical.wkpCounterLaborCostAndTime.RegisterWkpLaborCostAndTimeCommand;
import nts.uk.ctx.at.schedule.app.command.budget.schedulevertical.wkpCounterLaborCostAndTime.RegisterWkpLaborCostAndTimeCommandHandler;
import nts.uk.ctx.at.schedule.app.find.budget.schedulevertical.wkpCounterLaborCostAndTime.WkpLaborCostAndTimeDto;
import nts.uk.ctx.at.schedule.app.find.budget.schedulevertical.wkpCounterLaborCostAndTime.WkpLaborCostAndTimeFinder;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.List;

/**
 * Screen D
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
	public void registerCostAndTime(List<RegisterWkpLaborCostAndTimeCommand> command) {
		this.costAndTimeCommandHandler.handle(command);
	}

	
}
