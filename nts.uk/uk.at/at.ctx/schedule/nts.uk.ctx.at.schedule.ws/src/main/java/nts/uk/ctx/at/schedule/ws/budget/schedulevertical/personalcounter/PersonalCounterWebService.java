package nts.uk.ctx.at.schedule.ws.budget.schedulevertical.personalcounter;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.schedule.app.command.budget.schedulevertical.personalcounter.RegisterPersonalCounterCommand;
import nts.uk.ctx.at.schedule.app.command.budget.schedulevertical.personalcounter.RegisterPersonalCounterCommandHandler;
import nts.uk.ctx.at.schedule.app.find.budget.schedulevertical.personal.PersonalCounterCategoryDto;
import nts.uk.ctx.at.schedule.app.find.budget.schedulevertical.personal.PersonalCounterFinder;
import nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter.PersonalCounterCategory;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.List;

/**
 * KML002 Screen C
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
	public List<PersonalCounterCategory> registerPersonal(RegisterPersonalCounterCommand command) {
		return this.personalCounterCommandHandler.handle(command);
	}

}
