/**
 * 
 */
package nts.uk.screen.at.app.ksu001.getschedulesbyshift;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.screen.at.app.ksu001.getworkscheduleshift.GetScheduleOfShift;
import nts.uk.screen.at.app.ksu001.getworkscheduleshift.ScheduleOfShiftParam;
import nts.uk.screen.at.app.ksu001.getworkscheduleshift.ScheduleOfShiftDto;
import nts.uk.screen.at.app.ksu001.getworkscheduleshift.ScheduleOfShiftResult;

/**
 * @author laitv
 * <<ScreenQuery>> 予定・実績をシフトで取得する
 *
 */
@Stateless
public class GetScheduleActualOfShift {
	
	@Inject
	private GetScheduleOfShift getWorkScheduleShift;
	
	public SchedulesbyShiftDataResult dataSample(SchedulesbyShiftParam param) {
		
		// step 1 call ScreenQuery 勤務予定（シフト）を取得する
		ScheduleOfShiftResult resultStep51 = getWorkScheduleShift.getWorkScheduleShift(new ScheduleOfShiftParam());
		List<ScheduleOfShiftDto> listWorkScheduleShift = resultStep51.listWorkScheduleShift;
		
		if (param.getActualData) {
			// lấy data thực tế
			// call ScreenQuery 勤務実績（シフト）を取得する
		}
		
		return new SchedulesbyShiftDataResult(listWorkScheduleShift , null);
	}

}
