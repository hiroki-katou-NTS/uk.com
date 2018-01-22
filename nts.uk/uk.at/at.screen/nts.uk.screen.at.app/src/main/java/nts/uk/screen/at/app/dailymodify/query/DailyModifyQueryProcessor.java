package nts.uk.screen.at.app.dailymodify.query;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.app.find.dailyperform.DailyRecordDto;
import nts.uk.ctx.at.record.app.find.dailyperform.DailyRecordWorkFinder;
import nts.uk.ctx.at.shared.app.util.attendanceitem.AttendanceItemUtil;

/**　日別修正QueryProcessor　*/
@Stateless
public class DailyModifyQueryProcessor {

	@Inject
	private DailyRecordWorkFinder fullFinder;

	public DailyModifyResult initScreen(DailyModifyQuery query, List<Integer> itemIds){
		DailyModifyResult result = new DailyModifyResult();
		DailyRecordDto itemDtos = this.fullFinder.find(query.getEmployeeId(), query.getBaseDate());
		result.setItems(AttendanceItemUtil.toItemValues(itemDtos, itemIds));
		result.setEmployeeId(query.getEmployeeId());
		result.setDate(query.getBaseDate());
		return result;
	}
	
}
