/**
 * 
 */
package nts.uk.screen.at.app.ksu001.getschedulesbyshift;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.screen.at.app.ksu001.getworkscheduleshift.GetWorkScheduleShift;
import nts.uk.screen.at.app.ksu001.getworkscheduleshift.GetWorkScheduleShiftParam;
import nts.uk.screen.at.app.ksu001.getworkscheduleshift.WorkScheduleShiftResult;

/**
 * @author laitv
 * <<ScreenQuery>> 予定・実績をシフトで取得する
 *
 */
@Stateless
public class GetSchedulesAndAchievementsByShift {
	
	@Inject
	private GetWorkScheduleShift getWorkScheduleShift;
	
	public SchedulesbyShiftDataResult getData(SchedulesbyShiftParam param) {
		
		WorkScheduleShiftResult workScheduleShiftResult = null; 
		// khơi tạo param của ScreenQuery 勤務予定（シフト）を取得する
		GetWorkScheduleShiftParam param1 = new GetWorkScheduleShiftParam(param.listShiftMasterNotNeedGetNew, param.listSid, param.startDate, param.endDate);
		// gọi ScreenQuery 勤務予定（シフト）を取得する
		workScheduleShiftResult = getWorkScheduleShift.getData(param1);
		
		if (param.getActualData) {
			//  call ScreenQuery 勤務実績（シフト）を取得する
		}
		
		return null;
	}

}
