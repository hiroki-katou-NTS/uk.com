package nts.uk.ctx.at.record.dom.actualworkinghours.daily.medical;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.worktime.predset.WorkTimeNightShift;

/** 日別実績の医療時間 */
@Getter
public class MedicalCareTimeOfDaily {

	/** 日勤夜勤区分: 日勤夜勤区分 */
	private WorkTimeNightShift dayNightAtr;
	
	/** 申送時間: 勤怠時間 */
	private AttendanceTime takeOverTime;
	
	/** 控除時間: 勤怠時間 */
	private AttendanceTime deductionTime;
	
	/** 勤務時間: 勤怠時間 */
	private AttendanceTime workTime;
}
