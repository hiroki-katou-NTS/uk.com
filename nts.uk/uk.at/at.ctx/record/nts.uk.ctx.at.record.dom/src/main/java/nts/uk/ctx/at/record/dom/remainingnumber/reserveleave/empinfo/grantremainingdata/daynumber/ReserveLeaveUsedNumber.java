package nts.uk.ctx.at.record.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.daynumber;

import java.util.Optional;

import lombok.Getter;

@Getter
public class ReserveLeaveUsedNumber {

	/**
	 * 日数
	 */
	private ReserveLeaveUsedDayNumber days;

	/**
	 * 上限超過消滅日数
	 */
	private Optional<ReserveLeaveUsedDayNumber> overLimitDays;

	public ReserveLeaveUsedNumber(double days, Double overLimitDays) {
		this.days = new ReserveLeaveUsedDayNumber(days);
		this.overLimitDays = overLimitDays != null ? Optional.of(new ReserveLeaveUsedDayNumber(overLimitDays))
				: Optional.empty();
	}

}
