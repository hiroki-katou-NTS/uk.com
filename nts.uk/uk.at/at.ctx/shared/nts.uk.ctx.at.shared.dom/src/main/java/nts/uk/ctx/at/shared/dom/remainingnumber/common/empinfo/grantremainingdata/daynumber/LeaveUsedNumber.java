package nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber;

import java.util.Optional;

import lombok.Getter;
import lombok.Setter;

/**
 * 休暇使用数
 * @author masaaki_jinno
 *
 */
@Getter
@Setter
public abstract class LeaveUsedNumber{

	/**
	 * 日数
	 */
	protected LeaveUsedDayNumber days;

	/**
	 * 時間
	 */
	protected Optional<LeaveUsedTime> minutes;

	/**
	 * 積み崩し日数
	 */
	protected Optional<LeaveUsedDayNumber> stowageDays;

//	public LeaveUsedNumber(double days, Integer minutes, Double stowageDays) {
//		this.days = new LeaveUsedDayNumber(days);
//		this.minutes = minutes != null ? Optional.of(new LeaveUsedTime(minutes)) : Optional.empty();
//		this.stowageDays = stowageDays != null ? Optional.of(new LeaveUsedDayNumber(stowageDays))
//				: Optional.empty();
//	}
//
//	public static LeaveUsedNumber createFromJavaType(double days, Integer minutes, Double stowageDays) {
//		return new LeaveUsedNumber(days, minutes, stowageDays);
//	}

}
