package nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.daynumber;

import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveUndigestNumber;

/**
 * 年休未消化数
 * @author masaaki_jinno
 *
 */
@Getter
@Setter
public class AnnualLeaveUndigestNumber extends LeaveUndigestNumber {

//	/**
//	 * 日数
//	 */
//	private AnnualLeaveUndigestDayNumber days;
//
//	/**
//	 * 時間
//	 */
//	private Optional<AnnualLeaveUndigestTime> minutes;

	private AnnualLeaveUndigestNumber(double days, Integer minutes) {
		// super(days, minutes);
		this.days = new AnnualLeaveUndigestDayNumber(days);
		this.minutes = minutes != null ? Optional.of(new AnnualLeaveUndigestTime(minutes)) : Optional.empty();
	}
	
	public static AnnualLeaveUndigestNumber createFromJavaType(double days, Integer minutes) {
		return new AnnualLeaveUndigestNumber(days, minutes);
	}
	
}
