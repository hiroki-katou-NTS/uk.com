package nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.workschedule;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;

/**
 * 日別勤怠の勤務予定時間 (new)
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared(勤務予定、勤務実績).日の勤怠計算.日別勤怠.勤務予定.日別勤怠の勤務予定時間
 * 
 * @author nampt
 * 日別実績の勤務予定時間 (old)
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
	
	public static WorkScheduleTimeOfDaily defaultValue(){
		return new WorkScheduleTimeOfDaily(WorkScheduleTime.defaultValue(), new AttendanceTime(0), new AttendanceTime(0));
	}
}
