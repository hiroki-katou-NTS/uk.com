/**
 * 
 */
package nts.uk.screen.at.app.ksu001.getschedulesbyshift;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.screen.at.app.ksu001.getworkscheduleshift.GetWorkScheduleShift;
import nts.uk.screen.at.app.ksu001.getworkscheduleshift.GetWorkScheduleShiftParam;
import nts.uk.screen.at.app.ksu001.getworkscheduleshift.WorkScheduleShiftDto;
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
	
	public SchedulesbyShiftDataResult dataSample(SchedulesbyShiftParam param) {
		
		// step 1 call ScreenQuery 勤務予定（シフト）を取得する
		WorkScheduleShiftResult resultStep51 = getWorkScheduleShift.dataSample(new GetWorkScheduleShiftParam());
		List<WorkScheduleShiftDto> listWorkScheduleShift = resultStep51.listWorkScheduleShift;
		
		if (param.getActualData) {
			// lấy dât thực tế
			// call ScreenQuery 勤務実績（シフト）を取得する
		}
		
		return new SchedulesbyShiftDataResult(listWorkScheduleShift , null);
	}

}
