package nts.uk.screen.at.app.dailymodify.query;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.app.find.dailyperform.DailyRecordDto;
import nts.uk.ctx.at.record.app.find.dailyperform.DailyRecordWorkFinder;
import nts.uk.ctx.at.shared.app.util.attendanceitem.AttendanceItemUtil;
import nts.uk.ctx.at.shared.app.util.attendanceitem.type.ItemValue;

/**　日別修正QueryProcessor　*/
@Stateless
public class DailyModifyQueryProcessor {

	@Inject
	private DailyRecordWorkFinder fullFinder;
	
//	@Inject
//	private FormatFinder formatFinder; 

	public DailyModifyResult initScreen(DailyModifyQuery query, List<Integer> itemIds){
		DailyModifyResult result = new DailyModifyResult();
		DailyRecordDto itemDtos = this.fullFinder.find(query.getEmployeeId(), query.getBaseDate());
		//TODO: get item ids for show in screen
		List<ItemValue> viewItems = AttendanceItemUtil.toItemValues(itemDtos, itemIds);
		result.setItems(viewItems);
		result.setEmployeeId(query.getEmployeeId());
		result.setDate(query.getBaseDate());
		//TODO: get items format 
		return result;
	}
	
}
