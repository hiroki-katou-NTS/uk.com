package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.annualleave;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.common.days.MonthlyDays;

/**
 * 年休出勤率日数
 * @author shuichu_ishida
 */
@Getter
@Setter
public class AnnualLeaveAttdRateDays {

	/** 労働日数 */
	private MonthlyDays workingDays;
	/** 所定日数 */
	private MonthlyDays prescribedDays;
	/** 控除日数 */
	private MonthlyDays deductedDays;
	
	/**
	 * コンストラクタ
	 */
	public AnnualLeaveAttdRateDays(){
		
		this.workingDays = new MonthlyDays(0.0);
		this.prescribedDays = new MonthlyDays(0.0);
		this.deductedDays = new MonthlyDays(0.0);
	}
	
	/**
	 * ファクトリー
	 * @param workingDays 労働日数
	 * @param prescribedDays 所定日数
	 * @param deductedDays 控除日数
	 * @return 年休出勤率日数
	 */
	public static AnnualLeaveAttdRateDays of(
			MonthlyDays workingDays,
			MonthlyDays prescribedDays,
			MonthlyDays deductedDays){
		
		AnnualLeaveAttdRateDays domain = new AnnualLeaveAttdRateDays();
		domain.workingDays = workingDays;
		domain.prescribedDays = prescribedDays;
		domain.deductedDays = deductedDays;
		return domain;
	}
}
