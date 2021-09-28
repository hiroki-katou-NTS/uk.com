package nts.uk.screen.com.ws.closureperiod;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.app.find.workrule.closure.dto.ClosurePeriodDto;
import nts.uk.screen.com.app.find.closureperiod.ClosurePeriodFinder;
import nts.uk.shr.com.context.AppContexts;

@Path("com/screen/closurePeriod/")
@Produces("application/json")
public class ClosurePeriodWebService extends WebService {
	
	@Inject
	private ClosurePeriodFinder closurePeriodFinder;

	@POST
	@Path("get")
	public ClosurePeriodDto getClosurePeriod() {
		String employeeId = AppContexts.user().employeeId();
		GeneralDate baseDate = GeneralDate.today();
		return this.closurePeriodFinder.getClosurePeriod(employeeId, baseDate).orElse(null);
	}
}
