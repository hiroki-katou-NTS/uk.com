package nts.uk.ctx.at.record.ws.workrecord.erroralarm.schedule;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.ctx.at.record.app.find.workrecord.erroralarm.schedule.ErrorAlarmScheduleDailyFinder;
import nts.uk.ctx.at.record.app.find.workrecord.erroralarm.schedule.ScheFixItemDayDto;

@Path("at/record/workrecord/erroralarm/schedule/")
@Produces("application/json")
public class ErrorAlarmScheduleDailyWS {
	@Inject
	private ErrorAlarmScheduleDailyFinder errorAlarmScheduleDailyFinder;
	
	@POST
	@Path("fixedchecksheduledaily")
	public HashMap<Integer, String> getFixedCheckSDailyItems() {
		return errorAlarmScheduleDailyFinder.getFixedCheckScheduleDailyItems();
	}
	
	@POST
	@Path("schefixitemday")
	public List<ScheFixItemDayDto> getScheFixItemDay() {
		return errorAlarmScheduleDailyFinder.getScheFixItemDay();
	}
}
