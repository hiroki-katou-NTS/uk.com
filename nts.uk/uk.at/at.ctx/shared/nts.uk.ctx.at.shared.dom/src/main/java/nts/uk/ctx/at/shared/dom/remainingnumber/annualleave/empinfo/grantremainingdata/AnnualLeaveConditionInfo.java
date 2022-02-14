package nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.common.days.YearlyDays;

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
	private YearlyDays prescribedDays;

	/**
	 * 控除日数
	 */
	private YearlyDays deductedDays;

	/**
	 * 労働日数
	 */
	private YearlyDays workingDays;
	
	public AnnualLeaveConditionInfo(){
		this.prescribedDays=new YearlyDays(0.0);
		this.deductedDays=new YearlyDays(0.0);
		this.workingDays=new YearlyDays(0.0);
	}
	

	public static AnnualLeaveConditionInfo createFromJavaType(Double prescribedDays, Double deductedDays, Double workingDays) {
		AnnualLeaveConditionInfo domain = new AnnualLeaveConditionInfo();
		domain.prescribedDays = new YearlyDays(prescribedDays);
		domain.deductedDays = new YearlyDays(deductedDays);
		domain.workingDays = new YearlyDays(workingDays);
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
			YearlyDays prescribedDays,
			YearlyDays deductedDays,
			YearlyDays workingDays) {

		AnnualLeaveConditionInfo domain = new AnnualLeaveConditionInfo();
		domain.prescribedDays = prescribedDays;
		domain.deductedDays = deductedDays;
		domain.workingDays = workingDays;
		return domain;
	}

}
