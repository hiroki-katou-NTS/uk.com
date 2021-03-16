package nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.YearDayNumber;

/**
 * 年休付与条件情報
 */
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
	/**
	 * ファクトリー
	 * @param prescribedDays　所定日数
	 * @param deductedDays　控除日数
	 * @param workingDays 労働日数
	 * @return AnnualLeaveConditionInfo
	*/
	public static AnnualLeaveConditionInfo of(
			YearDayNumber prescribedDays,
			YearDayNumber deductedDays,
			YearDayNumber workingDays) {

		AnnualLeaveConditionInfo domain = new AnnualLeaveConditionInfo();
		domain.prescribedDays = prescribedDays;
		domain.deductedDays = deductedDays;
		domain.workingDays = workingDays;
		return domain;
	}

}
