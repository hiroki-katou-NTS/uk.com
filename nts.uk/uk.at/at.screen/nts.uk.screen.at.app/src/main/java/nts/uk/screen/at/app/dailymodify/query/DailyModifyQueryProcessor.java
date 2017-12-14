package nts.uk.screen.at.app.dailymodify.query;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.app.find.workrecord.daily.DailyWorkRecordFinder;
import nts.uk.ctx.at.record.app.find.workrecord.daily.dto.DailyWorkRecordDto;
import nts.uk.ctx.at.shared.app.find.attendanceitem.daily.DailyAttendanceItemFinder;
import nts.uk.ctx.at.shared.app.find.attendanceitem.daily.dto.AttendanceTimeDailyPerformDto;
import nts.uk.ctx.at.shared.app.util.attendanceitem.AttendanceItemUtil;
import nts.uk.ctx.at.shared.app.util.attendanceitem.type.ItemValue;

/**　日別修正QueryProcessor　*/
@Stateless
public class DailyModifyQueryProcessor {

	@Inject
	private DailyWorkRecordFinder workRecordFinder;

	@Inject
	private DailyAttendanceItemFinder attendanceItemFinder;
	
//	@Inject
//	private FormatFinder formatFinder; 

	public DailyModifyResult initScreen(DailyModifyQuery query){
		DailyModifyResult result = new DailyModifyResult();
		DailyWorkRecordDto itemDtos = this.workRecordFinder.find();
		List<ItemValue> viewItems = AttendanceItemUtil.toItemValues(itemDtos);
		result.setItems(viewItems);
		
		// need confirm
//		AttendanceTimeDailyPerformDto item = this.attendanceItemFinder.find();
		
		//TODO: get items format 
		return result;
	}
	
}
