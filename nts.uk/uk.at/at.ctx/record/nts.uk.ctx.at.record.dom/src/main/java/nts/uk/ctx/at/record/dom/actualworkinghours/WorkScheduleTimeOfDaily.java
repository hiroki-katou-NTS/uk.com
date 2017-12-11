package nts.uk.ctx.at.record.dom.actualworkinghours;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;

/**
 * 
 * @author nampt
 * 日別実績の勤務予定時間
 *
 */
@Getter
public class WorkScheduleTimeOfDaily {
	
	//勤務予定時間
	private AttendanceTime workScheduleTime;
	
	//計画所定労働時間
	private AttendanceTime schedulePrescribedLaborTime;
	
	//実績所定労働時間
	private AttendanceTime recordPrescribedLaborTime;

}
