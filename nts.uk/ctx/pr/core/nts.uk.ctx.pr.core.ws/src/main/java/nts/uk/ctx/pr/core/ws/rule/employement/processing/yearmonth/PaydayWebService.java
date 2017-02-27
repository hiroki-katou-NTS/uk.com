package nts.uk.ctx.pr.core.ws.rule.employement.processing.yearmonth;

import javax.inject.Inject;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pr.core.app.find.rule.employement.processing.yearmonth.PaydayFinder;

@Path("pr/core/payday")
@Produces("application/json")
public class PaydayWebService extends WebService {
	@Inject
	private PaydayFinder paydayFinder;
}
