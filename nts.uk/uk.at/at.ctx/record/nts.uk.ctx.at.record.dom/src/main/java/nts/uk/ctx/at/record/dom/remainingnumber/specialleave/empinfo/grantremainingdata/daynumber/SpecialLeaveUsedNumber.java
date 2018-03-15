package nts.uk.ctx.at.record.dom.remainingnumber.specialleave.empinfo.grantremainingdata.daynumber;

import java.util.Optional;

import lombok.Getter;
import nts.uk.ctx.at.record.dom.remainingnumber.base.DayNumber;

@Getter
public class SpecialLeaveUsedNumber extends DayNumber {

	/**
	 * 積み崩し日数
	 */
	private Optional<Integer> stowageDays;

	/**
	 * 上限超過消滅日数
	 */
	private Optional<Integer> overLimitDays;

	public SpecialLeaveUsedNumber(float days, Integer minutes, Integer stowageDays, Integer overLimitDays) {
		super(days, minutes);
		this.stowageDays = stowageDays == null ? Optional.of(stowageDays) : Optional.empty();
		this.overLimitDays = overLimitDays == null ? Optional.of(overLimitDays) : Optional.empty();
	}

}
