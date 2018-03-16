package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.empinfo.grantremainingdata;

import lombok.Getter;
import nts.uk.ctx.at.record.dom.remainingnumber.base.YearDayNumber;

@Getter
public class AnnualLeaveConditionInfo {

	/**
	 * 所定日数
	 */
	private YearDayNumber prescribedDays;

	/**
	 * 控除日数
	 */
	private YearDayNumber deductedDays;

	/**
	 * 労働日数
	 */
	private YearDayNumber workingDays;

	public static AnnualLeaveConditionInfo createFromJavaType(Double prescribedDays, Double deductedDays, Double workingDays) {
		AnnualLeaveConditionInfo domain = new AnnualLeaveConditionInfo();
		domain.prescribedDays = new YearDayNumber(prescribedDays);
		domain.deductedDays = new YearDayNumber(deductedDays);
		domain.workingDays = new YearDayNumber(workingDays);
		return domain;
	}

}
