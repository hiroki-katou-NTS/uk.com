package nts.uk.ctx.at.aggregation.ws.schedulecounter.personalcounter;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.aggregation.app.command.schedulecounter.personalcounter.RegisterPersonalCounterCommand;
import nts.uk.ctx.at.aggregation.app.command.schedulecounter.personalcounter.RegisterPersonalCounterCommandHandler;
import nts.uk.ctx.at.aggregation.app.find.schedulecounter.personal.PersonalCounterCategoryDto;
import nts.uk.ctx.at.aggregation.app.find.schedulecounter.personal.PersonalCounterFinder;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.PersonalCounterCategory;

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
