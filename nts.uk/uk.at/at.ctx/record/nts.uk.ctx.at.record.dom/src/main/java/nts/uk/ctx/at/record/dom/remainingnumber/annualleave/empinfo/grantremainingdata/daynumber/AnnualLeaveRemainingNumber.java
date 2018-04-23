package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.empinfo.grantremainingdata.daynumber;

import java.util.Optional;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnnualLeaveRemainingNumber {

	/**
	 * 日数
	 */
	private AnnualLeaveRemainingDayNumber days;

	/**
	 * 時間
	 */
	private Optional<AnnualLeaveRemainingTime> minutes;

	private AnnualLeaveRemainingNumber(double days, Integer minutes) {
		this.days = new AnnualLeaveRemainingDayNumber(days);
		this.minutes = minutes != null ? Optional.of(new AnnualLeaveRemainingTime(minutes)) : Optional.empty();
	}
	
	public static AnnualLeaveRemainingNumber createFromJavaType(double days, Integer minutes) {
		return new AnnualLeaveRemainingNumber(days, minutes);
	}

}
