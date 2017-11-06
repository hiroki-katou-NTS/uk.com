package nts.uk.ctx.at.record.dom.actualworkinghours;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;

/**
 * 
 * @author nampt
 * 日別実績の勤怠時間 - root
 *
 */
@Getter
public class AttendanceTimeOfDailyPerformance extends AggregateRoot {

	private String employeeId;
	
	private GeneralDate ymd;
	
	//勤務予定時間
	private WorkScheduleTimeOfDaily workScheduleTimeOfDaily;
	
	//実働時間 
	private ActualWorkingTimeOfDaily actualWorkingTimeOfDaily;
	
	//滞在時間
	private AttendanceTime stayingTime;
	
	//不就労時間
	private AttendanceTime unEmployedTime;
	
}
