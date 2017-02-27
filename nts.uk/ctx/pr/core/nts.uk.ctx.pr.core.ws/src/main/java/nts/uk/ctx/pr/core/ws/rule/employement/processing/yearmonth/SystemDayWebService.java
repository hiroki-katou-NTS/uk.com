package nts.uk.ctx.pr.core.ws.rule.employement.processing.yearmonth;

import javax.inject.Inject;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pr.core.app.find.rule.employement.processing.yearmonth.SystemDayFinder;

@Path("pr/core/systemday")
@Produces("application/json")
public class SystemDayWebService extends WebService {

	@Inject
	private SystemDayFinder systemDayFinder;
}
