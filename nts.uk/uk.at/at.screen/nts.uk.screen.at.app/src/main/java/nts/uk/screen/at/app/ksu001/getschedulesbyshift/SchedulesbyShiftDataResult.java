/**
 * 
 */
package nts.uk.screen.at.app.ksu001.getschedulesbyshift;

import java.util.List;

import nts.uk.screen.at.app.ksu001.displayinshift.PageShift;
import nts.uk.screen.at.app.ksu001.getworkscheduleshift.WorkScheduleShiftDto;

/**
 * @author laitv
 *
 */
public class SchedulesbyShiftDataResult {
	
	public List<WorkScheduleShiftDto> listWorkScheduleShift;  // List<勤務予定（シフト）dto>
	public List<PageShift> listOfPageShift;                   // 取得したシフト一覧：List<シフトマスタ, Optional<出勤休日区分>>

}
