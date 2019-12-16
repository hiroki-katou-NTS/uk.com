package nts.uk.ctx.pereg.ws.workrule.closure;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.uk.ctx.pereg.app.find.workrule.closure.WorkDayFinder;
import nts.uk.ctx.pereg.dom.workrule.closure.ClosureDateOfEmploymentImport;

@Path("ctx/pereg/closure")
@Produces("application/json")
public class ClosureWebservice {

	@Inject
	private WorkDayFinder finder;

	@POST
	@Path("getClosureDate/{companyId}")
	public List<ClosureDateOfEmploymentImport> getClosureDate(@PathParam("companyId") String companyId) {
		return this.finder.getClosureDate(companyId);
	}
}
