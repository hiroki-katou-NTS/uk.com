package nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.YearDayNumber;

@Getter
@Setter
public class AnnualLeaveConditionInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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
