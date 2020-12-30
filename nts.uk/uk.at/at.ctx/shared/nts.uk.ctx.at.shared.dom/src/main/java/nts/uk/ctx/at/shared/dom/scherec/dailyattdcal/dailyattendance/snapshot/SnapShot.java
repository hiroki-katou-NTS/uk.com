package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.snapshot;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;

/** スナップショット（仮） */
@Getter
@NoArgsConstructor
public class SnapShot {

	/** 勤務情報: 勤務情報 */
	private WorkInformation workInfo;
	
	/** 所定時間: 勤怠時間 */
	private AttendanceTime predetermineTime;
	
	private SnapShot(WorkInformation workInfo, AttendanceTime predetermineTime) {
		this.workInfo = workInfo;
		this.predetermineTime = predetermineTime;
	}
	
	public static SnapShot of(WorkInformation workInfo, AttendanceTime predetermineTime) {
		
		return new SnapShot(workInfo, predetermineTime);
	}
}
