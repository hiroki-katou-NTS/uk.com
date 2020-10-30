package nts.uk.ctx.at.schedule.ws.budget.schedulevertical.personalCounter;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.schedule.app.command.budget.schedulevertical.personalCounter.RegisterPersonalCounterCommand;
import nts.uk.ctx.at.schedule.app.command.budget.schedulevertical.personalCounter.RegisterPersonalCounterCommandHandler;
import nts.uk.ctx.at.schedule.app.command.budget.schedulevertical.workplaceCounter.RegisterWorkplaceCounterCommand;
import nts.uk.ctx.at.schedule.app.command.budget.schedulevertical.workplaceCounter.RegisterWorkplaceCounterCommandHandler;
import nts.uk.ctx.at.schedule.app.find.budget.schedulevertical.personalCounter.PersonalCounterCategoryDto;
import nts.uk.ctx.at.schedule.app.find.budget.schedulevertical.personalCounter.PersonalCounterFinder;
import nts.uk.ctx.at.schedule.app.find.budget.schedulevertical.workplaceCounter.WorkplaceCounterCategoryDto;
import nts.uk.ctx.at.schedule.app.find.budget.schedulevertical.workplaceCounter.WorkplaceCounterFinder;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.List;

/**
 * Screen C
 */
@Path("ctx/at/schedule/budget/personalCounter")
@Produces("application/json")
public class PersonalCounterWebService extends WebService {
	@Inject
	private PersonalCounterFinder personalCounterFinder;
	
	@Inject
	private RegisterPersonalCounterCommandHandler personalCounterCommandHandler;

	@Path("getById")
	@POST
	public List<PersonalCounterCategoryDto> findByCid() {
		return personalCounterFinder.findById();
	}

	@Path("register")
	@POST
	public void registerPersonal(RegisterPersonalCounterCommand command) {
		this.personalCounterCommandHandler.handle(command);
	}

	
}
