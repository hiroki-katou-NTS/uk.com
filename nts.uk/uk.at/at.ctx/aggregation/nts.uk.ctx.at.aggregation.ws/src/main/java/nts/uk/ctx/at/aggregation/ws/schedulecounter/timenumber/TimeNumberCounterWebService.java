package nts.uk.ctx.at.aggregation.ws.schedulecounter.timenumber;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.aggregation.app.command.schedulecounter.timenumber.RegisterTimeNumberCounterCommand;
import nts.uk.ctx.at.aggregation.app.command.schedulecounter.timenumber.RegisterTimeNumberCounterCommandHandler;

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
