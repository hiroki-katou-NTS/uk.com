package nts.uk.ctx.at.shared.ws.service.workrule.closure;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.ctx.at.shared.app.find.workrule.closure.ClosureFinder;
import nts.uk.ctx.at.shared.app.find.workrule.closure.dto.ClosureDto;


@Path("ctx/at/shared/service/workrule/closure")
@Produces("application/json")
public class ClosureWebService {
	/** The save handler. */
	@Inject
	private ClosureFinder finder;

	@POST
	@Path("find_by_empid")
	public ClosureDto findClosureByEmployeeId() {
		return this.finder.findClosureByEmployeeId();
	}
}
