package nts.uk.ctx.at.aggregation.ws.schedulecounter.workplacecounter;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.aggregation.app.command.schedulecounter.workplacecounter.RegisterWorkplaceCounterCommand;
import nts.uk.ctx.at.aggregation.app.command.schedulecounter.workplacecounter.RegisterWorkplaceCounterCommandHandler;
import nts.uk.ctx.at.aggregation.app.find.schedulecounter.wkpcounter.WorkplaceCounterCategoryDto;
import nts.uk.ctx.at.aggregation.app.find.schedulecounter.wkpcounter.WorkplaceCounterFinder;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.WorkplaceCounterCategory;

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
	public  List<WorkplaceCounterCategory> registerWorkplace(RegisterWorkplaceCounterCommand command) {
		return this.workplaceCounterCommandHandler.handle(command);
	}

}
