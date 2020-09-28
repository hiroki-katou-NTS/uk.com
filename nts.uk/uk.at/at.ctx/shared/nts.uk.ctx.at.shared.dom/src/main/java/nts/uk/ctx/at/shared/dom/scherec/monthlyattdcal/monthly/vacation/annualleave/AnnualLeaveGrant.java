package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.annualleave;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.common.days.MonthlyDays;
import nts.uk.ctx.at.shared.dom.common.days.YearlyDays;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.daynumber.AnnualLeaveGrantDayNumber;

/**
 * 年休付与情報
 * @author shuichu_ishida
 */
@Getter
@Setter
public class AnnualLeaveGrant implements Cloneable {

	/** 付与日数 */
	private AnnualLeaveGrantDayNumber grantDays;
	/** 付与労働日数 */
	private YearlyDays grantWorkingDays;
	/** 付与所定日数 */
	private YearlyDays grantPrescribedDays;
	/** 付与控除日数 */
	private YearlyDays grantDeductedDays;
	/** 控除日数付与前 */
	private MonthlyDays deductedDaysBeforeGrant;
	/** 控除日数付与後 */
	private MonthlyDays deductedDaysAfterGrant;
	/** 出勤率 */
	private AttendanceRate attendanceRate;
	
	/**
	 * コンストラクタ
	 */
	public AnnualLeaveGrant(){
		
		this.grantDays = new AnnualLeaveGrantDayNumber(0.0);
		this.grantWorkingDays = new YearlyDays(0.0);
		this.grantPrescribedDays = new YearlyDays(0.0);
		this.grantDeductedDays = new YearlyDays(0.0);
		this.deductedDaysBeforeGrant = new MonthlyDays(0.0);
		this.deductedDaysAfterGrant = new MonthlyDays(0.0);
		this.attendanceRate = new AttendanceRate(0.0);
	}
	
	/**
	 * ファクトリー
	 * @param grantDays 付与日数
	 * @param grantWorkingDays 付与労働日数
	 * @param grantPrescribedDays 付与所定日数
	 * @param grantDeductedDays 付与控除日数
	 * @param deductedDaysBeforeGrant 控除日数付与前
	 * @param deductedDaysAfterGrant 控除日数付与後
	 * @param attendanceRate 出勤率
	 * @return 年休付与情報
	 */
	public static AnnualLeaveGrant of(
			AnnualLeaveGrantDayNumber grantDays,
			YearlyDays grantWorkingDays,
			YearlyDays grantPrescribedDays,
			YearlyDays grantDeductedDays,
			MonthlyDays deductedDaysBeforeGrant,
			MonthlyDays deductedDaysAfterGrant,
			AttendanceRate attendanceRate){
		
		AnnualLeaveGrant domain = new AnnualLeaveGrant();
		domain.grantDays = grantDays;
		domain.grantWorkingDays = grantWorkingDays;
		domain.grantPrescribedDays = grantPrescribedDays;
		domain.grantDeductedDays = grantDeductedDays;
		domain.deductedDaysBeforeGrant = deductedDaysBeforeGrant;
		domain.deductedDaysAfterGrant = deductedDaysAfterGrant;
		domain.attendanceRate = attendanceRate;
		return domain;
	}
	
	@Override
	public AnnualLeaveGrant clone() {
		AnnualLeaveGrant cloned = new AnnualLeaveGrant();
		try {
			cloned.grantDays = new AnnualLeaveGrantDayNumber(this.grantDays.v());
			cloned.grantWorkingDays = new YearlyDays(this.grantWorkingDays.v());
			cloned.grantPrescribedDays = new YearlyDays(this.grantPrescribedDays.v());
			cloned.grantDeductedDays = new YearlyDays(this.grantDeductedDays.v());
			cloned.deductedDaysBeforeGrant = new MonthlyDays(this.deductedDaysBeforeGrant.v());
			cloned.deductedDaysAfterGrant = new MonthlyDays(this.deductedDaysAfterGrant.v());
			cloned.attendanceRate = new AttendanceRate(this.attendanceRate.v().doubleValue());
		}
		catch (Exception e){
			throw new RuntimeException("AnnualLeaveGrant clone error.");
		}
		return cloned;
	}
}
