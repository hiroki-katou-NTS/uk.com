package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.empinfo.grantremainingdata.daynumber;

import java.util.Optional;

import lombok.Getter;
import nts.uk.ctx.at.record.dom.remainingnumber.base.DayNumber;

@Getter
public class AnnualLeaveUsedNumber extends DayNumber {

	/**
	 * 積み崩し日数
	 */
	private Optional<Float> stowageDays;

	public AnnualLeaveUsedNumber(float days, Integer minutes, Float stowageDays) {
		super(days, minutes);
		this.stowageDays = stowageDays != null ? Optional.of(stowageDays) : Optional.empty();
	}

}
