package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.temporarytime;

import lombok.Data;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;

/** 日別実績の臨時枠時間 */
@Data
public class TemporaryFrameTimeOfDaily {

	/** 勤務NO: 勤務NO */
	private WorkNo workNo;
	
	/** 臨時時間: 勤怠時間 */
	private AttendanceTime temporaryTime;
	
	/** 臨時深夜時間: 勤怠時間 */
	private AttendanceTime temporaryLateNightTime;

	public TemporaryFrameTimeOfDaily() {
		super();
	}
	
	public TemporaryFrameTimeOfDaily(WorkNo workNo, AttendanceTime temporaryTime,
			AttendanceTime temporaryLateNightTime) {
		super();
		this.workNo = workNo;
		this.temporaryTime = temporaryTime;
		this.temporaryLateNightTime = temporaryLateNightTime;
	}
}
