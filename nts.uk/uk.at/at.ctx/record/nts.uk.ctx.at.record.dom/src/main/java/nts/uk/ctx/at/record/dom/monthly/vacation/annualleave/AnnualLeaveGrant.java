package nts.uk.ctx.at.record.dom.monthly.vacation.annualleave;

import lombok.Getter;

/**
 * 年休付与情報
 * @author shuichu_ishida
 */
@Getter
public class AnnualLeaveGrant {

	/** 付与区分 */
	private int grantAtr;
	/** 付与日数 */
	private int grantDays;
	/** 付与労働日数 */
	private int grantWorkingDays;
	/** 付与所定日数 */
	private int grantPrescribedDays;
	/** 付与控除日数 */
	private int grantDeductedDays;
	/** 控除日数付与前 */
	private int deductedDaysBeforeGrant;
	/** 控除日数付与後 */
	private int deductedDaysAfterGrant;
	/** 出勤率 */
	private int attendanceRate;
	
	/**
	 * コンストラクタ
	 */
	public AnnualLeaveGrant(){
		
		this.grantAtr = 0;
		this.grantDays = 0;
		this.grantWorkingDays = 0;
		this.grantPrescribedDays = 0;
		this.grantDeductedDays = 0;
		this.deductedDaysBeforeGrant = 0;
		this.deductedDaysAfterGrant = 0;
		this.attendanceRate = 0;
	}
}
