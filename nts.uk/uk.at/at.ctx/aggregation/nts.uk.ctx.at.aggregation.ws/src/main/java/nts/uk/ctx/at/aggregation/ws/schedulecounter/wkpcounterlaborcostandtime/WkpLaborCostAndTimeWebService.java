package nts.uk.ctx.at.aggregation.ws.schedulecounter.wkpcounterlaborcostandtime;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.aggregation.app.command.schedulecounter.wkpcounterlaborcostandtime.RegisterWkpLaborCostAndTimeCommand;
import nts.uk.ctx.at.aggregation.app.command.schedulecounter.wkpcounterlaborcostandtime.RegisterWkpLaborCostAndTimeCommandHandler;
import nts.uk.ctx.at.aggregation.app.find.schedulecounter.wkplaborcostandtime.WkpLaborCostAndTimeDto;
import nts.uk.ctx.at.aggregation.app.find.schedulecounter.wkplaborcostandtime.WkpLaborCostAndTimeFinder;

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
