package nts.uk.ctx.at.record.ws.dailyperformanceformat;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.record.app.command.dailyperformanceformat.DailyServiceTypeControlCommand;
import nts.uk.ctx.at.record.app.command.dailyperformanceformat.DailyServiceTypeControlUpdateCommand;
import nts.uk.ctx.at.record.app.find.dailyperformanceformat.DailyServiceTypeControlDto;
import nts.uk.ctx.at.record.app.find.dailyperformanceformat.DailyServiceTypeControlFinder;
@Path("at/record/DailyServiceTypeControl")
@Produces("application/json")
public class DailyServiceTypeControlWebService extends WebService {
	@Inject
	private DailyServiceTypeControlFinder dailyServiceTypeControlFinder;
	@Inject
	private DailyServiceTypeControlUpdateCommand dailyServiceTypeControlUpdateCommand;

	@POST
	@Path("getListDailyServiceTypeControl/{workTypeCode}")
	public List<DailyServiceTypeControlDto> getListDailyServiceTypeControl(
			@PathParam("workTypeCode") String workTypeCode) {
		return this.dailyServiceTypeControlFinder.getListDailyServiceTypeControl(workTypeCode);
	}
	@POST
	@Path("updateListDailyServiceTypeControlItem")
	public void updateListDailyServiceTypeControlItem(
			List<DailyServiceTypeControlCommand> lstDailyServiceTypeControlCommand){
		this.dailyServiceTypeControlUpdateCommand.handle(lstDailyServiceTypeControlCommand);
	}
	@POST
	@Path("getDailyServiceTypeControl/{workTypeCode}/{attendanceItemId}")
	public DailyServiceTypeControlDto getDailyServiceTypeControl(
			@PathParam("workTypeCode") String workTypeCode,@PathParam("attendanceItemId") int attendanceItemId ) {
		return this.dailyServiceTypeControlFinder.getDailyServiceTypeControl(workTypeCode, attendanceItemId);
	}
}
