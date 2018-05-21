package nts.uk.ctx.at.record.ws.remaingnumber;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.record.app.command.remainingnumber.dayoffmanagement.DayOffManaCommand;
import nts.uk.ctx.at.record.app.find.remainingnumber.dayoffmanagement.DayOffManagementDto;
import nts.uk.ctx.at.record.app.find.remainingnumber.dayoffmanagement.DayOffManagementFinder;

@Path("at/record/remainnumber/subhd")
@Produces("application/json")
public class LeaveManagementDataService extends WebService {
	
	
	@Inject
	DayOffManagementFinder dayOffManagementFinder;
	
	@POST
	@Path("getAll/{leaveId}")
	public List<DayOffManagementDto> getByRemainOrDayOffManagement(@PathParam("leaveId") String leaveId) {
		List<DayOffManagementDto> daysOffMana = new ArrayList<>();
		daysOffMana = dayOffManagementFinder.getBySidWithReDay(leaveId);
		return daysOffMana;
	}
	
	@POST
	@Path("update")
	public void update(DayOffManaCommand dayOffManaCommand) {
		List<DayOffManagementDto> daysOffMana = new ArrayList<>();
	}
	
}
