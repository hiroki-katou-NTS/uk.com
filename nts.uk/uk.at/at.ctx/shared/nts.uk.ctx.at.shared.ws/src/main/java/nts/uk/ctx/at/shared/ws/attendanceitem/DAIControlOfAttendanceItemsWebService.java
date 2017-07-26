package nts.uk.ctx.at.shared.ws.attendanceitem;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.shared.app.attendanceitem.command.DAIControlOfAttendanceItemsCommand;
import nts.uk.ctx.at.shared.app.attendanceitem.command.DAIControlOfAttendanceItemsUpdateCommand;
import nts.uk.ctx.at.shared.app.attendanceitem.find.DAIControlOfAttendanceItemsDto;
import nts.uk.ctx.at.shared.app.attendanceitem.find.DAIControlOfAttendanceItemsFinder;
@Path("at/share/DAIControlOfAttendanceItems")
@Produces("application/json")
public class DAIControlOfAttendanceItemsWebService extends WebService {
	@Inject
	private DAIControlOfAttendanceItemsFinder dAIControlOfAttendanceItemsFinder;
	@Inject
	private DAIControlOfAttendanceItemsUpdateCommand dAIControlOfAttendanceItemsUpdateCommand;

	@POST
	@Path("getListControlOfAttendanceItem/{workTypeCode}")
	List<DAIControlOfAttendanceItemsDto> getListControlOfAttendanceItem(
			@PathParam("workTypeCode") String workTypeCode) {
		return this.dAIControlOfAttendanceItemsFinder.getListControlOfAttendanceItem(workTypeCode);
	}
	@POST
	@Path("updateListControlOfAttendanceItem")
	void updateListControlOfAttendanceItem(
			List<DAIControlOfAttendanceItemsCommand> lstDAIControlOfAttendanceItemsCommand){
		this.dAIControlOfAttendanceItemsUpdateCommand.handle(lstDAIControlOfAttendanceItemsCommand);
	}
}
