package nts.uk.ctx.at.record.dom.actualworkinghours.daily.workschedule;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;

/**
 * 
 * @author nampt
 * 日別実績の勤務予定時間
 *
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class WorkScheduleTimeOfDaily {
	
	//勤務予定時間
	private WorkScheduleTime workScheduleTime;
	
	//計画所定労働時間
	private AttendanceTime schedulePrescribedLaborTime;
	
	//実績所定労働時間
	private AttendanceTime recordPrescribedLaborTime;
	
}
