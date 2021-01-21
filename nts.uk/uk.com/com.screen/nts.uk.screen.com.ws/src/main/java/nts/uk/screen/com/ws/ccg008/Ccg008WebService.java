package nts.uk.screen.com.ws.ccg008;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.auth.app.find.employmentrole.InitDisplayPeriodSwitchSetFinder;
import nts.uk.ctx.at.auth.app.find.employmentrole.dto.InitDisplayPeriodSwitchSetDto;
import nts.uk.ctx.at.shared.app.query.workrule.closure.ClosureResultModel;
import nts.uk.ctx.at.shared.app.query.workrule.closure.WorkClosureQueryProcessor;
import nts.uk.ctx.at.shared.dom.adapter.employment.ShareEmploymentAdapter;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmploymentRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService;
import nts.uk.ctx.sys.shared.dom.user.builtin.BuiltInUser;
import nts.uk.shr.com.context.AppContexts;


@Path("screen/com/ccg008")
@Produces("application/json")
public class Ccg008WebService {
	@Inject
	private ClosureRepository closureRepo;
	@Inject
	private ClosureEmploymentRepository closureEmploymentRepo;
	@Inject
	private ShareEmploymentAdapter shareEmploymentAdapter;
	
	@Inject
	private InitDisplayPeriodSwitchSetFinder displayPeriodfinder;
	
	@Inject
	private WorkClosureQueryProcessor workClosureQueryProcessor;
	
	@POST
	@Path("get-cache")
	public Ccg008Dto cache() {
		
		if (BuiltInUser.USER_ID.equals(AppContexts.user().userId())) {
			return Ccg008Dto.forBuiltInUser();
		}
		
		String employeeID = AppContexts.user().employeeId();
		GeneralDate systemDate = GeneralDate.today();
		Closure closure = ClosureService.getClosureDataByEmployee(
				ClosureService.createRequireM3(closureRepo, closureEmploymentRepo, shareEmploymentAdapter),
				new CacheCarrier(), employeeID, systemDate);
		InitDisplayPeriodSwitchSetDto rq609 = displayPeriodfinder.targetDateFromLogin();
		Ccg008Dto result = new Ccg008Dto(closure.getClosureId().value, rq609.getCurrentOrNextMonth());
		return result;
	}
	
	@POST
	@Path("get-closure")
	public List<ClosureResultModel> closure() {
		
		if (BuiltInUser.USER_ID.equals(AppContexts.user().userId())) {
			return Collections.emptyList();
		}
		
		List<ClosureResultModel> rq140 = workClosureQueryProcessor.findClosureByReferenceDate(GeneralDate.today());
		return rq140;
	}
}
