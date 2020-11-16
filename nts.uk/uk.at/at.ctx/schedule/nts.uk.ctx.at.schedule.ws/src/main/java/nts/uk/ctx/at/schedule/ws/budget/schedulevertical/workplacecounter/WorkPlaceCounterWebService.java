package nts.uk.ctx.at.schedule.ws.budget.schedulevertical.workplacecounter;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.schedule.app.command.budget.schedulevertical.workplacecounter.RegisterWorkplaceCounterCommand;
import nts.uk.ctx.at.schedule.app.command.budget.schedulevertical.workplacecounter.RegisterWorkplaceCounterCommandHandler;
import nts.uk.ctx.at.schedule.app.find.budget.schedulevertical.wkpcounter.WorkplaceCounterCategoryDto;
import nts.uk.ctx.at.schedule.app.find.budget.schedulevertical.wkpcounter.WorkplaceCounterFinder;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.List;

/**
 * KML002 Screen B
 */
@Path("ctx/at/schedule/budget/workplaceCounter")
@Produces("application/json")
public class WorkPlaceCounterWebService extends WebService {
	@Inject
	private WorkplaceCounterFinder workplaceCounterFinder;
	
	@Inject
	private RegisterWorkplaceCounterCommandHandler workplaceCounterCommandHandler;

	@Path("getById")
	@POST
	public List<WorkplaceCounterCategoryDto> findByCid() {
		return workplaceCounterFinder.findById();
	}

	@Path("register")
	@POST
	public void registerWorkplace(RegisterWorkplaceCounterCommand command) {
		this.workplaceCounterCommandHandler.handle(command);
	}

}
