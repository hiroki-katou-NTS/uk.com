package nts.uk.ctx.at.record.ws.workrecord.erroralarm.schedule;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.ctx.at.record.app.find.workrecord.erroralarm.schedule.ErrorAlarmScheduleMonthlyFinder;
import nts.uk.ctx.at.record.app.find.workrecord.erroralarm.schedule.ScheFixItemMonthlyDto;

@Path("at/record/workrecord/erroralarm/schedule/monthly")
@Produces("application/json")
public class ErrorAlarmScheduleMonthlyWS {
	@Inject
	private ErrorAlarmScheduleMonthlyFinder errorAlarmScheduleMonthlyFinder;
	
	@POST
	@Path("fixeditemmonth")
	public List<ScheFixItemMonthlyDto> getScheFixItemDay() {
		return errorAlarmScheduleMonthlyFinder.getScheFixItem();
	}
}
