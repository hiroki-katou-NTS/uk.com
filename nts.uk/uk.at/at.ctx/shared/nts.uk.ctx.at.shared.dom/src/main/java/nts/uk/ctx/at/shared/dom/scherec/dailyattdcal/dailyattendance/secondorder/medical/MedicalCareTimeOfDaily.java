package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.secondorder.medical;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.worktime.predset.WorkTimeNightShift;

/** 
 * 日別勤怠の医療時間 (new)
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared(勤務予定、勤務実績).日の勤怠計算.日別勤怠.■2次分.医療.日別勤怠の医療時間
 * 
 * 日別実績の医療時間 (old)
 * */
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
	
	public MedicalCareTimeOfDaily(WorkTimeNightShift dayNightAtr, AttendanceTime takeOverTime,
			AttendanceTime deductionTime, AttendanceTime workTime) {
		super();
		this.dayNightAtr = dayNightAtr;
		this.takeOverTime = takeOverTime;
		this.deductionTime = deductionTime;
		this.workTime = workTime;
	}
	
	public static MedicalCareTimeOfDaily defaultValue(){
		return new MedicalCareTimeOfDaily(WorkTimeNightShift.DAY_SHIFT, new AttendanceTime(0), new AttendanceTime(0), new AttendanceTime(0));
	}

}
