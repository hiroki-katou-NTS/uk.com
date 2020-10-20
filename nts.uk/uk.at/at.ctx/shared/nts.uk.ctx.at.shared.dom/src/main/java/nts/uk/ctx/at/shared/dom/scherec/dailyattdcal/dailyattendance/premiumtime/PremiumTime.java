package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.premiumtime;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.amount.AttendanceAmountDaily;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;

@Getter
/**　nts.uk.ctx.at.record.dom.premiumtime.PremiumTime　参照 */
/** 割増時間 */
public class PremiumTime {
	// 割増時間NO - primitive value
	private Integer premiumTimeNo;

	// 割増時間
	private AttendanceTime premitumTime;
	
	/** 割増金額: 勤怠日別金額 */
	private AttendanceAmountDaily premiumAmount;
	
	public PremiumTime(Integer premiumTimeNo, AttendanceTime premitumTime) {
		super();
		this.premiumTimeNo = premiumTimeNo;
		this.premitumTime = premitumTime;
		this.premiumAmount = new AttendanceAmountDaily(0);
	}

	public PremiumTime(Integer premiumTimeNo, AttendanceTime premitumTime, AttendanceAmountDaily premiumAmount) {
		super();
		this.premiumTimeNo = premiumTimeNo;
		this.premitumTime = premitumTime;
		this.premiumAmount = premiumAmount;
	}
}
