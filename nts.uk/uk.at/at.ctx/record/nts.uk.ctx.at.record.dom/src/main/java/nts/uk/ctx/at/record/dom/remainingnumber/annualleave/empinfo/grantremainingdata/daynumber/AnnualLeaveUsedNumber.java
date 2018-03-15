package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.empinfo.grantremainingdata.daynumber;

import java.util.Optional;

import lombok.Getter;
import nts.uk.ctx.at.record.dom.remainingnumber.base.DayNumber;

@Getter
public class AnnualLeaveUsedNumber extends DayNumber {

	/**
	 * 積み崩し日数
	 */
	private Optional<Integer> stowageDays;

	public AnnualLeaveUsedNumber(int days, Integer hours, Integer stowageDays) {
		super(days, hours);
		this.stowageDays = stowageDays != null ? Optional.of(stowageDays) : Optional.empty();
	}

}
