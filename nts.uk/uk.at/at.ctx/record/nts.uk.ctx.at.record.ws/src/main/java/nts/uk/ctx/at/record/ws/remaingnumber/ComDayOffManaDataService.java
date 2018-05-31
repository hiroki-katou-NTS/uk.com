package nts.uk.ctx.at.record.ws.remaingnumber;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.record.app.command.remainingnumber.subhdmana.DayOffManaCommand;
import nts.uk.ctx.at.record.app.command.remainingnumber.subhdmana.DayOffManaCommandHandler;
import nts.uk.ctx.at.record.app.find.remainingnumber.subhdmana.DayOffManagementFinder;
import nts.uk.ctx.at.record.app.find.remainingnumber.subhdmana.dto.DayOffResult;

@Path("at/record/remainnumber/subhd")
@Produces("application/json")
public class ComDayOffManaDataService extends WebService {
	
	
	@Inject
	private DayOffManagementFinder dayOffManagementFinder;
	
	@Inject
	private DayOffManaCommandHandler dayOffManaCommandHandler;
	
	@POST
	@Path("getAll/{leaveId}/{employeeId}")
	public DayOffResult getByRemainOrDayOffManagement(@PathParam("leaveId") String leaveId,@PathParam("employeeId") String employeeId) {
		DayOffResult daysOffMana = dayOffManagementFinder.getBySidWithReDay(leaveId,employeeId);
		return daysOffMana;
	}
	
	@POST
	@Path("updateComDayOff")
	public List<String> update(DayOffManaCommand dayOffManaCommand) {
		return dayOffManaCommandHandler.handle(dayOffManaCommand);
	}
	
}
