package nts.uk.ctx.at.record.ws.dailyperformanceformat;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.record.app.command.dailyperformanceformat.ControlOfAttendanceItemsCommand;
import nts.uk.ctx.at.record.app.command.dailyperformanceformat.ControlOfAttendanceItemsUpdateCommand;
import nts.uk.ctx.at.record.app.find.dailyperformanceformat.ControlOfAttendanceItemsDto;
import nts.uk.ctx.at.record.app.find.dailyperformanceformat.ControlOfAttendanceItemsFinder;

@Path("at/record/ControlOfAttendanceItems")
@Produces("application/json")
public class ControlOfAttendanceItemsWebService extends WebService {
	@Inject
	private ControlOfAttendanceItemsFinder controlOfAttendanceItemsFinder;
	@Inject
	private ControlOfAttendanceItemsUpdateCommand controlOfAttendanceItemsUpdateCommand;
	@POST
	@Path("updateControlOfAttendanceItem")
	public void updateControlOfAttendanceItem(ControlOfAttendanceItemsCommand controlOfAttendanceItemsCommand) {
		this.controlOfAttendanceItemsUpdateCommand.handle(controlOfAttendanceItemsCommand);
	}
	@POST
	@Path("getControlOfAttendanceItem/{attendanceItemId}")
	public ControlOfAttendanceItemsDto getControlOfAttendanceItem(
			@PathParam("attendanceItemId") int attendanceItemId) {
		return this.controlOfAttendanceItemsFinder.getControlOfAttendanceItem(attendanceItemId);
	}
	
}
