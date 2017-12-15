package nts.uk.screen.at.app.dailymodify.query;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.app.find.actualworkinghours.AttendanceTimeOfDailyPerformFinder;
import nts.uk.ctx.at.record.app.find.actualworkinghours.dto.AttendanceTimeDailyPerformDto;
import nts.uk.ctx.at.shared.app.util.attendanceitem.AttendanceItemUtil;
import nts.uk.ctx.at.shared.app.util.attendanceitem.type.ItemValue;

/**　日別修正QueryProcessor　*/
@Stateless
public class DailyModifyQueryProcessor {

//	@Inject
//	private DailyWorkRecordFinder workRecordFinder;

	@Inject
	private AttendanceTimeOfDailyPerformFinder attendanceItemFinder;
	
//	@Inject
//	private FormatFinder formatFinder; 

	public DailyModifyResult initScreen(DailyModifyQuery query){
		DailyModifyResult result = new DailyModifyResult();
		AttendanceTimeDailyPerformDto itemDtos = this.attendanceItemFinder.find();
		List<ItemValue> viewItems = AttendanceItemUtil.toItemValues(itemDtos);
		result.setItems(viewItems);
		
		//TODO: get items format 
		return result;
	}
	
}
