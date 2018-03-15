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
	
}
