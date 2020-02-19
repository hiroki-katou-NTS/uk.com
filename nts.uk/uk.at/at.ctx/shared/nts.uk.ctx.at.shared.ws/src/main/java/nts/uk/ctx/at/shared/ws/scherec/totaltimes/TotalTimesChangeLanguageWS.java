package nts.uk.ctx.at.shared.ws.scherec.totaltimes;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.shared.app.command.scherec.totaltimes.language.InsertTotalTimesLanguageCommand;
import nts.uk.ctx.at.shared.app.command.scherec.totaltimes.language.InsertTotalTimesLanguageCommandHandler;
import nts.uk.ctx.at.shared.app.find.scherec.totaltimes.TotalTimesLangFinder;
import nts.uk.ctx.at.shared.app.find.scherec.totaltimes.dto.TotalTimesLangDto;
/**
 * 
 * @author phongtq
 *
 */
@Path("at/shared/scherec/totaltimeslang")
@Produces("application/json")
public class TotalTimesChangeLanguageWS extends WebService {
	
	@Inject
	private TotalTimesLangFinder totalTimesFinder;
	
	@Inject InsertTotalTimesLanguageCommandHandler commandHandler;
	
	/**
	 * 
	 * @param langId
	 * @return
	 */
	@POST
	@Path("getdetail/{langId}")
	public List<TotalTimesLangDto> findWorkTypeLanguage(@PathParam("langId") String langId) {
		return this.totalTimesFinder.getTotalTimesDetails(langId);
	}

	@POST
	@Path("insert")
	public void insert(InsertTotalTimesLanguageCommand timesLanguageCommand) {
		this.commandHandler.handle(timesLanguageCommand);
	}
}
