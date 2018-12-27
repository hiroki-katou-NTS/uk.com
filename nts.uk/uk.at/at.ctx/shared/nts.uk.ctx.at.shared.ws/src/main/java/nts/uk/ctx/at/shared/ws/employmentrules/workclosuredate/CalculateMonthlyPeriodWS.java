package nts.uk.ctx.at.shared.ws.employmentrules.workclosuredate;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.shared.app.find.employmentrules.workclosuredate.CalculateMonthlyPeriodFinder;
import nts.uk.ctx.at.shared.app.find.employmentrules.workclosuredate.InputCalculateMonthly;
import nts.uk.ctx.at.shared.app.find.employmentrules.workclosuredate.OutputCalculateMonthly;

@Path("at/shared/workclosuredate")
@Produces(MediaType.APPLICATION_JSON)
public class CalculateMonthlyPeriodWS extends WebService {
	@Inject
	private CalculateMonthlyPeriodFinder finder;
	
	
	@Path("findbyclosureid/{closureid}")
	@POST
	public OutputCalculateMonthly saveData(@PathParam("closureid") int closureId) {
		return finder.getMonthlyPeriodResult(closureId);
	}
	
}
