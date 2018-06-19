package nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.daynumber;

import java.util.Optional;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnnualLeaveGrantNumber {

	/**
	 * 日数
	 */
	private AnnualLeaveGrantDayNumber days;

	/**
	 * 時間
	 */
	private Optional<AnnualLeaveGrantTime> minutes;

	private AnnualLeaveGrantNumber(double days, Integer minutes) {
		this.days = new AnnualLeaveGrantDayNumber(days);
		this.minutes = minutes != null ? Optional.of(new AnnualLeaveGrantTime(minutes)) : Optional.empty();
	}

	public static AnnualLeaveGrantNumber createFromJavaType(double days, Integer minutes) {
		return new AnnualLeaveGrantNumber(days, minutes);
	}

}
