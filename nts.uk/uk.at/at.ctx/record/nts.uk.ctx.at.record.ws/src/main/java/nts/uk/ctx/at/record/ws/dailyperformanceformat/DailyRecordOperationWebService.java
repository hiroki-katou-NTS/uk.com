package nts.uk.ctx.at.record.ws.dailyperformanceformat;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.ctx.at.record.app.find.dailyperformanceformat.DailyRecordOperationFinder;
import nts.uk.ctx.at.record.app.find.dailyperformanceformat.dto.DailyRecordOperationDto;
@Path("at/record/DailyRecordOperation")
@Produces("application/json")
public class DailyRecordOperationWebService {
	@Inject
	private DailyRecordOperationFinder dailyRecordOperationFinder;
	@POST
	@Path("getSettingUnit")
	public DailyRecordOperationDto getSettingUnit(){
		return this.dailyRecordOperationFinder.getSettingUnit();
	}
}
