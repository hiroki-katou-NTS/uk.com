package nts.uk.ctx.at.schedule.ws.schedule.workschedulestate;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.schedule.app.find.schedule.workschedulestate.WorkScheduleStateDto;
import nts.uk.ctx.at.schedule.app.find.schedule.workschedulestate.WorkScheduleStateFinder;

/**
 * 
 * @author sonnh1
 *
 */
@Path("at/schedule/workschedulestate")
@Produces("application/json")
public class WorkScheduleStateWebSevice extends WebService {

	@Inject
	private WorkScheduleStateFinder finder;

	@POST
	@Path("findAll")
	public List<WorkScheduleStateDto> findAll() {
		return finder.findAll();
	}
}