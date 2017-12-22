package nts.uk.ctx.at.record.dom.actualworkinghours.daily.workingtime;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;

/** 日別実績の滞在時間 */
@Getter
public class StayingTimeOfDaily {

	/** PCログオフ後時間: 勤怠時間 */
	private AttendanceTime afterPCLogOffTime;
	
	/** PCログオン前時間: 勤怠時間 */
	private AttendanceTime beforePCLogOnTime;
	
	/** 出勤前時間: 勤怠時間 */
	private AttendanceTime beforeWoringTime;
	
	/** 滞在時間: 勤怠時間 */
	private AttendanceTime stayingTime;
	
	/** 退勤後時間: 勤怠時間 */
	private AttendanceTime afterLeaveTime;
	
	public StayingTimeOfDaily() {
		super();
	}

	public StayingTimeOfDaily(AttendanceTime afterPCLogOffTime, AttendanceTime beforePCLogOnTime, AttendanceTime beforeWoringTime,
			AttendanceTime stayingTime, AttendanceTime afterLeaveTime) {
		super();
		this.afterPCLogOffTime = afterPCLogOffTime;
		this.beforePCLogOnTime = beforePCLogOnTime;
		this.beforeWoringTime = beforeWoringTime;
		this.stayingTime = stayingTime;
		this.afterLeaveTime = afterLeaveTime;
	}
}
