package nts.uk.screen.com.ws.ccg008;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.auth.app.find.employmentrole.InitDisplayPeriodSwitchSetFinder;
import nts.uk.ctx.at.auth.app.find.employmentrole.dto.InitDisplayPeriodSwitchSetDto;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService;
import nts.uk.shr.com.context.AppContexts;


@Path("screen/com/ccg008")
@Produces("application/json")
public class Ccg008WebService {
	@Inject
	private ClosureService closureService;
	
	@Inject
	private InitDisplayPeriodSwitchSetFinder displayPeriodfinder;
	
	@POST
	@Path("get-cache")
	public Ccg008Dto cache() {
		String employeeID = AppContexts.user().employeeId();
		GeneralDate systemDate = GeneralDate.today();
		Closure closure = this.closureService.getClosureDataByEmployee(employeeID, systemDate);
		InitDisplayPeriodSwitchSetDto rq609 = displayPeriodfinder.targetDateFromLogin();
		Ccg008Dto result = new Ccg008Dto(closure.getClosureId().value, rq609.getCurrentOrNextMonth());
		return result;
	}
}
