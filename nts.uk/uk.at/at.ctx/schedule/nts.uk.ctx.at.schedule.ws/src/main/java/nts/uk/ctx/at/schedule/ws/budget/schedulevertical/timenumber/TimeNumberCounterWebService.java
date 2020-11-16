package nts.uk.ctx.at.schedule.ws.budget.schedulevertical.timenumber;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.schedule.app.command.budget.schedulevertical.timenumber.RegisterTimeNumberCounterCommand;
import nts.uk.ctx.at.schedule.app.command.budget.schedulevertical.timenumber.RegisterTimeNumberCounterCommandHandler;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 * KML002 Screen G
 */
@Path("ctx/at/schedule/budget/timeNumberCounter")
@Produces("application/json")
public class TimeNumberCounterWebService extends WebService {

	@Inject
	private RegisterTimeNumberCounterCommandHandler timeNumberCounterCommandHandler;

	@Path("register")
	@POST
	public void registerWorkplace(RegisterTimeNumberCounterCommand command) {
		this.timeNumberCounterCommandHandler.handle(command);
	}

	
}
