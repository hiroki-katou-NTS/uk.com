package nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber;

import java.util.Optional;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LeaveRemainingNumber {

	/**
	 * 日数
	 */
	protected LeaveRemainingDayNumber days;

	/**
	 * 時間
	 */
	protected Optional<LeaveRemainingTime> minutes;

	protected LeaveRemainingNumber(double days, Integer minutes) {
		this.days = new LeaveRemainingDayNumber(days);
		this.minutes = minutes != null ? Optional.of(new LeaveRemainingTime(minutes)) : Optional.empty();
	}
	
	public static LeaveRemainingNumber createFromJavaType(double days, Integer minutes) {
		return new LeaveRemainingNumber(days, minutes);
	}

}
