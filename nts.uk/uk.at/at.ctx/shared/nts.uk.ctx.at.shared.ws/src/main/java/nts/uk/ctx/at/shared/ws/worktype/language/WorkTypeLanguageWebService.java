package nts.uk.ctx.at.shared.ws.worktype.language;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.shared.app.command.worktype.language.InsertWorkTypeLanguageCommand;
import nts.uk.ctx.at.shared.app.command.worktype.language.InsertWorkTypeLanguageCommandHandler;

/**
 * The Class Work Type Language WebService.
 * 
 * @author sonnh1
 *
 */
@Path("at/share/worktype/language")
@Produces("application/json")
public class WorkTypeLanguageWebService extends WebService {
	
	@Inject
	private InsertWorkTypeLanguageCommandHandler insert;

	/**
	 * Add/Update WorkTypeLanguage
	 * 
	 * @param workTypeLanguage
	 */
	@POST
	@Path("insert")
	public void insert(InsertWorkTypeLanguageCommand workTypeLanguage) {
		this.insert.handle(workTypeLanguage);
	}
}
