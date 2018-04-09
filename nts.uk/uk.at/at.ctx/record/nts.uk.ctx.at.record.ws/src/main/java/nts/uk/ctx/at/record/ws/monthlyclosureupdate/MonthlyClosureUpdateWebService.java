package nts.uk.ctx.at.record.ws.monthlyclosureupdate;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.arc.task.AsyncTaskInfo;
import nts.uk.ctx.at.record.app.command.monthlyclosureupdate.CheckMonthlyClosureCommandHandler;
import nts.uk.ctx.at.record.app.command.monthlyclosureupdate.ConfirmCompleteMonthlyUpdateCommandHandler;
import nts.uk.ctx.at.record.app.command.monthlyclosureupdate.ExecuteMonthlyClosureCommandHandler;
import nts.uk.ctx.at.record.app.command.monthlyclosureupdate.MonthlyClosureResponse;
import nts.uk.ctx.at.record.app.find.monthlyclosureupdate.Kmw006ResultDto;
import nts.uk.ctx.at.record.app.find.monthlyclosureupdate.MonthlyClosureUpdateFinder;
import nts.uk.ctx.at.record.app.find.monthlyclosureupdate.MonthlyClosureUpdateLogDto;

/**
 * 
 * @author HungTT
 *
 */

@Path("at/record/monthlyclosure")
@Produces("application/json")
public class MonthlyClosureUpdateWebService extends WebService {

	@Inject
	private ExecuteMonthlyClosureCommandHandler executeHandler;

	@Inject
	private CheckMonthlyClosureCommandHandler checkHandler;

	@Inject
	private MonthlyClosureUpdateFinder monthlyClosureFinder;

	@Inject
	private ConfirmCompleteMonthlyUpdateCommandHandler completeHandler;

	@POST
	@Path("execution")
	public AsyncTaskInfo executeMonthlyClosureUpdate(MonthlyClosureResponse command) {
		return executeHandler.handle(command);
	}

	@POST
	@Path("checkStatus/{closureId}")
	public MonthlyClosureResponse checkMonthlyClosureUpdate(@PathParam("closureId") int closureId) {
		return checkHandler.handle(closureId);
	}

	@POST
	@Path("getMonthlyClosure/{monthlyClosureId}")
	public MonthlyClosureUpdateLogDto checkMonthlyClosureUpdate(@PathParam("monthlyClosureId") String id) {
		return monthlyClosureFinder.findById(id);
	}

	@POST
	@Path("completeConfirm/{monthlyClosureId}")
	public void completeConfirm(@PathParam("monthlyClosureId") String id) {
		completeHandler.handle(id);
	}
	
	@POST
	@Path("getResults/{monthlyClosureId}")
	public Kmw006ResultDto getResults(@PathParam("monthlyClosureId") String id) {
		return monthlyClosureFinder.getClosureResult(id);
	}

}
