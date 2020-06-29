package nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.premiumtime;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;

@Getter
/** 割増時間 */
public class PremiumTime {
	// 割増時間NO - primitive value
	private Integer premiumTimeNo;

	// 割増時間
	private AttendanceTime premitumTime;

	public PremiumTime(Integer premiumTimeNo, AttendanceTime premitumTime) {
		super();
		this.premiumTimeNo = premiumTimeNo;
		this.premitumTime = premitumTime;
	}
}
