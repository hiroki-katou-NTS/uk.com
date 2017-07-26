package nts.uk.ctx.at.shared.ws.attendanceitem;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.shared.app.attendanceitem.command.ControlOfAttendanceItemsCommand;
import nts.uk.ctx.at.shared.app.attendanceitem.command.ControlOfAttendanceItemsUpdateCommand;
import nts.uk.ctx.at.shared.app.attendanceitem.find.ControlOfAttendanceItemsDto;
import nts.uk.ctx.at.shared.app.attendanceitem.find.ControlOfAttendanceItemsFinder;

@Path("at/share/ControlOfAttendanceItems")
@Produces("application/json")
public class ControlOfAttendanceItemsWebService extends WebService {
	@Inject
	private ControlOfAttendanceItemsFinder controlOfAttendanceItemsFinder;
	@Inject
	private ControlOfAttendanceItemsUpdateCommand controlOfAttendanceItemsUpdateCommand;

	@POST
	@Path("getControlOfAttendanceItem/{attendanceItemId}")
	public ControlOfAttendanceItemsDto getControlOfAttendanceItem(
			@PathParam("attendanceItemId") String attendanceItemId) {
		return this.controlOfAttendanceItemsFinder.getControlOfAttendanceItem(attendanceItemId);
	}
	@POST
	@Path("updateControlOfAttendanceItem")
	public void updateControlOfAttendanceItem(ControlOfAttendanceItemsCommand controlOfAttendanceItemsCommand) {
		this.controlOfAttendanceItemsUpdateCommand.handle(controlOfAttendanceItemsCommand);
	}
}
