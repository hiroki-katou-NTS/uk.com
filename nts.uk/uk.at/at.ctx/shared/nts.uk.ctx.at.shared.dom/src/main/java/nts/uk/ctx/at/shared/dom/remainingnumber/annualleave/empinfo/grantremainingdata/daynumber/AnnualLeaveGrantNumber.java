package nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.daynumber;

import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveGrantNumber;

@Getter
@Setter
public class AnnualLeaveGrantNumber extends LeaveGrantNumber {

//	/**
//	 * 日数
//	 */
//	private AnnualLeaveGrantDayNumber days;
//
//	/**
//	 * 時間
//	 */
//	private Optional<AnnualLeaveGrantTime> minutes;

	protected AnnualLeaveGrantNumber(double days, Integer minutes) {
		//super(days, minutes);
		this.days = new AnnualLeaveGrantDayNumber(days);
		this.minutes = minutes != null ? Optional.of(new AnnualLeaveGrantTime(minutes)) : Optional.empty();
	}

	public static AnnualLeaveGrantNumber createFromJavaType(double days, Integer minutes) {
		return new AnnualLeaveGrantNumber(days, minutes);
	}

}
