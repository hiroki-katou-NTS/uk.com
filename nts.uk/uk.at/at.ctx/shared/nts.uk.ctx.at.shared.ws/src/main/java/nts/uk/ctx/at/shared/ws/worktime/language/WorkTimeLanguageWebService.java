package nts.uk.ctx.at.shared.ws.worktime.language;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.ctx.at.shared.app.command.worktime.language.InsertWorkTimeLanguageCommand;
import nts.uk.ctx.at.shared.app.command.worktime.language.InsertWorkTimeLanguageCommandHandler;

/**
 * The Class Work Time Language WebService.
 * 
 * @author sonnh1
 *
 */
@Path("at/share/worktime/language")
@Produces("application/json")
public class WorkTimeLanguageWebService {
	
	@Inject
	private InsertWorkTimeLanguageCommandHandler insert;
	
	@POST
	@Path("insert")
	public void insert(InsertWorkTimeLanguageCommand workTypeLanguage) {
		this.insert.handle(workTypeLanguage);
	}
}
