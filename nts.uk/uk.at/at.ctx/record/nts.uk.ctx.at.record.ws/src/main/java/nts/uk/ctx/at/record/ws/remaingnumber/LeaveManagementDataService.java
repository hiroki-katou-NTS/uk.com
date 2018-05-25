package nts.uk.ctx.at.record.ws.remaingnumber;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.record.app.command.remainingnumber.subhdmana.LeaveManaComand;
import nts.uk.ctx.at.record.app.command.remainingnumber.subhdmana.LeaveManaCommandHandler;
import nts.uk.ctx.at.record.app.find.remainingnumber.subhdmana.LeaveManaFinder;
import nts.uk.ctx.at.record.app.find.remainingnumber.subhdmana.dto.LeaveManaResult;


@Path("at/record/remainnumber/subhd")
@Produces("application/json")
public class LeaveManagementDataService extends WebService {
	
	@Inject
	private LeaveManaFinder leaveManaFinder;
	
	@Inject
	private LeaveManaCommandHandler leaveManaCommandHandler;
	
	@POST
	@Path("getAllLeave/{comDayOffID}/{employeeId}")
	public LeaveManaResult getComDayOffId(@PathParam("comDayOffID") String comDayOffID,@PathParam("employeeId") String employeeId) {
		LeaveManaResult leaveDataDtos = new LeaveManaResult();
		leaveDataDtos = leaveManaFinder.getByComDayOffId(comDayOffID, employeeId);
		return leaveDataDtos;
	}
	
	@POST
	@Path("updateLeaveMana")
	public List<String> update(LeaveManaComand leaveManaCommand) {
		return leaveManaCommandHandler.handle(leaveManaCommand);
	}
	
}
