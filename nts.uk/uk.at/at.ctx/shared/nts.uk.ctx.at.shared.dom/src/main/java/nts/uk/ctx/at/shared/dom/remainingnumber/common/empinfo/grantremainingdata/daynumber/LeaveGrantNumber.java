package nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber;

import java.util.Optional;

import lombok.Getter;
import lombok.Setter;

/**
 * 休暇付与数
 * @author masaaki_jinno
 *
 */
@Getter
@Setter
public abstract class LeaveGrantNumber {

	/**
	 * 日数
	 */
	protected LeaveGrantDayNumber days;

	/**
	 * 時間
	 */
	protected Optional<LeaveGrantTime> minutes;

//	protected LeaveGrantNumber(double days, Integer minutes) {
//		this.days = new LeaveGrantDayNumber(days);
//		this.minutes = minutes != null ? Optional.of(new LeaveGrantTime(minutes)) : Optional.empty();
//	}
//
//	public static LeaveGrantNumber createFromJavaType(double days, Integer minutes) {
//		return new LeaveGrantNumber(days, minutes);
//	}

}
