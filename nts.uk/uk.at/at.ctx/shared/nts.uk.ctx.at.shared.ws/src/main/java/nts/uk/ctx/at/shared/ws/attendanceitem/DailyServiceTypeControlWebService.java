package nts.uk.ctx.at.shared.ws.attendanceitem;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.shared.app.attendanceitem.command.DailyServiceTypeControlCommand;
import nts.uk.ctx.at.shared.app.attendanceitem.command.DailyServiceTypeControlUpdateCommand;
import nts.uk.ctx.at.shared.app.attendanceitem.find.DailyServiceTypeControlDto;
import nts.uk.ctx.at.shared.app.attendanceitem.find.DailyServiceTypeControlFinder;
@Path("at/share/DailyServiceTypeControl")
@Produces("application/json")
public class DailyServiceTypeControlWebService extends WebService {
	@Inject
	private DailyServiceTypeControlFinder dailyServiceTypeControlFinder;
	@Inject
	private DailyServiceTypeControlUpdateCommand dailyServiceTypeControlUpdateCommand;

	@POST
	@Path("getListDailyServiceTypeControl/{workTypeCode}")
	List<DailyServiceTypeControlDto> getListDailyServiceTypeControl(
			@PathParam("workTypeCode") String workTypeCode) {
		return this.dailyServiceTypeControlFinder.getListDailyServiceTypeControl(workTypeCode);
	}
	@POST
	@Path("updateListDailyServiceTypeControlItem")
	void updateListDailyServiceTypeControlItem(
			List<DailyServiceTypeControlCommand> lstDailyServiceTypeControlCommand){
		this.dailyServiceTypeControlUpdateCommand.handle(lstDailyServiceTypeControlCommand);
	}
}
