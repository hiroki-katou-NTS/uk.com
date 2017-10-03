package nts.uk.ctx.at.schedule.ws.executionlog;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.schedule.app.find.executionlog.dto.ScheduleExecutionLogDto;

@Path("at/schedule/exelog")
@Produces("application/json")
public class ExecutionLogWebService extends WebService {

	@POST
	@Path("")
	public ScheduleExecutionLogDto findAllExeLog(){
		
		return null;
	}
}
