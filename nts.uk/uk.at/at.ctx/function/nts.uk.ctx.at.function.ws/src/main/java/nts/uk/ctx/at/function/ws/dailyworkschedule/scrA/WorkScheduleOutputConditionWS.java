package nts.uk.ctx.at.function.ws.dailyworkschedule.scrA;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.function.app.find.dailyworkschedule.scrA.WorkScheduleOutputConditionDto;
import nts.uk.ctx.at.function.app.find.dailyworkschedule.scrA.WorkScheduleOutputConditionFinder;

@Path("at/function/dailyworkschedule")
@Produces(MediaType.APPLICATION_JSON)
public class WorkScheduleOutputConditionWS extends WebService{
	@Inject
	private WorkScheduleOutputConditionFinder workScheduleOutputConditionFinder;
	
	@Path("startPage/{isExistWorkScheduleOutputCondition}/{keyRestoreDomain}")
	@POST
	public WorkScheduleOutputConditionDto find(@PathParam("isExistWorkScheduleOutputCondition") boolean isExistWorkScheduleOutputCondition,
												@PathParam("keyRestoreDomain") String keyRestoreDomain){
		return this.workScheduleOutputConditionFinder.startScr(isExistWorkScheduleOutputCondition, keyRestoreDomain);
	}
}
