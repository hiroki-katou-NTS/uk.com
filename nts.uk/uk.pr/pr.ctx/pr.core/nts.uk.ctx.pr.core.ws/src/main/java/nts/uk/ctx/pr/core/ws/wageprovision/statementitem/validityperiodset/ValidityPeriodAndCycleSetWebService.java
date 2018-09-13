package nts.uk.ctx.pr.core.ws.wageprovision.statementitem.validityperiodset;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.uk.ctx.pr.core.app.find.wageprovision.statementitem.ValidityPeriodAndCycleSetDto;
import nts.uk.ctx.pr.core.app.find.wageprovision.statementitem.ValidityPeriodAndCycleSetFinder;

@Path("ctx/pr/core/wageprovision/statementitem/validityperiodset")
@Produces("application/json")
public class ValidityPeriodAndCycleSetWebService {
	@Inject
	private ValidityPeriodAndCycleSetFinder validityPeriodAndCycleSetFinder;
	
	@POST
	@Path("getValidityPeriodAndCycleSet/{salaryItemId}")
	public ValidityPeriodAndCycleSetDto getValidityPeriodAndCycleSet(@PathParam("salaryItemId") String salaryItemId) {
		return this.validityPeriodAndCycleSetFinder.getAllSetValidityPeriodCycle(salaryItemId);
	}
}
