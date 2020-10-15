package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen;

import java.util.List;

import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainObject;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.premiumtime.PremiumTime;

@Getter
/** 応援別勤務の移動時間 */
public class OuenMovementTimeEachTimeSheet implements DomainObject {

	/** 総移動時間: 勤怠時間 */
	private AttendanceTime totalMoveTime;
	
	/** 休憩時間: 勤怠時間 */
	private AttendanceTime breakTime;
	
	/** 所定内移動時間: 勤怠時間 */
	private AttendanceTime withinMoveTime;
	
	/** 割増時間: 割増時間 */
	private List<PremiumTime> premiumTime;

	private OuenMovementTimeEachTimeSheet(AttendanceTime totalMoveTime, AttendanceTime breakTime,
			AttendanceTime withinMoveTime, List<PremiumTime> premiumTime) {
		super();
		this.totalMoveTime = totalMoveTime;
		this.breakTime = breakTime;
		this.withinMoveTime = withinMoveTime;
		this.premiumTime = premiumTime;
	}
	
	public static OuenMovementTimeEachTimeSheet create(AttendanceTime totalMoveTime, 
			AttendanceTime breakTime, AttendanceTime withinMoveTime, List<PremiumTime> premiumTime) {
		return new OuenMovementTimeEachTimeSheet(totalMoveTime, breakTime, withinMoveTime, premiumTime);
	}
	
}
