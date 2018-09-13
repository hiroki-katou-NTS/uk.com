package nts.uk.ctx.at.record.dom.actualworkinghours.daily.workschedule;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;

/** 勤務予定時間 */
@Getter
public class WorkScheduleTime {

	/** 合計時間: 勤怠時間 */
	private AttendanceTime total;
	
	/** 所定外時間: 勤怠時間 */
	private AttendanceTime excessOfStatutoryTime; 
	
	/** 所定内時間: 勤怠時間 */
	private AttendanceTime withinStatutoryTime;

	public WorkScheduleTime() {
		super();
	}

	public WorkScheduleTime(AttendanceTime total, AttendanceTime excessOfStatutoryTime,
			AttendanceTime withinStatutoryTime) {
		super();
		this.total = total;
		this.excessOfStatutoryTime = excessOfStatutoryTime;
		this.withinStatutoryTime = withinStatutoryTime;
	}
	
	public static WorkScheduleTime defaultValue(){
		return new WorkScheduleTime(new AttendanceTime(0), new AttendanceTime(0), new AttendanceTime(0));
	}

}
