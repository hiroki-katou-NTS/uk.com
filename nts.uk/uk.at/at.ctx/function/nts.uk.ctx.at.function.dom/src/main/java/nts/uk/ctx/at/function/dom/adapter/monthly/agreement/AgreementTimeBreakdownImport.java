package nts.uk.ctx.at.function.dom.adapter.monthly.agreement;

import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;

public class AgreementTimeBreakdownImport {

	/** 残業時間 */
	private AttendanceTimeMonth overTime;
	/** 振替残業時間 */
	private AttendanceTimeMonth transferOverTime;
	/** 休出時間 */
	private AttendanceTimeMonth holidayWorkTime;
	/** 振替時間 */
	private AttendanceTimeMonth transferTime;
	/** フレックス超過時間 */
	private AttendanceTimeMonth flexExcessTime;
	/** 所定内割増時間 */
	private AttendanceTimeMonth withinPrescribedPremiumTime;
	/** 週割増時間 */
	private AttendanceTimeMonth weeklyPremiumTime;
	/** 月割増時間 */
	private AttendanceTimeMonth monthlyPremiumTime;
	
	/**
	 * コンストラクタ
	 */
	public AgreementTimeBreakdownImport() {
		
		this.overTime = new AttendanceTimeMonth(0);
		this.transferOverTime = new AttendanceTimeMonth(0);
		this.holidayWorkTime = new AttendanceTimeMonth(0);
		this.transferTime = new AttendanceTimeMonth(0);
		this.flexExcessTime = new AttendanceTimeMonth(0);
		this.withinPrescribedPremiumTime = new AttendanceTimeMonth(0);
		this.weeklyPremiumTime = new AttendanceTimeMonth(0);
		this.monthlyPremiumTime = new AttendanceTimeMonth(0);
	}
	
	/**
	 * ファクトリー
	 * @param overTime 残業時間
	 * @param transferOverTime 振替残業時間
	 * @param holidayWorkTime 休出時間
	 * @param transferTime 振替時間
	 * @param flexExcessTime フレックス超過時間
	 * @param withinPrescribedPremiumTime 所定内割増時間
	 * @param weeklyPremiumTime 週割増時間
	 * @param monthlyPremiumTime 月割増時間
	 * @return 36協定時間内訳
	 */
	public static AgreementTimeBreakdownImport of(
			AttendanceTimeMonth overTime,
			AttendanceTimeMonth transferOverTime,
			AttendanceTimeMonth holidayWorkTime,
			AttendanceTimeMonth transferTime,
			AttendanceTimeMonth flexExcessTime,
			AttendanceTimeMonth withinPrescribedPremiumTime,
			AttendanceTimeMonth weeklyPremiumTime,
			AttendanceTimeMonth monthlyPremiumTime){
		
		AgreementTimeBreakdownImport domain = new AgreementTimeBreakdownImport();
		domain.overTime = overTime;
		domain.transferOverTime = transferOverTime;
		domain.holidayWorkTime = holidayWorkTime;
		domain.transferTime = transferTime;
		domain.flexExcessTime = flexExcessTime;
		domain.withinPrescribedPremiumTime = withinPrescribedPremiumTime;
		domain.weeklyPremiumTime = weeklyPremiumTime;
		domain.monthlyPremiumTime = monthlyPremiumTime;
		return domain;
	}
}