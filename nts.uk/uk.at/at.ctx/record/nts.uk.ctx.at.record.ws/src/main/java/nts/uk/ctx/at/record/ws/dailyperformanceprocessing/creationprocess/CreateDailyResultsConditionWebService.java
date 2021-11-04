package nts.uk.ctx.at.record.ws.dailyperformanceprocessing.creationprocess;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.app.command.JavaTypeResult;
import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.record.app.find.dailyperformanceprocessing.creationprocess.CreateFutureDayCheckFinder;
import nts.uk.ctx.at.record.app.find.dailyperformanceprocessing.creationprocess.CreateFutureDayCheckParam;

@Path("at/record/createDailyResultsCondtion")
@Produces("application/json")
public class CreateDailyResultsConditionWebService extends WebService {

	@Inject
	private CreateFutureDayCheckFinder createFutureDayCheckFinder;
	
	@POST
	@Path("/isCreatingFutureDay")
	public JavaTypeResult<Boolean> isCreatingFutureDay(CreateFutureDayCheckParam param) {
		return new JavaTypeResult<>(this.createFutureDayCheckFinder.isCreatingFutureDay(param));
	}
}
